;---
; Excerpted from "Clojure Applied",
; published by The Pragmatic Bookshelf.
; Copyrights apply to this code. It may not be used to create training material, 
; courses, books, articles, and the like. Contact us if you are in doubt.
; We make no guarantees that this code is fit for any purpose. 
; Visit http://www.pragmaticprogrammer.com/titles/vmclojeco for more book information.
;---
(ns ch5.queue)

;
(defn queue
  "Create a new stateful queue"
  []
  (ref clojure.lang.PersistentQueue/EMPTY))

(defn enq
  "Enqueue item in q"
  [q item]
  (dosync
    (alter q conj item)))

(defn deq
  "Dequeue item from q (nil if none)"
  [q]
  (dosync
    (let [item (peek @q)]
      (alter q pop)
      item)))
;