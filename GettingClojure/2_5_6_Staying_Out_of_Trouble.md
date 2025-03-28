
### トラブルを避ける
                 
では、従来のユニットテストと生成テストのどちらが優れているのでしょうか？ 答えは簡単です。 はい。 従来のテストと生成テストには、それぞれ長所と短所があります。 そして、どちらもコードが想定通りに動作していることを確認する上で役立ちます。

従来のユニットテストには、明白な大きな利点があります。 これを実行すると機能するだろうか？ はい。 あれを実行すると機能するだろうか？ はい。 よし、これで大丈夫だ。 では、この単純な機能をテストしようとしているとします。

test/trouble_one.clj
```clojure
(defn f [a b] (/ a b))
```

このテストをこのように書くことができる。

```clojure
(deftest test-f
  (is (= 1/2 (f 1 2)))
  (is (= 1/2 (f 3 6))) 
  (is (= 1 (f 10 10))))
```

そして、このような特定のケースでは、それが機能することはわかっています。このような手作業によるユニットテストの欠点は、我々の忍耐と想像力に強く制約されることです。私は、1日の仕事を終える前に、数十以上の数の整数のテストを書くつもりはありません。

一方、生成テストは、膨大なテストケースをカバーします。例えば、次のように書くことができます。

```clojure 
(ctest/defspec more-complex-spec 10000
  (prop/for-all [a gen/pos-int
                 b gen/pos-int]
    (is (= (* b (f a b)) a))))
```

そして、`b` がゼロのときは `f` が機能しないことがすぐにわかるでしょう。生成テストの危険性は、生成されたテストケースがすべて、すべての可能性をカバーしていると私たちを信じ込ませてしまうことです。計算してみると、何百万ものテストケースでも、可能な整数の組み合わせの割合はほぼゼロであることがわかるでしょう。関数を少し変更してみましょう：

```clojure
(defn more-complex-f [a b] (/ a (+ b 863947)))

(ctest/defspec test-more-complex 10000
  (prop/for-all [a gen/pos-int
                 b gen/pos-int]
    (= (* (more-complex-f a b) (- b 863947)) a)))
```

生成テストでは、除算をゼロと見逃す可能性が極めて高いでしょう。私が申し上げたいのは、通常、最善の解決策は、少数の従来のユニットテストと、より広範囲をカバーする生成テストを組み合わせることです。

test/trouble_two.clj
```clojure
;; ゼロによる割り算を防ぎます。
(defn more-complex-f [a b]
  (let [denominator (- b 863947)]
    (if (zero? denominator)
      :no-result
      (/ a denominator))))
;; しかし、私たちは、それが正しく検出されていることを確認したいのです。
(deftest test-critical-value
  (is (= :no-result (more-complex-f 1 863947))))
;; そして、その機能は他のケースでも有効です。
(def non-critical-gen (gen/such-that (partial not= 863947) gen/pos-int))

(ctest/defspec test-other-values 10000
  (prop/for-all [a gen/pos-int
                 b non-critical-gen]
    (= (* (more-complex-f a b) (- b 863947)) a)))
```

最後に、テストをまったく実施しないよりは、たとえ1つでもテストを実施する方がはるかに良いということを念頭に置いてください。

もう一度、最初の在庫テストに戻ってみましょう。

```clojure
(require '[inventory.core :as i])

(deftest test-finding-books
  (is (not (nil? (i/find-by-title "Emma" books)))))
```

このテストはあまり印象的ではないかもしれませんが、実行してみると、以下のことが示されます。

* `inventory.core` という名前空間が存在する。
* `inventory.core` に重大な構文エラーがない。
* `inventory.core` という名前空間には `find-by-title` という関数が存在する。
* `find-by-title` を2つのパラメータで呼び出すことができる。
* `find-by-title` を妥当なパラメータで呼び出しても例外は発生しません。
* `find-by-title` 関数は常に `nil` を返すわけではありません。

これは、`find-by-title` がまさにあなたが望むことを行うという厳粛な証言ではありませんが、何も無いというわけでもありません。少しのテストでも大きな効果があります。


