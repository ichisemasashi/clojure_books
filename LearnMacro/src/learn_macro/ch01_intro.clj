(ns learn-macro.ch01-intro
  "第1章核: 関数では短絡できない / and マクロとの対比。"
  (:require [learn-macro.util :as u]))

(defn demo
  []
  (u/section "Ch01 intro — and vs function")
  (let [calls (atom [])
        f (fn [x] (swap! calls conj x) x)]
    ((fn [a b] (if a b a)) (f false) (f :ran))
    (u/show "function-style side effects" @calls)
    (reset! calls [])
    (and (f false) (f :ran))
    (u/show "and macro side effects" @calls))
  (u/expand1 "and" '(and false :x))
  :ok)
