
### In the Wild

Our old friend the built-in `=` function is a great example of the function-related goodies we’ve been looking at in this chapter:

```clojure
;; Code edited a bit for clarity.
(defn =
  "Equality. Returns true if x equals y, false if not. Same as
  Java x.equals(y) except it also works for nil, and compares
  numbers and collections in a type-independent manner.
  Clojure's immutable data structures define equals()
  (and thus =) as a value, not an identity, comparison."
  ([x] true)
  ([x y] (clojure.lang.Util/equiv x y))
  ([x y & more]
    (if (clojure.lang.Util/equiv x y)
      (if (next more)
        (recur y (first more) (next more))
        (clojure.lang.Util/equiv y (first more)))
      false)))
```

The `=` function takes any number of arguments and returns `true` if they are all equal. Fortunately, `=` can turn to the lower-level `clojure.lang.Util/equiv` function to do most of the work. The catch is that `equiv` can only compare two values at a time.

Notice how the code breaks the job up into three arities. The single-argument arity is easy enough: evaluate `(= anything)` and you will always get back `true`. The two-argument arity is also pretty straightforward: just call `clojure.lang.Util/equiv`.

It’s the third arity, the one that deals with more than two arguments, where things get interesting. In this case the basic flow is to compare the first two or three arguments with `clojure.lang.Util/equiv` and to resort to `recur` if there are arguments left over.


> [!NOTE]
>
> **Simple Equality?**
>
> This whole two- or three-argument dance in `=` appears to be there to improve performance. We could build a perfectly rational version of `=` that simply compares the first two arguments and resorts to recur if there are more than two.


The ClojureScript source code contains a lovely example of a multimethod in `to-url`. Here’s a somewhat simplified version of `to-url`:

```clojure
(defmulti to-url class)
(defmethod to-url File [f] (.toURL (.toURI f)))
(defmethod to-url URL [url] url)
(defmethod to-url String [s] (to-url (io/file s)))
```

Like our `normalize-book` example, `to-url` is trying to bring order to a chaotic world by converting various URL-like things into a single data structure. Unlike the book example, `to-url` uses the built-in `class` function, which will return the underlying type of the value as its dispatch function. Essentially what we have here is a rough approximation of the class-based polymorphism that you find in many object-oriented programming languages, implemented in a handful of lines of code.


