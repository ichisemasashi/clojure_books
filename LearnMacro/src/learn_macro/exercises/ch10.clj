(ns learn-macro.exercises.ch10
  "第10章演習のたたき台。")

(defn emit-twice
  "TODO: `(do ~form ~form)` ではなく、一度束縛して二回使う形を返せ。"
  [form]
  :implement-me)

(defmacro twice
  [form]
  (emit-twice form))
