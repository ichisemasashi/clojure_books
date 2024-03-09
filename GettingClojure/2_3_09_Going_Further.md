           
### Going Further

Destructuring is one of those convenience features that always seems to leave you wanting more. For instance, consider that when destructuring a map with keyword keys, people tend to use symbols that look just like the keys.  If, for example, we were working with maps that looked like this:

```clojure
{:name "Romeo" :age 16 :gender :male}
```

we might be inclined to bind the value of `:name` to `name`, `:age` to `age`, and `:gender` to `gender`:
         
```clojure
(defn character-desc [{name :name age :age gender :gender}]
  (str "Name: " name " age: " age " gender: " gender))
```

This does work, but there’s a lot of repetition in the code as we repeat our way through `name :name age :age gender :gender`. If only we could just say, Oh, and pull out `:name`, `:age`, and `:gender` as `name`, `age`, and `gender`. Turns out we can:

```clojure
(defn character-desc [{:keys [name age gender]}]
  (str "Name: " name " age: " age " gender: " gender))
```

Essentially `:keys` says that you are going with the convention of using the keyword names as your local names. Instead of endlessly repeating the symbol and the keyword `(name :name)` you just list the values you want to extract in a vector following the `:keys`. 

Even better, you can mix and match `:keys` with ordinary by hand destructuring; thus we could have said 

```clojure
(defn character-desc [{:keys [name gender] age-in-years :age}]
  (str "Name: " name " age: " age-in-years " gender: " gender))
```

Don’t be fooled by the syntax. `:keys` is a special value, used by destructuring to mark off the "same symbol as key" vector. Destructuring knows this is a special value because when we’re doing normal destructuring we don’t have keywords (with their leading colon) on the left side of the destructuring equation.

Another seeming drawback of destructuring, at least when used with functions, is that it eats the value you’re destructuring. Nowhere, for example, in the preceding code’s `character-desc` function do we have access to the complete name/gender/age map. You can certainly have your map and your destructuring too, with the proper application of `let`:

```clojure
(defn add-greeting [character]
  (let [{:keys [name age]} character]
    (assoc character
           :greeting
           (str "Hello, my name is " name " and I am " age "."))))
```

The `add-greeting` function in our code example wants to add a new key/value pair to the `character` map passed in, but the new value depends on some of the existing values (`:name` and `:age`). Thus it needs both the destructured name and age but also the whole, untouched `character` map.

Happily, destructuring provides a convenient shortcut for this, as well, in the form of `:as`.

```clojure
(defn add-greeting [{:keys [name age] :as character}]
  (assoc character
         :greeting
         (str "Hello, my name is " name " and I am " age ".")))
```


In this last version of `add-greeting` we’re using `:as` to pick up the whole map without the need to write an additional `let`.

