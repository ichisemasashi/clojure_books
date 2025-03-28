
### 分散型のポリモーフィズム

プロトコルの1つの興味深くて便利な側面は、後から定義して実装できることです。例えば、あなたがClojureアプリケーションに取り組んでいて、6ヶ月前に同僚の1人が `Employee`、`FictionalCharacter`、`SuperComputer` レコードを実装したとします。そして今、私たちが気にする必要のない理由で、あなたはこれらのレコードタイプのそれぞれのインスタンスに対して肯定的なマーケティングスローガンを作成する仕事を渡されたところです。そこで、新しいプロトコルを定義することから始める：

```clojure
(defprotocol Marketable
  (make-slogan [this]))
```

そして、`Employee`、`FictionalCharacter`、`SuperComputer defrecords`に戻って、新しいプロトコルをサポートするように修正する。まあ、そうすることもできるが、そうする必要はない。Clojureの `extend-protocol` を使えば、それらの型定義とは別に、既存の型に新しいプロトコルを実装することができます：

```clojure
(extend-protocol Marketable
  Employee
    (make-slogan [e] (str (:first-name e) " is the BEST employee!"))
  FictionalCharacter
    (make-slogan [fc] (str (:name fc) " is the GREATEST character!"))
  SuperComputer
    (make-slogan [sc] (str "This computer has " (:no-cpus sc) " CPUs!")))
```

`extend-protocol`が一種の裏返しの`defrecord`であることに注目してほしい。それは一つのプロトコルから始まり、そのプロトコルの実装を列挙する。`extend-protocol`を使えば、従業員や架空のキャラクター、スーパーコンピュータに対して `make-slogan` を呼び出すことができる。実際、プロトコルを拡張してレコード以外のデータ型を受け入れることもできる：

```clojure
(extend-protocol Marketable
  String
    (make-slogan [s] (str \" s \" " is a string! WOW!"))
  Boolean
    (make-slogan [b] (str b " is one of the two surviving Booleans!")))
```

つまり、プロトコルとレコードは同じくらい相互に柔軟であるということだ。必要なときに新しいプロトコルを作ることができ、`extend-protocol`を使えば、その型の元の定義に触れることなく、既存の型に新しいプロトコルを実装することができる。


