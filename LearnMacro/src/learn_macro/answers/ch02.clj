(ns learn-macro.answers.ch02
  "第2章演習の解答例。")

(def answers
  {:q1 {:read "(when true (println 1) 2) というリスト／シンボルの木"
        :expand "(if true (do (println 1) 2)) へ書き換え（マクロ展開）"
        :eval "真枝が評価され、副作用のあと 2 が返る"}
   :q2 {:form '(list '+ 1 2)
        :checks ['(= '+ (first (list '+ 1 2)))
                 '(= 3 (count (list '+ 1 2)))]
        :note "eval せず first/count 等で形だけ見る"}})

(defn built-form
  []
  (list '+ 1 2))
