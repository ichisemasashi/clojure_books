
### Packages

So far we’ve been using the fully qualified classnames for files: `java.io.File` and `java.awt.Rectangle`. Since that can be a bit of a mouthful, Clojure provides `import`.  In much the same way that you can use `require/:as` to create syntactical shortcuts for the denizens of your Clojure namespaces, you can use `import` to do the same with Java classes. In the REPL you can use the stand-alone form of `import`:

```clojure
;; In the REPL.
(import java.io.File)
``` 

If you are writing a `.clj` file you should include the import in the `ns` at the top:

```clojure
(ns read-authors
  (:import java.io.File))
```

Note that there is no quoting needed in either form. However you do it, once you have imported a class you can refer to the class without its package name:

```clojure
(def authors (File. "authors.txt"))
```

If you happen to need a number of classes from the same Java package, you can roll all the imports into a single expression. Thus if we need the `InputStream` class—which also lives in `java.io`—we could say this:

```clojure 
;; Do this in a .clj file:
(ns read-authors 
  (:import (java.io File InputStream)))
```

or this:

```clojure
;; In the REPL.
(import '(java.io File InputStream))
```

Note that if you use the multiple-class `import` in the stand-alone `import`, you do (sigh) need a quote.

One Java package you will not need to import is `java.lang`. This package contains the very core Java classes, things like `java.lang.String` and `java.lang.Boolean`. Because it’s so central to life in the Java world, Clojure automatically imports `java.lang` into all namespaces. Thus if you evaluate `String` or `Boolean` in the REPL, like this:

```clojure
user=> String
java.lang.String
user=> Boolean
java.lang.Boolean
```

you will see they both originate in `java.lang`.




