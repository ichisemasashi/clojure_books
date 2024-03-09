
### Staying Out of Trouble

Perhaps the biggest danger vars pose to the new Clojure programmer grows out of the fact that vars look a lot like the familiar variables that we find in traditional programming languages. And the name "var" doesn’t help.

Don’t try to use vars as variables. In particular, don’t rely on changing the value of a var to model the changing state of the outside world. Clojure has other, more appropriate tools for modeling the changing state of the world, tools that we’ll look at in "Chapter 18, State, on page 215".

Do use vars to weave the parts of your program together with intention-revealing names. For the most part you want to `def` your values, `defn` your functions, and then "leave them alone". Yes, you can bind a symbol to a value with `def` and then go right back in and bind that same symbol to a different value with a second `def`. And yes, `binding` also exists. But you should not be doing any of that terribly often.

One last thing to keep in mind is that `let` does not create vars. Thus, if you do this:

```clojure
;; Don't do this!
(let [let-bound 42] #'let-bound)
```

you will see something like this:

```
CompilerException java.lang.RuntimeException:
   Unable to resolve var: let-bound...
```

That’s because there are no vars behind `let` bindings. Instead the bindings produced by `let` are more like the variables are in other programming languages, implemented by some behind-the-scenes magic performed by the Clojure compiler.


