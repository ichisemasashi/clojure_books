
### Refs: Team-Oriented Atoms

One drawback in using atoms is that each atom stands alone. For example, if we needed to track the total number of books in the inventory along with each book by title using two atoms, we might try something like this:

```clojure
(ns inventory)
(def by-title (atom {}))
(def total-copies (atom 0))
(defn add-book [{title :title :as book}]
  (swap! by-title #(assoc % title book))
  ;; Oh no! The two atoms are out of sync right here!
  (swap! total-copies + (:copies book)))
```

We’re going to have an unavoidable window where the new book is in the inventory but not counted.

It is for just such occasions that Clojure supplies us with refs. Setting up a ref is much like setting up an atom:

```clojure
(ns inventory)
(def by-title (ref {}))
(def total-copies (ref 0))
```

As is getting at the values inside of a ref: it’s still either `@by-title` or `(deref total-copies)`. The difference between refs and atoms is that you can update a number of refs in a single database transactionlike operation. So now we can have our new book and count it too:

```clojure
(defn add-book [{title :title :as book}]
  (dosync
    (alter by-title #(assoc % title book))
    (alter total-copies + (:copies book))))
```

As you can see, the ref-updating function is called `alter` and it uses the same update with a function style as `swap!`. The key difference between atoms and refs is that all calls to `alter` must happen within the body of a call to `dosync`.

The changes inside of a `dosync` will either all happen together or not at all. Any intermediate states—in this case the book that’s in the inventory but not counted—will not be visible to code outside of the `dosync`.

Refs share the same basic update and conflict-resolution strategies as atoms, only on a larger scale. The work of updating a ref happens on the thread that is trying to do the updating, and in the event of dueling updates, one of the competing calls to `dosync` will get reevaluated, perhaps more than once.


