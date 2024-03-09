
### Function Specs

So far we’ve been treating `clojure.spec` as a general-purpose library for ensuring that our data looks the way it should. And so it is. But think about how useful it would be during development to have a way to automatically do some spec matching at critical points in your code. For example, wouldn’t it be great if you could write a spec for the arguments of a function and have that spec checked each time the function is called?

We could do this by taking advantage of function pre and post conditions:

```clojure
;; Register a handy spec: An inventory is a collection of books.
(s/def :inventory.core/inventory
  (s/coll-of ::book))

(defn find-by-title
  [title inventory]
  {:pre [(s/valid? ::title title)
         (s/valid? ::inventory inventory)]}
  (some #(when (= (:title %) title) %) inventory))
```

This isn’t bad, but there is a much more concise and maintainable way to get the same effect. The `clojure.spec` library has a feature that lets you describe what should go into and come out of a function "separately from the function". The trick is to use `clojure.spec/fdef`:

```clojure
;; Define the function.
(defn find-by-title
  [title inventory]
  (some #(when (= (:title %) title) %) inventory))
;; Register a spec for the find-by-title function.
(s/fdef find-by-title
  :args (s/cat :title ::title
               :inventory ::inventory))
```

As you can see from the code, `fdef` takes a function and a spec that matches the arguments for that function. Since there can be a significant performance penalty involved in checking arguments, by default the checking is disabled.

To turn the argument checking on we to need require yet another namespace:

```clojure
(require '[clojure.spec.test.alpha :as st])
```

and explicitly instrument our function:

```clojure
(st/instrument 'inventory.core/find-by-title)
```

Once we have all that in place, we have `clojure.spec` checking on our function, so that if you pass a vector of strings instead of the expected book maps, as follows:

```clojure
(find-by-title "Emma" ["Emma" "2001" "Jaws"])
```

you will see this:

```
ExceptionInfo Call to #'inventory/find-by-title
did not conform to spec:
In: [1 0] val: "Emma" fails spec:
:inventory/book at: [:args :inventory] predicate: map?
...
```

Since spec-based argument checking can slow things down, it’s most useful during development and testing.
