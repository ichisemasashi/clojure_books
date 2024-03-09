
### Spec-Driven Tests

Checking arguments is not the only `clojure.spec-based` facility you can use to improve the reliability of your code. You also take advantage of `clojure.spec`—and specifically `fdef`—to drive `test.check` generative tests. Think about it: in spec’ing the arguments of a function you are also providing much of the information required to generate those arguments.

If we have this simple marketing blurb–generating function and an `fdef` to go with it, we can run the function with 1,000 randomly generated books:
    
```clojure
(defn book-blurb [book]
  (str "The best selling book " (:title book) " by " (:author book)))
  
(s/fdef book-blurb :args (s/cat :book ::book))
```

All we need is the check function:

```clojure
(require '[clojure.spec.test.alpha :as stest])

(stest/check 'inventory.core/book-blurb)
``` 

Now this is not much of a test because, while we are calling `book-blurb` with randomly generated data, our test is not checking the return value. But that’s not much of a challenge. We just add a `:ret` clause to our `fdef`:

```clojure
(s/fdef book-blurb
  :args (s/cat :book ::book)
  :ret (s/and string? (partial re-find #"The best selling")))
```

Here we are saying that the return value must be a string and contain the words "The best selling."

If you want to go even further, you can supply a function via the `:fn` key. The function gets handed a map containing both the function arguments and the return value. The test passes if the function returns a truthy value. Going back to our book blurb example, we could use `:fn` to check that the string returned contains the author’s name:

```clojure
(defn check-return [{:keys [args ret]}]
  (let [author (-> args :book :author)]
    (not (neg? (.indexOf ret author)))))

(s/fdef book-blurb
  :args (s/cat :book ::book)
  :ret (s/and string? (partial re-find #"The best selling"))
  :fn check-return)
```

Here the `check-return` function pulls the author out of the arguments and checks that the name of the author does indeed appear in the book blurb.



