
### 前条件と後条件

関数は便利なコード単位であるだけでなく、関数に渡された値が期待したものであるかどうかをチェックすることで、コードの信頼性を向上させることができる、自然なチェックポイントを提供します。例えば、本を出版する関数があり、出版する前に本のタイトルを確認したい場合、次のように書きます：

```clojure
;; Publish a book using the (unseen) print-book
;; and ship-book functions.
(defn publish-book [book]
  (when-not (contains? book :title)
    (throw (ex-info "Books must contain :title" {:book book})))
  (print-book book)
  (ship-book book)) 
```

幸いなことに、Clojureは`:pre`条件という形でこの種のショートカットを提供している：

```clojure
(defn publish-book [book]
  {:pre [(:title book)]}
  (print-book book)
  (ship-book book))
```

`:pre` 条件を設定するには、引数の後に `:pre` キーを持つマップを追加する。値はベクターの式でなければならない。関数が呼び出されたときに、式のどれかが不正であることが判明した場合、実行時例外が発生します。したがって、本の著者とタイトルの両方を確実に取得したい場合は、次のように書くことができる：


```clojure
(defn publish-book [book]
  {:pre [(:title book) (:author book)]}
  (print-book book)
  (ship-book book))
```

さらに一歩進んで、`:post`という条件を指定することで、関数から返される値をチェックすることもできる。例えば、次のように書くことができる。

```clojure
(defn publish-book [book]
  {:pre [(:title book) (:author book)]
   :post [(boolean? %)]}
  (print-book book)
  (ship-book book))
```

戻り値（最終的には `ship-book` から返される）がブール値であることを確認するためである。なお、`:post` の条件では、戻り値の代わりに `%` を使っている。

