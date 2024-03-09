
### Striking a More Specific Bargain with Records

Happily, Clojure provides an alternative to maps that mitigates some of these shortcomings: the record. You can think of records as maps with some predefined keys. To make a record you need to first define the record type:
                                 
```clojure                       
(defrecord FictionalCharacter[name appears-in author])
```

As you can see from the code, the record type has a name and a list of the predefined fields. So if you evaluate the expression in our example, you end up with a record type called `FictionalCharacter` that has three fields, one for the character’s name, one for the name of the fictional work the character appears in, and one for the author. Behind the scenes, `defrecord` creates a couple of functions, whose names are based on the record type. In our example, we get `->FictionalCharacter` and `map->FictionalCharacter`.
                   
> [!NOTE]
>
> **Instant Vars** 
> 
> Since `defrecord` begins with def it’s reasonable to assume that it is in the var-creation business. In fact, `defrecord` creates a number of vars.  There’s one for the record type, and one each for the two factory functions. The same is true of `defprotocol`, which we’ll meet presently.


There are a couple of ways to kick off the second stage of record creation, which is to create instances of your record type—actual fictional characters in our example. First, we can use the `->FictionalCharacter` function to create our first `FictionalCharacter` instance:

```clojure
(def watson (->FictionalCharacter "John Watson" "Sign of the Four" "Doyle"))
```


As you can see from the example, `->FictionalCharacter` takes values for each of the fields in the record, in the order that they were specified in `defrecord`, and gives you back a new record instance, which will print in the REPL like this:

```clojure
#records.core.FictionalCharacter{:name "John Watson",
                                 :appears-in "Sherlock Holmes",
                                 :author "Doyle"}
```

Alternatively, we can use `map->FictionalCharacter`, which expects its arguments rolled up in a map, like this:

```clojure
(def elizabeth (map->FictionalCharacter
                  {:name "Elizabeth Bennet"
                   :appears-in "Pride & Prejudice"
                   :author "Austen"}))
```


Just make sure the map you pass in has keyword arguments that match the names of the fields in the record.


