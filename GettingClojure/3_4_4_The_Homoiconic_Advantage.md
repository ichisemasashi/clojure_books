
### The Homoiconic Advantage

So there’s our answer: Clojure’s syntax is the way it is because it’s amphibious, equally at home representing code and data. Having a single text format along with a single in-memory representation of both code and data is not just elegant; it also has some serious practical advantages. For example, writing Clojure code-analysis tools is very straightforward. Need to read a file full of Clojure code? No problem:

```clojure
(ns codetool.core
  (:require [clojure.java.io :as io]))

(defn read-source [path]
  (with-open [r (java.io.PushbackReader. (io/reader path))]
    (loop [result []]
      (let [expr (read r false :eof)]
        (if (= expr :eof)
          result
          (recur (conj result expr)))))))
```

Call `read-source` with the path to a Clojure source file, and you will get back a sequence of all of the expressions in that file, parsed into the lists and vectors that you already know how to use.

If you look closely at `read-source`, you will see that at its center is a slightly more elaborate call to `read`: `(read r false :eof)`. The extra arguments tell `read` to read from somewhere besides standard input (that’s the `r`), and to return the keyword `:eof` when it hits the end of the file. But the truly remarkable thing about `read-source` is that most of it is devoted to the mundane tasks of opening the file and managing the results. The actual parsing of the Clojure source is all bundled up in that call to `read`.

Even more remarkable is that the combination of `read` and `eval` makes writing a REPL so easy that sooner or later every Clojure programmer gives it a go.  Here’s my shot at it:

```clojure
(defn russ-repl []
  (loop []
    (println (eval (read)))
    (recur)))
```

Just read an expression, evaluate it, print the result, and loop. Thanks mostly to `eval`, the REPL is the rare acronym that is nearly a program.


> [!NOTE]
>
> **REPR?**
>
> Strictly speaking the loop in `russ-repl` is not necessary. Remove it and the `recur` will recursively call the function. It’s there because the L in REPL demands it.



