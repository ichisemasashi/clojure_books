
### Asking Questions

Being able to branch on an explicit true or `false` is only half of what makes `if` the programming workhorse that it is. The other half is being able to ask the questions that evaluate to a Boolean. Probably the most common question we ask in programs is "Does this thing equal this other thing?" Happily, the Clojure equality-testing function has a very obvious name.

It’s just a single equals sign:

```clojure
(= 1 1)                         ; True!
(= 2 (+ 1 1))                   ; True again!
(= "Anna Karenina" "Jane Eyre") ; Nope.
(= "Emma" "Emma")               ; Yes!
```

Like `+` and `*`, the `=` function looks like an operator but is really just a function.

And like `+` and `*`, the `=` function will take any number of arguments:

```clojure
(= (+ 2 2) 4 (/ 40 10) (* 2 2) (- 5 1)) ; True!
(= 2 2 2 2 3 2 2 2 2 2) ; False! There's a 3 in there.
```

> [!NOTE]
> 
> **Equality**
>  
> Note that the `=` function is built on the idea of structural equality: roughly, two values are equal according to `=` if they have the same value. Under the hood, `=` is identical to the Java `equals` method.


You can check if two things are not equal:


```clojure
(not= "Anna Karenina" "Jane Eyre")     ; Yes!
(not= "Anna Karenina" "Anna Karenina") ; No!
```

As you might expect, Clojure has a wide range of other Boolean-returning functions—or predicates—besides `=`. You can, for example, find out which of two numbers is bigger with `>` and `<`:

```clojure
(if (> a b)
  (println "a is bigger than b"))
(if (< b c)
  (println "b is smaller than c"))
```

If you have trouble mentally parsing the `>` and `<` expressions, start by thinking about the infix version: `(a > b)` or `(b < c)` and then move the operator to the front, giving you `(> a b)` and `(< b c)`. Accompanying `<` and `>` are `<=` and `>=`, which do exactly what you expect.

There is also a variety of "is this a that?" functions:

```clojure
(number? 1984)             ; Yes!
(number? "Anna Karenina")  ; "Anna Karenina" isn't a number.
(string? "Anna Karenina")  ; Yes, it is a string.
(keyword? "Anna Karenina") ; Not a keyword.
(keyword? :anna-karenina)  ; Yes a keyword.
(map? :anna-karenina)      ; Not a map.
(map? {:title 1984})       ; Yes!
(vector? 1984)             ; Nope.
(vector? [1984])           ; Yes!
```

These will tell you if a value is a number, a string, a keyword, a map, or a vector.


Clojure also features the usual cast of characters for doing more complicated Boolean logic. There is, for example, the `not` function, so that `(not true)` is `false` and `(not false)` is, unsurprisingly, `true`. There are also `and` and `or` for assembling larger Boolean expressions:

```clojure
;; Charge extra if it's an express order or oversized
;; and they are not a preferred customer.
(defn shipping-surcharge? [preferred-customer express oversized]
  (and (not preferred-customer) (or express oversized)))
```

It’s important to note that `and` and `or` do short-circuit evaluation: they evaluate just enough of their arguments to come up with a result. Thus in the last example, if we’re dealing with a preferred customer the function won’t even consider whether the order is being shipped express or is oversized.


