
### This Goes with That
           
Virtually all modern programming languages include some kind of data structure that lets you associate arbitrary keys with equally arbitrary values, and Clojure is no exception. Appropriately enough, Clojure calls its arbitrary mapping data structure a map. In keeping with Clojure’s barebones philosophy, the map literal syntax only requires a pair of curly braces and some key/value pairs. For instance, this:

map/examples.clj
```clojure
{"title" "Oliver Twist" "author" "Dickens" "published" 1838}
```

creates a map that associates the string `"title"` with `"Oliver Twist"`, `"author"` with `"Dickens"`, and `"published"` with `1838`.

You can also cook up a new map with the hash-map function:

```clojure
(hash-map "title" "Oliver Twist"
          "author" "Dickens"
          "published" 1838)
```

> [!NOTE]
> 
> **Why Not Just map?**
> 
> Yes, the name of the function that manufactures new maps is `hash-map`, not `map`. There is a `map` function, which we’ll meet presently, but it does something different.


Once you have a map, there are a surprising number of ways to look up a value.  The most obvious is to call the `get` function. For example, if we have this:

```clojure
(def book {"title" "Oliver Twist"
           "author" "Dickens"
           "published" 1838})
```

then we can get the date our book was published with this:

```clojure
(get book "published") ; Returns 1838.
```

An alternative—and more common—way to pull a value out of a map is to call the map itself like a function, supplying the key as an argument, so this:

```clojure
(book "published")
```

will also give you back `1838`, while `(book "title")` will return `"Oliver Twist"`. If you happen to reach for a key that’s not there, as in `(book "copyright")`, you will get a `nil`.


