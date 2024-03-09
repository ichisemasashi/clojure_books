
## CHAPTER 12 Destructuring

One of the secrets of Clojure’s power is that it gives you basic tools—things like functions and vectors and maps—that let you do the simple things simply. 

But Clojure also provides facilities to compose those tools into more capable conglomerates that can take on the bigger jobs. On the code side, we have higher-order functions and vars and namespaces. On the data side, we have a set of simple data structures that you layer. If your problem requires it, there’s nothing stopping you from putting that vector inside of a map inside of a set inside of another map.

The downside of all this convenient data packaging is that peeling off the wrapping can be tedious. If you do have that vector inside of a map inside of a set, you could spend a lot of time—and programming energy—cutting through the wrapping to get at the actual data.

So in this chapter we’ll look at destructuring, a tool that you can use to cut through the data structure packaging. We’ll see how destructuring works and how it’s seamlessly integrated into key parts of Clojure. We’ll also look at how you can use destructuring to make your code cleaner and clearer, and at how to avoid letting destructuring defeat its own purpose by obscuring your code.

Let’s get started.



