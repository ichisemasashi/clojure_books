
### Other Handy Map Functions

If you need to get hold of all the keys in a map—well, you can guess the function name:

```clojure
(keys book)
```

Evaluate that expression and you will end up with a collection of the keys of your map, something like this:

```clojure
(:title :author :published)
```

Or it might be `(:published :title :author)` or `(:author :published :title)`; the maps that you create with the literal `{}` or the `hash-map` function make no promises about the order of their keys.

> [!NOTE]
>
> **Sorted Maps**
> 
> There is a second flavor of map that keeps its keys sorted. You can make one of these sorted maps with the aptly named function `sorted-map`.


In much the same way that you can get all the keys from a map with `keys`, you can get all the values out of the map with the `vals` function, so that this:

```clojure
(vals book)
```

will give you all of the values in the map. Again, the order of values returned from `vals` is arbitrary, but it’s guaranteed to match the order of the keys returned by the `keys` function.

One final note on map literals: earlier I made a big deal out of how Clojure doesn’t require all of those annoying commas between items in lists and vectors and, as you can see from the preceding example, the elements of a map. Except that sometimes commas can be helpful:

```clojure
{:title "Oliver Twist", :author "Dickens", :published 1838}
```

Since Clojure treats commas as whitespace, this last example is perfectly valid code. Clojure programmers seem a bit torn on the question of commas in maps. They are widely but not universally used. So here is an area where the convention seems to be use them if they help.




