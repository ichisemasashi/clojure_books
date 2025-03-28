
### セット

マップと並んで、Clojureには組み込みのセット・データ型もあります。セットのリテラル構文は、マップから波括弧を借用しますが、先頭に `#` を追加します：

```clojure
(def genres #{:sci-fi :romance :mystery})
(def authors #{"Dickens" "Austen" "King"})
```

数学の名前と同じように、Clojureのセットはメンバーシップに関するものです。値がセットのメンバーであるか、そうでないかです。値がセットに入るのは一度だけなので、セット・リテラルで値を繰り返すとエラーになります。したがって、このようになります：

```clojure
#{"Dickens" "Austen" "Dickens"}
```

は " Dickens " が多すぎる：

```
IllegalArgumentException Duplicate key: Dickens...
```

マップと同じように、セットにも要素の順序に関する独自の考え方がある。あなたが`#{:sci-fi :romance :mystery}` と書いたセットは、`#{:sci-fi :mystery :romance}` と戻ってくる可能性がある。

セットはメンバーシップに関するものなので、セットでできる主なことは、この値がセットに入っているかどうかを調べることです。関数 `contains?` はセットのメンバーシップをチェックすることができ、`true` か `false` のどちらかを返します：

```clojure
(contains? authors "Austen") ; => true
(contains? genres "Austen") ; => false
```

セットを関数のように使うこともでき、その場合は値か`nil`のどちらかを返す：

```clojure
(authors "Austen") ; => "Austen"
(genres :historical) ; => nil
```

セットの中でたまたまキーワードを探している場合は、物事を切り替えてキーワードを関数として使うことができる：

```clojure
(:sci-fi genres) ; => :sci-fi
(:historical genres) ; => nil
```

古くからの友人である `conj` を使えば、既存のセットからより大きなセットを作ることができる：

```clojure
;; A four element set.
(def more-authors (conj authors "Clarke"))
```

ある値を2回目にセットへ `conj` するのはエラーではない：

```clojure
(conj more-authors "Clarke")
```

しかし、1つの値がセットに入るのは1回だけなので、ちょっともったいない。

最後に、`disj`で要素を削除することができる：


```clojure
;; A set without "King".
(disj more-authors "King")
```

この文脈では、削除という言葉は、2つ目の小さなセットを作ることを意味する。


