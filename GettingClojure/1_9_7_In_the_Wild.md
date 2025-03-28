                 
### 野生の中で
  
Clojureの名前空間といえば、1つだけ巨大なものがある: `clojure.core`だ。 clojure.core`名前空間は、基本的で定義済みの関数、例えば `println` や `str` や `count` が存在する場所だ。では、なぜ `println` や `count` と書くだけで、 `clojure.core/println` や `clojure.core/count` と書かないのだろうか？clojure.core/`が必要になることはほとんどありません。新しい名前空間を作成した直後に、`ns`がこれと同等のことを行うからです：

```clojure
(require '[clojure.core :refer :all]) 
```

この`:all`オプションは、先ほどの`:refer`をさらに大げさにしたものだ。
他の名前空間から「すべての」束縛を取り込みます。言語環境を起動させるのには便利ですが、日常的なコードではあまり使わない方がいいかもしれません。

もし、`clojure.core`にどのような良いものが含まれているのか興味があるのであれば、ドキュメントを読むこともできますが（実際、ドキュメントを読むべきです）、この章で説明したことを考慮すると、`ns-map`を使用して、`clojure.core`に隠れているすべての不思議を明らかにすることもできます：

```clojure
(ns-map 'clojure.core)
```

それだけでなく、`clojure.core`名前空間が`clojure/core.clj`というファイルに存在することも推測できます。実際、Clojureのソースコードを見ると、まさに[それが見つかる](https://github.com/clojure/clojure/blob/master/src/clj/clojure/core.clj)。

このソースファイルは、`not`の定義を含む、あらゆる種類の驚異に満ちている：

```clojure
(ns clojure.core) 
;; Lots of code omitted...
(defn not
  "Returns true if x is logical false, false otherwise."
  [x] (if x false true))
```

また、`not=`の定義も保持している：

```clojure
(defn not=
  "Same as (not (= obj1 obj2))"
  ([x] false)
  ([x y] (not (= x y)))
  ([x y & more]
    (not (apply = x y more))))
```

新しいプログラミング言語を探求する素晴らしい方法は、新しいプログラミング言語を内側から探求することです。

もちろん、Clojureには言語に組み込まれた要素以上のものがあります。Clojureの世界は、コードを書いてリリースする人々でいっぱいの、生きて呼吸する技術的なエコシステムです。うれしいことに、Leiningenプロジェクトから他の人のコードにアクセスするには、1つのステップを追加するだけでよい：Leiningenが生成した`project.clj`ファイルに依存関係を追加するだけでよい。

例えば、Korma SQLライブラリをブックストア・アプリケーションで使いたい場合、そのライブラリと現在のバージョン番号（Kormaプロジェクトのページから取得できる）を`:dependencies`の値に追加するだけだ：

namespace/blottsbooks-2/project.clj
```clojure
(defproject blottsbooks "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [korma "0.4.0"]]
  :main ^:skip-aot blottsbooks.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
```

そしてREPLを再起動する：

```bash
$ lein repl
nREPL server started on port 54569 on host 127.0.0.1 - nrepl://127.0.0.1:54569
...
```

で、Korma名前空間を`require`する：

```clojure
blottsbooks.core=> (require '[korma.db :as db])
nil
```

我々は今、営業中だ。


