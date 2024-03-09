
#### A Value with a Future

This pattern of firing off a computation in another thread and then getting the result back via a promise is so common that Clojure provides a prepackaged version of it in the form of a future. You can think of a future as a promise that brings along its own thread. Creating a future could not be easier. You just supply `future` with one or more expressions:

```clojure
(def revenue-future
  (future (apply + (map :revenue inventory))))
```

That’s it. Sometime after you create your future, Clojure will—behind the scenes—evaluate the function in a separate thread and set the value of the future to the result.

From the programmer’s point of view, a future works like a promise that magically gets loaded with a value. You can use `@` or `deref` to get the result:

```clojure
(println "The total revenue is " @revenue-future)
```

As with a promise, dereferencing a future will block if the future is not quite done being computed.


