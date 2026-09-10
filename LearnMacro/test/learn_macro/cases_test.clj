(ns learn-macro.cases-test
  (:require [clojure.test :refer [deftest is testing run-tests]]
            [learn-macro.ch04-defmacro :refer [unless]]
            [learn-macro.ch13-cases :refer [when-let-lite labeled-time]]))

(deftest case-a-unless-and-when-let-lite
  (testing "unless"
    (is (= :yes (unless false :yes)))
    (is (nil? (unless true :no))))
  (testing "when-let-lite evaluates test once and binds"
    (is (= 8 (when-let-lite [x (first [7])] (inc x))))
    (is (nil? (when-let-lite [x nil] (inc x))))
    (let [n (atom 0)]
      (is (= 1 (when-let-lite [x (do (swap! n inc) @n)] x)))
      (is (= 1 @n)))))

(deftest case-b-labeled-time
  (let [result (labeled-time "sum" (reduce + (range 10)))]
    (is (= "sum" (:label result)))
    (is (= 45 (:ret result)))
    (is (number? (:ms result)))))

(defn -main [& _]
  (let [{:keys [fail error]} (run-tests 'learn-macro.cases-test)]
    (System/exit (if (and (zero? fail) (zero? error)) 0 1))))
