(ns learn-macro.exercises.ch01
  "第1章演習（判断）: API 案をマクロか関数かに分類する。
  解答は answers/ch01.clj")

;; Q1. (defn debug [x] (println x) x) のようなトレーシング
;; Q2. (when-let [x (lookup id)] ...) のような束縛＋条件
;; Q3. マップのキーを全部キーワード化する変換

(def prompts
  [{:id :q1 :prompt "デバッグ用に値を印字して返す API"}
   {:id :q2 :prompt "lookup が non-nil のときだけ本体を走る束縛"}
   {:id :q3 :prompt "連想配列のキーをキーワード化する純変換"}])
