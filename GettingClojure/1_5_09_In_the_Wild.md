
### 野生の中で

私たちの古くからの友人である組み込みの `=` 関数は、この章で見てきた関数関連のグッズの素晴らしい例です：

```clojure
;; Code edited a bit for clarity.
(defn =
  "Equality. Returns true if x equals y, false if not. Same as
  Java x.equals(y) except it also works for nil, and compares
  numbers and collections in a type-independent manner.
  Clojure's immutable data structures define equals()
  (and thus =) as a value, not an identity, comparison."
  ([x] true)
  ([x y] (clojure.lang.Util/equiv x y))
  ([x y & more]
    (if (clojure.lang.Util/equiv x y)
      (if (next more)
        (recur y (first more) (next more))
        (clojure.lang.Util/equiv y (first more)))
      false)))
```

関数 `=` は任意の数の引数を取り、それらがすべて等しい場合に `true` を返します。幸いなことに、 `=` はほとんどの処理を低レベルの `clojure.lang.Util/equiv` 関数に任せることができます。ただし、`equiv`は一度に2つの値しか比較できません。

コードがどのように仕事を3つのアリティに分割しているかに注目してください。単一引数の配列は簡単で、`(= anything)`を評価すれば常に`true`が返ってくる。2引数のアリティも非常に簡単で、`clojure.lang.Util/equiv`を呼び出すだけです。

3番目のアリティ、つまり2つ以上の引数を扱うアリティが面白くなるところです。この場合、基本的なフローは、最初の2つまたは3つの引数を `clojure.lang.Util/equiv`で比較し、引数が残っている場合は `recur` に頼ることです。


> [!NOTE]
>
> **シンプルな同等性？**
>
> この2つまたは3つの引数による `=` のダンスは、パフォーマンスを向上させるためにあるように見える。最初の2つの引数を単純に比較し、2つ以上ある場合は再帰に頼る、完全に合理的なバージョンの `=` を作ることもできる。


ClojureScriptのソースコードには、`to-url`のマルチメソッドの素敵な例が含まれています。以下は `to-url` のやや簡略化したバージョンである：

```clojure
(defmulti to-url class)
(defmethod to-url File [f] (.toURL (.toURI f)))
(defmethod to-url URL [url] url)
(defmethod to-url String [s] (to-url (io/file s)))
```

`normalize-book`の例と同様に、`to-url`は様々なURLのようなものを単一のデータ構造に変換することで、混沌とした世界に秩序をもたらそうとしています。bookの例とは異なり、 `to-url` は組み込みの `class` 関数を使用し、ディスパッチ関数として値の基本型を返します。本質的に、ここにあるのは、多くのオブジェクト指向プログラミング言語で見られるクラスベースのポリモーフィズムの大まかな近似であり、ほんの一握りのコード行で実装されています。


