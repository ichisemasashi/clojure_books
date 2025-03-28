
### データをこじ開ける

デストラクチャリングを使ってどのようにデータ構造のレイヤーを剥がすことができるかを見るために、まずは簡単なものを分解して、そこから作業してみよう。このあまり難しくないベクターを見てみよう：

destructuring/examples.clj

```clojure
(def artists [:monet :austen])
```

この2つのキーワードを分離したいとします。分離する方法はたくさんあるが、ここでは2要素のベクターしか扱わないので、`first`関数とその便利な兄弟である`second`を使うことにしよう。

そこでこれを実行する：
  
```clojure 
(let [painter (first artists)
      novelist (second artists)]
  (println "The painter is:" painter
           "and the novelist is" novelist))
```

これで `painter` と `novelist` に正しい値が入る。

デストラクチャリングは、このような手作業による逆アセンブルに代わる方法を提供する。以下に同じ `let` のデストラクチャリングバージョンを示します：

```clojure
(let [[painter novelist] artists]
  (println "The painter is:" painter
           "and the novelist is:" novelist))
```
  

`let`の束縛式の左辺が、単純なシンボルではなく、シンボルのベクターになっていることに注目してほしい：`[painter novelist]`。この左辺のベクターは`artists`のベクターのテンプレートのような働きをする。要するに、私たちは「最初のベクターにあるシンボルをそれに対応する次のベクターにある値に一致させ、束縛しなさい」と言っているのだ。したがって、`painter`というシンボルは`:monet`にバインドされ、`novelist`は`:austen`に束縛される。デストラクチャリングのいいところは、うまくスケールできることだ。

もしベクターにもっと多くの値があったとしたら、おそらくこのようになるだろう：

```clojure
(def artists [:monet :austen :beethoven :dickinson])
```

ベクターにどんどん要素を追加していけばいい：

```clojure
(let [[painter novelist composer poet] artists]
  (println "The painter is" painter)
  (println "The novelist is" novelist)
  (println "The composer is" composer)
  (println "The poet is" poet))
```

そして、シンボルを束縛する。



