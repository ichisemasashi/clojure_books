
### 野生の中で

そして、今、私たちはこの章の冒頭の質問に対する答えを持っています: Clojureを関数型プログラミング言語にするものは、関数を書くことによって基本的なことを行い、関数を値として扱うことによってより洗練されたことを行うことです、その値は渡したり、呼び出したり、組み合わせたりすることができます。

"関数は値である"という考えを最もよく表しているのは、おそらくdefnそのものの中にある。`defn`は`def`と`fn`の上の薄いレイヤーに過ぎない。`defn`で新しい関数を定義すると、おそらくこのようになる：

```clojure
(defn say-welcome [what]
  (println "Welcome to" what "!"))
```   
      
評価されるのは次のようなものだ：

```clojure
(def say-welcome
  (fn [what] (println "Welcome to" what "!")))
```

その名の通り、`defn`は`def`に`fn`を足したものだ。

この考え方に慣れていないと、関数型値は少し特殊で魔法のようで、極端な状況でのみ使うようなテクニックに思えるかもしれません。 そうではありません。Clojureでは、関数値は日常的なプログラミングの一部です。

例えば、平凡な `update` 関数を考えてみましょう。その名前が示すように、`update` を使って値、特にマップ内の値を変更します。

> [!NOTE]
> 
> **マップは変更できません**。
> 
> 正確には、`update`は入力マップによく似た、ちょっと違うだけの新しいマップを生成する。しかし、あなたがそれを読むのに疲れているように、私もそれを書くのに疲れてきている。


だから、ある本がもう一冊売れたことを記録したい場合、こう書くかもしれない：

```clojure
;; Start with 1,000 copies sold.
(def book {:title "Emma" :copies 1000})
;; Now we have 1,001.
(def new-book (update book :copies inc))
```

見てわかるように、`update`は3つのパラメータを取る：マップと、値を更新したいキーと、更新を行う "関数"だ。関数はキーの古い値（この場合は `1000`）で呼び出され、返されるマップは古いマップとほぼ同じになるのだが、キーには関数を評価した結果が格納される。


もしマップが入れ子になっている場合は、少し地味な `update-in` 関数を使うことができる。この関数は `update` と似たような働きをするが、キーのベクターを利用してマップを何階層も掘り下げることができる：

```clojure
(def by-author
  {:name "Jane Austen"
   :book {:title "Emma" :copies 1000}})
(def new-by-author (update-in by-author [:book :copies] inc))
```

しかし、関数値でどれだけのことができるかを知るには、Webアプリケーションを構築するのに役立つ人気のClojureライブラリである[Ring](https://github.com/ring-clojure/ring)ほどふさわしいものはありません。

Ringを使ってWebアプリケーションを構築するには、まずRingを読み込むために適切な呪文を唱える必要があります（`require`については "第9章 名前空間 (95ページ) "で説明します）：


```clojure
(ns ring-example.core
  (:require [ring.adapter.jetty :as jetty]))
```

そして、次のように、HTTPリクエストをマップの形で受け取り、同じくマップの形でレスポンスを返す関数を作る：

```clojure
(defn handler [request]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body "Hello from your web application!"})
```

関数を書いたら、今度は "this" が Web リクエストが来たときに Ring が参照すべき関数であることを Ring に伝える必要があります。Ringの `run-jetty` 関数に `handler` 関数を渡すことで、Jettyというシンプルなウェブサーバを起動させることができます：

```clojure
(defn -main []
  (jetty/run-jetty handler {:port 8080}))
```

これで、あなたのハンドラ関数は8080番ポートからのリクエストに対して呼び出されるようになる。

リング・アプリケーションでは、単なるハンドラー以外に、一般的に「ミドルウェア」を使用する。 ミドルウェアとは、ハンドラ関数をパラメータとして受け取り、新しいハンドラ関数を返す関数のことです。Ringのプログラマは、ミドルウェアを使ってハンドラに追加機能をレイヤーします。例えば、レスポンスをログに記録するミドルウェア関数を定義します：

```clojure
(defn log-value
  "Log the message and the value. Returns the value."
  [msg value]
  (println msg value)
  value)
(defn wrap-logging
  "Return a function that logs the response."
  [msg handler]
  (fn [request]
    (log-value msg (handler request))))
```

そして、コンテンツ・タイプを指定する2番目のハンドラです：

```clojure
(defn wrap-content-type
  "Return a function that sets the response content type."
  [handler content-type]
  (fn [request]
    (assoc-in
      (handler request)
      [:headers "Content-Type"]
      content-type)))
```

ミドルウェア関数はハンドラー（関数）を受け取り、別のハンドラーを返す。新しいハンドラは通常、古いハンドラを実行し、途中で独自の機能を追加します。最初のミドルウェア関数 `wrap-logging` は、渡されたハンドラ関数を実行し、レスポンスを表示し、そのレスポンスを返します。2番目のミドルウェア関数はもっと面白いことをします：レスポンスに（コンテントタイプの）ヘッダーを追加します。

> [!NOTE]
>
>**Assoc-inとは?**
>
> この例のcontent-typeハンドラでは、`assoc-in`という関数が使われていることにお気づきだろうか。この関数は、マップに新しいキーと値の関連付けを追加するという点では `assoc` とよく似ている。違うのは、`assoc-in`にキーのベクターを渡すと、何階層ものマップを探検してくれることだ。別の言い方をすれば、`update-in` が `update` のマルチストーリーバージョンであるのと同じように、`assoc-in` は `assoc` のマルチストーリーバージョンである。


伝統的にRingアプリケーションは、完全にラップされた最後のハンドラを application を略して `app` と呼んでいます。つまり、これが最後の `app` をセットアップし、Ring を立ち上げる方法だ：

```clojure
(defn handler [request]
  {:status 200
   :body "Hello from your web application!"})
(def app
  (wrap-logging
    "Final response:"
    (wrap-content-type handler "text/html")))
```


先の例では、最終的なレスポンス、つまり`wrap-content-type`が処理を終えた後のレスポンスを記録していることに注目すれば、「値を関数とする」世界観の威力を感じることができるだろう。しかし、少しアレンジすれば、content-typeが追加される前のレスポンスを記録することができる：

```clojure
(def app
  (wrap-content-type
    (wrap-logging "Initial response:" handler)
    "text/html"))
```

あるいは両方を記録することもできる：


```clojure
(def app
  (wrap-logging
    "Final response:"
    (wrap-content-type
      (wrap-logging "Initial response:" handler)
      "text/html")))
```

この最後のコードは、関数型プログラミングのパワーを示す好例である。 これは、4つの別々の関数（そのうちの3つは動的に生成される）を、部分の和よりも大きな全体として動作するように組み立てている。



