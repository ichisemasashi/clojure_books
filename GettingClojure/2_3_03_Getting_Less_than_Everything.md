
### すべてより少ないものを得る

便利なことに、名前とデータを一対一に対応させる必要はない。例えば、4項目のベクターの最初の3要素にしか興味がない場合、次のように記述することができる：

```clojure
(let [[painter novelist composer] artists]
  (println "The painter is" painter)
  (println "The novelist is" novelist)
  (println "The composer is" composer))
```

この場合、`:dickinson`は無視される。

しかし、ベクターの先頭の項目を無視したい場合はどうすればいいのだろう？問題ない。仮の名前を入れればいいのだ：

```clojure
(let [[dummy dummy composer poet] artists]
  (println "The composer is" composer)
  (println "The poet is" poet))
```

`dummy`というシンボルには特別な意味はなく、`:monet`と`:austen`の値を吸収するために使っているだけだ。また、`let`の中で同じ名前を2回使っても、何も驚くようなことはない。名前は単に最後の値にバインドされるだけで、どちらの値も気にしないので問題ありません。実際、Clojureプログラマは、このような "私は本当にこれについて気にしない " 名前の規約を持っています。アンダースコア1つからなる記号を使います。したがって、最後の例のより良い表現は次のようになります：

```clojure
(let [[_ _ composer poet] artists]
  (println "The composer is" composer)
  (println "The poet is" poet))
```

また、1レベルのデストラクチャリングに制限されることもない。もしこの2レベルのベクターから始めるとしたら：

```clojure
(def pairs [[:monet :austen] [:beethoven :dickinson]])
```

これを使えば、各ペアの最初のメンバーも手に入れることができる：

```clojure
(let [[[painter] [composer]] pairs]
  (println "The painter is" painter)
  (println "The composer is" composer))
```

一番左のテンプレートベクターの2レベル構造は、`pairs` ベクターの2レベル構造を反映していることに注意してください。先のコードを実行すると、このようになる：

```
The painter is :monet
The composer is :beethoven
```

あるいは、物事を混ぜて、最初のペアの最初のアイテムと2番目のペアの2番目のアイテムを取り出すこともできる、つまり、`:monet`と`:dickinson`を探しているなら、こう言うことができる：

```clojure
(let [[[painter] [_ poet]] pairs]
  (println "The painter is" painter)
  (println "The poet is" poet))
```


デストラクチャリングの背後にある考え方は、APIコールごとにデータ構造を苦労してナビゲートする代わりに、データ構造の大雑把なスケッチを提供することだ。スケッチには、探しているデータを示すプログラム的な矢印が含まれている。


