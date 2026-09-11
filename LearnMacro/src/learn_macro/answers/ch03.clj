(ns learn-macro.answers.ch03
  "第3章演習の解答例。")

(defn template-str
  "実行時に x と ys から `(str ~x ~@ys) 相当のフォームを組む。"
  [x ys]
  `(str ~x ~@ys))

(def answers
  {:q1 {:example "(let [x \"hi\" ys [\"a\" \"b\"]] (template-str x ys))"
        :expected '(clojure.core/str "hi" "a" "b")}
   :q2 {:broken "`(str ~@x ~ys) — x はスカラーなのに splicing、ys はベクタなのに要素展開しない"
        :fixed "`(str ~x ~@ys)"}})
