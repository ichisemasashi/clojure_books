
### Pry Open Your Data

To see how we can use destructuring to peel back the layers of our data structures, we’ll start by disassembling something simple and work from there. Take this not-very-intimidating vector:

destructuring/examples.clj

```clojure
(def artists [:monet :austen])
```

and imagine you wanted to separate out the two keywords. There are a lot of ways to do that separation, but given that we’re only dealing with a two-element vector, we might go for the `first` function and its convenient sibling, `second`.

So running this:
  
```clojure 
(let [painter (first artists)
      novelist (second artists)]
  (println "The painter is:" painter
           "and the novelist is" novelist))
```

will give you the right values in `painter` and `novelist`.

Destructuring provides an alternative to this kind of hand disassembly. Here’s the destructuring version of the same `let`:

```clojure
(let [[painter novelist] artists]
  (println "The painter is:" painter
           "and the novelist is:" novelist))
```
  

Notice how the left side of the `let` binding equation, instead of being a simple symbol, is now a vector of symbols: `[painter novelist]`. That left-side vector acts as a sort of template for the `artists` vector. Essentially we’re saying, "Match up—and bind—the symbols in the first vector with the corresponding values in the second vector". Thus the symbol `painter` gets bound to `:monet` and `novelist` gets bound to `:austen`. The nice thing about destructuring is how well it scales.

If we had more values in our vector, perhaps like this:

```clojure
(def artists [:monet :austen :beethoven :dickinson])
```

we could just add more names to the names vector:

```clojure
(let [[painter novelist composer poet] artists]
  (println "The painter is" painter)
  (println "The novelist is" novelist)
  (println "The composer is" composer)
  (println "The poet is" poet))
```

and get our symbols bound.



