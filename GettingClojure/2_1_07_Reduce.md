
### Reduce

Sometimes you don’t need to transform each item of a collection the way that `map` or `for` does. Sometimes what you need is to combine all the elements of a collection into a single value. For this we have `reduce`. Like `map`, `reduce` takes a function and a sequence and calls the function for each item in the sequence.

Unlike `map`, `reduce` passes two arguments to the function—along with the element from the collection, your function gets called with the current result. Each time `reduce` calls your function, it updates the current result with the return value from that call. When `reduce` runs out of elements, it returns the last result.
    
Although there are a lot of variations on how you can use `reduce`, the easiest to grasp is where you pass in three things: a two-argument function, an initial value, and the collection. Do this, and `reduce` will kick things off by calling the function with the initial value and the first item in the collection and then roll from there. To make this a little more real, let’s imagine we wanted to sum up all the numbers in this vector:

```clojure
(def numbers [10 20 30 40 50])
``` 

We’ve already seen that you could do this with `apply`, but you can also get it done with `reduce`:

```clojure
(defn add2 [a b] 
  (+ a b))

(reduce add2 0 numbers)
```

Run the preceding code, and you will get the sum.


You get that sum because `reduce` called the `add2` function, first with your starting value (0) and the first element of the `numbers` (10). It got the result (again 10), and then called the function again with the new result (10) and the second element (20), and so on down the line, eventually returning the last result.

We can simplify this last example quite a bit. Take the `add2` function: all it does is add its arguments together. Well, we already have a function that does that. It goes by the name of `+`:

```clojure
(reduce + 0 numbers)
```

We don’t even need the initial value. If you omit the initial value from the call to `reduce`, `reduce` will use the first element of the collection as the initial value, which—since we’re just adding up numbers—works fine:

```clojure
(reduce + numbers)
```

There is something elegant and wonderful about the brevity of code like this.

Don’t get the idea that `reduce` is only for adding numbers. Think of `reduce` as the basic tool for combining—or reducing—the items of a sequence into a single value. For example, you can use `reduce` to find the highest-priced book in your collection:

```clojure
(defn hi-price [hi book]
  (if (> (:price book) hi)
    (:price book)
    hi))

(reduce hi-price 0 books)
```

