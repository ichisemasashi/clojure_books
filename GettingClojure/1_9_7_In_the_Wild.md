                 
### In the Wild
  
When it comes to Clojure namespaces, there is one giant walking the land: `clojure.core`. The `clojure.core` namespace is where all of those fundamental, predefined functions—things like `println` and `str` and `count`—live. So why is it that we can just write `println` and `count` and not `clojure.core/println` and `clojure.core/count`? We rarely need the `clojure.core/` because just after it creates a new namespace, `ns` does the equivalent of this:

```clojure
(require '[clojure.core :refer :all]) 
```

The `:all` option is an even more dramatic version of the `:refer` that we saw earlier.
It pulls in "all" the bindings from the other namespace. That’s great for booting up a language environment, but perhaps not something you should use in everyday code.

If you are curious about what goodies come wrapped in `clojure.core`, you could read the documentation (in fact, you should read the documentation), but given what we’ve covered in this chapter, you can also use `ns-map` to reveal all the wonders hiding in `clojure.core`:

```clojure
(ns-map 'clojure.core)
```

Not only that, but you can also deduce that the `clojure.core` namespace lives in a file called `clojure/core.clj`. In fact, if you look at the Clojure source code, that is exactly [what you will find](https://github.com/clojure/clojure/blob/master/src/clj/clojure/core.clj).

This source file is full of all sorts of wonders, including the definition of `not`, here shown in a lightly edited form:

```clojure
(ns clojure.core) 
;; Lots of code omitted...
(defn not
  "Returns true if x is logical false, false otherwise."
  [x] (if x false true))
```

It also holds the definition of `not=`:

```clojure
(defn not=
  "Same as (not (= obj1 obj2))"
  ([x] false)
  ([x y] (not (= x y)))
  ([x y & more]
    (not (apply = x y more))))
```

A great way to explore your new programming language is to do exactly that: explore your new programming language, from the inside.

Of course, there is more to Clojure than just the bits that come built into the language. The Clojure world is a living, breathing technical ecosystem full of people writing and releasing code. Happily, getting to other people’s code from a Leiningen project only requires one additional step: you need to add a dependency in the Leiningen-generated `project.clj` file.

For example, if we wanted to use the Korma SQL library in our book-store application, we would just add it and its current version number—which we can get from the Korma project page—to the `:dependencies` value:

namespace/blottsbooks-2/project.clj
```clojure
(defproject blottsbooks "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [korma "0.4.0"]]
  :main ^:skip-aot blottsbooks.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
```

and restart our REPL:

```bash
$ lein repl
nREPL server started on port 54569 on host 127.0.0.1 - nrepl://127.0.0.1:54569
...
```

then `require` in the Korma namespace:

```clojure
blottsbooks.core=> (require '[korma.db :as db])
nil
```

We’re in business.


