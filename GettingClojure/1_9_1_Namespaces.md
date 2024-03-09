
## CHAPTER 9 Namespaces

One of the best things about programming is that you are never done. Solve one problem, and chances are that three other issues tag along with your solution. You can find a great example of this in naming things in programs.  It’s hard to imagine anything more important to a clear and concise program than well-chosen names. But create enough names, and now you have a new problem: how do you tell the difference between the `book` var in your book-store application, the `book` function that records revenue for the accounting department, and the function that registers a police arrest? In short, how do you manage the names?

In this chapter we’re going to look at Clojure’s answer to that question, the "namespace". We’ll see how you can wall off groups of related vars in their own namespaces and how you can share vars between namespaces. Along the way we’ll also see how Clojure programs ensure that the code they need is actually loaded, and we’ll uncover some related tricks for ensuring that your code isn’t cluttered with overly long names.


