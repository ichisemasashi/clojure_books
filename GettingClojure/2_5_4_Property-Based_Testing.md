
### プロパティベースのテスト

`clojure.test` は伝統的なユニットテストの優れた実装ですが、すべてのユニットテストフレームワークに共通する欠点があります。コードの与えられたプロパティをテストするために、あなたはそのプロパティを行使するいくつかの例を考え、タイプし始めます。このアプローチの問題点は、テストが本当に興味のあるもの、つまりプロパティから一歩離れていることです。例えば、この章で最初に書いたテストを見てみよう：

```clojure
(deftest test-finding-books
  (is (not (nil? (i/find-by-title "Emma" books)))))
```

このテストは小説『エマ』と関係があるのだろうと、傍観者は思うかもしれない。そうではない。このテストは、インベントリにある本を検索したときに、`nil`ではない結果を得るべきだということを言おうとしているのだ。

では、コードを一例ずつテストする代わりに、テストしたいプロパティを、そのプロパティが保持すべき入力データの説明とともに記述することを想像してみよう。「インベントリからランダムに選択された本のタイトルを検索する場合、`find-by-title` は `nil` ではないものを返すべきである」。

このように「これを実行したときに `nil` ではない結果が得られるはずだ」というようなアサーションを行えるようにすることが、生成的なプロパティベースのテストの背後にある考え方です。人気のある `test.check` Clojure ライブラリは、[この種のテスト](https://github.com/clojure/test.check) を構築するのに役立ちます。

そのために、 `test.check` ライブラリには様々なジェネレータが用意されており、あなたのニーズに合わせて多かれ少なかれランダムなテストデータを生成することができます。例えば、`string-alphanumeric`と呼ばれるジェネレーターがあり、本のタイトルを基本的にランダムに生成するのに使うことができる。実際に値を生成するには、`test.check` が提供する別の関数 sample を使用する。`sample`にジェネレーターを渡すと、ジェネレーターから出てきた次の10個の値を返してくれる。このようにすると

test/sample_generators.clj
```clojure
(require '[clojure.test.check.generators :as gen])

(gen/sample gen/string-alphanumeric)
```

これを実行すると、`string-alphanumeric`によって生成された最初の10個の値が得られる：

```clojure
("" "Q" "h" "q" "7" "ap" "6" "fdKMQ" "KcuWd" "h2O")
```

ご想像の通り、数値、ブール値、キーワード、その他すべての基本的なClojure値のジェネレータもあり、`int`、`pos-int`、`Boolean`、`keyword`などの名前がついています。

これらのジェネレータで武装することで、本のテストデータに取りかかることができる：

test/inventory/test/inventory/core_gen_test.clj
```clojure
(def title-gen gen/string-alphanumeric)
(def author-gen gen/string-alphanumeric)
(def copies-gen gen/pos-int)
```

タイトルの生成に `string-alphanumeric` を使用した場合の問題点として、長さがゼロの文字列が生成されることがあります。同様に、`pos-int`はゼロを生成するので、在庫の本の部数としては意味をなさないかもしれない。幸いなことに、`test.check`には`such-that`という解決策もある。

したがって、（空でない）タイトルのジェネレーター、著者のジェネレーター、（ゼロでない）部数のジェネレーターを作ることができます：

```clojure
(def title-gen (gen/such-that not-empty gen/string-alphanumeric))
(def author-gen (gen/such-that not-empty gen/string-alphanumeric))
(def copies-gen (gen/such-that (complement zero?) gen/pos-int))
```

しかし、私たちが本当に求めているのは、タイトルや著者、部数といった個別のものではなく、それらを含む一連のマップなのだ。そこで、`hash-map`が登場する。`test.check`の`hash-map`では、キーとジェネレーターを交互に渡すと、キーとジェネレーターの値を関連付けたマップが無限に出てくる。`hash-map`を使えば、ランダムな本のマップを無限に提供するジェネレーターを作ることができる：

```clojure
(def book-gen
  (gen/hash-map :title title-gen :author author-gen :copies copies-gen))
```

それを使って在庫を無限に生み出すことができる：

```clojure
(def inventory-gen (gen/not-empty (gen/vector book-gen)))
```

しかし、在庫を無限に供給することが必要なのではない。また、各在庫から1冊の本が必要である。そのためには、`element`（コレクションからランダムな要素を取り出す）と、`let`（既存のジェネレーターの値に基づいた複合ジェネレーターを作ることができる）のジェネレーターバージョンを使うことができる：

```clojure
(def inventory-and-book-gen
  (gen/let [inventory inventory-gen
            book (gen/elements inventory)]
    {:inventory inventory :book book}))
```

これを実行すると、`inventory-and-book-gen`で一連のマップを生成するジェネレーターができ、各マップには本の在庫と在庫から取り出した個々の本が含まれる：

test/sample_generators.clj
```clojure
{:inventory [{:title "S0" :author "po" :copies 2}
             {:title "B2d" :author "fN" :copies 2}]
 :book {:title "S0" :author "po" :copies 2}}
```

これでテスト用のデータが揃った。

> [!NOTE]
>
> **Generator Magic?**
>
> `test.check`ジェネレータが魔法のように見えても気にしないでください。少し魔法のようですが、すべて洗練された、しかしまったく普通のClojureで実装されています。例えば、各ジェネレータは実際にはレコードのインスタンスで、中に関数が埋め込まれています。そして、Lazy Sequencesでランダムな本を生成することから想像できるように、遅延シーケンスもあります。


