(ns learn-macro.util
  "プロトタイプ共通: 展開前後の表示ヘルパー。"
  (:require [clojure.pprint :as pp]
            [clojure.walk :as walk]))

(defn section
  [title]
  (println)
  (println "====" title "===="))

(defn show
  [label form]
  (print (str label ": "))
  (pp/pprint form)
  form)

(defn expand1
  "マクロ呼び出しフォームを一段展開して表示する。
  form は既に解決済みのリストであること（syntax-quote で引数を修飾しない）。"
  [label form]
  (show (str label " (before)") form)
  (show (str label " (after macroexpand-1)") (macroexpand-1 form)))

(defn expand-all
  [label form]
  (show (str label " (before)") form)
  (show (str label " (after macroexpand-all)") (walk/macroexpand-all form)))
