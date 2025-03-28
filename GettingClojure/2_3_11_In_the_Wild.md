
### 野生の中で

Clojureエコシステムを見回せば、構造化の例は簡単に見つかる。例えば、データベースライブラリの[Korma](https://github.com/korma/Korma/blob/master/src/korma/db.clj)にはこの関数があり、MySQLデータベースへの接続をセットアップするのに一役買っています：

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

ここでも典型的な "docstringを持つ関数 "がセットアップされているが、この関数は興味深いデストラクチャリングを使って、引数として期待されているマップを掘り下げている。最初は何の変哲もない。先頭には、ホストやポートなどの値を取得するための `:keys` ベースのデストラクチャリングがある。そして、デストラクチャの最後にはおなじみの `:as` があり、関数は渡されたマップ全体にアクセスできる。

しかし、`:keys`と`:as`の間には、これまで見たことのない `:or` というものがある。この `:or` 機能を使うと、デストラクチャリングで値を探したがなかった場合に対処することができる。もし呼び出し元が `:host` や `:port` に値を渡さなかったらどうなるだろうか？ 答えは `host` と `port` には `nil` がセットされることになる。あるいは、 `:or` がなくてもそうなる。`:or` を使うと、デフォルト値をマップ形式で指定することができる。そのため、`mysql`関数に渡すマップの `:host` を省略すると、デフォルト値の `"localhost"` になる。同様に、ポートを省略すると 3306 になる。



