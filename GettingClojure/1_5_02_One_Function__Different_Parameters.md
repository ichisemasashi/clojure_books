
### One Function, Different Parameters

Because Clojure—and Clojure programmers—do rely so heavily on functions, it’s not surprising that the language provides some extras to help you craft just the function that you want. For example, all the functions we’ve written so far have taken a fixed number of arguments. Sometimes it’s convenient to build functions that take a less doctrinaire view of how many arguments they are willing to accept. For example, we might want to create a "Hello, World"–style function that will let you supply a greeting or default to a plain old "Hello".

This is not hard:

capable/examples.clj

```clojure
(defn greet
  ([to-whom] (println "Welcome to Blotts Books" to-whom))
  ([message to-whom] (println message to-whom)))
```

The `greet` function as defined here will accept either one or two parameters.  The single-argument version works like our original `say-welcome`, while the two-argument rendition takes a message along with the recipient. Notice how `greet` is essentially two function definitions in one, each with its own parameter list and body, each one wrapped in yet another set of round parentheses.

Armed with the preceding code, we can call greet with one argument, like this:

```clojure
(greet "Dolly") ; Welcomes Dolly to Blotts Books. 
```

or with two, like this:

```clojure 
(greet "Howdy" "Stranger") ; Prints Howdy Stranger.
```

The technical term for the number of arguments a function takes is arity, and so functions like `greet` are called multi-arity functions. A Clojure function can have as many arities as you like, but in practice people tend to limit themselves to three or four.

One problem with `greet` is that the two function bodies are reasonably redundant: both variations of `greet` print a message and a name. This is a fairly common occurrence in multi-arity functions. After all, each arity of the function should be doing more or less the same thing or we probably should have written two separate functions.

The way to get rid of this redundancy is simple and equally common: just call one arity from the other:

```clojure
(defn greet
  ([to-whom] (greet "Welcome to Blotts Books" to-whom))
  ([message to-whom] (println message to-whom)))
```

The idea of this "filling in the defaults" technique is that you have one arity—usually the one with the most arguments—that really does something. All the other arities, the ones that take fewer arguments, call that main version, filling in the missing parameters as they go.



