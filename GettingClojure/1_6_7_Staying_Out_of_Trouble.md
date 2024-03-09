    
### Staying Out of Trouble

Going from the simple-minded idea of functions as something you write and call manually to the "functions as values" idea has some interesting implications.

Chief among these is that you don’t always know the exact context in which your function will be called. Since functions are values, they can get passed around and evaluated any number of times:

```clojure
(defn execute-that-function-three-times [your-function]
  (your-function)
  (your-function)
  (your-function))
```

Or they might get called sometime later. For example, we might use `Thread/sleep` to wait 372 milliseconds before calling your function:

```clojure
(defn execute-that-function-later [your-function]
  (Thread/sleep 372) ; Pause for 372 ms.
  (your-function))
```

Or it might never get called:

```clojure
(defn execute-that-function-never [your-function]
  (+ 2 2))
```

Or it might get called in some odd combination:


```clojure
(defn some-odd-combination [your-function]
  (execute-that-function-three-times
    #(execute-that-function-later your-function)))
```

Given all this, the functional programmer’s Prime Directive is simple: try to write functions that "don’t care" about the context in which they are called. In practice this means you should avoid writing functions that rely on or generate side effects. In functional programming, the best functions are the ones that look only at their arguments and produce only their return value. They don’t read, create, or delete files; they don’t roll the current time or date into their answer; and they certainly don’t consult the user for input. They just look at their arguments and come up with a result. We even have an appropriately positive term for functions that follow these rules. We call them "pure functions".

The good news is that pure functions are not hard to write. In fact, take out the `printf`s that we’ve sprinkled here and there, and all the functions we’ve written in this chapter are indeed pure. From `adventure?` to `cheap-horror?`, we’ve managed—without even trying—to write functions that look only at their arguments to come up with a return value. The goal of writing pure functions also explains the immutability of Clojure’s data structures: by disallowing inplace modification of vectors and maps and all the rest, Clojure outlaws a whole class of side effects.

Note that the directive is to "try" to write pure functions. Much of the value that we programmers generate comes out in side effects—we read or write or delete the file, we update the database, or we increment the hit count on a web page. The only thing wrong with side effects is that functions that depend on them aren’t the easy-to-assemble building blocks that pure functions are.

So we try to write pure functions when we can. Because life is a lot easier without side effects.


