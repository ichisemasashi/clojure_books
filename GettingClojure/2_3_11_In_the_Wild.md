
### In the Wild

Examples of destructuring are easy to find if you look around in the Clojure ecosystem. For example, [Korma](https://github.com/korma/Korma/blob/master/src/korma/db.clj), the database library, contains this function, which helps set up connections to MySQL databases:

```clojure
(defn mysql
  "Create a database specification for a
   mysql database. Opts should include
   keys for :db, :user, and :password.
   You can also optionally set host and port.
   Delimiters are automatically set to \"`\"."
    [{:keys [host port db make-pool?]
      :or {host "localhost", port 3306, db "", make-pool? true}
      :as opts}]
      ;; Do something with host, port, db, make-pool? and opts
  )
```

Again we have the typical "function with a docstring" setup, but this function is using some interesting destructuring to dig into the map that it’s expecting as an argument. Things begin innocently enough. At the front we have some `:keys`-based destructuring to get at values such as the host and port. And at the end of the destructuring we have a familiar `:as`, which gives the function access to the entire map that is passed in.

But in between the `:keys` and the `:as`, we have something we haven’t seen before: `:or`. The `:or` feature helps you deal with the situation where you look for some value in your destructuring and it’s not there. Take another look at the preceding code and ask yourself, what if the caller doesn’t pass in a value for `:host?` Or `:port?` The answer is that `host` and `port` would end up set to `nil`. Or they would without the `:or`. The `:or` lets you specify default values in the form of a map. So if you leave out `:host` in the map that you pass to the `mysql` function, you will get the default value of `"localhost"`. Similarly, leave out the port and you will get 3306.



