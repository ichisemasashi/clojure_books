(ns learn-macro.answers.ch01
  "第1章演習の解答例（著者が一度通した判断）。")

(def answers
  {:q1 {:choice :function
        :why "評価を遅らせる必要がなく、値を受けて副作用して返せばよい"}
   :q2 {:choice :macro
        :why "lookup を二度評価したくない／偽のとき本体を評価したくない"}
   :q3 {:choice :function
        :why "純粋なデータ変換。構文も評価順も不要"}})
