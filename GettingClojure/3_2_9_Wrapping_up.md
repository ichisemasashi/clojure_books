
### Wrapping up

In this chapter we took a look at some of the ins and outs of threads. We saw how multiple threads can help you push your computing job forward on multiple fronts at once, but also how a multithreaded program can come to grief if you are not careful.

But we also saw how Clojure’s immutable data structures and dynamic binding, along with promises and futures, can enable you to use multiple threads safely.

But we’re not quite done with multiple threads yet. In this chapter we focused on keeping your threads separated, on keeping them from sharing state. In real life, though, in everything from a simple web application hit counter to complex data-handling systems, we need to have threads deal with shared state. And so that is what we’ll tackle in the next chapter.


