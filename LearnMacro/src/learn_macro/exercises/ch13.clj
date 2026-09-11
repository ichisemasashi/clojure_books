(ns learn-macro.exercises.ch13
  "第13章演習: ケースの罠と設計判断。
  解答は answers/ch13.clj")

;; Q1. when-let-lite を二重評価する間違った版を書き、テストで撃て。
;; Q2. labeled-time のラベル: (a) 展開時定数のみ (b) 計測後付け の引数の置き方
;; Q3. （ケース C）対句の増減と奇数条項の例外

(def prompts
  [{:id :q1 :prompt "二重評価版 when-let-lite をテストで撃つ"}
   {:id :q2 :prompt "ラベル設計 (a)/(b)"}
   {:id :q3 :prompt "cond->lite 対句の展開照合"}])
