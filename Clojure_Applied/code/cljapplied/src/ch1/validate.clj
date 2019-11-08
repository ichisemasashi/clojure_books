;---
; Excerpted from "Clojure Applied",
; published by The Pragmatic Bookshelf.
; Copyrights apply to this code. It may not be used to create training material, 
; courses, books, articles, and the like. Contact us if you are in doubt.
; We make no guarantees that this code is fit for any purpose. 
; Visit http://www.pragmaticprogrammer.com/titles/vmclojeco for more book information.
;---
;
(ns ch1.validate
  (:require [schema.core :as s])
;
  (:require [clojure.pprint :refer (pprint)]
            [clojure.repl :refer (doc)]))

;
(defrecord Recipe
  [name         ;; string
   description  ;; string
   ingredients  ;; sequence of Ingredient
   steps        ;; sequence of string
   servings     ;; number of servings
   ])

;
(defrecord Ingredient
  [name     ;; string
   quantity ;; amount
   unit     ;; keyword
   ])
;
;

;
(def spaghetti-tacos
  (map->Recipe
    {:name "Spaghetti tacos"
     :description "It's spaghetti... in a taco."
     :ingredients [(->Ingredient "Spaghetti" 1 :lb)
                   (->Ingredient "Spaghetti sauce" 16 :oz)
                   (->Ingredient "Taco shell" 12 :shell)]
     :steps ["Cook spaghetti according to box."
             "Heat spaghetti sauce until warm."
             "Mix spaghetti and sauce."
             "Put spaghetti in taco shells and serve."]
     :servings 4}))
;

;
(s/defrecord Ingredient
  [name     :- s/Str
   quantity :- s/Int
   unit     :- s/Keyword])

(s/defrecord Recipe
  [name        :- s/Str
   description :- s/Str
   ingredients :- [Ingredient]
   steps       :- [s/Str]
   servings    :- s/Int])
;

;; redefine with new record types

(def spaghetti-tacos
  (map->Recipe
    {:name "Spaghetti tacos"
     :description "It's spaghetti... in a taco."
     :ingredients [(->Ingredient "Spaghetti" 1 :lb)
                   (->Ingredient "Spaghetti sauce" 16 :oz)
                   (->Ingredient "Taco shell" 12 :shell)]
     :steps ["Cook spaghetti according to box."
             "Heat spaghetti sauce until warm."
             "Mix spaghetti and sauce."
             "Put spaghetti in taco shells and serve."]
     :servings 4}))

;;(pprint (s/explain Recipe))


