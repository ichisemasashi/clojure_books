
### Registering Specs

So far in our examples we have been treating specs like the garden-variety Clojure values that they are. But consider how useful it would be to have a global registry, a place where you could announce, "When I’m talking about books I mean a map with `:title`, `:author`, and `:copies` keys". That way any bit of code anywhere can check to see if a value qualifies as a book according to the high standards of our book inventory system.

This is the motivation behind `clojure.spec/def`, not to be confused with our old buddy `clojure.core/def`! The idea of `clojure.spec/def` is to allow you to register your spec in a JVM-wide registry of specs any code can then use. For example, this:

```clojure
(s/def
  :inventory.core/book
  (s/keys
    :req-un
    [:inventory.core/title :inventory.core/author :inventory.core/copies]))
```

registers our simple book spec under the keyword `:inventory.core/book`.

Once a spec is registered, you can use the keyword as a spec:

```clojure
;; Validate a book against the registered spec.
(s/valid? :inventory.core/book {:title "Dracula" :author "Stoker" :copies 10})
```

The global registry is the reason we need all those namespace-laden keywords.  In a global registry we don’t want the spec for our books to collide with other book specs, perhaps entered by some accounting- (as in cook the books) or travel- (as in book a flight) related library.

Happily, we don’t have to constantly write out the fully qualified keyword.  Recall that `::book` is the same as `:inventory.core/book` when the current namespace is `inventory.core`. So if the current namespace is `inventory.core`, this will give us the same effect as our previous example:

```clojure
(s/def ::book (s/keys :req-un [::title ::author ::copies]))
```

