    
### In the Wild

A great way to get a feel for working with multiple threads in Clojure is to look at the [Compojure library](https://github.com/weavejester/compojure).  Compojure helps you route the requests that come into your web application to a suitable response. Here, for example, is a simple placeholder web application for our book store, built with Compojure:

```clojure
(ns storefront.handler
  (:require [compojure.core :refer :all]
            [compojure.handler :as handler]))

(defroutes main-routes
  (GET "/" [] "Welcome to Blotts Books!!")
  (GET "/book" [title author]
       (str "Sorry " title " By " author " is not available.")))

(def app (handler/site main-routes)) 
```

The key bit of code here is the call to `defroutes`, which allows you to associate a route—`/book`, for example—with the code to handle requests that come in on that route. If you peek inside the Compojure source code, you can find the bits behind `defroutes`:

```clojure 
(defmacro defroutes 
  "Define a Ring handler function from a sequence of routes. The name may
  optionally be followed by a docstring and metadata map."
  [name & routes]
  (let [[name routes] (macro/name-with-attributes name routes)]
    `(def ~name (routes ~@routes))))
```

and GET: 

```clojure
(defmacro GET "Generate a `GET` route."
  [path args & body]
  (compile-route :get path args body))
```

and the `compile-route` function called by GET:

```clojure
(defn compile-route
  "Compile a route in the form `(method path bindings & body)` into a function.
  Used to create custom route macros."
  [method path bindings body]
  `(make-route
    ~method
    ~(prepare-route path)
    (fn [request#]
      (let-request [~bindings request#] ~@body))))
```

There are Clojure features in this code that we haven’t covered yet. In particular, we’ll get to those odd backquotes and tildes as well as `defmacro` in "Chapter 20, Macros, on page 241".

The first thing to note is that all of this code, from our trivial book-store application down to the deep plumping of Compojure, needs to work in a multithreaded environment. JVM-based web servers tend to spin off all sorts of threads in order to handle large numbers of simultaneous requests. Thus web applications and libraries like Compojure need to be OK with running inside of any number of threads. So while neither our application nor Compojure is spinning off any new threads, the threads are out there.

The second thing to note is how this code, from top to bottom, is completely devoid of any "Oh, gee, I’m working in a multithreaded application!" special pleading. It’s certainly possible to write Clojure code that will fail utterly in the presence of multiple threads. Just carelessly write to a file or modify a shared mutable Java object or simply suck up the available CPU cycles. But Clojure, with its emphasis on functional programming and immutable data structures, does make it a bit easier to write thread-friendly code.

You can find a great example of the use of futures in the `clojure.core/pmap` function. The idea behind `pmap` is to farm out the work that might otherwise be done with `map` into a number of threads, in parallel—hence the name.

From the outside, `pmap` is identical to `map`:

```clojure
(pmap some-computationally-intensive-f a-collection)
```

While the real implementation of pmap is complicated by practical questions like "Just how many processors does this machine have?", a basic implementation that captures the spirit of the original is a simple application of `future`:

```clojure
(defn toy-pmap [f coll]
  (let [futures (doall (map #(future (f %)) coll))]
    (map deref futures)))
```

Start by mapping each value in the collection into a future, which will eventually contain the mapped value. Then map over the futures, dereferencing them back to the mapped value.

The only twist here is the interaction between the "start working on it now so that we have the result later" nature of futures and the very lazy nature of `map`.  Recall that `map` will actually not compute anything until you start looking at the elements of the lazy sequence that it returns. But in `toy-pmap` we want to create the futures so that they can get started pre-computing the results. To get this all to happen, we wrap the call to the first `map` in a `doall`. We do this to ensure that we get an early start on the futures, at the cost of making `toy-pmap` eager—don’t use `toy-pmap` on an infinite sequence.

> [!NOTE]
>
> **Industrial-Strength pmap**
>
> Another complication that the real `pmap` deals with revolves around being just lazy enough to work with large, possibly unbounded, sequences while at the same time being eager enough to keep the futures working just a bit ahead of the game.


One thing that you can glean from `toy-pmap` is that there is quite of bit of overhead involved: Think about creating all those futures along with two invocations of `map`. This is also true of the real `pmap`, which means that `pmap` is likely to be slower than `map` unless the function you are passing into `pmap` is doing some intense computing.

