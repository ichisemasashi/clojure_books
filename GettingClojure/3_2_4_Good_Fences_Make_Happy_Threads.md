
### Good Fences Make Happy Threads

Fortunately, all is not lost. As I say, race conditions happen when you have more than one thread trying to modify the same data structure. So our first line of defense is to make sure that our threads don’t accidentally change a shared resource. And this brings us back to Clojure’s immutable data structures.

Recall that once you make a Clojure vector or list or map or set you cannot change it: you can only make modified copies. In general, immutable data structures are a huge help in crafting understandable programs. But mix in some threads, and the immutability becomes almost indispensable. Take this book inventory for example:
  
```clojure
(def inventory [{:title "Emma" :sold 51 :revenue 255}
                {:title "2001" :sold 17 :revenue 170}
                ;; Lots and lots of books...
                ])
```

Imagine you started several threads to process the inventory in different ways:

```clojure
(.start (Thread. (sum-copies-sold inventory)))
(.start (Thread. (sum-revenue inventory)))
```

Since we haven’t seen the code behind `sum-copies-sold` and `sum-revenue`, we have no idea what those functions do. They might be well behaved and do exactly what their names suggest. Or these functions might be pathological messes.

What we do know is our pair of functions cannot meddle with the contents of the vector that we pass to them. We know this because the vector and all of its component parts are immutable. And that single bit of knowledge is a giant first step toward writing multithreaded programs that actually work: as long as we stick to Clojure’s everyday immutable data structures—and avoid doing dumb things like embedding `def`s in our functions—there is zero risk of accidental thread collision.

In the same spirit, we also don’t need to worry about the dynamic vars we met in "Chapter 8, Def, Symbols, and Vars, on page 85". Clojure keeps any dynamic binding of vars safely separated by thread. Here’s an example:

```clojure
(def ^:dynamic *favorite-book* "Oliver Twist")
(def thread-1
  (Thread.
    #(binding [*favorite-book* "2001"]
      (println "My favorite book is" *favorite-book*))))
(def thread-2
  (Thread.
    #(binding [*favorite-book* "Emma"]
      (println "My favorite book is" *favorite-book*))))

(.start thread-1)
(.start thread-2)
```

This will always result in `thread-1` announcing it loves the science-fiction classic `2001` while `thread-2` will claim loyalty to `Emma`. This is true no matter which thread runs first or if the threads are running at exactly the same time.

> [!NOTE]
>
> **Thread Local**
>
> If you’re familiar with Java threading, you will have probably worked out that Clojure’s dynamic vars live in thread local storage.
