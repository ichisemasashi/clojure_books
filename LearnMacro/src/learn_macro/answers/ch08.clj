(ns learn-macro.answers.ch08
  "第8章演習の解答例（レビューメモ）。"
  (:require [learn-macro.answers.ch04 :refer [when-not-lite]]))

(def review
  {:expand "macroexpand-1 で (when (not …) …) になり読みやすい"
   :doc "学習用なら docstring を付け、本番では clojure.core/when-not を勧める旨を書く"
   :naming "*-lite は教材用と分かる。衝突を避ける"
   :practice "実務では when-not で足りる。新規評価制御マクロは既存 core を先に疑う"})

(defn demo-expand
  []
  (macroexpand-1 '(when-not-lite false :yes)))
