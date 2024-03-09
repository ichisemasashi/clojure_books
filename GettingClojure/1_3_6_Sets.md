
### Sets

Along with maps, Clojure also sports a built-in set data type. The literal syntax for a set borrows the braces from maps but adds a `#` to the front:

```clojure
(def genres #{:sci-fi :romance :mystery})
(def authors #{"Dickens" "Austen" "King"})
```

Like their mathematical namesakes, Clojure sets are all about membership: a value either is or is not a member of a set. Since a value can only be in a set once, if you repeat a value in a set literal, you’ll get an error. Thus, this:

```clojure
#{"Dickens" "Austen" "Dickens"}
```

is one "Dickens" too many:

```
IllegalArgumentException Duplicate key: Dickens...
```

Like maps, sets have their own ideas about the order of their elements. The set that you wrote as `#{:sci-fi :romance :mystery}` is liable to come back to you as `#{:sci-fi :mystery :romance}`.

Since sets are all about membership, the main thing you can do with them is discover if this or that value is in the set. You can check set membership with the `contains?` function, which will return either `true` or `false`:

```clojure
(contains? authors "Austen") ; => true
(contains? genres "Austen") ; => false
```

Or you can use the set like a function, in which case it will return either the value or `nil`:

```clojure
(authors "Austen") ; => "Austen"
(genres :historical) ; => nil
```

If you happen to be looking for a keyword in your set, you can switch things around and use the keyword as a function:

```clojure
(:sci-fi genres) ; => :sci-fi
(:historical genres) ; => nil
```

You can create a larger set from an existing set with our old friend `conj`:

```clojure
;; A four element set.
(def more-authors (conj authors "Clarke"))
```

It’s not an error to `conj` a value into a set a second time:

```clojure
(conj more-authors "Clarke")
```

But it is a bit of a waste, since a value can be in a set only once.

Finally, you can remove elements with `disj`:


```clojure
;; A set without "King".
(disj more-authors "King")
```

In this context, the word remove means make a second, smaller set.


