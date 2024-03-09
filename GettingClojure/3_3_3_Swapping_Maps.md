
### Swapping Maps

Atoms are not limited to storing just numbers: in fact, you can wrap an atom around any Clojure value. Thus a more practical example might look like this:

```clojure
(ns inventory)
(def by-title (atom {}))

(defn add-book [{title :title :as book}]
  (swap! by-title #(assoc % title book)))
(defn del-book [title] 
  (swap! by-title #(dissoc % title )))
(defn find-book [title]
  (get @by-title title))
```

That gives us a simple but complete stateful inventory manager: 

```clojure
(find-book "Emma") ; Nope
(add-book {:title "1984", :copies 1948})
(add-book {:title "Emma", :copies 100})
(del-book "1984")
(find-book "Emma") ; Yup
(find-book "1984") ; Nope
```

There really is not much to an atom. An atom is a container for a mutable value. You can get at the value with `deref` or `@` and you update it with `swap!`.

Conceptually simple and easy to use, atoms should be the first tool you reach for when you need to manage some mutable state. 


