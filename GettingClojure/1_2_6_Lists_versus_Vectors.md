
### Lists versus Vectors

So if vectors and lists are both ordered, sequential data structures, why have both? The reason Clojure includes both vectors and lists is that while they are similar on the outside, internally these two data structures are very different. As shown in the next figure, you can think of a vector as similar to an array, a big chunk of continuous memory. Just put the first item in the first slot of your block of memory, the second item in the second, and so on.

![fig_vectore](img/1_2_6_001.png)

Lists, by contrast, are implemented as linked lists—hence the name. As illustrated in the "figure on page 22", you can think of a list as a series of two-slot objects.

![fig_list](img/1_2_6_002.png)

One slot contains a reference to some data item while the second slot points at the next object in the list.

These two ways of organizing a single-file line of items have very different strengths. For example, getting to the 654th item of a vector is quick—behind the scenes Clojure does a little address arithmetic and there is the 654th item. In contrast, getting to the 654th item on a list involves running down the chain of all the previous items one at a time.

> [!NOTE]
>
> **How Many Slots?**
>
> Those two-slot list objects actually have three slots. The third slot is a count of the number of items in the list. This enables the `count` function do its thing without having to run down the whole list saying, One item, two items, three items …. We can get away with caching the count because lists are immutable.

However, the advantage does not lay entirely with the vectors. It’s much easier—and quicker—to tack a new item to the front of a list than a vector: with a list you just allocate a new two-slot thingie, then point the one slot at your item and the other slot at the original list. Since vectors rely on more or less continuous chunks of memory, adding a new item to the front is a lot more involved and might require allocating more memory and copying items from here to there. On the other hand, adding a new item to the end of a vector can be quick if there happens to be room at the end of the block of memory.

The implementation difference between lists and vectors bubbles to the surface very clearly with the `conj` function. Recall that `conj` takes a collection and an item and returns a new collection with all of the stuff from the original, plus the new item. Significantly, `conj` is aware of the differing strengths of vectors and lists and acts accordingly: it efficiently tacks the new item to the front of lists but to the end of vectors, so that

```clojure
(def poems '("Iliad" "Odyssey" "Now We Are Six"))
(conj poems "Jabberwocky")
```

will give you

```clojure
("Jabberwocky" "Iliad" "Odyssey" "Now We Are Six")
```

On the other hand, adding a new item to a vector with `conj` puts the item at the end, so that

```clojure
(def vector-poems ["Iliad" "Odyssey" "Now We Are Six"])
(conj vector-poems "Jabberwocky")
```

will return

```clojure
["Iliad" "Odyssey" "Now We Are Six" "Jabberwocky"]
```


