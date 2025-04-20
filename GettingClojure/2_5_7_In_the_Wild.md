    
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

このテストでは、メディテーションの一部として`is`の代わりに、`are`を使用していることに注意してください。本質的には、`are`を使用することでパラメータ化されたテストを構築できます。重要なロジックは先頭の`(= x y)`であり、大部分のテストでは`x`と`y`の値を指定しています。

そして、ここでは [`cons` 関数のテスト](https://github.com/clojure/clojure/blob/master/test/clojure/test_clojure/sequences.clj) があります。文字列に文字を `cons` して文字のシーケンスを作成できることをご存知でしたか？

```clojure
(deftest test-cons
  ;; 一部のテストは省略...
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
    ;; さらに多くのテストが省略されました...
    ))
```

そして、同じファイルで、`test.check` の動作を確認できます。

```clojure
(defspec longrange-equals-range 100
  (prop/for-all [start gen/int
                 end gen/int
                 step gen/s-pos-int]
                (= (clojure.lang.Range/create start end step)
                   (clojure.lang.LongRange/create start end step))))
```

このテストは、数値の範囲を表す `clojure.lang.Range` と `clojure.lang.LongRange` のインスタンスが等価であることを示すために実施されます。当然ながら、これらは range 関数によって生成されます。


