
### ソリューションを構成する

シーケンスと、それを処理するためにClojureが提供する多数の関数から、膨大な量のプログラミング・マイレージを得ることができます。例えば、あなたが書籍ビジネスの販売アプリケーションに取り組んでいて、先に見た書籍ベクターから始めて、次のような3つのトップ評価の書籍を宣伝する文字列を整形する必要があったとします：

```clojure
"Emma // Dracula // Deep Six"
```

ステップ1では、評価順に上位3冊を見つける。まず、評価順に並べ替えることから始めよう：

```clojure
(sort-by :rating books)
```

そうすれば、評価の低いものから高いものへと本を並べることができるだろうが、他の方法の方が便利だ：

```clojure
(reverse (sort-by :rating books))
```
    
次に、別のシーケンスの最初のN個のアイテムからなるシーケンスを生成する関数である`take`を使って、最も評価の高い3冊の本を取り出すことができる。
この場合は最初の3項目である：

```clojure
(take 3 (reverse (sort-by :rating books)))
```

でも、本のマップはすべて必要なわけではなく、タイトルだけでいいんです。マップのための仕事のようだ：


```clojure
(map :title (take 3 (reverse (sort-by :rating books))))
```

これで、`("Emma" "1984" "Jaws")` のようなシーケンスができた。ここからは文字列を組み立てるだけだ。スラッシュは `interpose` を使ってシーケンスに挿入することができる：

```clojure
(interpose
  " // "
  (map :title (take 3 (reverse (sort-by :rating books)))))
```

これで5弦のシークエンスができた：

```clojure
("Emma" " // " "1984" " // " "Jaws")
```

そして、全体を1つの文字列にまとめ、便利な関数でコードをラップすればいい：

```clojure
(defn format-top-titles [books]
  (apply
    str
    (interpose
      " // "
      (map :title (take 3 (reverse (sort-by :rating books)))))))
```

数個のシーケンス関数で多くの計算を行うことができる。


