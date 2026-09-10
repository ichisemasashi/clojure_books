(ns learn-macro.ch12-tooling
  "第12章核: 展開テストと展開時エラー。"
  (:require [learn-macro.util :as u]
            [learn-macro.ch10-codegen :as ch10]))

(defmacro unless
  "第4章と同型。展開テスト用に本章 ns でも定義。"
  [test & body]
  `(if (not ~test) (do ~@body)))

(defn expand-unless
  [test body]
  (macroexpand-1 (list `unless test body)))

(defn demo
  []
  (u/section "Ch12 tooling / expand tests")
  (u/show "expand equals?"
          (= (expand-unless false :ok)
             '(if (clojure.core/not false) (do :ok))))
  (u/show "runtime" (unless false :ok))
  (try
    (ch10/emit-op-call 'unknown 1)
    (catch Exception e
      (u/show "ex-info message" (ex-message e))
      (u/show "ex-info data" (ex-data e))))
  (u/show "Clojure" (clojure-version))
  (u/show "Java" (System/getProperty "java.version"))
  :ok)
