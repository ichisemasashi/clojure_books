  
### In the Wild
  
The Clojure language plugin for the [LightTable text editor](http://lighttable.com) shows off a great example of using an atom to track some fairly [complex data](https://github.com/LightTable/Clojure).      
In this case it’s configuration data:
                    
```clojure                     
;; Lots of code omitted.       
(def my-settings (atom {:name "clj"
                        :dir (fs/absolute-path fs/*cwd*)
                        :type "lein-light-nrepl"
                        :commands [:editor.eval.clj
                                   :editor.clj.doc
                                   :editor.cljs.doc
                                   :editor.clj.hints
                                   ;; Many keywords omitted... 
                                   :editor.eval.cljs
                                   :cljs.compile]}))
;; Lots of code omitted.
(defn settings! [setts]
  (swap! my-settings merge setts))
``` 

Here we start with an atom containing a map of program settings along with a function that lets us merge new settings into the map. Anytime we need one of the settings, we just have to remember to dereference the atom, perhaps `(:dir @my-settings)`.

While atoms are frequently bound in a top-level `def`, you can also create them inside of functions. A wonderful example of this is lurking in the standard Clojure function `memoize`. The purpose of `memoize` is to speed up your program by caching the results of function calls. The `memoize` function takes an existing function, perhaps this one:

```clojure
(defn blurb [book]
  (str "Don't miss the exciting new book, "
       (:title book)
       " by "
       (:author book)))
```

and returns a new function:

```clojure
(def memoized-blurb (memoize blurb))
```

You can now use the new—or memoized—function exactly as you would the original. The first time you call the memoized function it will simply delegate to the original. But it will also cache the result, so that if you happen to call the memoized function with the same arguments a second—or fifty-fourth—time, it will skip the original function and return the cached result:

```clojure
(def emma {:title "Emma" :author "Austen"})
;; Only calls the blurb function once.
(memoized-blurb emma)
(memoized-blurb emma)
(memoized-blurb emma)
```

The `memoize` function is doing the classic trade-off of memory—all of those cached values—for speed—not having to make redundant calls to the original function. Of course, this all assumes that the original function is pure, that the result depends only on the values passed in. So memoizing a function that returns the current time is probably a bad idea.

Without knowing anything else about the internals of the function returned by `memoize`, we can deduce that it somehow has access to mutable state. After all, its behavior changes over time. Call the function once with some set of arguments, and it will call the wrapped function. Do the same thing again, and it will return the cached value. Somewhere, something changed during that first function call.


In fact, each function that is returned by `memoize` has its own private atom, an atom that contains a map of arguments to return values. When you call the memoized function it first consults the map to see if it has seen these arguments before. If it has, it returns the cached value. If not, it calls the original function, caches the new result in the map, and returns the result.

Here’s the code for `memoize`, slightly reformatted:

```clojure
(defn memoize
  "Returns a memoized version of a referentially
  transparent function. The memoized version of
  the function keeps a cache of the mapping from
  arguments to results and, when calls with the
  same arguments are repeated often, has
  higher performance at the expense of higher
  memory use."
  [f]
  (let [mem (atom {})]
    (fn [& args]
      (if-let [e (find @mem args)]
        (val e)
        (let [ret (apply f args)]
          (swap! mem assoc args ret)
          ret)))))
```

Taking this step by step, we see that the anonymous function returned by `memoize` starts—as predicted—with an atom containing an empty map. That’s the `(atom {})`. When the anonymous function is called, it updates the value of the atom as needed. That’s the call to `swap!` at the core of the function.


It’s important to keep track of what is and isn’t changing as the anonymous function is called. It’s certainly not the function. It’s also not the map: that’s one of those solidly immutable Clojure maps. Instead it’s the mutable value inside of the atom that changes, successively getting hold of bigger and bigger—but steadfastly immutable—maps.

You can find an interesting use of refs lurking inside the open source music system Overtone that we met back in "Chapter 10, Sequences, on page 111".
Overtone includes a software metronome, a virtual rendition of one of those "click Stay click On click The click Beat" machines so hated by 10-year-old piano students.

Here’s the function that manufactures new metronomes:

```clojure
(defn metronome
  [bpm]
  (let [start (ref (now))
        bar-start (ref @start)
        bpm (ref bpm)
        bpb (ref 4)]
    (Metronome. start bar-start bpm bpb)))
```

Notice all the calls to `ref`, which makes sense because as the software metronome is clicking away we want to carefully coordinate the time (`start`) it started with the number of clicks (or beats) per minute (`bpm`), among other things.

And here’s a snippet of the code that changes the beats per minute:

```clojure
(dosync
  (ensure bpb)
  (let [cur-beat (metro-beat metro)
        cur-bar (metro-bar metro)
        new-tick (beat-ms 1 new-bpm)
        new-tock (* @bpb new-tick)
        new-start (- (metro-beat metro cur-beat) (* new-tick cur-beat))
        new-bar-start (- (metro-bar metro cur-bar) (* new-tock cur-bar))]
    (ref-set start new-start)
    (ref-set bar-start new-bar-start)
    (ref-set bpm new-bpm)))
```

This is a ref-oriented `dosync` with a couple of twists we haven’t seen before.  First, the ref updating is done with `ref-set` rather than `alter`. You can think of `ref-set` as a sort of shortcut that you can use to update a ref when the new value doesn’t depend on the previous value, more or less synonymous with `(alter ref (fn [_] new-value))`.

The other new wrinkle is the use of `(ensure bpb)`, which makes `bpb` part of the current update without altering its value. It ensures that no other `dosync` changes the value of `bpb` while the current `dosync` is executing.

You can also find an interesting use of `agent` lurking inside Overtone:

```clojure
(def samples (load-samples "~/Desktop/tech/*.wav"))

(defn start-samples
  "Starts all samples playing at init-vol. Returns a seq containing info
  regarding all running samples. Samples start playing 1s after this
  fn is called to ensure that they are all started in sync"
  []
  (at (+ 1000 (now))
      (doall
        (reduce (fn [res samp]
                  (let [id (loop-synth samp 0)]
                    (conj res {:ampl 0
                               :id id
                               :samp samp})))
                []
                samples))))
(def playing-samples* (agent (start-samples)))
```

Like our inventory notification example, the `playing-samples*` agent is there to perpetrate side effects. In this case the side effect is actual music coming out of real speakers. The "one update at a time" processing used by agents is just the thing to prevent you from playing Mozart over your hip-hop. Unless that’s the effect you’re looking for.


