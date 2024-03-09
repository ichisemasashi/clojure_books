
### A Toolkit of Functions

Along with the literal syntax there is also a function that manufactures vectors, called, appropriately enough, `vector`. The `vector` function takes any number of any kind of argument and wraps a vector around those arguments:

```clojure
;; The same as [true 3 "four" 5]
(vector true 3 "four" 5)
;; The same as []
(vector)
```

However you conjure it up, once you have a vector you’ll want to do stuff with it. Fortunately, Clojure provides all sorts of useful functions to go with your vectors. For example, if you’re interested in how many items are hiding inside your vector you can use our old friend `count`:

```clojure
(def novels ["Emma" "Coma" "War and Peace"])
(count novels) ; Returns 3.
```

You can get at the first item of your vector with the `first` function:

```clojure 
(first novels)
``` 

Run the preceding code and you will get `"Emma"` back for your trouble.

The `rest` function is a sort of negative doppelgänger of `first`; `rest` will give you back everything but the first item, so that this:

```clojure 
(rest novels)
```

will return a collection that starts with `"Coma"` and ends with `"War and Peace"`.  You may notice something odd about the results that come back from `rest`: the collection returned from `rest` prints with round parentheses instead of the square brackets that you are probably expecting. Instead of `(rest novels)` returning this:

```clojure
["Coma" "War and Peace"]
```

you will in fact see this:

```clojure
("Coma" "War and Peace")
```

The short explanation of this mystery is that instead of returning a vector, `rest` actually returns a sort of generic collection, called a sequence. You can find the longer answer in "Chapter 10, Sequences, on page 111". Note that you can drop as many items off the front of your vector as you have patience for by nesting calls to `rest`, so that this:

```clojure
(rest (rest novels))
```

will give you a collection containing all but the first two elements, or just `("War and Peace")`. Note that calling `rest` on a one-element vector, like this:

```clojure
(rest ["Ready Player One"]) ; Returns an empty collection.
```

will give you back an empty collection, as will calling `rest` on an empty vector:


```clojure
(rest []) ; Also an empty collection.
```

With a bit of effort you can use a combination of `rest` and `first` to get at any element in your vector. Need the third item? No problem; all you need is two calls to `rest` and one to `first`:

```clojure
(def year-books ["1491" "April 1865", "1984", "2001"])
(def third-book (first (rest (rest year-books)))) ; "1984".
```

This is doable but not very convenient, and happily there is an easier way: you can turn to `nth`, which takes a vector and a (zero-based) index:

```clojure
(nth year-books 2) ; Returns "1984".
```

Alternatively, you can call the vector like a function, supplying the index as an argument:

```clojure
(year-books 2) ; Also returns "1984".
```

Keep in mind none of these operations change the original vector in any way.  While `first` and `nth` both return a value from the vector, that value stays firmly in place in the original vector. Similarly, `rest` returns a new (shorter) vector without changing the original. This is our first glimpse of something very fundamental to Clojure: with a few exceptions, Clojure is built on a mountain of immutability.  Generally, once you create a Clojure data structure such as a vector, there is no way to modify it. The closest you can come is to use a function like `rest` to make a new data structure, one that is a only a little different from the original.

> [!NOTE]
> **Immutable Exceptions?**
>
> There are some exceptions to the Clojure everything is immutable rule. For example, consider that something must change when we say (def n 99). To find out what, see "Def, Symbols, and Vars".


