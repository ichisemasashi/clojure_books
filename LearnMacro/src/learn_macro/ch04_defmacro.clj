(ns learn-macro.ch04-defmacro
  "第4章核: はじめての defmacro — unless。"
  (:require [learn-macro.util :as u]))

(defmacro unless
  "test が偽のとき body を評価する。"
  [test & body]
  `(if (not ~test)
     (do ~@body)))

(defn demo
  []
  (u/section "Ch04 defmacro — unless")
  (u/expand1 "unless" (list `unless false :ok))
  (u/show "skip when true" (unless true :no))
  (u/show "take when false" (unless false :yes))
  :ok)
