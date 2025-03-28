
### 野生の中で
                     
レコードとプロトコルの感覚をつかむ最良の方法は、実際のコードで動作しているのを見ることだ。
                       
Clostacheは、事実上すべてのウェブ・アプリケーションが実行する必要がある「HTMLでこの値を代入する」タスクの苦痛のほとんどを取り除いてくれる。Clostacheを動作させるには、テンプレート文字列と値のマップが必要です：

```clojure
(require 'clostache.parser) 
(def template "The book {{title}} is by {{author}}")
(def values {:title "War and Peace" :author "Tolstoy"})
```

そして、Clostache の `render` 関数を使用して、テンプレートに値を入力することができます：

```clojure
;; Gives you "The book War and Peace is by Tolstoy"
(clostache.parser/render template values)
```   
      
Clostacheのより強力な機能の一つは、セクションを使って条件付きで出力の一部を含めたり省略したりできることです。以下はセクションを持つテンプレートです：

```clojure
(def data {:author "Tolstoy" :show-author true})
(def section-templ "{{#show-author}} by {{author}} {{/show-author}}")
```

ここで、`{#show-author}}`と`{/show-author}}`はセクションを定義します。セクションのテキストは、セクションの値 - この場合は ``show-author`` が真である場合にのみレンダリングされます。

Clostacheのソースコードを調べると、セクションがレコード値で表現されていることがわかります：

```clojure
(defrecord Section [name body start end inverted])
```

`Section`のキーとなるフィールドは `name` で、これはコントロールする値（例では `show-author`）の名前であり、`body` はセクションのテキストである。

もう少し見てみると、`Section`の値が実際に使われているのがわかるだろう：

```clojure
(defn- render-section
  [section data partials]
  (let [section-data ((keyword (:name section)) data)]
    (if (:inverted section)
      (if (or (and (seqable? section-data)
                   (empty? section-data))
              (not section-data))
          (:body section))
    ; Lots of code omitted
    )))
```

`render-section`が `Section` のインスタンスである `section` の値を、 `:name`、`:body`、`:inverted` のキーを持つ普通のマップのように扱っていることに注目してほしい。これは最も基本的なレコード使用法である。Clostacheはプロトコルもポリモーフィズムも2番目のレコード型さえも持たない。セクションのレコード型は、コードを少しわかりやすくするためにあるだけです。

[Stuart SierraのComponentライブラリ](https://github.com/stuartsierra/component)にプロトコルの素晴らしい例があります。 Componentは、実世界のアプリケーションを構築する手助けをします。アプリケーションは、起動時（データベースへの接続やデータの初期化など）やシャットダウン時（データベース接続のクローズなど）に頻繁に何かをする必要があります。

Componentライブラリは`Lifecycle`と呼ばれるプロトコルを中心に構築されている。以下に、非常にシンプルなプロトコルを紹介する：

```clojure
(defprotocol Lifecycle
  (start [component]
    "Begins operation of this component. Synchronous, does not return
    until the component is started. Returns an updated version of this
    component.")
  (stop [component]
    "Ceases operation of this component. Synchronous, does not return
    until the component is stopped. Returns an updated version of this
    component."))
```

`start`と`stop`の2つのメソッドだけで、`Lifecycle`は些細なものに見えるかもしれないが、基本的なコンポーネントや他の小さなコンポーネントを組み合わせた複合コンポーネントなどを構築するための接着剤を提供する。

時には、プロトコルのその場限りの実装を作る必要があるかもしれない。 プロトコルの実装を必要とする関数を使ったりテストしたりしているときに、適切なレコードが手元にないことがあります。例えば、Componentシステムをテストしようとしていて、 `Lifecycle`の実装が必要かもしれません。このような場合、Clojureは `reify` を提供してくれます。これは、プロトコル名といくつかのメソッド実装を受け取り、そのプロトコルのその場限りの実装を作成します：

```clojure
(def test-component (reify Lifecycle
                     (start [this]
                       (println "Start!")
                       this)
                     (stop [this]
                       (println "Stop!")
                       this)))
```


先ほどのコードを実行すると、 `test-component` に `Lifecycle` を実装した値が格納されます。さらに良いのは（特にテスト目的では）、`reify`はプロトコル全体を実装する必要がないということだ：

```clojure
;; A partial implementation of Lifecycle. We can call start, but
;; we will get an exception if we call stop.
(def dont-stop
  (reify Lifecycle
    (start [this]
      (println "Start!")
      this)))
```

先のコードで定義したように、`dont-stop`は`Lifecycle`のインスタンスになるが、stopメソッドを呼び出そうとすると例外を投げるインスタンスになる。


