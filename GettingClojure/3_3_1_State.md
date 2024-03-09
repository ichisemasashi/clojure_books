
## CHAPTER 18 State

There’s a fundamental irony to the Clojure approach to programming, which arises from a principle that can be summed up—at least in part—as avoid mutable state. Avoiding mutable state is why you can’t just jam a new value into the fifth element of an existing vector or delete a key from an existing map. It’s also why Clojure has `let` instead of local variables and why we restrain ourselves from `def`ing things on the fly in production. The idea is the less mutable state we have—at least in production—the easier our programs are to understand.

Now for the irony: having gotten rid of all of the mutable state, we’re now at a loss when it comes to modeling things that do change over time. How, for example, do we keep track of our book-store inventory as books get sold and new stock arrives? As things stand right now we would have a hard time building an application that handles a simple "You Are the 500th Person To Visit Our Site" counter, let alone a service to, say, manage inventories.

So in this chapter we’ll look at how you represent mutable state in Clojure.  In the pages that follow we’ll see that Clojure comes with a variety of containers for your mutable state, each with a different set of talents and drawbacks.

