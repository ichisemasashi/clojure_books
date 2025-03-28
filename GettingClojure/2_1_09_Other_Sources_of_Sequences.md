
### その他のシーケンス源

シーケンスを活用することができることを考えると、マップ、ベクター、リスト以外にも多くのものをシーケンスに変えることができるのは驚くことではありません。 例えば、`line-seq`関数はテキストファイルの内容をシーケンスに変換します。例えば、`authors.txt`というファイルにすべての著者の名前があるとすると、著者がそのリストに載っているかどうかを判断する関数を書くことができる：

```clojure
(require '[clojure.java.io :as io])

(defn listed-author? [author]
  (with-open [r (io/reader "authors.txt")]
    (some (partial = author) (line-seq r))))
```

`requireと`with-open`（ファイルを開いたり閉じたりする）式はひとまず置いておいて、最後の行に注目してみよう。最後の行で`line-seq`を使って`authors.txt`ファイルの内容を一連の文字列に変えて、1行に1つの文字列にしてから、`some`を使って著者名の検索を行っている。

あるいは、ファイルの代わりに文字列があり、特定の正規表現にマッチする文字列をすべて抜き出したいかもしれない。Clojureの組み込みの正規表現リテラルは、文字列の前に`#`を付けて記述します。正規表現でできる最も簡単なことは、ある文字列にマッチするかどうかを尋ねることです：

```clojure
;; A regular expression that matches Pride and Prejudice followed by anything.
(def re #"Pride and Prejudice.*")
;; A string that may or may not match.
(def title "Pride and Prejudice and Zombies")
;; And we have a classic!
(if (re-matches re title)
  (println "We have a classic!"))
```


しかし、`re-seq`を使って、与えられた正規表現にマッチする文字列のシーケンスを生成することもできる。つまり、一つの単語にマッチする正規表現 `#" \w+"` があれば、次のようにすることができる：

```clojure
(re-seq #"\w+" title)
```

あなたは`("Pride" "and" "Prejudice" "and" "Zombies")`というシーケンスに行き着く。


