
### The Trouble with Maps

The great thing about Clojure maps—those lovely `{:key "value"}` creatures—is that they are very flexible. You can use a map to associate just about any key with essentially any value. It’s this "I can deal with anything" flexibility that makes maps so useful and ubiquitous in Clojure programs.

But this flexibility of maps does not come for free. Part of the cost of flexibility can be measured in CPU seconds at runtime. Maps are wonderfully speedy, but the "deal with anything flexibility" does come with a runtime penalty. Since maps need to deal with arbitrary keys, they are a bit slower than a data structure designed to deal with just these keys. Mostly the speed penalty doesn’t matter, but sometimes—for example, when you’re trying to process huge amounts of data—it matters a lot.

A second—and more commonly felt—drawback of maps is their cost in terms of code coherence and documentation. Since you can put anything into any map, the only way to discern the intent of maps like these:

record/examples.clj
```clojure
(let [watson-1 (get-watson-1)
      watson-2 (get-watson-2)]
      ;; Do something with our watsons...
      )
```

is to see if you have a fictional character or a Jeopardy!-winning supercomputer:

```clojure
;; A fictional character.
(defn get-watson-1 []
  {:name "John Watson"
   :appears-in "Sign of the Four"
   :author "Doyle"})
  ;; A Jeopardy playing computer.
(defn get-watson-2 [] {:cpu "Power7" :no-cpus 2880 :storage-gb 4000})
```

