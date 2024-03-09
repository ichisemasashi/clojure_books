  
### Diving into Nested Maps

In the same way that you can use destructuring to dig into several layers of sequential data structures such as lists or vectors, you can also excavate several layers of maps. For example, given this two-level description of Jane Austen:

```clojure
(def austen {:name "Jane Austen"
             :parents {:father "George" :mother "Cassandra"}
             :dates {:born 1775 :died 1817}})
```

we can extract the names of her parents with this: 

```clojure
(let [{{dad :father mom :mother} :parents} austen]
  (println "Jane Austen's dad's name was" dad)
  (println "Jane Austen's mom's name was" mom))
```
  
A good way to look at this kind of two-level map destructuring is from the outside in. At the very outside, we have the basic let structure:

```clojure
(let [<<something-to-bind-to>> austen]
  ;; Do something with the data...
  )
```

Digging into the next level, we see that we have a map destructuring, one that is going to grab the `:parents` key:

```clojure
(let [{<<something-to-bind-parents-to>> :parents} austen]
  ;; Do something with the data... 
  )
```

And what are you going to do in the `something-to-bind-parents-to` spot? Yet another destructuring, of course, which takes us back to this:

```clojure
(let [{{dad :father mom :mother} :parents} austen]
  ;; Do something with the data...
  )
```

Once you have the idea, you can pull as little or as much as you want out of the maps. We could, for example, grab Jane’s name along with her mother’s name and her year of birth:

```clojure
(let [{name :name
      {mom :mother} :parents
      {dob :born} :dates} austen]
  (println name "was born in" dob)
  (println name "mother's name was" mom))
```

Just keep in mind that the order of things in the left side of the destructuring is reversed from what you would expect if you were creating a map. When it comes to destructuring, it’s value then key all the way down.



