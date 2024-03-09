
### Composing a Solution

You can get a huge amount of programming mileage out of sequences and the panoply of functions that Clojure provides to process them. For example, imagine that you were working on a sales application for your book business and, starting with the books vector we saw earlier, you needed to format a string proclaiming the three top-rated books, something like this:

```clojure
"Emma // Dracula // Deep Six"
```

Step one is to find the top three books, by rating. We could start by sorting the books by their rating:

```clojure
(sort-by :rating books)
```

That will give us the books from lowest to highest rating, but the other way around is more useful:

```clojure
(reverse (sort-by :rating books))
```
    
Then we can then pull out the three highest-rated books, with `take`, a function that produces a sequence consisting of the first N items of another sequence.
In this case it’s the first three items:

```clojure
(take 3 (reverse (sort-by :rating books)))
```

But we don’t really need the whole book map, just the title. Looks like a job for map:


```clojure
(map :title (take 3 (reverse (sort-by :rating books))))
```

We now have a sequence that looks like `("Emma" "1984" "Jaws")`. From here it’s just a matter of assembling the string. We can put the slashes into our sequence with `interpose`:

```clojure
(interpose
  " // "
  (map :title (take 3 (reverse (sort-by :rating books)))))
```

Which gives us a five-string sequence:

```clojure
("Emma" " // " "1984" " // " "Jaws")
```

And then we just need to assemble the whole thing into a single string and wrap the code in a convenient function:

```clojure
(defn format-top-titles [books]
  (apply
    str
    (interpose
      " // "
      (map :title (take 3 (reverse (sort-by :rating books)))))))
```

You can get a lot of computing out of a few sequence functions.


