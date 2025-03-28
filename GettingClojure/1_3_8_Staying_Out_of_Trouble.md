
### トラブルに巻き込まれないために

マップ・プログラミングにおける他のすべてのものと同様に、セットとキーワードにも落とし穴がある。キーワードの良い点は、非常に単純なので、間違った使い方をするのが難しいということだ。主に、キーワードは文字列ではないということを覚えておく必要がある。例えば、キーワード`:title`は文字列`"title"`とは違う。 したがって、このようなキーワードベースのマップでは

```clojure
(def book 
  {:title "Oliver Twist"
   :author "Dickens"
   :published 1838})
```

こうすると
           
```clojure
(book "title") 
```

実行すると、まったく何も返さない（まあ、`nil`だが）のに対し、こうする：

```clojure
(assoc book "title" "Pride and Prejudice")
```

4エントリーのマップを返す：

```clojure
{:title "Oliver Twist"
 :author "Dickens"
 :published 1838
 "title" "Pride and Prejudice"}
```

また、マップ特有の落とし穴もある。例えば、このように存在しないキーを検索すると、マップもセットも`nil`を返すのは事実である：

```clojure
(book :some-key-that-is-clearly-not-there) ; Gives you nil.
```

キーが存在するかどうかを推測するために、この動作に依存するのは要注意である。

結局のところ、誰かがこれを書いたのかもしれないのだから：

```clojure
(def anonymous-book {:title "The Arabian Nights" :author nil})
```

`anonymous-book`マップは、片方の値が`nil`であっても、2エントリーのマップであることに変わりはない。

あるキーがマップに存在するかどうかを知りたい場合は、`contains?`を使う：

```clojure
(contains? anonymous-book :title) ; True!
(contains? anonymous-book :author) ; Also true!
(contains? anonymous-book :favorite-color) ; False!
```

これとまったく同じロジックと解決法がセットにも適用される。`nil`をメンバに持つセットがあるのではないかと心配な場合は、`contains?`を使ってメンバかどうかをチェックする：

```clojure
;; Our books may be anonymous.
(def possible-authors #{"Austen" "Dickens" nil})

(contains? possible-authors "Austen") ; True!
(contains? possible-authors "King") ; False!
(contains? possible-authors nil) ; True!
```


新しいClojureプログラマが時々つまずくマップのもう1つの特徴(これは本当に特徴です)は、言語がマップをリストやベクターのような普通の値のシーケンスのように扱うことに満足していることです。これについてはシーケンスで詳しく説明しますが、今は `first`、`rest`、`count` などの関数がマップを2要素のベクトルのコレクションとして見ていることを覚えておいてください：

```clojure
(def book {:title "Hard Times"
           :author "Dickens"
           :published 1838})

(first book) ; Might return [:published 1838].
(rest book) ; Maybe ([:title "Hard Times] [:author "Dickens"]).
(count book) ; Will definitely return 3.
```

また、Clojureはマップの順序について約束しないので、 `first` から得られるキーと値のペアがどれであるかは誰にも分からないことに注意してください。1つ頼れることは、任意のマップに対して、 `first` と `rest` の結果は一貫しているということです。

最後に、`(:author book)`や`(:sci-fi genres)`のような表現では、`:author`や`:sci-fi`というキーワードは単に関数のふりをしているのではないということを覚えておいてほしい。これらは関数であり、マップやセットで自分自身を検索する関数なのだ。明らかに関数が必要とされている文脈で`:title`のようなキーワードを目にすることは、初心者にとっては少し混乱するかもしれないが、非常によくあることだ。そのような状況では、マップかセットのどちらかが関係していると思って間違いない。


