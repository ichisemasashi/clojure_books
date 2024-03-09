
### In the Wild

We can see one of the more useful applications of lazy sequences in [nREPL](https://github.com/clojure/tools.nrepl), a library that enables you to create a client/server rendition of a REPL, where the client sends Clojure expressions off to a server, which evaluates them and sends back the results. Here’s the sequence:

```clojure
(repeatedly #(transport/recv transport timeout))
```

The `repeatedly` function is the function-driven equivalent of `repeat`. Instead of returning the same value over and over, `repeatedly` calls the function you pass in over and over, returning a lazy sequence of the resulting values. The `repeatedly` function is useful in those situations where you are dealing with side effects —presumably you will get something different back from the repeated calls to the function. That’s certainly true in the preceding code, where `transport/recv` reads one message after another from some sort of message stream.

The key thing here is that this code is converting a series of side effect–driven events—the messages showing up to be read—into a lazy sequence of values that can be then be processed further using the ordinary tools of Clojure programming.

Lazy sequences—and the functions that produce them—are common enough in Clojure that it is probably more productive to point out some functions that are not lazy, functions that will try to immediately realize any sequence you hand them. Obviously there is `count`; try to count the elements of a sequence, and you’re going to realize the whole thing, or try to do this:

```clojure
;; Say goodnight.
(count (iterate inc 0))
```

Similarly, it’s a bad idea to try to `sort` or `reduce` over an infinite sequence.


