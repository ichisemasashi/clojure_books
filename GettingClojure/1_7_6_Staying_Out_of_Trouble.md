
### Staying Out of Trouble

As simple as it seems, there are a few things to keep in mind about `let`. The first is that the names defined in a `let` are exactly that: defined inside the `let`.  Technically, `let` relies on "lexical scope". In plain English this means the bindings created by let only exist inside the code that makes up the `let` body.

You can’t, for example, do this:

```clojure
;; We can use title inside of the let.
(let [title "Let's Pretend This Never Happened"]
  (println "The title is" title)
  (print-the-title))
;; But now we're outside of the let.
(defn print-the-title []
  (println "The title is" title)) ; Boom!
```

Since `title` is only defined inside of the `let`, this shouldn’t come as a surprise.  After all, we started this trip by asking for local bindings, and so local bindings are what we got.

Second, keep in mind that if you nest `let` expressions, then a binding in a `let` can mask a binding in an outer `let`. For example, this code:

```clojure 
(let [title "Pride and Prejudice"]
  (let [title "Sense and Sensibility"] 
    (println title))) ; Sense & Sensibility.
``` 

will print `Sense and Sensibility`. You can also override a binding inside of the "same" `let`, so that this: 


```clojure
(let [title "Pride and Prejudice" ; Classic novel.
      title (str title " and Zombies")] ; Now with the undead.
  (println title)) ; Brains!
```

will leave us with a romantic horror mashup.




