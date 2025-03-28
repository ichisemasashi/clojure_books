
### 野生の中で

マップはClojureプログラミングのスイスアーミーナイフです: 関連するデータアイテムを束ねて統一された全体にする必要があるときはいつでも、最初に考えることの1つは、"おい、マップを使おう "であるべきです。この考え方は `clojure.java.jdbc` ライブラリで見ることができます。`clojure.java.jdbc` が提供する関数は、データベースを見つけるためにいくつかの設定情報を必要とします：

```clojure
(require 'clojure.java.jdbc)
(def db {:dbtype "derby" :dbname "books"})
(clojure.java.jdbc/query db ["select * from books"])
```

この例では、`db` にバインドされたマップにデータベース接続情報が含まれています。`require`式は気にしないでください。`clojure.java.jdbc`ライブラリがロードされることを保証するだけです。これについてはNamespacesで詳しく説明します。今は、この種の設定データにマップを使用することで、状況に応じて自由に内容を変更できることに注目してください。 したがって、別の種類のデータベースに接続したい場合は、別のマップを作成すればいいのです：

```clojure
(def db {:dbtype "MySQL"
         :dbname "books"
         :user "russ"
         :password "noneofyourbeeswax"})
```

接続情報だけでなく、`clojure.java.jdbc`はクエリの結果もマップとして返します。つまり、データベースに`books`という単純なテーブルがあったとすると、クエリ関数からこのようなものが返ってくることになります：

```clojure
({:id 10, :title "Oliver Twist", :author "Dickens"}
 {:id 20, :title "Emma", :author "Austen"})
```

このように`clojure.java.jdbc`は典型的なClojureソフトウェアである。

セットは実際のClojureコードではマップほど一般的ではありませんが、珍しいものでもありません。例えば、`clojure.java.jdbc`にもこの式があります：

```clojure
(#{"derby" "h2" "hsqldb" "sqlite"} subprotocol)
```

奇妙に見えるかもしれないが、複雑ではない。これはセット（この場合はセットリテラル）を関数として使用したもので、`subprotocol`がセットの要素の1つである場合にのみ値を返す。そうでなければ `nil` を返す。要するに、これは `subprotocol` に束縛された値が、我々が認識しているデータベースの名前であるかどうかをテストするものである。


しかし、明確な人気の勝者はキーワードです: いくつかのキーワードを散りばめずに重要なClojureコードを書くのは難しいです。例えば、ClojureのビルドツールのLeiningenの競合[boot](https://github.com/boot-clj/boot)のソースをブラウズすると、このようなものに出くわします：

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

数えている人のために言っておくと、10行のコードに11個のキーワードが入っていることになる。Clojureのコードにはキーワードが至る所にある。


