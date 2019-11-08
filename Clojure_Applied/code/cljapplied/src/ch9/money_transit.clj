;---
; Excerpted from "Clojure Applied",
; published by The Pragmatic Bookshelf.
; Copyrights apply to this code. It may not be used to create training material, 
; courses, books, articles, and the like. Contact us if you are in doubt.
; We make no guarantees that this code is fit for any purpose. 
; Visit http://www.pragmaticprogrammer.com/titles/vmclojeco for more book information.
;---
;
(ns ch9.money-transit
  (:require [cognitect.transit :as transit]
            [ch1.money :refer :all])
  (:import [ch1.money Money Currency]
           [com.cognitect.transit WriteHandler ReadHandler TransitFactory]
           [java.io ByteArrayInputStream ByteArrayOutputStream]))

;
(def write-handlers {
  Currency
  (reify WriteHandler
    (tag [_ _] "currency")
    (rep [_ c] [(:divisor c) (:sym c) (:desc c)])
    (stringRep [_ _] nil)
    (getVerboseHandler [_] nil))

  Money
  (reify WriteHandler
    (tag [_ m] "money")
    (rep [_ m] [(:amount m) (:currency m)])
    (stringRep [_ _] nil)
    (getVerboseHandler [_] nil))})
;

;
(def read-handlers {
  "currency"
    (reify ReadHandler
      (fromRep [_ c] (apply ->Currency c)))
  "money"
    (reify ReadHandler
      (fromRep [_ m] (apply ->Money m)))})
;

(comment

;; for repl:

(require '[ch1.money :refer :all]
         '[ch1.money-transit :refer :all]
         '[cognitect.transit :as transit])

(import '[java.io ByteArrayInputStream ByteArrayOutputStream])
(def output (ByteArrayOutputStream. 4096))
(def writer (transit/writer output :json {:handlers write-handlers}))
(def cash-out [(->Money 9900 (:usd currencies))
               (->Money 8500 (:usd currencies))])
(transit/write writer cash-out)
(str output)

(def input (ByteArrayInputStream. (.toByteArray output)))
(def reader (transit/reader input :json {:handlers read-handlers}))
(def cash-in(transit/read reader))
cash-in

(= cash-in cash-out)

)
