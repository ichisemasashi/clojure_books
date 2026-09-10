(ns learn-macro.ch11-dsl
  "第11章核: 入口マクロ＋データ駆動ルート。"
  (:require [learn-macro.util :as u]))

(defn compile-routes
  "ルート表データ → {(method path) handler-kw}。"
  [routes]
  (into {}
        (map (fn [[method path handler]]
               [[method path] handler]))
        routes))

(defmacro defroutes-lite
  "書き味用の薄い入口。route-forms は展開時にデータとして埋め込む（リテラル想定）。
  実行時に組み立てた表は compile-routes を関数として呼べ。"
  [name & route-forms]
  `(def ~name (compile-routes ~(vec route-forms))))

(defroutes-lite sample-routes
  ["GET" "/" :home]
  ["GET" "/items/:id" :item])

(defn demo
  []
  (u/section "Ch11 DSL boundary")
  (u/show "data routes"
          [["GET" "/" :home]
           ["GET" "/items/:id" :item]])
  (u/show "compile-routes"
          (compile-routes [["GET" "/" :home]
                           ["GET" "/items/:id" :item]]))
  (u/expand1 "defroutes-lite"
             (list `defroutes-lite 'demo-routes
                   ["GET" "/" :home]
                   ["POST" "/items" :create]))
  (u/show "sample-routes" sample-routes)
  (u/show "lookup" (get sample-routes ["GET" "/"]))
  :ok)
