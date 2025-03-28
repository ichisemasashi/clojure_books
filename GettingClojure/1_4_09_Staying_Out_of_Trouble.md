
### トラブルに巻き込まれないために
  
あなたのプログラミングの背景によって、論理に対するtuthy/falsyのアプローチは、まったく平凡なものに見えるか、あるいはすべての正義と道徳に対する暴挙のように見えるだろう。もしあなたが暴挙派に傾いているのであれば、「falseとnilはfalseであり、それ以外はすべてtrueである」というルールは、プログラマーが注意深くないときにだけ現れるような小さな異常ではないということを覚えておいてください。 Clojureには、この振る舞いを利用したコードがたくさんあります。その例として、`and` を見てほしい。(and true true)` は確かに `true` と評価され、`(and false false)` は快適に `false` と評価されますが、`and` 関数は明らかにブール値ではない値を返すことがあります：

```clojure
(and true 1984)       ; Evaluates to 1984, which is truthy.
(and 2001 "Emma")     ; Evaluates to "Emma", again truthy.
(and 2001 nil "Emma") ; Evaluates to nil, which is falsy.
``` 
    
ポイントは、`and`の動作は、真偽の観点から見た場合にのみ意味をなすということだ。このことを考えると、明示的に真か偽かをテストすることは避けるべきである。例えば、この`if`の条件部分：

```clojure
(if (= (some-predicate? some-argument) true)
  (some-other-function))
```

は余計な言葉が多いだけでなく、間違っている。some-predicate?` の作者は、`and`の作者と同じように、真偽を示すために`true`以外のものを返すことにしたのかもしれない。もしそうなら、この `if` はそれを見逃すことになる。

また、避けるべき文体の落とし穴もある： `if`と`cond`を含む関数を作り、少し入れ子にしていくと、最後になるにつれて、たくさんの括弧を閉じなければならないことに気づくだろう。慣例としては、式の最後の行で括弧を閉じます。これは、`shipping-charge`の最後の3つの括弧でやった方法です：


```clojure
(defn shipping-charge [preferred-customer order-amount]
  (cond
    preferred-customer 0.0
    (< order-amount 50.0) 5.0
    (< order-amount 100.0) 10.0
    :else (* 0.1 order-amount))) ; Close, close, close.
```

やってはいけないのは、次のように閉じ括弧をそれぞれ独立した行にすることだ：

```clojure
(defn shipping-charge [preferred-customer order-amount]
  (cond
    preferred-customer 0.0
    (< order-amount 50.0) 5.0
    (< order-amount 100.0) 10.0
    :else (* 0.1 order-amount) ; No.
  ) ; No.
) ; No!
```

Clojureの括弧ベースの構文に慣れてくると、すべての閉じ括弧は心理的な背景の中に消えていきます。そうなるのを待つ間、それらに注意を向けないでください。


