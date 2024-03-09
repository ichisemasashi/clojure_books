
### Symbols Are Things

But what exactly is the "it" that is part of the environment? In other words, what does `def` do, precisely? The answer is implicit in the terminology: We say that `def` binds a symbol to a value. The thing to note here is that the word "symbol" is not just Clojure jargon for what most other languages call an identifier. The reason it’s not just jargon is that symbols are first-class "takes up bytes in memory" values in Clojure, similar to strings and keywords. So this expression:

```clojure
(def author "Austen")
```

involves "two" values: the string `"Austen"` and the symbol `author`.

Symbols have a lot in common with keywords: both are just strings of characters that are meaningful to humans. And both symbols and keywords stand for some value. The difference is that while keywords always stand for themselves—evaluate `:title` and you always get `:title` back—symbols are typically bound to some other value. So if you evaluate the symbol `author` you will get back the value to which it is bound, `"Austen"`. 

Since symbols are actual things in Clojure, you can, with a little effort, reach in and get at the symbol itself as opposed to the value that the symbol is bound to. All you need is our old friend the single quote to prevent the symbol from being evaluated:

```clojure
'author ; The symbol author, not the string "Austen"
'title ; A symbol that starts with a 't'.
```

The idea is that symbols are not magical things that are built into the language. In Clojure, a symbol is just another kind of value. You can, for instance, turn them into strings:

```clojure
(str 'author) ; The string "author".
```

and compare them:

```clojure
(= 'author 'some-other-symbol) ; Nope.
(= 'title 'title)              ; Yup.
```

Being a value also means that a symbol can exist on its own, without being bound to another value. You can, in fact, make stand-alone symbols as fast as you can type, so that `'some-other-symbol` and `'still-another-symbol` are perfectly good expressions, even if neither symbol has ever appeared in a `def`.


