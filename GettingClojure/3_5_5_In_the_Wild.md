
### In the Wild

If it seems like macros might be one of those advanced programming features, something that you aren’t likely to have a lot of contact with until you get really good at the language, well, no. Certainly, you can—and probably should—do a lot of Clojure programming without writing any macros. But there’s no avoiding macros because much of the familiar core of Clojure is actually made of macros.
 
Here, for example, is a somewhat cut-down version of Clojure’s own [`when`](https://github.com/clojure/clojure/blob/master/src/clj/clojure/core.clj):

```clojure
(defmacro when
  [test & body]
  (list 'if test (cons 'do body)))
```

And here’s the rather more complicated `cond`:

```clojure
(defmacro cond
  [& clauses]
    (when clauses
      (list 'if (first clauses)
            (if (next clauses)
                (second clauses)
                (throw (IllegalArgumentException.
                         "cond requires an even number of forms")))
            (cons 'clojure.core/cond (next (next clauses))))))
```

Even something as fundamental as `and` is implemented as a macro. Here again is a slightly simplified version:

```clojure
(defmacro and
  ([] true)
  ([x] x)
  ([x & next]
    `(let [and# ~x]
      (if and# (and ~@next) and#))))
```

Note that `when` and `cond` use our original "build the code by hand" technique, while `and` uses syntax quoting.

In fact, `and` uses a couple of features of syntax quoting we haven’t seen yet.  Note how the symbol bound in the `let` is called `and#`. This is another name-confusion-avoidance feature provided by syntax quoting. If you have a local symbol, perhaps a function parameter or a `let` bound symbol in a syntax-quoted expression, you need to add a `#` to the end of the symbol. The `#` is a signal to syntax quoting to replace your symbol with a generated, unique symbol—in `and` the generated symbol would be something like `and__1330__auto__`.  Years of bitter macro-writing experience has shown that you need to watch out for local symbol name collisions.

You can spot the other syntax-quoting feature used by `and` in the form of the very odd-looking `~@next`. To see what this is all about, let’s take a shot at implementing a simplified version of `defn`. Our defn will take the function name, its argument vector, and any number of expressions to make up the body of the function. Here’s our first cut:

```clojure
(defmacro our-defn [name args & body]
  `(def ~name (fn ~args ~body)))
```

It’s simple, but unfortunately not quite right. The trouble is this expression:

```clojure
(our-defn add2 [a b] (+ a b)))
```

gets expanded to something like this:

```clojure
(def add2 (fn [a b] ((+ a b))))
```

Note the extra set of parentheses around the `(+ a b)`. This does make sense.  When we wrote `~body` we asked to have the value of `body`—a collection—inserted right there, and that’s exactly what we got, parentheses and all. But what we really want is to have the contents of `body` spliced into the expression, sans parentheses. And that’s what `~@body` will do. So here’s our updated macro:

```clojure
(defmacro our-defn [name args & body]
  `(def ~name (fn ~args ~@body)))
```

And now we can use it to define new functions:

```clojure
(our-defn add2 [a b] (+ a b))
(add2 2 2) ; Give us 4!
```

And now we know that `defn` is just a mashup of `def` and `fn`.


