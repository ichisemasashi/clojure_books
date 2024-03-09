
### In the Wild

While the REPL-based "Hello, World" is wonderful for getting a feel for writing Clojure, it doesn’t quite capture the whole spirit of the "Hello, World" exercise.  Along with giving you a feel for the language, "Hello, World" is supposed to get you to work out the details of going from source code stored in a file to a running program. Happily, we’re exactly one command away from getting a real Clojure project set up on disk. All we need is `lein new app` followed by the name of the application. So if we were creating a book store–related application for Blotts Books (Ms. Flourish has retired) we might say this:

```bash
$ lein new app blottsbooks
```

Feed that command into your operating system’s command line, and Leiningen will respond with this:

```bash
Generating a project called blottsbooks...
```
  
And you will have a newly minted—albeit skeletal—Clojure project in a directory called `blottsbooks`. Look around inside of the new `blottsbooks` directory and you will find a collection of files—`CHANGELOG.md` and `README.md` among them—as well as a number of subdirectories, with `src` and `test` chief among them:

```bash
$ cd blottsbooks
$ ls
README.md doc/ project.clj resources/ src/ test/ ...
```

For our purposes we’re mostly interested in the file `src/blottsbooks/core.clj`, which contains the Clojure source code for our project. It’ll look like this: 

hello/blottsbooks-1/src/blottsbooks/core.clj
```clojure
(ns blottsbooks.core
  (:gen-class))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))
```

Note that the file starts off with an `ns` (short for namespace) expression, which sets up a new namespace and requests—with the `:gen-class`—that the namespace be compiled. We’ll have a lot more to say about namespaces in "Chapter 9, Namespaces, on page 95", but for the moment let’s move on.

The second expression in `core.clj` obviously defines a function, but this `defn` clearly has some optional accessories that we haven’t encountered. There is, for example, a mysterious string just before the parameters, and then there is an ampersand in the parameter list. We could stop here and talk about supplying a handy description of your function or about functions that take a variable number of arguments, but let’s leave that to "Chapter 5, More Capable Functions, on page 49", and instead focus on the thing that makes this function special: its name. The main function in a Clojure program, the one that gets run to kick off the program, is always called `-main`.

To see this in action, we can replace the `-main` with our book store greeting code. Here is our modified core.clj in its entirety:

hello/blottsbooks-2/src/blottsbooks/core.clj
```clojure
(ns blottsbooks.core
  (:gen-class))

(defn say-welcome [what]
  (println "Welcome to" what "!"))

(defn -main []
  (say-welcome "Blotts Books"))
```

We can get our Clojure application to run like this:

```bash
$ lein run
```

Do that, and you should see

```
Welcome to Blotts Books !
```

Aside from the technical thrill of seeing the code run, we can learn something subtle from our little Clojure application. Did you notice how we defined `say-welcome` before we used it in `-main`? This underlines a basic Clojure rule: you need to define your functions before you use them. This means Clojure code tends to read from the bottom up, with the lower-level functions defined first.

> [!NOTE]
> Declaration First?
>
> There is a way to get around the you gotta define it before you use it rule: you can use `declare`, as in `(declare say-welcome)`, to do a sort of predefinition of a function.
> Mostly Clojurists stick to defining their functions before they use them, and reserve `declare` for sticky situations like mutually recursive functions.


Finally, be aware that both `def` and `defn` draw from the same well of names.  This means that

```clojure
(def author "Dickens")

(defn author [name]
  (println "Hey," name "is writing a book!"))
```

will leave you with a function called `author`. Reverse the order of the `def` and
`defn`, and author will be a string. The rule is that the last `def` or `defn` in wins.


