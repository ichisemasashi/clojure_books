
### Wrapping Up

In this chapter we explored lazy sequences, those Zen collections full of elements that are not there until you look. We saw how Clojure is full of functions like `repeat` and `iterate` that create lazy sequences, and functions like `map` and `take` that are capable of processing them. We also looked at how you can use these functions to build up useful sequences, and at how it’s all implemented.

Along the way we saw how laziness can sometimes come as a bit of a surprise and how to use `doall` and `doseq` to ensure that your sequence is populated "right now".

Now that you know a lot about putting data into collections, it’s time to look at reversing the process. So in the next chapter we’re going to turn to a feature that will enable us to pluck just the value we need from a collection, even if that collection is nested inside of another collection. Or two. Or three.



