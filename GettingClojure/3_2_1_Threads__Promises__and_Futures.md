
## CHAPTER 17 Threads, Promises, and Futures

One of the things that makes programming such a challenge is that many of our sharpest tools are also our most dangerous weapons. Having functional values means that we can deliver a little package of code to the right place at the right time. But functional values also separate the action from the source code in a way that can make it hard to debug your program. In the same spirit, lazy sequences are wonderful, except for those infuriating times when you forget that your sequence is indeed lazy. But nothing can compare to the potential for both good and evil that comes with the thread. Having your program do several things at once is a boon: "Hey, I can do all these jobs at the same time!" But it’s also a danger: "Oh, look at those two threads fighting over that data!"

So with this chapter we start a two-chapter look into the related topics of threads and mutable state. We’ll kick things off in this chapter by introducing the fundamentals. We’ll look at how you can create threads in a Clojure program, at some of the havoc they can wreak if you aren’t careful, and at some of the simple mechanisms that Clojure provides to keep the chaos in check.



