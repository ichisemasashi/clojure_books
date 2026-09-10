(ns learn-macro.exercises.ch06
  "第6章演習（直す）: 二重評価バグを直す。
  壊れたマクロは intentional。解答は answers/ch06.clj")

(defmacro broken-twice
  "【演習用・壊れている】expr を二度埋め込む。"
  [expr]
  `(+ ~expr ~expr))

;; TODO: once-add として、expr を一度だけ評価する版を書け。
)
