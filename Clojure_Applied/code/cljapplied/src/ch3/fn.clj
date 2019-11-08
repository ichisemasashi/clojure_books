;---
; Excerpted from "Clojure Applied",
; published by The Pragmatic Bookshelf.
; Copyrights apply to this code. It may not be used to create training material, 
; courses, books, articles, and the like. Contact us if you are in doubt.
; We make no guarantees that this code is fit for any purpose. 
; Visit http://www.pragmaticprogrammer.com/titles/vmclojeco for more book information.
;---
(ns ch3.fn)

;
(defn nth-page
      "Return up to page-size results for the nth (0-based) page
      of source."
  [source page-size page]
  (->> source
    (drop (* page page-size))
    (take page-size)))
;

;
(defn page-and-rest
  [source page-size]
  (split-at page-size source))
;

;
(defn smallest-n
  [planets n]
  (->> planets
    (sort-by :volume)
    (take n)))
;

;
(defn index-planets
  [planets]
  (group-by #(first (:name %)) planets))
;

;
(defn has-moons?
  [planet]
  (pos? (:moons planet)))
;

;
(defn split-moons
  [planets]
  (group-by has-moons? planets))
;