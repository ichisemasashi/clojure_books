;---
; Excerpted from "Clojure Applied",
; published by The Pragmatic Bookshelf.
; Copyrights apply to this code. It may not be used to create training material, 
; courses, books, articles, and the like. Contact us if you are in doubt.
; We make no guarantees that this code is fit for any purpose. 
; Visit http://www.pragmaticprogrammer.com/titles/vmclojeco for more book information.
;---
(ns ch6.async
  (:require [clojure.core.async :as async :refer
             (chan dropping-buffer pipe mult tap pub sub mix admix toggle)]))

(defn make-comp-1 [])
(defn make-comp-2 [])
(defn get-output [comp1])
(defn get-input [comp2])

(comment
;
(let [component1 (make-comp-1)
      output-chan (get-output component1)
      component2 (make-comp-2)
      input-chan (get-input component2)]
  (pipe output-chan input-chan))
;
)

;
(defn connect-and-tap
  "Connect input and output and return channel logging
  data flowing between them."
  [input output]
  (let [m (mult input)
        log (chan (dropping-buffer 100))]
    (tap m output)
    (tap m log)
    log))
;


;
(defn assemble-chans []
  (let [in (chan 10)
        p (pub in :topic)
        news-ch (chan 10)
        weather-ch (chan 10)]
    (sub p :news news-ch)
    (sub p :weather weather-ch)
    [in news-ch weather-ch]))
;

;
(defn combine-channels
      [twitter-channel facebook-channel]
  (merge [twitter-channel facebook-channel] 100))
;

;
(defn mix-channels
      [twitter-channel facebook-channel out]
  (let [m (mix out)]
    (admix m twitter-channel)
    (admix m facebook-channel)
    (toggle m {twitter-channel {:mute true}
               facebook-channel {:mute true}})
    m))
;

