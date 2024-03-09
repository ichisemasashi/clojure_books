
### In the Wild

If you’re getting the feeling that sequences are pretty central to Clojure programing, well, yes. You can find sequences littered through the length and breath of real-world Clojure code. To see some real-world sequence action, you might have a look at the source code for the wonderful [Overtone](http://overtone.github.io), which describes itself as a "collaborative programmable music" system. Overtone spends a lot of its code creating, shaping, tuning, and distorting (hopefully) musical sounds, and makes heavy use of sequences and sequence functions in the process. For example, here is a bit of code that is part of generating a sound "envelope", which controls how the loudness of a sound evolves over time:
    
```clojure
(map #(+ %1 bias) [0 0 level (* level sustain) 0])
``` 

Keep in mind that `bias`, `level`, and `sustain` are all bound to floating-point numbers as this expression gets evaluated. Stare at the expression for a minute, and you will see that it’s just a call to `map`, one that uses a function literal to add `bias` to each of the five elements of the vector.

Alternatively, the authors of Overtone could’ve written this expression with a `for`:

```clojure
(for [v [0 0 level (* level sustain) 0]] (+ v bias))
```
 
But they didn’t. This is typical of real-world Clojure code, which leans on `map` much more frequently than on `for`.

You can also find some sequence-based string-building code along the lines of our book-title example in [ClojureScript](https://github.com/clojure/clojurescript).
ClojureScript takes on the daunting task of translating Clojure programs into the equivalent JavaScript code. Part of that translation involves transforming Clojure vectors and lists into their JavaScript equivalents, complete with brackets and commas. Dig into the ClojureScript code, and you will find this gem: 

```clojure
(defn seq->js-array [v]
(str "[" (apply str (interpose ", " (map pr-str v))) "]"))
```

Note that `seq->js-array` is not a terribly general function—the call to `pr-str` (which turns its arguments into quoted strings) means that `seq->js-array` can only handle one-level sequences of strings. Fortunately `seq->js-array` can afford to be less than general because it’s only intended to help in configuring the JavaScript environment. Still, the family resemblance to our earlier example—complete with calls to `map` and `interpose`—should be clear.

One thing to note from both of these real-world examples, as well as our earlier "format the highest-rated books" sample, is how deeply nested the sequence operations tend to get. Here’s the book example again:

```clojure
(defn format-top-titles [books]
  (apply
    str
    (interpose
      " // "
      (map :title (take 3 (reverse (sort-by :rating books)))))))
```

It’s a lovely example of functional code, but it does take some effort to read.

You need to work from the inside out. First we sort by `:ratings`. Then we reverse the sorted sequence and so on until we get to using `(apply str ...)` to turn the resulting sequence into a single string.

Happily, Clojure provides some convenient syntactical sugar for these occasions. In this case the sugar takes the form of a very pointy arrow: `->>`.  Essentially `->>` lets you write a nested expression like the one we just saw in a more human "do this, then that, then the other thing" order. Here’s how we would recast `format-top-titles` with `->>`:

```clojure
(defn format-top-titles [books]
  (->>
    books
    (sort-by :rating)
    reverse
    (take 3)
    (map :title)
    (interpose " // ")
    (apply str)))
```

This new version of `format-top-titles` starts with `books` and sorts the books by their ratings. Then it takes the resulting sequence and reverses it. Next it grabs the first three elements from the reversed sequence—these are the top-rated books—and pulls out their titles and formats a string using those titles. Note that `->>` is clever in that it knows how to deal with plain symbols like `reverse` as well as incomplete function calls like `(take 3)`. Even better, there is no performance penalty for using `->>`: behind the scenes, Clojure just turns a `->>` expression into the equivalent set of nested function calls.

There is also `->` (note the single `>`), which is very similar to `->>`. The difference is in where the current result gets placed at each step of the computation.  Use `->>`, and the result ends up at the end of the argument list. Use `->`, and the result gets slipped in at the front. It really just depends on what the functions you’re using expect.


