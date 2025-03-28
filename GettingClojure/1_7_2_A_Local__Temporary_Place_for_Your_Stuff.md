      
### ローカルな一時的な場所

Clojureのローカルネーミングの探求を、私たちの書店が定期的な特売を実施していると想像することから始めます。時々、私たちは顧客に本の購入の割引を提供します。残念なことに、私たちの取引にはいくつかの細かい制限があります：各注文には、割引を上書きする最低料金があります。

関数と`if`に関する苦労して得た知識で武装すれば、この割引ポリシーをClojureコードに変えるのは難しいことではありません：


let/examples.clj 
```clojure
(defn compute-discount-amount [amount discount-percent min-charge]
  (if (> (* amount (- 1.0 discount-percent)) min-charge)
    (* amount (- 1.0 discount-percent))
    min-charge))
```   

日常的なソフトウェア・エンジニアリングの一端として、`compute-discount-amount`は賛否両論である。 プラス面では、これは機能する。残念なことに、`compute-discount-amount`は明瞭さの見本とは言い難い。数ヶ月ぶりにこのコードに戻ってきたら、"待てよ、誰が何倍より大きいんだ？"とつぶやいている可能性が高い。

明らかに、意図を明らかにするネーミングが必要なのだ。シンボル（おそらく`discounted-amount`という名前）を適切な値にバインドして、割引の計算が終わったらそのバインディングが消えるようにすれば、コードはもっと簡単になるだろう。でもどうやって？

こんなことをしたくなるかもしれない：

```clojure
;; Don't do this!
(defn compute-discount-amount [amount discount-percent min-charge]
  (def discounted-amount (* amount (- 1.0 discount-percent))) ; NOOOOO!
  (if (> discounted-amount min-charge)
    discounted-amount
    min-charge))
```

関数の内部でこのような`def`の使用を避けるべき理由は2つある。一つ目の理由は、これまで見てきたように、`def`で束縛されたシンボルはグローバルに参照できるからである。このバージョンの`compute-discount-amount`を呼び出すと、`discounted-amount`に束縛された値が変更されるという醜い副作用が発生し、その変更は「関数の外」で見ることができる：

```clojure
;; A nasty side effect is brewing here.
(def discounted-amount "Some random string.")
(compute-discount-amount 10.0 0.20 1.0)
discounted-amount ; Is now 8.00
```

2 番目の、より哲学的な理由は、`def`をこのように使うことは、実は誤用であるということである： `def`は多かれ少なかれグローバルなシンボルを、多少なりとも安定した値に束縛するように設計されている。`def`の代わりに、ローカルな名前付けが必要な場合は `let` を使うべきである：

```clojure
;; Do use let!
(defn compute-discount-amount [amount discount-percent min-charge]
  (let [discounted-amount (* amount (- 1.0 discount-percent))]
    (if (> discounted-amount min-charge)
      discounted-amount
      min-charge)))
```



`let`の仕組みはこれ以上ないほどシンプルだ。関数のように`let`を呼び出し、名前と値を角括弧で囲み、その後に式を渡す。要するに、「このシンボルをこの値に束縛してこの式を実行せよ」ということだ。この例では、シンボルは `discounted-amount` で、値は注文金額から割引率を引いたものである。この例からわかるように、`let`が返す値は、`let`の式（"body"）によって計算された値です。重要なことは、`let` が終了すると、`let` によって生成された束縛は消えるということです。

`let`の優れた機能の一つは、一つの`let`の中で複数の名前をバインドできることです。

例えば、割引の計算を2つのステップで行うことで、コードをもう少しわかりやすくすることができます：

```clojure
(defn compute-discount-amount [amount discount-percent min-charge]
  (let [discount (* amount discount-percent)
        discounted-amount (- amount discount)]
    (if (> discounted-amount min-charge)
      discounted-amount
      min-charge)))
```


このルールは、`let`がそれぞれの名前を対応する値に束縛するというものである。各名前は束縛された直後から利用可能になるので、 `discount` を使って `discounted-amount` を計算することができる。

また、`let`本体の中に複数の式を記述することもできる。例えば、デバッグのために中間値を表示したい場合は、このようにします：

```clojure
(defn compute-discount-amount [amount discount-percent min-charge]
  (let [discount (* amount discount-percent)
        discounted-amount (- amount discount)]
    (println "Discount:" discount)
    (println "Discounted amount" discounted-amount)
    (if (> discounted-amount min-charge)
      discounted-amount
      min-charge)))
```

`let`の本体内の式はすべて評価されるが、`let`が返す値については、最後の式だけが意味を持つことを覚えておいてほしい。



