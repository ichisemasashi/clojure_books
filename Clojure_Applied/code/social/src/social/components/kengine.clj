;---
; Excerpted from "Clojure Applied",
; published by The Pragmatic Bookshelf.
; Copyrights apply to this code. It may not be used to create training material, 
; courses, books, articles, and the like. Contact us if you are in doubt.
; We make no guarantees that this code is fit for any purpose. 
; Visit http://www.pragmaticprogrammer.com/titles/vmclojeco for more book information.
;---
(ns social.components.kengine
  (:require [com.stuartsierra.component :as component]
            [clojure.core.async :as async]
            [clojure.string :as str]))

(defn watch-feeds [feed-chan alert-chan]
  ;; pull messages from feed-chan
  ;; process rules
  ;; push messages to alert-chan
  )

;
(defrecord KnowledgeEngine 
  [ke-config feed-chan alert-chan rules]

  component/Lifecycle
  (start [component]
    (watch-feeds feed-chan alert-chan)
    component)
  (stop [component]
    component))

(defn new-knowledge-engine
  "Create a new knowledge engine with no initial rules"
  [ke-config feed-chan alert-chan]
  (->KnowledgeEngine ke-config feed-chan alert-chan 
                     (atom (:rule-set ke-config))))
;

(defn get-rules
  "Get current rule set"
  [ke]
  @(:rules ke))

;
(defn add-rule
  "Add rule to set"
  [ke rule]
  (swap! (:rules ke) conj rule))
;

(defn remove-rule
  "Remove rule from set"
  [ke rule]
  (swap! (:rules ke) disj rule))

(defn- match
  [[match replacement] request]
  (str/replace (:text request) match replacement))

(defn apply-rules
  "Apply the current rules to the request and produce an alert"
  [ke request]
  (let [matches (sort-by :priority (filter #(match % request) @(:rules ke)))]
    (if (pos? (count matches))
      (first matches)
      nil)))
