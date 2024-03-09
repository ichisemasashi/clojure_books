
### Truthy and Falsy

One notable aspect of Clojure’s handling of Booleans is that the language is willing to treat any value as a Boolean. This, for example is a perfectly good `if`:
 
```clojure
(if 1
  "I like science fiction!"
  "I like mysteries!")
```

As is this:

```clojure
(if "hello"
  "I like science fiction!"
  "I like mysteries!")
```

And even this:

```clojure
(if [1 2 3]
  "I like science fiction!"
  "I like mysteries!")
```

Not only are these good `if` statements, but in all three cases the winner is science fiction. The rule is simple: in an `if` statement and any other Boolean context, only `false` and `nil` get treated as false. Everything else is treated as true. Thus this expression will announce a love of mysteries:

```clojure
(if false "I like scifi!" "I like mysteries!") ; Mysteries!
```

And so will this one:

```clojure
(if nil "I like scifi!" "I like mysteries!") ; Mysteries!
```

Since everything other than `false` and `nil`—and I do mean everything—gets treated as `true`, Clojure treats all strings, all numbers, and all keywords as `true`. Consequently, all of the following expressions will evaluate to `"yes"`:

```clojure
(if 0 "yes" "no")       ; Zero's not nil or false so "yes".
(if 1 "yes" "no")       ; 1 isn't nil or false so "yes".
(if 1.0 "yes" "no")     ; 1.0 isn't false nor nil: "yes".
(if :russ "yes" "no")   ; Keywords aren't false or nil so "yes".
(if "Russ" "yes" "no")  ; "yes" again.
(if "true" "yes" "no")  ; String contents don't matter: "yes".
(if "false" "yes" "no") ; The string "false" isn't false: "yes".
(if "nil" "yes" "no")   ; And the string "nil" ain't nil: "yes".
```

The "anything else is true" rule also applies to collections. Thus, since no vector is equal to `false` nor is any vector equal to `nil`, all vectors—even empty ones—are treated as `true`:

```clojure
(if [] (println "An empty vector is true!"))
(if [1 2 3] (println "So is a populated vector!"))

(if {} (println "An empty map is true!"))
(if {:title "Make Room! Make Room!" }
  (println "So is a full map!"))

(if () (println "An empty list is true!"))
(if '(:full :list) (println "So is a full list!"))
```


All the `if` expressions in the preceding examples will produce some output.

One issue with having a more or less infinite collection of true things along with two different false things is the terminology. When we say something is true, do we mean the specific value `true` or just true in the sense of not being `false` or `nil`? To avoid confusion, Clojurists sometimes refer to the values that are treated as true in the more general sense as being truthy. Thus, while only `true` is really `true`, `"hello"`, `1.0`, and `"Russ"` are all truthy. Similarly, we can use falsy to describe the metaphysical quality shared by `nil` and `false`. In Clojure there are exactly two falsy things—`false` and `nil`—and an infinite number of truthy things.

> [!NOTE]
>
> **Falsy**
>
> Some Clojure programmers believe the proper spelling is falsey.  This belief is false.

