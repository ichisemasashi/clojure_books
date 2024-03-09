
### Bindings Are Things Too

Surprisingly, the bindings between symbols and values, the things created by `def`, are also ordinary values. When you evaluate a `def`, Clojure creates a "var", a thing that represents the binding between a symbol and a value. As illustrated in the "figure on page 88", you can think of vars as having two slots: there’s a place for the symbol (maybe `author`) and a place for the value (perhaps `"Austen"`).

In a lovely bit of introspection, you can also get at a var with the proper incantation, this time a `#` followed by a `'`:

```clojure
(def author "Austen") ; Make a var.
#'author ; Get at the var for author -> "Austen".
```

![fig_1_8_4](img/1_8_4_001.png)

Like any other Clojure value, you can use vars in the business end of a `def`:

```clojure
(def the-var #'author) ; Grab the var.
```

And, if you know the API, you can get at both the symbol and the value buried inside of the var:

```clojure
(.get the-var)  ; Get the value of the var: "Austen"
(.-sym the-var) ; Get the symbol of the var: author
```

Don’t worry about the strange `.get` and `.-sym` syntax—we’ll get to that in "Chapter 16, Interoperating with Java, on page 189". Focus instead on the idea that when you evaluate a `def` you are dealing with three separate values. First there’s the symbol you’re binding, in this case `author`. Then there’s the value you’re binding to that symbol, `"Austen"` in the example. Finally we come to the punchline, the var that binds the symbol to the value, `#'author`.
