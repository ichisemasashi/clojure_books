;---
; Excerpted from "Clojure Applied",
; published by The Pragmatic Bookshelf.
; Copyrights apply to this code. It may not be used to create training material, 
; courses, books, articles, and the like. Contact us if you are in doubt.
; We make no guarantees that this code is fit for any purpose. 
; Visit http://www.pragmaticprogrammer.com/titles/vmclojeco for more book information.
;---
(ns social.util.properties
  (:import [java.util Properties]))

(defn map->properties [m]
  (let [prop (Properties.)]
    (reduce (fn [p [k v]] (.setProperty p (str k) (str v)) p)
            prop m)))

