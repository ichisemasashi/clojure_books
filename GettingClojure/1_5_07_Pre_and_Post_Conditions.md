
### Pre and Post Conditions

Aside from being convenient units of code, functions provide natural choke points where you can improve the reliability of your code by checking that the values passed to your function are what you expect. For example, if we had a function to publish a book and we wanted to make sure our book had a title before we did any publishing, we might write this:

```clojure
;; Publish a book using the (unseen) print-book
;; and ship-book functions.
(defn publish-book [book]
  (when-not (contains? book :title)
    (throw (ex-info "Books must contain :title" {:book book})))
  (print-book book)
  (ship-book book)) 
```

Happily, Clojure provides a shortcut for this sort of thing in the form of the `:pre` condition:

```clojure
(defn publish-book [book]
  {:pre [(:title book)]}
  (print-book book)
  (ship-book book))
```

To set up a `:pre` condition just add a map after the arguments—a map with a `:pre` key. The value should be a vector of expressions. You will get a runtime exception if any of the expressions turn out to be falsy when the function is called. So if we wanted to ensure that our books had both authors and titles, we could write this:


```clojure
(defn publish-book [book]
  {:pre [(:title book) (:author book)]}
  (print-book book)
  (ship-book book))
```

You can even take the checking one step further by specifying a `:post` condition, which lets you check on the value returned from the function. Thus we could write

```clojure
(defn publish-book [book]
  {:pre [(:title book) (:author book)]
   :post [(boolean? %)]}
  (print-book book)
  (ship-book book))
```

to ensure that the return value (which is ultimately going to come from `ship-book`) is a Boolean. Note that we use `%` to stand in for the return value in the `:post` conditions.

