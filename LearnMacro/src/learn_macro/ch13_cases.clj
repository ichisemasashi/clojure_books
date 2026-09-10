(ns learn-macro.ch13-cases
  "第13章（通読版）: ケース A / B。"
  (:require [learn-macro.ch04-defmacro :refer [unless]]
            [learn-macro.ch06-hygiene :refer [good-time]]
            [learn-macro.util :as u]))

(defmacro when-let-lite
  "単一束縛の when-let 風。test を一度だけ評価する。"
  [[sym test] & body]
  `(let [temp# ~test]
     (when temp#
       (let [~sym temp#]
         ~@body))))

(defmacro labeled-time
  "ケース B: ラベル付き計測。"
  [label expr]
  `(let [result# (good-time ~expr)]
     (assoc result# :label ~label)))

(defn demo-case-a
  []
  (u/section "Ch13 Case A — unless / when-let-lite")
  (u/expand1 "when-let-lite"
             (list `when-let-lite '[x (first [7])] '(inc x)))
  (u/show "run" (when-let-lite [x (first [7])] (inc x)))
  (u/show "nil" (when-let-lite [x nil] (inc x)))
  (u/expand1 "unless" (list `unless false :ok))
  :ok)

(defn demo-case-b
  []
  (u/section "Ch13 Case B — labeled-time")
  (u/expand1 "labeled-time"
             (list `labeled-time "sum" '(reduce + (range 1000))))
  (u/show "run" (labeled-time "sum" (reduce + (range 1000))))
  :ok)

(defn demo
  []
  (demo-case-a)
  (demo-case-b))
