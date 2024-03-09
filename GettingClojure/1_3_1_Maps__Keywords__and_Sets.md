
## CHAPTER 3 Maps, Keywords, and Sets

As we saw in the last chapter, when a Clojure programmer wants to store some values one after the other, single file, they tend to reach for a vector or perhaps a list. But one thing after the other is not the only way to organize the data in your programs. Sometimes you want to take a hodgepodge of values, give each one a name, and roll them all into a single value. Perhaps you have the author’s name and the title and the date of publication and you want to pull them all together into a single bookish thing. In this chapter we’re going to look at maps, the Clojure data structure that allows you to do exactly that. Along the way we’ll run into another Clojure data structure, the set, as well as a new primitive type, the keyword. Let’s get started.


