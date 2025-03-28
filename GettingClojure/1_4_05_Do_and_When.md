
### do と when

Clojureの`if`の1つの欠点は、真の足と偽の足に対して1つの式に制限されていることです。しかし、条件が真のときに複数のことを行いたい場合はどうなるでしょうか？あるいは、条件が偽であるときにいくつかのことをしたい場合はどうなるのだろう？ここでのキーワードはdoで、これはClojureが "group "と呼ぶ "式の束を1つの式にする "構文だからだ。したがって、こうなる：

```clojure
(do
  (println "This is four expressions.")
  (println "All grouped together as one")
  (println "That prints some stuff and then evaluates to 44")
  44)
```

これは`44`を返す一つの式である。do`で武装すれば、単純な`if`に真の足と偽の足をつけることができる：

```clojure
(defn shipping-charge [preferred-customer order-amount]
  (if preferred-customer
    (do
      (println "Preferred customer, free shipping!")
      0.0)
    (do
      (println "Regular customer, charge them for shipping.")
      (* order-amount 0.10))))
```

Clojureはまた、`when`と呼ばれる`if`の変種も持っている。これはelse（またはfalsy）レッグを持たないが、`do`を必要とせずに複数のステートメントをサポートする：


```clojure
(when preferred-customer
  (println "Hello returning customer!")
  (println "Welcome back to Blotts Books!"))
```

予想通り、`when`は条件が真で ないときには`nil`を返す。


