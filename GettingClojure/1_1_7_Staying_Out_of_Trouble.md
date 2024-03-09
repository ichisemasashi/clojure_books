
### Staying Out of Trouble

The irony of picking up a new programming language is that while the goal is to write perfect code, a key skill is to learn how to deal with the myriad of mistakes you’re going to make along the way. To that end, let’s make some mistakes and look at Clojure’s reaction to them.

For example, what happens if we make the classic divide by zero blunder?

```clojure
(/ 100 0)
```

What you will see when you divide by zero depends on the environment in which you are executing your Clojure code. You might see a brief

```
ArithmeticException Divide by zero
clojure.lang.Numbers.divide (Numbers.java:156)
```

Or you might see this much more extensive message:

```
java.lang.ArithmeticException: Divide by zero
Numbers.java:156 clojure.lang.Numbers.divide
Numbers.java:3731 clojure.lang.Numbers.divide
/Users/russolsen/projects/clojure/quill1/foo.clj:3 user/eval6197
Compiler.java:6703 clojure.lang.Compiler.eval
Compiler.java:6666 clojure.lang.Compiler.eval
core.clj:2927 clojure.core/eval
eval.clj:77 lighttable.nrepl.eval/->result
AFn.java:156 clojure.lang.AFn.applyToHelper
<<and on and on and on>>
ThreadPoolExecutor.java:1145
java.util.concurrent.ThreadPoolExecutor.runWorker
ThreadPoolExecutor.java:615
java.util.concurrent.ThreadPoolExecutor$Worker.run
Thread.java:745 java.lang.Thread.run
```

Yes, it can be a bit overwhelming, but it is well intentioned: the idea of this huge stack trace is to give you the fullest explanation of what went wrong and especially where it all went bad. But don’t be intimidated. Just focus on the first line of this field of computing debris, which is where the most important bit of information is hiding:

```
java.lang.ArithmeticException: Divide by zero
```

You also shouldn’t be intimidated by the `java` part of that exception. Clojure—at least the version we’re working with here—is built on top of Java, and now and then, especially when the exceptions go flying, you will see the Java leaking through. Don’t panic: knowing Java is not a prerequisite for being a Clojure programmer.

Moving on, let’s see what happens if you misspell a symbol, perhaps typing `catty-average` instead of `chatty-average`:

```
CompilerException java.lang.RuntimeException:
Unable to resolve symbol: catty-average in this context,
compiling:(NO_SOURCE_PATH:0:0)
```

Or you might get its 80-line big brother. Either way, you just need to fix the name of the function.  Clojure beginners also tend to have a hard time with the parentheses. The easiest parentheses problem to diagnose is adding one too many to the end.

If you do something like this:

```clojure
(+ (* 2 2) 10))
```

you will get a straightforward message back:

```
RuntimeException Unmatched delimiter: )
clojure.lang.Util.runtimeException (Util.java:221)
```

On the other hand, if you happen to forget a closing parenthesis like this:

```clojure
(+ (* 2 2) 10
```

and you are using Clojure interactively via the REPL, then, well, nothing. The REPL will wait patiently for you to complete the thought by supplying that final parenthesis. So the rule of thumb is that if you’re sitting in the REPL and nothing is happening, consider that you need to add a closing parenthesis.
Or perhaps six closing parentheses.

One other thing to remember as you stick your toe into Clojure coding is that Clojure functions are themselves values. So, in exactly the same way that you might type `first-name` into the REPL and get back `"Russ"`, you can also get the value of a function by typing in the function name sans parentheses. Do this:

```
chatty-average
```

and you will see the value of the function, which gets printed something like this:

```
#object[user$chatty_average 0x39fcbef6 "user$chatty_average@39fcbef6"]
```

There is actually a fairly deep reason for this behavior (which we’ll talk about in "Chapter 6, Functional Things, on page 63"), but it does tend to confuse beginners when they forget a parenthesis. The bottom line is that if you see unexpected values that look like `#object[user$chatty_average...` coming out of your program you probably missed an opening parenthesis somewhere. If you do get frustrated with all the parentheses, just be patient. Yes, Clojure’s syntax is a little odd. But it’s also simple: before you know it, all those parentheses will seem like old friends and you will wonder how you ever got along without them.


