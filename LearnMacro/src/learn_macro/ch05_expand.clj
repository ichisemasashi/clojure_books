(ns learn-macro.ch05-expand
  "第5章核: macroexpand / macroexpand-1 / macroexpand-all。"
  (:require [clojure.walk :as walk]
            [learn-macro.util :as u]))

(defn demo
  []
  (u/section "Ch05 expand tools")
  (u/expand1 "when" '(when true 1 2))
  (u/show "macroexpand when" (macroexpand '(when true 1 2)))
  (u/expand-all "nested when"
                '(when true (when false 1)))
  (u/show "note" "&form/&env: 通読版は存在紹介のみ（詳細は完全版）")
  :ok)
