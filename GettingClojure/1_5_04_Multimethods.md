
### Multimethods 
          
Multi-arity and variadic functions are great for those situations where you want to build functions that are less picky about the number of arguments they will accept. Sometimes what you want is to be able to vary your function’s behavior based on some other aspect of the values that get passed to it. 

For example, imagine that our system was getting book data from various sources, in different formats. Some books still look like the maps we’ve been using:
  
```clojure
{:title "War and Peace" :author "Tolstoy"}
```

While others come in maps with different keys:

```clojure
{:book "Emma" :by "Austen"}
``` 

And still others are encoded in vectors:

```clojure
["1984" "Orwell"]
```

Clearly we could handle all of this by writing a function to convert the odd-ball formats into our standard map, complete with `:title` and `author` keys:


```clojure
;; Normalize book data to {:title ? :author ?}
(defn normalize-book [book]
  (if (vector? book)
    {:title (first book) :author (second book)}
    (if (contains? book :title)
      book
      {:title (:book book) :author (:by book)})))
```

There’s nothing wrong with this kind of "just do it" approach, but what if we suddenly had to deal with a whole blizzard of book formats, everything from lists to XML and JSON encoded strings. Our simple `normalize-book` function is likely to get very ugly very rapidly.

One way to deal with this kind of situation is to build a multimethod. Like functions with multiple arities, multimethods let you have a single function with multiple implementations. But unlike multi-arity functions, which pick the implementation based on the number of arguments, multimethods allow you to pick the implementation based on any—and I do mean any—characteristic of its arguments.

Writing a multimethod is an exercise in splitting the problem apart. First we need a function to do the splitting by categorizing the different sets of arguments. In our example this would be a function that can distinguish the different formats of book data:


```clojure
(defn dispatch-book-format [book]
  (cond
    (vector? book) :vector-book
    (contains? book :title) :standard-map
    (contains? book :book) :alternative-map))
```

Pass a book value to `dispatch-book-format`, and it will tell you which format you have—`:vector-book`, `:standard-map`, or `:alternative-map`.

Next, we declare a multimethod that uses our function to categorize its arguments:

```clojure
(defmulti normalize-book dispatch-book-format)
```


That code example defines a new multimethod—essentially a function—that picks its implementation based on what it gets back from its "dispatch function", in this case `dispatch-book-format`. All that is left is to define the implementations, one for each possible value returned from the dispatch function. We do this with defmethod:

```clojure
(defmethod normalize-book :vector-book [book]
  {:title (first book) :author (second book)})
(defmethod normalize-book :standard-map [book]
  book)
(defmethod normalize-book :alternative-map [book]
  {:title (:book book) :author (:by book)})
```

We end up with a single argument function called `normalize-book` that will first run its argument through `dispatch-book-format` and, based on the result, pick an implementation:

```clojure
;; Just returns the same (standard) book map.
(normalize-book {:title "War and Peace" :author "Tolstoy"})
;; Returns {:title "Emma" :author "Austen"}
(normalize-book {:book "Emma" :by "Austen"})
;; Returns {:title "1984" :author "Orwell"}
(normalize-book ["1984" "Orwell"])
```

And thus we can bring harmony to our books.

> [!NOTE]
>
> **Multi Who?**
>
> The careful reader will have noticed that `normalize-book` doesn’t contain any code to handle bad input. The good news is that if the dispatch function produces a value for which there is no corresponding `defmethod`, Clojure will generate an exception, which is probably what you want. Alternatively, you can supply a method for the value `:default` that will cover the "everything else" case.


The cool thing about multimethods is that in writing the dispatch function you can choose any criteria that you want. For example, in the United States the copyright period is different depending on when a book was published.  If our book maps include a `:published` key, then we could write a multimethod that decides what to do based on the year of publication:

```clojure
(defn dispatch-published [book]
  (cond
    (< (:published book) 1928) :public-domain
    (< (:published book) 1978) :old-copyright
    :else :new-copyright))

(defmulti compute-royalties dispatch-published)

(defmethod compute-royalties :public-domain [book] 0)
(defmethod compute-royalties :old-copyright [book]
  ;; Compute royalties based on old copyright law.
  )
(defmethod compute-royalties :new-copyright [book]
  ;; Compute royalties based on new copyright law.
  )
```

In a sense multimethods are a generalization of the kind of type-based polymorphism that you find in most object-oriented programming languages.
Multimethods are more general in the sense that you get to decide which criteria to use to pick the implementation. You can always change the guts of `dispatch-book-format` to pick your implementation a different way. Or create a different multimethod that categorizes its arguments in some other way.


Even better, there’s no requirement that all the bits of a single multimethod be defined in the same file or at the same time. If, for example, our books contained a `:genre` key, like this:

```clojure
(def books [{:title "Pride and Prejudice" :author "Austen" :genre :romance}
            {:title "World War Z" :author "Brooks" :genre :zombie}])
```

we could certainly create a multimethod based on the genre:

```clojure
;; Remember you can use keys like :genre like functions on maps.
(defmulti book-description :genre)

(defmethod book-description :romance [book]
  (str "The heart warming new romance by " (:author book)))
(defmethod book-description :zombie [book]
  (str "The heart consuming new zombie adventure by " (:author book)))
```

But what if much later someone comes up with a new genre?

```clojure
(def ppz {:title "Pride and Prejudice and Zombies"
          :author "Grahame-Smith"
          :genre :zombie-romance})
```

No problem! Just define a new method:


```clojure
(defmethod book-description :zombie-romance [book]
  (str "The heart warming and consuming new romance by " (:author book)))
```

As I say, this sort of multimethod addition does not have to appear in the same file or be written by the same programmer as the originals. And this means multimethods provide a great extension point for your code.


