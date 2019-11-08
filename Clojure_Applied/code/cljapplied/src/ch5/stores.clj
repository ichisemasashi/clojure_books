;---
; Excerpted from "Clojure Applied",
; published by The Pragmatic Bookshelf.
; Copyrights apply to this code. It may not be used to create training material, 
; courses, books, articles, and the like. Contact us if you are in doubt.
; We make no guarantees that this code is fit for any purpose. 
; Visit http://www.pragmaticprogrammer.com/titles/vmclojeco for more book information.
;---
(ns ch5.stores)

;; stubs
(defn query [store product] ,,,)
(defn long-running-task [] ,,,)


;
(defn query-stores [product stores]
  (for [store stores]
    (query store product)))
;

;
(defn query-stores [product stores]
  (for [store stores]
    (future (query store product))))
;

;
(defn query-stores [product stores]
  (let [futures (doall
                  (for [store stores]
                    (future (query store product))))]
    (map deref futures)))
;

;
(defn launch-timed []
  (let [begin-promise (promise)
        end-promise (promise)]
    (future (deliver begin-promise (System/currentTimeMillis))
      (long-running-task)
      (deliver end-promise (System/currentTimeMillis)))
    (println "task begin at" @begin-promise)
    (println "task end at" @end-promise)))
;