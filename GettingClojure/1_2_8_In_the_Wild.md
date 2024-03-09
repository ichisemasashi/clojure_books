
### In the Wild

If you look at real-world code you will discover that Clojure programmers overwhelmingly choose the vector over the list for their sequential-data-structure needs. Thus, finding real-world uses of the vector brings new meaning to the word easy. Vectors are found at the heart of just about every Clojure program. Take, for example, this bit of real-world code:

```clojure
(defn escape-html [string]
  (replace-all string [["&" "&amp;"]
                       ["\"" "&quot;"]
                       ["<" "&lt;"]
                       [">" "&gt;"]]))
```

Clearly the preceding function—which I adopted from the [Clostache HTML templating library](https://github.com/fhd/clostache) defines a function called `escape-html`. Equally clear is that the `escape-html` function takes a single parameter with the less-than-enlightening name of `string`. The body of `escape-html` consists of a single call to the function `replace-all`, which takes the string and a vector of vectors.

Step back a bit and it’s obvious what `escape-html` is all about—obvious, at least, to anyone who has ever tripped over an ampersand in the middle of a web page. The `escape-html` function is in the business of foiling those attempts to embed actual HTML in innocent-looking text. And it’s all done with vectors.

You can find a similar use of vectors in this bit of code, lifted from the [Pedestal application framework examples](https://github.com/pedestal/samples/blob/master/template-server/src/template_server/service.clj):


```clojure
(defroutes routes
  [[["/" {:get home-page} ^:interceptors [bootstrap/html-body]
     ["/hiccup" {:get hiccup-page}] 
     ["/enlive" {:get enlive-page}]
     ["/mustache" {:get mustache-page}]
     ["/stringtemplate" {:get stringtemplate-page}]
     ["/comb" {:get comb-page}]]]])
```

This bit of code contains Clojure features we haven’t covered yet: what, you might wonder, is this `defroutes` thing? (It’s a macro. See "Chapter 20, Macros, on page 241.") And what is this `^:interceptors` weirdness? (It’s metadata. See "Chapter 19, Read and Eval, on page 229.") Not to mention all the curly brackets.  (Those are maps. See "Chapter 3, Maps, Keywords, and Sets, on page 27".)

But skip over all that and focus on the vectors and, again, if you are at all familiar with web applications, it’s probably clear what is going on: the code is using the vectors to specify what the application should do when a get request comes on various URL paths: do this if someone points their browser at `"/hiccup"` and that if they hit `"/enlive"`. The lesson here is that even at this very early phase of our adventures in Clojure, we can look at some advanced code and glean a bit of what it’s up to. And that there are vectors everywhere.

While Clojure programmers mostly rely on vectors, lists do get a fair bit of use, especially in those situations where you want to build your sequence by appending new items to the front as opposed to the back. Certainly it is no great leap to imagine `defroutes` with lists instead of vectors. But when there aren’t great algorithmic issues at stake, Clojure programmers generally reach for square brackets.

There is one giant exception to the mostly use vectors rule. Earlier we saw that you need the quote in front of your list to prevent it from being confused with Clojure code, specifically a function call. Clearly we don’t want to confuse `'("Emma" "Coma" "War and Peace")` which is data, with `(println "Emma" "Coma" "War and Peace")`, which is code. But as we’ll see in "Chapter 19, Read and Eval, on page 229", the similarity between lists and code is neither accidental nor skin-deep.  In fact we’ll discover that every time you write Clojure code you are really creating lists, lots of lists. Stay tuned.

