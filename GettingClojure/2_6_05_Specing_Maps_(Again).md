
### Spec’ing Maps (Again)

That brings us back to writing specs for maps. Recall that we passed over the strangeness that while the keywords in our actual book maps were sans namespaces (`:title`, for example), in the spec we used keywords with a namespace (`:inventory.core/title`, for example).

The reason for this is both clever and useful, two things that don’t always go together. When you specify the keys to a map, `clojure.spec` will try to look up the (fully qualified) keys in the registry. If it doesn’t find a spec registered under that key, no problem. But if it does, "it validates the value associated with that key in the map against the spec".

Since up to now we haven’t registered specs under `:inventory.core/title`, `:inventory`.  `core/author`, or `:inventory.core/copies`, the spec didn’t check the values associated with the `:title`, `:author`, and `:copies` keys in the map. That means that up to now this is a perfectly good `:inventory.core/book`:

```clojure
{:title 1234 :author false :copies "many"}
```

But it’s easy to tighten up the spec. All we have to do is define specs for `:inventory.core/title` and `:inventory.core/author`. Here’s the whole thing: 

```clojure
(s/def ::title string?)
(s/def ::author string?)
(s/def ::copies int?)
(s/def ::book (s/keys :req-un [::title ::author ::copies]))
```

Now we have a spec for our books that requires both the title and the author to be strings.


