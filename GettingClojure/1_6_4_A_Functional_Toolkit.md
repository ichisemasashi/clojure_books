
### A Functional Toolkit

Since so much of Clojure programming revolves around creating, combining, and using functions, it’s unsurprising that the language provides a fair number of functions aimed at easing the job.

Take, for example, the `apply` function. It tackles the surprisingly common situation where you have a function and the arguments that you want to call that function with "in a collection". In other words, instead of having this: 

```clojure
(+ 1 2 3 4) ; Gives you 10.
```

what if you had the function (`+` in this case) and the arguments, like this:

```clojure
(def the-function +)
(def args [1 2 3 4])
```

Enter `apply`. You supply a function and a collection of arguments, and `apply` will call that function with the arguments, returning the result. Armed with `apply` we can get the job done like this:
 
```clojure
(apply the-function args) ; (the-function args0 args1 args2 ...)
```

The `apply` function is particularly useful for converting from one kind of value to another. Thus, if you have a vector like this:

```clojure
(def v ["The number " 2 " best selling " "book."])
```


you can use the combination of `apply` and `str` to turn it into a string:

```clojure
;; More or less the same as:
;; (str "The number " 2 " best selling " "book.")
(apply str v)
```

or `apply` and `list` to turn it into a list:

```clojure
;; More or less the same as:
;; (list "The number " 2 " best selling " "book.")
(apply list v)
```

and then back into a vector:


```clojure
(apply vector (apply list v))
```

Another incredibly useful function is `partial`. It’s called `partial` because it partially fills in the arguments for an existing function, producing a new function of fewer arguments in the process. For example, Clojure includes a function called `inc` that adds one to the number you pass in, so that `(inc 1)` gives you `2` and `(inc 41)` is `42`. It’s easy enough to cook up your own version of `inc`:

```clojure
(defn my-inc [n] (+ 1 n))
```

But consider that `my-inc` is simply filling in the first argument of `+` with `1`.  Which is exactly the kind of thing that `partial` does:

```clojure
(def my-inc (partial + 1))
```

Returning to our book example, we can use `partial` to rework and simplify our cheapness-discriminating functions:

```clojure
(defn cheaper-than [max-price book]
  (when (<= (:price book) max-price)
    book))

(def real-cheap? (partial cheaper-than 1.00))
(def kind-of-cheap? (partial cheaper-than 1.99))
(def marginally-cheap? (partial cheaper-than 5.99))
```

Each call to `partial` there is giving us back a new function that—when called—calls `cheaper-than` with one of the prices as the first argument.


Another handy function-producing function that comes packaged with Clojure is `complement`. With `complement` every day is opposite day. `complement` wraps the function that you supply with a call to `not`, producing a new function that is, well, the complement of the original. For example, earlier we wrote `adventure?`, which could tell adventure books from those of other genres:

```clojure
(defn adventure? [book]
  (when (= (:genre book) :adventure)
    book))
```

But what if we needed a function that looked for nonadventure books?  Clearly we could write it by hand:

```clojure
(defn not-adventure? [book] (not (adventure? book)))
```


But we did say we were trying to get out of the hand-coding business, so instead we turn to `complement`:

```clojure
(def not-adventure? (complement adventure?))
```

As I say, `complement` produces a function that returns the truthy negation of the function that you pass to `complement`.

One more example of a function-generating function is `every-pred`. It combines predicate functions into a single function that "ands" them all together. With `every-pred` we can dispense with our home-grown `both-f`:

```clojure
(def cheap-horror? (every-pred cheap? horror?))
```


Even better, `every-pred` will take any number of arguments, so that this:

```clojure
(def cheap-horror-possession?
  (every-pred
    cheap?
    horror?
    (fn [book] (= (:title book) "Possession"))))
```

will do exactly what you want it to do.



