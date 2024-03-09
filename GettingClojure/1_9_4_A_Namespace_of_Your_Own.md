  
### A Namespace of Your Own

Now that you’ve seen how to create new namespaces in the REPL and how to get namespaces loaded with `require`, we’re ready to take the final step: defining a namespace of our own in a real `.clj` file in a directory project.
Let’s start by creating a new Clojure application. Recall from "Hello, Clojure" that we can use Leiningen to create a new Clojure project like this:

```bash
$ lein new app blottsbooks 
```
 
Run those commands, and you will end up with a fully functional, if skeletal, Clojure project. For our purposes, the key bit of the project is a single source file that you’ll find at `src/blottsbooks/core.clj`.

Inside of that file you’ll find the following:

hello/blottsbooks-1/src/blottsbooks/core.clj
```clojure 
(ns blottsbooks.core
  (:gen-class)) 

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))
```

So far this is the same ground we covered back in "Hello, Clojure", but now the `ns` at the top of the file should make a bit more sense: we’re setting up a new namespace called `blottsbooks.core`.


There are a couple of things to watch as you build file-based namespaces. The first and most important is the correspondence between the namespace name —`blottsbooks.core` in the example—and the name of the file—`blottsbooks/core.clj`. This correspondence is neither an accident nor optional. In order for tools like `require` to work, Clojure assumes that it can do a simple transformation of a namespace name and come up with the name of the file harboring the code for the namespace. This transformation is about as simple as it comes: take the namespace name, convert any periods to directory-separating slashes, slap a `.clj` on the end, and voilà: `blottsbooks.core` is found in `blottsbooks/core.clj`.


> [!NOTE]
>
> **Class Paths**
>
> Along with the namespace-to-file-name transformation, Clojure also relies on the Java class path—essentially a list of places that the JVM looks for code—to help it locate namespaces. This is how Clojure knows to look in the `src` directory, and how it manages to locate the built-in Clojure library code. More on this in "Interoperating with Java".

Thus if we wanted to add a second namespace to our `blottsbooks` project (a namespace to hold our pricing code), we might create a file called `src/blottsbooks/pricing.clj`. Given that file name, the namespace name must be `blottsbooks.pricing`:

namespace/blottsbooks-1/src/blottsbooks/pricing.clj
```clojure
(ns blottsbooks.pricing)

(def discount-rate 0.15)

(defn discount-price [book]
  (- (:price book)
     (* discount-rate (:price book))))
```


If you happen to have any dashes in your namespace name—perhaps it’s called `blotts-books.current-pricing`—then the dashes get converted to underscores in the file name: `blotts_books/current_pricing.clj`.

The other thing you should keep in mind as you create files full of namespaces is that it is possible to fold `require` expressions into the `ns` expression. So if we wanted to use the `discount-price` function from `blottsbooks.pricing` in `blottsbooks.core`, we could do it like this:

namespace/blottsbooks-1/src/blottsbooks/core.clj
```clojure
(ns blottsbooks.core
  (:require blottsbooks.pricing)
  (:gen-class))

(defn -main []
  (println
    (blottsbooks.pricing/discount-price
      {:title "Emma" :price 9.99})))
```

Generally Clojure programmers prefer to use the stand-alone `require` when they’re working in the REPL—it’s just convenient to be able to `require` in namespaces as you go—and the `ns` version when writing source files. You should too, but you should also be aware that the syntax of the two forms of `require` is maddeningly different. In the stand-alone version it’s `require`, a symbol.

In the `ns` version it’s `:require`, a keyword. In the stand-alone version you must quote the argument. In the `ns` version you must not quote the argument.


