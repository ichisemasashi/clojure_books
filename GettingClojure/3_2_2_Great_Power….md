
### Great Power … 

Let’s start with the basics: a thread is the little computing engine that breathes life into your program, turning code into action. In fact, every program that has ever run on the JVM was pushed along by a thread:

thread/examples.clj 
```clojure
(ns blottsbooks.threads)

(defn -main []
  (println "Coming to you live from the main thread!"))
```

In the Java world the thread that you get by default, the one that runs your main program, is called the main thread. Things only get interesting—or scary—when you start dealing with a second thread. Or a twenty-fifth thread.

To see this in action in Clojure we can resort to some simple Java interop: there is an aptly named Java class called `Thread` that will take a Clojure function and run it in a separate thread when you call the `start` method:

```clojure
;; Make a thread.
(defn do-something-in-a-thread []
  (println "Hello from the thread.")
  (println "Good bye from the thread."))
(def the-thread (Thread. do-something-in-a-thread))
;; And run it.
(.start the-thread)
```

While this example illustrates the basic mechanics of threads, it doesn’t really show the thread magic in action. You kick off the thread and the messages print. So what?

> [!NOTE]
>
> **Threads and Functions**
>
> To be more precise, the Java `Thread` class takes any object that implements the `Runnable` interface. Conveniently, Clojure functions implement `Runnable`.


One way to experience the magic is to create a thread that takes a long time to run. And we can do that by inserting a `Thread/sleep` in our function, so that it pauses for a few seconds:

```clojure
;; Print two messages with a three second pause in the middle.
(defn do-something-else []
  (println "Hello from the thread.")
  (Thread/sleep 3000)
  (println "Good bye from the thread."))

(.start (Thread. do-something-else))
```

In the example we’ve created a second thread of execution. This second thread prints a message, pauses, and then prints a second message.

So if you run this code in a REPL, the call to `.start` will return immediately, probably either just before or just after you see the output from the first `println`. There will be a pause and then you will see the second message. But since the call to `.start` does return immediately, you are free to do other things in the REPL while you’re waiting for the second message to appear. And that is the magic of threads.

In more practical terms, if you happen to have two jobs that need doing, you can decide to do them one after the other:

```clojure
;; Do the first thing then the second.
(do-the-first-job)
(do-the-second-job)
;; Go on with the rest of the program...
```

Or do one in a separate thread while you work on the other in the current thread:

```clojure
;; Do the first job in another thread.
(.start (Thread. do-the-first-job))
;; Immediately start on the second job.
(do-the-second-job)
;; Go on with the rest of the program (but keep reading!)...
```

Or spin off threads to do both jobs:

```clojure
(.start (Thread. do-the-first-job))
(.start (Thread. do-the-second-job))
;; Do something else...
```

And you can go off and do a third thing while the two threads are humming away.


