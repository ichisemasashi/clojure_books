
### Do and When

One wrinkle with Clojure’s `if` is that you are limited to one expression for the truthy leg and one for the falsy leg. But what happens if you want to do several things when the condition is truthy? Or several when the condition is falsy? The key word here is do because that is what Clojure calls its "group" a "bunch of expressions into a single expression" construct. Thus, this:

```clojure
(do
  (println "This is four expressions.")
  (println "All grouped together as one")
  (println "That prints some stuff and then evaluates to 44")
  44)
```

is a single expression that returns `44`. Armed with `do`, we can flesh out a simple `if` with multipart true and false legs:

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

Clojure also sports a variant of `if` called `when`, which doesn’t have an else (or falsy) leg but which supports multiple statements without needing the `do`:


```clojure
(when preferred-customer
  (println "Hello returning customer!")
  (println "Welcome back to Blotts Books!"))
```

As you might expect, `when` returns `nil` when the condition is not truthy.


