
### Variations on the Theme

In addition to the plain vanilla version of let that we’ve looked at so far, Clojure comes packaged with a couple of handy variations. The most commonly used of these is probably `if-let`. As you might guess from the name, `if-let` is an `if` and a `let` rolled into one. To see `if-let` in action, imagine that we decide to represent anonymous books with our now-familiar book map, sans the `:author` key:
    
```clojure
(def anonymous-book
  {:title "Sir Gawain and the Green Knight"})
(def with-author
  {:title "Once and Future King" :author "White"})
```

Now imagine we needed to write a function that will return the uppercase version of the author’s name, or `nil` if there is no author. The twist is that we need to avoid computing the uppercase version of `nil`, which will blow up with an exception. Given that, we might do something like this:

```clojure
(defn uppercase-author [book]
  (let [author (:author book)]
    (if author
      (.toUpperCase author))))
```

That will work, but we can say it a bit more succinctly with `if-let`:

```clojure
(defn uppercase-author [book]
  (if-let [author (:author book)]
    (.toUpperCase author)))
```

In essence, `if-let` takes a single binding and uses the value bound—in the example, the author’s name—as the condition of an `if`. Like a plain `if`, `if-let` will take a second expression, for the else case:

```clojure
(defn uppercase-author [book]
  (if-let [author (:author book)]
    (.toUpperCase author)
    "ANONYMOUS"))
```

And if you think it would make more sense to call it `let-if`, well, me too.

Unsurprisingly, there is also a `when-let` which does about what you would expect:


```clojure
(defn uppercase-author [book]
  (when-let [author (:author book)]
    (.toUpperCase author)))
```

There really is nothing terribly deep about `if-let` and `when-let`: They are just the kinds of things that grow out of the observation that people frequently combine `let` with `if` and `when`.



