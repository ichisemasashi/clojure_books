(ns learn-macro.exercises.ch05
  "第5章演習: 展開の予想と展開テスト。
  解答は answers/ch05.clj")

;; Q1. (or false nil :ok) の展開を予想してから照合せよ。
;; Q2. 自作 unless（または ch04 の when-not-lite）の展開テストを1本書け。

(def prompts
  [{:id :q1 :prompt "(or false nil :ok) の展開予想"}
   {:id :q2 :prompt "unless / when-not-lite の展開テスト"}])
