
### Staying Out of Trouble

One thing to keep in mind as you dive into Java interop is that while we can use `.method` and `class/staticMethod` in place of functions, they are not functions.  They are, instead, special forms, more or less hardwired into Clojure. In particular, you cannot bind a method name:

```clojure
;; Nope. Nope. Nope.
(def count-method .count)
```

All is not lost, however: you can use the built-in `memfn` function to turn a method name into a function. For example, while `.exists` is not a function, `(memfn exists)` is. This can be useful when you need a function value. If, for instance, you had a collection of `File` instances:

```clojure
(def files [(File. "authors.txt") (File. "titles.txt")])
```

and you needed the parallel collection of Booleans indicating which files were there, you could say this:

```clojure
(map (memfn exists) files)
```

One thing you should not do is blindly wrap all of your interop forms in functions, either with `memfn` or with plain old `defn`. It can be hard to resist that inner call to write something like this:

```clojure
(defn file-exists?
  "Wrap the exists method cause I hate that dot."
  [f]
  (.exists f))
(defn readable?
  "Wrap the canRead method cause I hate camel case."
  [f]
  (.canRead f))
```

Resist you should: generally you only want to wrap calls into Java when the wrapper adds something. Otherwise just use interop like the native Clojure it is.

The final—and most important—thing you should keep firmly in mind as you work with Java objects is that many—not all, but many—Java objects are mutable. To pick one example, Java’s answer to Clojure’s vectors is a class called `java.util.Vector`.

It’s easy enough to work with them from Clojure. You just make one:

```clojure
(def jv-favorite-books (java.util.Vector.))
```

and then you can start adding elements to it:

```clojure
(.addElement jv-favorite-books "Emma")
(.addElement jv-favorite-books "Andromeda Strain")
(.addElement jv-favorite-books "2001")
```

And then you stop and realize that you are changing this Java vector in place.  First it’s an empty vector, then it has one element, then two, and then three.  This is the very thing that we gave up to get all the wonders of immutability in Clojure.

Don’t panic. But do avoid dealing with mutable objects if you can. For example, in the trivial preceding example, the solution is a simple "don’t do that". Just use a plain old Clojure vector: `["Emma" "Andromeda Strain" "2001"]`. If you do need to deal with mutable Java objects—perhaps you are getting them back from a Java API—try to turn them into something immutable if at all possible. For example, if we did have that mutable collection of books, we can easily turn it into a safely immutable Clojure vector with `vec`, which also accepts Java-style collections:


```clojure
;; Back in the immutable world!
(def thankfully-immutable-books (vec jv-favorite-books))
```

The main thing is to know when you’re dealing with a mutable object and work from there.


