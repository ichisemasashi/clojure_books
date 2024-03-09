
### Arguments with Wild Abandon

Multi-arity functions are fine for functions like `greet`, functions that might take one or two or some other definite number of arguments. But what if you want to write a function that can deal with a completely arbitrary number of arguments? The good news is that you can get this done with the strategic placement of an & in your argument list.

Here, for example, is a function that will take any number of arguments and print them all out:

```clojure
(defn print-any-args [& args]
  (println "My arguments are:" args))
```

When someone calls `print-any-args`, the arguments show up in the args parameter—the one after the ampersand—"as a collection". So if you call `print-any-args` like this:

```clojure
(print-any-args 7 true nil)
```

you will see this:

```
My arguments are as follows: (7 true nil)
```

In the same spirit, here’s a function that returns its first argument:

```clojure
(defn first-argument [& args]
  (first args))
```


Even better, you can have ordinary arguments before the `&`, so that we can rewrite `first-argument` like this:

```clojure
(defn new-first-argument [x & args] x)
```

Functions that take advantage of the magic of the `&` are called "varargs" or "variadic" functions. Note the key syntactical difference between variadic functions and the multi-arity functions that we looked at earlier: multi-arity functions devote a separate body to each argument set while variadic functions (the ones with the `&`) have a single function body.


