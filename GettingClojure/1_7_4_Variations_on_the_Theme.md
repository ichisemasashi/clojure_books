
### テーマのバリエーション

これまで見てきたletのプレーン・バニラ・バージョンに加え、Clojureにはいくつかの便利なバリエーションがパッケージされています。その中で最もよく使われるのは `if-let` でしょう。名前から推測できるように、 `if-let` は `if` と `let` をひとつにしたものだ。`if-let`を実際に使ってみるために、`:author`キーを除いた、お馴染みのブックマップを使って匿名のブックを表現することにしたとしよう：
    
```clojure
(def anonymous-book
  {:title "Sir Gawain and the Green Knight"})
(def with-author
  {:title "Once and Future King" :author "White"})
```

ここで、著者の名前の大文字バージョンを返すか、著者がいない場合は`nil`を返す関数を書く必要があるとしよう。ただし、大文字の`nil`を計算すると例外が発生するため、それを避ける必要がある。それを考えると、次のようになるだろう：

```clojure
(defn uppercase-author [book]
  (let [author (:author book)]
    (if author
      (.toUpperCase author))))
```

これでもいいのだが、`if-let`を使えばもう少し簡潔に表現できる：

```clojure
(defn uppercase-author [book]
  (if-let [author (:author book)]
    (.toUpperCase author)))
```

要するに、`if-let`は1つの束縛を取り、その束縛された値（例では著者の名前）を`if`の条件として使用する。プレーンな `if` と同様に、`if-let` は else の場合のために2つ目の式を取ります：

```clojure
(defn uppercase-author [book]
  (if-let [author (:author book)]
    (.toUpperCase author)
    "ANONYMOUS"))
```

`let-if`と呼ぶ方がわかりやすいと思うのなら、私もそう思う。

当然のことながら、`when-let`もある：


```clojure
(defn uppercase-author [book]
  (when-let [author (:author book)]
    (.toUpperCase author)))
```

`if-let`と`when-let`については、本当に深い意味はない： これらは、人が頻繁に`let`と`if`や`when`を組み合わせるという観察から生まれたものにすぎない。



