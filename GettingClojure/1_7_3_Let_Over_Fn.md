        
### Let Over Fn
          
letの背後にあるアイデアはそれほど難しいものではないが（純粋にローカルで一時的な方法で名前と値を結びつける）、`let`には隠された超大国があり、それは`fn`と組み合わせて初めて見えてくる。この隠された力を垣間見るために、私たちの本の割引が顧客依存であると想像してみよう。 どこかにユーザー名とそのユーザーが受ける割引のマップがあるものとする：

```clojure
(def user-discounts
  {"Nicholas" 0.10 "Jonathan" 0.07 "Felicia" 0.05})
```

これに対処するために、`compute-discount-amount`にいくつかのパラメーターを追加することは可能だ：

```clojure
(defn compute-discount-amount [amount user-name user-discounts min-charge] 
  (let [discount-percent (user-discounts user-name)
        discount (* amount discount-percent)
        discounted-amount (- amount discount)]
    (if (> discounted-amount min-charge)
      discounted-amount
      min-charge)))
```

この方法の問題点は、価格を計算するたびに "ユーザー名と割引額 "のテーブルを持ち運ばなければならないことである。このような余分な作業が問題になるのであれば、より高レベルの関数を作成し、特定の顧客に合わせた`compute-discount-amount`関数の変種を生成するのが良い方法かもしれません：


```clojure
(defn mk-discount-price-f [user-name user-discounts min-charge]
  (let [discount-percent (user-discounts user-name)] 
    (fn [amount]
      (let [discount (* amount discount-percent)
            discounted-amount (- amount discount)]
        (if (> discounted-amount min-charge)
          discounted-amount
          min-charge)))))
;; Get a price function for Felicia.
(def compute-felicia-price (mk-discount-price-f "Felicia" user-discounts 10.0))
;; ...and sometime later compute a price
(compute-felicia-price 20.0)
```

`mk-discount-price-f`には非常に多くの部品がありますが、それはすでに見てきた機能の組み合わせにすぎません。最初の `let` で `mk-discount-price-f` が行うことは、ユーザーの割引率を調べることである。割引率を取得すると、 `mk-discount-price-f` は `compute-discount-amount` とほぼ同じ内容の fn を持つ無名関数を作成する。

`mk-discount-price-f`の面白いところは、`discount-percent`が最初の`let`で束縛され `fn` の「外側」で使用され、その後 `fn` の「内側」で使用されることである。つまり、`discount-percent` は `let` の内部でしか見ることができませんが、`mk-discount-price-f` の呼び出しが完了した後も、匿名関数の内部で生き続けることができます。


この「letの中で計算し、fnの中で使用する」という方法は、効率的で明快な無名関数を構築するための優れた方法です。`let`を使えば、無名関数を構築するために必要なすべての計算を行うことができるので効率的だ。また、無名関数の内部では、事前に計算された値に説明的な名前を使うことができるので、明快です。


