
### レコードでより具体的な取引をする

幸いなことに、Clojureはこれらの欠点のいくつかを緩和するマップの代替手段を提供します：レコードです。レコードは定義済みのキーを持つマップと考えることができます。レコードを作るには、まずレコード型を定義する必要がある：
                                 
```clojure                       
(defrecord FictionalCharacter[name appears-in author])
```

コードからわかるように、レコード型には名前と定義済みのフィールドのリストがあります。つまり、この例で示した式を評価すると、`FictionalCharacter`というレコード型ができあがり、そのレコード型は3つのフィールドを持つことになる。舞台裏では、`defrecord`がレコード型に応じた名前の関数をいくつか作っている。この例では、 `->FictionalCharacter` と `map->FictionalCharacter` がある。
                   
> [!NOTE]
>
> **Instant Vars** 
> 
> `defrecord`はdefで始まるので、varを作る仕事をしていると考えるのが妥当だろう。実際、`defrecord`はいくつかのvarを作成する。 レコード型用に1つ、2つのファクトリー関数用にそれぞれ1つだ。同じことが `defprotocol` にも言える。


レコード作成の第2段階を開始するには、いくつかの方法があります。それは、レコード型のインスタンス（この例では、実際のフィクションのキャラクター）を作成することです。まず、`->FictionalCharacter`関数を使って最初の`FictionalCharacter`インスタンスを作成します：

```clojure
(def watson (->FictionalCharacter "John Watson" "Sign of the Four" "Doyle"))
```


この例からわかるように、`->FictionalCharacter`はレコードの各フィールドに、`defrecord`で指定された順番に値を取り、新しいレコードのインスタンスを返す、REPLではこのように表示される：

```clojure
#records.core.FictionalCharacter{:name "John Watson",
                                 :appears-in "Sherlock Holmes",
                                 :author "Doyle"}
```

別の方法として、`map->FictionalCharacter`を使うこともできる。これは、次のように引数をマップにまとめることを期待する：

```clojure
(def elizabeth (map->FictionalCharacter
                  {:name "Elizabeth Bennet"
                   :appears-in "Pride & Prejudice"
                   :author "Austen"}))
```


渡すマップのキーワード引数が、レコードのフィールド名と一致していることを確認してほしい。


