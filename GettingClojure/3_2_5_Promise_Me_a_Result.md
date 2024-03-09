
### Promise Me a Result

The separating effect of immutability and per-thread dynamic binding means that we don’t have to worry quite so much about one thread accidentally interfering with the operation of another, which is a great first step. But it’s only a first step. Having separated our threads for their own safety, we now need to figure out how to get them to work together. Sometimes this isn’t a problem. Some threads are born to silently do a job and silently expire:

```clojure
;; Delete a file in the background.
(.start (Thread. #(.delete (java.io.File. "temp-titles.txt"))))
```
  
But more often we want some kind of result back from the thread we just fired off. "How many copies did we sell?" And what was the revenue? Or simply, "Is this thread done?"

Let’s start with the last question. All we need here is a bit more Java interop, in the form of the join:

```clojure
(def del-thread (Thread. #(.delete (java.io.File. "temp-titles.txt"))))
(.start del-thread)
(.join del-thread)
```

Call `join`, and it will pause, returning only when the thread you pass to it has finished. The main downside of `join` is while it will tell you a thread is done, it won’t tell you anything about what the thread did: `join` always returns `nil`.

A more practical way to get a result out of a thread is to use a "promise". You can think of a promise as a sort of value trap. When you create a promise, you have set the trap but it’s empty:

```clojure
(def the-result (promise))
```

Once you have a promise you can put a value in it with the `deliver` function:

```clojure
(deliver the-result "Emma")
```

And BLAM! the trap snaps shut, grabbing the value. Keep in mind that you can only deliver a single value to a promise. Once the trap shuts on a value it’s trapped in the promise and there is no changing it.

To get at the value in your promise you can use the `deref` function:

```clojure
(println "The value in my promise is" (deref the-result))
```

Or you can use the completely equivalent syntactical shortcut of prepending an `@` to the promise:

```clojure
(println "The value in my promise is" @the-result)
```

This brings us to the punchline: if you try to dereference a promise with no value, `deref` (or `@`) will pause until there is a value. This means that promises are great for transferring a single value from one thread to another.

Let’s go back to our book-inventory example:

```clojure
(def inventory [{:title "Emma" :sold 51 :revenue 255}
                {:title "2001" :sold 17 :revenue 170}
                ;; Lots and lots of books...
                ])
(defn sum-copies-sold [inv]
  (apply + (map :sold inv)))
(defn sum-revenue [inv]
  (apply + (map :revenue inv)))
```

We can run the calculations in separate threads and use promises to communicate the results back to our original, default thread:

```clojure
(let [copies-promise (promise)
      revenue-promise (promise)]
  (.start (Thread. #(deliver copies-promise (sum-copies-sold inventory))))
  (.start (Thread. #(deliver revenue-promise (sum-revenue inventory))))
  ;; Do some other stuff in this thread...
  (println "The total number of books sold is" @copies-promise)
  (println "The total revenue is " @revenue-promise))
```

We can be serene in the knowledge that if the threads haven’t finished with their sums by the time we start printing, the main thread will simply wait until they are finished.



