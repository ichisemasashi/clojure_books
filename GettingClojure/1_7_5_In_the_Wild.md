      
### 野生の中で
      
実際のClojure関数はlet式でいっぱいです。実際、 `let` は `defn` や `def` と並んで最もよく使われる Clojure の機能の1つです。例えば、Ringのソースコードを見ると、 `parse-params` 関数があります。以下はその少し簡略化したものです：

```clojure
(defn parse-params [params encoding] 
  (let [params (codec/form-decode params encoding)]
    (if (map? params) params {})))
```

Ringの各パーツについて詳しく説明するまでもないが、 `parse-params` は生のパラメータデータをデコードして、マップかそれ以外の値（おそらく `nil`）に変換する。デコードの結果を `let` の `params-courtesy` に渡すと、デコードされた値が本当にマップであればその値を返し、マップでなければ空のマップを返す。

Ringをもう少し調べてみると、`parse-params`を使用する `assoc-query-params` 関数を発見できるだろう。その関数を少し簡略化したものがこちらで、`if-let`の中にバニラletが埋め込まれています：

```clojure
(defn assoc-query-params
  "Parse and assoc parameters from the query string
  with the request."
  [request encoding]
  (merge-with merge request
    (if-let [query-string (:query-string request)]
      (let [params (parse-params query-string encoding)]
        {:query-params params, :params params})
      {:query-params {}, :params {}})))
```

リクエストからクエリー文字列を取り出し、それを `query-string` と呼び、実際に何かを取得したら、パラメーターをパースして `params` で結果を呼び出します。

本当に見事な`let`の例としては、[Incanter](http://incanter.org)にあるこれ以外にはないだろう。


```clojure
(let [opts (if options (apply assoc {} options) {})
      data (or (:data opts) $data)
      _x (data-as-list x data)
      nbins (or (:nbins opts) 10)
      theme (or (:theme opts) :default)
      density? (true? (:density opts))
      title (or (:title opts) "")
      x-lab (or (:x-label opts) (str 'x))
      y-lab (or (:y-label opts)
                 (if density? "Density" "Frequency"))
      series-lab (or (:series-label opts) (str 'x))
      legend? (true? (:legend opts))
      dataset (HistogramDataset.)]
  ;; Do something heroic with x-lab and density?
  ;; and title and...
)
```

ヒストグラムの描画に使用される先のコードでは、12個以上の名前がバインドされている。詳細はここでは省くが、冒頭で定義された`opts`が後の`let`で9回使われていることがわかる。`y-lab`の値は`or`に埋め込まれた`if`で計算されている。式ベースの言語の威力を見よ。

もう一つは、この `let` の角括弧の中でどれだけの計算が行われているかということだ。`if`や`or`、その他多くの関数呼び出しがこの中で行われているのだ。ここでの教訓は、もし複雑なステップを踏んで計算する値があるのなら、`let`の角括弧の中で計算することを考え、それぞれの中間結果に有益な名前をつけることだ。未来の自分はあなたに感謝するだろう。
