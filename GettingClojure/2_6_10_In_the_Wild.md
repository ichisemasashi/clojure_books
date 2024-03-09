
### In the Wild

You can find a great example of `clojure.spec` in action in the [`ring-spec`](https://github.com/ring-clojure/ring-spec), which contains specs for the Ring web application library. For example, as we saw back in "Chapter 6, Functional Things, on page 63", a key part of a Ring application is dealing with requests, which arrive at the application in the form of maps. But what, exactly, do those request maps contain?

I’m glad you asked:

```clojure
(s/def :ring/request
(s/keys :req-un [:ring.request/server-port
                 :ring.request/server-name
                 :ring.request/remote-addr
                 :ring.request/uri
                 :ring.request/scheme
                 :ring.request/protocol
                 :ring.request/headers
                 :ring.request/request-method]
        :opt-un [:ring.request/query-string
                 :ring.request/body]))
```

Looks like we have a map with entries for port, a server name, a remote address, and so on. And it looks like the query string and body are optional.

And if you were curious about what you might find in these fields, you just have to look a bit closer:

```clojure
(s/def :ring.request/server-port (s/int-in 1 65535))
(s/def :ring.request/server-name string?)
(s/def :ring.request/remote-addr string?)
; And so on...
```

You can see other interesting specs in [`clojure.specs.alpha`](https://github.com/clojure/core.specs.alpha), which contains the specs for Clojure itself. Look in that repository, and you will find the following spec definition:

```clojure
(s/def ::defn-args
  (s/cat :name simple-symbol?
         :docstring (s/? string?)
         :meta (s/? map?)
         :bs (s/alt :arity-1 ::args+body
                    :arity-n (s/cat :bodies (s/+ (s/spec ::args+body))
                                    :attr (s/? map?)))))
```

The name `::defn-args` says it all. This is the spec for the arguments that you can pass to `defn`. Note that this isn’t a spec for the arguments to some particular function defined with `defn`; it’s the spec for what `defn` itself will accept as arguments. Notice how the spec requires a symbol—the name of the function— followed by a docstring, and then the metadata (something we’ll talk about in "Chapter 19, Read and Eval, on page 229"), followed by the body of the function.

There are a couple of things to note about this spec. The first is that it’s not stand-alone. It relies on the previously defined `::args+body` spec and the `simple-symbol?` function (both of which I’m omitting here) to do much of the work. Also note that the `::defn-args` spec uses a feature of `clojure.spec` we haven’t seen before, `s/?`. Essentially, `s/?` makes the next part of the spec optional. Thus, while the function name is required, both the docstring and the metadata are optional.

You can also find the function spec for `defn` in the same file:

```clojure
(s/fdef clojure.core/defn
  :args ::defn-args
  :ret any?)
```


and discover that defn takes the arguments specified by `::defn-args` and (at least according to this spec) returns some value—we care not which.


