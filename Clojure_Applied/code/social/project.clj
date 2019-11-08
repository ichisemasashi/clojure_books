;---
; Excerpted from "Clojure Applied",
; published by The Pragmatic Bookshelf.
; Copyrights apply to this code. It may not be used to create training material, 
; courses, books, articles, and the like. Contact us if you are in doubt.
; We make no guarantees that this code is fit for any purpose. 
; Visit http://www.pragmaticprogrammer.com/titles/vmclojeco for more book information.
;---
(defproject social "0.1.0-SNAPSHOT"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [org.clojure/core.async "0.1.346.0-17112a-alpha"]
;
                 [com.stuartsierra/component "0.2.3"]
;
                 [javax.mail/javax.mail-api "1.5.2"]
                 [environ "1.0.0"]
                 [levand/immuconf "0.1.0"]]
  :plugins [[lein-environ "1.0.0"]]
  :aot :all
  :main social.main
)
