
## CHAPTER 14 Tests

If there is one thing programmers have learned over the past few decades, it’s that seeing is believing. No matter how carefully you think out your design, and no matter how brilliantly you implement your code, if you haven’t actually seen your system working, you don’t know that it does. The "seeing is believing" philosophy is why we need to actively test our code. And since there are at most 24 hours in a day, hours that we would like to spend doing things besides testing, we try to automate that testing.

So in this chapter we’re going to look at some of the tools that Clojure provides to help you convince yourself that your code is, in fact, working. We’ll start by looking at how you can write traditional unit tests. From there we’ll move on to generative property-based testing, which allows you to specify—and then test—the properties of your program.

And since the world is full of buggy programs, let’s get going!




