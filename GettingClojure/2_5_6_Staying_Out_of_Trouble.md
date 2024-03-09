
### Staying Out of Trouble
                 
So which is better, traditional unit tests or generative tests? The answer is simple: yes. Both traditional tests and their generative cousins have strengths and weaknesses. And both have a place in making sure your code is doing what it is supposed to be doing.

Traditional unit tests do have one huge advantage: they are shatteringly obvious. Does it work when I do this? Yes. Does it work when I do that? Yes.  OK, then we’re good. So, if we were trying to test this simple function:

test/trouble_one.clj
```clojure
(defn f [a b] (/ a b))
```

we might write this test:

```clojure
(deftest test-f
  (is (= 1/2 (f 1 2)))
  (is (= 1/2 (f 3 6))) 
  (is (= 1 (f 10 10))))
```

And we know that for these specific instances, it works. The drawback of this kind of hand-crafted unit testing is that it is tightly constrained by our patience and imagination. I’m certainly not going to write tests for more than a few dozen integers before I call it a day.

Generative testing, on the other hand, opens up vast stretches of test cases.  We could, for example, write this:

```clojure 
(ctest/defspec more-complex-spec 10000
  (prop/for-all [a gen/pos-int
                 b gen/pos-int]
    (is (= (* b (f a b)) a))))
```

And we’d pretty rapidly discover that `f` does not work when `b` is zero. The danger with generative testing is that all those generated test cases will lull us into believing that we’ve covered all the possibilities. Do the math, and you will discover that even millions of test cases make up approximately zero percent of the possible pairs of integers. Change the function a bit:

```clojure
(defn more-complex-f [a b] (/ a (+ b 863947)))

(ctest/defspec test-more-complex 10000
  (prop/for-all [a gen/pos-int
                 b gen/pos-int]
    (= (* (more-complex-f a b) (- b 863947)) a)))
```

Now the chances are excellent that the generative test will miss the division by zero. As I say, the best solution is usually a small number of traditional unit tests combined with the greater reach of generative testing:

test/trouble_two.clj
```clojure
;; Prevent the division by zero.
(defn more-complex-f [a b]
  (let [denominator (- b 863947)]
    (if (zero? denominator)
      :no-result
      (/ a denominator))))
;; But we still want to be sure we detect it correctly.
(deftest test-critical-value
  (is (= :no-result (more-complex-f 1 863947))))
;; And the function works in other cases.
(def non-critical-gen (gen/such-that (partial not= 863947) gen/pos-int))

(ctest/defspec test-other-values 10000
  (prop/for-all [a gen/pos-int
                 b non-critical-gen]
    (= (* (more-complex-f a b) (- b 863947)) a)))
```

Finally, keep in mind that even one test is much better than no tests at all.

Let’s return one more time to our original inventory test:

```clojure
(require '[inventory.core :as i])

(deftest test-finding-books
  (is (not (nil? (i/find-by-title "Emma" books)))))
```

This test may not seem impressive, but get it to run, and you have demonstrated the following:

* There is a namespace called `inventory.core`.
* There are no gross syntax errors in `inventory.core`.
* The `inventory.core` namespace contains a function called `find-by-title`.
* You can call `find-by-title` with two parameters.
* Calling `find-by-title` with reasonable parameters doesn’t throw an exception.
* The `find-by-title` function does not always return `nil`.

This is not sworn testimony that `find-by-title` does exactly what you want, but it is also not nothing. A little bit of testing goes a long way.


