
### … And Back to Clojure

To see how we can get at the wonders of Java from Clojure, let’s reach for every programmer’s favorite abstraction, the file. Java comes packaged with a handy class for representing files, namely `java.io.File`. According to the documentation for `java.io.File`, we can create an instance by passing in a string, the path to the file. To create a new instance of a Java class using Clojure’s interoperation facilities—interop for short—we use the classname with a dot on the end in the lead-off spot in the expression:
 
interop/examples.clj
```clojure
(def authors (java.io.File. "authors.txt"))
```

The preceding code will create a new instance of `java.io.File`, calling the constructor along the way, and return that instance—which represents an operating-system file called `authors.txt`—back to our Clojure code.

Once we have an instance in hand we can start calling its methods. For example, just having an instance of `java.io.File` doesn’t guarantee that the file actually exists in the file system. To find out if the file `authors.txt` is really there, we can use the exists method:

```clojure
(if (.exists authors)
  (println "Our authors file is there.")
  (println "Our authors file is missing."))
```

Similarly, we can find out if the file is readable with the canRead method:

```clojure
(if (.canRead authors)
  (println "We can read it!"))
```


And—assuming we have the proper operating-system authority—we can even change the permissions of the underlying file:

```clojure
(.setReadable authors true)
```

As you can see, to call a method on a Java instance you stick a dot on the front of the method name—so that `canRead` becomes `.canRead`—and use the result like a function, passing the instance as the first argument, followed by any additional arguments.

You can also access your object’s public fields from Clojure. `File` instances don’t have any public fields, but another built-in class, `java.awt.Rectangle`, does.  As you might guess from the name, `java.awt.Rectangle` represents a rectangular area on your screen—AWT being the original Java GUI library. If we create a new 10-by-20 rectangle sitting at the origin:

```clojure
(def rect (java.awt.Rectangle. 0 0 10 20))
```

we could then reach into its public fields:

```clojure
(println "Width:" (.-width rect))
(println "Height:" (.-height rect))
```

Yes, the syntax is a little odd: `(.-<field> <instance>)`. Note the dot and dash in front of the field name.



