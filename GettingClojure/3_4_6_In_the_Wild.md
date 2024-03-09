 
### In the Wild
 
If you are interested in the ins and outs of implementing Clojure, you should definitely check out the [MAL project](https://github.com/kanaka/mal).  MAL, short for Make a Lisp, defines a simple Clojure-like language and then proceeds to implement it in (as of this writing) 68 languages, everything from Ada to Visual Basic.

You can find a great example of the power of using plain old Clojure values for both code and data in metadata. Metadata is extra data that you can hang on Clojure symbols and collections, data that in some ways enhances your value without being an official part of the value.

There are two ways you can hang some metadata on a value. You can do it either explicitly with the `with-meta` function:

```clojure
(def books1 (with-meta ["Emma" "1984"] {:favorite-books true}))
```

or by using the `^:keyword` syntactical sugar:

```clojure
(def books1 ^:favorite-books ["Emma" "1984"])
```

Having applied some metadata to your value, you can get it back with the `meta` function:

```clojure
;; Gives you the {:favorite-books true} map. 
(meta books1)
```

The key thing about metadata is that it is extra data: metadata doesn’t affect the actual value. That means that two otherwise equal values are still equal even if they have different metadata:

```clojure 
;; Otherwise identical vectors with different metadata...
(def books2 (with-meta ["Emma" "1984"] {:favorite-books true}))
(def books3 (with-meta ["Emma" "1984"] {:favorite-books false}))
;; Are still equal.
(= books2 books3) ; True!
```

If the metadata syntax looks familiar, it should. We already came across metadata when we were dealing with dynamic vars:

```clojure
(def ^:dynamic *print-length* nil)
```

There are also less obvious uses of metadata. For example, when you define a function with a docstring, Clojure stashes the docstring of the function in the metadata of the symbol.

But don’t take my word for it. Define a function with a docstring:

```clojure
(defn add2
  "Return the sum of two numbers"
  [a b]
  (+ a b))
```

and then look at the metadata on the add2 var:

```clojure
(meta #'add2)
```

You will see something like this:

```clojure
{:doc "Return the sum of two numbers",
 :arglists ([a b]),
 :name add2,
 :ns #object[clojure.lang.Namespace 0xa55c011 "user"]
 :line 1
 :column 1
 ...
}
```

And there is the docstring, along with all sorts of useful information about our function, all stashed in the metadata.

Finally—and returning to the topic at hand—in exactly the same way that a function call is just a list and the parameters in a `defn` are just a vector, metadata is just a map. So if you pick up some metadata from a value:

```clojure
(def md (meta books3))
```

you have a plain old map:

```clojure
;; Do mapish things to the metadata
(count md) ; Returns 1
(vals md)  ; Returns (false)
```

Remember, Clojure code is just Clojure data, all the way down.


