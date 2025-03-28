
### 複数の条件を扱う 
 
技術的には、どんなに複雑な決定をする必要があっても、古くからある「if」があれば十分です。3 つの選択肢から選ぶ場合は？いくつかの `if` を入れ子にすればいい。例えば、送料が優先顧客(preferred-customer)には無料、それ以外の50ドル未満の注文には5ドル、50ドル以上100ドル未満の注文には10ドル、それ以上の注文には購入金額の10％だったとすると、次のように書くことができる： 

```clojure
(defn shipping-charge [preferred-customer order-amount]
  (if preferred-customer
    0.0
    (if (< order-amount 50.0)
      5.0      
      (if (< order-amount 100.0)
        10.0   
        (* 0.1 order-amount)))))
```

他にも選択肢がある場合？それなら別の`if`をネストすればいい。あなたのCPUは深くネストされたif式でも平気かもしれないが、あなたの耳の間にあるムズムズしたコンピューターは、おそらくこの種の状況を一連の選択肢として見ることを好むだろう： "これか？違うか？じゃあ、それは？..."

幸いなことに、Clojureにはそのような場合のための表現がある： `cond`である。以下は `cond` を使ったshipping-charge関数の部分的な実装です：

```clojure
(defn shipping-charge [preferred-customer order-amount]
  (cond
    preferred-customer 0.0
    (< order-amount 50.0) 5.0
    (< order-amount 100.0) 10.0)) 
``` 


この例を見ればわかるように、`cond`は述語式と値式のペアを取ります。この例では、述語は `preferred-customer`、`(< order-amount 50)`、`(< order-amount 100)`です。評価されるとき、 `cond` はそれぞれの述語を順番に評価します。述語が偽の場合、つまり `nil` または `false` の場合、 `cond` は次のペアに進みます。述語が真であれば、 `cond` は値式を評価してそれを返し、残りのペアは評価されない。

しかし、この `cond` ベースの `shipping-charge` 関数の1つの問題は、100ドル以上の注文を適切に処理できないことです。もし、`(shipping-charge 200)` を評価した場合、`nil` が返されるでしょう。

`shipping-charge`を修正するためにいくつかのオプションがある。"100ドル以上"のケースを明示的にカバーする述語を追加することができます：

```clojure
(defn shipping-charge [preferred-customer order-amount]
  (cond
    preferred-customer 0.0
    (< order-amount 50.0) 5.0
    (< order-amount 100.0) 10.0
    (>= order-amount 100.0) (* 0.1 order-amount)))
```

あるいは、すべてをカバーする `:else` 節を追加することができる：


```
(defn shipping-charge [preferred-customer order-amount]
  (cond
    preferred-customer 0.0
    (< order-amount 50.0) 5.0
    (< order-amount 100.0) 10.0
    :else (* 0.1 order-amount)))
```

なお、`:else`節は特別な新しい`cond`構文ではない。単なる述語と式のペアである。考えてみてほしい：もし他の述語がどれもtruthyでない場合、`cond`は最後の述語と式のペアに到達し、`:else`は`false`でも`nil`でもないのでtruthyに違いないと判断し、注文金額の10%を返す。原理的には、`:else`の代わりにどんな真理値でも使うことができた。 `:default`, `true`, `"whatever"`のどれでも使えるだろう。我々は `:else` を使っているが、これは `cond` の everything else 節に対する Clojure の慣例だからである。

`cond`と同じように、やや強力ではないが便利な `case` がある。ここでは `case` を使ってウェルカムメッセージを考えてみよう：

```clojure
(defn customer-greeting [status]
  (case status
    :gold      "Welcome, welcome, welcome back!!!"
    :preferred "Welcome back!"
               "Welcome to Blotts Books"))
```

この考え方は、あなたの値（例では ``status` ）が case 文の定数（例では ``:gold`` または `:preferred` ）にマッチしなければならないというものである。もしマッチすれば、`case`全体が定数と対になった式で評価されます。もし何もマッチしなければ、その式は最後の、ペアになっていない式、この例では `"Welcome to Blotts Books"`として評価されます。

`case`について覚えておくべきことがいくつかある： まず第一に、最後のキャッチオール式は省略可能ですが、もし省略した場合、`case`はどの定数もマッチしない場合にエラーを生成します。第二に、定数はあくまでも定数である必要があります。case式の定数は、式が評価されない数少ない場所の1つです。


