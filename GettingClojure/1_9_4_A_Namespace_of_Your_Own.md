  
### 独自の名前空間

REPL で新しい名前空間を作成する方法と `require` で名前空間を取り込む方法を見たので、最後のステップに進みましょう： ディレクトリプロジェクトの実際の `.clj` ファイルで独自の名前空間を定義します。
新しいClojureアプリケーションを作成することから始めましょう。"Hello, Clojure "で、Leiningenを使用して新しいClojureプロジェクトをこのように作成できたことを思い出してください：

```bash
$ lein new app blottsbooks 
```
 
これらのコマンドを実行すると、骨格はあるにせよ、完全に機能するClojureプロジェクトが完成します。このプロジェクトで重要なのは、`src/blottsbooks/core.clj`にある1つのソースファイルです。

このファイルの中には次のようなものがあります：

hello/blottsbooks-1/src/blottsbooks/core.clj
```clojure 
(ns blottsbooks.core
  (:gen-class)) 

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))
```

ここまでは "Hello, Clojure "で説明したのと同じですが、ファイルの先頭にある`ns`はもう少し意味があるはずです：`blottsbooks.core`という新しい名前空間を設定しているのです。


ファイルベースで名前空間を構築する際に注意すべき点がいくつかあります。まず一番重要なのは、名前空間名（例では`blottsbooks.core`）とファイル名（`blottsbooks/core.clj`）の対応です。この対応は偶然でも省略可能でもありません。`require`のようなツールが機能するために、Clojureは名前空間名の単純な変換を行い、名前空間のコードを保持するファイル名を導き出すことができると仮定しています。名前空間名を取り、ピリオドをディレクトリ区切りのスラッシュに変換し、末尾に `.clj` を付けると、ほら: `blottsbooks.core` は `blottsbooks/core.clj` にあります。


> [!NOTE]
>
> **Class Paths**
>
> 名前空間からファイル名への変換とともに、ClojureはJavaクラスパス-基本的にJVMがコードを探す場所のリスト-にも依存して、名前空間を見つけるのを助けます。これは、Clojureが`src`ディレクトリを探す方法を知っていて、どうやって組み込みのClojureライブラリのコードを見つけるかを管理する方法です。これについては "Javaとの相互運用 "で詳しく説明します。

したがって、もし私たちが`blottsbooks`プロジェクトに2つ目の名前空間（プライシングコードを保持する名前空間）を追加したかったら、`src/blottsbooks/pricing.clj`というファイルを作成すればよいことになります。このファイル名から、名前空間名は `blottsbooks.pricing` となります：

namespace/blottsbooks-1/src/blottsbooks/pricing.clj
```clojure
(ns blottsbooks.pricing)

(def discount-rate 0.15)

(defn discount-price [book]
  (- (:price book)
     (* discount-rate (:price book))))
```


もし、名前空間名にダッシュがある場合（例えば、`blotts-books.current-pricing`）は、ファイル名`blotts_books/current_pricing.clj`の中でダッシュがアンダースコアに変換されます。

名前空間でつながったファイルを作成するときに注意しなければならないのは、`ns`式の中に`require`式を折り込むことができるということです。つまり、`blottsbooks.pricing`の `discount-price` 関数を `blottsbooks.core` で使いたい場合は、次のようにします：

namespace/blottsbooks-1/src/blottsbooks/core.clj
```clojure
(ns blottsbooks.core
  (:require blottsbooks.pricing)
  (:gen-class))

(defn -main []
  (println
    (blottsbooks.pricing/discount-price
      {:title "Emma" :price 9.99})))
```

一般的にClojureプログラマは、REPLで作業しているときはスタンドアロンの `require` を好んで使い(ネームスペースで `require` できるのは便利です)、ソースファイルを書いているときは `ns` バージョンを使います。この2つの `require` のシンタックスは驚くほど違うので注意が必要だ。スタンドアロン版では`require`というシンボルだ。

`ns`バージョンでは `:require` で、キーワードです。スタンドアロン版では引数を引用符で囲む必要がある。`ns`バージョンでは引数を引用符で囲む必要はない。


