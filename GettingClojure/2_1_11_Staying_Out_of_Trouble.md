
### トラブルに巻き込まれないために

良いニュースは、Clojureにはシーケンスで面白いことをするための関数がたくさん用意されていることです。`butlast`（最後の要素以外をすべて渡す）から `zipmap`（2つのシーケンスからマップを作る）まである。悪いニュースとしては、利用可能なものに慣れるまでは、車輪を再発明することになりがちだということだ。特に、シーケンスを一度に1つずつ処理するような場合には、次のように `loop` と `recur` を使用することになるだろう：

```clojure
(defn total-sales [books] 
  "Total up book sales. Books maps must have :sales key."
  (loop [books books total 0]
    (if (empty? books)
      total
      (recur (next books) 
             (+ total (:sales (first books)))))))
``` 

もっと簡単な方法があるはずだ：

```clojure 
(defn total-sales [books] (apply + (map :sales books)))
```

もうひとつ注意しなければならないのは、特化されたコレクションをシーケンスに変えると、特化された能力をもはや持たない一般的なリストのようなものになってしまうということだ。最も顕著なのは、マップをシーケンスに変えた場合、マップをとても便利にしている「キーから値へ」という迅速なスーパーパワーを失ってしまうことだ：

```clojure
(def maze-runner {:title "The Maze Runner" :author "Dashner"})
; Gives you back "Dashner"
(:author maze-runner)
; But this give you a nil - a seq is not a map!
(:author (seq maze-runner))
```

注意しなければならないのは、明示的な`seq`呼び出しだけでなく、このように静かにseqを返すライブラリ関数もすべてだ：

```clojure
(:author (rest maze-runner)) ; Also nil: rest returns a seq.
```

嬉しいことに、シーケンスを "返さない "関数もある。 `cons`と同様に、`conj`はコレクションに新しいアイテムを追加する関数である：

```clojure
;; A *vector* ending with "Jaws".
(conj ["Emma" "1984" "The Maze Runner"] "Jaws")
;; A *list* starting with "Jaws".
(conj '("Emma" "1984" "The Maze Runner") "Jaws")
```

その違いは、`conj`は渡されたコレクションの種類に注意を払い、決定的に重要なのは、`conj`は渡されたコレクションと同じ種類のコレクションを返すということだ。そのため、`conj`は "新しい要素はリストの先頭に置くが、ベクターは後ろに置く "ということが可能なのである。conjはコレクションタイプごとに特殊なケースを持つプログラムです。対照的に、cons関数は単にコレクションに対して`seq`を呼び出し、結果のシーケンスの先頭に新しいアイテムを配置します：

```clojure
;; A *seq* starting with "Jaws".
(cons "Jaws" ["Emma" "1984" "The Maze Runner"])
;; A *seq* starting with "Jaws".
(cons "Jaws" '("Emma" "1984" "The Maze Runner"))
```

だからといって、シーケンスが悪いとか、シーケンスへの自然な流れに抗うべきだという意味ではない。それどころか、ベクターやセットやリストを、どれを持っているかを常に気にすることなく扱えるようにする普遍的な抽象化を持つことは、信じられないほど便利である。しかし、いつ実際のものを持っているのか、いつシーケンスを持っているのかに気を配る必要はある。


