
### Function Literals

Another way that Clojure comes to your aid in creating new functions is to supply an alternate, minimalistic syntax for defining them. So for those moments when even the sleek lines of `fn` seem like too much syntactical overhead, you can use a function literal: just a `#` followed by the function body, wrapped in the usual parentheses. Here, for example, are the guts of `adventure?` recast as a function literal:
 
```clojure
#(when (= (:genre %1) :adventure) %1)
```

Note there are no named arguments in function literals; instead they use the very shell script-ish notation of `%1` to stand for the first argument, `%2` for the second argument, and so on. So if we needed a function that would double a number, we might use partial or we might do this:

```clojure
#(* 2 %1)
```

Or if we need a function to add three numbers together, we might cook this up:

```clojure 
#(+ %1 %2 %3)
``` 

There are a few things to keep in mind about function literals, or "lambdas", as they are sometimes known. First, function literals and `fn` produce exactly the same kind of thing (a function value); the only difference is the syntax. 

Second, remember that Clojure infers the number of arguments that your literally defined function takes from the highest-numbered argument in the function body. Thus, if we modified our "double the number" function into this:

```clojure
#(* 2 %11) 
```

we would end up with a (very inconvenient) function that takes 11 arguments and ignores the first 10.

Finally, function literals have a special feature aimed directly at the very common case of creating a one-argument function. If the function you’re building takes a single argument, you can use plain old `%`—without a number—for the one and only argument. So a minimal version of our number doubler would be as follows:

```clojure
#(* % 2)
```


The trade-off between defining a full-blown named function with `defn` and using the streamlined `fn` or a completely stripped-down function literal is one of those familiar software-engineering choices. If you’re going to be reusing the function, then by all means use defn and give it a name. Giving your function a name is also worthwhile if the name will help you (and those who come later) understand some intricate bit of code. You also probably will want to use `defn` on lengthy functions to visually break up the code.

On the other hand, `fn` and function literals are wonderful when you’re cooking up short, single-use functions and when you need to take advantage of a closure to pick up some values.

The choice between `fn` and function literals centers on complexity and number of arguments. Lean toward function literals for really short, simple functions.  If, for example, you need a function to double a number, then by all means write `#(* % 2)`. Conversely, lean toward `fn` if you have a longer function, and especially one that takes more than a very few arguments. Examples aside, no one really writes function literals that have a `%11`.


