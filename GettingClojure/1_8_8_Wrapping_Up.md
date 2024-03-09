
### Wrapping Up

In this chapter we took a hard look at the mechanisms behind `def`. They’re remarkably transparent: `def` creates a var (a Clojure value) which is an association between another Clojure value (a symbol) and a third value. We also looked at dynamic vars—vars that let you swap in a new value while you evaluate an expression or six.

Armed with an understanding of `def` and vars, we’re now ready to look at namespaces, the mechanism Clojure uses to organize vars into related buckets.

