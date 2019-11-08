;---
; Excerpted from "Clojure Applied",
; published by The Pragmatic Bookshelf.
; Copyrights apply to this code. It may not be used to create training material, 
; courses, books, articles, and the like. Contact us if you are in doubt.
; We make no guarantees that this code is fit for any purpose. 
; Visit http://www.pragmaticprogrammer.com/titles/vmclojeco for more book information.
;---
(ns social.util.mail
  (:require [social.util.properties :as prop])
  (:import [javax.mail Session Message Authenticator PasswordAuthentication Transport Message$RecipientType Folder Flags$Flag]
           [javax.mail.internet MimeMessage InternetAddress]
           [java.util Properties]))

(def defaults {"mail.imap.host" "imap.gmail.com"
               "mail.imap.port" "993"
               "mail.imap.connectiontimeout" "5000"
               "mail.imap.timeout" "5000"
               "mail.store.protocol" "imaps"})

(defn new-session [imap-config user pw]
  (let [props (prop/map->properties (merge defaults imap-config))]
    (Session/getInstance
      props
      (proxy [Authenticator] []
        (getPasswordAuthentication []
          (PasswordAuthentication. user pw))))))

