(ns learn-macro.run
  "プロトタイプの一括デモ（通読版＋完全版 9–12）。
  実行: clojure -M:run"
  (:require [learn-macro.ch01-intro :as ch01]
            [learn-macro.ch02-data :as ch02]
            [learn-macro.ch03-quoting :as ch03]
            [learn-macro.ch04-defmacro :as ch04]
            [learn-macro.ch05-expand :as ch05]
            [learn-macro.ch06-hygiene :as ch06]
            [learn-macro.ch07-core-read :as ch07]
            [learn-macro.ch08-unless :as ch08]
            [learn-macro.ch09-binding :as ch09]
            [learn-macro.ch10-codegen :as ch10]
            [learn-macro.ch11-dsl :as ch11]
            [learn-macro.ch12-tooling :as ch12]
            [learn-macro.ch13-cases :as ch13]
            [learn-macro.ch14-checklist :as ch14]
            [learn-macro.util :as u]))

(def demos
  [["ch01" ch01/demo]
   ["ch02" ch02/demo]
   ["ch03" ch03/demo]
   ["ch04" ch04/demo]
   ["ch05" ch05/demo]
   ["ch06" ch06/demo]
   ["ch07" ch07/demo]
   ["ch08" ch08/demo]
   ["ch09" ch09/demo]
   ["ch10" ch10/demo]
   ["ch11" ch11/demo]
   ["ch12" ch12/demo]
   ["ch13" ch13/demo]
   ["ch14" ch14/demo]])

(defn -main
  [& _]
  (println "Clojure" (clojure-version))
  (println "Java" (System/getProperty "java.version"))
  (println "LearnMacro demos (thin + full ch09–12)")
  (doseq [[label f] demos]
    (println)
    (println "----- running" label "-----")
    (f))
  (u/section "ALL DEMOS OK")
  (println "Prototype finished."))
