
### Staying Out of Trouble

Like most programming tools, destructuring works best when mixed with a healthy dose of common sense. The real value of destructuring is that it makes those deep dives into data structures easier to code and easier to read. There is something about the "look for the value here" approach of destructuring that clicks with the human brain. Or it clicks to a point: digging too far into a complex data structure with a single destructuring expression is one sure way to make your code confusing.

For example, if you had a vector of reader information, like this:

```clojure
[{:name "Charlie", :fav-book {:title "Carrie", :author ["Stephen" "King"]}}
 {:name "Jennifer", :fav-book {:title "Emma", :author ["Jane" "Austen"]}}]
```

and you wanted the full name of the author of the second reader’s favorite book—say that three times fast—you might write this:

```clojure
(defn format-a-name [[_ {{[fname lname] :author} :fav-book}]]
  (str fname " " lname))
``` 

Most programmers would have to stare at this gem for a few minutes in order to understand it. A better approach might be to go at this problem in a couple of stages, like this:

```clojure
(defn format-a-name [[_ second-reader]]
  (let [author (-> second-reader :fav-book :author)]
    (str (first author) " " (second author))))
```
   
There’s nothing like some intention-revealing names to, well, reveal your intentions.
   
   
The other thing to keep in mind about destructuring is that it’s purely a creature of local bindings. While you can use destructuring with function parameters and with `let`, you can’t use destructuring directly in a def. So this:

```clojure
;; No!!
(def author {:name "Jane Austen" :born 1775})
(def author-name [{n :name} author])
```

will not compile. But don’t despair. This will:

```clojure
(def author-name
  (let [{n :name} author] n))
```

A little let goes a long way.


