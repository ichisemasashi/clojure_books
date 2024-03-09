      
### A Local, Temporary Place for Your Stuff

We’ll begin our exploration of local naming in Clojure by imagining that our book store runs periodic specials. Every now and then we offer our customers a percentage discount on their book purchases. Unfortunately, our deal does come with some fine print: there’s a minimum charge for each order that overrides the discount.

Armed with our hard-won knowledge of functions and `if`, it’s not difficult to turn this discount policy into Clojure code:


let/examples.clj 
```clojure
(defn compute-discount-amount [amount discount-percent min-charge]
  (if (> (* amount (- 1.0 discount-percent)) min-charge)
    (* amount (- 1.0 discount-percent))
    min-charge))
```   

As a bit of everyday software engineering, `compute-discount-amount` is a mixed bag.  On the plus side, it does work. Unfortunately, `compute-discount-amount` is hardly a model of clarity. Come back to this code after a few months’ absence, and there’s a fair chance you’ll be muttering, "Wait, what times who is greater than huh?"

Clearly a bit of intention-revealing naming is called for. The code would be a lot easier to follow if we could bind a symbol—perhaps called `discounted-amount`—to the appropriate value and have that binding disappear once we’re done computing discounts. But how?

You might be tempted to do something like this:

```clojure
;; Don't do this!
(defn compute-discount-amount [amount discount-percent min-charge]
  (def discounted-amount (* amount (- 1.0 discount-percent))) ; NOOOOO!
  (if (> discounted-amount min-charge)
    discounted-amount
    min-charge))
```

There are two reasons that you should avoid using `def` like this, inside of a function. The first reason is that, as we have seen, symbols bound with `def` have reasonably global visibility. Calling this version of `compute-discount-amount` will have the ugly side effect of changing the value bound to `discounted-amount`, and that change will be visible "outside of the function":

```clojure
;; A nasty side effect is brewing here.
(def discounted-amount "Some random string.")
(compute-discount-amount 10.0 0.20 1.0)
discounted-amount ; Is now 8.00
```

The second, more philosophical reason is that using `def` this way is actually misusing it: `def` is designed to bind more or less global symbols to their more or less stable values. Instead of `def` you should use `let` for your local-naming needs:

```clojure
;; Do use let!
(defn compute-discount-amount [amount discount-percent min-charge]
  (let [discounted-amount (* amount (- 1.0 discount-percent))]
    (if (> discounted-amount min-charge)
      discounted-amount
      min-charge)))
```



The mechanics of `let` couldn’t be simpler: you call `let` like a function, passing in a name and a value—wrapped in square brackets—followed by an expression. In essence you say, "Execute this expression with this symbol bound to this value". In our example, the symbol was `discounted-amount` and the value was the order amount less the percentage discount. As you can probably figure out from the example, the value returned by the `let` is the value computed by the expression—the "body"—of the `let`. Critically, the bindings manufactured by `let` go away once the `let` is done, so that you can get on with the rest of the code without littering your mental landscape with stray names.

One nice feature of `let` is that you can bind multiple names inside a single `let`.

We could, for example, make the code a bit clearer by doing the percentage-off calculation in two steps:

```clojure
(defn compute-discount-amount [amount discount-percent min-charge]
  (let [discount (* amount discount-percent)
        discounted-amount (- amount discount)]
    (if (> discounted-amount min-charge)
      discounted-amount
      min-charge)))
```


The rule is that `let` will bind each name to its corresponding value, starting with the first one. Each name becomes available immediately after it’s bound, which is why we can use `discount` to compute `discounted-amount`.

You can also have more than one expression inside the body of the `let`. If, for example, you wanted to print our intermediate values for debugging purposes, you could do this:

```clojure
(defn compute-discount-amount [amount discount-percent min-charge]
  (let [discount (* amount discount-percent)
        discounted-amount (- amount discount)]
    (println "Discount:" discount)
    (println "Discounted amount" discounted-amount)
    (if (> discounted-amount min-charge)
      discounted-amount
      min-charge)))
```

Keep in mind that while all the expressions in the body of a `let` get evaluated, only the last expression has anything to say about the value returned by the `let`.



