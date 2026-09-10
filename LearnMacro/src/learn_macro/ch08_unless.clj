(ns learn-macro.ch08-unless
  "第8章（通読版）: unless の品質向上版 — doc 付き再訪。"
  (:require [learn-macro.ch04-defmacro :refer [unless]]
            [learn-macro.util :as u]))

(defn demo
  []
  (u/section "Ch08 unless revisit")
  (u/expand1 "unless" (list `unless '(seq []) ':run))
  (u/show "doc" (-> #'unless meta :doc))
  (u/show "truthy test skips body" (unless (seq [1]) :hidden))
  (u/show "falsy test runs body" (unless (seq []) :shown))
  :ok)
