(ns learn-macro.ch07-core-read
  "第7章: clojure.core マクロを読んで展開を予想する（抜粋＋追記）。
  参照の目安（Clojure 1.12.x ソース）:
  - when / when-not / when-let … src/clj/clojure/core.clj（defmacro when 付近）
  - or / and … 同ファイル（defmacro or 付近）
  - cond … 同ファイル（defmacro cond）
  - -> / ->> … 同ファイル（defmacro ->）
  行番号は版で動くため、シンボル名で検索すること。"
  (:require [learn-macro.util :as u]))

(defn demo
  []
  (u/section "Ch07 core read")
  (u/expand1 "when" '(when true :a :b))
  (u/expand1 "or" '(or false nil :ok))
  (u/expand1 "when-let" '(when-let [x (first [1])] (inc x)))
  (u/expand1 "cond" '(cond false :a true :b :else :c))
  (u/expand1 "->" '(-> 1 inc (* 2)))
  (u/show "steal"
          "薄い本体・短絡・一度束縛・cond の対句・-> の形状変換")
  :ok)
