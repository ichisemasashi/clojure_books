(ns learn-macro.verify
  "工程2再完了条件: 核の REPL 検証と要検証項目.
  実行: clojure -M -m learn-macro.verify"
  (:require [clojure.walk :as walk]
            [clojure.pprint :as pp]))

(defn section [title]
  (println)
  (println "====" title "===="))

(defn show [label form]
  (print (str label ": "))
  (pp/pprint form))

(defmacro unless [test & body]
  `(if (not ~test)
     (do ~@body)))

(defmacro when-let-lite [[sym test] & body]
  `(let [temp# ~test]
     (when temp#
       (let [~sym temp#]
         ~@body))))

(defmacro good-time [expr]
  `(let [start# (System/nanoTime)
         ret# ~expr]
     {:ms (/ (double (- (System/nanoTime) start#)) 1.0e6)
      :ret ret#}))

(defmacro env-keys []
  (let [locals (vec (keys &env))]
    `'~locals))

(defn ch1-and-as-function []
  (section "Ch1 and-as-function")
  (let [calls (atom [])
        f (fn [x] (swap! calls conj x) x)]
    ((fn [a b] (if a b a)) (f false) (f :ran))
    (show "function-style side effects (no short-circuit)" @calls)
    (reset! calls [])
    (and (f false) (f :ran))
    (show "and macro side effects (short-circuit)" @calls)
    (show "macroexpand-1 and" (macroexpand-1 '(and false :x)))))

(defn ch2-code-as-data []
  (section "Ch2 code-as-data")
  (let [form (list '+ 1 2)]
    (show "form as data" form)
    (show "eval" (eval form))))

(defn ch3-quoting []
  (section "Ch3 quoting")
  (let [x 10 ys [1 2 3]]
    (show "quote" '(+ 1 2))
    (show "syntax-quote+unquote" `(+ ~x 2))
    (show "splicing" `(+ ~@ys))
    (show "auto-gensym" `(let [a# 1] a#))))

(defn ch4-unless []
  (section "Ch4 unless")
  (show "expand" (macroexpand-1 (list `unless false :ok)))
  (show "skip" (unless true :no))
  (show "take" (unless false :yes)))

(defn ch5-expand []
  (section "Ch5 macroexpand")
  (show "macroexpand-1 when" (macroexpand-1 '(when true 1 2)))
  (show "macroexpand when" (macroexpand '(when true 1 2)))
  (show "macroexpand-all nested when"
        (walk/macroexpand-all '(when true (when false 1)))))

(defn ch6-hygiene []
  (section "Ch6 hygiene / double-eval")
  (let [n (atom 0)]
    ;; 二重評価: 同じ式を二度埋め込む形
    (show "double-embed expansion"
          (macroexpand-1 '(let [x (do (swap! n inc) @n)] (+ x x))))
    (reset! n 0)
    (let [x (do (swap! n inc) @n)]
      (+ x x))
    (show "let-once side-effect count" @n)
    (show "captured-looking expand (uses gensym)"
          (macroexpand-1 (list `good-time '(+ 1 2))))
    (show "good-time run" (good-time (+ 1 2)))))

(defn case-a []
  (section "Case A unless / when-let-lite")
  (show "when-let-lite expand"
        (macroexpand-1 (list `when-let-lite '[x (first [7])] '(inc x))))
  (show "run" (when-let-lite [x (first [7])] (inc x)))
  (show "nil branch" (when-let-lite [x nil] (inc x)))
  (show "unless expand" (macroexpand-1 (list `unless false :ok))))

(defn case-b []
  (section "Case B time-like")
  (show "run" (assoc (good-time (reduce + (range 1000))) :label "sum")))

(defn verify-env []
  (section "Verify &env")
  (let [a 1 b 2]
    (show "locals at real expansion site" (env-keys)))
  (show "macroexpand-1 alone"
        (macroexpand-1 `(env-keys)))
  (show "conclusion"
        "&env is populated in real compilation/expansion context; inspecting via macroexpand alone can look empty."))

(defn verify-auto-gensym-nesting []
  (section "Verify foo# nesting")
  (let [same-template (let [f `(fn [x#] [x# x#])] f)
        two-templates [(first `(x#)) (first `(x#))]]
    (show "same syntax-quote shares auto-gensym" same-template)
    (show "separate syntax-quotes get distinct symbols?"
          [(not= (first two-templates) (second two-templates))
           two-templates])
    (show "nested syntax-quote is a new gensym scope"
          ;; 内側の ` は新しい自動 gensym 対応表
          (let [form `(list `x# x#)]
            form))))

(defn verify-macroexpand-all-limits []
  (section "Verify macroexpand-all limits")
  (show "nested macros expanded"
        (walk/macroexpand-all '(when true (or false 1))))
  (show "reader fn already expanded by reader before walk"
        (walk/macroexpand-all '(#(+ % 1) 2)))
  (show "special forms remain"
        (walk/macroexpand-all '(let [x 1] x))))

(defn -main [& _]
  (println "Clojure" (clojure-version))
  (println "Java" (System/getProperty "java.version"))
  (ch1-and-as-function)
  (ch2-code-as-data)
  (ch3-quoting)
  (ch4-unless)
  (ch5-expand)
  (ch6-hygiene)
  (case-a)
  (case-b)
  (verify-env)
  (verify-auto-gensym-nesting)
  (verify-macroexpand-all-limits)
  (section "DONE")
  (println "All verification sections executed."))
