
### トラブルに巻き込まれないために

ネームスペースについて、ひどく複雑なことは何もない。各ネームスペースは、それ自体が名前を持つ大きな名前/値の参照テーブルにすぎません。名前空間に階層はないことを覚えておいてください。各名前空間は名前を持っていて、舞台裏のどこかでClojureは名前空間名、おそらく`clojure.core`、またはおそらく`clojure.core.data`と実際の名前空間の間のマッピングを維持します。しかし、Clojureに関する限り、`clojure.core.data`と`clojure.core`の間には、`clojure.core`と`blottsbooks.pricing`の間の関係以上の関係はありません。特に、Clojure は `clojure.core.data` を `clojure.core` の下にあるものとして見ません。名前空間を好きなように呼ぶことができ、`clojure.core`や`clojure.core.data`のような名前は、私たち人間がコードを理解するのに役立ちます。しかし、Clojureに関する限り、名前は任意です。

名前空間を読み込むのは同じように簡単で、特に実運用では、名前空間を準備して、おそらく他の名前空間で `require` して、そのままにしておきます。開発環境では少し複雑になることがあります。ある名前空間で `require` して、それを修正して、また `require` して......ということを頻繁に繰り返します。

例えば、REPLで`blotts-books.pricing`ネームスペースを試しているとします。

```clojure
blottsbooks.core=> (require '[blottsbooks.pricing :as pricing])
nil 
blottsbooks.core=> (pricing/discount-price {:title "Emma" :price 20.0})
17.0
```

そして、あなたは`discount-price`関数をより説明的な`compute-discount-price`にリネームすることにした。お気に入りのテキストエディタを立ち上げ、`src/blotts-books/pricing.clj`を次のように修正しました：

namespace/blottsbooks-2/src/blottsbooks/pricing.clj
```clojure
(ns blottsbooks.pricing)

(def discount-rate 0.15)

(defn compute-discount-price [book]
  (- (:price book)
     (* discount-rate (:price book))))
```

そうしたら、REPLに戻って名前空間を再びrequireする：

```clojure
blottsbooks.core=> (require '[blottsbooks.pricing :as pricing])
nil
```

悲しいかな、うまくいかない：

```clojure
blottsbooks.core=> (pricing/compute-discount-price
                     {:title "Emma" :price 20.0})
CompilerException java.lang.RuntimeException:
  No such var: pricing/compute-discount-price, ...
```

問題は、`require`は`blottsbooks.pricing`名前空間がすでに読み込まれていることを知っていて、2回目の読み込みを静かに拒否することです。これは本番環境では望ましいことです。`require`は何度でも名前空間に読み込みますが、読み込まれるのは一度だけです。しかし、この "load once "ポリシーは開発中に邪魔になります。 幸いなことに、解決方法は簡単で、`require` に `:reload` キーワードを追加するだけです：

```clojure
blottsbooks.core=> (require :reload '[blottsbooks.pricing :as pricing])
nil
blottsbooks.core=> (pricing/compute-discount-price
                     {:title "Emma" :price 20.0})
17.0
```


これで `require` は既に存在する名前空間であろうとなかろうと、毎回コードを再読み込みするようになります。

ただし、`:reload`オプションはリロード時に名前空間の既存の内容を消去しないことに注意してください。つまり、ソース中で`discount-price`の名前を`compute-discount-price`に変更してから名前空間をリロードすると、確かに`compute-discount-price`という新しい関数が定義されます。しかし、古い`discount-price`はまだREPLの中をゾンビのようにさまよっている。たいていの場合、これは問題にはならないが、もしそうであれば、いつでも `ns-unmap` を実行して削除することができる：

```clojure
blottsbooks.core=> (ns-unmap 'blottsbooks.pricing 'discount-price)
```

`:reload` のもう1つの残念な点は、名前空間がリロードされるたびに実行したくないコードがある場合が時々あるということです。おそらく、実行に時間がかかる関数を呼び出していたり、何らかの副作用があったりするのでしょう：

```clojure
;; I really only want this to happen once.
(def some-value (function-with-side-effects))
```

ここでもClojureは救済に乗り出し、今度は`def`の代わりに`defonce`を使う：

```clojure
;; Just set some-value the first time.
(defonce some-value (function-with-side-effects))
```

その名前が示すように、`defonce`はシンボルと値を最初の1回だけ結合する。したがって、この例では、`function-with-side-effects`が評価されるのは、名前空間を最初にロードしたときの一度だけである。その後、キーボードが擦り切れるまで `:reload` することができ、`some-value` が再定義されることはありません。


もし気が変わったら、いつでも `ns-unmap` を使って `defonce` を動かすことができる：

```clojure
(ns-unmap *ns* 'some-value)
```

そして、輪は完成した。



