           
### さらに先へ

デストラクチャリングは、便利な機能の1つであるが、常に「もっと欲しい」と思わせるものである。例えば、マップをキーワードのキーでデストラクチャするとき、人々はキーと同じようなシンボルを使いがちである。 例えば、次のようなマップを使っていたとしよう：

```clojure
{:name "Romeo" :age 16 :gender :male}
```

私たちは、`:name` の値を `name` に、`:age` の値を `age` に、`:gender` の値を `gender` にバインドしたくなるだろう：
         
```clojure
(defn character-desc [{name :name age :age gender :gender}]
  (str "Name: " name " age: " age " gender: " gender))
```

これはうまくいくのだが、`name :name age :age gender :gender`を繰り返すので、コードに繰り返しが多くなる。ただ、`name`、`age`、`gender`として、`:name`、`:age`、`:gender`を取り出すことができればいいの です。それは可能だ：

```clojure
(defn character-desc [{:keys [name age gender]}]
  (str "Name: " name " age: " age " gender: " gender))
```

基本的に `:keys` は、キーワード名をローカル名として使用する規約に従うことを示します。シンボルとキーワード `(name :name)` を延々と繰り返す代わりに、抽出したい値を `:keys` に続くベクターに列挙するだけでいい。

さらに良いことに、`:keys`と通常の手作業によるデストラクチャを組み合わせて使うことができる。

```clojure
(defn character-desc [{:keys [name gender] age-in-years :age}]
  (str "Name: " name " age: " age-in-years " gender: " gender))
```

構文にだまされてはいけない。`:keys`は特別な値で、デストラクチャによって「キーと同じシンボル」のベクターを示すために使われる。通常のデストラクチャでは、デストラクチャ式の左辺にキーワード（先頭のコロン）がないため、デストラクチャはこれが特別な値であることを知っています。

デストラクチャのもう1つの欠点は、少なくとも関数で使用する場合、デストラクチャする値を食べてしまうことです。例えば、前のコードの `character-desc` 関数のどこにも、完全な名前・性別・年齢のマップにアクセスすることはできません。`let`を適切に使用すれば、マップとデストラクチャリングを同時に使用することができます：

```clojure
(defn add-greeting [character]
  (let [{:keys [name age]} character]
    (assoc character
           :greeting
           (str "Hello, my name is " name " and I am " age "."))))
```

コード例の `add-greeting` 関数は、渡された `character` マップに新しいキーと値のペアを追加したいが、新しい値は既存の値（`:name` と `:age`）に依存している。従って、デストラクチャされた名前と年齢の両方が必要であるが、そのままの `character` マップ全体も必要である。

幸いなことに、デストラクチャリングは `:as` という便利なショートカットを提供してくれる。

```clojure
(defn add-greeting [{:keys [name age] :as character}]
  (assoc character
         :greeting
         (str "Hello, my name is " name " and I am " age ".")))
```


この`add-greeting`の最後のバージョンでは、追加の`let`を書くことなくマップ全体をピックアップするために`:as`を使っている。

