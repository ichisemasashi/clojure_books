(ns learn-macro.ch10-codegen
  "第10章核: 薄いマクロ＋ヘルパー関数、テーブル駆動。"
  (:require [learn-macro.util :as u]))

(defn emit-greeting
  "シンボル名 → println フォーム。純粋にテストできる。"
  [name-sym]
  `(println (str "hello, " ~(name name-sym))))

(defmacro greet
  [name-sym]
  (emit-greeting name-sym))

(def op-table
  {'inc 'clojure.core/inc
   'dec 'clojure.core/dec})

(defn emit-op-call
  [op arg-form]
  (let [f (get op-table op)]
    (when-not f
      (throw (ex-info "unknown op" {:op op :known (keys op-table)})))
    `(~f ~arg-form)))

(defmacro op-call
  [op arg]
  (emit-op-call op arg))

(defn demo
  []
  (u/section "Ch10 codegen / thin macro")
  (u/show "emit-greeting" (emit-greeting 'world))
  (u/expand1 "greet" (list `greet 'world))
  (u/show "op-call emit" (emit-op-call 'inc 41))
  (u/expand1 "op-call" (list `op-call 'inc 41))
  (u/show "op-call run" (op-call inc 41))
  (try
    (emit-op-call 'unknown 1)
    (catch Exception e
      (u/show "unknown op error" (ex-data e))))
  :ok)
