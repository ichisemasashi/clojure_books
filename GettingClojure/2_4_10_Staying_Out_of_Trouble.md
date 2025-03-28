
### トラブルに巻き込まれないために
  
レコードで犯しやすいミスといえば、フィールドに値を入力しようとして失敗することだろう。このコードはもっともらしく見えるかもしれないが、ナンセンスだ：

```clojure
(map->FictionalCharacter {:full-name "Elizabeth Bennet"
                          :book "Pride & Prejudice"
                          :written-by "Austen"})
```

問題は、`FictionalCharacter`のフィールドは`name`、`appears-in`、`author`という名前であり、`full-name`、`book`、`written-by`という名前ではないということだ。前のコードの式は6フィールドのレコードになり、3つの組み込みフィールドが`nil`に設定され、3つの追加フィールドが補完される。
 
また、`assoc`の使い方を間違えると、同じような不幸な結末を迎えることになる：

```clojure
(assoc elizabeth :book "Pride & Prejudice")
```

もう1つ注意しなければならないのは、プロトコルは見た目以上に名前空間を占有するということです。これまで見てきたように、`(defprotocol Person...)`を評価すると、シンボル `Person` がプロトコル定義に束縛される。これは `defprotocol` の `def` の部分で暗示されている。プロトコルを定義するとき、プロトコルのメソッドごとに関数を定義することになります。つまり、メソッドの名前には注意が必要です。例えば、私たちの `Person` プロトコルで `full-name` を使う代わりに、次のような名前にしていたとする：

```clojure 
(defprotocol CollidingPerson 
  (name [this])
  (greeting [this msg])
  (description [this]))
```


Clojureから次のような警告が表示されるはずだ：

```
Warning: protocol #'user/CollidingPerson is overwriting function name
```

問題は、`defprotocol`が`name`という関数を定義しようとしているが、`name`はすでにClojureに付属している組み込み関数であるということだ。どの瞬間にどの `name` を使っているかを知っている限り、これは必ずしも致命的な問題ではない。

同じように、競合するプロトコルにも気をつける必要がある：

```clojure
(defprotocol Person
  (full-name [this])
  (greeting [this msg])
  (description [this]))
(defprotocol Product
  (inventory-name [this])
  (description [this]))
```

この場合も、`Product` の `description` が `Person` の `description` を上書きするため、警告が表示されます：

```
Warning: protocol #'user/Product is overwriting method
description of protocol Person
```

このようなプロトコル対プロトコルの名前の衝突を解決する方法は簡単で、迷ったらそれぞれのプロトコルを独自の名前空間に置くことである。


最後に、レコードにはより一般的な型があることに注意する必要がある。 `defrecord`で新しいレコード型を定義するのと同じように、 `deftype`で新しい型(あるいは型タイプ？)を定義できる。レコードがマップのような機能を持っているのに対して、型はより白紙の状態である。型を定義するとき、新しい型のインスタンスのすべての振る舞いを定義するのはあなた自身です。その分作業は増えるが、完全にコントロールできるということでもある。型は言語機能のひとつであり、使うことはないかもしれないが、必要に迫られたときには利用できる。


