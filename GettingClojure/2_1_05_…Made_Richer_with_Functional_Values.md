
### … Made Richer with Functional Values

As convenient as functions like `partition` and `interpose` are, the real power of sequences only comes into play when you mix in the "functions are values" idea. A great example of this is the `filter` function. You can probably guess from the name what `filter` does: you pass `filter` a predicate and a collection, and it gives you back a new sequence populated with only those items from the original sequence that measured up to the predicate. The predicate is, of course, expressed as a function. So if we needed all the negative numbers in a list, we could combine `filter` with the `neg?`:

```clojure 
;; Returns the sequence (-22 -99 -77)
(filter neg? '(1 -22 3 -99 4 5 6 -77))
```
 
More realistically, if we had this vector of maps:

```clojure
(def books
  [{:title "Deep Six" :price 13.99 :genre :sci-fi :rating 6}
   {:title "Dracula" :price 1.99 :genre :horror :rating 7}
   {:title "Emma" :price 7.99 :genre :comedy :rating 9}
   {:title "2001" :price 10.50 :genre :sci-fi :rating 5}])
```

we could use our old buddy the `cheap?` function:

```clojure 
(defn cheap? [book]
  (when (<= (:price book) 9.99)
    book))
```

to find all the inexpensive books:

```clojure
(filter cheap? books)
```

which are as follows:

```clojure
({:genre :horror, :title "Dracula", :price 1.99 :rating 7}
 {:genre :comedy, :title "Emma", :price 7.99 :rating 9})
```

Similar to `filter` is `some`. Like `filter`, `some` takes a predicate and a seqable collection.  And like `filter`, `some` will go looking for items for which the predicate will return a truthy value. The difference is that `some` quits when it finds the first passing item, returning the value from the predicate. So while `filter` will always return a (possibly empty) sequence, `some` will return either the first truthy value from the predicate function, or `nil` if it can’t find anything. Since our predicate function `cheap?` returns either the book or `nil`, if you write this:

```clojure
(some cheap? books)
```

you will get the first cheap book, `{:genre :horror, :title "Dracula", :price 1.99}`. Even better, since the example will return either a book or `nil`, its return value follows the rules of truthy logic. And that means you can use it in an `if`:

```clojure
(if (some cheap? books)
  (println "We have cheap books for sale!"))
```

You can think of some as posing the question, "Is there some item that passes this test?"


