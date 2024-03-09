
### Reading and Evaluating

To make all this theory a bit more real, let’s dive into the machinery that Clojure uses to read data and execute code. Actually, calling it machinery is overstating things since we’re talking about two functions, one mundane and one wonderful. The mundane function is `read` and it does exactly what its name suggests: it reads data. Feed `read` a character-producing input stream—perhaps an open file or a network connection or your terminal—and it will read and return one Clojure value. Conveniently, if you just call `read` without any parameters, it will read from standard input. So if you call (read) in the REPL like this:

```clojure
user=> (read)
```

then read will sit there quietly, waiting for you to type something in. So enter `55`, and `read` will return the number after `54`. Alternatively, if you type in `"hello"`, `read` will return a five-character string. And if you type in the following:

```clojure
(defn print-greeting [preferred-customer]
  (if preferred-customer (println "Welcome back!")))
```

you will get a four-element list that looks suspiciously like a Clojure function definition but is nevertheless just some data. 

Along with `read`, Clojure also comes equipped with `read-string`, a function that parses the characters in a string into a Clojure value. Thus we could get the same four-element list like this:

```clojure
;; A complicated string with some escaped quotes.
(def s
  "(defn print-greeting [preferred-customer]
    (if preferred-customer (println \"Welcome back!\")))")
;; A four-element list. 
(read-string s)
```

Which brings us to the wonderful function, `eval`. If the `read` function’s job is to turn characters into data structures, then it falls to `eval` to turn data structures into action:

```clojure
;; A three element list.
(def a-data-structure '(+ 2 2))
;; The number 4.
(eval a-data-structure)
```

The wonderful thing about `eval` is that it takes the data you pass in, which should look like Clojure code, and "compiles and runs it" as Clojure code:

```clojure
;; Bind some-data to a list
(def some-data
  '(defn print-greeting [preferred-customer]
    (if preferred-customer (println "Welcome back!"))))
;; At this point we have some-data defined,
;; but not the print-greeting function.
;; Now let's eval some-data...
(eval some-data)
;; And now print-greeting is defined!
(print-greeting true)
```

Essentially, `eval` attempts to evaluate whatever data you pass in "as Clojure code". Sometimes that evaluation is trivial. Numbers, strings, and keywords just evaluate to themselves:

```clojure
(eval 55)      ; Returns the number after 54.
(eval :hello)  ; Returns the keyword :hello
(eval "hello") ; And a string.
```

But you can also have `eval` evaluate symbols or call functions:

```clojure
(def title "For Whom the Bell Tolls")
;; Get hold of the unevaluated symbol 'title...
(def the-symbol 'title)
;; ...and evaluate it.
(eval the-symbol)
;; While a list gets evaluated as a function call.
(eval '(count title))
```

The key to understanding `eval` is not so much what it does—it "runs stuff as code"—as what it takes in: ordinary Clojure lists, vectors, keywords, and symbols. You can, for example, construct some code on the fly—using garden-variety Clojure functions like `list` and `vector`—and evaluate it with `eval`. So this is yet another way to define and then call `print-greeting`:

```clojure
(def fn-name 'print-greeting)
(def args (vector 'preferred-customer))
(def the-println (list 'println "Welcome back!"))
(def body (list 'if 'preferred-customer the-println))
(eval (list 'defn fn-name args body))
(eval (list 'print-greeting true))
```



