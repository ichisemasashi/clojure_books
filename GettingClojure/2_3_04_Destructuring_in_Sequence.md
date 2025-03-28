
### シーケンスのデストラクチャリング

これまでのところ、ベクターのデストラクチャリングに限定してきましたが、これまで見てきたテクニックはすべて、Clojureのシーケンシャルなデータ型のどれでも動作します。 したがって、名前をベクターからリストに切り替えると、次のようになります：

```clojure
(def artist-list '(:monet :austen :beethoven :dickinson))
```

デストラクチャリングはまったく変わらない：

```clojure
(let [[painter novelist composer] artist-list]
  (println "The painter is" painter)
  (println "The novelist is" novelist)
  (println "The composer is" composer))
```

特に、リストをデストラクチャする場合でも、テンプレートを角括弧で囲み続けることに注意してほしい。束縛式の左辺は `[painter novelist composer]` のままです。この文脈では、角括弧はシーケンシャルなデータ型の区切り記号の代わりをしていると考えることができます。

つまり、あらゆるシーケンシャルなデータ型ということだ。リストや ベクター以外にも、シーケンスに変換できるClojureの値であれば、何でもデストラクチャ化できます。例えば文字列は、個々の文字にデストラクチャされます：

```clojure
(let [[c1 c2 c3 c4] "Jane"]
  (println "How do you spell Jane?")
  (println c1)
  (println c2)
  (println c3)
  (println c4))
```

前のコードを実行するとこうなる：

```
How do you spell Jane?
J
a
n
e
```

もし、それをシーケンスに変えることができるのであれば、それを再構築することができる、というのがルールだ。

