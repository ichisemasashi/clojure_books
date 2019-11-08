;---
; Excerpted from "Clojure Applied",
; published by The Pragmatic Bookshelf.
; Copyrights apply to this code. It may not be used to create training material, 
; courses, books, articles, and the like. Contact us if you are in doubt.
; We make no guarantees that this code is fit for any purpose. 
; Visit http://www.pragmaticprogrammer.com/titles/vmclojeco for more book information.
;---
(ns ch8.check
;
  (:require [clojure.test.check :as tc]
            [clojure.test.check.generators :as gen]
            [clojure.test.check.properties :as prop])
;
  (:require [ch1.convert :refer :all]
            [ch1.validate :refer :all])
  )

;
(def range-count-eq-n
  (prop/for-all [n gen/int]
    (= n (count (range n)))))
;

;
(def range-count-eq-n-pos
  (prop/for-all [n gen/pos-int]
    (= n (count (range n)))))
;

;
(def gen-food
  (gen/elements ["flour" "sugar" "butter"]))
;

;
(def gen-unit
  (gen/elements [:oz :lb]))
;

;
(def gen-ingredient
  (gen/fmap map->Ingredient
    (gen/hash-map
      :name gen-food
      :quantity gen/s-pos-int
      :unit gen-unit)))
;

;
(def identity-conversion-prop
  (prop/for-all [u gen-unit
                 n gen/s-pos-int]
    (= n (convert u u n))))
;

;
(def conversion-order-prop
  (prop/for-all [u1 gen-unit
                 u2 gen-unit
                 u3 gen-unit
                 u4 gen-unit
                 n gen/s-pos-int]
    (= (->> n (convert u1 u2) (convert u2 u3) (convert u3 u4))
       (->> n (convert u1 u3) (convert u3 u2) (convert u2 u4)))))
;

;
(def roundtrip-conversion-prop
  (prop/for-all [u1 gen-unit u2 gen-unit
                 q gen/s-pos-int]
    (and (= q
            (convert u1 u2 (convert u2 u1 q))
            (convert u2 u1 (convert u1 u2 q))))))
;

;
(defn add-and-convert [i1 i2 i3 output-unit]
  (let [{:keys [quantity unit]} (ingredient+ i1 (ingredient+ i2 i3))]
    (convert unit output-unit quantity)))

(def associative-ingredient+-prop
  (prop/for-all [i1 gen-ingredient
                 i2 gen-ingredient
                 i3 gen-ingredient]
    (= (add-and-convert i1 i2 i3 (:unit i1))
       (add-and-convert i3 i1 i2 (:unit i1))
       (add-and-convert i2 i1 i3 (:unit i1)))))
;