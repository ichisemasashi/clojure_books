
### Agents
 
Returning to our book-inventory example, let’s imagine that we have a new requirement: we need to notify the corporate inventory system of each change in the inventory. Happily, someone else has written a function called `notify-inventory-change` that takes care of all the notification details. We just need to call it at the right times.

So rolling back to our original, atom-based code, our first stab at this is to just add a call to `notify-inventory-change` in the inventory-update function:

```clojure
(def by-title (atom {})) 
(defn add-book [{title :title :as book}]
  (swap!
    by-title
    (fn [by-title-map]
      (notify-inventory-change :add book) 
      (assoc by-title-map title book))))
;; Similar logic in del-book.
```

Life is good … until we start getting complaints from corporate about redundant notifications.
  
The problem is that we’ve forgotten one of the key aspects of atom update functions: they may get called several times in the course of resolving an update conflict. And that means that atom update functions are bad places for code that generates side effects, which is exactly what we’re doing with `notify-inventory-change`. Since refs use the same "call it until there are no conflicts" update strategy, they are unlikely to be of any help here either.

What we need is the third Clojure mutable value container, the agent. Here’s our original inventory manager—sans the notification—recast to use an agent:

```clojure
(ns inventory)
(def by-title (agent {}))
(defn add-book [{title :title :as book}]
  (send
    by-title
    (fn [by-title-map]
      (assoc by-title-map title book))))
;; Similar logic in del-book.
```

Like atoms, agents are stand-alone value containers. And as with atoms and refs, you get at the value in an agent with an `@` or `deref`.

On the surface, updating an agent is similar to updating an atom, except that the magic update function is called `send`. But it’s the same "change via a function" pattern we see with atoms and refs.

Agents and atoms part company in where and especially when the update happens. While the atomic `swap!` is synchronous—once the call to `swap!` is done, you know that your atom has been updated—`send` is asynchronous.  Behind the scenes every agent has a queue of functions associated with it.  When you call `send`, your update function gets added to the end of the queue.  Sometime later a background thread will pop your function off of the queue and execute it, updating the value in the agent in the process.

The very visible implication of the agent-update strategy is that `send` returns immediately after it puts the update function in the queue. Thus it’s not only possible but likely that `send` will return before the update completes:

```clojure
;; Queue up and add book request.
(add-book {:title "War and Peace" :copies 25})
;; At this point the agent may or may not have been updated.
```

The less visible—but critical—implication of the timing of agent updates is that the update function will get called exactly once, which is just what we need with our external inventory manager. So here is the agent-based `add-book` with the notification mixed back in:

```clojure
(defn add-book [{title :title :as book}]
  (send
    by-title
    (fn [by-title-map]
      (notify-inventory-change :add book)
      (assoc by-title-map title book))))
```

And we have no more redundant notifications.


