
### Destructuring Function Arguments

Not only does destructuring work with all the sequential types (and, as we’ll see in a minute, with maps), but it’s also not limited to `let`. Most notably, you can use destructuring to drill into the arguments passed to a function. Using destructuring with your function arguments is nearly identical to using it in a `let`, except that you don’t supply the value—that comes from the call to the function. Here, for example, is a function that’s looking for a two-element vector:

```clojure
(defn artist-description [[novelist poet]]
  (str "The novelist is " novelist " and the poet is " poet))
```

As I say, the value to be destructured is supplied when you call the function, so that this:

```clojure
(artist-description [:austen :dickinson])
```

will return `"The novelist is :austen and the poet is :dickinson"`.

You can even mix and match normal and destructured arguments. Here, for example, is a function that has a garden-variety argument along with a destructured one:

```clojure
(defn artist-description [shout [novelist poet]]
  (let [msg (str "Novelist is " novelist
                 "and the poet is " poet)]
    (if shout (.toUpperCase msg) msg)))
```

This latest version of `artist-description` returns an uppercase version of the message if the nondestructured parameter shout is truthy.


