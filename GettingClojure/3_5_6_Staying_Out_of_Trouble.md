
### Staying Out of Trouble

One thing should be clear: macros are hard to get right—much harder than plain functions. With an ordinary function you write the code and it gets executed. With a macro you write the code that writes the code that gets executed. Implicit in this two-step process is that macros make themselves felt at two distinct times. There’s the moment when the macro is expanded—when it is writing the code—and then there’s the moment when the generated code is executed. We can see that distinction here:
    
```clojure
(defmacro mark-the-times []
  (println "This is code that runs when the macro is expanded.")
  `(println "This is the generated code."))
;; Expand the macro and you get the 1st println
;; but not the 2nd.
(defn use-the-macro []
  (mark-the-times)) 
;; Here we will get the second println, which is in the generated
;; code, twice.
(use-the-macro)
(use-the-macro)
```

In this example, Clojure will evaluate the first `println` exactly once, when the macro is expanded inside of `use-the-macro`. The second `println`—which is part of the generated code—will go off whenever you call the `use-the-macro` function.

While macros do make themselves felt at runtime—after all, they generate code—in a very real sense macros do not exist at all at runtime. Once it’s expanded, there is only the expanded code: the macro itself fades from the scene. For example, if you wrote this pathological expression you should definitely expect to see a stack trace:

```clojure
(arithmetic-if 1
  (/ 1 0)
  (/ 0 0)
  (/ -1 0))
```

What you should not expect to see is any mention of `arithmetic-if` in that stack trace. By the time the divide-by-zero exception happens, it’s not `arithmetic-if` that is being executed, but rather the `cond` that `arithmetic-if` is expanded into.  Macros are the Cheshire Cats of Clojure: all they leave behind is some code.

One handy tool in figuring out why your macro is doing what it is doing is `macroexpand-1`. Feed a prospective macro-containing expression into `macroexpand-1` and you will get the code with the macro expanded. So do this:

```clojure
(macroexpand-1 '(arithmetic-if 100 :pos :zero :neg))
```

and you will see something like this:

```clojure
(cond (pos? 100) :pos (zero? 100) :zero :else :neg)
```

The `macroexpand-1` function should be the first thing you reach for when your macros are misbehaving.

Another issue with macros is that, since they run at (or more accurately, just before) compile time, they form their own little world, separate from ordinary functions that execute at runtime. This means that you can’t pass the name of a macro into any of those wonderful higher-order functions. Imagine, for example, you wrote a macro called `describe-it`, a macro that described its argument:

```clojure
(defmacro describe-it [it]
  `(let [value# ~it]
    (cond
      (list? value#) :a-list
      (vector? value#) :a-vector
      (number? value#) :a-number
      :else :no-idea)))
```

The `describe-it` macro works fine when you use it directly, so that `(describe-it 37)` would indeed evaluate to `:a-number`. But if you try to use `describe-it` with the map function:

```clojure
(map describe-it [10 "a string" [1 2 3]])
```

you are in for an unpleasant surprise:

```
CompilerException java.lang.RuntimeException:
Can't take value of a macro: #'user/describe-it...
```

Clojure has noticed that you are trying to use a macro—which it knows is meant to transform code—as an ordinary data-slinging function, and has cut you off before you hurt yourself.

The solution is simple. There is no reason why `describe-it` should be written as a macro:

```clojure
(defn describe-it [it]
  (cond
    (list? it) :a-list
    (vector? it) :a-vector
    (number? it) :a-number
    :default :no-idea))
```

And this is an important lesson: if you can solve the problem concisely with ordinary functions, do that. If you find yourself—as we did with `arithmetic-if`—at odds with Clojure’s evaluation rules or if you are writing a lot of repetitive code, then perhaps a macro is the way to go. If you find yourself in one of those excruciatingly rare situations where you need to turn data directly into code, then perhaps turn to `eval`.


