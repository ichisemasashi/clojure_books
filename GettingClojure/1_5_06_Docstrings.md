
### Docstrings

One of the challenges of programming is that code has two audiences, one electronic and the other human. On one hand, code is the medium that we use to order the computer around, and we have to do that ordering with explicit, sometimes painful, precision. But code is also literature. It needs to communicate its intent and workings to the humans who maintain and enhance it.

The traditional way you help the people understand code is via comments. A comment is always as close as the semicolon key. Thus, if we wanted to add a bit of explanation to our average function we might do something like this:
    
```clojure
;; Return the average of the two parameters.
(defn average [a b]
  (/ (+ a b) 2.0))
```

As every programmer knows, the beauty of comments is that they disappear early on in parsing. Thus you can gab away inside a comment, confident that whatever you say will be forgotten by the time the compiler gets down to business. But the early demise of comments has a downside. We don’t write comments for decorative purposes; we write them because we’re trying to say something helpful about the code. Wouldn’t it be nice if we could somehow hang those helpful descriptions on the function?

Helpful indeed, which is why Clojure provides documentation strings. A documentation string—or "docstring" for short—is a regular string that you can insert just after the function name in your defn:

```clojure
(defn average
  "Return the average of a and b."
  [a b]
  (/ (+ a b) 2.0))
```

Clojure will store the string along with the function. You can get at the docstring for any function with the built-in `doc` macro. To get at the docstring in the REPL, you just use `doc`:

```
user=> (doc average)
-------------------------
user/average
([a b])
  Return the average of a and b.
```

Note that Clojure even added the argument list to the documentation for free.

> [!NOTE]
>
> **Docstrings for the House!**
>
> Docstrings are not just for functions. Other members of the Clojure menagerie, creatures like macros and records—which we’ll meet in later chapters—also support docstrings.
> So you can also supply a docstring in a plain old `def: (def ISBN-LENGTH "Length of an ISBN code." 13)`.


Adding docstrings to multi-arity functions is also easy. We could, for example, implement a well-documented number-averaging function that can average two or three numbers, like this:

```clojure
(defn multi-average
  "Return the average of 2 or 3 numbers."
  ([a b]
    (/ (+ a b ) 2.0))
  ([a b c]
    (/ (+ a b c) 3.0)))
```

Just remember that the docstring always comes after the function name.


