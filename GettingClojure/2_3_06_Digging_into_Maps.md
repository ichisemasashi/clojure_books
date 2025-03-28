
### マップを掘り下げる

デストラクチャリングにはさらに良いニュースがある。 マップのデストラクチャリングの考え方は、シーケンシャル型と同じです。 データ構造のテンプレートを提供するのです。テンプレートには、マップの中のさまざまな値に束縛したいシンボルが含まれています。マップのデストラクチャリングがどのように機能するかを理解するために、マップを作るときにキーと値の組み合わせを提供することを考えてみよう：

```clojure
(def artist-map {:painter :monet :novelist :austen})
```

ここでは、`:monet`を`:painter`に、`:austen`を`:novelist`に関連付けると言っている。つまり、このキーとその値を関連付けるということだ。マップのデストラクチャで提供するテンプレートでは、非常に似たことを行う。一連のシンボルとキーを指定します。こんな感じだ：

```clojure
(let [{painter :painter writer :novelist} artist-map]
  (println "The painter is" painter)
  (println "The novelist is" writer))
```

このシンボルには、このキーが対応する：

```
The painter is :monet
The novelist is :austen
```

この最後の例では、キー `:painter` に関連する値を `painter` に、`:novelist` に関連する値を writer にバインドしている。

マップのデストラクチャリングで重要なことは、デストラクチャリング式の左側ではキーが2番目に来るということです；シンボル `painter` の後にキー `:painter` が続きます。また、マップのデストラクチャリングはキーの話なので、シンボルとキーのペアの順番は重要ではないということを覚えておいてほしい。したがって、最後の例を次のように書くこともできる：


```clojure
(let [{writer :novelist painter :painter} artist-map]
  (println "The painter is" painter)
  (println "The novelist is" writer))
```

結果は変わらない。



