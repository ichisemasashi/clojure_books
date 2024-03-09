
### Record Confusion

If you’re coming to Clojure from an object-oriented language, then this record and protocol talk probably rings a very familiar bell. Record types and their instances do look a lot like the classes and objects that you find in object-oriented languages. And protocols clearly resemble the abstract types or interfaces that you also find in the object-oriented world. None of this is by chance. Records are Clojure’s approach to building a structured, composite data type with predefined fields. Similarly, protocols are Clojure’s riff on type-based polymorphism—the idea that you can have a single operation implemented in different ways by different types.

But record types and values part company with object-oriented classes and objects in important ways. Clojure record values are exactly as immutable as Clojure’s vectors and maps. Records are also innocent of implementation inheritance. There are no super record types. And, as we saw in the last section, records and protocols are independent of each other in time and space.

At any given moment you can decide to implement some protocol—including one you have just constructed—on any record type.

Perhaps the biggest practical difference between Clojure’s records and protocols and the objects and classes that you find in an object-oriented language is that many significant Clojure programs get along just fine without them.

That leads us to the best approach to using records and protocols: start without them. Start with plain old maps and functions and see how things go. If, as you go along, your code starts to drift toward the unreadable, and records help, then put in some records. If you are having performance problems and it seems like records, with their speedy field access, would make things better, then try some records. And if you find that you need some polymorphism, and perhaps need to leave the door open for some polymorphic extensions in the future, then reach for the protocols. But start simple: you may just find that maps and functions are enough.

Protocols raise a different question, one that can be summed up with "Don’t we have this polymorphic thing covered with multimethods"? Recall that multimethods (which we covered in "Chapter 5, More Capable Functions, on page 49") let you create completely arbitrary polymorphic functions.


While there is a lot of overlap between protocols and multimethods, there is also a fair bit of daylight. Each multimethod defines a single, stand-alone operation. Protocols can include a whole bundle of related operations. Multimethods support a completely arbitrary dispatch mechanism. Protocols dispatch based on a type mechanism. If you don’t need all of the generality of a multimethod you are better off using a protocol.


