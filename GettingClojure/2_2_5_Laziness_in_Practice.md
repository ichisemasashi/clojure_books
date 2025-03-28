
### 怠惰の実践

遅延シーケンスを本当に理解するために、もっと面白い例を試してみよう。テスト用に、多少なりともランダムに生成された本のマップが必要だとしよう。私たちは座って、手作業でこのようなナンセンスな本を作ることができる：

```clojure
(def test-books
  [{:author "Bob Jordan", :title "Wheel of Time, Book 1"}
   {:author "Jane Austen", :title "Wheel of Time, Book 2"}
   {:author "Chuck Dickens", :title "Wheel of Time, Book 3"}
   {:author "Leo Tolstoy", :title "Wheel of Time, Book 4"} 
   {:author "Bob Poe", :title "Wheel of Time, Book 5"}
   {:author "Jane Jordan", :title "Wheel of Time, Book 6"}
   {:author "Chuck Austen", :title "Wheel of Time, Book 7"}])
```

しかし、遅延シーケンスを使えば、いくらでもナンセンスなタイトルを作ることができる。その方法を知るために、まずタイトルを作ってみよう。ベースとなるタイトルをいくつかの数字と組み合わせることで、有限の一連のタイトルを得ることができる： 

```clojure
(def numbers [1 2 3])
(def trilogy (map #(str "Wheel of Time, Book " % ) numbers))
```

結局はこうなる：

```clojure
("Wheel of Time, Book 1"
 "Wheel of Time, Book 2"
 "Wheel of Time, Book 3")
```

しかし、私たちは扱える限りの数に簡単にアクセスできるし、`map`が怠惰であることも知っている：

```clojure
(def numbers (iterate inc 1))
(def titles (map #(str "Wheel of Time, Book " % ) numbers))
```

生成された本の著者も必要かもしれない。ここでは別の方法をとって、限られた姓と名のセットからたくさんのユニークな著者を生成してみよう。定型の姓名のささやかなベクターを作るのは簡単だ：

```clojure
(def first-names ["Bob" "Jane" "Chuck" "Leo"])
```

しかし、私たちが本当に必要としているのは（理由はすぐにわかるだろう）、何度も何度も繰り返される名前なのだ：

```clojure
(cycle first-names)
```

苗字も必要だ：

```clojure
(def last-names ["Jordan" "Austen" "Dickens" "Tolstoy" "Poe"])
```

そして、その名前を繰り返す必要がある：

```clojure
(cycle last-names)
```

また、姓と名を組み合わせてフルネームにする関数も必要だ：

```clojure
(defn combine-names [fname lname]
  (str fname " " lname))
```

これで `map` を使って名前を組み合わせれば、姓と名の組み合わせの無限リストを簡単に得ることができる：

```clojure
(def authors
  (map combine-names
    (cycle first-names)
    (cycle last-names)))
```

最後に、私たちは著者とタイトルを本のマップの怠惰なシーケンスにまとめることができます：

```clojure
(defn make-book [title author]
  {:author author :title title})
(def test-books (map make-book titles authors))
```

私たちは結局、このセクションの冒頭で手動で生成されたようなものから始まり、永遠に続き、私たちが耐えられるだけ多くの『時の輪』の続編を提供する、遅延シーケンスに束縛された`test-books`を持つことになる。

本を見るたびに、おそらく`(first test-books)`を実行することで、数字が生成され、タイトルが生成され、新しい名前と姓が生成され、フルネームが生成され、最後に本のマップが生成される。しかし、外見上は`test-books`はありふれたシーケンスのように見える。


これは遅延シーケンスについて重要なことを強調している。遅延シーケンスは究極の「使う分だけ支払う」プログラミングテクニックなのだ。`test-books`シーケンスをセットアップすることで、私たちは膨大な量のデータを生成する方法をシステムに提供した。しかし、ここでの「しかし」が重要なのだが、私たちは実際に使用するデータに対してのみCPUとメモリーの料金を支払うのである。データが生成されるのは、シーケンスから何かを取り出したときだけなのだ。


