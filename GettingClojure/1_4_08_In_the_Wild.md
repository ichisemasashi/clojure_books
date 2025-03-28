    
###野生の中で

ご想像の通り、`if`、`when`、`cond`、`case`の非常に見慣れた例を見つけるのは簡単だ。例えば、Leiningenから抜粋した数行である：

```clojure 
(when (real-directory? f)
  (doseq [child (.listFiles f)] 
    (delete-file-recursively child silently)))
```

詳細はわからないかもしれないが、このコードの一般的な考え方は難しくない：`f`がファイルでもシンボリックリンクでもない本当のディレクトリである場合、そのディレクトリとすべてのコンテンツを削除する。
  
同じくLeiningenからのスニペットである：

```clojure
(if (.isDirectory entry)
  (.mkdirs f)
  (do (.mkdirs (.getParentFile f))
      (io/copy (.getInputStream jar entry) f)))
```

entryがディレクトリの場合はこの処理を行い、そうでない場合は別の処理を行う。

> [!NOTE]
> 
>**ドット(.)は何？**
> 
> .isDirectory`や`.mkdirs`といったおかしなClojureらしくない関数名の理由は、このコードが低レベルのJavaライブラリを呼び出しているからです。これについては「第16章 Javaとの相互運用（189ページ）」を参照してください。



意外なことに、Clojureでは `if` の「if (または when) this, then do that」というユースケースはそれほど一般的ではありません。代わりに見られるのは、値を計算するために使われる `if`、`when`、`cond` である。Leiningenから引用したコードを見てみよう：

```clojure
(if (vector? task) task [task])
```

この式の面白いところは、何もしないことだ。 その代わりに、元の `task` やベクターでラップされた `task` などの値が評価される。したがって、結果をシンボルにバインドすることもできる：

```clojure
(def task-vector (if (vector? task) task [task]))
```

あるいは関数の中に埋め込む：


```clojure
(defn ensure-task-is-a-vector [task]
  (if (vector? task) task [task]))
```

この方法で使用されるとき（一般的に使用されるとき）、ClojureのifはJavaやC++で見られる3項式に似ている：これが真の場合、この値が欲しい、そうでない場合、この別の値が欲しい。

オープンソースライブラリ[Korma](https://github.com/korma/Korma)に`cond`の素晴らしい例があります。 Korma(キャッチフレーズ: "Tasty SQL for Clojure";ウィットに点数を与えなければなりません)は、ClojureプログラマがSQLの気まぐれに対処するのを助けようとしており、次のようなコードを含んでいます：

```clojure
(defn str-value [v]
  (cond
    (map? v) (map-val v)
    (keyword? v) (field-str v)
    (nil? v) "NULL"
    (coll? v) (coll-str v)
    :else (parameterize v)))
```

`str-value`の仕事は、Clojureの値を受け取り、SQLで受け入れられる文字列に変換することである。この関数は、どのような値を持っているかを尋ねることによって、その職務を遂行する： それはマップですか？それなら、`map-val`に渡してください。キーワードか？ それなら`field-str`に渡す、という具合だ。

Kormaでは`cond`を使用しており、`case`を使用していないことに注意してほしい。これは特定の値のテストではなく、値のクラス全体のテストだからだ。一方、Kormaの他の場所では、このようなcase表現が見られる：

```clojure
(case (:type query)
  :insert (update-in query [:values] #(map prep-fn %))
  :update (update-in query [:set-fields] prep-fn)
  query)
```

ここでは、既知の値のペアに対してテストを行っている： クエリのタイプが `:insert` であれば、これを返す。クエリのタイプが `:update` であれば、別のものを返す。そうでない場合は、クエリをそのまま返します。

