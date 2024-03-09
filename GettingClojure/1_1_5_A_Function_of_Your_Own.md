
### A Function of Your Own

Let’s return to our "Hello, World" example and see if we can turn our one-liner into something more worthy of the name program. We can do this by wrapping it with the fundamental unit of Clojure code, the function:

```clojure
(defn hello-world [] (println "Hello, world!"))
```

Once you have `hello-world` defined you can call it just like any other Clojure function, so that if you run 

```clojure
(hello-world)
```

You will see `Hello, world!` printed.

As with the original, you can learn a lot from this new version of "Hello, World".  For example, you can see that the function definition kicks off with `defn` instead of `def` and is wrapped in its own set of parentheses on the outside. Inside the `defn` we have the function parameters, set off with square brackets, `[]`. Since our `hello-world` function doesn’t have any parameters, there is nothing inside of its brackets.

While we wrote the original version of Hello, World entirely on one line, we could have spread the defn over a couple of lines: 

```clojure 
(defn hello-world []
(println "Hello, world!"))
```

It’s all the same to the compiler because Clojure mostly ignores whitespace.

Clojure programmers do, however, have opinions about whitespace. By convention, you can either write a short function on a single line or spread it out over a couple of lines as we did in the last example. Longer functions should take up as many lines as they need. Clojure programmers also have a strong opinion about indentation, one that we followed in the example: each level of indentation is done with two spaces. There are exceptions to the two-space rule, mostly around lining up function arguments and the like. But for the moment we’ll stick to two spaces. And note it’s always spaces, no tabs allowed.

> [!NOTE]
> Sans Tabs?
>
> Why no tabs? Because one of the great mysteries of programming is the exchange rate between tabs and spaces. Is it four spaces to a tab? Eight? Three? It’s safer to stick to spaces.

Writing a function with a parameter or two is also straightforward: just put the parameter names in the brackets and then use them inside the function body. Here’s a greeting function that takes a single parameter:

```clojure
(defn say-welcome [what]
  (println "Welcome to" what))
```

The `say-welcome` function takes one parameter, called `what`, and prints an appropriate greeting. Calling your new function is like calling the `println` function, so that if you do this:

```clojure
(say-welcome "Clojure")
```

you should see a friendly greeting:

```
Welcome to Clojure
```


Happily, we can rely on `println` to supply the spaces around the values that it prints so that we see "Welcome to Clojure" and not "Welcome toClojure." Now that we have the basic function-building mechanics down, let’s see if we can create a function that does something useful:

```clojure
;; Define the average function.
(defn average [a b]
  (/ (+ a b) 2.0))
;; Call average to find the mean of 5.0 & 10.0.
(average 5.0 10.0) ; Returns 7.5
```


The `average` function takes a couple of numbers and returns their arithmetic mean. There are three things to note about the `average` function. The first is the comma between the two parameters: it’s not there. In contrast to many programming languages, Clojure never requires you to sprinkle commas in when you’re writing a sequence of items such as the parameter list of a function. A bit of whitespace between the items is plenty. But if you really miss the commas, you can put them in: Clojure treats commas as whitespace.  Clojure programmers mostly dispense with commas.

The second thing to note about `average` is that there is no explicit `return` statement. Clojure functions just return whatever they compute. More precisely, they return the last thing computed by the function. We need the qualifier because you can have more than one expression inside of your function body.  Here, for example, is a variation on `average` that has a four-expression body:

```clojure
(defn chatty-average [a b]
  (println "chatty-average function called")
  (println "** first argument:" a)
  (println "** second argument:" b)
  (/ (+ a b) 2.0))
```

You can probably guess what happens when you evaluate `chatty-average`:

```clojure
(chatty-average 10 20)
```

Each expression inside the body gets evaluated in turn, so that you would see this:

```
chatty-average function called
** first argument: 10
** second argument: 20
```

Since the last expression supplies the return value, the function returns 15.0.

The final thing to note about `average` is that there are no type declarations, nothing stating explicitly that `a` and `b` must now and forever be numbers. Nor is there anything declaring what the function returns. In the great "static versus dynamic typing" trade-off, Clojure has chosen the flexibility and terseness of dynamic typing.


