    
### In the Wild

As you might expect, it’s easy to find very familiar-looking examples of `if`, `when`, `cond`, and `case`. Here, for instance, are a few lines pulled from Leiningen:

```clojure 
(when (real-directory? f)
  (doseq [child (.listFiles f)] 
    (delete-file-recursively child silently)))
```

We may not follow the details, but the general idea of this code is not hard to work out: when `f` is a real directory—that is, not a file or a symbolic link—then delete it and all of its contents.
  
Here’s another snippet, also from Leiningen:

```clojure
(if (.isDirectory entry)
  (.mkdirs f)
  (do (.mkdirs (.getParentFile f))
      (io/copy (.getInputStream jar entry) f)))
```

If entry is a directory, then do this; otherwise do something else.

> [!NOTE]
> 
> **Dot What?**
> 
> The reason for the funny un-Clojurelike function names such as `.isDirectory` and `.mkdirs` is that this code is calling into lower-level Java libraries. More on this in "Chapter 16, Interoperating with Java, on page 189".



Surprisingly, the if (or when) this, then do that use case of `if` is not all that common in Clojure. What you find instead is `if`, `when`, and `cond` used to compute a value. Take this bit of code, again lifted from Leiningen:

```clojure
(if (vector? task) task [task])
```

The interesting thing about this expression is that it doesn’t do anything.  Instead it evaluates to a value, specifically the original `task`, or `task` wrapped in a vector. Thus we might bind the result to a symbol:

```clojure
(def task-vector (if (vector? task) task [task]))
```

or maybe embed it in a function:


```clojure
(defn ensure-task-is-a-vector [task]
  (if (vector? task) task [task]))
```

When used this way—which it commonly is—Clojure’s if is a lot like the ternary expressions that you find in Java or C++: If this is truthy I want this value; otherwise I want this other value.

You can find a great example of `cond` in the open source library [Korma](https://github.com/korma/Korma).  Korma (tagline: "Tasty SQL for Clojure"; you’ve got to give them points for wit) tries to help Clojure programmers deal with vagaries of SQL and contains the following bit of code:

```clojure
(defn str-value [v]
  (cond
    (map? v) (map-val v)
    (keyword? v) (field-str v)
    (nil? v) "NULL"
    (coll? v) (coll-str v)
    :else (parameterize v)))
```

The job of `str-value` is to take a Clojure value and turn it into a string that is acceptable to SQL. The function goes about its duties by asking what kind of value it has: Is it a map? Well, then hand it off to `map-val`. Is it a keyword?  Then off to `field-str`, and so on.

Notice that Korma uses a `cond` and not a `case` since this is not a test for specific values but for whole classes of values. On the other hand, elsewhere in Korma we find this case expression:

```clojure
(case (:type query)
  :insert (update-in query [:values] #(map prep-fn %))
  :update (update-in query [:set-fields] prep-fn)
  query)
```

Here we’re testing against a pair of known values: If the query type is `:insert`, then return this. If the type is `:update`, return something else. Otherwise just return the query unchanged.

