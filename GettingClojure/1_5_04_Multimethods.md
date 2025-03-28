
### マルチメソッド 
          
マルチアリティ関数や variadic 関数は、引数の数にあまりこだわらない関数を作りたい場合に便利です。関数に渡される値の別の側面に基づいて、関数の動作を変化させたい場合があります。

例えば、私たちのシステムが様々なソースから様々なフォーマットで書籍データを取得していたとします。いくつかの書籍は、これまで使ってきたマップのように見えます：
  
```clojure
{:title "War and Peace" :author "Tolstoy"}
```

一方、異なるキーのマップもある：

```clojure
{:book "Emma" :by "Austen"}
``` 

また、ベクターで表現されるものもある：

```clojure
["1984" "Orwell"]
```

このような変則的なフォーマットを、`:title`と`author`のキーを持つ標準的なマップに変換する関数を書けば、これらすべてを処理できるのは明らかだ：


```clojure
;; Normalize book data to {:title ? :author ?}
(defn normalize-book [book]
  (if (vector? book)
    {:title (first book) :author (second book)}
    (if (contains? book :title)
      book
      {:title (:book book) :author (:by book)})))
```

このような "ただやる "アプローチには何の問題もないのだが、もし突然、リストからXMLやJSONエンコードされた文字列まで、ありとあらゆる書籍フォーマットを扱わなければならなくなったらどうだろう。私たちの単純な`normalize-book`関数は、非常に醜いものになる可能性が高い。

このような状況に対処する一つの方法は、マルチメソッドを構築することである。複数のアリティを持つ関数と同じように、マルチメソッドでは、1つの関数に複数の実装を持たせることができます。しかし、複数のアリティを持つ関数が引数の数に基づいて実装を選択するのとは異なり、マルチメソッドでは引数の任意の（つまりあらゆる）特徴に基づいて実装を選択することができます。

マルチメソッドを書くことは、問題を分割する練習になります。まず、引数の種類を分類する関数が必要だ。この例では、書籍データの異なる形式を区別できる関数がこれにあたる：


```clojure
(defn dispatch-book-format [book]
  (cond
    (vector? book) :vector-book
    (contains? book :title) :standard-map
    (contains? book :book) :alternative-map))
```

`dispatch-book-format`に本の値を渡すと、`:vector-book`、`:standard-map`、`:alternative-map`のどのフォーマットかを教えてくれる。

次に、関数を使用して引数を分類するマルチメソッドを宣言する：

```clojure
(defmulti normalize-book dispatch-book-format)
```


このコード例では、「ディスパッチ関数」（この場合は`dispatch-book-format`）から返される値に基づいて実装を選択する新しいマルチメソッド（基本的には関数）を定義している。あとは、ディスパッチ関数から返される可能性のある値ごとに実装を定義するだけです。これをdefmethodで行う：

```clojure
(defmethod normalize-book :vector-book [book]
  {:title (first book) :author (second book)})
(defmethod normalize-book :standard-map [book]
  book)
(defmethod normalize-book :alternative-map [book]
  {:title (:book book) :author (:by book)})
```

`normalize-book`という単一の引数を持つ関数で、まずその引数を`dispatch-book-format`に通し、その結果に基づいてどの実装を選ぶかを決める：

```clojure
;; Just returns the same (standard) book map.
(normalize-book {:title "War and Peace" :author "Tolstoy"})
;; Returns {:title "Emma" :author "Austen"}
(normalize-book {:book "Emma" :by "Austen"})
;; Returns {:title "1984" :author "Orwell"}
(normalize-book ["1984" "Orwell"])
```

こうして、私たちは本に調和をもたらすことができる。

> [!NOTE]
>
> **Multi Who?**
>
> 注意深い読者は、`normalize-book`が不正な入力を処理するコードを含んでいないことに気づいただろう。良いニュースは、ディスパッチ関数が対応する `defmethod`にない値を生成した場合、Clojureは例外を生成します。あるいは、`:default`という値のメソッドを提供することで、"他のすべて "のケースをカバーすることができます。


マルチメソッドのいいところは、ディスパッチ関数を書くときに、好きな基準を選べることだ。例えば、アメリカでは、本がいつ出版されたかによって著作権期間が異なります。 もしブックマップに `:published` キーが含まれていれば、出版年に基づいて何をすべきかを決定するマルチメソッドを書くことができる：

```clojure
(defn dispatch-published [book]
  (cond
    (< (:published book) 1928) :public-domain
    (< (:published book) 1978) :old-copyright
    :else :new-copyright))

(defmulti compute-royalties dispatch-published)

(defmethod compute-royalties :public-domain [book] 0)
(defmethod compute-royalties :old-copyright [book]
  ;; Compute royalties based on old copyright law.
  )
(defmethod compute-royalties :new-copyright [book]
  ;; Compute royalties based on new copyright law.
  )
```

ある意味、マルチメソッドは、ほとんどのオブジェクト指向プログラミング言語に見られる、型ベースのポリモーフィズムを一般化したものだ。
どの基準で実装を選ぶかを決めることができるという意味で、マルチメソッドはより一般的です。`dispatch-book-format`の中身を変更すれば、いつでも別の方法で実装を選ぶことができます。あるいは、引数を別の方法で分類する別のマルチメソッドを作ることもできる。


さらに良いことに、1つのマルチメソッドのすべての要素が同じファイルで同時に定義されている必要はない。たとえば、私たちのブックに `:genre` キーが含まれていたとします：

```clojure
(def books [{:title "Pride and Prejudice" :author "Austen" :genre :romance}
            {:title "World War Z" :author "Brooks" :genre :zombie}])
```

ジャンルに応じたマルチメソッドを作ることができるのは言うまでもない：

```clojure
;; Remember you can use keys like :genre like functions on maps.
(defmulti book-description :genre)

(defmethod book-description :romance [book]
  (str "The heart warming new romance by " (:author book)))
(defmethod book-description :zombie [book]
  (str "The heart consuming new zombie adventure by " (:author book)))
```

しかし、ずっと後になって誰かが新しいジャンルを考え出したらどうだろう？

```clojure
(def ppz {:title "Pride and Prejudice and Zombies"
          :author "Grahame-Smith"
          :genre :zombie-romance})
```

問題ない！新しいメソッドを定義するだけです：


```clojure
(defmethod book-description :zombie-romance [book]
  (str "The heart warming and consuming new romance by " (:author book)))
```

言っておくが、このようなマルチメソッドの追加は、オリジナルのものと同じファイルに書いたり、同じプログラマーが書いたりする必要はない。つまり、マルチメソッドはあなたのコードに素晴らしい拡張ポイントを提供してくれるのだ。


