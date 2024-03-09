
### Functions on the Fly

There’s something else you can do with functional values: you can manufacture new ones, on the fly. In much the same way you can make a new number with `(+ 2 3)` or `(* 5 x)`, you can use `fn` to create new functions. Here, for example, we manufacture a new function with `fn`, one that doubles its argument:

```clojure
(fn [n] (* 2 n)) 
```


As you can see from the example, using `fn` is a lot like using `defn`, except that you leave out the name. Like `defn`, `fn` creates a new function, essentially a packaged bit of code. The difference between `fn` and `defn` is that `fn` doesn’t bind its newborn bundle of code to a name; you just get the function value. So what can you do with a function value? Anything you can do with any other value. You can, for example, print it:


```clojure
(println "A function:" (fn [n] (* 2 n)))
``` 

or bind it to a symbol:

```clojure
(def double-it (fn [n] (* 2 n))) 
```

and, most importantly, call it:

```clojure
(double-it 10)        ; Gives you 20.
((fn [n] (* 2 n)) 10) ; Also gives you 20.
```


Returning to our book example, here is a nameless function that does the same thing as `cheap?`:

```clojure
(fn [book]
  (when (<= (:price book) 9.99)
    book))
```

Armed with `fn`, we can write functions that produce functions:

```clojure
(defn cheaper-f [max-price]
  (fn [book]
    (when (<= (:price book) max-price)
      book)))
```

It’s important to understand just how meta we’ve gone here: `cheaper-f` is a function that produces a whole family of bargain-spotting functions, each with its own idea of what constitutes a bargain.

```clojure
;; Define some helpful functions.
(def real-cheap? (cheaper-f 1.00))
(def kind-of-cheap? (cheaper-f 1.99))
(def marginally-cheap? (cheaper-f 5.99))

;; And use them.
(real-cheap? dracula)       ; Nope.
(kind-of-cheap? dracula)    ; Yes.
(marginally-cheap? dracula) ; Indeed.
```


If this all looks less than spectacular, look again. The thing to note is that a function produced by `fn` picks up and remembers the parameters around when the `fn` was run. So in the last example, the function produced when you call `(cheaper-f 1.00)` will remember that `max-price` is `1.00` while the function produced by `(cheaper-f 5.99)` will remember `max-price` as `5.99`.

Going a step further, we can write a function that manufactures `both?`-like functions:

```clojure
(defn both-f [predicate-f-1 predicate-f-2]
  (fn [book]
    (when (and (predicate-f-1 book) (predicate-f-2 book))
      book)))
```

With `both-f` we can then build a whole family of book-discriminating functions:

```clojure
(def cheap-horror? (both-f cheap? horror?))
(def real-cheap-adventure? (both-f real-cheap? adventure?))
(def real-cheap-horror? (both-f real-cheap? horror?))
```

And then go up yet another level of meta:

```clojure
(def cheap-horror-possession?
  (both-f cheap-horror?
    (fn [book] (= (:title book) "Possession"))))
```

This idea of a function grabbing and remembering the bindings that existed when the function was born is called a "closure". We say that the function "closes" over the scope in which it was defined. More than anything else, the twin ideas of functions as values and "closure" are at the heart of what makes Clojure the programming language it is, and might explain the name as well.
