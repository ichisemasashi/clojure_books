
### Varsを変更する

もしvarsがあなたのコードにグローバルで安定した環境を提供するためのものであるなら、なぜvarsがミュータブルなのか不思議に思うかもしれません。結局のところ、Clojureは不変性を愛しています。 しかし、私たちは `def` と `re-def` を自由に行うことができます。答えは実用的であるのと同じくらい簡単です：ミュータブルなvarsは、より生産的なClojureプログラマのためになります。ほとんどのClojureプログラミングは、REPLなどの何らかの形で行われます。そのため、開発中に、いくつかのvarを作成することから始めることがあります：

```clojure
user=> (def PI 3.14)
#'user/PI
user=> (defn compute-area [diameter]
#_=>     (* PI diameter diameter))
#'user/compute-area
```

そして、もっと正確さが必要だと気づく：

```clojure
user=> (def PI 3.14159)
#'user/PI
```
  
また、計算も間違っていた：
  
```clojure
user=> (defn compute-area [diameter]
#_=>     (* PI radius (/ diameter 2.0)))
#'user/compute-area
```

開発中のコードでは、可変varsは天からの贈り物です。

本番では事情が異なる。実運用プログラムの変数は開発中のものと同じように変更可能ですが、「変更は避けるべきです」。実運用コードでは、`def`（と `defn`）して、そのままにしておくべきです。

> [!NOTE]
>
> **状態を変更するには？**
>
> では、モデル化する必要のある状態があり、その状態が時間とともに変化する場合、プログラマーはどうすればいいのでしょうか？長い答えは、アトムや Ref やエージェントを使うというアドバイスから始まる。もっと短い答えは、「第18章状態（215ページ）」を読むことだ。


まあ、たいていの場合は、varsはそのままにしておくべきです。一時的にvarsの値を変更できると便利な場合があります。例えば、単純なロギング関数を書いたとします：


```clojure
;; First cut at debugging, needs some work.
(def debug-enabled false)

(defn debug [msg]
  (if debug-enabled
    (println msg)))
```

しかし、"関数内でdefを使用しない "というClojureの基本方針に違反することなく、ロギングをオンにするにはどうすればよいでしょうか？

このような状況のために、Clojureは `binding` を用意している。シンボルと値のペアを含むベクターと、束縛の本体を構成する1つ以上の式を `binding` に与えます。`binding`式は与えられた式を評価しながら、シンボルに対応するバーを一時的に与えられた値で設定します：


```clojure
(binding [debug-enabled true]
  (debug "Calling that darned function")
  (some-troublesome-function-that-needs-logging)
  (debug "Back from that darned function"))
```


この例では、 `debug` と `some-troublesome-function` の呼び出しが評価される間に `debug-enabled` が true に設定される。これにより、実際に `debug` 関数からの出力を見ることができる。 `some-troublesome-function` によって呼び出された関数も、一時的に `debug-enabled` の値を見ることになる。

`binding` にはもう一つ厄介な点がある： `binding` で使用する var は、次のように "dynamic" として宣言する必要がある：

```clojure
;; Make debug-enabled a dynamic var.
(def ^:dynamic debug-enabled false)
```


今のところ、`^:dynamic`は`debug-enabled` varにメタデータ(これについては "第19章 読み取りと評価(229ページ) "で説明します)を追加します。 今は、`binding`を使えるようにするために必要な呪文として受け入れてください。最後に、動的バーの命名にはClojureの慣例があります。その規約とは、動的なバーは \* で始まり、 \* で終わるというものです。というわけで、デバッグの最後の練習は次のようになります：

```clojure
(def ^:dynamic *debug-enabled* false)

(defn debug [msg]
  (if *debug-enabled*
    (println msg)))

(binding [*debug-enabled* true]
  (debug "Calling that darned function")
  (some-troublesome-function-that-needs-logging)
  (debug "Back from that darned function"))
```


動的なバーをアスタリスクで囲むことで、どのバーが `binding` の中で使用可能で、どのバーが使用不可能なのかが一目でわかるようになります。動的であることは少しオーバーヘッドを伴うので、`^:dynamic`タグは本当に必要なvarにのみ付けるべきであることに注意してください。


