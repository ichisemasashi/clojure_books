(ns learn-macro.ch09-binding
  "第9章核: 束縛マクロとリソースの薄い例。"
  (:require [learn-macro.util :as u])
  (:import (java.io StringReader)))

(defmacro when-let-lite
  "単一束縛。test は一度だけ評価する。"
  [[sym test] & body]
  `(let [temp# ~test]
     (when temp#
       (let [~sym temp#]
         ~@body))))

(defmacro if-let-lite
  "単一束縛の if-let 相当。"
  ([bindings then]
   `(if-let-lite ~bindings ~then nil))
  ([[sym test] then else]
   `(let [temp# ~test]
      (if temp#
        (let [~sym temp#] ~then)
        ~else))))

(defmacro with-closeable
  "Closeable を開き、body の後に .close するミニ版。"
  [[sym init] & body]
  `(let [~sym ~init]
     (try
       ~@body
       (finally
         (.close ~sym)))))

(defn demo
  []
  (u/section "Ch09 binding / resources")
  (u/expand1 "when-let-lite"
             (list `when-let-lite '[x (first [7])] '(inc x)))
  (u/show "when-let-lite run" (when-let-lite [x (first [7])] (inc x)))
  (u/show "when-let-lite nil" (when-let-lite [x nil] (inc x)))
  (let [n (atom 0)]
    (when-let-lite [x (do (swap! n inc) @n)] x)
    (u/show "side-effect once" @n))
  (u/expand1 "if-let-lite"
             (list `if-let-lite '[x (first [1])] '(inc x) :else))
  (u/show "if-let-lite else" (if-let-lite [x nil] :then :else))
  (u/expand1 "with-open (core)"
             '(with-open [r (clojure.java.io/reader "x.txt")]
                (slurp r)))
  (let [s (with-closeable [r (StringReader. "abc")]
            (slurp r))]
    (u/show "with-closeable run" s))
  (u/expand1 "defonce" '(defonce answer 42))
  :ok)
