
### Functions Are Values

Let’s start our adventures in functional programming by imagining that we have decided to add price and genre to the maps we’ve been using to keep track of our books, like this:


functional/examples.clj
```clojure
(def dracula {:title "Dracula"
              :author "Stoker"
              :price 1.99
              :genre :horror}) 
```

Further, let’s imagine that we need to write some code to distinguish the books based on an arbitrary price:
             
```clojure
(defn cheap? [book]
  (when (<= (:price book) 9.99)    
    book))
(defn pricey? [book]
  (when (> (:price book) 9.99)
    book))

(cheap? dracula)  ; Yes!
(pricey? dracula) ; No!
```

or the genre: 


```clojure
(defn horror? [book] 
  (when (= (:genre book) :horror)
    book))
(defn adventure? [book]
  (when (= (:genre book) :adventure)
    book))

(horror? dracula)    ; Yes!
(adventure? dracula) ; Nope!
```

The only halfway interesting thing about these functions is that they take advantage of Clojure’s truthy logic and return `nil` when the book fails the test, and the book map itself—which is truthy—when it passes.

We might also be interested in combinations of price and genre:


```clojure
(defn cheap-horror? [book]
  (when (and (cheap? book)
             (horror? book))
    book))
(defn pricy-adventure? [book]
  (when (and (pricey? book)
             (adventure? book))
    book))
```

We could write functions like this all day. What about cheap books by some author or the expensive books entitled Possession?

> [!NOTE]
>
> **Possession**
>
> It turns out there’s a remarkable number of novels called Possession, with at least a dozen in print as I write this.



The key—and unfortunate—word here is "write". When you are building real systems you don’t want to spend your time writing these kinds of combinations by hand. What you want is to code the basic operations and then create the combinations dynamically. Fortunately, all you need to get out of the handcoding business is to realize that in Clojure functions have something in common with numbers and strings and Booleans and vectors. Like these more mundane things, "functions are values".

This means that when you evaluate the name of a function you’ve defined with `defn`, perhaps like this:

```
cheap?
```

you will see something like this:

```
#object[user$cheap_QMARK_ 0x71454b9d "user$cheap_QMARK_@71454b9d"]
```


The `#object[user$cheap_QMARK_..."]` is the semi-intelligible string that gets output when Clojure tries to print the function that knows a cheap book from an expensive one. You can also bind that function value to another symbol:

```clojure
(def reasonably-priced? cheap?)
```

Do that, and `reasonably-priced?` is now an alternate name for our thrifty function:

```clojure
(reasonably-priced? dracula) ; Yes!
```

You can also pass function values to other functions. To take a silly example, we could do this:


```clojure
(defn run-with-dracula [f]
  (f dracula))
```

`run-with-dracula` does exactly what the name suggests: it evaluates a function with the `dracula` value as an argument. Which function? The one that you pass to `run-with-dracula`:

```clojure
(run-with-dracula pricey?) ; Nope.
(run-with-dracula horror?) ; Yes!
```

More practically, this idea of functions as values gives us an easy way of combining our predicates:


```clojure
(defn both? [first-predicate-f second-predicate-f book]
  (when (and (first-predicate-f book)
             (second-predicate-f book))
    book))

(both? cheap? horror? dracula)     ; Yup!
(both? pricey? adventure? dracula) ; Nope!
```

The only difference between the more general-purpose `both?` function and the very specific `cheap-horror?` is that `both?` lets you pass in your pair of predicate functions, which means you can use it to run your books by any two predicates you can cook up.


