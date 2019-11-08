;---
; Excerpted from "Clojure Applied",
; published by The Pragmatic Bookshelf.
; Copyrights apply to this code. It may not be used to create training material, 
; courses, books, articles, and the like. Contact us if you are in doubt.
; We make no guarantees that this code is fit for any purpose. 
; Visit http://www.pragmaticprogrammer.com/titles/vmclojeco for more book information.
;---
(ns ch5.pipeline
  (:require [clojure.core.async :as async]))

;
;; parse message into set of words
(def parse-words (map #(set (clojure.string/split % #"\s"))))

;; filter messages that contain a word of interest
(def interesting (filter #(contains? % "Clojure")))

;; detect sentiment based on different word lists
(defn match [search-words message-words]
  (count (clojure.set/intersection search-words message-words)))
(def happy (partial match #{"happy" "awesome" "rocks" "amazing"}))
(def sad (partial match #{"sad" "bug" "crash"}))
(def score (map #(hash-map :words %1
                           :happy (happy %1)
                           :sad (sad %1))))
;

;
(defn sentiment-stage
  [in out]
  (let [xf (comp parse-words interesting score)]
    (async/pipeline 4 out xf in)))
;

;
(defn interesting-stage
  [in intermediate]
  (let [xf (comp parse-words interesting)]
    (async/pipeline 4 intermediate xf in)))

(defn score-stage
  [intermediate out]
  (async/pipeline 1 out score intermediate))

(defn assemble-stages
  [in out]
  (let [intermediate (async/chan 100)]
    (interesting-stage in intermediate)
    (score-stage intermediate out)))
;