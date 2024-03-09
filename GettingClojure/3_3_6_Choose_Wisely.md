
### Choose Wisely

Sometimes—especially for the newly arrived Clojure programmer—it can be difficult to know which container to reach for in any given situation. After all, along with vars we now have atoms and refs and agents. It is an embarrassment of programming riches. Fortunately there are some straightforward guidelines that you can follow.

The first rule: If your value is mostly stable over the life of the program, perhaps with some thread-local variations, then put it in a var.

The second rule: If you have a number of mutable values that need to be updated together—and those updates don’t involve side effects—then use refs. Remember, it’s refs that give you that databaselike ability to build transactions.

The third rule: If there are side effects that need to happen as you update your mutable state, then use an agent. You should also consider using an agent if your update function is slow. That way you can foist the slowness off on the background thread that is updating the agent.

The last rule is simple: If you have a mutable value but your update function is side effect–free and you don’t need to keep several values consistent, then use an atom. And rejoice, because atoms are by far the simplest of our mutable state trio. In fact, there is even more reason to rejoice because in most cases you can use atoms. Most of the time we don’t need to coordinate changes with a ref or strictly serialize our updates with an agent. The bottom line is that if you are thinking about mutable state, your first thought should 
be an atom.

> [!NOTE]
>
> **Do You Really Need That Ref?**
>
> Since atoms are the easiest of the mutable value containers to work with, it’s sometimes worth reshaping your code just so you can use an atom. In the example we used a ref to track the total number of books stored in our inventory, held by a second ref.  Alternatively we could have created a single ubermap to hold both the count and our inventory and stored that in an atom. The choice comes down to a trade-off: the simplification of using an atom versus having separately updatable refs.

