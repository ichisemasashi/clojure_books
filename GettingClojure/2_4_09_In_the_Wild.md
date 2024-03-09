
### In the Wild
                     
The best way to get a feeling for records and protocols is to see them in action in real code.
                       
For that let’s return to the Clostache templating library, which you will recall takes most of the pain out of the "substitute this value in the HTML" task that virtually all web applications need to perform. To make Clostache work you need a template string and a map of values:

```clojure
(require 'clostache.parser) 
(def template "The book {{title}} is by {{author}}")
(def values {:title "War and Peace" :author "Tolstoy"})
```

And you can use the Clostache `render` function to populate the template with your values:

```clojure
;; Gives you "The book War and Peace is by Tolstoy"
(clostache.parser/render template values)
```   
      
One of the more powerful features of Clostache is the ability to conditionally include or omit parts of the output with sections. Here’s a template with a section:

```clojure
(def data {:author "Tolstoy" :show-author true})
(def section-templ "{{#show-author}} by {{author}} {{/show-author}}")
```

Here the `{{#show-author}}` and `{{/show-author}}` define a section. The text in the section will only be rendered if the section value—`show-author` in this case—is truthy.

If you dig into the Clostache source code you will discover that sections are represented by record values:

```clojure
(defrecord Section [name body start end inverted])
```

The key fields in `Section` are `name`, which is the name of the controlling value—`show-author` in the example—and `body`, which is the text of the section.

Look a little further, and you will see `Section` values in action:

```clojure
(defn- render-section
  [section data partials]
  (let [section-data ((keyword (:name section)) data)]
    (if (:inverted section)
      (if (or (and (seqable? section-data)
                   (empty? section-data))
              (not section-data))
          (:body section))
    ; Lots of code omitted
    )))
```

Notice how `render-section` treats the `section` value—which is an instance of `Section`—like a garden-variety map with `:name`, `:body`, and `:inverted` keys. This is record use at its most basic. Clostache harbors no protocols, no polymorphism, not even a second record type. It’s just the Section record type there to make the code a little clearer.

You can find a great example of a protocol in [Stuart Sierra’s Component library](https://github.com/stuartsierra/component).  Component takes on the task of helping you build real-world applications—applications that frequently need to do things when they start up (perhaps connect to a database or initialize some data) and other things when they shut down (maybe close that database connection).

The Component library is built around a protocol called `Lifecycle`. Here it is in all its starkly simple glory:

```clojure
(defprotocol Lifecycle
  (start [component]
    "Begins operation of this component. Synchronous, does not return
    until the component is started. Returns an updated version of this
    component.")
  (stop [component]
    "Ceases operation of this component. Synchronous, does not return
    until the component is stopped. Returns an updated version of this
    component."))
```

With just two methods, `start` and `stop`, `Lifecycle` may seem trivial, but it provides the glue that you can use to build basic components, composite components made up of other little components, and so on.

Occasionally you may need to create a one-off implementation of a protocol.  Perhaps you are using—or testing—a function that takes a protocol implementation and you just don’t have an appropriate record at hand. For example, perhaps you’re trying to test your Component system and you need an implementation of `Lifecycle`. For these occasions Clojure provides us with `reify`, which takes a protocol name and some method implementations and creates a one-off implementation of that protocol:

```clojure
(def test-component (reify Lifecycle
                     (start [this]
                       (println "Start!")
                       this)
                     (stop [this]
                       (println "Stop!")
                       this)))
```


Run the preceding code, and you will end up with value in `test-component` that implements `Lifecycle`. Even better—especially for testing purposes—is that `reify` doesn’t require you to implement the whole protocol:

```clojure
;; A partial implementation of Lifecycle. We can call start, but
;; we will get an exception if we call stop.
(def dont-stop
  (reify Lifecycle
    (start [this]
      (println "Start!")
      this)))
```

As defined in the preceding code, `dont-stop` will be an instance of `Lifecycle`, but one that will throw an exception if you try to call the stop method.


