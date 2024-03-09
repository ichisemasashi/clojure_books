
### Staying Out of Trouble

Like everything else in programming maps, sets and keywords have their pitfalls. The good news about keywords is that they’re so simple it’s hard to go wrong with them. Mainly you just have to keep in mind that keywords are not strings. The keyword `:title` is not, for example, the same as the string `"title"`.  Thus with a keyword-based map like this:

```clojure
(def book 
  {:title "Oliver Twist"
   :author "Dickens"
   :published 1838})
```

doing this:
           
```clojure
(book "title") 
```

will return you exactly nothing (well, a `nil`), whereas doing this:

```clojure
(assoc book "title" "Pride and Prejudice")
```

will return a four-entry map: 

```clojure
{:title "Oliver Twist"
 :author "Dickens"
 :published 1838
 "title" "Pride and Prejudice"}
```

There are also some map-specific pitfalls to watch out for. For example, while it’s true that maps and sets both return `nil` if you go searching for a nonexistent key like this:

```clojure
(book :some-key-that-is-clearly-not-there) ; Gives you nil.
```

be careful depending on this behavior to deduce whether a key is present.

After all, someone may have written this:

```clojure
(def anonymous-book {:title "The Arabian Nights" :author nil})
```

The `anonymous-book` map is still a two-entry map, even if one of the values is `nil`.

If you need to know if some key exists in a map, reach for `contains?`:

```clojure
(contains? anonymous-book :title) ; True!
(contains? anonymous-book :author) ; Also true!
(contains? anonymous-book :favorite-color) ; False!
```

Exactly the same logic—and solution—applies to sets. If you’re worried that you may have a set with `nil` as a member, use `contains?` to check for membership:

```clojure
;; Our books may be anonymous.
(def possible-authors #{"Austen" "Dickens" nil})

(contains? possible-authors "Austen") ; True!
(contains? possible-authors "King") ; False!
(contains? possible-authors nil) ; True!
```


Another feature—and it really is a feature—of maps that sometimes trips up the new Clojure programmer is that the language is happy to treat maps like ordinary sequences of values, such as lists or vectors. We’ll talk more about this in Sequences, but for now remember that functions like `first`, `rest`, and `count` see maps as collections of two-element vectors:

```clojure
(def book {:title "Hard Times"
           :author "Dickens"
           :published 1838})

(first book) ; Might return [:published 1838].
(rest book) ; Maybe ([:title "Hard Times] [:author "Dickens"]).
(count book) ; Will definitely return 3.
```

Also be aware that since Clojure makes no promises about the order of maps, exactly which key/value pair you get from `first` is anybody’s guess. One thing you can rely on is that for any given map the results of `first` and `rest` will be consistent.

Finally, keep in mind that in expressions like `(:author book)` or `(:sci-fi genres)`, the keywords `:author` and `:sci-fi` aren’t just pretending to be functions. They are functions—functions that look themselves up in a map or a set. It is very common, if a bit confusing to beginners, to see a keyword like `:title` in a context where a function is clearly called for. In those situations you can bet that there is either a map or a set involved.


