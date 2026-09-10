(ns learn-macro.answers.ch09
  "第9章演習の解答例。")

(defmacro when-let-lite
  [[sym test] & body]
  `(let [temp# ~test]
     (when temp#
       (let [~sym temp#]
         ~@body))))
