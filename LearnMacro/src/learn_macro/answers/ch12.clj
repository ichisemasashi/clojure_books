(ns learn-macro.answers.ch12
  "第12章演習の解答例。")

(defmacro shout
  [s]
  (when-not (string? s)
    (throw (ex-info "shout: argument must be a string literal"
                    {:got s :hint "use (shout \"hi\")"})))
  `(println ~(str "!" s "!")))
