
### The Record Advantage

So if records are just maps with some fields wired in, are they really worth the bother? As usual, the answer is it depends. One concrete advantage of records is that getting at the hard-wired fields is faster than getting at the equivalent fields in a map. Thus, if you had this:

```clojure
(def irene {:name "Irene Adler"
            :appears-in "A Scandal in Bohemia"
            :author "Doyle"})
```
  
you would expect that this would be faster:
    
```clojure
(:name watson)
```

than this:

```clojure
(:name irene)
```

How much faster? Certainly not enough to matter if you are only dealing with a few novels—or a few tens of thousands. But if you are dealing with seriously large mounds of data, the performance advantages of records are something to consider.

Another reason to use records is the one that I touched on at the beginning of this chapter: they can help you make your code clearer. For example, a glance at this code:

```clojure
;; Define the record types.
(defrecord FictionalCharacter[name appears-in author])
(defrecord SuperComputer [cpu no-cpus storage-gb])
;; And create some records. 
(def watson-1 (->FictionalCharacter
                "John Watson"
                "Sign of the Four"
                "Doyle"))
(def watson-2 (->SuperComputer "Power7" 2880 4000))
```

leaves no doubt that `watson-1` is a fictional detective’s assistant, while `watson-2` is an eerily intelligent quiz show–playing machine. And if you’re still puzzled about what kind of thing you’ve gotten hold of, you can always use the `class` function, which will return the type of the record.

```clojure
(class watson-1) ; user.FictionalCharacter
(class watson-2) ; user.SuperComputer
```

You can also use `instance?` to test if a value has a particular type:


```clojure
(instance? FictionalCharacter watson-1) ; True.
(instance? SuperComputer watson-2)      ; Nope.
```

Keep in mind that while `class` and `instance?` are great tools for poking around in the REPL, you should generally avoid using them in real code. This sort of thing:

```clojure
;; Don't do this!
(defn process-thing [x]
  (if (= (instance? FictionalCharacter x))
    (process-fictional-character x)
    (process-computer x)))
```

is bound to lead you to spaghetti code and grief. Fortunately, Clojure has a better way of dealing with this sort of type-sensitive code: protocols.

> [!NOTE]
>
> **Class?**
>
> The `class` function works for all values, not just records. A good rainy-day programming project is to spend some time feeding values into `class` to see what comes out.



