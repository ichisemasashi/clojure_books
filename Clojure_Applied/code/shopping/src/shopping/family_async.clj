;---
; Excerpted from "Clojure Applied",
; published by The Pragmatic Bookshelf.
; Copyrights apply to this code. It may not be used to create training material, 
; courses, books, articles, and the like. Contact us if you are in doubt.
; We make no guarantees that this code is fit for any purpose. 
; Visit http://www.pragmaticprogrammer.com/titles/vmclojeco for more book information.
;---
(ns shopping.family-async
  (:require [clojure.core.async :as async :refer [>!! >! <! go go-loop chan]]
            [shopping.store :as store]))

(def my-kids #{:alice :bobbi :cindy :donnie})

(defn born! [new-kid]
  (alter-var-root #'my-kids conj new-kid))

(defn notify-parent
  [k r _ nv]
  (if (contains? nv :candy)
    (println "there's candy in the cart!")))

;
(def shopping-list (ref #{}))
(def assignments (ref {}))
(def shopping-cart (ref #{}))

(defn init []
  (store/init {:eggs 2 :bacon 3 :apples 3
               :candy 5 :soda 2 :milk 1
               :bread 3 :carrots 1 :potatoes 1
               :cheese 3})
  (dosync
    (ref-set shopping-list #{:milk :butter :bacon :eggs
                           :carrots :potatoes :cheese :apples})
    (ref-set assignments {})
    (ref-set shopping-cart #{})))
;

(defn maybe?
  "invoke f 1/3 of the time"
  [f]
  (if (= 0 (rand-int 3))
    (f)))

(defn collect-item-from-child
  [m child]
  (let [item (child  m)]
    (dissoc m child)
    (item)
    (assoc m :assigns (dissoc (:assigns m) child))
    (assoc m :cart (conj (:cart m) item))))

(defn disj-1
  [m k item]
  (assoc m k (disj (k m) item)))

;
(defn assignment
  [child]
  (get @assignments child))

(defn buy-candy []
  (dosync
   (commute shopping-cart conj (store/grab :candy))))

(defn collect-assignment
  [child]
  (let [item (assignment child)]
    (dosync
     (alter shopping-cart conj item)
     (alter assignments dissoc child)
     (ensure shopping-list) ;; not needed
                            ;; included as an example
     )
    item))

(defn assign-item-to-child [child]
  (let [item (first @shopping-list)]
    (dosync
      (alter assignments assoc child item)
      (alter shopping-list disj item))
    item))
;

;
(defn dawdle
  "screw around, get lost, maybe buy candy"
  []
  (let [t (rand-int 5000)]
    (Thread/sleep t)
    (maybe? buy-candy)
    ))
;


;
(defn send-child-for-item
  "eventually shop for an item"
  [child item q]
  (println child "is searching for" item)
  (dawdle)
  (collect-assignment child)
  (>!! q child))

(defn report []
  (println "store inventory" @store/inventory)
  (println "shopping-list" @shopping-list)
  (println "assignments"   @assignments)
  (println "shopping-cart" @shopping-cart))

(defn go-shopping []
  (init)
  (report)
  (let [kids (chan 10)]
    (doseq [k my-kids]
      (>!! kids k))
    (go-loop [kid (<! kids)]
      (if (seq @shopping-list)
        (do
          (go
            (send-child-for-item kid (assign-item-to-child kid) kids))
          (recur (<! kids)))
        (do
          (println "done shopping.")
          (report))))))
;


;

(comment

;
(def shopping-cart (ref #{}
  :validator #(not (contains? % :candy))))
;

;
(defn notify-parent
  [k r _ nv]
  (if (contains? nv :candy)
    (println "there's candy in the cart!")))
;

;
(add-watch shopping-cart :candy notify-parent)
;
)
