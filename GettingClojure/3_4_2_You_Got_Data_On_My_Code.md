
### You Got Data On My Code!

If you have read this far you probably noticed something odd about the syntax of Clojure code: it looks a lot like the syntax of Clojure data literals. If, for example, you started with this bit of nonsensical Clojure data:

read/examples.clj
```clojure
;; Just some data: Note the quote.
'(helvetica times-roman [comic-sans]
  (futura gil-sans
    (courier "All the fonts I have loved!")))
```

you know you have a four-element list that contains a couple of symbols along with a vector and another list. But by swapping out the symbols and changing contents of the string, you can transform that data into something convincingly codelike:

```clojure
;; Still just data -- note the quote.
'(defn print-greeting [preferred-customer]
  (if preferred-customer
    (println "Welcome back!")))
```

This last example is still just a four-element list of data—notice the quote at the front—but it’s also a dead ringer for a Clojure function definition. You can, in fact, turn your data into actual code by removing the quote:

```clojure
;; Now this is code!
(defn print-greeting [preferred-customer]
  (if preferred-customer
    (println "Welcome back!")))
```


This little journey from data to code underlines the fundamental idea of Clojure syntax: Clojure code looks like Clojure data because in Clojure code is data.  Clojure uses the same syntax and data structures to represent both code and data. So Clojure function calls don’t just look like lists; they are lists. The arguments to your function definitions don’t get wrapped in things that look like vectors; they are vectors. Languages that support this sort of code equals data equation are said to be homoiconic.

