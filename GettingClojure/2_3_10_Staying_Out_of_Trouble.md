
### トラブルに巻き込まれないために

ほとんどのプログラミング・ツールがそうであるように、デストラクチャリングは、健全な量の常識と混在しているときに最もうまく機能する。デストラクチャリングの真価は、データ構造への深い潜入をコーディングしやすくし、読みやすくしてくれることだ。デストラクチャリングの「ここに価値を見いだす」というアプローチには、人間の脳を刺激する何かがある。複雑なデータ構造を1つのデストラクチャリング式で深く掘り下げるのは、コードを混乱させる確実な方法の1つだ。

例えば、読者情報のベクタがあったとする：

```clojure
[{:name "Charlie", :fav-book {:title "Carrie", :author ["Stephen" "King"]}}
 {:name "Jennifer", :fav-book {:title "Emma", :author ["Jane" "Austen"]}}]
```

2番目の読者の好きな本の著者のフルネームが知りたかったら、3回早口でこう言うかもしれない、と：

```clojure
(defn format-a-name [[_ {{[fname lname] :author} :fav-book}]]
  (str fname " " lname))
``` 

ほとんどのプログラマーは、この問題を理解するために数分間この作品を見つめなければならないだろう。より良いアプローチは、この問題をいくつかの段階に分けて、次のように進めていくことだろう：

```clojure
(defn format-a-name [[_ second-reader]]
  (let [author (-> second-reader :fav-book :author)]
    (str (first author) " " (second author))))
```
   
意図を明らかにする名前ほど、自分の意図を明らかにするものはない。
   
   
デストラクチャリングについて覚えておくべきもうひとつのことは、デストラクチャリングは純粋にローカルな束縛のためのものだということだ。関数のパラメーターや`let`でデストラクチャリングを使うことはできますが、def で直接デストラクチャリングを使うことはできません：

```clojure
;; No!!
(def author {:name "Jane Austen" :born 1775})
(def author-name [{n :name} author])
```

はコンパイルできない。しかし、絶望することはない。これならできる：

```clojure
(def author-name
  (let [{n :name} author] n))
```

ちょっとしたことが大きな力になる。


