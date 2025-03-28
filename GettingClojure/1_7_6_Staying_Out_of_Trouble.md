
### トラブルに巻き込まれないために

単純に見えるかもしれませんが、`let`についてはいくつか注意すべき点があります。まず一つ目は、`let`の中で定義された名前は、まさに`let`の中で定義された名前であるということです。 技術的には、`let`は「レキシカルスコープ」に依存しています。わかりやすく言うと、letで作成されたバインディングは `let` 本体を構成するコードの中にしか存在しないということです。

例えば、こんなことはできません：

```clojure
;; We can use title inside of the let.
(let [title "Let's Pretend This Never Happened"]
  (println "The title is" title)
  (print-the-title))
;; But now we're outside of the let.
(defn print-the-title []
  (println "The title is" title)) ; Boom!
```

`title`は`let`の中でしか定義されないので、これは驚くべきことではない。 結局のところ、私たちはローカルバインディングを求めることからこの旅を始めたので、ローカルバインディングは私たちが得たものなのだ。

次に、もし `let` 式を入れ子にした場合、 `let` 内のバインディングが外側の `let` 内のバインディングを隠してしまう可能性があることを覚えておいてください。例えば、次のようなコードだ：

```clojure 
(let [title "Pride and Prejudice"]
  (let [title "Sense and Sensibility"] 
    (println title))) ; Sense & Sensibility.
``` 

結果は `Sense and Sensibility` と表示されます。また、"同じ" `let` 内の束縛を上書きすることもできます： 


```clojure
(let [title "Pride and Prejudice" ; Classic novel.
      title (str title " and Zombies")] ; Now with the undead.
  (println title)) ; Brains!
```

これはロマンチック・ホラーのマッシュアップを残すだろう。




