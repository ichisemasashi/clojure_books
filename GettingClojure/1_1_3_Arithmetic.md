
### Arithmetic

Another thing that usually comes early in learning a programming language is figuring out how to do basic arithmetic. Clojure’s approach to doing math is refreshingly—and perhaps a little disconcertingly—simple. To see what this means, let’s add a couple of numbers together:

```
(+ 1900 84)
```

Run the expression in that example through the REPL and you will get back `1984`. You do multiplication, subtraction, and division in the same way:

```clojure
(* 16 124) ; Gives you 1984.
(- 2000 16) ; 1984 again.
(/ 25792 13) ; 1984 yet again!
```

As you might expect, you can assemble these basic math operations into arbitrarily complex expressions. Thus you can get the average of `1984` and `2010` with this:

```clojure
(/ (+ 1984 2010) 2)
```

Arithmetic in Clojure can be a bit disorienting at first, a disorientation that can be summed up by the question Why is the + first? or perhaps What happened to my nice infix operators? The answer is that Clojure is trading some convenience, in the form of the familiar infix operators, for simplicity. By treating the arithmetic operators like ordinary functions, Clojure manages to keep the syntax of the language uniform. In Clojure, no matter what you are doing, you do it by saying

```clojure
(verb argument argument argument...)
```

This means that in the same way we print the string `"hello"` with `(println "hello")`, we add two numbers with `(+ 1982 2)` and we divide them with `(/ 25792 13)`. It’s always the thing we want to do, followed by any arguments, all wrapped in round parentheses.

Conveniently, the basic math operators/functions take a variable number of arguments. Thus we can add up a bunch of numbers with this:

```clojure
(+ 1000 500 500 1) ; Evaluates to 2001.
```

or do a running subtraction with this:

```clojure
(- 2000 10 4 2) ; Evaluates to 1984;
```

There is one other twist lurking in the math functions, specifically in the `/` (division) function. Many programming languages, when asked to divide one integer by another, will give you back a truncated integer result. For example, in Ruby or Java when you divide 8 by 3 you get 2. Not so in Clojure, where `(/ 8 3)` will give you `8/3`, which is a ratio, one of Clojure’s built-in data types.

To get the familiar integer truncating behavior, you need to use the quot—short for quotient—function. So one way to get `2` is to write `(quot 8 3)`.

By default, Clojure turns unadorned numeric literals like `8` and `3` and `4976` into integers. If you are interested in numbers with decimal points, Clojure also offers the familiar floating-point notation. Here’s our averaging expression again, this time using floating-point numbers:

```clojure
(/ (+ 1984.0 2010.0) 2.0)
```


Clojure also provides a sensible set of numeric promotions, so that if you add an integer to a floating-point number, perhaps `(+ 1984 2010.0)`, you will get back a floating-point number—in this case `3994.0`—for your trouble.


