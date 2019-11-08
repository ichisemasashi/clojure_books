;---
; Excerpted from "Clojure Applied",
; published by The Pragmatic Bookshelf.
; Copyrights apply to this code. It may not be used to create training material, 
; courses, books, articles, and the like. Contact us if you are in doubt.
; We make no guarantees that this code is fit for any purpose. 
; Visit http://www.pragmaticprogrammer.com/titles/vmclojeco for more book information.
;---
(ns ch2.search)

;
(def units [:lb :oz :kg])

(some #{:oz} units)
;;=> :oz
;


;
(defn contains-val?
  [coll val]
  (reduce
    (fn [ret elem] (if (= val elem) (reduced true) ret))
    false coll))

(contains-val? units :oz)
;;=> true
;