

### A Global, Stable Place for Your Stuff

As we discovered all the way back in Chapter 1, `def` is about as simple as programming-language features come. You just hand it a symbol and a value:

def/examples.clj
```clojure
(def title "Emma")
```

And it binds the symbol to the value. We’ve also seen that in contrast to the very local and temporary `let`, you use `def` for longer-lasting, more stable name-to-value bindings. Let’s start with the "stable" part: the rule is that a binding created with `def` will hang around until either you change it or the program terminates. This makes def perfect for constants:

```clojure
;; Everyone's favorite universal constant.
(def PI 3.14)
;; Length of a standard book ID.
(def ISBN-LENGTH 13)
;; Company names are more or less constant.
(def COMPANY-NAME "Blotts Books")
```

The stable lifespan of `def` bindings also makes it a great tool for pulling the parts of your program together into a functioning whole. Thus we have `defn`, which is, as the name suggests, a functional mash-up of `def` and `fn`, so that this:

```clojure
(defn book-description [book]
  (str (:title book)
       " Written by "
       (:author book)))
```

is just a more convenient way of writing the following:

```clojure
(def book-description
  (fn [book]
    (str (:title book)
         " Written by "
         (:author book))))
```

The other advantage that `def` offers is that the bindings it creates are widely visible. Once defined, like this:

```clojure
;; Length of a standard book ID.
(def ISBN-LENGTH 13)
;; Before 2007 ISBNs were 10 characters long.
(def OLD-ISBN-LENGTH 10)
````

you can use the bindings that come out of `def` in other `def`s:

```clojure
(def isbn-lengths [OLD-ISBN-LENGTH ISBN-LENGTH])
```

and inside of functions:

```clojure
(defn valid-isbn [isbn]
  (or (= (count isbn) OLD-ISBN-LENGTH)
      (= (count isbn) ISBN-LENGTH)))
```

The rule is that once you’ve bound a symbol to a value with `def` it’s just there, part of the environment.

