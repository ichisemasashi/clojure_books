
### Spotting Bugs with clojure.test

Let’s begin our adventures in testing by building a brand-new book store inventory project:

```bash 
$ lein new inventory
```

Now imagine we’re going to have a book inventory that looks like this:
  
```clojure
[{:title "2001" :author "Clarke" :copies 21}
{:title "Emma" :author "Austen" :copies 10}
{:title "Misery" :author "King" :copies 101}])
```

And we write some functions to do useful things with it:
  
test/inventory/src/inventory/core.clj
```clojure
(ns inventory.core)
    
(defn find-by-title
  "Search for a book by title,
  where title is a string and books is a collection
  of book maps, each of which must have a :title entry"
  [title books]
  (some #(when (= (:title %) title) %) books))
(defn number-of-copies-of
  "Return the number of copies in inventory of the
  given title, where title is a string and books is a collection
  of book maps each of which must have a :title entry"
  [title books]
  (:copies (find-by-title title books)))
```


And now we need to convince ourselves that this code does what it claims to do. Happily, Clojure comes equipped with a simple and capable library for writing traditional unit tests: `clojure.test`.

In a Clojure project the test typically lives in the `test` subdirectory. The convention is to put the tests for a namespace in a parallel `-test` module. So to test the `inventory.core` namespace, we create `inventory.core-test`. Inside the test namespace we’ll need to pull in `clojure.test`, and since writing a test is all about using the facilities that clojure.test provides, we can be forgiven for using `:refer :all` as we do it:

test/inventory/test/inventory/core_test.clj

```clojure
(ns inventory.core-test
  (:require [clojure.test :refer :all])
  (:require [inventory.core :as i]))
```

Obviously we also need to `require` in the namespace that we’re testing, in this case `inventory.core`.

Now that we have all the infrastructure, building tests is easy. You use `deftest`.  For example, if we wanted to convince ourselves that we could find a book by its title, we might write this:

```clojure
(def books
  [{:title "2001" :author "Clarke" :copies 21}
   {:title "Emma" :author "Austen" :copies 10}
   {:title "Misery" :author "King" :copies 101}])
(deftest test-finding-books
  (is (not (nil? (i/find-by-title "Emma" books)))))
```

As you can see, `deftest` takes a symbol—the name of the test—followed by the code for the test. In our example we use the `clojure.test`-supplied `is` to assert we can find one of the books in our inventory by title. Using is couldn’t be easier:


If the expression you supply is truthy, the test passes. If not, it fails.

Under the hood `deftest` binds a zero-argument function to the test name, a function that runs the test. Thus one—but certainly not the only—way to run your test is to call the function:

test/inventory/dev/run_test.clj
```clojure
(require '[inventory.core-test :as ct])

(ct/test-finding-books)
```

If the test succeeds the function will quietly return `nil`. If the test fails you will get a reasonably informative exception—something like this:

```
FAIL in (test-something-that-fails) (form-init8361253184899189179.clj:2)
expected: (not (nil? (i/find-by-title "Some other book" inventory)))
  actual: (not (not true))
```

You’re also not limited to one expression per test. So if it makes sense to test more than one condition in your test, you can write this:

test/inventory/test/inventory/core_test.clj
```clojure
(deftest test-finding-books-better
  (is (not (nil? (i/find-by-title "Emma" books))))
  (is (nil? (i/find-by-title "XYZZY" books))))
```

You can even organize your tests into subtests—or contexts—with `testing`:


```clojure
(deftest test-basic-inventory
  (testing "Finding books"
    (is (not (nil? (i/find-by-title "Emma" books))))
    (is (nil? (i/find-by-title "XYZZY" books))))
  (testing "Copies in inventory"
    (is (= 10 (i/number-of-copies-of "Emma" books)))))
```

The combination of `deftest` and `testing` means that you can organize your tests in just about any way that makes sense.


