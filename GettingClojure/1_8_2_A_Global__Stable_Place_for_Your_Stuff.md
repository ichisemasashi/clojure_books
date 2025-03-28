

### データのグローバルで安定した場所

第1章で学んだように、`def`はプログラミング言語の機能と同じくらいシンプルだ。シンボルと値を渡すだけだ：

def/examples.clj
```clojure
(def title "Emma")
```

そして、シンボルと値を束縛する。また、ローカルで一時的な `let` とは対照的に、長続きする、より安定した名前と値のバインディングには `def` を使うことも見てきた。「安定」の部分から始めましょう：ルールは、`def`で作成された束縛は、あなたがそれを変更するか、プログラムが終了するかのどちらかまで、いつまでも残るということです。このため、defは定数に最適です：

```clojure
;; Everyone's favorite universal constant.
(def PI 3.14)
;; Length of a standard book ID.
(def ISBN-LENGTH 13)
;; Company names are more or less constant.
(def COMPANY-NAME "Blotts Books")
```

`def` の束縛は非常に長持ちするので、プログラムの各パーツを機能的な全体としてまとめるのに最適なツールである。`defn`はその名の通り、`def`と`fn`を機能的に組み合わせたものである：

```clojure
(defn book-description [book]
  (str (:title book)
       " Written by "
       (:author book)))
```

というのは、次のように書くより便利な方法だ：

```clojure
(def book-description
  (fn [book]
    (str (:title book)
         " Written by "
         (:author book))))
```

`def`が提供するもうひとつの利点は、作成された束縛が広く可視化されることである。一度定義すれば、このように

```clojure
;; Length of a standard book ID.
(def ISBN-LENGTH 13)
;; Before 2007 ISBNs were 10 characters long.
(def OLD-ISBN-LENGTH 10)
```

`def `で生成された束縛を他の`def`で使うことができる：

```clojure
(def isbn-lengths [OLD-ISBN-LENGTH ISBN-LENGTH])
```

および関数の内部で使用される：

```clojure
(defn valid-isbn [isbn]
  (or (= (count isbn) OLD-ISBN-LENGTH)
      (= (count isbn) ISBN-LENGTH)))
```

ルールとしては、一旦`def`でシンボルを値に束縛したら、それはただそこにあるだけで、環境の一部である。

