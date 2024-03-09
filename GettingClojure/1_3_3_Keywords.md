
### Keywords

While a map will let you use virtually anything for a key, Clojure programmers commonly use keywords as keys. Like strings, numbers, and Booleans, keywords are a basic data type that comes packaged with Clojure. Syntactically, a keyword literal starts with a colon and then follows the same rules as symbols. Thus any of the following are fine keywords:

```clojure
:title
:author
:published
:word-count
:preface&introduction
:chapter-1-and-2
```

You can look at keywords as a sort of subspecies of a string: both are just a sequence of characters. The reason we have both keywords and strings is that, like vectors and lists, they are good at different things. Strings are data.  If you read an author’s name or title from a file, you’ll probably store that information in a string. Keywords are part of the program itself and meaningful to the people who read code. If you need a label to represent something in your code, perhaps the state of your finite state machine or whether you want to your logger to include the `:debug` information, use a keyword.

> [!NOTE]
>
> **Keywords Behind the Scenes**
>
> Technically, keywords are interned strings, similar to symbols in Ruby and distant cousins to the individual items that go into enumerated types in other languages.


Maps underline the contrast between keywords and strings brilliantly: they bring together the data—which could be the string `"Oliver Twist"`—with its programmatic marker, `:title`. Thus a more idiomatic version of our novel would be: 


```clojure
(def book
  {:title "Oliver Twist" :author "Dickens" :published 1838})

(println "Title:" (book :title))
(println "By:" (book :author))
(println "Published:" (book :published))
```

Using keywords as the keys of your map gives you yet another way to look up values in your map: if you call the keyword like a function and pass in a map, the keyword will look itself up in the map. In plain English this means that you can reverse this:

```clojure
(book :title)
```

to this:

```clojure
(:title book)
```

and still get back `"Oliver Twist"`. In fact, the second form, the one that uses a keyword as the function, is probably the most common way to extract a value from a map.


