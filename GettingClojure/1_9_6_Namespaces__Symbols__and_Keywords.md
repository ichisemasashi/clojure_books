
### Namespaces, Symbols, and Keywords

Like symbols and vars, namespaces are just ordinary Clojure values, accessible to the mortal Clojure programmer. You can, for example, get at the current namespace: it’s always bound to the symbol `*ns*`, so that

```clojure
(println "Current ns:" *ns*)
```

will print something like

```
Current ns: #object[clojure.lang.Namespace 0x76c706bf user]
```

You can also look up any existing namespace by name:

```clojure
(find-ns 'user) ; Get the namespace called 'user.
```

With a namespace in hand, you can discover all the things defined in that namespace, so that this:

```clojure
(ns-map (find-ns 'user)) ; Includes all the predefined vars.
```
 
will give you a very large map of symbols to vars, essentially everything the `user` namespace knows about:

```clojure
{primitives-classnames #'clojure.core/primitives-classnames,
 +' #'clojure.core/+',
 Enum java.lang.Enum,
 decimal? #'clojure.core/decimal?,
 << and on and on >>
}
```

Conveniently, `ns-map` will find the namespace for you if you pass in a symbol, so we can shorten our last example to this:

```clojure
(ns-map 'user)
```

and still get a map of everything the namespace knows about.

Namespaces and symbols have an interesting relationship. As we’ve seen, you can write fully qualified symbols by including a namespace name, followed by a slash, followed by the symbol proper: `pricing/discount-price`. The namespace is actually part of the symbol, a part that you can get at with the `namespace` function:

```clojure
;; Gives us "pricing".
(namespace 'pricing/discount-print)
```

The thing to keep in mind about the namespace part of a symbol is that it’s just a name, not a reference to a namespace value. Thus we can make up symbols with nonexistent namespaces, so that `'narnia/caspian` is a fine symbol even if the `narnia` namespace doesn’t actually exist. Of course, if you remove the quote, as in `narnia/caspian`, then you are trying to look up `caspian` in the `narnia` namespace, which had better be there.

Keywords also have room for a namespace, which you can add by either explicitly calling out the namespace:

```clojure
:blottsbooks.pricing/author
```


or by doubling up the colon in the front:

```clojure
::author
```

If you double up the colon, the keyword will pick up the current namespace.

Given that there is no automatic lookup of keywords, adding a namespace to a keyword is mainly about preventing keyword collisions. Thus if you are worried that your `:book` may be confused with someone else’s `:book`, you can always slap an extra colon on it: `::book`. In practice most keywords go sans namespace.


