(ns learn-macro.exercises.ch02
  "第2章演習: read / expand / eval とフォーム組み立て。
  解答は answers/ch02.clj")

;; Q1. (when true (println 1) 2) に、read / expand / eval のどこで何になるかラベルを付けよ。
;; Q2. list だけで (+ 1 2) 相当のフォームを組み立て、eval せず構造だけ検査せよ。

(def prompts
  [{:id :q1 :prompt "when フォームの read/expand/eval ラベル"}
   {:id :q2 :prompt "list で (+ 1 2) 相当を組み構造検査"}])
