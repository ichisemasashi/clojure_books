
### 基本的なif

Clojureには`if`式があり、良いニュースはそれについて言うことがあまりないということです： Clojureの `if` は、プログラミング言語としては退屈な機能です。if`式は `if` という単語で始まり、その後に2つの式が続きます：最初に条件、次に条件が真であった場合に評価する式です。if`式全体は丸括弧でくくられる：

logic/examples.clj
```clojure
(defn print-greeting [preferred-customer]
  (if preferred-customer
    (println "Welcome back to Blotts Books!")))
```

この例の関数を`true`で呼び出すと、温かい挨拶が表示される。一方、`false`で呼び出すと、沈黙が得られます。また、`if`の中にオプションで3つ目の式を追加することもでき、`else`キーワードを除いた古典的な`if/then/else`式になります：

```clojure
(defn print-greeting [preferred-customer]
  (if preferred-customer
    (println "Welcome back to Blotts Books!")
    (println "Welcome to Blotts Books!")))
```

私が `if` 式について話し続けていることに注目してほしい。なぜなら、Clojureの`if`は、言語の他のすべてのものと同様に、値を返す式だからです。したがって、優先顧客(preferred-customer)は送料(shipping-charge)が無料になり、それ以外の顧客は10%支払う場合、次のようになります：

```clojure
(defn shipping-charge [preferred-customer order-amount]
  (if preferred-customer
    0.00
    (* order-amount 0.10)))
```


`if` から返される値は、`if` の中で最後に評価された式から返される値である。もし1本足の `if` があり、else 式がなく、条件が false の場合、`if` 式全体が `nil` と評価されます。このように

```clojure
(if preferred-customer
  "So nice to have you back!")
```

は preferred-customer の値に応じて、文字列か `nil` のどちらかを返します。

また、Clojureプログラマは通常、前の例のような短い `if` 式を1行で書くことに注意してください：

```clojure
(if preferred-customer "So nice to have you back!")
```

これは完全に良いClojureだ。


