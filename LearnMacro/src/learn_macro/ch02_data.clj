(ns learn-macro.ch02-data
  "第2章核: コードはデータ。"
  (:require [learn-macro.util :as u]))

(defn demo
  []
  (u/section "Ch02 code as data")
  (let [form (list '+ 1 2)]
    (u/show "assembled form" form)
    (u/show "first/rest" [(first form) (rest form)])
    (u/show "eval (demo only; not a macro substitute)" (eval form)))
  :ok)
