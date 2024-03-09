
### Changing Your Map Without Changing It

The rule for modifying maps is pretty simple: you can’t. In exactly the same way that lists and vectors are immutable, maps are stubbornly resistant to change. But like lists and vectors, you can create a new map that is a modified copy of an existing map. One way you can make a modified copy is to add a key to a value, which you can do with assoc:

```clojure
(assoc book :page-count 362)
```

Which will give you this:

```clojure
{:page-count 362
 :title "Oliver Twist"
 :author "Dickens"
 :published 1838}
```

Using `assoc` is easy. You supply the original map, along with a key and a value, and you will get back a new map, just like the old one but with the key set to the new value. You can also feed more than one key/value pair into `assoc`, so if we wanted to change the title at the same time we add a page count, we could write this:

```clojure
(assoc book :page-count 362 :title "War & Peace")
```

which would give us a historically incorrect 362-page Russian novel by Dickens.

The `assoc` function’s natural partner in crime is `dissoc`. Where `assoc` adds a new key or changes the value associated with an existing key, `dissoc` removes a key and its associated value. Using `dissoc` is also straightforward: give it a map and a key and you will get back a new map, just like the old one, sans the key. Thus if you wanted to remove the publication date from Oliver Twist, you could do this:

```clojure
(dissoc book :published)
```

You can also throw multiple keys at `dissoc`, so that this:

```clojure
(dissoc book :title :author :published)
```

will leave you with a completely empty map. Keep in mind that `dissoc` will quietly ignore any keys that aren’t actually in the map, so that this:

```clojure
(dissoc book :paperback :illustrator :favorite-zoo-animal)
```

will return `book` untouched.

> [!NOTE]
>
> **Associative Vectors**
>
> Vectors and maps have a lot in common. They both associate keys with values, the difference being that with vectors the keys are limited to integers while in maps the keys can be more or less anything.
> That is, in fact, how Clojure looks at vectors—which means that many of the functions that work with maps will also work with vectors. For example, `assoc` and `dissoc` work fine on vectors. Thus `(assoc [:title :by :published] 1 :author)` will give you `[:title :author :published]`.


