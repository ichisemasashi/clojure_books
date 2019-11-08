;---
; Excerpted from "Clojure Applied",
; published by The Pragmatic Bookshelf.
; Copyrights apply to this code. It may not be used to create training material, 
; courses, books, articles, and the like. Contact us if you are in doubt.
; We make no guarantees that this code is fit for any purpose. 
; Visit http://www.pragmaticprogrammer.com/titles/vmclojeco for more book information.
;---
(ns ch1.recipe)

;
(defrecord Recipe
  [name        ;; string
   author      ;; recipe creator
   description ;; string
   ingredients ;; list of ingredients
   steps       ;; sequence of string
   servings    ;; number of servings
   ])
;

;
(defrecord Person
  [fname ;; first name
   lname ;; last name
   ])
;

;
(def toast
  (->Recipe
    "Toast"
    (->Person "Alex" "Miller") ;; nested
    "Crispy bread"
    ["Slice of bread"]
    ["Toast bread in toaster"]
    1))
;

;
(def people
  {"p1" (->Person "Alex" "Miller")})

(def recipes
  {"r1" (->Recipe
          "Toast"
          "p1" ;; Person id
          "Crispy bread"
          ["Slice of bread"]
          ["Toast bread in toaster"]
          1)})
;
