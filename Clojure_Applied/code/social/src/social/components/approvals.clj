;---
; Excerpted from "Clojure Applied",
; published by The Pragmatic Bookshelf.
; Copyrights apply to this code. It may not be used to create training material, 
; courses, books, articles, and the like. Contact us if you are in doubt.
; We make no guarantees that this code is fit for any purpose. 
; Visit http://www.pragmaticprogrammer.com/titles/vmclojeco for more book information.
;---
(ns social.components.approvals
  (:require [com.stuartsierra.component :as component]
            [clojure.core.async :as async]))

;
(defrecord Approvals [approval-config     ;; approvals config
                      alert-chan          ;; incoming alert messages
                      knowledge-engine    ;; direct hook to knowledge engine
                      response-chan]      ;; output response messages pub/sub
  component/Lifecycle
  (start [component]
    (process-alerts alert-chan)
    (process-responses knowledge-engine response-chan)
    component)
  (stop [component]
    component))

(defn new-approvals [approval-config alert-chan response-chan]
  (map->Approvals {:approval-config approval-config
                   :alert-chan      alert-chan
                   :response-chan   response-chan}))
;
