;---
; Excerpted from "Clojure Applied",
; published by The Pragmatic Bookshelf.
; Copyrights apply to this code. It may not be used to create training material, 
; courses, books, articles, and the like. Contact us if you are in doubt.
; We make no guarantees that this code is fit for any purpose. 
; Visit http://www.pragmaticprogrammer.com/titles/vmclojeco for more book information.
;---
(ns ch2.update
;
  (:require [medley.core :refer (map-keys)])
;
;
  (:require [medley.core :refer (map-vals)])
;
  )

;
(defn keywordize-entity
  [entity]
  (map-keys keyword entity))

(keywordize-entity {"name"       "Earth"
                    "moon"       1
                    "volume"     1.08321e12
                    "mass"       5.97219e24
                    "aphelion"   152098232
                    "perihelion" 147098290})
;; {:name "Earth",
;;  :moons 1,
;;  :volume 1.08321E12,
;;  :mass 5.97219E24,
;;  :aphelion 152098232,
;;  :perihelion 147098290}
;


(defn compute-calories [recipe] ,,,)

;
(defn- update-calories
  [recipe]
  (assoc recipe :calories (compute-calories recipe)))

(defn include-calories
  [recipe-index]
  (map-vals update-calories recipe-index))
;