
### Staying Out of Trouble

As I pointed out at the opening of this chapter, dealing with multiple threads is one of those "great power versus great responsibility" situations. Programming with multiple threads opens up the possibility that you can keep all your CPU cores busy doing productive work. But multiple threads also means dealing with the headaches—and dangers—that come with trying to do more than one thing at a time.

So far in this chapter we have walked through some of the obvious "threads fighting over resources" problems, but threads come with some other, less obvious dangers. For example, threads are expensive: creating a new thread takes a long time, and each thread takes up a reasonable chunk of memory.

Thus if you just want to get some background processing done, use a future, not a promise, since futures will deal with the issues of creating and disposing of threads. If you do need more control, you should consider using one of the thread-pool facilities supplied by Java. The best place to start is with the `java.util.concurrent.Executors` class, which provides a number of static methods for building thread pools. You can, for example, use it to create a fixed-size thread pool:
    
```clojure 
(import java.util.concurrent.Executors)
;; Create a pool of at most three threads.
(def fixed-pool (Executors/newFixedThreadPool 3))
```

Once you have your thread pool, you can throw work at it in the form of functions to be evaluated:

```clojure
(defn a-lot-of-work []
  (println "Simulating function that takes a long time.")
  (Thread/sleep 1000))
(defn even-more-work []
  (println "Simulating function that takes a long time.")
  (Thread/sleep 1000))

(.execute fixed-pool a-lot-of-work)
(.execute fixed-pool even-more-work)
```

The thread pool will execute those functions in one of the threads in the pool.  Even better, if you happen to throw more work at the pool than there are threads, the pool will queue the work and do it as the threads free up:

```clojure
;; Throw more jobs at the fixed-pool than it can handle...
(.execute fixed-pool even-more-work)
(.execute fixed-pool even-more-work)
(.execute fixed-pool even-more-work)
(.execute fixed-pool even-more-work)
(.execute fixed-pool even-more-work)
(.execute fixed-pool even-more-work)
;; ... and it will get it all done as quickly as it can.
```

A simple fixed-sized thread pool is not your only option. The `Executors` class provides methods to create thread pools with a variety of behaviors, everything from a pool that will execute your functions periodically to one that will attempt to keep all your CPU cores as busy as possible. In fact, behind the scenes, Clojure uses one of the thread-pool variants to manage the threads that drive futures.

The bottom line is that while the raw `Thread` class is conceptually simple—and therefore great for examples—it is also a low-level and reasonably blunt tool.  In real applications you are much better off using higher-level constructs like futures and thread pools than creating your own threads manually.

You should also be careful with operations like `join` and `deref`—operations that block the current thread. The problem with this:

```clojure
(deref revenue-promise)
```

or its syntactically sugared twin:

```clojure
@revenue-promise
```

is that if something prevents a value being delivered to `revenue-promise`, then those expressions will wait forever—or at least until someone kills the program.

In production code you should almost always supply a timeout, and a value to return if a timeout does occur:

```clojure
;; Wait 1/2 second (500 milliseconds) for the revenue.
;; Return :oh-snap on timeout.
(deref revenue-promise 500 :oh-snap)
```

Consistently using timeouts provides a sort of circuit breaker to guard against a cascade of endlessly waiting threads.

Finally, be aware that by default the JVM will refuse to stop if there are threads still running. So if you write something like this:

```clojure
(defn -main []
  (let [t (Thread. #((Thread/sleep 5000)))]
    (.start t))
  (println "Main thread is all done, but..."))
```

you have just built a program that will hang around for about five seconds.

> [!NOTE]
>
> **Sleeping Precision**
>
> Keep in mind that `Thread/sleep` only promises to pause your thread for at least the interval you requested. But it might pause for longer. Threads are hard.


There are a couple of ways to deal with this. The most straightforward is to make sure that your threads are all nicely dead and cleaned up before your program terminates.

Alternatively, you can mark your threads as daemons:

```clojure
(defn -main []
  (let [t (Thread. #((Thread/sleep 5000)))]
    (.setDaemon t true)
    (.start t))
  (println "Main thread is all done!"))
```


But be aware that the JVM will terminate daemon threads without warning when the main thread terminates.


