    
### In the Wild

So far we have limited ourselves to the built-in libraries that come packaged with Java. But Clojure’s interop features work just as well with third-party libraries. For example, you might be interested in using Google’s take on reading and writing JSON, the [Gson library](https://github.com/google/gson).

To get a feel for using Gson from Clojure, you might start by creating a new project:

```bash
$ lein new exploregson
Generating a project called `exploregson`...
$ cd exploregson
```

Next we need to tell Leiningen that our project depends on a particular version of the Gson library. So we add the Gson library and its latest version (at least it’s the latest version as I write this) to the `project.clj` dependencies. The syntax is exactly the same for Java libraries and Clojure libraries:

```clojure
(defproject exploregson "0.1.0-SNAPSHOT"
  :description "FIXME: write description" 
  :url "http://example.com/FIXME" 
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [com.google.code.gson/gson "2.8.0"]])
```

Next, start a REPL with `lein repl`. Looking over the Gson documentation, you see that there seems to be a central class, `com.google.gson.Gson`:

```clojure
user=> (import com.google.gson.Gson)
com.google.gson.Gson
user=> (def gson-obj (Gson.))
#'user/gson-obj
```

Once you have your `Gson` instance, it’s easy enough to turn Clojure values into JSON strings:

```clojure
user=> (.toJson gson-obj 44)
"44"
user=> (.toJson gson-obj {:title "1984" :author "Orwell"})
"{\":title\":\"1984\",\":author\":\"Orwell\"}"
```

And now you are off and interacting with Gson. While this kind of REPL-based exploration is not a substitute for reading the library documentation, it is a great way to get a feel for what the documentation means.

Aside from the built-in Java classes and third-party libraries like Gson, there is another giant pile of Java code lying around that is of at least academic interest to every Clojure programmer: Clojure itself.

At its most basic, Clojure is just a collection of Java classes. Even better, every Clojure value is just a reference to some Java object. So in the same way we can call Java methods on instances of `java.io.File`, we can call them on garden-variety Clojure values. For example, having peeked at the Clojure source code, I know the Java class behind vectors defines a method called `count`. So I can call it:

```clojure
;; Make a Clojure value which is also a Java object.
(def v [1 2 3])
;; Call a Java method on our Clojure value/Java object.
(.count v)
```


This means that we can explore Clojure itself, using Clojure.

In fact we have already done a bit of this. Recall back in "Chapter 8, Def, Symbols, and Vars, on page 85", when we were talking about digging into vars to get at their symbol and value:

```clojure
(def author "Dickens") ; Make a var.
(def the-var #'author) ; Grab the var.

(.get the-var) ; Pull the value out of the var: "Dickens"
(.-sym the-var) ; Pull the symbol out of the var: author
```

Armed with our new insights into Java interop, we now can see that the `(.get the-var)` expression is calling the `get` method on `the-var` while the second expression is accessing a public field called `sym`.

To take one more example, let’s look at the value we get back from the `cons` function:

```clojure
(def c (cons 99 [1 2 3]))
```

Recall that `cons` prepends a value to a sequence. We can use our old buddy `class` to discover the class of the value returned by `cons`:

```clojure
(class c) ; clojure.lang.Cons
```

and based on that we can go looking for the `Cons` Java code:

```java
final public class Cons extends ASeq implements Serializable {
    private final Object _first;
    private final ISeq _more;
    public Cons(IPersistentMap meta, Object _first, ISeq _more) {
        super(meta);
        this._first = _first;
        this._more = _more;
    }
    // Code omitted...
    public Object first() {
        return _first;
    }
    public ISeq more() {
        if (_more == null)
            return PersistentList.EMPTY;
        return _more;
    }
    // Lots of code omitted...
}
```

and then try them out:

```clojure
;; And call into the Java.
(.first c) ; 99
(.more c) ; (1 2 3)
```

Would I use `.first` this way in production code? Of course not. Is this a great way to get a feeling for how Clojure works? Absolutely!



