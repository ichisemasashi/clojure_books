
### A Universal Interface

Getting at the contents of a sequence is easy—and familiar. You just call `first` to get the lead-off element:

```clojure
;; Returns "Emma".
(first (seq '("Emma" "Oliver Twist" "Robinson Crusoe")))
```

and `rest` to get everything except the first element:

```clojure
;; Returns the sequence ("Oliver Twist" "Robinson Crusoe")
(rest (seq '("Emma" "Oliver Twist" "Robinson Crusoe")))
```

Alternatively, you use `next` to get at the "all but the first" sequence. The difference between `next` and `rest` is that while `rest` of an empty sequence is an empty sequence, `next` of an empty sequence is `nil`. While most of the time rest works fine, occasionally `nil` comes in handy.

You can also add a new element to the front of your sequence with `cons`:

```clojure
(cons "Emma" (seq '("Oliver Twist" "Robinson Crusoe")))
```

And that’s it. Once you have a sequence, the only things you can do with it are get the first element with `first`, get the other ones with `next` or `rest`, and slap a new element on the front with `cons`.

Armed with all this, implementing `my-count` is reasonably straightforward:

```clojure
(defn my-count [col]
  (let [the-seq (seq col)]
    (loop [n 0 s the-seq]
      (if (seq s)
        (recur (inc n) (rest s))
        n))))
```

The first thing `my-count` does is turn the collection into a sequence (in the `let`).

The rest is just a `loop` that runs through the sequence—using `rest`—counting as it goes. Notice there is no special-case code in `my-count` for vectors or maps or sets. Once we have the sequence we don’t need to worry about which kind of collection we have.

The only remotely tricky thing going on in `my-count` is the way we determine when we’ve run out of items to count. We call seq on our sequence. If `seq` returns `nil` we know that we’re out of items, since `seq` of an empty sequence is `nil`.


> [!NOTE]
>
> **Watch Those nils!**
>
> If you’re thinking that `my-count` would be clearer if we simply checked whether `(first s)` was `nil`, consider trying to count the items in `[nil nil nil]`.


Aside from the thrill of understanding how `count` works, there is a very practical reason for diving into all of this sequence talk. While functions like `count` and `first` can manage to keep their involvement with sequences quiet, the same is not true of functions like `rest`, `next`, and `cons`. These three functions always (aside from the occasional `nil`) return sequences:

```clojure
(rest [1 2 3])                         ; A sequence!
(rest {:fname "Jane" :lname "Austen"}) ; Another sequence.
(next {:fname "Jane" :lname "Austen"}) ; Yet another sequence.
(cons 0 [1 2 3])                       ; Still another.
(cons 0 #{1 2 3})                      ; And another.
```

And now we finally have the explanation for why `(rest some-vector)` gives you back a collection that prints with round parentheses: the `rest` function always returns a sequence.



