
### … And Great Responsibility

Unfortunately, along with the "do stuff at the same time" magic there is the inevitable "hair on fire" complication: without some kind of positive control mechanism, "we know nothing about how fast a thread will run". The implications of this ignorance are deep and pervasive. To see these implications in action we’re going to break the rules a bit and embed some `def` expressions in a couple of functions:

```clojure
(def fav-book "Jaws")
(defn make-emma-favorite [] (def fav-book "Emma"))
(defn make-2001-favorite [] (def fav-book "2001"))
``` 

As we saw in back in "Chapter 8, Def, Symbols, and Vars, on page 85", this is not a great idea: sticking a `def` inside of a function creates a more or less hidden side effect, making your program harder to understand. But let’s do it anyway, for the sake of the example.

Now if we run these two functions in sequence, like this:

```clojure
(make-emma-favorite) 
(make-2001-favorite)
```

it’s clear that `2001` will prevail in the "last function wins" contest. Alternatively, if we reverse the order, Emma will win the favorite-book contest.

Now imagine we spin these two functions off in threads:

```clojure 
;; Kick off the threads.
(.start (Thread. make-emma-favorite))
(.start (Thread. make-2001-favorite))
```


And wait a few seconds. … Now what’s our favorite book?

Without actually examining the value bound to `fav-book`, the only correct answer to this question is "We don’t know". We don’t know because we have no idea how fast the threads ran, relative to each other and relative to the main thread.  Threads are independent engines of computation, with the emphasis on independent.

We can certainly make a guess: probably each thread will do its thing very quickly. Probably the `2001` thread—which we started second—will run a bit behind the `Emma` thread and get the last word in. That’s a good guess, but it’s still a guess. It could have happened the other way around. It’s even possible that it will take both threads a long time to get going, in which case we might see Jaws as our favorite book. In just a few lines of code we have created Schrödinger’s var.

The technical term for this sort of situation is race condition. The effect of our code depends on which thread happens to run a bit faster or slower. Race conditions can occur anytime you have two or more threads making changes to a shared resource. In our somewhat contrived example, the threads shared a var. But real-world programs are full of references to data structures, database tables, file systems, and the like—a whole world of things that can be shared and changed and therefore fought over by multiple threads.


