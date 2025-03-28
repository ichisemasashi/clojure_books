    
### 現実世界
 
`clojure.test` の実例を現実世界のコードで見つけるには、[Clojure 自体のテスト](https://github.com/clojure/clojure/blob/master/test/clojure/test_clojure/numbers.clj) を見るだけで十分です。 例えば、`+` が何をすべきかについての禅問答のような記述は次の通りです。
    
```clojure
(deftest test-add
  (are [x y] (= x y)
      (+) 0
      (+ 1) 1
      (+ 1 2) 3 
      (+ 1 2 3) 6
      (+ -1) -1
      (+ -1 -2) -3
      (+ -1 +2 -3) -2
      (+ 1 -1) 0 
      (+ -1 1) 0 
      ;; テストの多くは省略。
      ))           
```

Note that as part of its meditations this test uses `are` instead of `is`. Essentially `are` lets you build parameterized tests. The key logic is the `(= x y)` at the top, while the bulk of the test specifies values for `x` and `y`.

And here we have a [test of the `cons` function](https://github.com/clojure/clojure/blob/master/test/clojure/test_clojure/sequences.clj).  Did you know that you could `cons` characters onto a string to get a sequence of characters?

```clojure
(deftest test-cons
  ;; Some of the test omitted...
  (are [x y] (= x y)
    (cons 1 nil) '(1)
    (cons nil nil) '(nil)
    (cons \a nil) '(\a)
    (cons \a "") '(\a)
    (cons \a "bc") '(\a \b \c)
    (cons 1 ()) '(1)
    (cons 1 '(2 3)) '(1 2 3)
    (cons 1 []) [1]
    (cons 1 [2 3]) [1 2 3]
    ;; More of the test omitted...
    ))
```

And in the same file we can see `test.check` in action:

```clojure
(defspec longrange-equals-range 100
  (prop/for-all [start gen/int
                 end gen/int
                 step gen/s-pos-int]
                (= (clojure.lang.Range/create start end step)
                   (clojure.lang.LongRange/create start end step))))
```

This test is out to show that instances of `clojure.lang.Range` and `clojure.lang.LongRange`, which represent ranges of numbers—and unsurprisingly are produced by the range function—are equivalent.


