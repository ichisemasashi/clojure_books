  
### Checking Properties 
      
The final piece of the property-based testing puzzle is expressing the property.

Happily, `test.check` provides a lovely syntax for doing just that. To start with a simple example, here we’re stating that each positive integer is smaller than the next positive integer:

test/sample_generators.clj
```clojure
(prop/for-all [i gen/pos-int]
  (< i (inc i)))
```
  
But we’re not quite done: since we’re defining a test and not a theorem, we need to supply a limit on the number of integers we’ll try. Here we pare the infinite run of positive integers down to the first 50 produced by the `pos-int` generator:

```clojure 
(tc/quick-check 50
  (prop/for-all [i gen/pos-int] 
    (< i (inc i))))
```

There, the `quick-check` function will check the property that we specified for 50 randomly generated cases. The `quick-check` function returns a map describing the results, something like this:

```clojure
{:result true, :num-tests 50, :seed 1509151628189}
```

Now (finally!) we can write the test for our inventory code:

test/inventory/test/inventory/core_gen_test.clj
```clojure
(tc/quick-check 50
  (prop/for-all [i-and-b inventory-and-book-gen]
    (= (i/find-by-title (-> i-and-b :book :title) (:inventory i-and-b))
      (:book i-and-b))))
```

Conceptually we say, "For all the inventory/book combinations we care to generate, looking for a book in the inventory with a given title should produce a book with that title".

There is also a smooth integration with `clojure.test` in the form of `defspec`, found in yet another namespace, `clojure.test.check.clojure-test`. So do this:

```clojure
(ctest/defspec find-by-title-finds-books 50
  (prop/for-all [i-and-b inventory-and-book-gen]
    (= (i/find-by-title (-> i-and-b :book :title) (:inventory i-and-b))
      (:book i-and-b))))
```

You end up with a `clojure.test` test that runs the property test.

As you might imagine, there’s a lot more to `test.check`, but this is the basic idea: define your data (in the form of generators), combine it with statements about properties, and roll the whole thing into a test.

