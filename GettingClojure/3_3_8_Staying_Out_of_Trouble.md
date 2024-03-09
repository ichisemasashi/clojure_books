  
### Staying Out of Trouble
    
Programmers new to Clojure sometimes get lost in expressions like this:

```clojure
(def title-atom (atom "Pride and Prejudice")) 
```

The thing to keep in mind is that this expression creates two value containers.  The first is a var, which gets bound to the name `title-atom`. The value of that var is another container, an atom. And inside the atom we find the string `"Pride and Prejudice"`.

If we `swap!` another value in, perhaps like this, the var doesn’t change at all:

```clojure
(swap! title-atom #(str % " and Zombies"))
```
  
It is still the same var, bound to the same name and pointing at the same atom. It’s the value "inside the atom" that gains some zombies.

Another thing to keep in mind is what happens should an update function fail. The good news is that atoms and refs are easy. If something goes horribly wrong in the function you pass `swap!` or inside of a `dosync`, then the whole update fails and an exception gets thrown.

The story with agents, with their "off in another thread" update strategy, is more complex. If an agent update function fails, then that update has no effect on the value of the agent:

```clojure
(def title-agent (agent "A Night to Remember"))
;; BOOM! You can't add 99 to a string!
(send title-agent + 99) 
```


But you will not see the exception back from the `send`—after all, the exception happened later, on a different thread. The agent will enter a failed state and will immediately reject any further `send` calls by raising the "exception that caused the original failure":

```clojure
(send title-agent #(str % " Forever"))
;; ClassCastException java.lang.String cannot be cast to java.lang.Number
```

This is actually helpful behavior, but it can be confusing if you aren’t expecting it.

Happily, you can check the state of your agent with `agent-error` and use `restart-agent` to get it back to normal:

```clojure
;; If the agent is dead then restart it with a new value
;; and clear any pending updates.
(if (agent-error title-agent)
  (restart-agent
    title-agent
    "Poseidon Adventure"
    :clear-actions true))
```

Finally, there will probably come a time when you will wish that your agents— or at least the threads behind them—would just go away. One of the annoyances with using agents is that they rely on a behind-the-scenes thread pool managed by Clojure. And, as we saw in the last chapter, the JVM is finicky about terminating when there are live threads still around. Fortunately, Clojure provides you with the `shutdown-agents` function to shut down the agents in the agent thread pool, allowing your JVM to exit:

```clojure
(defn -main []
  ;; Do some stuff with agents.
  ;; Shut down the threads that have been running the agent updates
  ;; so that the JVM will actually shut down.
  (shutdown-agents))
```

It’s always a great idea to call `shutdown-agents` just before the end of any program that uses agents.



