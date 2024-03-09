
### Loading Namespaces

While namespaces are conceptually simple, especially if you’re working in the REPL, things do get a bit more complicated when you want to use namespaces that are stored in `.clj` files—either files you have written or those authored by others. The rub is that you need to ensure that Clojure knows to read the `.clj` file and compile it into a namespace that you can use. In short, you need to make sure the namespace you want to use is "loaded" before you try to use it.

For example, your Clojure installation comes equipped with a ready-made namespace called `clojure.data`. The `clojure.data` namespace—and the period is part of the name—contains a handy function called `diff`, which will compare two data structures, perhaps vectors, and tell you which values are only in the first, which are only in the second, and which are in both.

So if you had some vectors of book titles:

```clojure
user=> (def literature ["Emma" "Oliver Twist" "Possession"])
#'user/literature
user=> (def horror ["It" "Carry" "Possession"])
#'user/horror
```

and you wanted to compare them, you might try to use `clojure.data/diff`. Sadly, you’ll be disappointed:

```clojure
user=> (clojure.data/diff literature horror)
ClassNotFoundException clojure.data
  java.net.URLClassLoader.findClass (URLClassLoader.java:381)
```

The problem is that while `clojure.data` comes packaged with your Clojure installation, it isn’t loaded by default. To use `clojure.data`—and this is true of most of the namespaces you’ll use in your Clojure career—you need to tell Clojure to read and compile the code behind the namespace.


Fortunately, that’s not difficult. All you need is `require`:

```clojure
user=> (require 'clojure.data)
user=> (clojure.data/diff literature horror)
[["Emma" "Oliver Twist"] ["It" "Carrie"] [nil nil "Possession"]]
```

As you can see from the example, `require` takes the name of the namespace, which you will need to quote, and loads the code behind that namespace.

And once you’ve done the `require`, you can start using the contents of the namespace.


