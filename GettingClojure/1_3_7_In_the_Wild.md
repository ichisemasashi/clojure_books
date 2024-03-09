
### In the Wild

Maps are the Swiss Army knives of Clojure programming: any time you need to bundle together some related data items into a unified whole, one of your first thoughts should be, "Hey, I’ll use a map". You can see this thinking at work in the `clojure.java.jdbc` library, which provides an easy-to-use Clojure interface to a wide variety of relational databases. The functions supplied by `clojure.java.jdbc` need several pieces of configuration information to find your database, which you supply with a map:

```clojure
(require 'clojure.java.jdbc)
(def db {:dbtype "derby" :dbname "books"})
(clojure.java.jdbc/query db ["select * from books"])
```

In the example, the map bound to `db` contains the database connection information. Don’t worry about the `require` expression—it simply ensures that the `clojure.java.jdbc` library is loaded. I’ll have much more to say about this in Namespaces. For now focus on how using a map for this kind of configuration data means you’re free to vary the contents depending on circumstances.  Thus, if you want to connect to a different kind of database, you just cook up a different map:

```clojure
(def db {:dbtype "MySQL"
         :dbname "books"
         :user "russ"
         :password "noneofyourbeeswax"})
```

Along with the connection information, `clojure.java.jdbc` also returns query results in maps. So, if you had a simple-minded table called `books` in your database, you would see something like this come back from the query function:

```clojure
({:id 10, :title "Oliver Twist", :author "Dickens"}
 {:id 20, :title "Emma", :author "Austen"})
```

In this way `clojure.java.jdbc` is a typical bit of Clojure software: maps go in and maps come out.

Sets are not nearly as common as maps in real-world Clojure code, but neither are they rare. For example, `clojure.java.jdbc` also contains this expression:

```clojure
(#{"derby" "h2" "hsqldb" "sqlite"} subprotocol)
```

It may look odd, but it’s not complicated: this is a set—in this case a set literal—used as a function, which will return the value only if `subprotocol` is one of the elements in the set. Otherwise it will return `nil`. In essence this is a test to see if the value bound to `subprotocol` is the name of a database that we recognize.


But the clear popularity winner is the keyword: it’s hard to write any significant Clojure code without sprinkling in some keywords. For example, if you browse through the source of the Clojure build tool and Leiningen competitor [boot](https://github.com/boot-clj/boot), you will come across this:

```clojure
(defn resolve-dependencies
  [{:keys [checkouts] :as env}]
  (let [checkouts (set (map first checkouts))]
    (->> [:dependencies :repositories :local-repo :offline? :mirrors :proxy]
         (select-keys env)
         resolve-dependencies-memoized*
         ksort/topo-sort
         (keep
           (fn [[p :as x]] (when-not (checkouts p)
           {:dep x :jar (dep->path x)}))))))
```

For those who are counting, that’s 11 keywords in 10 lines of code. Keywords are everywhere in Clojure code.


