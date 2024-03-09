
### Dealing with Multiple Conditions 
 
Technically, no matter how complicated the decision you need to make, the plain old `if` is all you ever need. Choosing between three alternatives? Just nest a couple of `if`s. Say, for example, our shipping charges were free to preferred customers and otherwise $5 for orders under $50, $10 for orders between $50 and $100, and 10% of the purchase price for bigger orders, we could write something like this: 

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

Have another alternative? Then just nest another `if`—and at some point drive yourself crazy. While your CPU may be fine with deeply nested if expressions, the mushy computer between your ears probably prefers to look at this sort of situation as a series of alternatives: "Is it this? No? Well, is it that? …"

Fortunately Clojure has an expression for just such occasions: `cond`. Here’s a partial implementation of our shipping-cost function using `cond`:

```clojure
(defn shipping-charge [preferred-customer order-amount]
  (cond
    preferred-customer 0.0
    (< order-amount 50.0) 5.0
    (< order-amount 100.0) 10.0)) 
``` 


As you can see in the example, `cond` takes pairs of expressions, each pair made up of a predicate expression and a value expression. In the example, the predicates are `preferred-customer`, `(< order-amount 50)`, and `(< order-amount 100)`. When it’s evaluated, `cond` evaluates each predicate in order. If the predicate is falsy—that is, either `nil` or `false`—`cond` goes on to the next pair. If the predicate is truthy, then `cond` will evaluate the value expression and return that, leaving the remaining pairs unevaluated.

One problem with our `cond`-based `shipping-charge` function is that it doesn’t handle orders of $100 or more properly. If you evaluated `(shipping-charge 200)` you would get a `nil` back for your trouble, since that’s what `cond` returns if none of the predicates come back truthy.

We have a couple of options for fixing `shipping-charge`. We could certainly add a predicate to explicitly cover the "$100 or more" case:

```clojure
(defn shipping-charge [preferred-customer order-amount]
  (cond
    preferred-customer 0.0
    (< order-amount 50.0) 5.0
    (< order-amount 100.0) 10.0
    (>= order-amount 100.0) (* 0.1 order-amount)))
```

Alternatively, we could add in a catch-all `:else` clause:


```
(defn shipping-charge [preferred-customer order-amount]
  (cond
    preferred-customer 0.0
    (< order-amount 50.0) 5.0
    (< order-amount 100.0) 10.0
    :else (* 0.1 order-amount)))
```

Note that the `:else` clause is not special new `cond` syntax. It’s just another predicate/expression pair. Think about it: `if` none of the other predicates are truthy, `cond` will arrive at the last predicate/expression pair, decide that since `:else` is neither `false` nor `nil` it must be truthy, and return 10% of the order amount. In principle we could have used any truthy value instead of `:else`.  `:default`, `true`, and `"whatever"` would all work. We use `:else` because that’s the Clojure convention for the everything else clause of a `cond`.

Along the lines of `cond` we have the somewhat less powerful but still useful `case`, which lets your code turn this way or that based on a single value. Here is how we might use `case` to come up with a welcome message:

```clojure
(defn customer-greeting [status]
  (case status
    :gold      "Welcome, welcome, welcome back!!!"
    :preferred "Welcome back!"
               "Welcome to Blotts Books"))
```

The idea is that your value—`status` in the example—should match one of the constants in the case statement—`:gold` or `:preferred` in the example. If it does match, then the whole `case` evaluates to the expression paired with the constant. If nothing matches, then the expression evaluates to the last, unpaired expression—in this case `"Welcome to Blotts Books"`.

A couple of things to keep in mind about `case`: First, the last catch-all expression is optional, but if you do leave it out the `case` will generate an error if none of the constants match. Second, the constants need to be just that: constant. The constants in case expressions are one of the few places where an expression does not get evaluated.


