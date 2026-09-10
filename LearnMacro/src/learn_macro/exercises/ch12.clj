(ns learn-macro.exercises.ch12
  "第12章演習のたたき台。")

(defmacro shout
  "TODO: 引数が文字列リテラルでないとき、分かりやすい ex-info を投げよ。"
  [s]
  `(println ~(str "!" s "!")))
