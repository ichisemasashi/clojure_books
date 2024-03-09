
## CHAPTER 20 Macros

We programmers both love and hate code. We love it because it’s what we make. Line by painful line we labor over our keyboards, producing symbols and strings and numbers and—this being Clojure—parentheses, lots of parentheses. We hate code because the pain never stops. Every line of code needs to be debugged and tested and deployed and updated and recompiled and … well, you get it.

It’s exactly because code is so painful that we try to have as little of it as we can—and why we try to keep it as concise and as expressive as we possibly can. Keeping code concise and expressive is a multilevel job. On the large scale, we struggle to find a solid design. At the medium scale, we try to pick data structures and an execution flow that make sense. And at the very smallest scale, we try to wrest the maximum coding mileage out of every expression and line.

In this chapter we’re going to explore macros, a Clojure feature that can help with the smaller-scale struggles of writing expressive code. In the pages that follow we’ll see how macros can automate some of your code-writing for you.  We’ll also explore how macros can go horribly wrong and why you should use them with more than the usual dose of caution.


