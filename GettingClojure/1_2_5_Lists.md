
### Lists

Clojure has a second data type that—at least at first blush—seems a lot like the vector: the list. Like a vector, a list is an ordered collection of items. To make a list, you surround your items with round parentheses instead of the square brackets you would use for a vector:

```clojure
'(1 2 3)
```

If you look closely at the list in that code example, you will see a second syntactic twist: the list is preceded by a single-quote character. We need that quote for a very prosaic reason: lists, with their round parentheses, look just like Clojure code. Syntactically it’s hard to tell the difference between `(1 2 3)` and `(def x 99)`. The quote’s job is to stand there in front of the list and shout, Hey! The thing that comes next is data. Don’t try to execute it!

Happily, since the single quote applies to the whole of the thing that comes next, you only need one quote no matter how deeply your lists are nested.  The only exception to the quote that list! rule is when you have an empty list: since there is no way that `()` can be mistaken for anything other than an empty list, you can write it sans quote.

And yes, it’s hard to the tell the difference between a list and those sequence things we discussed earlier, since they both are surrounded by round parentheses. Again, I’ll have more to say about sequences in "Chapter 10, Sequences, on page 111".

Like vectors, lists can hold whatever data you care to throw at them, so that all of the following are perfectly good lists: 

```clojure
'(1 2 3 "four" 5 "six")
'(1 2.0 2.9999 "four" 5.001 "six")
'([1 2 ("a" "list" "inside a" "vector")] "inside" "a" "list")
``` 

And as with vectors there is also a function that will create a list from the arguments you pass in:

```clojure
;; More or less the same as '(1 2 3 "four" 5 "six")
(list 1 2 3 "four" 5 "six")
```

And you can do many of the same things with a list that you can with a vector:

```clojure
(def poems '("Iliad" "Odyssey" "Now We Are Six"))

(count poems) ; Returns 3.
(first poems) ; "Iliad".
(rest poems) ; ("Odyssey" "Now We Are Six")
(nth poems 2) ; "Now We Are Six".
```

As you can see, count, first, rest, and nth do exactly what you expect.


