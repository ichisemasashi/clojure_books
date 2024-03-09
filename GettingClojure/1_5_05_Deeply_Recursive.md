        
### Deeply Recursive

Along with letting you do interesting things with your function arguments, Clojure also provides specialized support for writing recursive functions. A recursive function is, of course, a function that calls itself. For example, suppose we had this collection of book maps:
 
```clojure
(def books
  [{:title "Jaws" :copies-sold 2000000}
   {:title "Emma" :copies-sold 3000000} 
   {:title "2001" :copies-sold 4000000}])
```

and we wanted to know the total number of books sold. We could write a recursive function to run through all the elements of the vector:

```clojure
(defn sum-copies
  ([books] (sum-copies books 0))
  ([books total]
    (if (empty? books)
      total
      (sum-copies
        (rest books)
        (+ total (:copies-sold (first books)))))))
```

Notice that `sum-copies` uses the "filling in the defaults" trick we discussed earlier.

The second arity—the one that does all the work—takes two arguments: the vector and the current total. The first arity kicks things off by supplying a zero for the count so that you can call `sum-copies` with just the vector. Other than that, the operation of `sum-copies` is simple: it starts by using the Clojure-supplied `empty?` function to check if the vector is empty. If it is, then `sum-copies` returns `total` and we’re done. Otherwise `sum-copies` recursively calls itself with all but the first book and a new total.

Unfortunately there is serious problem with `sum-copies`. Every time `sum-copies` recursively calls itself, it eats up some stack space. That means `sum-copies` will work for a modestly sized collection of books, but make the books vector a little too long and you will run out of stack space:

```
StackOverflowError clojure.walk/walk (walk.clj:44)
```

On my machine, "too long" is around 4,000 books. Not small, but well within the realm of the possible.

This is where the specialized support for recursive functions comes in. Notice how making the recursive call to `sum-copies` is pretty much the last thing the function does? And how the only data flowing from one invocation of the function to the next flows through the function parameters? Given this, there’s no reason to accumulate all those stack frames. We can take advantage of "tail call optimization" by replacing the recursive call to `sum-to-n` with `recur`:

```clojure
(defn sum-copies
  ([books] (sum-copies books 0))
  ([books total]
    (if (empty? books)
      total
      (recur
        (rest books)
        (+ total (:copies-sold (first books)))))))
```

It appears that `recur` is making the same recursive call that we had in the first version of `sum-copies`. But `recur` knows how to take advantage of being the last expression in a function to avoid accumulating all those stack frames. And that means that this second version of `sum-copies` will work no matter how many books you’re dealing with.

One apparent downside of `recur` is that we need to build a new function—or, in the example, a new function arity—to use it, something that will get old quickly. Fortunately we can dispense with the function with `loop`. Here’s our `sum-copies` one more time, this time recast as a loop expression:

```clojure
(defn sum-copies [books]
  (loop [books books total 0]
    (if (empty? books)
      total
      (recur
        (rest books)
        (+ total (:copies-sold (first books)))))))
```


The way to understand `loop` is to think of it as a blend of a phantom function and a call to that function. In our example, the "function" has two parameters, `books` and `total`, which initially get bound to the original book collection and `0`.

With `books` and `total` bound, we evaluate the body, in this case the `if` expression.
The trick is that `loop` works with `recur`. When it hits a `recur` inside the body of a `loop`, Clojure will reset the values bound to the symbols to values passed into `recur` and then recursively reevaluate the `loop` body.

There are a couple of things to keep in mind about `recur`. The first is that `recur`, either with or without `loop`, is the Clojure way of writing a completely general-purpose loop. Think about it: `recur` lets you execute the same block of code over and over, each time with slightly different data, and break out just when you are ready. That is a loop. The second thing is that `recur` is a reasonably low-level tool. Chances are there is a better—and easier—way to get your task done. If, for example, you need to add up all those book sales, you would probably say something like this:


```clojure
(defn sum-copies [books] (apply + (map :copies-sold books)))
```

Well, that’s what you’ll say after you read a bit further. As we’ll see in the next few chapters, the `map` bit converts the collection of books into a collection of numbers—the copies sold—while the `apply +` sums up the copies. Details aside, the beauty of this last rendition of `sum-copies` is that it enables us to rise above the item-by-item processing of `loop` and `recur` and instead deal with the collection as a whole. While `loop` and `recur` are great tools to have as a last resort, there is usually a better way to solve most programming problems.



