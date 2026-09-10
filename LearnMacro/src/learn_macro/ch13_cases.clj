(ns learn-macro.ch13-cases
  "第13章: ケース A / B / C。"
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

(defn emit-cond->
  "ケース C: expr と test/form 対からフォームを生成（薄いマクロ＋関数）。
  clojure.core/cond-> と同様、累積値を let で再束縛する。"
  [expr clauses]
  (when-not (even? (count clauses))
    (throw (ex-info "cond->lite expects even number of test/form clauses"
                    {:count (count clauses)
                     :hint "pairs like: test1 form1 test2 form2"})))
  (let [g (gensym "cond")
        steps (map (fn [[test step]]
                     `(if ~test (-> ~g ~step) ~g))
                   (partition 2 clauses))]
    `(let [~g ~expr
           ~@(mapcat (fn [step] [g step]) steps)]
       ~g)))

(defmacro cond->lite
  "簡易 cond->。expr を一度束縛し、真の test のときだけ form へスレッドする。
  test にはスレッド値が渡らない（clojure.core/cond-> と同じ）。"
  [expr & clauses]
  (emit-cond-> expr clauses))

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

(defn demo-case-c
  []
  (u/section "Ch13 Case C — cond->lite")
  (u/show "emit-cond->"
          (emit-cond-> 1 '(true inc false (* 10) true (* 3))))
  (u/expand1 "cond->lite"
             (list* `cond->lite 1 '(true inc false (* 10) true (* 3))))
  (u/show "run" (cond->lite 1 true inc false (* 10) true (* 3)))
  (u/show "core cond->" (cond-> 1 true inc false (* 10) true (* 3)))
  (let [n (atom 0)]
    (cond->lite (do (swap! n inc) @n)
                true inc
                false (* 100))
    (u/show "expr side-effect once" @n))
  :ok)

(defn demo
  []
  (demo-case-a)
  (demo-case-b)
  (demo-case-c))
