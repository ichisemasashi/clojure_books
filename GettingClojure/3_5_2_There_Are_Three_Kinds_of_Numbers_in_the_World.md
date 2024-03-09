    
### There Are Three Kinds of Numbers in the World

Let’s imagine we’ve come up with a simple rating system for books: you love a book, you hate it, or you’re completely indifferent to it. To handle this, we decide to represent book ratings with integers: positive numbers for good ratings, negative numbers for bad ratings, and zero for a devastating meh.

Given this design, we might end up writing code that looks like this:
    
macro/examples.clj
```clojure
(defn print-rating [rating]
  (cond
    (pos? rating)  (println "Good book!")
    (zero? rating) (println "Totally indifferent.")
    :else          (println "Run away!"))) 
```

Other than our use of `pos?` and `zero?` to pick out the numeric ranges, there is nothing very exciting going on here.

Now, if all we’re talking about are a few `cond` expressions here and there, we’re done. But what if variants of the book-rating `cond` started popping up throughout our system? What if you were spending a lot of time writing that same `cond` over and over? The thing for the astute—and fatigued—programmer to do is to factor out the common bits of that `cond` expression. The obvious thing to do is to write a function, which we’ll call `arithmetic-if`:

```clojure
(defn arithmetic-if [n pos zero neg]
  (cond
    (pos? n) pos
    (zero? n) zero
    (neg? n) neg))
```

At first blush all seems well. Have `arithmetic-if` return some keywords like this:

```clojure
(arithmetic-if 0 :great :meh :boring)
```

And indeed you will get your expected `:meh`.

> [!NOTE]
>
> **Arithmetic If**
>
> The name "arithmetic if" is what this positive/zero/negative construct was called in FORTRAN. And if you like to think of programming as a modern activity, consider the arithmetic if first appeared in 1957.


The trouble starts when we try something with side effects, like the `println` expressions we had in our original example:

```clojure
(defn print-rating [rating]
  (arithmetic-if rating
    (println "Good book!")
    (println "Totally indifferent.")
    (println "Run away!")))

(print-rating 10)
```

Run this code and all three strings get printed:

```clojure
"Good book!"
"Totally indifferent."
"Run away!"
```

Think for a second, and it makes sense: the rules of Clojure say that the first thing that happens when a function gets called is that the arguments get evaluated. All the arguments. That means that the second we tried to call `arithmetic-if`, Clojure evaluated not just the `n` parameter—which is fine—but also `pos`, `zero`, and `neg`—which is not so great. The rub is that Clojure does all this parameter evaluating before it evaluates the function body. This isn’t just an issue with explicit side effects, either; what if one of the branches of your `arithmetic-if` perpetrated the very indirect side effect of taking a long time to compute?

One way to prevent all three branches of our `arithmetic-if` from getting evaluated is to have `arithmetic-if` take three functions as arguments—something like this:

```clojure
(defn arithmetic-if [n pos-f zero-f neg-f]
  (cond
    (pos? n) (pos-f)
    (zero? n) (zero-f)
    (neg? n) (neg-f)))
```

With this version of `arithmetic-if` we would have to recast our rating-printing function to this:

```clojure
(defn print-rating [rating]
  (arithmetic-if rating
    #(println "Good book!")
    #(println "Totally indifferent.")
    #(println "Run away!")))

(print-rating 10)
```

This would work, but it’s odd—particularly if you are just trying to return some constants. What we really want is a clean version of `arithmetic-if`, one that doesn’t require any function wrappers. Can we build that?
