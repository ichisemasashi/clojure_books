
### Staying Out of Trouble

The good news is that Clojure comes equipped with a large assortment of functions to do interesting things with sequences—so many that we can only scratch the surface in this chapter. There’s everything from `butlast` (give me all but the last element) to `zipmap` (build a map from two sequences). The bad news is that until you’re familiar with what’s available you’ll tend to reinvent the wheel. In particular, if you find yourself processing a sequence one item at a time, perhaps with `loop` and `recur`, like this:

```clojure
(defn total-sales [books] 
  "Total up book sales. Books maps must have :sales key."
  (loop [books books total 0]
    (if (empty? books)
      total
      (recur (next books) 
             (+ total (:sales (first books)))))))
``` 

consider that there is probably an easier way:

```clojure 
(defn total-sales [books] (apply + (map :sales books)))
```

The other thing to keep in mind is that when you turn a specialized collection into a sequence you end up with a generic listlike thing that no longer has any of its specialized talents. Most notably, if you turn a map into a sequence it loses that rapid "key to value" superpower that makes maps so useful:

```clojure
(def maze-runner {:title "The Maze Runner" :author "Dashner"})
; Gives you back "Dashner"
(:author maze-runner)
; But this give you a nil - a seq is not a map!
(:author (seq maze-runner))
```

And it’s not just explicit calls to `seq` you need to watch for, but also all those library functions that quietly return seqs, like this:

```clojure
(:author (rest maze-runner)) ; Also nil: rest returns a seq.
```

Happily, there are functions that "do not" return sequences, most notably `conj`.  Recall that, like `cons`, `conj` is in the business of adding new items to collections:

```clojure
;; A *vector* ending with "Jaws".
(conj ["Emma" "1984" "The Maze Runner"] "Jaws")
;; A *list* starting with "Jaws".
(conj '("Emma" "1984" "The Maze Runner") "Jaws")
```

The difference is that `conj` pays attention to the type of collection you pass in and, critically, `conj` will give you back the same flavor of collection that you pass it. That’s why `conj` can do the "new element goes at the front of lists but the back of vectors" thing. It has special-case code for each collection type. By contrast, the cons function just calls `seq` on your collection and proceeds to slap the new item on the front of the resulting sequence:

```clojure
;; A *seq* starting with "Jaws".
(cons "Jaws" ["Emma" "1984" "The Maze Runner"])
;; A *seq* starting with "Jaws".
(cons "Jaws" '("Emma" "1984" "The Maze Runner"))
```

None of this means sequences are bad or that you should always fight the natural drift toward them. On the contrary, having a universal abstraction that allows you to work with vectors and sets and lists without constantly worrying about which you have is incredibly useful. But you do need to be mindful of when you have the actual thing and when you have the sequence.


