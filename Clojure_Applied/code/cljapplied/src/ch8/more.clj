;---
; Excerpted from "Clojure Applied",
; published by The Pragmatic Bookshelf.
; Copyrights apply to this code. It may not be used to create training material, 
; courses, books, articles, and the like. Contact us if you are in doubt.
; We make no guarantees that this code is fit for any purpose. 
; Visit http://www.pragmaticprogrammer.com/titles/vmclojeco for more book information.
;---
(ns ch8.more
  (:require [clojure.test :refer :all]))

;
(deftest test-range
  (is (= '(0 1 2 3 4) (range 5))
    "Got 0-indexed sequence when only end specified.")
  (is (= '() (range 0)) "Got empty sequence when end index = 0"))
;

;
(deftest test-range-group
  (testing "Testing range(endIndex)"
    (is (= '(0 1 2 3 4) (range 5))
      "Got 0-indexed sequence when only end specified.")
    (is (= '() (range 0))
      "Got empty sequence when end index = 0")))
;

;
(deftest test-range-fail
  (testing "Testing range(endIndex)"
    (is (= '(0 1 2 3 4) (range 5))
      "Got 0-indexed sequence when only end specified.")
    (is (= '() (range 0))
      "Got empty sequence when end index = 0")
    (is (= '(0 1) (range 0)))))
;

;
(deftest test-range-are
  (testing "Testing range(endIndex)"
    (are [expected endIndex]
      (= expected (range endIndex))
      '(0 1 2 3 4) 5
      '() 0)))
;

;
(deftest test-range-exception
  (try
    ;; use doall to force seq realization
    (doall (range "boom"))
    (is nil)
    (catch ClassCastException e
      (is true))
    (catch Throwable t
      (is nil))))
;

;
(deftest test-range-exception
  (is (thrown? ClassCastException
        (doall (range "boom")))))
;

;
(deftest test-range-exception-msg
  (is (thrown-with-msg? ClassCastException
        #"java.lang.String cannot be cast to java.lang.Number"
        (doall (range "boom")))))
;
