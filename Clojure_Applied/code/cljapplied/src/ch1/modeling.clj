;---
; Excerpted from "Clojure Applied",
; published by The Pragmatic Bookshelf.
; Copyrights apply to this code. It may not be used to create training material, 
; courses, books, articles, and the like. Contact us if you are in doubt.
; We make no guarantees that this code is fit for any purpose. 
; Visit http://www.pragmaticprogrammer.com/titles/vmclojeco for more book information.
;---
(ns ch1.modeling)

;; just a map

;
(def earth {:name       "Earth"
            :moons      1
            :volume     1.08321e12 ;; km^3
            :mass       5.97219e24 ;; kg
            :aphelion   152098232  ;; km, farthest from sun
            :perihelion 147098290  ;; km, closest to sun
            })
;

;; with type declaration

;
(def earth {:name       "Earth"
            :moons      1
            :volume     1.08321e12 ;; km^3
            :mass       5.97219e24 ;; kg
            :aphelion   152098232  ;; km, farthest from sun
            :perihelion 147098290  ;; km, closest to sun
            :type       :Planet    ;; entity type
            })
;

;; as a record

;
(defrecord Planet [name
                   moons
                   volume     ;; km^3
                   mass       ;; kg
                   aphelion   ;; km, farthest from sun
                   perihelion ;; km, closest to sun
                   ])
;

;
;; Positional factory function
(def earth
     (->Planet "Earth" 1 1.08321e12 5.97219e24 152098232 147098290))

;; Map factory function
(def earth
     (map->Planet {:name       "Earth"
                   :moons      1
                   :volume     1.08321e12
                   :mass       5.97219e24
                   :aphelion   152098232
                   :perihelion 147098290}))
;
