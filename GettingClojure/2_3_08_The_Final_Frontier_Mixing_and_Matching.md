
### 最後のフロンティア：ミックスとマッチング

これまで、ベクターやリストのようなシーケンシャルなものをデストラクチャする方法を見てきた。また、マップをデストラクチャする方法も見てきた。まだやっていないのは、この2つの混合物、おそらくベクターの中のマップか、マップの中のベクターのようなものをデストラクチャすることだ。シーケンスとマップを混ぜてデストラクチャリングするには、デストラクチャリング構文を想像通りの方法で混ぜてください。

このベクターとマップの中のベクターが混ざったものを見てみよう：

```clojure
(def author {:name "Jane Austen"
             :books [{:title "Sense and Sensibility" :published 1811}
                     {:title "Emma" :published 1815}]})
```

ジェーンの名前とエマに関する情報は、以下の簡単な操作で入手できる。

```clojure
(let [{name :name [_ book] :books} author]
  (println "The author is" name)
  (println "One of the author's books is" book))
```

あるいは、ベクターの中にいくつかのマップがあった場合、おそらく次のようになるだろう：

```clojure
(def authors [{:name "Jane Austen" :born 1775}
              {:name "Charles Dickens" :born 1812}])
```

生年月日を調べるのは簡単だ：

```clojure
(let [[{dob-1 :born} {dob-2 :born}] authors]
  (println "One author was born in" dob-1)
  (println "The other author was born in" dob-2))
```

