;---
; Excerpted from "Clojure Applied",
; published by The Pragmatic Bookshelf.
; Copyrights apply to this code. It may not be used to create training material, 
; courses, books, articles, and the like. Contact us if you are in doubt.
; We make no guarantees that this code is fit for any purpose. 
; Visit http://www.pragmaticprogrammer.com/titles/vmclojeco for more book information.
;---
(ns ch1.protocols
  (:require [ch1.validate]
            [ch1.money :refer (+$ zero-dollars)])
  (:import [ch1.validate Recipe Ingredient]))

(defrecord Store [,,,])

(defn cost-of [store ingredient] ,,,)

;
(defprotocol Cost
  (cost [entity store]))

(extend-protocol Cost
  Recipe
  (cost [recipe store]
    (reduce +$ zero-dollars
      (map #(cost % store) (:ingredients recipe))))

  Ingredient
  (cost [ingredient store]
    (cost-of store ingredient)))
;