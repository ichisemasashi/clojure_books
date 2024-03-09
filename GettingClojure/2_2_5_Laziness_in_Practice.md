
### Laziness in Practice

To really get our arms around lazy sequences, let’s try a more interesting example. Suppose we needed some more or less randomly generated book maps for testing. We could sit down and manually cook up some nonsense books like this:

```clojure
(def test-books
  [{:author "Bob Jordan", :title "Wheel of Time, Book 1"}
   {:author "Jane Austen", :title "Wheel of Time, Book 2"}
   {:author "Chuck Dickens", :title "Wheel of Time, Book 3"}
   {:author "Leo Tolstoy", :title "Wheel of Time, Book 4"} 
   {:author "Bob Poe", :title "Wheel of Time, Book 5"}
   {:author "Jane Jordan", :title "Wheel of Time, Book 6"}
   {:author "Chuck Austen", :title "Wheel of Time, Book 7"}])
```

This will work, but armed with lazy sequences we can generate as many nonsense titles as we could ever want. To see how, let’s start by building some titles. We could combine a base title with some numbers to get a very finite sequence of titles: 

```clojure
(def numbers [1 2 3])
(def trilogy (map #(str "Wheel of Time, Book " % ) numbers))
```

to end up with this:

```clojure
("Wheel of Time, Book 1"
 "Wheel of Time, Book 2"
 "Wheel of Time, Book 3")
```

But since we have easy access to as many numbers as we can handle, and we know that `map` is lazy, it’s a short step to an unlimited sequence of sequels:

```clojure
(def numbers (iterate inc 1))
(def titles (map #(str "Wheel of Time, Book " % ) numbers))
```

We might also need authors for our generated books. Let’s take a different tack here and generate a number of unique authors from a limited set of first names and last names. It’s easy enough to build a modest vector of canned first names:

```clojure
(def first-names ["Bob" "Jane" "Chuck" "Leo"])
```

But what we really need (you’ll see why in a second) is the names repeated over and over:

```clojure
(cycle first-names)
```

We also need some last names:

```clojure
(def last-names ["Jordan" "Austen" "Dickens" "Tolstoy" "Poe"])
```

And we need a repeating sequence of those names:

```clojure
(cycle last-names)
```

We also need a function to combine a first name with a last name to give us a full name:

```clojure
(defn combine-names [fname lname]
  (str fname " " lname))
```

We can now use `map` to combine our names together to get a lazily infinite list of first- and last-name combinations:

```clojure
(def authors
  (map combine-names
    (cycle first-names)
    (cycle last-names)))
```

Finally we can pull our authors and titles together into a lazy sequence of book maps:

```clojure
(defn make-book [title author]
  {:author author :title title})
(def test-books (map make-book titles authors))
```

We end up with `test-books` bound to a lazy sequence that starts out like the manually generated one at the beginning of this section but then goes on forever, providing us with as many "Wheel of Time" sequels as we can stand.

It’s wonderful to think that every time we look at a book, perhaps by doing `(first test-books)`, we trigger a cascade of computing that generates a number, then a title, then a fresh first name and last name, and then a full name, and finally a book map. And yet on the outside `test-books` looks like a garden-variety sequence.


This underlines something important about lazy sequences: they are the ultimate "pay only for what you use" programming technique. By setting up the `test-books` sequence we have provided our system with a way to generate a huge amount of data. But—and the "but" here is key—we only pay the CPU and memory price for the data that we actually use. It’s only when we grab something from the sequence that the data gets generated.


