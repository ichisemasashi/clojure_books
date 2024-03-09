
### An Eval of Your Own 

The wonderful thing about `eval` is that it’s simultaneously a gateway to the entire Clojure programming language and a very ordinary function. That `eval` is just an ordinary function raises an interesting question: can we—as an intellectual exercise—implement our own version of `eval`?

Remarkably, we can. We’ve already seen that if you hand `eval` a string or a keyword or a number, you get the same string, keyword, or number back, unchanged. So here’s a start on our own toy `eval` function:

```clojure
(defn reval [expr] 
  (cond
    (string? expr) expr
    (keyword? expr) expr
    (number? expr) expr
    :else :completely-confused))
```

Note that the real `eval` throws an exception when you hand it something it doesn’t understand, but to keep the example simple we’ll return `:completely-confused`.

We can make the confusion less likely by handling symbols and vectors and lists. These are all a bit more complex, so let’s delegate them to separate
functions: 

```clojure
(defn reval [expr]
  (cond 
    (string? expr) expr
    (keyword? expr) expr
    (number? expr) expr
    (symbol? expr) (eval-symbol expr)
    (vector? expr) (eval-vector expr)
    (list? expr) (eval-list expr)
    :else :completely-confused))
```

Actually evaluating symbols isn’t too difficult: just look them up in the current namespace:

```clojure
(defn eval-symbol [expr]
  (.get (ns-resolve *ns* expr)))
```

Vectors are also straightforward. We just need to recursively evaluate the contents:

```clojure
(defn eval-vector [expr]
  (vec (map reval expr)))
```

Things only get interesting when we evaluate lists. First we need to evaluate the contents of the list in exactly the same way that we did with vectors. Once we’ve done that we just need to call the function, which we do with `apply`:

```clojure
(defn eval-list [expr]
  (let [evaled-items (map reval expr)
        f (first evaled-items)
        args (rest evaled-items)]
    (apply f args)))
```

We could go on, perhaps adding support for maps and `if` expressions and `ns` and so forth, but let’s pause here and take stock.

> [!NOTE]
>
> **Go!**
>
> I encourage you to go on and see how much Clojure you can implement. There is nothing like building your own to get a clearer idea of how the real thing works.


The first thing to note about our excursion into programming-language implementation is that we’re cheating. We’re relying on all the glories that Clojure provides to implement a slow, partial subset of Clojure. What we get out of `reval` is not a practical programming language but insight—insight into how Clojure works—all courtesy of the homoiconic power of the language.

Second, it’s important to keep in mind that since Clojure—real Clojure—is a compiled language, the details of the real `eval` are rather more complicated.  Instead of saying, "Oh, this is a list. Treat it as a function call", the real `eval` says, "Oh, this is a list. Generate some code to call the function and then run that code".

Nevertheless, building toy versions of `eval` is so much fun and so illuminating that it has been a cottage industry among programmers using LISP-based languages for decades. Welcome to the club.

