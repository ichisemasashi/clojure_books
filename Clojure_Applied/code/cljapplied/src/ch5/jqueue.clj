;---
; Excerpted from "Clojure Applied",
; published by The Pragmatic Bookshelf.
; Copyrights apply to this code. It may not be used to create training material, 
; courses, books, articles, and the like. Contact us if you are in doubt.
; We make no guarantees that this code is fit for any purpose. 
; Visit http://www.pragmaticprogrammer.com/titles/vmclojeco for more book information.
;---
;
(ns ch5.jqueue
  (:import [java.util.concurrent LinkedBlockingQueue]))

(defn pusher [q n]
  (loop [i 0]
    (when (< i n)
      (.put q i)
      (recur (inc i))))
  (.put q :END))

(defn popper [q]
  (loop [items []]
    (let [item (.take q)]
      (if (= item :END)
        items
        (recur (conj items item))))))

(defn flow [n]
 (let [q (LinkedBlockingQueue.)
       consumer (future (popper q))
       begin (System/currentTimeMillis)
       producer (future (pusher q n))
       received @consumer
       end (System/currentTimeMillis)]
  (println "Received:" (count received) "in" (- end begin) "ms")))
;