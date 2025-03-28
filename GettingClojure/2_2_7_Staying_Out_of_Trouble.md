
### トラブルに巻き込まれないために

遅延シーケンス、特に無限シーケンスは強力なプログラミング・ツールですが、REPLでそれを扱うには問題があります。このように明示的に無限シーケンス全体を表示しようとするのは確かに避けたい：

```clojure
user=> (def numbers (iterate inc 1)) 
#'user/numbers
user=> (println numbers) ; Say goodnight!
```

あるいは暗黙のうちに：

```clojure
user=> numbers ; And that is it!
```

決して無限の顔を見つめてはいけない。第8章 Def, Symbols, and Vars（85ページ）」で見た`*print-length*`ダイナミック変数と`set！`の両方が役に立つ：

```clojure
user=> (set! *print-length* 10)
10 
user=> numbers
(1 2 3 4 5 6 7 8 9 10 ...)
```

さらに微妙なことだが、遅延シーケンスを扱うときには副作用にも注意する必要がある。例えば、本の各章のテキストが`chap1.txt`や`chap10.txt`のような名前のファイルに保存されているとします。Clojureの組み込み関数 `slurp` (そう、これが本当の名前です) を使って、章ファイルの内容を文字列に読み込むことができます：

```clojure 
;; Get the contents of the file as a string.
(slurp "chap1.txt")
```

`map`と`take`の巧みさを加えれば、最初の10章のテキストのシーケンスを作ることができる：

```clojure
(def chapters (take 10 (map slurp (map #(str "chap" % ".txt") numbers))))
```

ただし、ファイルを読むためのパイプラインをセットアップしただけで、実際にはまだ何も読んでいない。`take`と`map`は遅延シーケンスを生成するので、ファイルの読み込みが行われる前に`chapters`の要素で実際に何かを行う必要がある。Clojureのコレクションはイミュータブルですが、ファイルの内容はイミュータブルではありません。

> [!NOTE]
>
>  **SlurpとSpit**
>
> `slurp`関数は、Clojureプログラマの普遍的な "何かを読む必要がある “ときの友達です。特に、 `slurp` は URL を渡すと、あなたが望むことを正確に実行します： (slurp "http://russolsen.com/index.html")`.
> 読み込みよりも書き込みに興味があるのであれば、`spit`がある： `(spit "/tmp/chapter1.txt" "It was a dark...")`.
> これらの関数はあなたの友達です。名前は......まあ、その場にいなければならなかったのだろう。


Clojureが`doall`関数を提供しているのは、まさにこのような場面のためである：


```clojure
;; Read the chapters NOW!
(doall chapters)
```

`doall` 関数は遅延シーケンスを処理して各要素にアクセスし、シーケンスを返す。"今すぐに"実行したい場合には、 `doall` が便利である。

同じようなものとして `doseq` があり、これは遅延シーケンスの各要素を一度に取り出すが、全体を保持しようとはしない。構文的には `doseq` は `for` に似ている：

```clojure
;; Read the chapters NOW!
(doseq [c chapters]
  (println "The chapter text is" c))
```

違いは、`for`が遅延シーケンスを返すのに対して（シーケンスを遅延から解放しようとするときにはあまり役に立たない）、`doseq`は強調して熱心であることだ。



