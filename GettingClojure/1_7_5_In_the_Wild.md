      
### In the Wild
      
Real-life Clojure functions are full of let expressions. In fact, `let` is one of the most commonly used Clojure features, up there with `defn` and `def`. If, for example, you look at the Ring source code you will find the `parse-params` function. Here’s a slightly simplified version of it:

```clojure
(defn parse-params [params encoding] 
  (let [params (codec/form-decode params encoding)]
    (if (map? params) params {})))
```

Without diving into the belts and pulleys of Ring, we can deduce that `parse-params` decodes some raw parameter data into a value that is either a map or something else, presumably `nil`. Once it has the result of that decoding bound to `params—courtesy` of `let`—it proceeds to return the decoded value if it is indeed a map, or an empty map if it’s not. 

If you dig around in Ring some more you’ll discover the `assoc-query-params` function, which uses `parse-params`. Here is a slightly simplified version of that function, which has a vanilla let embedded in an `if-let`:

```clojure
(defn assoc-query-params
  "Parse and assoc parameters from the query string
  with the request."
  [request encoding]
  (merge-with merge request
    (if-let [query-string (:query-string request)]
      (let [params (parse-params query-string encoding)]
        {:query-params params, :params params})
      {:query-params {}, :params {}})))
```

Pull the query string out of the request and call it `query-string` and, if you actually got something, proceed to parse the parameters and call the result in `params` and then … well, you get the picture.

For a truly imposing example of a `let`, we need to look no further than this, from [Incanter](http://incanter.org) :]


```clojure
(let [opts (if options (apply assoc {} options) {})
      data (or (:data opts) $data)
      _x (data-as-list x data)
      nbins (or (:nbins opts) 10)
      theme (or (:theme opts) :default)
      density? (true? (:density opts))
      title (or (:title opts) "")
      x-lab (or (:x-label opts) (str 'x))
      y-lab (or (:y-label opts)
                 (if density? "Density" "Frequency"))
      series-lab (or (:series-label opts) (str 'x))
      legend? (true? (:legend opts))
      dataset (HistogramDataset.)]
  ;; Do something heroic with x-lab and density?
  ;; and title and...
)
```

The preceding code, which is used in drawing histograms, binds no less then a dozen names. Glossing over the details—which thankfully need not concern us here—we can see that `opts`, which is defined right out of the gate, is used nine times later on in the `let`. Look a little more closely, and you can see that the value of opts comes out of an `if` expression, while the value of `y-lab` is computed with an `if` embedded in an `or`. Behold the power of an expression-based language.

The other thing to behold is how much computing gets done inside the square brackets of this `let`. There are `if`s and `or`s and a number other function calls going off in there. The lesson here is that if you have an intricate set of step-by-step values to compute, consider doing it inside the square brackets of `let`, giving each intermediate result an informative name. Your future self will thank you.
