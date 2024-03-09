
## CHAPTER 8 Def, Symbols, and Vars

So far in our Clojure adventures we’ve been using `def` to associate names with values without thinking about what exactly we were doing. There is more to `def` than meets the eye. So in this chapter we’re going to take a long look at the machinery behind `def`. We’ll start with a quick review of the syntax and intent of def before we move to an exploration of the remarkably transparent mechanisms lurking just behind the scenes. And along the way we’ll discover two new Clojure data types, the symbol and the var. Finally we’ll come back to the difference between the bindings created by `def` and `let` and look at some interesting real-world uses of `def`. Let’s get started.


