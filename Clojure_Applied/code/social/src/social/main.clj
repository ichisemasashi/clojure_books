;---
; Excerpted from "Clojure Applied",
; published by The Pragmatic Bookshelf.
; Copyrights apply to this code. It may not be used to create training material, 
; courses, books, articles, and the like. Contact us if you are in doubt.
; We make no guarantees that this code is fit for any purpose. 
; Visit http://www.pragmaticprogrammer.com/titles/vmclojeco for more book information.
;---
(ns social.main
  (:require [immuconf.config :as ic]
            [com.stuartsierra.component :as component]
            [clojure.core.async :as async]
            [social.components.feed :as feed]
            [social.components.kengine :as kengine]
            [social.components.approvals :as approvals])
  (:gen-class))

;
(defn system [{:keys (twitter facebook knowledge approvals) :as config}]
  (let [twitter-chan (async/chan 100)
        twitter-response-chan (async/chan 10)
        facebook-chan (async/chan 100)
        facebook-response-chan (async/chan 10)
        alert-chan (async/chan 100)
        response-chan (async/chan 100)
        feed-chan (async/merge [twitter-chan facebook-chan])
        response-pub (async/pub response-chan :feed)]
    (async/sub response-pub :twitter twitter-response-chan)
    (async/sub response-pub :facebook facebook-response-chan)

    (component/system-map
      :twitter (feed/new-feed twitter twitter-chan twitter-response-chan)
      :facebook (feed/new-feed facebook facebook-chan facebook-response-chan)
      :knowledge-engine 
        (kengine/new-knowledge-engine knowledge feed-chan alert-chan)
      :approvals (component/using
                   (approvals/new-approvals approvals alert-chan response-chan)
                   [:knowledge-engine]))))
;

(defn start-app []
  (let [conf (ic/load "resources/config.edn" "user.edn")]
    (component/start-system (system conf))))


(defn stop-app [app])

(defn -main [& args]
  (start-app))
