(ns learn-macro.answers.ch06
  "第6章演習解答: 二重評価を直す。"
  (:require [learn-macro.util :as u]
            [learn-macro.exercises.ch06 :refer [broken-twice]]))

(defmacro once-add
  [expr]
  `(let [x# ~expr]
     (+ x# x#)))

(defn demo
  []
  (let [n (atom 0)]
    (broken-twice (do (swap! n inc) 1))
    (u/show "broken side effects" @n))
  (let [n (atom 0)]
    (once-add (do (swap! n inc) 1))
    (u/show "fixed side effects" @n))
  (u/expand1 "once-add" (list `once-add '(+ 1 2)))
  :ok)
