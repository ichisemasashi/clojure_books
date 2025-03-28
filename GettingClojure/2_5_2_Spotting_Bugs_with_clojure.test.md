
##clojure.testでバグを見つける

真新しい本屋の在庫プロジェクトを作って、テストの冒険を始めよう：

```bash 
$ lein new inventory
```

さて、このような本の在庫があるとしよう：
  
```clojure
[{:title "2001" :author "Clarke" :copies 21}
{:title "Emma" :author "Austen" :copies 10}
{:title "Misery" :author "King" :copies 101}])
```

そして、それを使って便利なことをするためにいくつかの関数を書く：
  
test/inventory/src/inventory/core.clj
```clojure
(ns inventory.core)
    
(defn find-by-title
  "Search for a book by title,
  where title is a string and books is a collection
  of book maps, each of which must have a :title entry"
  [title books]
  (some #(when (= (:title %) title) %) books))
(defn number-of-copies-of
  "Return the number of copies in inventory of the
  given title, where title is a string and books is a collection
  of book maps each of which must have a :title entry"
  [title books]
  (:copies (find-by-title title books)))
```


そして今、私たちはこのコードが主張することを実行することを自分自身に納得させる必要がある。幸いなことに、Clojureには伝統的なユニットテストを書くためのシンプルで有能なライブラリが用意されている： `clojure.test`です。

Clojureプロジェクトでは、テストは通常 `test` サブディレクトリにあります。ある名前空間のテストを並列の `-test` モジュールに置くのが通例である。そのため、`inventory.core` 名前空間をテストするには、`inventory.core-test` を作成します。このtest名前空間の中に`clojure.test`を入れる必要がある。テストを書くということは、clojure.testが提供する機能を全て使うということなので、`:refer :all`を使っても許されるだろう：

test/inventory/test/inventory/core_test.clj
```clojure
(ns inventory.core-test
  (:require [clojure.test :refer :all])
  (:require [inventory.core :as i]))
```

もちろん、テストする名前空間（この場合は `inventory.core`）で `require` も必要だ。

これですべてのインフラストラクチャが揃ったので、テストをビルドするのは簡単だ。`deftest`を使うのだ。 例えば、本のタイトルで検索できることを確認したい場合、次のように書きます：

```clojure
(def books
  [{:title "2001" :author "Clarke" :copies 21}
   {:title "Emma" :author "Austen" :copies 10}
   {:title "Misery" :author "King" :copies 101}])
(deftest test-finding-books
  (is (not (nil? (i/find-by-title "Emma" books)))))
```

見ての通り、 `deftest` はシンボル (テストの名前) を取り、その後にテストのコードを続けます。この例では、`clojure.test` が提供する `is` を使って、インベントリにある本をタイトルで見つけることができることを表明しています。isを使うのはこれ以上簡単なことはありません：


指定した式が真であれば、テストはパスします。もしそうでなければ、テストは失敗する。

`deftest`は引数ゼロの関数をテスト名に結びつけます。したがって、テストを実行する方法のひとつ（しかしそれだけではありません）は、その関数を呼び出すことです：

test/inventory/dev/run_test.clj
```clojure
(require '[inventory.core-test :as ct])

(ct/test-finding-books)
```

テストが成功すれば、関数は静かに `nil` を返します。テストが失敗した場合は、それなりに有益な例外が発生します：

```
FAIL in (test-something-that-fails) (form-init8361253184899189179.clj:2)
expected: (not (nil? (i/find-by-title "Some other book" inventory)))
  actual: (not (not true))
```

また、1つのテストにつき1つの式に制限されることもありません。ですから、テストの中で複数の条件をテストすることに意味がある場合は、このように書くことができます：

test/inventory/test/inventory/core_test.clj
```clojure
(deftest test-finding-books-better
  (is (not (nil? (i/find-by-title "Emma" books))))
  (is (nil? (i/find-by-title "XYZZY" books))))
```

`testing`を使えば、テストをサブテスト（コンテキスト）にまとめることもできる：


```clojure
(deftest test-basic-inventory
  (testing "Finding books"
    (is (not (nil? (i/find-by-title "Emma" books))))
    (is (nil? (i/find-by-title "XYZZY" books))))
  (testing "Copies in inventory"
    (is (= 10 (i/number-of-copies-of "Emma" books)))))
```

`deftest`と `testing` を組み合わせることで、テストをあらゆる方法で整理することができる。


