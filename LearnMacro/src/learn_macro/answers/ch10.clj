(ns learn-macro.answers.ch10
  "第10章演習の解答例。")

(defn emit-twice
  [form]
  `(let [x# ~form]
     [x# x#]))

(defmacro twice
  [form]
  (emit-twice form))
