
###Truthy と Falsy

Clojureのブール値の扱いで特筆すべき点は、この言語がどんな値もブール値として扱うことを厭わないことです。例えば、これは完全に正しい `if` です：
 
```clojure
(if 1
  "I like science fiction!"
  "I like mysteries!")
```

これも同じだ：

```clojure
(if "hello"
  "I like science fiction!"
  "I like mysteries!")
```

そしてこれさえもだ：

```clojure
(if [1 2 3]
  "I like science fiction!"
  "I like mysteries!")
```

これらは正しい`if`文であるだけでなく、3つのケースとも勝者はscience fictionである。ルールは単純で、`if`文やその他のブーリアン文脈では、`false`と`nil`だけが偽として扱われる。それ以外はすべて真として扱われる。したがって、この式はミステリー好きを表明することになる：

```clojure
(if false "I like scifi!" "I like mysteries!") ; Mysteries!
```

そしてこれもそうなる：

```clojure
(if nil "I like scifi!" "I like mysteries!") ; Mysteries!
```

`false` と `nil` 以外のすべてのもの、つまりすべてのものが `true` として扱われるので、Clojure はすべての文字列、すべての数値、すべてのキーワードを `true` として扱います。その結果、以下の式はすべて `"yes"` と評価されます：

```clojure
(if 0 "yes" "no")       ; Zero's not nil or false so "yes".
(if 1 "yes" "no")       ; 1 isn't nil or false so "yes".
(if 1.0 "yes" "no")     ; 1.0 isn't false nor nil: "yes".
(if :russ "yes" "no")   ; Keywords aren't false or nil so "yes".
(if "Russ" "yes" "no")  ; "yes" again.
(if "true" "yes" "no")  ; String contents don't matter: "yes".
(if "false" "yes" "no") ; The string "false" isn't false: "yes".
(if "nil" "yes" "no")   ; And the string "nil" ain't nil: "yes".
```

「それ以外は真」というルールはコレクションにも適用される。したがって、どのベクターも `false` と等しくなく、どのベクターも `nil` と等しくないので、空のベクターであってもすべて `true` として扱われます：

```clojure
(if [] (println "An empty vector is true!"))
(if [1 2 3] (println "So is a populated vector!"))

(if {} (println "An empty map is true!"))
(if {:title "Make Room! Make Room!" }
  (println "So is a full map!"))

(if () (println "An empty list is true!"))
(if '(:full :list) (println "So is a full list!"))
```


先ほどの例の`if`式はすべて何らかの出力を生成する。

多かれ少なかれ無限にある真のコレクションと2つの異なる偽のコレクションを持つことの1つの問題は、用語である。何かが真であると言うとき、特定の値 `true` を意味するのでしょうか、それとも `false` や `nil` ではないという意味での真なのでしょうか？混乱を避けるために、Clojuristsはより一般的な意味で真として扱われる値をtruthyと呼ぶことがある。したがって、`true`だけが本当に`true`ですが、`"hello"`、`1.0`、`"Russ"`はすべて真です。同様に、falsy を使って `nil` と `false` が共有する形而上学的な性質を表すことができる。Clojureでは、ちょうど2つのfalsyなもの-`false`と`nil`があり、無限のtruthyなものがある。

> [!NOTE]
>
> **Falsy**
>
> 一部のClojureプログラマは、正しい綴りはfalseyだと信じている。 この信念は間違っている。

