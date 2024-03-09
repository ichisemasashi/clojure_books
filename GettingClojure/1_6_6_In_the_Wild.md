
### In the Wild

And now we have the answer to the opening question of this chapter: the thing that makes Clojure a functional programming language is that you do basic things by writing functions and you do more sophisticated things by treating the functions as values—values that you can pass around and call and combine.

Possibly the best demonstration of the "functions are values" idea can be found inside the machinery of defn itself. `defn` is just a thin layer over `def` and `fn`. So when you define a new function with `defn`, perhaps this:

```clojure
(defn say-welcome [what]
  (println "Welcome to" what "!"))
```   
      
what gets evaluated is something like this:

```clojure
(def say-welcome
  (fn [what] (println "Welcome to" what "!")))
```

As the name suggests, `defn` is `def` plus `fn`.

If you are not used to the idea, functional values can seem a bit special and magical, the kind of technique you would use only in extreme circumstances.  Not so; in Clojure they are just part of the everyday programming landscape.

Take, for example, the mundane `update` function. As the name suggests, you use `update` to modify values, specifically the values inside of a map.

> [!NOTE]
> 
> **You Can’t Modify That Map**
> 
> To be precise, `update` produces a new map that’s a lot like the input map, only different. But I’m getting as tired of writing that as you are of reading it.


So if we wanted to record that we’ve sold another copy of a book, we might write this:

```clojure
;; Start with 1,000 copies sold.
(def book {:title "Emma" :copies 1000})
;; Now we have 1,001.
(def new-book (update book :copies inc))
```

As you can see, `update` takes three parameters: the map, the key whose value you want to update, and "a function" to do the updating. Your function will get called with the old value of the key (in this case `1000`) and the map you get back will be just like the old map, except that the key will have the result of evaluating the function.


If you happen to have nested maps, you can reach for the slightly less mundane `update-in` function, which works like `update` but will also let you drill down through several layers of maps using a pathlike vector of keys:

```clojure
(def by-author
  {:name "Jane Austen"
   :book {:title "Emma" :copies 1000}})
(def new-by-author (update-in by-author [:book :copies] inc))
```

But to see how much you can do with functional values, look no further than [Ring](https://github.com/ring-clojure/ring), the popular Clojure library that helps you build web applications.

To build a web application with Ring you first need to utter the proper incantation to load Ring (we’ll talk about `require` in "Chapter 9, Namespaces, on page 95"):


```clojure
(ns ring-example.core
  (:require [ring.adapter.jetty :as jetty]))
```

Then you create a function that takes in an HTTP request—in the form of a map—and returns a response, also in the form of a map, like this:

```clojure
(defn handler [request]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body "Hello from your web application!"})
```

Having written your function, you now need to tell Ring that "this" is the function Ring should look to when a web request comes in. You can do that by passing the `handler` function to Ring’s `run-jetty` function, which kicks off a simple web server called Jetty:

```clojure
(defn -main []
  (jetty/run-jetty handler {:port 8080}))
```

And now your handler function will get called for requests on port 8080.

Aside from plain handlers, Ring applications also commonly use "middleware".  Middleware are functions that take a handler function as a parameter and return a new handler function. Ring programmers use middleware to layer additional features onto their handlers. For example, we might define a middleware function that logs the response:

```clojure
(defn log-value
  "Log the message and the value. Returns the value."
  [msg value]
  (println msg value)
  value)
(defn wrap-logging
  "Return a function that logs the response."
  [msg handler]
  (fn [request]
    (log-value msg (handler request))))
```

and a second handler to specify the content type:

```clojure
(defn wrap-content-type
  "Return a function that sets the response content type."
  [handler content-type]
  (fn [request]
    (assoc-in
      (handler request)
      [:headers "Content-Type"]
      content-type)))
```

As I say, middleware functions take in a handler—a function—and return another handler. The new handler typically runs the old handler while adding its own goodness along the way. Our first middleware function, `wrap-logging`, runs the handler function passed to it, prints the response, and then returns the response. The second middleware function does something more interesting: it adds a header (for the content type) to the response.

> [!NOTE]
>
> **Assoc-in?**
>
> You may have noticed that the content-type handler in the example uses a function called `assoc-in`. This function is a lot like `assoc` in that it adds a new key/value association to a map. The difference is that you pass `assoc-in` a vector of keys and it will go spelunking down through multiple levels of maps for you. To put it another way, in the same way that `update-in` is the multistory version of `update`, `assoc-in` is the multistory version of `assoc`.


Traditionally Ring applications call the final, fully wrapped handler the `app`, short for application. So this is how we set up our final `app` and kick off Ring:

```clojure
(defn handler [request]
  {:status 200
   :body "Hello from your web application!"})
(def app
  (wrap-logging
    "Final response:"
    (wrap-content-type handler "text/html")))
```


You can get a feeling for the power of the "functions as values" view of the world by noting that in the preceding example we’re logging the final response—that is, the response after `wrap-content-type` has had its say. But with a little rearranging we can log the response before the content type gets added:

```clojure
(def app
  (wrap-content-type
    (wrap-logging "Initial response:" handler)
    "text/html"))
```

or we can log both:


```clojure
(def app
  (wrap-logging
    "Final response:"
    (wrap-content-type
      (wrap-logging "Initial response:" handler)
      "text/html")))
```

This last bit of code is a great example of the power of functional programming.  It assembles four separate functions, three of them dynamically generated, into a working whole that is greater than the sum of its parts.



