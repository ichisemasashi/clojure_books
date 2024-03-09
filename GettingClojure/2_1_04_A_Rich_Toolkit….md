
### A Rich Toolkit …

If all we were talking about was 'first' and 'rest', this whole sequence kerfuffle wouldn’t amount to much. The real power of sequences lies in the mountain of useful library functions that—like our 'my-count' function—take any kind of collection, convert it to a sequence, and then do something interesting with the sequence. For example, if you have a seqable collection but it’s in the wrong order, you can sort it with 'sort':

```clojure
(def titles ["Jaws" "Emma" "2001" "Dracula"])
(sort titles) ; Sequence: ("2001" "Dracula" "Emma" "Jaws")
```

Like `count`, `sort` converts the collection you pass in into a sequence, sorts it, and returns the resulting sequence.

> [!NOTE]
> 
> **Seqa What?**
> 
> Yes, "seqable" is a word, at least if you are a Clojurist. A seqable is something that the `seq` function can turn into a sequence.


The `reverse` function works the same way, except that it reverses instead of sorts:

```clojure
;; A Sequence: ("Dracula" "2001" "Emma" "Jaws")
(reverse titles)
```

And since sequences are also (trivially) seqable you can feed the output of `sort` into `reverse` to get your titles sorted in the other direction:

```clojure
;; A Sequence: ("Jaws" "Emma" "Dracula" "2001")
(reverse (sort titles)) ;
```

There is also `partition`, which chops up a big sequence into a sequence of smaller sequences, enabling you to take a flat vector:

```clojure
(def titles-and-authors ["Jaws" "Benchley" "2001" "Clarke"])
(partition 2 titles-and-authors)
```

and turn it into something more structured:

```clojure
(("Jaws" "Benchley") ("2001" "Clarke"))
```


There is also `interleave`, which weaves two sequences together into one:

```clojure
;; A vector of titles and a list of authors.
(def titles ["Jaws" "2001"])
(def authors '("Benchley" "Clarke"))
;; Combine the authors and titles into a single sequence
;; ("Jaws" "Benchley" "2001" "Clarke")
(interleave titles authors)
```

Not to mention `interpose`, which sprinkles a separator value between the elements of a sequence:

```clojure
;; Gives us ("Lions" "and" "Tigers" "and" "Bears")
;; Oh my!
(def scary-animals ["Lions" "Tigers" "Bears"])
(interpose "and" scary-animals)
```

Functions like `sort`, `reverse`, `partition`, `interleave`, and `interpose` all share the same basic processing skeleton. They start by turning their collection arguments into sequences with `seq`. They then do their thing using only `first`, `rest`, and `cons`, or using functions that rely on the magic foursome. Finally they return the result as—you guessed it—a sequence.




