
### Getting Less than Everything

Conveniently, we don’t have to have a one-to-one correspondence between the names and the data. For example, if we are only interested in the first three elements of our four-item vector, we could say this:

```clojure
(let [[painter novelist composer] artists]
  (println "The painter is" painter)
  (println "The novelist is" novelist)
  (println "The composer is" composer))
```

and simply ignore the leftover data, in this case `:dickinson`.

But what if we wanted to ignore some of the leading items of the vector? No problem—we can put a dummy name in as a placeholder:

```clojure
(let [[dummy dummy composer poet] artists]
  (println "The composer is" composer)
  (println "The poet is" poet))
```

There’s nothing special about the symbol `dummy`; we’re just using it to soak up the values for `:monet` and `:austen`. There’s also nothing earth-shattering about using the same name twice in a `let`. The name simply ends up bound to the last value, which is fine since we don’t care about either value. In fact, Clojure programmers have a convention for this sort of "I don’t really care about this" name. We use a symbol consisting of a single underscore. Thus, a better rendition of that last example would be as follows:

```clojure
(let [[_ _ composer poet] artists]
  (println "The composer is" composer)
  (println "The poet is" poet))
```

We’re also not limited to a single level of destructuring. If we started with this two-level vector:

```clojure
(def pairs [[:monet :austen] [:beethoven :dickinson]])
```

we could get hold of the first member of each pair with this:

```clojure
(let [[[painter] [composer]] pairs]
  (println "The painter is" painter)
  (println "The composer is" composer))
```

Notice the two-level structure of the leftmost template vector mirrors the two-level structure of the `pairs` vector. Run the preceding code and you’ll see this:

```
The painter is :monet
The composer is :beethoven
```

Alternatively, you can mix things up and pull out the first item of the first pair and the second item of the second pair, so that if your were looking for `:monet` and `:dickinson` you could say:

```clojure
(let [[[painter] [_ poet]] pairs]
  (println "The painter is" painter)
  (println "The poet is" poet))
```


The idea behind destructuring is that instead of painfully navigating your way through a data structure, API call by API call, you provide a rough sketch of the data structure—a sketch that includes a programmatic arrow marking the data you’re looking for.


