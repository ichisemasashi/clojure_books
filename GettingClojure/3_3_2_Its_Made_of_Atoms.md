
### It’s Made of Atoms
 
Let’s start with that simple hit-counter application. Here is a basic Ring web application that counts the number of visitors to the site and congratulates every 100th visitor:

state/examples.clj
```clojure
(def counter 0) 
(defn greeting-message [req]
  (if (zero? (mod counter 100))
    (str "Congrats! You are the " counter " visitor!")
    (str "Welcome to Blotts Books!")))
;; ect
``` 
 
Except that it doesn’t. The trouble is that `counter` stays firmly bound to zero and it’s not clear how we can fix that. And as we’ve discussed any number of times, we cannot yield to the temptation of slipping `(def counter (inc counter))` in `greeting-message`: vars are there to hold relatively stable values and a hit counter is anything but stable.

Fortunately, Clojure does come with a container for more volatile values: the atom. Here’s our hit-counter application augmented with an atom to manage the count:

```clojure
(def counter (atom 0))
(defn greeting-message [req]
  (swap! counter inc)
  (if (= @counter 500)
    (str "Congrats! You are the " @counter " visitor!")
    (str "Welcome to Blotts Books!")))
;; ect
```


As you can see, we create the atom by calling the `atom` function and passing in the initial value of the atom, zero in the example. You can also see that we get the value back out of the atom by prefixing an `@` to it. We could also have gotten the value by writing the slightly longer `(deref counter)`. If all of this looks familiar, it should: getting at the values inside of an atom is exactly the same as getting the value out of a promise or a future.

The punchline with atoms is the way that they march from one mutable state to another. The key is in the `swap!` function:

```clojure
(swap! counter inc)
```

The `swap!` function takes two arguments, the atom and a function to produce the next value of the atom. The idea is that `swap!` calls the function with the old value of the atom and uses the value returned from the function as the new value for the atom. So in our example, each call to `swap!` will increment the value in the atom, giving us our counter. Conceptually `swap!` lets you march from one value of your atom to the next.

Conveniently, `swap!` will pass any additional arguments into the function, so if we wanted to cheat a bit and count each visitor to our site as a dozen, we could write this:

```clojure
(swap! counter + 12)
```

The great thing about atoms is that the update mechanism ensconced in `swap!` is completely thread safe. Here’s how it works:

* The first thing `swap!` does is grab the value in the atom.
* It then calls your update function with the value to get the new value.
* Next—and this is the critical bit—`swap!` checks that the value in the atom is still what it was when the update started. If the value is still the same, then `swap!` replaces the value with the new value and we’re done.
* If the value has changed, `swap!` will grab the new value and "start the process all over".

Thus `swap!` keeps trying to update the atom until it manages to do so without interference from other threads. Even better, all this happens on the thread that calls `swap!` so that when `swap!` returns, you know your update has been applied to the value in the atom.


