
### Lazy Friends

Interestingly, `take` is itself lazy. It does indeed return the first N items of the sequence you pass to it, but it doesn’t actually grab anything off of the sequence until you ask for it. Thus this:

```clojure
(def many-nums (take 1000000000 (iterate inc 1)))
```

doesn’t create a billion-item collection immediately. Instead `take` knows that it should limit itself to the first billion items, but like the lazy sod that it is, `take` waits to be asked before it does anything. So this:

```clojure
(println (take 20 (take 1000000000 (iterate inc 1))))
```

will print the first 20 integers and is only microscopically slower at doing so than the version sans the inner `take`.

`take` isn’t alone in being surprisingly lazy. A lot of the sequence functions that we’ve been using are lazy. For example, our old friend `map` is lazy. We can, for example, use `map` to create a lazy sequence of all the even numbers and then just grab the first 20:

```clojure
(def evens (map #(* 2 %) (iterate inc 1)))
(take 20 evens)
```

Also lazy is `interleave`, which you will recall weaves sequences together. Since it’s lazy, we can safely interleave infinite sequences. We might, for instance, want to number our even numbers:

```clojure
;; Returns (1 2 2 4 3 6 4 8 5 10)
(take 10 (interleave numbers evens))
```

An expression like `(take 10 (interleave numbers evens))` is more like an assembly line ready to start spewing out numbers than an actual list of numbers. Production only kicks off when you start asking for the contents of the resulting sequence.

