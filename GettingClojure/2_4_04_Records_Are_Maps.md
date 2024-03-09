
### Records Are Maps

No matter how you create your record instances, once you have them in hand you can treat them just like maps with keyword keys:

```clojure
(:name elizabeth)    ; => "Elizabeth Bennet"
(:appears-in watson) ; => "Sign of the Four"
```

The resemblance between records and maps is much more than skin- (or keyword?) deep: Any function that works with a map will also work with a record:

```clojure
(count elizabeth) ; => 3
(keys watson)     ; => (:name :appears-in :author)
```

You can also use `assoc` to modify the values in your record:

```clojure
(def specific-watson (assoc watson :appears-in "Sign of the Four"))
```

You can even `assoc` brand-new, not-in-the-record-type keys into your record instances:

```clojure
(def more-about-watson (assoc watson :address "221B Baker Street"))
```

You will get back a new `FictionalCharacter` instance that has the three predefined fields along with the new `:address` entry. Note that any extra values you `assoc` into your records are exactly that: extra. They get carried around like any other map fields, but they don’t affect the record type in any way and they don’t get the magic speed boost of the built-in fields.

