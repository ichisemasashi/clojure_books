
### More Interesting Laziness

While the sequences returned from `repeat` are impressive at scale, they are also relentlessly boring—just the same value over and over. We can get more interesting sequences with `cycle`. The `cycle` function takes a collection and returns a lazy sequence of the items in the collection repeated over and over.

So this:

```clojure
(take 7 (cycle [1 2 3]))
```

will give you `(1 2 3 1 2 3 1)`.

We can generate still more interesting sequences with `iterate`. To use `iterate` you pass it a function and a starting value:

```clojure
(def numbers (iterate inc 1))
```

The `iterate` function returns a sequence whose first element is the value you passed in, in our example the `1`, so that this:

```clojure
(first numbers)
```

returns `1`. The plot thickens with the second item, which is the value returned by applying the function to the first value. In the example this would be `(inc 1)`, so the second item is `2`. And the third value is the function applied to the second value, so `3`. And off we go:

```clojure
(nth numbers 0) ; Returns 1
(nth numbers 1) ; Returns 2
(nth numbers 99) ; Returns 100.

(take 5 numbers) ; Returns (1 2 3 4 5)
```

In principle, `numbers` contains all the positive integers starting with `1`.

Keep in mind that what we got back from `iterate` is not some value or object that is somehow changing as it goes from `1` to `2` and so on. Instead what we get is a solidly immutable sequence whose first element is `1` and whose second element is `2` and then `3` and so on.


