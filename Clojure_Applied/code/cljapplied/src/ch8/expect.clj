;---
; Excerpted from "Clojure Applied",
; published by The Pragmatic Bookshelf.
; Copyrights apply to this code. It may not be used to create training material, 
; courses, books, articles, and the like. Contact us if you are in doubt.
; We make no guarantees that this code is fit for any purpose. 
; Visit http://www.pragmaticprogrammer.com/titles/vmclojeco for more book information.
;---
(ns ch8.expect
  (:require [expectations :refer :all]))

;
(expect '(0 1 2 3 4) (range 5))
(expect '() (range 0))
;

;
(expect ClassCastException (doall (range "boom")))
;

;
(expect #"java.lang.String cannot be cast to java.lang.Number"
  (try (doall (range "boom"))
       (catch ClassCastException e (.getMessage e))))
;

;
(expect {:a {:b 2}} (update-in {:a {:b 1}} [:a :b] dec))
;