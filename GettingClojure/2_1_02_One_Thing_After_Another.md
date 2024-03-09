
### One Thing After Another

Let’s start our exploration of sequences by asking a simple question: how does the "count" function work? Specifically, how does this one function manage to count the elements in a vector, the items in a list, the characters in a string, and the entries in a map? After all, we know that behind the scenes lists and vectors and maps and strings are all very different data structures, and if you want to count the elements of each one, somewhere there needs to be code to deal with the structural differences.

One possible design for this would be to write in special-case code for each collection type, maybe by using a multimethod. So if we were building our own count, we might write this:

sequences/examples.clj
```clojure 
;; Is this how count is implemented?
(defn flavor [x]
  (cond
    (list? x) :list
    (vector? x) :vector
    (set? x) :set
    (map? x) :map
    (string? x) :string
    :else :unknown)) 
(defmulti my-count flavor)
(defmethod my-count :list [x] (list-specific-count x))
(defmethod my-count :vector [x] (vector-specific-count x))
;; And so on...
```

Alternatively, we can imagine that `my-count` has access to a number of generic wrappers, one per collection type. There would be a wrapper for lists, and one for vectors, and so on. Internally each kind of wrapper would know how to deal with its own collection type, but from the outside the different wrapper types would present the same, generic interface. The `count` function could then start by encasing the collection in the correct flavor of wrapper and then work with the uniform interface of the wrapper from there.


And now for the big reveal: "Clojure opts for the wrapper method". Clojure calls its generic collection wrappers "sequences". Under the hood there are as many flavors of sequences as there are collection types, but to the outside world all sequences provide a very uniform interface: no matter if it’s a vector or a map or a list or a set behind a sequence, one sequence looks exactly like another.

> [!NOTE]
>
> **Sequence Adapter**
>
> If you have an object-oriented programming background, then the wrapper design of sequences might look familiar: hidden behind those sequences is the adapter pattern.


There’s even a function for wrapping your collection in a sequence: that function is called `seq`. Here’s a sequence made from a vector:

```clojure
(def title-seq (seq ["Emma" "Oliver Twist" "Robinson Crusoe"]))
```

Run this code, and you will get back the sequence view of your vector. So, if you print `title-seq` you will see this:

```clojure
("Emma" "Oliver Twist" "Robinson Crusoe")
```

Don’t be fooled by its round-parentheses disguise. `title-seq` is not a list; it’s a sequence, or "seq" for short.


You can also get a seq from a list:

```clojure
(seq '("Emma" "Oliver Twist" "Robinson Crusoe"))
```

A little more interesting is that you can call `seq` on a map: do this, and you will end up with a sequence of key/value pairs. Thus this:

```clojure
(seq {:title "Emma", :author "Austen", :published 1815})
```

will give you

```clojure
([:title "Emma"] [:author "Austen"] [:published 1815])
```

Well, more or less. Since Clojure makes no guarantees on the order of the keys in a map, it’s possible that the elements of the map sequence might come out in a different order.

You can even call seq on a sequence, like this:

```clojure
;; Calling seq on a sequence is a noop.
(seq (seq ["Red Queen" "The Nightingale" "Uprooted"]))
```

and get exactly the same sequence back.

A slightly more surprising thing about `seq` is that it will return a `nil` when handed an empty collection:


```clojure
(seq [])  ; Gives you nil.
(seq '()) ; Also nil.
(seq {})  ; Nil again.
```

This "empty sequence becomes a nil" behavior is handy because it means we can use `(seq collection)` as a truthy value to detect empty collections.



