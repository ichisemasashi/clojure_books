
### Destructuring in Sequence

Although so far we have limited ourselves to destructuring vectors, all of the tricks we’ve seen so far will work with any of Clojure’s sequential data types.  Thus if we switch our names from a vector to a list:

```clojure
(def artist-list '(:monet :austen :beethoven :dickinson))
```

the destructuring stays exactly the same:

```clojure
(let [[painter novelist composer] artist-list]
  (println "The painter is" painter)
  (println "The novelist is" novelist)
  (println "The composer is" composer))
```

Note especially that even when we’re destructuring a list, we continue to use square brackets around our template. The left side of the binding equation remains `[painter novelist composer]`. In this context you can think of those square brackets as standing in for the delimiters around any sequential data type.

And I do mean any sequential data type. Aside from lists and vectors, you can destructure any Clojure value that can be turned into a sequence. Strings, for example, destructure into their individual characters:

```clojure
(let [[c1 c2 c3 c4] "Jane"]
  (println "How do you spell Jane?")
  (println c1)
  (println c2)
  (println c3)
  (println c4))
```

Run the preceding code and you will see this:

```
How do you spell Jane?
J
a
n
e
```

The rule is, if you can turn it into a sequence, you can destructure it.

