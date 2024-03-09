
### Sequences Without End

As we saw in the last chapter, the useful thing about sequences is that they are abstract. Instead of being a linked list or an array or some other definite kind of collection, a seq captures the idea of a sequential collection. A sequence is a thing that will give you the first value when you call `first`, and another sequence—representing the remaining contents—when you call `rest` or `next`.

That is pretty much it.

Now consider that sequences are defined in terms of function calls. You can get the lead item in your sequence by calling the `first` function and a sequence representing the remaining items by calling another function, either `next` or (more likely) `rest`. We’ve seen how we can take advantage of the function-driven nature of sequences to abstract away the differences between the various collection types: once you turn it into a sequence, you don’t care if you started with a list or a map or a vector. 

But our assumption has been that behind every sequence is some concrete data structure like a list or a vector. But nowhere in the sequence API is this a requirement: The rules just say that `first` needs to return the first thing and `rest` and `next` need to return another sequence. This flexibility brings up an interesting possibility: perhaps we could dispense with the collection and make up the values returned by `first` and `rest` and `next` on the fly.

For example, imagine we were testing our book-store software and we needed to create some test books filled with nonsense text. We decide to take our cue from Stephen King:

lazy/examples.clj
```clojure
(def jack "All work and no play makes Jack a dull boy.")
(def text [jack jack jack jack jack jack jack jack jack jack])
```

But repeatedly typing `jack` is both tedious and quite possibly not enough. What if we wanted 11 repetitions or 111 or 10,011? The typing is also unnecessary.  After all, the sequence we’re looking for is just the same value repeated over and over.


Wouldn’t it be nice if we could say, "Conjure up a sequence that is a boring repetition of this value?" It would indeed, and that’s why we have the `repeat` function. Using `repeat` is simultaneously simple and mind-blowing. The simple part is that to use repeat you just supply it with a value:

```clojure
;; Be careful with repeated-text in the REPL.
;; There's a surprise lurking...
(def repeated-text (repeat jack))
```

You will get a sequence populated with the value you supplied:

```clojure
;; Returns the "All work..." string.
(first repeated-text)
;; So does this.
(nth repeated-text 10)
;; And this.
(nth repeated-text 10202)
```

That brings us to the mind-blowing part: the sequence returned by `repeat` does not end. No matter how many items you ask for, they are always there. Fortunately, the sequences returned by `repeat` are also "lazy": they wait until they are asked before they generate anything. They can get away with this because sequences are defined in terms of what functions like `rest` and `next` return.

Now for a bit of terminology. A "lazy sequence" is one that waits to be asked before it generates its elements. An "unbounded sequence" is a lazy sequence that, in theory, can go for ever. Not all lazy sequences are unbounded—a three-element sequence can wait to generate its elements—but all unbounded sequences are lazy.

One function that is particularly handy for taming unbounded sequences is take, which you’ll recall returns the first N items of a sequence:

```clojure
;; Twenty dull boys.
(take 20 repeated-text)
```

Infinity is so much easier to deal with when you can cut it down to size.


