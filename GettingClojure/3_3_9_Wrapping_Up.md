
### Wrapping Up

In this chapter we looked at Clojure’s trio of state-management constructs: the atom, the ref, and the agent. We saw that the atoms, refs, and agents have a lot in common. Each one is just a mutable container for a value. Each lets you march from one value to the next by supplying a function—a function that takes the old value and produces the new one. But atoms, refs, and agents part company in the way they manage change. Atoms are simple, stand-alone containers that march from one value to the next, resolving any conflicts by reevaluating the update function. Refs are just like atoms, but can be updated in batches, inside of a `dosync` transaction. Agents are stand-alone, but queue up updates and execute them one at a time in a separate thread.

Next we’re going to turn our attention back to Clojure itself, specifically to Clojure’s syntax and how it got that way. 


