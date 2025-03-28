
### プロトコル
 
プロトコールという単語は、非技術的な用語の1つで、元々は大使がナブーから訪問してきたり、似たような状況に対処するための一連のルールを意味していました。Clojureにおけるプロトコルの意味は、元の外交的な意味に近い。Clojureプロトコルは、コードで表現された振る舞い方のルールのセットです。

プロトコルがどのように機能するかを見るために、`FictionalCharacter`と一緒に、会社の従業員を追跡するために使われる2つ目のレコード型があったと想像してみよう：

```clojure
(defrecord Employee [first-name last-name department])
```

ここでは特に目新しいことはない： `Employee`は3つのフィールドを持つレコード型である。 この新しい定義を使って、`Employee`インスタンスを作成することができる：

```clojure 
(def alice (->Employee "Alice" "Smith" "Engineering"))
```

ここで、理解しがたい理由だが、同じプログラムの中に`Employee`と`FictionalCharacter`のインスタンスがあるとしよう。明らかに、従業員と架空のキャラクターには共通点がある。どちらも名前を持っていて、ある意味どこかの国の人なのだ。便利なときにいつでもこの2種類のレコードを同じものとして扱えるメカニズムがあれば最高だ。

プロトコルの登場だ。以下は、従業員と架空の人物の共通点を活用するためのプロトコル定義である：

```clojure
(defprotocol Person
  (full-name [this])
  (greeting [this msg])
  (description [this]))
```

このコードはいろいろなことをする。まず、最も明らかなことは、`Person`という新しいプロトコルを作成することである。あまり明らかではありませんが、このコード例の式は `full-name`、`greeting`、`description` という3つの新しい関数も作っています。プロトコルで生成される関数の特別な点は、それらが多相的であるということである。何をするかは最初の引数の型に依存する。

現時点では、`full-name greeting`関数と`description`関数はまったく何もしません。そのためには、`FictionalCharacter` と `Employee` の定義を再構築して `Person` を実装する必要がある：

```clojure
(defrecord FictionalCharacter[name appears-in author]
  Person
  (full-name [this] (:name this))
  (greeting [this msg] (str msg " " (:name this)))
  (description [this]
    (str (:name this) " is a character in " (:appears-in this))))

(defrecord Employee [first-name last-name department]
  Person
  (full-name [this] (str first-name " " last-name))
  (greeting [this msg] (str msg " " (:first-name this)))
  (description [this]
    (str (:first-name this) " works in " (:department this))))
```

プロトコルの名前が各レコード定義の最後にくっ付いていて、その後に `full-name`、`greeting`、`description`の実装が続いていることに注目してほしい。 適切な専門用語を使うなら、これらのメソッド定義は `defns` から `defn` を抜いたようなもので、メソッド名の後にパラメータが続き、その後にメソッド本体が続きます。各メソッドには少なくとも1つのパラメータが必要で、これは操作対象のレコードであり、慣例的に`this`と呼ばれている。もし `this` パラメータが複数ある場合は、先ほどの例の `greeting` メソッドのように、 `this` を最初のパラメータにしなければなりません。


したがって、この例の新しい `FictionalCharacter` 定義では、レコードの `:name` フィールドを返すことで `full-name` メソッドを実装しています: `(:name this)`. `description`と`greeting`の実装はもう少し複雑ですが、それほど大きくはありません。キャラクターの名前と、そのキャラクターが登場する架空の作品に基づいて、適切な文字列を組み立てるだけです。

`FictionalCharacter`と `Employee` が `Person` プロトコルを実装したら、インスタンスを生成し始めることができる：

```clojure
(def sofia (->Employee "Sofia" "Diego" "Finance"))
(def sam (->FictionalCharacter "Sam Weller" "The Pickwick Papers" "Dickens"))
```

そして、新しい多相関数を利用することができる：

```clojure
;; Call the full-name method. Returns "Sofia Diego".
(full-name sofia)
;; Call description.
;; Returns "Sam Weller is a character..."
(description sam)
;; Returns "Hello! Sofia"
(greeting sofia "Hello!")
```

最後に、`FictionalCharacter` の `author` フィールドは `Person` プロトコルには含まれないので、どのメソッド定義にも表示されないことに注意してください。しかし、 author はレコードの中に存在し、それを取得することができますので、`(:author sam)` は期待通りの結果を与えてくれます。


