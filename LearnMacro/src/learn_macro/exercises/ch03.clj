(ns learn-macro.exercises.ch03
  "第3章演習: 構文引用とアンクォート。
  解答は answers/ch03.clj")

;; Q1. 局所 x とベクタ ys から `(str ~x ~@ys) 相当を作れ。
;; Q2. ~ と ~@ を取り違えたテンプレートを直せ。

(def prompts
  [{:id :q1 :prompt "x と ys から `(str ~x ~@ys) 相当"}
   {:id :q2 :prompt "~ と ~@ の取り違えを直す"}])

;; 壊れたテンプレート例（演習用）:
;; (let [x "hi" ys ["a" "b"]]
;;   `(str ~@x ~ys))   ; 誤り
