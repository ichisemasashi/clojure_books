
### 豊富なツールキット

もし私たちが話しているのが'first'と'rest'だけだったら、このシーケンスの騒動はあまり意味がないだろう。シーケンスの本当の力は、私たちの'my-count'関数のように、あらゆる種類のコレクションを受け取り、それをシーケンスに変換し、そのシーケンスで何か面白いことをする便利なライブラリ関数の山にあります。例えば、seqableコレクションを持っているが順番が間違っている場合、'sort'でソートすることができる：

```clojure
(def titles ["Jaws" "Emma" "2001" "Dracula"])
(sort titles) ; Sequence: ("2001" "Dracula" "Emma" "Jaws")
```

`count` と同様に、 `sort` は渡されたコレクションをシーケンスに変換してソートし、結果のシーケンスを返す。

> [!NOTE]
> 
> **Seqa何とか**
> 
> 少なくともClojuristであれば、"seqable "は単語です。seqable は `seq` 関数がシーケンスに変換できるものです。


`reverse`関数は、ソートの代わりに反転させることを除けば、同じように機能する：

```clojure
;; A Sequence: ("Dracula" "2001" "Emma" "Jaws")
(reverse titles)
```

また、シーケンスも（些細なことだが）seqableなので、`sort`の出力を`reverse`に送り、タイトルを逆方向にソートすることができる：

```clojure
;; A Sequence: ("Jaws" "Emma" "Dracula" "2001")
(reverse (sort titles)) ;
```

また、`partition`もあり、これは大きなシーケンスを小さなシーケンスのシーケンスに切り刻むもので、フラットなベクターを取ることができる：

```clojure
(def titles-and-authors ["Jaws" "Benchley" "2001" "Clarke"])
(partition 2 titles-and-authors)
```

それをより構造化されたものに変えていく：

```clojure
(("Jaws" "Benchley") ("2001" "Clarke"))
```


また、2つのシーケンスを1つにまとめる`interleave`もある：

```clojure
;; A vector of titles and a list of authors.
(def titles ["Jaws" "2001"])
(def authors '("Benchley" "Clarke"))
;; Combine the authors and titles into a single sequence
;; ("Jaws" "Benchley" "2001" "Clarke")
(interleave titles authors)
```

`interpose`は言うまでもなく、シーケンスの要素間にセパレーターの要素を入れる：

```clojure
;; Gives us ("Lions" "and" "Tigers" "and" "Bears")
;; Oh my!
(def scary-animals ["Lions" "Tigers" "Bears"])
(interpose "and" scary-animals)
```

`sort`、`reverse`、`partition`、`interleave`、`interpose` などの関数は、すべて同じ基本的な処理構造を共有している。これらの関数は、まず `seq` でコレクションの引数をシーケンスに変換します。その後、 `first`、`rest`、`cons` のみを使用するか、マジック4人組に依存する関数を使用して処理を行う。最後に、結果をシーケンスとして返す。




