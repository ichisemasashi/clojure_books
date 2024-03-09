
### The Very Basics

To get started you need to install some development tools. There’s a wide selection of Clojure development environments and build tools available—everything from [the `clj` tool](https://clojure.org/guides/deps_and_cli) that comes packaged with Clojure starting with version 1.9 to [the IntelliJ-based](https://www.jetbrains.com/idea) [Cursive](https://cursive-ide.com) to [Emacs](https://www.gnu.org/software/emacs) and [Cider](https://github.com/clojure-emacs/cider) and [boot](https://github.com/boot-clj/boot). But in this book we’re mostly going to stick to the popular Clojure development tool Leiningen. So if you haven’t already, head over to the [Leiningen](https://leiningen.org) website and follow the installation instructions for your operating system.  While you’re there, you might also note that it’s pronounced LINE-ing-en.

By tradition, the first program you write when learning a programming language simply prints a greeting. Here’s the Clojure version:

hello/examples.clj
```clojure
(println "Hello, world!")
; Say hi.
```

To keep things simple, we’ll take our first stab at Hello, World in the Clojure REPL, a handy utility that lets you type in code and see it evaluated right here, right now. The command to start a REPL with Leiningen is as follows:

```bash
$ lein repl
```

And once you have the REPL running you can type in this code:

```
user=> (println "Hello, world!")
; Say hi.
```

And see the familiar greeting:

```
Hello, world!
nil
```

Don’t fret about the `nil`; that’s just the value returned by `println` after it does its thing, which the REPL helpfully printed for us.

> [!NOTE]
> **REPL Who?**
>
> The REPL is one of the few programs whose name is its algorithm.  All the REPL does is read some code—the code you type in—evaluate the code, print the result, and then loop back to read some more code: Read. Evaluate. Print. Loop.


One of the things that has made Hello, World such a popular first program is just how much we can learn from that single line of code. Looking at our Clojure Hello, World, we can work out that in Clojure strings come "wrapped in double quotes".

We can also see that comments start with a semicolon and run to the end of the line. Typically Clojure programmers will use a single semicolon when they add a comment to the end of a line with some code—as we did in the example—but will double up on the semicolons if the comment is all alone on its own line:

hello/examples.clj
```clojure
;; Do two semicolons add up to a whole colon?
(println "Hello, world!")
; Say hi
```

More subtly, we can deduce that Clojure treats simple, unadorned names like `println` as identifying things that get looked up. Thus our little program only worked because `println` is the name of a predefined function, one that comes to us courtesy of Clojure itself. As you might expect, Clojure predefines a whole range of other handy functions. There is, for example, `str`, which takes any number of values, converts them to strings, and concatenates the whole thing together:


```clojure
(str "Clo" "jure")                ; Returns "Clojure".
(str "Hello," " " "world" "!")    ; Returns the string "Hello, world!"
(str 3 " " 2 " " 1 " Blast off!") ; Fly me to the Moon!
```

There is also `count`, which will tell you how long your string is:

```clojure
(count "Hello, world") ; Returns 12.
(count "Hello")        ; Returns 5.
(count "")             ; Returns 0.
```

Clojure also comes with a number of predefined constants. For example, we have the Boolean siblings `true` and `false`:

```clojure
(println true)  ; Prints true...
(println false) ; ...and prints false.
```

There is also `nil`, which is Clojure’s version of the “nobody’s home” value, known in some languages as `null` or `None`:

```clojure
(println "Nobody's home:" nil) ; Prints Nobody's home: nil
```

Note that `println` will print just about anything you throw at it, so that if we run this:


```clojure
(println "We can print many things:" true false nil)
```

we’ll see

```
We can print many things: true false nil
```

You’ve probably noticed something odd about the parentheses in a Clojure function call: they are on the outside. It’s

```clojure
(println "Hello, world!")
```

not

```
println("Hello, world!")
```

If you’re coming to Clojure from a more traditional programming language, those parentheses will look out of place. There is a method to the Clojure syntax madness, which we’ll return to in Read and Eval. For now let’s just note that the Clojure syntax for making something happen—such as calling a function— is to wrap the something in round parentheses, and move on.



