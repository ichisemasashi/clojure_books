(ns learn-macro.ch14-checklist
  "第14章（通読版短縮）: 書く前チェックリスト（データ）。"
  (:require [learn-macro.util :as u]))

(def before-writing
  ["評価順・短絡が本質か？"
   "新しい構文／束縛が必要か？"
   "データ＋関数では足りないか？"
   "呼び出し側にマクロ感染を強いないか？"])

(def after-writing
  ["変数捕獲はないか？（gensym / x#）"
   "二重評価はないか？（let で一度）"
   "展開結果は読めるか？"
   "展開テストはあるか？"
   "失敗時のメッセージは分かるか？"])

(defn demo
  []
  (u/section "Ch14 checklist (short)")
  (u/show "before-writing" before-writing)
  (u/show "after-writing" after-writing)
  :ok)
