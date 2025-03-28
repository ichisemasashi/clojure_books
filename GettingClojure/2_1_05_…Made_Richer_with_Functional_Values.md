
### …関数の値でよりリッチに

`partition` や `interpose` のような関数は便利だが、「関数は値である」という考え方が混ざって初めてシーケンスの本当の力が発揮される。その好例が `filter` 関数である。`filter`に述語とコレクションを渡すと、元のシーケンスから述語に一致するアイテムだけを取り出した新しいシーケンスが返されます。述語はもちろん関数として表現される。従って、リストに含まれる全ての負の数が必要な場合、`filter`と`neg?`を組み合わせることができる：

```clojure 
;; Returns the sequence (-22 -99 -77)
(filter neg? '(1 -22 3 -99 4 5 6 -77))
```
 
もっと現実的な話だが、もしこのマップのベクターがあったとしたら......：

```clojure
(def books
  [{:title "Deep Six" :price 13.99 :genre :sci-fi :rating 6}
   {:title "Dracula" :price 1.99 :genre :horror :rating 7}
   {:title "Emma" :price 7.99 :genre :comedy :rating 9}
   {:title "2001" :price 10.50 :genre :sci-fi :rating 5}])
```

古くからの相棒である`cheap?`関数を使うことができる：

```clojure 
(defn cheap? [book]
  (when (<= (:price book) 9.99)
    book))
```

安価な本を見つけるために：

```clojure
(filter cheap? books)
```

結果は以下の通りである：

```clojure
({:genre :horror, :title "Dracula", :price 1.99 :rating 7}
 {:genre :comedy, :title "Emma", :price 7.99 :rating 9})
```

`filter` と似ているものに `some` がある。`filter` と同様に、 `some` は述語と seqable コレクションを受け取る。 そして、 `filter` と同様に、 `some` は述語が真の値を返すアイテムを探しに行く。違いは、 `some` は最初にアイテムを見つけた時点で終了し、述語の値を返すことである。つまり、 `filter` が常に（空の可能性のある）シーケンスを返すのに対して、 `some` は述語関数から真の値を最初に返すか、何も見つからなければ `nil` を返します。私たちの述語関数 `cheap?` は本か `nil` のどちらかを返すので、次のように書きます：

```clojure
(some cheap? books)
```

あなたは最初の安い本、`{:genre :horror, :title "Dracula", :price 1.99}` を得ることになる。さらに良いことに、この例では本か `nil` のどちらかを返すので、その返り値は真理論理のルールに従います。つまり、`if`の中で使えるということだ：

```clojure
(if (some cheap? books)
  (println "We have cheap books for sale!"))
```

`some` というのは、"このテストに合格する項目はあるのか？"という質問を投げかけていると考えることができる。


