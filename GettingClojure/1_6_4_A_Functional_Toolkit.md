
### 関数型ツールキット

Clojureプログラミングの多くは、関数の作成、組み合わせ、および使用を中心に展開されるので、この言語がその作業を容易にすることを目的とした関数をかなり多く提供しているのは当然です。

例えば、`apply`関数。この関数は、関数とその関数を呼び出したい引数が「コレクションの中にある」という、驚くほど一般的な状況に対処します。つまり、このようにする代わりに 

```clojure
(+ 1 2 3 4) ; Gives you 10.
```

関数（この場合は`+`）と引数があったとしたら、次のようになる：

```clojure
(def the-function +)
(def args [1 2 3 4])
```

`apply`の登場だ。関数と引数のコレクションを与えると、`apply`が引数を使ってその関数を呼び出し、結果を返す。このように `apply` を使えば、次のような処理を行うことができる：
 
```clojure
(apply the-function args) ; (the-function args0 args1 args2 ...)
```

`apply`関数は、ある種類の値から別の種類の値への変換に特に便利です。したがって、次のようなベクターがあるとします：

```clojure
(def v ["The number " 2 " best selling " "book."])
```


`apply` と `str` の組み合わせを使って文字列にすることができる：

```clojure
;; More or less the same as:
;; (str "The number " 2 " best selling " "book.")
(apply str v)
```

または、`apply`と`list`でリストにする：

```clojure
;; More or less the same as:
;; (list "The number " 2 " best selling " "book.")
(apply list v)
```

そして、ベクターに戻す：


```clojure
(apply vector (apply list v))
```

もう一つの非常に便利な関数が `partial` である。これは、既存の関数の引数を部分的に埋めて、その過程でより少ない引数の新しい関数を生成するので、`partial`と呼ばれている。例えば、Clojureには `inc` という関数があり、渡された数値に1を足すことで、 `(inc 1)` は `2` になり、 `(inc 41)` は `42` になる。独自の `inc` を作るのは簡単だ：

```clojure
(defn my-inc [n] (+ 1 n))
```

しかし、`my-inc`は単に`+`の第一引数を`1`で補っているだけだと考えてほしい。 これはまさに `partial` が行っていることだ：

```clojure
(def my-inc (partial + 1))
```

本の例に戻ると、`partial`を使って、安さを判別する関数を作り直し、単純化することができる：

```clojure
(defn cheaper-than [max-price book]
  (when (<= (:price book) max-price)
    book))

(def real-cheap? (partial cheaper-than 1.00))
(def kind-of-cheap? (partial cheaper-than 1.99))
(def marginally-cheap? (partial cheaper-than 5.99))
```

`partial`を呼び出すたびに新しい関数が返され、その関数が呼び出されると、価格のいずれかを第一引数として`cheaper-than`が呼び出される。


Clojureに同梱されているもう一つの便利な関数生成関数は `complement` である。`complement`を使えば、毎日が反対の日になる。`complement`は`not`を呼び出すことで関数をラップし、元の関数を補完する新しい関数を生成する。例えば、先ほど私たちは`adventure?`と書いた：

```clojure
(defn adventure? [book]
  (when (= (:genre book) :adventure)
    book))
```

しかし、冒険以外の本を探す関数が必要だとしたらどうだろう？ 手で書けばいいのは明らかだ：

```clojure
(defn not-adventure? [book] (not (adventure? book)))
```


しかし、我々はハンドコーディングのビジネスから脱却しようとしていると言った：

```clojure
(def not-adventure? (complement adventure?))
```

言っておくが、`complement`は、`complement`に渡した関数の真偽の否定を返す関数を生成する。

関数生成関数のもう一つの例は `every-pred` である。これは述語関数を1つの関数にまとめ、それらをまとめて「アンド」するものである。every-pred`を使えば、自作の `both-f` を使う必要がなくなる：

```clojure
(def cheap-horror? (every-pred cheap? horror?))
```


さらに良いことに、`every-pred`は任意の数の引数を取るので、こうなる：

```clojure
(def cheap-horror-possession?
  (every-pred
    cheap?
    horror?
    (fn [book] (= (:title book) "Possession"))))
```

これは、あなたが望むとおりのことをしてくれる。



