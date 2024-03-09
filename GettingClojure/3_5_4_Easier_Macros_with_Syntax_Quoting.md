    
### Easier Macros with Syntax Quoting
    
Let’s return to our `arithmetic-if` macro:

```clojure
(defmacro arithmetic-if [n pos zero neg]
  (list 'cond (list 'pos? n) pos
              (list 'zero? n) zero
              :else neg)) 
```           
              
We’re working pretty hard to produce that `cond` expression. After all, it’s just some boilerplate code with a few custom values slotted in here and there. If this sounds like a familiar problem, it should. People who develop web applications frequently need to mix context-specific data—perhaps today’s date or the current share price of Anacott Steel—into a skeleton of otherwise static HTML.

The solution in both situations is the same: use a template. You cook up the static bulk of your output as a sort of skeleton, which includes special markers that indicate "date here" and "share price there". And when you have the date and share price, you jam them in the right spots and you have your finished HTML—or Clojure code.

Macro author, let me introduce you to the Clojure code templating system, syntax quoting. The good news is that syntax-quoted templates are just plain old Clojure data—vectors and lists and the like—with a few special tweaks.  Tweak number one is that you set off syntax-quoted expressions with a backquote character, like this:

```clojure
;; Notice the backquote character at the start.
`(:a :syntax "quoted" :list 1 2 3 4)
```

When applied to ordinary values as it is in the example, the syntax quote acts like a regular quote: it prevents the expression from being evaluated. So our first syntax-quoted expression is the same as this:

```clojure
'(:a :syntax "quoted" :list 1 2 3 4)
```

Tweak number two is the "your value here" marker. Syntax quoting uses a ~ (tilde) character to mark the places where values should get inserted.

```clojure
;; Set up some values.
(def n 100)
(def pos "It's positive!")
(def zero "It's zero!")
(def neg "It's negative")
;; And plug them in the cond.
`(cond
  (pos? ~n) ~pos
  (zero? ~n) ~zero
  :else ~neg)
```

Run this code, and you will get something that looks like a very respectable `cond` statement:

```clojure
(cond
  (pos? 100) "It's positive!"
  (zero? 100) "It's zero!"
  :else "It's negative!")
```


Almost. One of the big issues with real-life macros is name confusion: is this `pos?` the one defined in the context where the macro was defined or is it the `pos?` defined in the context where the macro was called? To help minimize name confusion, syntax quoting outputs fully qualified symbols. So what you will really get out of the syntax-quoted expression in our example is this more verbose—but equivalent—version:

```clojure
(clojure.core/cond
  (clojure.core/pos? 100) "It's positive!"
  (clojure.core/zero? 100) "It's zero!"
  :else "It's negative")
```

which is exactly what we need for `arithmetic-if`. Here’s our macro redone with syntax quoting:

```clojure
(defmacro arithmetic-if [n pos zero neg]
  `(cond
    (pos? ~n) ~pos
    (zero? ~n) ~zero
    :else ~neg))
```

It’s so much clearer than the original:

```clojure
(defmacro arithmetic-if [n pos zero neg]
  (list 'cond (list 'pos? n) pos
              (list 'zero? n) zero
              :else neg))
```

As I say, the syntax-quoted implementation produces exactly the same results as the original `list`-encrusted one. It’s just easier to write—and read.
