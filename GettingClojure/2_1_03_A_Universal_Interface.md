
### 共通のインターフェイス

シーケンスの内容を取得するのは簡単で、なじみがある。先頭の要素を取得するには `first` を呼び出すだけです：

```clojure
;; Returns "Emma".
(first (seq '("Emma" "Oliver Twist" "Robinson Crusoe")))
```

そして、`rest`で、最初の要素以外を取得する：

```clojure
;; Returns the sequence ("Oliver Twist" "Robinson Crusoe")
(rest (seq '("Emma" "Oliver Twist" "Robinson Crusoe")))
```

別の方法として、`next` を使って「最初以外のすべて」のシーケンスを取得することもできる。`next` と `rest` の違いは、空のシーケンスの `rest` が空のシーケンスであるのに対して、空のシーケンスの `next` は `nil` であるということである。ほとんどの場合は rest で問題ないが、たまに `nil` が便利になることがある。

また、シーケンスの先頭に新しい要素を追加するには `cons` を使用する：

```clojure
(cons "Emma" (seq '("Oliver Twist" "Robinson Crusoe")))
```

それで終わりだ。いったんシーケンスができたら、それを使ってできることは、最初の要素を `first` で取得し、他の要素を `next` または `rest` で取得し、新しい要素を `cons` で先頭に追加することだけである。

これだけあれば、 `my-count` を実装するのは簡単だ：

```clojure
(defn my-count [col]
  (let [the-seq (seq col)]
    (loop [n 0 s the-seq]
      (if (seq s)
        (recur (inc n) (rest s))
        n))))
```

まず最初に `my-count` が行うのは、コレクションを（`let` の中で）シーケンスに変換することである。

残りはシーケンスを実行する `loop` で、`rest` を使ってカウントを行う。`my-count`にはベクターやマップや集合のための特別なコードがないことに注意してほしい。シーケンスさえあれば、どのような種類のコレクションを持っているかを気にする必要はない。

`my-count`で行われている唯一の微妙にトリッキーなことは、カウントするアイテムがなくなったときの判断方法である。シーケンスに対してseqを呼び出す。空のシーケンスの `seq` は `nil` なので、`seq` が `nil` を返したら、アイテムがなくなったことがわかる。


> [!NOTE]
>
> **複数のnilに気を付けろ！**
>
> もし `(first s)` が `nil` かどうかをチェックするだけなら `my-count` の方がわかりやすいと思うのであれば、`[nil nil nil]` の中の項目を数えてみてください。


`count` がどのように動作するかを理解するわくわく感もさることながら、このようなシーケンスの話に飛び込むのには非常に実用的な理由がある。`count` や `first` のような関数はシーケンスとの関わりをなんとか穏便に保つことができるが、 `rest`、`next`、`cons` のような関数はそうはいかない。これら3つの関数は（たまに `nil` が返されることを除けば）常にシーケンスを返す：

```clojure
(rest [1 2 3])                         ; A sequence!
(rest {:fname "Jane" :lname "Austen"}) ; Another sequence.
(next {:fname "Jane" :lname "Austen"}) ; Yet another sequence.
(cons 0 [1 2 3])                       ; Still another.
(cons 0 #{1 2 3})                      ; And another.
```

そして、`(rest some-vector)`が丸括弧で表示されるコレクションを返す理由の説明がようやくできた：`rest`関数は常にシーケンスを返す。



