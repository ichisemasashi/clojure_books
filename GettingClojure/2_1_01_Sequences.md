
# Part II Intermediate

## CHAPTER 10 Sequences

There’s a pleasant mystery about Clojure that most newly arrived programmers notice sooner or later. On one hand the language has this rich variety of collection types, everything from the map to the set to the vector and the list, each one good at some operations and less so at others. And Clojure programmers use the range of collection types with reasonably wild abandon.

Now for the mystery: There is very little "Oh, this is a vector but this is a map" special pleading in real-world Clojure code. Certainly you can find code here and there that is concerned with specific collection types, but you can also find vast stretches of Clojure code that seem to simply ignore the differences between the collection types.

So in this chapter we’ll have a hard look at sequences, the feature of Clojure that makes this all possible. We’ll see how Clojure has a sort of programmatic gravitational field that tends to pull collections into sequences. We’ll also discover how Clojure supplies you with a huge toolkit of functions that lets you do interesting things with sequences. We’ll round out the chapter by looking at some examples of sequences in action and at some ways you can get into sequential trouble.



