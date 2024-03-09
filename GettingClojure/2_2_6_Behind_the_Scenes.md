
### Behind the Scenes 

Now that we have a feeling for how lazy sequences work from the outside, let’s look at how they are built. The key tool for building a lazy sequence from scratch is the aptly named `lazy-seq`. The `lazy-seq` function is similar to `seq`. Like with `seq`, you give `lazy-seq` an expression and it will turn it into a sequence. So this:

```clojure
(lazy-seq [1 2 3])
```          

will give you the three-element sequence `(1 2 3)`. The difference is that `lazy-seq`, being lazy, will hold off on evaluating the expression until you actually start pulling things off the sequence that it returns. For example, if you wrapped your vector in a chatty function: 

```clojure
(defn chatty-vector []
  (println "Here we go!")
  [1 2 3])
```

and then made a lazy sequence out of it:

```clojure
;; No output when we do this.
(def s (lazy-seq (chatty-vector)))
```

you would not immediately see the output from the `println`. It’s only seen when you start expanding the sequence, perhaps by pulling off the first element:

```clojure
;; This will cause "Here we go!" to print.
(first s)
```


Under the hood, `lazy-seq` uses some macro magic (we’ll talk about macros in "Chapter 20, Macros, on page 241") to wrap its argument in an anonymous function, a function that only gets called when the time is right. And it’s this "delay until the last possible second" behavior that is the key to laziness.

To see how it works in practice, we can implement our own version of `repeat`.  Recall that the `repeat` function takes a value and produces an infinite sequence of that value repeated over and over. How would we write our own `repeat`?

Like this:

```clojure
;; Note that the real `repeat` has a couple of arities
;; that we won't bother to implement here.
(defn my-repeat [x]
  (cons x (lazy-seq (my-repeat x))))
```

The `my-repeat` function starts reasonably enough—it returns a sequence, manufactured with `cons`. Recall that `cons` takes a value and a sequence and returns a new sequence consisting of the value followed by the contents of the old sequence. In `my-repeat` we use `cons` to build a sequence consisting of the value passed in followed by the sequence created by—wait for it—a call to `my-repeat`. And that sequence starts with the value passed in, followed by another call to `repeat`, and so on. The magic that prevents this from flying off into recursive Neverland is the delaying action of `lazy-seq`. The only time those additional calls to `my-repeat` get fired is when you start accessing the resulting sequence. Lazy sequences are concise, marvelous, and recursively insane.

Now that we have the hang of it, it’s not hard to implement some of the more involved functions. Here, for example, is a perfectly serviceable version of `iterate`:

```clojure
(defn my-iterate [f x]
  (cons x (lazy-seq (my-iterate f (f x)))))
```

You start the sequence with the value passed in, followed by a new lazy sequence that starts with the function applied to the value, followed by the same again, ad infinitum.

Implementing a simple version of `map` is also not hard, and illustrates how to bring your lazy sequence to an end:

```clojure
(defn my-map [f col]
  (when-not (empty? col)
    (cons (f (first col))
             (lazy-seq (my-map f (rest col))))))
```

Like `my-repeat` and `my-iterate`, `my-map` will keep building as much of the sequence as it needs. The difference is that `my-map` is limited by the input collection.  When the `my-map` detects that there are no more elements in the input collection, it returns `nil`. This is the signal to `lazy-seq` that the end has arrived. Lazy sequences are indeed a bit mind-bending, so it’s not surprising that their implementations are, as well.



