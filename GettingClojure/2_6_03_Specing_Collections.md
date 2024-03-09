
### Spec’ing Collections

We can also create specs for the various collections. The most basic here is `coll-of`, which specifies a collection of something:

```clojure
;; Something like '("Alice" "In" "Wonderland").
(def coll-of-strings (s/coll-of string?))
;; Or a collection of numbers or strings, perhaps ["Emma" 1815 "Jaws" 1974]
(def coll-of-n-or-s (s/coll-of n-or-s))
```

To produce tighter specifications on collections, we can reach for `cat`. Essentially `cat` lets you specify this should follow that in a collection. For example, if we wanted to match only four-element collections consisting of alternating strings and numbers we could say this:

```clojure
(def s-n-s-n (s/cat :s1 string? :n1 number? :s2 string? :n2 number?))
(s/valid? s-n-s-n ["Emma" 1815 "Jaws" 1974]) ; Yes!
```

Note that like `or`, `cat` requires descriptive keywords.

We can also write specs for maps using the `keys` function. Here, for example, is a spec for our familiar book map:

```clojure
(def book-s 
  (s/keys :req-un [:inventory.core/title
          :inventory.core/author
          :inventory.core/copies]))
```

The spec in that example will match any map that has `:title`, `:author`, and `:copies` keys:
  
```clojure
;; Yes!
(s/valid? book-s {:title "Emma" :author "Austen" :copies 10})
;; No! :author missing.
(s/valid? book-s {:title "Arabian Nights" :copies 17})
;; Yes! Additional entries are OK:
(s/valid? book-s {:title "2001" :author "Clarke" :copies 1 :published 1968})
```

Note that there is something a bit odd going on with the `keys` function. We supplied namespace-qualified keys in the spec: it’s `:inventory.core/title`, not `:title`.  But having supplied namespace-qualified keywords, the `-un` part of `:req-un` says that when it comes time to match, the spec will look for unqualified keyword keys in the map. There is a method to this namespace madness, but to understand it we need to first talk about how you can register your specs.


