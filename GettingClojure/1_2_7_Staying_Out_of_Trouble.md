
### Staying Out of Trouble

The main way to trip up with Clojure’s vectors and lists is to forget just how immutable they are. For example, if you start with this:

```clojure
(def novels ["Emma" "Coma" "War and Peace"])
```

and then add a new book like this:

```clojure
(conj novels "Jaws")
```

you have done nothing. Well, not precisely nothing: you started with the three-element `novels` vector. Then you created a new vector with four elements. Then you threw that new vector away, leaving the universe pretty much as you found it. To do something useful you need to grab the new, four-element vector, perhaps by binding it to a new symbol:

```clojure
(def more-novels (conj novels "Jaws"))
```

Exactly the same logic applies to most other Clojure data structures, including lists:

```clojure
;; Create a list.
(def novels '("Emma" "Coma" "War and Peace"))
;; Just making the room warmer!
(conj novels "Jaws")
```


A downside of immutable lists and vectors is that it tends to cause disquiet in the hearts of new Clojure programmers, a disquiet centered on performance.  The fretting generally runs like this: What if I have a 50,000-element vector and I need to add a series of values to it? Won’t that require a lot of useless copying as `conj` manufactures one same-except-for-one-element result after another?

In a word, no. Under the hood, vectors store their data in chunks, organized in a shallow tree. Breaking the data up into chunks means that when it comes time to make an almost-the-same copy, Clojure can minimize the amount of copying by reusing most of the chunks as is.

This scheme is surprisingly efficient: some CPU cycles and memory do go into managing the chunks, but not a lot. Accessing an element of a vector—which involves traversing the tree—is not quite as fast as getting at an element of a simple array, but it’s still fast. And you can make modified copies without much actual copying. And it’s not just vectors: under the hood, all of Clojure’s data structures are carefully designed to support fast creation of almost-the-same copies.

Conversely, the payoff from immutability is huge. In Clojure, once your code gets hold of a data structure, you don’t have to worry that some other bit of code will unexpectedly change it. In Clojure your data structures always have the values that they were born with.

There is one other nontechnical danger lurking in Clojure data structures: the terminology. The computer-science term for immutable data structures that support the manufacture of very fast almost-the-same copies is persistent, as in persistent data structures. This is a very different use of the word persistent than most professional programmers are used to. Keep in mind that in Clojure, a persistent data structure is immutable and efficient and is not necessarily destined to be stored in a database or a file or anywhere else.


