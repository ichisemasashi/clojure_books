
### Growing Your Vectors

Now that we’ve seen how to get things out of a vector let’s move on to putting
new things in. Again, you can’t actually change an existing vector, but you
can make a new, slightly longer vector. One way to get a bigger vector is with
the `conj`—short for conjunction—function:

```clojure
(conj novels "Carrie") ; Adds "Carrie" to our list of novels.
```

Evaluate the preceding code and you will get back a new four-element vector:

```clojure
["Emma" "Coma" "War and Peace" "Carrie"]
```

Note that Stephen King’s guide to dealing with high-school conflict has landed
at the end of the new vector. If you want to add an item to the front of your
vector you can turn to the `cons`—as in construct—function:

```clojure
(cons "Carrie" novels)
```

Evaluate the preceding code and you will get this:

```clojure
("Carrie" "Emma" "Coma" "War and Peace")
```

Note that unlike `conj`, `cons` returns one of those round-parentheses sequence
things. Again, let’s leave that until we’re ready to talk about sequences.


