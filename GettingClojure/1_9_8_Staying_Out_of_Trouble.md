
### Staying Out of Trouble

There is nothing terribly complicated about the namespace. Each namespace is just a big name/value lookup table which itself has a name. Keep in mind that there’s no hierarchy of namespaces. Each namespace has a name and somewhere behind the scenes Clojure maintains a mapping between the namespace name, perhaps `clojure.core` or maybe `clojure.core.data`, and the actual namespace. But as far as Clojure is concerned there is no more of a relationship between `clojure.core.data` and `clojure.core` than there is between `clojure.core` and `blottsbooks.pricing`. In particular, Clojure does not look at `clojure.core.data` as somehow under `clojure.core`. You can call your namespace whatever you want, and names like `clojure.core` and `clojure.core.data` do help us humans make sense of the code. But as far as Clojure is concerned the names are arbitrary.

Loading namespaces is equally straightforward, especially in production, where you set up your namespace, perhaps `require` in some other namespaces, and leave it there. Life can get a little more complicated in development, where you frequently want to `require` in a namespace, modify it, and `require` it in again—and then do it all again. 

For example, imagine that you’re working in the REPL, trying out the `blotts-books.pricing` namespace.

```clojure
blottsbooks.core=> (require '[blottsbooks.pricing :as pricing])
nil 
blottsbooks.core=> (pricing/discount-price {:title "Emma" :price 20.0})
17.0
```

and you decide to rename the `discount-price` function to the more descriptive `compute-discount-price`. You start up your favorite text editor and modify `src/blotts-books/pricing.clj` to look like this:

namespace/blottsbooks-2/src/blottsbooks/pricing.clj
```clojure
(ns blottsbooks.pricing)

(def discount-rate 0.15)

(defn compute-discount-price [book]
  (- (:price book)
     (* discount-rate (:price book))))
```

Having done that, you return to the REPL and re-require the namespace:

```clojure
blottsbooks.core=> (require '[blottsbooks.pricing :as pricing])
nil
```

Sadly, it doesn’t work:

```clojure
blottsbooks.core=> (pricing/compute-discount-price
                     {:title "Emma" :price 20.0})
CompilerException java.lang.RuntimeException:
  No such var: pricing/compute-discount-price, ...
```

The problem is that `require` knows that the `blottsbooks.pricing` namespace is already loaded and will quietly refuse to load it a second time. This is what you want in production: `require` in a namespace any number of times, and it only gets loaded once. But this "load once" policy gets in the way during development.  Fortunately, the solution is easy: just add the `:reload` keyword to your `require`:

```clojure
blottsbooks.core=> (require :reload '[blottsbooks.pricing :as pricing])
nil
blottsbooks.core=> (pricing/compute-discount-price
                     {:title "Emma" :price 20.0})
17.0
```


Now `require` will reload your code every time, existing namespace or not.

Keep in mind that the `:reload` option does not clear out the existing contents of the namespace when it reloads it. That means that when you renamed `discount-price` to `compute-discount-price` in the source and then reloaded the namespace, you did indeed define a new function called `compute-discount-price`. But the old `discount-price` is still wandering around zombielike in your REPL. Mostly this isn’t a problem, but if it is, you can always deploy `ns-unmap` to remove it:

```clojure
blottsbooks.core=> (ns-unmap 'blottsbooks.pricing 'discount-price)
```

One other unfortunate twist with `:reload` is that sometimes you have code you don’t want to execute every time the namespace gets reloaded. Perhaps you’re calling a function that takes a long time to run or has some side effect:

```clojure
;; I really only want this to happen once.
(def some-value (function-with-side-effects))
```

Again, Clojure comes to the rescue, this time with `defonce`, which you use in place of `def`:

```clojure
;; Just set some-value the first time.
(defonce some-value (function-with-side-effects))
```

As the name suggests, `defonce` binds the symbol to the value exactly once—the first time. Thus, in the example the `function-with-side-effects` will only get evaluated once, the first time you load the namespace. After that, you can `:reload` until your keyboard wears out and `some-value` will not get redefined.


If you change your mind you can always use `ns-unmap` to persuade `defonce` that it’s time to actually do something:

```clojure
(ns-unmap *ns* 'some-value)
```

And the circle is complete.



