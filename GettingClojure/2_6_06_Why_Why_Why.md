
### Why? Why? Why?

One issue with more complex specs is that it’s not always immediately clear why a particular value doesn’t match. For example, you might have to look at this expression for a while to see why `valid?` returns `false`:

```clojure
(s/valid? ::book {:author :austen :title :emma}) 
```

To help you figure out why your spec isn’t matching, `clojure.spec` provides the `explain` function, which takes the same arguments as `valid?`. If the spec matches, `explain` will print a celebratory message, so that if you do this:

```clojure
(s/explain n-gt-10 44)
```

you will see this:

```
Success!
```

Things are much more interesting if the spec doesn’t match. Do this:

```clojure
(s/explain n-gt-10 1)
```

and you will see something like the following:

```
val: 4 fails predicate: (> % 10)
``` 


Thus we can use `explain` to figure out why your book map failed to match the spec:

```clojure
(s/explain ::book {:author :austen :title :emma})
```

To wit:

```
In: [:author] val: :austen fails spec: :inventory.core/author
at: [:author] predicate: string?
```

Of course! The author and title need to be strings, not keywords!

Note that `explain` prints its results. Match or no, its return value is always `nil`.

The `explain` function has a positive doppelgänger in the form of `conform`. While `explain` will tell you what went wrong when a spec doesn’t match, `conform` will tell you all about a successful match. Like `valid?` and `explain`, `conform` takes a spec and a value:

```clojure
(s/conform s-n-s-n ["Emma" 1815 "Jaws" 1974])
```

If the spec doesn’t match, `conform` returns the keyword `:clojure.spec.alpha/invalid`.

If it does match, `conform` will give you a detailed explanation of the match, as a Clojure value. For simple specs, this is just the matching value itself, so that this will give you `1968`:

```clojure
(s/conform number? 1968)
```


Things get interesting when you use `conform` with a spec containing descriptive keywords, so that this:

```clojure
(s/conform s-n-s-n ["Emma" 1815 "Jaws" 1974])
```

will return

```clojure
{:s1 "Emma", :n1 1815, :s2 "Jaws", :n2 1974}
```

Note that unlike `explain`, `conform` actually returns results instead of printing the results.



