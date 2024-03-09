
### The Final Frontier: Mixing and Matching

So far we have seen how to destructure sequential things like vectors and lists. We’ve also seen how to destructure our way into maps. What we haven’t done yet is destructure a mixture of the two, perhaps a map in a vector or a vector in a map. Fortunately, it’s just more of the same: to destructure a mix of sequences and maps, you mix the destructuring syntax in exactly the way that you would guess.

Take this vectors-inside-of-a-map conglomeration:

```clojure
(def author {:name "Jane Austen"
             :books [{:title "Sense and Sensibility" :published 1811}
                     {:title "Emma" :published 1815}]})
```

We can get hold of Jane’s name and the information about Emma with a simple

```clojure
(let [{name :name [_ book] :books} author]
  (println "The author is" name)
  (println "One of the author's books is" book))
```

Alternatively, if we had a couple of maps inside of a vector, perhaps like this:

```clojure
(def authors [{:name "Jane Austen" :born 1775}
              {:name "Charles Dickens" :born 1812}])
```

we could easily dig down into the dates of birth:

```clojure
(let [[{dob-1 :born} {dob-2 :born}] authors]
  (println "One author was born in" dob-1)
  (println "The other author was born in" dob-2))
```

