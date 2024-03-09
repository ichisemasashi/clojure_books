
### Staying Out of Trouble

Possibly the biggest danger lurking inside of `clojure.spec` is related to mistyping a keyword while registering a spec. For example, if you do this you might think you are (among other things) declaring that the value associated with `:title` in a book map needs to be a string:

```clojure
(s/def ::author string?)
(s/def ::titlo string?)
(s/def ::copies pos-int?)
(s/def ::book
  (s/keys :req-un [::title ::author ::copies]))
;; Register a spec for the find-by-title function.
(s/fdef find-by-title
  :args (s/cat :title ::title
               :inventory ::inventory))
```

But take a closer look and you will see that we misspelled `::title` when defining the spec. This means that while books are required to have titles, since there is no spec called `::title` the value can be anything.

The other thing to keep firmly in mind about specs is that when we talk about a global `clojure.spec` registry, we’re talking about global in the sense of "available to other code running in this JVM". If your system consists of a number of cooperating Clojure processes, you need to ensure that you register any specs that you intend to use in each JVM. In this sense you can think of specs as akin to functions and values you bind with def: they need to be loaded before you can use them.


