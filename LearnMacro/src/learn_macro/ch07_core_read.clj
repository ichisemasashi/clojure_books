(ns learn-macro.ch07-core-read
  "第7章抜粋: clojure.core マクロを読んで展開を予想する。"
  (:require [learn-macro.util :as u]))

(defn demo
  []
  (u/section "Ch07 core read (excerpt)")
  (u/expand1 "when" '(when true :a :b))
  (u/expand1 "or" '(or false nil :ok))
  (u/expand1 "when-let" '(when-let [x (first [1])] (inc x)))
  (u/show "steal" "薄い本体・短絡・一度束縛してから本体へ渡す")
  :ok)
