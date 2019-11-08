;---
; Excerpted from "Clojure Applied",
; published by The Pragmatic Bookshelf.
; Copyrights apply to this code. It may not be used to create training material, 
; courses, books, articles, and the like. Contact us if you are in doubt.
; We make no guarantees that this code is fit for any purpose. 
; Visit http://www.pragmaticprogrammer.com/titles/vmclojeco for more book information.
;---
(ns ch3.orbital
  (:import [ch1.modeling Planet]))

;; Gravitational constant: 6.674×10−11 N⋅m2/kg2
(def G ,,,)

;
(defn semi-major-axis
  "The planet's average distance from the star" [p]
  (/ (+ (:aphelion p) (:perihelion p)) 2))

(defn mu [mass] (* G mass))

(defn orbital-period
  "The time it takes for a planet to make a complete
  orbit around a mass, in seconds"
  [p mass]
  (* Math/PI 2
     (Math/sqrt (/ (Math/pow (semi-major-axis p) 3)
                   (mu mass)))))
;

;
(defn orbital-periods
  "Given a collection of planets, and a star, return the
  orbital periods of every planet."
  [planets star]
  (let [solar-mass (:mass star)]
    (map (fn [planet] (orbital-period planet solar-mass)) planets)))
;

;
(defn orbital-period-transformation
  "Create a map transformation for planet->orbital-period."
  [star]
  (map #(orbital-period % (:mass star))))
;

;
(defn orbital-periods
  [planets star]
  (sequence (orbital-period-transformation star) planets))
;

;
(defn orbital-periods
  [planets star]
  (into [] (orbital-period-transformation star) planets))
;

;
(defn orbital-periods
  [planets star]
  (into () (orbital-period-transformation star) planets))
;

;
(defn total-moons
  [planets]
  (reduce + 0 (map :moons planets)))
;

;
(defn total-moons
  [planets]
  (transduce (map :moons) + 0 planets))
;

;
(defn find-planet
  [planets pname]
  (reduce
    (fn [_ planet]
      (when (= pname (:name planet))
        (reduced planet)))
    planets))
;

;
(defn planet?
  [entity]
  (instance? Planet entity))

(defn total-moons
  [entities]
  (reduce + 0
    (map :moons
      (filter planet?
        entities))))
;

;
(defn total-moons
  [entities]
  (->> entities
    (filter planet?)
    (map :moons)
    (reduce + 0)))
;

;
(def moons-transform
  (comp (filter planet?) (map :moons)))

(defn total-moons
  [entities]
  (transduce moons-transform + 0 entities))
;
