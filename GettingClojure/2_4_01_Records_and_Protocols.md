
## CHAPTER 13 Records and Protocols

One of the things that makes programming such a joyous challenge is that it’s full of trade-offs. One day you might find yourself choosing between making the program faster at the cost of a more complicated algorithm or a bit more memory. Twenty-four hours later you’re trying to strike a balance between ease of use and a more powerful interface. And by the third day you’re wrestling with the tension between building code that works decently everywhere versus a more specialized implementation that works better, but only "right here".

It’s this last "generic versus specialized" question that we’re going to take up in this chapter as we look at Clojure records. In the pages that follow we’ll see how records are a more specialized data-storage alternative to maps. We’ll start by reviewing just what a record is and how you create them. We’ll move on to the question of when to use a record and when to use a map. That will bring us face to face with the question of how you do polymorphism in Clojure, and that will bring us to protocols. Finally we’ll round out this chapter in our usual way by looking at some real-world uses of records and protocols and how to avoid some of the sharp edges of records.


