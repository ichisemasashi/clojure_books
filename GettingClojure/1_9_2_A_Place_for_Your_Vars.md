
### A Place for Your Vars

In the last chapter we saw how def creates vars—vars that represent the binding between a symbol and a value. What we skipped over in that chapter is how vars are organized. But really it’s very simple. As illustrated in the "figure on page 96", vars live in "namespaces".

Conceptually, a Clojure namespace is just a big lookup table of vars, indexed by their symbols. And since you can have as many namespaces in your program as you want, each namespace itself has a unique name. At any given moment there is one special namespace, called the current namespace. So if you do this:

![fig_1_9_2_001](img/1_9_2_001.png)

namespace/examples.clj
```clojure
(def discount-rate 0.15)
```

it’s the current namespace that gets updated with a var associating `discount-rate` with `0.15`. If you mention `discount-rate` a little later in your code, Clojure will consult the current namespace to come up with `0.15`.

When it boots up, Clojure creates a fresh namespace for you, called `user`, and makes it the current namespace.

Thus every `def` and `defn` that we have evaluated so far—with the example of our file-based project back in "Hello, Clojure"—has gone straight into the `user` namespace.

While `user` is the default namespace, it’s certainly not your only namespace choice. As I say, you can create as many namespaces as you need, and the easiest way to create a new namespace is with `ns`:


```clojure
(ns pricing)
``` 

Just feed `ns` the name of your new namespace, no quoting required. Conveniently, `ns` not only creates the new namespace, but also makes it the current namespace. Thus this:

```clojure
(ns pricing)
(def discount-rate 0.15)
(defn discount-price [book]
  (* (- 1.0 discount-rate) (:price book)))
```

creates the `pricing` namespace and adds the `discount-rate` and `discount-price` vars to it.

If you supply `ns` with the name of an existing namespace, it will skip the creation and simply switch the current namespace to the existing namespace.
If, for example, we switch from `pricing` to `user`:

```clojure
(ns user)
```

and then back to `pricing`:

```clojure
;; Back to the pricing namespace.
(ns pricing)
```

we discover that `discount-price` is still alive and well:

```clojure
(println (discount-price {:title "Emma" :price 9.99}))
```


By default, the vars inside of one namespace are completely separate from the vars in another. Thus I can have as many functions or plain values called `discount-price` as I want, as long as I keep them in separate namespaces. That raises the question of how to get at the vars defined in one namespace from a different namespace. For example, what happens if we’re in the `user` namespace, as here:

```clojure
(ns user)
;; How do I get at discount-price?
```

and we want to call `discount-price?`

The simplest solution to the "I need something from another namespace" issue is to use a "fully qualified symbol". A fully qualified symbol is just a long version of a symbol, one that includes the namespace. To write a fully qualified symbol you start with the namespace, follow it with a slash, and follow that with the symbol name. So `discount-price` becomes `pricing/discount-price`:

```clojure
(ns user)
;; I can get at discount-price in pricing like this!
(println (pricing/discount-price {:title "Emma" :price 9.99}))
```

Again, namespaces are simple: they’re just a place for your stuff, with their own names. And if you need something from a namespace, all you have to do is qualify the symbol with the namespace name.



