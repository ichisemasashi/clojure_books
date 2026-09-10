(ns learn-macro.ch06-hygiene
  "第6章核: 二重評価回避と gensym / auto-gensym。"
  (:require [learn-macro.util :as u]))

(defmacro good-time
  "expr を一度だけ評価し、経過時間(ms)と戻り値を返す。"
  [expr]
  `(let [start# (System/nanoTime)
         ret# ~expr]
     {:ms (/ (double (- (System/nanoTime) start#)) 1.0e6)
      :ret ret#}))

(defmacro bad-time-double
  "【載せない失敗例】expr が展開結果に二度現れうる形の教材用。
  実際には ~expr を二回書いている。"
  [expr]
  `(let [start# (System/nanoTime)]
     {:ms (/ (double (- (System/nanoTime) start#)) 1.0e6)
      :ret ~expr
      :ret-again ~expr}))

(defn demo
  []
  (u/section "Ch06 hygiene / double evaluation")
  (let [n (atom 0)]
    (let [x (do (swap! n inc) @n)]
      (+ x x))
    (u/show "let-once side-effect count" @n))
  (u/expand1 "good-time" (list `good-time '(+ 1 2)))
  (u/show "good-time run" (good-time (+ 1 2)))
  (let [n (atom 0)
        expr (list 'do (list 'swap! 'n 'inc) (list 'deref 'n))]
    ;; 失敗例の形だけ見せる（実行は answers / 失敗例メモ側）
    (u/expand1 "bad-time-double (do not ship)" (list `bad-time-double '(do (swap! n inc) @n))))
  :ok)
