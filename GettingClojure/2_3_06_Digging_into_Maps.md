
### Digging into Maps

There’s even more good news with destructuring: it also works with maps.  The idea behind destructuring a map is the same as with the sequential types.  You provide a template for the data structure—a template that includes the symbols that you want to bind to various values in the map. To see how map destructuring works, consider that when you build a map, you supply a bunch of keys and values:

```clojure
(def artist-map {:painter :monet :novelist :austen})
```

Here we’re saying Associate `:monet` with `:painter` and `:austen` with `:novelist`. That is, this key with that value. In the template that you provide in a map destructuring, you do something very similar. You provide a series of symbols and keys. Like this:

```clojure
(let [{painter :painter writer :novelist} artist-map]
  (println "The painter is" painter)
  (println "The novelist is" writer))
```

This symbol with this key:

```
The painter is :monet
The novelist is :austen
```

In this last example we’re binding the value associated with the key `:painter` to `painter`, and the value associated with `:novelist` to writer.

The important thing to note about map destructuring is that in the left side of the destructuring equation the keys come second; the symbol `painter` is followed by the key `:painter`. Also keep in mind that since map destructuring is all about the keys, the order of the symbol/key pairs is not important. Thus we could have written our last example as follows:


```clojure
(let [{writer :novelist painter :painter} artist-map]
  (println "The painter is" painter)
  (println "The novelist is" writer))
```

without changing the result.



