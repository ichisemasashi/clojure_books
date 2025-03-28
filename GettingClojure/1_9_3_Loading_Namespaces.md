
### 名前空間の読み込み

名前空間は概念的には単純で、特に REPL で作業している場合は簡単ですが、`.clj` ファイルに格納されている名前空間を使用したい場合、物事は少し複雑になります。それは、Clojureが `.clj` ファイルを読み込むことを知っていて、使用できる名前空間にコンパイルできることを確認する必要があることです。要するに、使いたい名前空間が "load "されていることを確認してから使う必要があります。

例えば、Clojureのインストールには`clojure.data`という既製の名前空間が装備されています。`clojure.data`名前空間（ピリオドは名前の一部です）には `diff` という便利な関数があり、2つのデータ構造（おそらくベクター）を比較して、どの値が1つ目にのみあり、どの値が2つ目にのみあり、どの値が両方にあるかを教えてくれます。

つまり、本のタイトルのベクターがいくつかあったとする：

```clojure
user=> (def literature ["Emma" "Oliver Twist" "Possession"])
#'user/literature
user=> (def horror ["It" "Carry" "Possession"])
#'user/horror
```

で、それらを比較したい場合、`clojure.data/diff`を使えばいいかもしれません。悲しいことに、がっかりするでしょう：

```clojure
user=> (clojure.data/diff literature horror)
ClassNotFoundException clojure.data
  java.net.URLClassLoader.findClass (URLClassLoader.java:381)
```

問題は、`clojure.data`はClojureのインストールに同梱されていますが、デフォルトではロードされないことです。clojure.data`を使用するには(これはClojureのキャリアで使用するほとんどの名前空間に当てはまります)、名前空間の背後にあるコードを読み込んでコンパイルするようにClojureに指示する必要があります。


幸いなことに、それは難しいことではありません。必要なのは `require` だけです：

```clojure
user=> (require 'clojure.data)
user=> (clojure.data/diff literature horror)
[["Emma" "Oliver Twist"] ["It" "Carrie"] [nil nil "Possession"]]
```

例からわかるように、`require` は名前空間の名前を取り（引用符で囲む必要がある）、その名前空間の背後にあるコードをロードする。

そして、一度 `require` したら、名前空間の内容を使い始めることができる。


