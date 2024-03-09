
### Other Sources of Sequences

Given all the leverage you can get out of sequences, it’s not surprising that you can turn lots of things besides maps, vectors, and lists into sequences.  The `line-seq` function, for example, will turn the contents of a text file into a sequence. So if we had all our authors’ names in a file called `authors.txt`, we could write a function to determine if an author is listed:

```clojure
(require '[clojure.java.io :as io])

(defn listed-author? [author]
  (with-open [r (io/reader "authors.txt")]
    (some (partial = author) (line-seq r))))
```

Let’s put aside the require and `with-open` (it opens and closes a file) expressions for the moment and focus on the last line. In that last line we use `line-seq` to turn the contents of the `authors.txt` file into a sequence of strings, one string per line, and then use `some` to go looking for our author. 

Or perhaps instead of a file you have a string and you want to pull out all of the bits of the string that match a particular regular expression. Look no further than Clojure’s built-in regular-expression literals—which are written as a string prefixed by a `#`. The simplest thing you can do with a regular expression is ask if it matches some string:

```clojure
;; A regular expression that matches Pride and Prejudice followed by anything.
(def re #"Pride and Prejudice.*")
;; A string that may or may not match.
(def title "Pride and Prejudice and Zombies")
;; And we have a classic!
(if (re-matches re title)
  (println "We have a classic!"))
```


But you can also use `re-seq` to generate a sequence of strings that match a given regular expression. So armed with the regular expression `#"\w+"`, which matches a single word, you can do this:

```clojure
(re-seq #"\w+" title)
```

You’ll end up with the sequence `("Pride" "and" "Prejudice" "and" "Zombies")`.


