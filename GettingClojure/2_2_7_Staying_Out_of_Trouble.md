
### Staying Out of Trouble

While lazy—and particularly unbounded— sequences are a powerful programming tool, dealing with them in the REPL has its issues. You certainly don’t want to try to print an entire infinite sequence, either explicitly, like this:

```clojure
user=> (def numbers (iterate inc 1)) 
#'user/numbers
user=> (println numbers) ; Say goodnight!
```

or implicitly:

```clojure
user=> numbers ; And that is it!
```

Never try to stare into the face of the infinite. This is where the `*print-length*` dynamic var and `set!`, both of which we saw back in "Chapter 8, Def, Symbols, and Vars, on page 85", come in handy:

```clojure
user=> (set! *print-length* 10)
10 
user=> numbers
(1 2 3 4 5 6 7 8 9 10 ...)
```

More subtly, you also need to be careful about side effects when dealing with lazy sequences, since your code might run at unexpected times. For example, imagine that we had the text for each chapter of a book stored in files with names like `chap1.txt` and `chap10.txt`. We could use the built-in Clojure function `slurp` (yes, that’s really the name) to read the contents of a chapter file into a string:

```clojure 
;; Get the contents of the file as a string.
(slurp "chap1.txt")
```

Add in some `map` and `take` cleverness and we can create a sequence of the text
of the first 10 chapters:

```clojure
(def chapters (take 10 (map slurp (map #(str "chap" % ".txt") numbers))))
```

Except that while we have just set up a pipeline for reading the files we haven’t actually read anything yet. Think about it: `take` and `map` both produce lazy sequences, so we need to actually do something with the elements of `chapters` before any slurping occurs. This can be a problem, because while Clojure’s collections are immutable, the contents of files are not.

> [!NOTE]
>
> **Slurp and Spit**
>
> The `slurp` function is the Clojure programmer’s universal "I need to read something" friend. Most notably, `slurp` will do exactly what you want if you hand it a URL: `(slurp "http://russolsen.com/index.html")`.
> If your interest lies more in writing than reading, there is `spit`, which will take a path and a string to write the string to that file, as in: `(spit "/tmp/chapter1.txt" "It was a dark...")`.
> As I say, these functions are your friends. The names … well, I guess you had to be there.


It’s for just these occasions that Clojure provides the `doall` function:


```clojure
;; Read the chapters NOW!
(doall chapters)
```

The `doall` function runs down your lazy sequence, accessing each element, and returns the sequence, effectively wringing the laziness out. If you want it done "right now", `doall` is your friend.

Along the same lines is `doseq`, which realizes each item in a lazy sequence one at a time but doesn’t try to hold onto the whole thing. Syntactically, `doseq` looks a lot like `for`:

```clojure
;; Read the chapters NOW!
(doseq [c chapters]
  (println "The chapter text is" c))
```

The difference is that while `for` returns a lazy sequence—not much help when we’re trying to de-lazy our sequence—`doseq` is emphatically eager.



