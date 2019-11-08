;---
; Excerpted from "Clojure Applied",
; published by The Pragmatic Bookshelf.
; Copyrights apply to this code. It may not be used to create training material, 
; courses, books, articles, and the like. Contact us if you are in doubt.
; We make no guarantees that this code is fit for any purpose. 
; Visit http://www.pragmaticprogrammer.com/titles/vmclojeco for more book information.
;---
(ns catalog-import.core)

(require '[clojure.data.csv :as csv]
         '[clojure.java.io :as io])

;
(defrecord CatalogItem [num dept desc price])

(defn read-catalog [file]
  (with-open [in-file (io/reader file)]
    (doall
     (csv/read-csv in-file))))

(def catalog (atom []))
;

(defn make-catalog-item [fields]
  (apply ->CatalogItem fields))

;
(defn import-catalog-map [data]
  (vec (map make-catalog-item data)))
;

;
(defn import-catalog [data]
  (reduce #(conj %1 %2) [] data))
;

;
(defn import-catalog-fast [data]
  (persistent!
    (reduce #(conj! %1 %2) (transient []) data)))
;

(defn- reset-catalog! []
  (reset! catalog []))

(defn -main []
  (let [item-data (read-catalog "resources/long.csv")]
    (time (import-catalog item-data))
    (time (import-catalog-fast item-data))

    ;; Want to see how map compares?
    ;; (reset-catalog!)
    ;; (time (import-catalog-map))
    ;; (println (str "imported " (count @catalog) " items"))
    ))
