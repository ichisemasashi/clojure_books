
### Decentralized Polymorphism

One interesting—and useful—aspect of protocols is that you can define and implement them after the fact. Imagine, for example, that you are working on a Clojure application and six months ago one of your colleagues implemented the `Employee`, `FictionalCharacter`, and `SuperComputer` records. And now, for reasons that need not concern us, you have just been handed the job of producing a positive marketing slogan for instances of each of these record types. So you start by defining a new protocol:

```clojure
(defprotocol Marketable
  (make-slogan [this]))
```

And then you go back and modify the `Employee`, `FictionalCharacter`, and `SuperComputer defrecords` so that they support the new protocol. Well, you could do that, but you don’t have to. Armed with Clojure’s `extend-protocol`, you can implement the new protocol for existing types independently of those type definitions:

```clojure
(extend-protocol Marketable
  Employee
    (make-slogan [e] (str (:first-name e) " is the BEST employee!"))
  FictionalCharacter
    (make-slogan [fc] (str (:name fc) " is the GREATEST character!"))
  SuperComputer
    (make-slogan [sc] (str "This computer has " (:no-cpus sc) " CPUs!")))
```

Notice how `extend-protocol` is a sort of inside-out `defrecord`. It starts with a single protocol and then enumerates the implementations of that protocol. Armed with `extend-protocol` you can call `make-slogan` on employees, fictional characters, and supercomputers. In fact, you can even extend your protocol to embrace data types that aren’t records:

```clojure
(extend-protocol Marketable
  String
    (make-slogan [s] (str \" s \" " is a string! WOW!"))
  Boolean
    (make-slogan [b] (str b " is one of the two surviving Booleans!")))
```

This means that protocols and records are as mutually flexible as they can be. You can cook up new protocols as you need them and use `extend-protocol` to implement your new protocol on any existing type, without touching the original definition of that type.


