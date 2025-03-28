
### AsとRefer

`blottsbooks.pricing/discount-price` のような完全修飾名を使うことの欠点は、長くなることで、長い名前はコードを乱雑にし、読みにくくする傾向があります。幸いなことに、Clojureは乱雑さを最小限にするためのショートカットをいくつか提供しています。

コードをよりノイズの少ないものにする方法の1つは、次のように名前空間のエイリアスを作成することです：

```clojure 
(require '[blottsbooks.pricing :as pricing])
```

あるいはこうだ：

```clojure
(ns blottsbooks.core
  (:require [blottsbooks.pricing :as pricing])
  (:gen-class))
```

`require`に名前空間名だけでなく、3要素のベクターを与えていることに注意してください。101ページの図のように、`require`の拡張バージョンは`blottsbooks.pricing`名前空間を取り込むだけでなく、古き良き`pricing`のエイリアスを与えています。

![fig_1_9_5_001](img/1_9_5_001.png)

このようにすると、`blottsbooks.pricing/discount-price`を`pricing/discount-price`と参照することができる：

```clojure
(defn -main []
  (println
    (pricing/discount-price {:title "Emma" :price 9.99})))
```


`:as`で指定するエイリアスは完全に任意です。`[blottsbooks.pricing :as p]`として、`p/discount-price`を呼び出すこともできます。また、`:as` で作成されるエイリアスは `require` を評価した名前空間に対してローカルであることに注意してください。

もし `pricing` というエイリアスを2つ目の名前空間で使いたい場合は、2つ目の `require.../:as` が必要になります。最後に、エイリアスは通常の束縛をマスクしないので、もし自分の名前空間に `pricing` エイリアスがあったとしても、問題なく `pricing` 関数を定義することができます。

require.../:as`の組み合わせは、完全修飾名が邪魔になる場合の最初の選択肢になるはずです。極端な場合には、さらに一歩進んで `:refer` を使うこともできる：

```clojure
(require '[blottsbooks.pricing :refer [discount-price]])
```

以下の図に示すように、`:refer`を使用すると、基本的に他の名前空間からカレント名前空間にvarsを引き込むことになる。

![fig_1_9_5_002](img/1_9_5_002.png)

つまり、完全修飾名やエイリアスの使用を心配する必要はない：

```clojure
;; Now that I've done the :refer...
(discount-price {:title "Emma" :price 9.99})
```

`:refer`は最も簡潔なコードになる素晴らしい方法のように思えるかもしれないが、その便利さには危険が潜んでいる。すでに`discount-price`関数が定義されていたらどうだろう？そのような場合、`:refer`はそれを上書きしてしまう。これはアプリケーション関数にとって危険なことなので、Clojureが提供する標準的な関数も誤って `:refer` で上書きしてしまう可能性があることを考えてみてください。誰が `first` や `list` が再定義されたコードをデバッグする気になりますか？名前空間はまさにこのような衝突を防ぐために存在するので、`:refer`は使うとしてもごくまれにすべきです。

> [!NOTE]
>
>**REPLプロンプト**。
>
>さて、名前空間について見てきたところで、ついに偉大なるREPLプロンプトの謎を解くことができます。REPL は一般的にカレント・ネームスペースの名前をプロンプトに含めます。プロジェクトディレクトリの外で Leiningen を使って REPL を開始した場合、最初の名前空間は `user` となり、プロンプトにもそれが表示されます。一方、Clojure プロジェクトディレクトリの中から REPL を起動した場合、 Leiningen はそのプロジェクトの `core` 名前空間をデフォルトにします。



