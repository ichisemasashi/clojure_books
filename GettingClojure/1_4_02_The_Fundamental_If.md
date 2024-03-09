
### The Fundamental If

Clojure includes an `if` expression, and the good news is that there isn’t a lot to say about it: Clojure’s `if` is about as boring a programming-language feature as you are likely to come across. An `if` expression starts with the word `if`, which is followed by two other expressions: first a condition, then an expression to evaluate if the condition is true. The whole `if` expression is wrapped in round parentheses:

logic/examples.clj
```clojure
(defn print-greeting [preferred-customer]
  (if preferred-customer
    (println "Welcome back to Blotts Books!")))
```

Call the function in that example with `true`, and you will get a warm greeting printed. Call it with false, and you will get silence. You can also add an optional third expression inside of your `if`, giving you the classic `if/then/else` expression, sans the `else` keyword:

```clojure
(defn print-greeting [preferred-customer]
  (if preferred-customer
    (println "Welcome back to Blotts Books!")
    (println "Welcome to Blotts Books!")))
```

Notice that I keep talking about `if` expressions. I do this because a Clojure `if`, like everything else in the language, is a value-returning expression. Thus if preferred customers get free shipping while every one else pays 10 percent, we might come up with something like this:

```clojure
(defn shipping-charge [preferred-customer order-amount]
  (if preferred-customer
    0.00
    (* order-amount 0.10)))
```


The value returned from an `if` is the value returned from the last expression evaluated in the `if`. If you have a one-legged `if`—one with no else expression—and the condition is false, then the whole `if` expression will evaluate to `nil`. Thus this:

```clojure
(if preferred-customer
  "So nice to have you back!")
```

will return either the string or `nil`, depending on the value of preferred-customer.

You should also note that Clojure programmers usually write short `if` expressions like the last example on a single line, so that this:

```clojure
(if preferred-customer "So nice to have you back!")
```

is perfectly good Clojure.


