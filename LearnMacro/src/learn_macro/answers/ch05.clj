(ns learn-macro.answers.ch05
  "第5章演習の解答例。"
  (:require [learn-macro.answers.ch04 :refer [when-not-lite]]))

(def answers
  {:q1 {:prediction "先頭 false を一度束縛し、偽なら (or nil :ok) へ再帰"
        :hint "macroexpand-1 では外側の let/if だけが見え、内側の or は残る場合がある"}
   :q2 {:test-idea "(= (macroexpand-1 '(when-not-lite true :no))
                       (macroexpand-1 (list `when-not-lite true :no)))"}})

(defn unless-expand-ok?
  "when-not-lite の展開が when+not 形であること（形の検査）。"
  []
  (let [m (macroexpand-1 (list `when-not-lite true :no))]
    (and (seq? m)
         (= 'clojure.core/when (first m)))))
