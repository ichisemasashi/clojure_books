
### 関数引数のデストラクチャリング

デストラクチャリングは、すべてのシーケンス型（そして、後で説明するように、マップ）で機能するだけでなく、`let`に限定されるものでもありません。特に、関数に渡される引数にデストラクチャリングを使用することができます。関数の引数でデストラクチャリングを使うのは、 `let` でデストラクチャリングを使うのとほぼ同じです。例えば、2つの要素を持つベクターを探す関数です：

```clojure
(defn artist-description [[novelist poet]]
  (str "The novelist is " novelist " and the poet is " poet))
```

つまり、この関数を呼び出すときに、分解される値を指定するのである：

```clojure
(artist-description [:austen :dickinson])
```

これは `"The novelist is :austen and the poet is :dickinson"` を返します。

通常の引数とデストラクチャする引数を混ぜて使うこともできる。例えば、これは通常の引数とデストラクチャする引数を持つ関数です：

```clojure
(defn artist-description [shout [novelist poet]]
  (let [msg (str "Novelist is " novelist
                 "and the poet is " poet)]
    (if shout (.toUpperCase msg) msg)))
```

この`artist-description`の最新バージョンは、デストラクチャしないパラメータのshoutがtruthyの場合、大文字のメッセージを返す。


