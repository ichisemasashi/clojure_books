
### 次から次へと

簡単な質問からシーケンスの探求を始めよう："count "関数はどのように動作するのだろうか？具体的には、この関数ひとつでベクターの要素、リストの項目、文字列の文字、マップの項目をどうやって数えることができるのだろうか？結局のところ、舞台裏ではリストやベクター、マップや文字列はすべてまったく異なるデータ構造であり、それぞれの要素をカウントしたいのであれば、どこかに構造の違いに対処するコードが必要なのだ。

そのために考えられる設計のひとつは、コレクション・タイプごとに特殊なケース・コードを記述することだ。つまり、独自のカウントを構築する場合、次のように書くことができる：

sequences/examples.clj
```clojure 
;; Is this how count is implemented?
(defn flavor [x]
  (cond
    (list? x) :list
    (vector? x) :vector
    (set? x) :set
    (map? x) :map
    (string? x) :string
    :else :unknown)) 
(defmulti my-count flavor)
(defmethod my-count :list [x] (list-specific-count x))
(defmethod my-count :vector [x] (vector-specific-count x))
;; And so on...
```

別の方法として、`my-count`がコレクションタイプごとに1つずつ、いくつかの汎用ラッパーにアクセスできると想像することもできる。リスト用のラッパー、ベクター用のラッパーなどだ。内部的には、それぞれのラッパーは自分のコレクションタイプをどのように扱うかを知っているが、外部からは異なるラッパータイプが同じ汎用的なインターフェイスを提示することになる。関数 `count` はコレクションを適切なラッパーで包むことから始め、そこからラッパーの統一されたインターフェースで動作することができる。


そして、いよいよ大公開だ： "Clojureはラッパー・メソッドを採用する"。Clojureは、ジェネリック・コレクション・ラッパーを "シーケンス "と呼ぶ。その内部では、コレクション・タイプの数だけシーケンスのフレーバーがありますが、外部の世界では、すべてのシーケンスは非常に均一なインターフェイスを提供します：シーケンスの後ろにベクターやマップやリストやセットがあっても、1つのシーケンスは他のシーケンスとまったく同じように見えます。

> [!NOTE]
>
> **シーケンス・アダプター**
>
> オブジェクト指向プログラミングの経験があれば、シーケンスのラッパーデザインに見覚えがあるかもしれない：これらのシーケンスの背後に隠れているのは、アダプタパターンである。


コレクションをシーケンスでラップする関数もある：その関数は `seq` と呼ばれる。以下はベクターから作られたシーケンスである：

```clojure
(def title-seq (seq ["Emma" "Oliver Twist" "Robinson Crusoe"]))
```

このコードを実行すると、そのベクターのシーケンスを表示することができます。つまり、`title-seq`と表示すると、このようになる：

```clojure
("Emma" "Oliver Twist" "Robinson Crusoe")
```

丸括弧の見せかけに騙されてはいけない。`title-seq`はリストではなく、シーケンス、略して「seq」である。


リストからseqを得ることもできる：

```clojure
(seq '("Emma" "Oliver Twist" "Robinson Crusoe"))
```

もう少し面白いのは、マップに対して `seq` を呼び出すことができることだ：これを実行すると、キーと値のペアのシーケンスができあがる。このように

```clojure
(seq {:title "Emma", :author "Austen", :published 1815})
```

will give you

```clojure
([:title "Emma"] [:author "Austen"] [:published 1815])
```

ほとんど同じです。Clojureはマップのキーの順番を保証しないので、マップのシーケンスの要素が違う順番で出てくる可能性がある。

次のように、シーケンスに対してseqを呼び出すこともできます：

```clojure
;; Calling seq on a sequence is a noop.
(seq (seq ["Red Queen" "The Nightingale" "Uprooted"]))
```

で、全く同じシーケンスを返す。

`seq`のもう少し驚くべき点は、空のコレクションを渡されたときに `nil` を返すことである：


```clojure
(seq [])  ; Gives you nil.
(seq '()) ; Also nil.
(seq {})  ; Nil again.
```

この「空のシーケンスがnilになる」動作は、空のコレクションを検出するための真理値として`(seq collection)`を使えるという意味で便利である。



