
### This Is the Data You’re Looking For

To get a feeling for how `clojure.spec` works, let’s return to our book-store example and imagine that we’re writing code to process our familiar book maps, values that look like this:

```clojure
{:title "Getting Clojure" :author "Olsen" :copies 1000000}
```

Further, imagine that we’re getting our book data from a not-terribly-reliable source, and we’ve decided that the first thing we need to do is validate that this value that claims to be a book is in fact a book-shaped value. Clearly we could write a function:

spec/inventory/src/inventory/core.clj
```clojure
(defn book? [x]
  (and
    (map? x)
    (string? (:author x))
    (string? (:title x))
    (pos-int? (:copies x))))
``` 

While this does work, the "code it by hand" approach to validating data doesn’t scale well. In any sizable system we are likely to have a significant number of complicated data shapes, and writing functions like this for each one would quickly become tedious.

Building this kind of data validation is why we have [`clojure.spec`](https://clojure.org/about/spec).  At its most basic, a `clojure.spec` is a sort of regular expression facility for Clojure data. In exactly the same way you can use a regular expression to express a pattern—perhaps an A followed by any number of Bs—that either will or won’t match some string, you can use clojure.spec to express a pattern—perhaps a collection consisting of only numbers—that either will or won’t match some Clojure data.

To see it in action you will need to load the `clojure.spec.alpha` namespace:


```clojure
(ns inventory.core
(:require [clojure.spec.alpha :as s]))
```

The key function supplied by spec is `valid?`:

```clojure
(s/valid? number? 44)     ; Returns true.
(s/valid? number? :hello) ; Returns false.
```

As you can see, `valid?` takes a predicate function and a value and will tell you if the value passes the test posed by the function. If this seems less than impressive, well, yes. But it’s just the beginning.

> [!NOTE]
>
> **Clojure Dot Spec Dot Alpha?**
>
> As I write these words `clojure.spec` is in the process of being finished off, which explains the `alpha` in the namespace name. Depending on when you’re reading this, that `alpha` may or may not still be there.
> Note also that while `clojure.spec` is well integrated with Clojure, it is delivered as a separate library. Thus if you are using Leiningen you will need an additional dependency entry in your project file.

For example, by using `clojure.spec/and`, you can combine a couple of predicates into something that will test if the value is a number and greater than 10:

```clojure
(def n-gt-10 (s/and number? #(> % 10)))

(s/valid? n-gt-10 1)  ; Nope.
(s/valid? n-gt-10 10) ; Still nope.
(s/valid? n-gt-10 11) ; True.
```

Conveniently, `and` doesn’t limit you to just two predicates:

```clojure
(def n-gt-10-lt-100
  (s/and number? #(> % 10) #(< % 100)))
```

One thing to be aware of is that the terminology is a bit confusing: We generally refer to the pattern-matching values as specs. But people do sometimes refer to the whole `clojure.spec` library itself as spec. For clarity, I’ll stick to calling the library `clojure.spec` and the values specs.

Along with and, `clojure.spec` also provides `or`, which lets you create a spec that will match either this or that. So if we needed a spec that would match either a number or a string, we might write the following:

```clojure
(def n-or-s (s/or :a-number number? :a-string string?))

(s/valid? n-or-s "Hello!") ; Yes!
(s/valid? n-or-s 99)       ; Yes!
(s/valid? n-or-s 'foo)     ; No, it's a symbol.
```

Notice the slight twist in using `or`. It requires its arguments in pairs, a keyword followed by a predicate. The keyword is required to help in producing coherent feedback when a spec fails to match.

More significantly, both `and` and `or` will accept specs as well as simple predicate functions as arguments. This means we can build up arbitrarily complex specs, so that this defines a spec that will accept numbers greater than 10, or any symbol:

```clojure
(def n-gt-10-or-s (s/or :greater-10 n-gt-10 :a-symbol symbol?))
```
