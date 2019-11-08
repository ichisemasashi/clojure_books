;---
; Excerpted from "Clojure Applied",
; published by The Pragmatic Bookshelf.
; Copyrights apply to this code. It may not be used to create training material, 
; courses, books, articles, and the like. Contact us if you are in doubt.
; We make no guarantees that this code is fit for any purpose. 
; Visit http://www.pragmaticprogrammer.com/titles/vmclojeco for more book information.
;---
(ns ch8.fixture
  (:require [clojure.test :refer :all]))

(defn create-db-conn [] ,,,)
(defn load-test-data [conn] ,,,)
(defn add-user [conn user] ,,,)
(defn check-user [conn user] ,,,)
(defn destroy-test-data [conn] ,,,)
(defn close-db-conn [conn] ,,,)

;
(deftest test-setup-db-add-user
  ;; setup
  (let [conn (create-db-conn)]
    (load-test-data conn)
    ;; test logic
    (add-user conn "user")
    (check-user conn "user")
    ;; tear down
    (destroy-test-data conn)
    (close-db-conn conn)))
;

;
(def ^:dynamic *conn*)

(defn db-fixture [test-function]
  (binding [*conn* (create-db-conn)]
    (load-test-data *conn*)
    (test-function)
    (destroy-test-data *conn*)
    (close-db-conn *conn*)))

(use-fixtures :each db-fixture)

(deftest test-db-add-user
  (add-user *conn* "user")
  (check-user *conn* "user"))
;