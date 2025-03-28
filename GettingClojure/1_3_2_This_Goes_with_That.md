
### これはあれに合う
           
事実上すべてのモダンなプログラミング言語には、任意のキーと同様に任意の値を関連付けることができる何らかのデータ構造が含まれており、Clojureも例外ではない。十分に適切なことに、Clojureはその任意のマッピング・データ構造をマップと呼びます。Clojureの素朴な哲学に従って、マップ・リテラルの構文は、一対の波括弧といくつかのキー/値のペアを必要とするだけです。例えばこうだ：

map/examples.clj
```clojure
{"title" "Oliver Twist" "author" "Dickens" "published" 1838}
```

これは、文字列 `"title"` を `"Oliver Twist"` に、`"author"` を `"Dickens"` に、`"published"` を `1838` に関連付けるマップを作成する。

hash-map関数で新しいマップを作ることもできる：

```clojure
(hash-map "title" "Oliver Twist"
          "author" "Dickens"
          "published" 1838)
```

> [!NOTE]
> 
> **なぜmapではないのか？**
> 
> そう、新しいマップを作る関数の名前は `map` ではなく `hash-map` だ。今後紹介する `map` 関数もあるが、これは違うことをしている。


一旦マップができれば、値を調べる方法は驚くほどたくさんある。 最も簡単なのは `get` 関数を呼び出すことである。例えば、次のようにする：

```clojure
(def book {"title" "Oliver Twist"
           "author" "Dickens"
           "published" 1838})
```

これを使えば、本の出版日を知ることができる：

```clojure
(get book "published") ; Returns 1838.
```

マップから値を取り出す、より一般的な別の方法は、マップ自体を関数のように呼び出し、引数としてキーを与えることである：

```clojure
(book "published")
```

これは、`1838`を返します。また、`(book "title")`は`"Oliver Twist"`を返します。もし`(book "copyright")`のように、そこにないキーに手を伸ばしたら、`nil`が返される。


