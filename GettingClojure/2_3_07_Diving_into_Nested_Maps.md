  
### ネストしたマップへの掘り下げ

リストやベクターのようなシーケンシャルなデータ構造を何層にも掘り下げるためにデストラクチャリングを使うのと同じように、マップも何層にも掘り下げることができます。例えば、ジェーン・オースティンを2階層で説明するとしよう：

```clojure
(def austen {:name "Jane Austen"
             :parents {:father "George" :mother "Cassandra"}
             :dates {:born 1775 :died 1817}})
```

これで彼女の両親の名前を取り出すことができる： 

```clojure
(let [{{dad :father mom :mother} :parents} austen]
  (println "Jane Austen's dad's name was" dad)
  (println "Jane Austen's mom's name was" mom))
```
  
このような2レベルのマップのデストラクチャリングを見る良い方法は、外側から内側を見ることだ。一番外側には、基本的なlet構造がある：

```clojure
(let [<<something-to-bind-to>> austen]
  ;; Do something with the data...
  )
```

次のレベルに進むと、マップのデストラクチャリングがあり、`:parents`キーを取得しようとしていることがわかる：

```clojure
(let [{<<something-to-bind-parents-to>> :parents} austen]
  ;; Do something with the data... 
  )
```

そして、`something-to-bind-parents-to`のところはどうするのか？もちろん、ここでまた新たなデストラクションをしなければならないのだが、それはまた別の話である：

```clojure
(let [{{dad :father mom :mother} :parents} austen]
  ;; Do something with the data...
  )
```

いったんアイデアが浮かんだら、あとはマップから好きな分だけ抜き出すことができる。たとえば、ジェーンの名前と母親の名前、それにジェーンの生まれた年を取り出すことができる：

```clojure
(let [{name :name
      {mom :mother} :parents
      {dob :born} :dates} austen]
  (println name "was born in" dob)
  (println name "mother's name was" mom))
```

ただ、左側のデストラクチャーの順番は、マップを作成する場合とは逆になることを覚えておいてほしい。デストラクチャリングに関しては、値→キーの順でずっと下がっていく。



