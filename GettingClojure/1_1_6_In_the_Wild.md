
### 野生の中で

REPLベースの "Hello, World "は、Clojureを書く感覚を得るには素晴らしいものですが、"Hello, World "の練習の精神全体を完全に捉えるものではありません。 言語の感触を得るのと一緒に、"Hello, World "は、ファイルに保存されたソースコードから実行中のプログラムまでの詳細を解決するようになっています。幸運なことに、私たちはディスク上にセットアップされた本物のClojureプロジェクトを手に入れるまで、まさに1つのコマンドで済んでいる。必要なのは、`lein new app`の後にアプリケーション名を続けるだけだ。つまり、Blotts Books（Flourishさんは引退しました）のブックストア関連のアプリケーションを作成する場合、次のようになります：

```bash
$ lein new app blottsbooks
```

このコマンドをオペレーティング・システムのコマンドラインに入力すると、Leiningenは次のように応答する：

```bash
Generating a project called blottsbooks...
```
  
そして、新しく `blottsbooks` というディレクトリに、新しい（スケルトンではありますが） Clojure プロジェクトが作成されます。新しい `blottsbooks` ディレクトリの中を見回すと、`CHANGELOG.md` や `README.md` などのファイルや、`src` や `test` などのサブディレクトリがあります：

```bash
$ cd blottsbooks
$ ls
README.md doc/ project.clj resources/ src/ test/ ...
```

今回の目的では、プロジェクトのClojureソースコードを含むファイル`src/blottsbooks/core.clj`に興味があります。それは次のようになります： 

hello/blottsbooks-1/src/blottsbooks/core.clj
```clojure
(ns blottsbooks.core
  (:gen-class))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))
```

このファイルは `ns`（namespace の略）式で始まり、新しい名前空間を設定し、`:gen-class` で名前空間のコンパイルを要求していることに注意してください。名前空間については "第9章 名前空間（95ページ）"で詳しく説明しますが、とりあえず先に進みましょう。

`core.clj`の2番目の式は明らかに関数を定義しているが、この`defn`には明らかに我々が遭遇していないオプションのアクセサリがある。例えば、パラメータの直前に謎の文字列があり、パラメータリストにはアンパサンドがある。ここで止めて、関数の便利な説明を提供することや、可変個の引数を取る関数について話すこともできますが、それは「第5章、より有能な関数、49ページ」に任せて、代わりにこの関数を特別なものにしているもの、つまりその名前に焦点を当てましょう。Clojureプログラムのメイン関数、プログラムを開始するために実行される関数は、常に`-main`と呼ばれます。

これを実際に見るために、`-main`をブックストアの挨拶コードに置き換えてみましょう。ここに修正したcore.cljの全体があります：

hello/blottsbooks-2/src/blottsbooks/core.clj
```clojure
(ns blottsbooks.core
  (:gen-class))

(defn say-welcome [what]
  (println "Welcome to" what "!"))

(defn -main []
  (say-welcome "Blotts Books"))
```

Clojureアプリケーションはこのように実行できる：

```bash
$ lein run
```

そうすれば、こうなる。

```
Welcome to Blotts Books !
```

コードが実行されるのを見る技術的な興奮はさておき、私たちはこの小さなClojureアプリケーションから微妙なことを学ぶことができる。`-main` で使う前に `say-welcome` を定義したことに気づいただろうか？これは基本的なClojureのルールを強調している。つまり、Clojureのコードは、低レベルの関数を最初に定義して、下から上に読み進めるという性質があります。

> [!NOTE]
> 宣言が先か？
>
>`(declare say-welcome)` のように `declare` を使うことで、関数の事前定義を行うことができます。
> ほとんどのClojuristは、使う前に関数を定義することにこだわり、`declare`は相互に再帰的な関数のような厄介な状況のために取っておく。


最後に、`def`も`defn`も同じ名前の泉から出ていることに注意しよう。 つまり

```clojure
(def author "Dickens")

(defn author [name]
  (println "Hey," name "is writing a book!"))
```

を実行すると、`author`という関数ができる。`def`と `defn`の順番を逆にすると、authorは文字列になる。規則としては、最後の `def` または `defn` が勝つ。


