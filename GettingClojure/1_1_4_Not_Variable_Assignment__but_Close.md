
### Not Variable Assignment, but Close

Once you get beyond the add a few numbers together stage you naturally start looking for a way to hang a name on the result. The most straightforward way to do that in Clojure is with `def`:

```clojure
(def first-name "Russ")
```

There are very few surprises in using def. You give it an identifier—Clojure calls this a symbol—and a value, and def will associate, or bind, the symbol to the value. In this example the symbol is `first-name` and the value is the string `"Russ"`. The value that you supply to def gets evaluated, so it can be any expression. So evaluating this

```clojure
(def the-average (/ (+ 20 40.0) 2.0))
```

Will bind `30.0` to `the-average`.

One thing that you might find surprising is that it’s `the-average` and not `theAverage` or `the_average` or even `TheAverage`. While the Clojure language is gloriously easygoing when it comes to the characters you can use in a symbol—`this&that|other` and `Much=M*re!` are both fine—Clojure programmers have adopted the `all-lower-case-with-words-separated-by-dashes` convention—also known as kebab case—when picking symbols, so it’s `first-name` and `the-average`.

A note of caution: `def` is great when you’re just playing around or debugging in the REPL, but it’s not the direct analog of traditional variable assignment that it seems. We’ll get back to the distinction in "Def, Symbols, and Vars", but for now we’ll put that aside and continue to def things with wild abandon.

>[!NOTE]
> **Symbolic Rules?**
> 
> As I say, there are very few rules about the characters that can go into a symbol. But there are some: You can’t, for example include parentheses, square brackets, or braces in your symbols since these all have a special meaning to Clojure. For the same reason, you can’t use the `@` and `^` characters in your symbols.
> There are also some special rules for the first character of your symbols: you can’t kick your symbol off with a digit—it would be too easily confused with a number—and symbols that start with a colon are not actually symbols but rather keywords, which we’ll talk about in "Maps, Keywords, and Sets".

