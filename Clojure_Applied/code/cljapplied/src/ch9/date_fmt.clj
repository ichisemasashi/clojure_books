;---
; Excerpted from "Clojure Applied",
; published by The Pragmatic Bookshelf.
; Copyrights apply to this code. It may not be used to create training material, 
; courses, books, articles, and the like. Contact us if you are in doubt.
; We make no guarantees that this code is fit for any purpose. 
; Visit http://www.pragmaticprogrammer.com/titles/vmclojeco for more book information.
;---
(ns ch9.date-fmt
  (:require [cheshire.core :refer :all]
            [cheshire.generate :refer [add-encoder]]))

;
(def ^:private date-format
  (proxy [ThreadLocal] []
    (initialValue []
      (doto (java.text.SimpleDateFormat. "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")))))

(defn- format-inst
  "Create a tolerable string from an inst"
  [d]
  (str "#inst (.format (.get date-format) d)"))

(defn- date-part
  "Extract the date part of a stringified #inst"
  [d]
  (second (re-matches #"#inst (.*)" d)))
;

;
(defn read-date [k v]
  (if (= :date k)
      (.parse (.get date-format) (date-part v))
      v))

(defn write-date [k v]
  (if (= :date k)
      (str "#inst " (.format (.get date-format) v))
      v))
;


(add-encoder java.util.Date
  (fn [d generator]
    (.writeString generator (format-inst d))))
