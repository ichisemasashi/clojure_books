(ns learn-macro.ch03-quoting
  "第3章核: quote / syntax-quote / unquote / splicing.
  auto-gensym の安全な演習は第6章（ここでは形の紹介のみ）。"
  (:require [learn-macro.util :as u]))

(defn demo
  []
  (u/section "Ch03 quoting")
  (let [x 10
        ys [1 2 3]]
    (u/show "quote" '(+ 1 2))
    (u/show "syntax-quote + unquote" `(+ ~x 2))
    (u/show "splicing" `(+ ~@ys))
    (u/show "auto-gensym shape (safety in ch06)" `(let [a# 1] a#)))
  :ok)
