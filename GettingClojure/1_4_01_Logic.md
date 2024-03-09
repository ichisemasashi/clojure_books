
## CHAPTER 4 Logic

There comes a time in the life of virtually every program when decisions need to made. If the bank account is overdrawn, then send a nasty email. If the denominator is zero, then best not to do that division. If this is a preferred customer, then give them a break on the shipping charge. Programs are so full of these kinds of decisions that it’s not surprising that we sometimes refer to them as logic.

In this chapter we’ll look at how you make decisions in Clojure programs.  We’re going to start with the logic hammer suitable for most coding nails—the `if` expression—and then move on to the slightly more general `cond` expression.  Along the way we’ll discover that Clojure has its own ideas of what is true, what is false, and what things are equal, ideas that may surprise you.


