
### Varying Your Vars

If vars are all about providing the global, stable environment for your code, you might wonder why vars are mutable. After all, Clojure loves immutability.  But we can `def` and `re-def` our vars with wild abandon. The answer is as simple as it is pragmatic: mutable vars make for more productive Clojure programmers. Most Clojure programming is done in some form of REPL or other. So while developing, we might start out by creating a couple of vars:

```clojure
user=> (def PI 3.14)
#'user/PI
user=> (defn compute-area [diameter]
#_=>     (* PI diameter diameter))
#'user/compute-area
```

and then realize that we need more precision:

```clojure
user=> (def PI 3.14159)
#'user/PI
```
  
and we also got the calculation wrong:
  
```clojure
user=> (defn compute-area [diameter]
#_=>     (* PI radius (/ diameter 2.0)))
#'user/compute-area
```

While your code is under development, mutable vars are a gift from heaven.

Things are different in production. The vars in a production program are just as mutable as those in development, but "you should avoid changing them". In production code you should `def` (and `defn`) things and let them be.

> [!NOTE]
>
> **Changing State?**
>
> So what’s a programmer to do if you have some state that you need to model and that state changes over time? The longer answer starts with the advice that you use atoms or refs or agents. The shorter answer is to read "Chapter 18, State, on page 215".


Well, mostly you should leave your vars alone. There are times when it’s handy to be able to temporarily change the value bound in your vars. Imagine, for example, that you write a simple logging function that uses a var to turn the actual output off and on:


```clojure
;; First cut at debugging, needs some work.
(def debug-enabled false)

(defn debug [msg]
  (if debug-enabled
    (println msg)))
```

But how do you turn the logging on without violating the Clojure prime directive of "no defs in a function?"

It’s for situations like this that Clojure gives you `binding`. Syntactically a `binding` expression looks a lot like `let`: you supply `binding` with a vector containing pairs of symbols and values, along with one or more expressions that make up the body of the binding. The `binding` expression will temporarily set the vars corresponding to the symbols with the supplied values as it evaluates the expressions:


```clojure
(binding [debug-enabled true]
  (debug "Calling that darned function")
  (some-troublesome-function-that-needs-logging)
  (debug "Back from that darned function"))
```


In the example, `debug-enabled` gets set to true while the calls to `debug` and `some-troublesome-function` are evaluated, so that we will actually see output from the `debug` function. Note that any function called by `some-troublesome-function` will also see the temporary value of `debug-enabled`, and so on down the call stack.

There is one other wrinkle to `binding`: Any var that we use in `binding` needs to be declared as "dynamic", like this:

```clojure
;; Make debug-enabled a dynamic var.
(def ^:dynamic debug-enabled false)
```


The `^:dynamic` adds a bit of metadata—which we’ll talk about in "Chapter 19, Read and Eval, on page 229"—to the `debug-enabled` var. For now just accept it as the incantation you need to do to enable you to use `binding`. Finally, there is a Clojure convention for naming dynamic vars. The convention is that dynamic vars should begin and end with *. So our final exercise in debugging is as follows:

```clojure
(def ^:dynamic *debug-enabled* false)

(defn debug [msg]
  (if *debug-enabled*
    (println msg)))

(binding [*debug-enabled* true]
  (debug "Calling that darned function")
  (some-troublesome-function-that-needs-logging)
  (debug "Back from that darned function"))
```


By surrounding your dynamic vars with asterisks—charmingly referred to as "earmuffs"—you can tell at a glance which vars are usable inside of `binding` and which are not. Note that since being dynamic entails a bit more overhead, you should only hang the `^:dynamic` tag on vars that really need it.


