
### 関数は値である

関数型プログラミングの冒険を始めるにあたって、これまで使ってきた本の管理用マップに価格とジャンルを追加することにしたと仮定しよう：


functional/examples.clj
```clojure
(def dracula {:title "Dracula"
              :author "Stoker"
              :price 1.99
              :genre :horror}) 
```

さらに、任意の価格に基づいて書籍を区別するコードを書く必要があるとしよう：
             
```clojure
(defn cheap? [book]
  (when (<= (:price book) 9.99)    
    book))
(defn pricey? [book]
  (when (> (:price book) 9.99)
    book))

(cheap? dracula)  ; Yes!
(pricey? dracula) ; No!
```

あるいはジャンルについて： 


```clojure
(defn horror? [book] 
  (when (= (:genre book) :horror)
    book))
(defn adventure? [book]
  (when (= (:genre book) :adventure)
    book))

(horror? dracula)    ; Yes!
(adventure? dracula) ; Nope!
```

これらの関数で中途半端に面白いのは、Clojureの真理値ロジックを利用し、本がテストに失敗すると`nil`を返し、合格すると真理値であるブックマップ自体を返すことです。

価格とジャンルに関する組み合わせにも興味があるかもしれない：


```clojure
(defn cheap-horror? [book]
  (when (and (cheap? book)
             (horror? book))
    book))
(defn pricy-adventure? [book]
  (when (and (pricey? book)
             (adventure? book))
    book))
```

このような機能は一日中書くことができる。どこかの作家の安い本や、『所有権』と題された高価な本はどうだろう？

> [!NOTE]
>
> **Possession**
>
> Possessionと呼ばれる小説が驚くほどたくさんあることがわかった。



ここでのキーワードは残念ながら「書く」である。実際のシステムを構築する場合、この種の組み合わせを手で書くことに時間を費やしたくはないだろう。あなたが望むのは、基本的なオペレーションをコード化し、それから動的に組み合わせを作成することだ。幸いなことに、手作業によるコーディングから抜け出すために必要なことは、Clojureでは関数が数値や文字列、ブール値やベクターと共通するものを持っていることを理解することです。これらのありふれたもののように、"関数は値である"。

つまり、`defn`で定義した関数の名前を評価すると、おそらく次のようになる：

```
cheap?
```

これを実行すると、このように表示される：

```
#object[user$cheap_QMARK_ 0x71454b9d "user$cheap_QMARK_@71454b9d"]
```


`#object[user$cheap_QMARK_..."]`は、Clojureが安い本と高い本を区別する関数を実行しようとしたときに出力される意味不明な文字列です。その関数の値を別のシンボルにバインドすることもできます：

```clojure
(def reasonably-priced? cheap?)
```

すると、`reasonably-priced?`は今や私たちの倹約関数の別名となる：

```clojure
(reasonably-priced? dracula) ; Yes!
```

関数の値を他の関数に渡すこともできる。くだらない例を挙げると、次のようになる：


```clojure
(defn run-with-dracula [f]
  (f dracula))
```

`run-with-dracula`はその名の通り、`dracula`の値を引数として関数を評価する。どんな関数か？`run-with-dracula` に渡す関数である：

```clojure
(run-with-dracula pricey?) ; Nope.
(run-with-dracula horror?) ; Yes!
```

より現実的には、この関数を値とみなす考え方は、述語を組み合わせる簡単な方法を与えてくれる：


```clojure
(defn both? [first-predicate-f second-predicate-f book]
  (when (and (first-predicate-f book)
             (second-predicate-f book))
    book))

(both? cheap? horror? dracula)     ; Yup!
(both? pricey? adventure? dracula) ; Nope!
```

より汎用的な`both?`関数と、非常に特殊な`cheap-horror?`関数との唯一の違いは、`both?`は述語関数のペアを渡すことができるということである、ということは、この関数を使えば、どんな2つの述語でもブックを実行することができるということだ。


