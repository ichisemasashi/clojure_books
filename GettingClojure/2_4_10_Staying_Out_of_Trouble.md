
### Staying Out of Trouble
  
Possibly the easiest mistake you can make with a record is to attempt to get a value into a field and to miss. While this code may look plausible, it’s nonsense:

```clojure
(map->FictionalCharacter {:full-name "Elizabeth Bennet"
                          :book "Pride & Prejudice"
                          :written-by "Austen"})
```

The trouble is that fields in `FictionalCharacter` are called `name`, `appears-in`, and `author`, not `full-name`, `book`, and `written-by`. The expression in the preceding code will give you a six-field record, with the three built-in fields set to `nil`, complemented by three additional fields—remember, records are also maps— set to the values we see in the code.
 
You can come to the same unfortunate end with botched uses of `assoc`:

```clojure
(assoc elizabeth :book "Pride & Prejudice")
```

Another thing to keep in mind is that protocols take up more room in your namespace than is immediately apparent. As we’ve seen, evaluating `(defprotocol Person...)` binds the symbol `Person` to the protocol definition. That’s implied in the `def` part of `defprotocol`. The thing that can bite you is that when you define a protocol you are also defining functions, one for each method in your protocol. This means you need to be careful with the names you pick for your method. If, for example, instead of using `full-name` in our `Person` protocol we had gone with name like this:

```clojure 
(defprotocol CollidingPerson 
  (name [this])
  (greeting [this msg])
  (description [this]))
```


we would have seen the following warning from Clojure:

```
Warning: protocol #'user/CollidingPerson is overwriting function name
```

The problem is that `defprotocol` is trying to define a function called `name` but `name` is already a built-in function that comes with Clojure. This is not necessarily fatal, as long as you know which `name` you’re using at any given moment.

In the same vein, you should watch out for dueling protocols:

```clojure
(defprotocol Person
  (full-name [this])
  (greeting [this msg])
  (description [this]))
(defprotocol Product
  (inventory-name [this])
  (description [this]))
```

Again you will see a warning as `description` from `Product` overrides the `Person` `description`:

```
Warning: protocol #'user/Product is overwriting method
description of protocol Person
```

The solution to these kinds of protocol-versus-protocol name conflicts is simple: when in doubt, put each protocol in its own namespace.


Finally, you should be aware that records have a more generic cousin in types.  In the same way that you define a new record type with `defrecord`, you define new types (or perhaps a type type?) with `deftype`. The difference is that while records come with a fair bit of built-in behavior—think of all those maplike talents that records just have—types are more of a blank slate. When you define a type it’s up to you to define all of the behavior of instances of your new type. This is more work, but it also means you have full control. Types are one of those language features that you may never use, but they are there if the need ever arises.


