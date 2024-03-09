
### Staying Out of Trouble
    
One of the surprising things about Clojure functions is that you can mix and match the variadic `&` into a multi-arity function "if you are careful". Here’s us being careful:
    
```clojure
(defn one-two-or-more
  ([a] (println "One arg:" a))
  ([a b] (println "Two args:" a b))
  ([a b & more] (println "More than two:" a b more)))
```

We just need to keep in mind that Clojure is sharp enough not to let us define a multi-arity function with overlapping arguments. For example, if we had written that last function as

```clojure 
;; Oh no!
(defn one-two-or-more
  ([a] (println "One arg:" a))
  ([a b] (println "Two args:" a b))
  ([& more] (println "More than two:" more)))
```
  
then we wouldn’t get past the Clojure compiler. The problem is that it’s unclear which arity should get evaluated when you call the function with two parameters.

You should also be careful not to confuse a "more than one expression in the body" function, like this:


```clojure
(defn chatty-average
  ([a b]
  (println "chatty-average function called with 2 arguments")
  (println "** first argument:" a)
  (println "** second argument:" b)
  (/ (+ a b) 2.0)))
```

with a multi-arity function:

```clojure
(defn chatty-multi-average
  ([a b]
    (println "chatty-average function called with 2 arguments")
    (/ (+ a b) 2.0))
  ([a b c]
    (println "chatty-average function called with 3 arguments")
    (/ (+ a b c) 3.0)))
```

The key is to look for the parameters, which will tell you which flavor of function you have.


Finally, keep in mind when you’re defining variadic functions that `&` is just an ordinary one-character symbol that happens to have a special meaning in the context of defining function arguments. That means this:

```clojure
(defn print-any-args [& args]
  (println "My arguments are:" args))
```

is a function that will take any number of arguments, while this:

```clojure
(defn print-any-args [&args]
  (println "My arguments are:" args))
```

will not compile. Why? Look again and note the lack of whitespace between the `&` and the `args`, which means we’re trying to define a function with a single argument called `&args`. Our function then tries to use the unbound symbol `args` and blam!—we have a compiler error. What we meant to write was an `&`, then some space, then the catchall argument: `[& args]`.


