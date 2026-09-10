(ns learn-macro.answers.ch04
  "第4章演習解答: when-not 相当。"
  (:require [learn-macro.util :as u]))

(defmacro when-not-lite
  [test & body]
  `(when (not ~test)
     ~@body))

(defn demo
  []
  (u/expand1 "when-not-lite" (list `when-not-lite true :no))
  (u/show "run" (when-not-lite false :yes))
  :ok)
