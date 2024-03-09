
### In the Wild

Since garden-variety vars are the mortar that binds Clojure code together, they are everywhere in real-world code. In fact, def figures into some of the first code that runs when [Clojure boots up](https://github.com/clojure/clojure/blob/master/src/clj/clojure/core.clj).
Here are slightly simplified versions of a couple of defs that get run very early on:

```clojure
(def second (fn second [x] (first (next x))))
(def ffirst (fn ffirst [x] (first (first x))))
```
   
These are just two handy functions: `second`, which pulls the second item off of a collection, and `ffirst`, which takes the first item from a collection—which itself should be a collection—and pulls the first item off of it. You might wonder why this code goes to the trouble of using `def` and `fn` instead of the sleeker `defn`. The answer is simple: these two functions get defined before `defn`.

Dynamic vars are less common, but still out there. For example, if you’re dealing with large collections in the REPL you may not want to see them printed in their full glory. Fortunately, Clojure provides a dynamic var called `*print-length*`, which limits how much of a collection gets printed. As you might imagine, somewhere in the [depths of Clojure itself](https://github.com/clojure/clojure/blob/master/src/clj/clojure/core_print.clj), `*print-length*` is set up as a dynamic var. Here it is in all its well-documented glory:



```clojure 
(def ^:dynamic
   ^{:doc "*print-length* controls how many items of each collection the
    printer will print. If it is bound to logical false, there is no
    limit. Otherwise, it must be bound to an integer indicating the maximum
    number of items of each collection to print. If a collection contains
    more items, the printer will print items up to the limit followed by
    '...' to represent the remaining items. The root binding is nil
    indicating no limit."
     :added "1.0"}
  *print-length* nil)
```

A bit later, just before actually executing your program, Clojure sets up a binding for `*print-length*`, conceptually like this:

```clojure
(binding [*print-length* nil]
  (run-your-code))
```

That brings us to `set!`, which changes the value of a dynamic var from "inside" the binding. So if you have this vector:

```clojure
user=> (def books ["Emma" "2001" "Jaws" "Oliver Twist"])
```


and you `set!` `*print-length*` to `2`:

```clojure
user=> (set! *print-length* 2)
```

you will only see the first couple of items of your vector:

```clojure
user=> books
["Emma" "2001" ...]
```

There are a couple of other dynamic vars that can improve your REPL experience. For example, `*1`—and yes, it only has the left earmuff—is always bound to the last result you got from the REPL:

```clojure
user=> (+ 2 2)
4
user=> *1
4
```

Similarly, `*2` is bound to the second-to-last result, and `*3` to the one before that:

```clojure
user=> "Austen"
"Austen"
user=> "King"
"King"
user=> "Orwell"
"Orwell"
user=> *3
"Austen"
```


Finally there is `*e`, which the REPL binds to the last exception:

```
user=> (/ 1 0)
ArithmeticException Divide by zero clojure.lang.Numbers.divide
(Numbers.java:158)
user=> *e
#error {
 :cause "Divide by zero"
 :via
 [{:type java.lang.ArithmeticException
   :message "Divide by zero"
   :at [clojure.lang.Numbers divide "Numbers.java" 158]}]
 :trace
 [[clojure.lang.Numbers divide "Numbers.java" 158]
 << And so on for quite some time... >>
```


Now you always have a reminder of your last programmatic screw-up!



