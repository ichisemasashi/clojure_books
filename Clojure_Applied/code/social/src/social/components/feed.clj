;---
; Excerpted from "Clojure Applied",
; published by The Pragmatic Bookshelf.
; Copyrights apply to this code. It may not be used to create training material, 
; courses, books, articles, and the like. Contact us if you are in doubt.
; We make no guarantees that this code is fit for any purpose. 
; Visit http://www.pragmaticprogrammer.com/titles/vmclojeco for more book information.
;---
(ns social.components.feed
  (:require [clojure.core.async :as async]
;
            [com.stuartsierra.component :as component] 
;
            [social.domain :as domain])
  (:import [java.util Date]))

(defn rand-msg []
  (domain/map->Message
    {:text (rand-nth ["Blurb is so cool"
                      "I like Blurb"
                      "Blurb didn't work on Linux"])
     :from (rand-nth ["jeff" "sally" "mike"])
     :when (Date.)}))

(defn process-messages [status msg-chan]
  (future
    (while (= @status :running)
      (async/>!! msg-chan (rand-msg))
      (Thread/sleep 5000))
    (async/close! msg-chan)))

(defn handle-responses [status response-chan]
  )

;
(defrecord Feed [auth status msg-chan response-chan]
  component/Lifecycle
  (start [component]
    (reset! (:status component) :running)
    (process-messages status msg-chan)
    (handle-responses status response-chan)
    component)
  (stop [component]
    (reset! (:status component) :stopped)
    component))
;

(defn new-feed [auth msg-chan response-chan]
  (->Feed auth (atom :init) msg-chan response-chan))
