
## CHAPTER 19 Read and Eval

Programming-language syntax is a lot like politics: very few people do it for a living but virtually everyone has an opinion. And many have strong opinions.  Certainly it’s not hard to find strong opinions about Clojure’s syntax. Many of us love what we see as its elegant terseness while some programmers can only see parentheses. Lots and lots of parentheses.

Clojure’s somewhat odd syntax is not the shady outcome of a conspiracy of parentheses manufacturers. Nor is it a completely arbitrary esthetic choice.

Clojure’s syntax is an integral part of how the language works. So in this chapter we’re going to look at the two critical functions at the heart of Clojure, `read` and `eval`, and at how they relate to all those parentheses. Along the way we’ll take a moment to write our own version of `eval`, essentially building our very own toy Clojure implementation.

If all that sounds intimidating, take heart: like everything else in Clojure, `read` and `eval` are simple. They are also critical to getting to the next level of insight into how the language works. So let’s get started. 

