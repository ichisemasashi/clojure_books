;---
; Excerpted from "Clojure Applied",
; published by The Pragmatic Bookshelf.
; Copyrights apply to this code. It may not be used to create training material, 
; courses, books, articles, and the like. Contact us if you are in doubt.
; We make no guarantees that this code is fit for any purpose. 
; Visit http://www.pragmaticprogrammer.com/titles/vmclojeco for more book information.
;---

;
(require '[money :refer [+$ *$ zero-dollars])

;; catalog items
(defrecord CatalogItem   [num dept desc price])

;; checkout concerns
(defrecord Cart          [number customer line-items settled?])
(defrecord LineItem      [quantity item-number price])
(defrecord Customer      [name email membership-number])
(defrecord BillingRecord [customer transaction-number
                          payment-type amount tax total])

(def carts (ref {}))
(def billing-records (ref {}))
;


(defn- add-line-item-total
  "adds line item total into an existing subtotal."
  [subt li]
  (+$ subt (*$ (:quantity li) (:price li))))

(defn- lookup-tax-rate
  "find a customer's local tax rate"
  [cust]
  ,,, )

;
(defn cart->billing-record [c]
  (let [subtotal (reduce add-line-item-total
                    zero-dollars
                    (:line-items cart))
        rate     (lookup-tax-rate (:customer c))]
    (map->BillingRecord {
      :customer (:customer c)
      :amount   subtotal
      :tax      (*$ subtotal rate)
      :total    (+$ subtotal tax)
      })))

(defn settle
  "processes payment information, returns transaction number
   if payment was successful"
  [cart payment-info]
  ,,,)

(defn checkout
  "checkout a cart: bill the customer, persist the
   billing record, and settle the cart"
  [cart payment-info]
  (let [br     (cart->billing-record cart)
        tx-num (settle br payment-info)]
    (when tx-num
      (dosync
        (alter billing-records assoc :transaction tx-num br)
        (alter carts assoc-in [(:number cart) :settled?] tx-num))
    ,,, ))
;
