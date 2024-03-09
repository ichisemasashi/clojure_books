
### One Thing After Another

The vector is one of Clojure’s most widely used—and useful—data structures.  It’s just an ordered collection of items. Here, for example, is a vector of four numbers:

vector/examples.clj
```clojure
[1 2 3 4]
```

Syntactically, our little four-element vector could not be simpler: it’s just four values surrounded by a pair of square brackets, sans commas. While our first vector contained only numbers, there is no requirement that all the items in a vector be of the same type. You can, for example, mix in strings:

```clojure
[1 "two" 3 "four"]
```

Or Booleans:

```clojure
[true 3 "four" 5]
```

You can even nest vectors within vectors:

```clojure
[1 [true 3 "four" 5] 6]
```

And then do it again:

```clojure
[0 [1 [true 3 "four" 5] 6] 7]
```

Or Booleans:

```clojure
[true 3 "four" 5]
```

You can even nest vectors within vectors:

```clojure
[1 [true 3 "four" 5] 6]
```

And then do it again:

```clojure
[0 [1 [true 3 "four" 5] 6] 7]
```

You can, in fact, embed any Clojure value in a vector.


