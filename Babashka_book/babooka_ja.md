[**Babashka Babooka**](/quests/deploy)

コマンドラインでClojureを書く


# Babashka Babooka： コマンドラインで Clojure を書く

# はじめに 




世の中には2種類のプログラマーがいる：実用的で良識があり、`ln -s` の正しい引数順序をググる必要があるシェルに身を任せた人たちと、57個のコマンドを1行のbashスクリプトにつなぎ合わせて会社のインフラ全体を嬉々として動かす、ひねくれたストックホルムの魂だ。


このガイドは前者のためのものだ。後者については：申し訳ないが、力になれない。



[Babashka](https://babashka.org)はClojureスクリプトランタイムで、あなたが慣れ親しんだシェルスクリプトに代わる強力で楽しいものです。 この包括的なチュートリアルでは


- babashkaとは何か、何をするのか、どのように動作するのか、あなたのワークフローにどのようにフィットするのか。

- babashkaスクリプトの書き方

- babashkaプロジェクトの整理方法

- ポッドとは何か、ポッドが外部プログラムにClojureネイティブインターフェイスを提供する方法

- `make`や`npm`に似たインターフェースを作成するためのタスクの使い方



もしあなたが、苦痛なこと(理解できないシェルスクリプトを書くこと)をやめて、素晴らしいこと(Babashkaスクリプトを書くこと)を始めたいのであれば、この先を読んでください！


## スポンサー



もしこのチュートリアルを楽しんでいただけたら、[GitHubスポンサーを通して私Daniel Higginbothamのスポンサーになることを検討してください](https://github.com/sponsors/flyingmachine)。2022年4月現在、私はClojureをより初心者に優しくするために、無料のClojure教材とオープンソースライブラリに週2日費やしています！


また、[babashkaを作ったborkdudeことMichiel Borkentへのスポンサーもご検討ください](https://github.com/sponsors/borkdude)。Michielは、Clojureの状況を変えるために本当に信じられないような仕事をしており、私たち全員に利益をもたらす方法で、その有用性とリーチを拡張しています。彼は有用なツールを提供し、コミュニティと関わってきた実績があります。




## Babashkaとは? 



ユーザーの視点から見ると、babashkaはClojureプログラミング言語のためのスクリプトランタイムです。通常bashやruby、pythonなどを使用するコンテキストでClojureプログラムを実行することができます。使用例としては、ビルドスクリプト、コマンドラインユーティリティ、小さなウェブアプリケーション、gitフック、AWS Lambda関数など、高速起動や低リソース使用が重要なClojureを使いたいあらゆる場所があります。


ターミナルで以下のように実行すれば、Clojureプログラムを即座に実行できる：




``` {.pygments .highlight}
bb my-clojure-program.clj
```




Clojureに慣れている人なら、ファイルをコンパイルする必要がないのはもちろん、JVMコンパイルされたClojureプログラムのために悩まされるスタートアップ時間がなくなるので、これが重要だとわかるだろう。また、jarを実行するよりもはるかに少ないメモリしか使用しません。Babashkaを使えば、今以上にClojureを使いこなすことが可能です。


Clojureに馴染みがない場合、Babashkaを使うことは、この言語を試すのに最適な方法です。Clojureは*ホスト型*言語であり、言語が基礎となる実行環境とは独立して定義されていることを意味します。 ほとんどのClojureプログラムはJava Virtual Machine (JVM)上で動作するようにコンパイルされているので、Javaが動作する場所であればどこでも実行できます。もう1つの主なターゲットはJavaScriptで、Clojureをブラウザで実行できるようにします。Babashkaを使えば、通常bashスクリプトを実行する場所でClojureプログラムを実行できる。あなたがClojureに投資する時間は、あなたの知識がこれらの多様な環境に移行する際に配当されます。


実装の観点からは、Babashkaはスタンドアロンでネイティブにコンパイルされたバイナリであり、JVMで実行するのではなく、オペレーティングシステムが直接実行することを意味する。Babashkaバイナリがコンパイルされるとき、ネイティブパフォーマンスで使用できるように、多くのClojure名前空間とライブラリが含まれます。あなたは[組み込み名前空間の全リストをチェックする](https://book.babashka.org/#libraries)ことができます。Babashkaは、deps.ednやLeiningenを使用している場合のように、他のライブラリをインクルードすることもできます。


このバイナリには[Small Clojure Interpreter (SCI)](https://github.com/babashka/SCI) も含まれており、あなたが書いたClojureや追加ライブラリをその場で解釈します。Clojureの実装はJVM Clojureとほぼ同等で、[Michiel Borkent](https://github.com/borkdude)氏の絶え間ない努力のおかげで日々改善されています。GraalVMで構築されています。このガイドは、Babashkaで生産的になることに重点を置いており、実装を深くカバーしていませんが、[GraalVMブログのこの記事](https://medium.com/graalvm/babashka-how-graalvm-helped-create-a-fast-starting-scripting-environment-for-clojure-b0fcc38b0746)を読むことで、より詳しく知ることができます。



## なぜ使うべきなのか? 



Clojureそのものの利点については、[他](https://jobs-blog.braveclojure.com/2022/03/24/long-term-clojure-benefits.html)にたくさん資料があるので、ここでは触れません。


Clojureであるという事実を超えて、Babashkaは競合から際立ついくつかの特徴をもたらします：



**ファーストクラスのマルチスレッドプログラミングのサポート.** Clojureは、マルチスレッドプログラミングをシンプルで簡単に書くことができます。 Babashkaを使えば、例えば複数のデータベースから並列にデータを取得して処理するような簡単なスクリプトを書くことができます。


**実際のテスト.** 他のClojureプロジェクトと同じように、Babashkaコードをユニットテストできます。bashのテストはどうやるの？



** 本当のプロジェクト管理.** Clojure名前空間は、プロジェクトの関数を整理し、再利用可能なライブラリを構築するまともな方法です。



**クロスプラットフォーム互換性.** OS Xで開発されたスクリプトが、継続的インテグレーション・パイプラインで壊れていることを心配する必要がないのは良いことです。



**対話的な開発.** Lispの伝統に従って、BabashkaはREPL(read-eval-print loop)を提供します。スクリプト開発は本質的に速いものですが、Babashkaはそれをより速くします。


**スクリプトのインターフェイスを定義するための組み込まれたツール.** シェルスクリプトを書く理由のひとつは、複雑な処理に簡潔でわかりやすいインターフェイスを提供することです。例えば、`build`コマンドと`deploy`コマンドを含むビルドスクリプトを書くとします。




``` {.pygments .highlight}
./my-script build
./my-script deploy
```




Babashkaには、そのようなコマンドを定義し、コマンドライン引数をClojureデータ構造にパースするための一貫した方法を提供するツールが付属しています。bashもどうぞ！


**豊富なライブラリセット.** Babashkaには、プロセスとのやり取りやファイルシステムの操作など、典型的なシェルスクリプトの補助作業を行うためのヘルパーユーティリティが付属しています。また、余計な依存関係を必要とせず、以下のようなものもサポートしています：



- JSON の解析

- YAMLの解析

- HTTPサーバーの起動

- 生成テストを書く



そしてもちろん、Clojureライブラリを依存関係として追加して、さらに多くのことを達成することができます。Clojureは他のプログラミング・パラダイムへの入り口であり、コマンドラインからロジック・プログラミングなどをやりたかったら、今がチャンスだ！



**良いエラーメッセージ.** Babashkaのエラー処理は、すべてのClojure実装の中で最も友好的で、エラーが発生した場所を正確に指示します。





## インストール 



brewでのインストールは `brew install borkdude/brew/babashka` です。



[他のシステムについては、Babashkaの完全なインストール手順を参照してください](https://github.com/babashka/babashka#installation)




# 最初のスクリプト 


このチュートリアルでは、CLIベースの小さな夢の日記帳を作って遊びます。なぜかって？なぜなら、あなた方オタクが潜在意識に潜む奇妙な幻覚を記録するというアイデアが、私にはとても面白いからです。


このセクションでは、以下のことを学びます：



- 最初のBabashkaスクリプトの書き方と実行方法

- デフォルト出力の扱い方

- Babashkaの名前空間の扱い方



`hello.clj`という名前のファイルを作成し、次のように記述します：




``` {.pygments .highlight}
(require '[clojure.string :as str])
(prn (str/join " " ["Hello" "inner" "world!"]))
```




次に、`bb`でbabashka実行ファイルを実行する：




``` {.pygments .highlight}
bb hello.clj
```




すると、`"Hello inner world!"`というテキストが表示されるはずだ。



経験豊富なClojurianのために指摘しておくことがいくつかあります：



- deps.ednファイルやproject.cljは必要ありません。

- 名前空間宣言がない; `(require ...)` を使っている。

- ただのClojureです。



この例は、あなたが慣れ親しんでいるものとは異なるように*感じる*ので、先に進む前に実際に試してみることを強くお勧めします。ファイルにいくつかのClojure式を放り込んで、すぐに実行できることに慣れているとは思えない。


私が初めてBabashkaを使い始めたとき、あまりの違いに戸惑いました。初めて電気自動車を運転してみたとき、エンジンがかかる音や感触といった典型的な感覚的な合図が得られなかったので、体が少しパニックになったような感じだった。


Babashkaはそんな感じだ。体験はとても静かでスムーズで、衝撃的だ。deps.ednも名前空間宣言もなく、必要なコードだけを書けば実行される！



必要なコードだけを書けば実行されるのです！だから私は「ただのClojureだ」という箇条書きを入れたのです。違うと感じるかもしれませんが、これはまだClojureです。他の点をもっと詳しく調べてみよう。



## Babashkaの出力 



何が起こっているかと言うと： `bb` はあなたが書いたClojureコードを解釈し、その場で実行する。`prn`は`stdout`に出力するので、`"Hello, inner world!"`がターミナルに返されます。

**Note**
テキストを `stdout` に出力すると、それがターミナルに出力されます。 このチュートリアルでは `stdout` が実際にどのようなものなのかについては触れませんが、あなたのプログラムの内部とあなたのプログラムの環境の世界の間にあるものと考えてください。あなたが `stdout` に何かを送信すると、ターミナルがそれを受信してそれを表示します。



値が出力されるとき、引用符が維持されていることに注意してほしい。`bb`はあなたのデータ構造の*文字列化された表現*を表示します。もし`hello.clj`を更新して



``` {.pygments .highlight}
"Hello, inner world!"
(prn ["It's" "me," "your" "wacky" "subconscious!"])
```




そうすると、`[" It's" "me," "your" "wacky" "subconscious!" ]`は出力され、`"Hello, inner world!" `は出力されない。文字列を `stdout` に送信するには、フォーム上で表示関数を使用する必要があります。


引用符で囲まずに文字列を表示したい場合は




``` {.pygments .highlight}
(println "Hello, inner world!")
```






## 名前空間はオプションです 



名前空間がないことについては、Babashkaがスクリプトツールとして有用である理由のひとつです。スクリプティングを思いついたとき、すぐにでもアイデアをハックしたいものです；スクリプティングを始めるために、定型文を扱う必要はありません。BabashkaはあなたのBabackaを持っています。



名前空間を定義することもできますが（プロジェクトの構成について説明するときに詳しく説明します）、定義しない場合、Babashkaはデフォルトで`user`名前空間を使用します。ファイルを更新してみてください：



``` {.pygments .highlight}
(str "Hello from " *ns* ", inner world!")
```




実行すると`"Hello from user, inner world!"`と表示される。これは、ファイル名(`hello.clj`)と名前空間名の間にミスマッチがあるためです。他のClojure実装では、現在の名前空間はソースファイルのファイル名に厳密に対応しますが、Babashkaはこの特定のコンテキストでそれを少し緩和します。これは、他のスクリプト言語を使用する際に期待されるものと、より一致したスクリプティング体験を提供します。





## 他の名前空間をrequireする場合は？



いくつかの名前空間をrequireしたいので、名前空間宣言を含めたいかもしれません。JVM ClojureとClojurescriptでは、通常このように名前空間を要求します：




``` {.pygments .highlight}
(ns user
  (:require
   [clojure.string :as str]))
```




ソースコード中に`(require '[clojure.string :as str])`と記述して名前空間をrequireするのは悪い形式だと考えられています。



しかし、Babashkaではそうではありません。他の例では `(require ...)` が自由に使われています、そして、あなたもそうしても構いません。





## スクリプトを実行可能にする 



もし、`bb hello.clj`ではなく、`./hello`のように入力してスクリプトを実行したい場合はどうすればいいだろうか？ファイル名を変更し、shebangを追加し、`chmod +x` するだけです。`hello.clj`を更新する：



``` {.pygments .highlight}
#!/usr/bin/env bb

(str "Hello from " *ns* ", inner world!")
```



**Note**
最初の行、`#!/usr/bin/env bb`は「shebang」で、ここには書かない。



そして、ターミナルでこれを実行する：




``` {.pygments .highlight}
mv hello{.clj,}
chmod +x hello
./hello
```




まずファイル名を変更し、次に`chmod +x`を呼び出して実行可能にする。そして実際に実行し、ちょっと愛らしい自分の内なる世界に挨拶する。




## まとめ 



このセクションで学んだことは以下の通りだ：



- スクリプトは `bb script-name.clj` で実行できる。

- スクリプトの先頭行に `#!/usr/bin/env bb` を追加し、 `chmod +x script-name.clj` で `execute` パーミッションを追加することで、スクリプトを直接実行可能にすることができる。

- スクリプトに `(ns ...)` 宣言を含める必要はない。 しかし、それでも実行され、Clojureであることに変わりはありません！

- 名前空間を `(require ...)` で require することは問題ありませんし、推奨されます。

- Babashka は最後に出会った値を `stdout` に書き込みますが、その値が `nil` の場合は例外です。




# Working with files 


Shell scripts often need to read input from the command line and produce output somewhere, and our dream journal utility is no exception. It's going to store entries in the file `entries.edn`. The journal will be a vector, and each entry will be a map with the keys `:timestamp` and `:entry` (the entry has linebreaks for readability):



``` {.pygments .highlight}
[{:timestamp 0
  :entry     "Dreamt the drain was clogged again, except when I went to unclog
              it it kept growing and getting more clogged and eventually it
              swallowed up my little unclogger thing"}
 {:timestamp 1
  :entry     "Bought a house in my dream, was giving a tour of the backyard and
              all the... topiary? came alive and I had to fight it with a sword.
              I understood that this happens every night was very annoyed that
              this was not disclosed in the listing."}]
```




To write to the journal, we want to run the command `./journal add --entry "Hamsters. Hamsters everywhere. Again."`. The result should be that a map gets appended to the vector.



Let's get ourselves part of the way there. Create the file `journal` and make it executable with `chmod +x journal`, then make it look like this:




``` {.pygments .highlight}
#!/usr/bin/env bb

(require '[babashka.fs :as fs])
(require '[clojure.edn :as edn])

(def ENTRIES-LOCATION "entries.edn")

(defn read-entries
  []
  (if (fs/exists? ENTRIES-LOCATION)
    (edn/read-string (slurp ENTRIES-LOCATION))
    []))

(defn add-entry
  [text]
  (let [entries (read-entries)]
    (spit ENTRIES-LOCATION
          (conj entries {:timestamp (System/currentTimeMillis)
                         :entry     text}))))

(add-entry (first *command-line-args*))
```




We require a couple namespaces: `babashka.fs` and `clojure.edn`.  `babashka.fs` is a collection of functions for working with the filesystem; check out its [API docs](https://github.com/babashka/fs).  When you're writing shell scripts, you're very likely to work with the filesystem, so this namespace is going to be your friend.


Here, we're using the `fs/exists?` function to check that `entries.edn` exists before attempting to read it because `slurp` will throw an exception if it can't find the file for the path you passed it.


The `add-entry` function uses `read-entries` to get a vector of entries, uses `conj` to add an entry, and then uses `spit` to write back to `entries.edn`. By default, `spit` will overwrite a file; if you want to append to it, you would call it like




``` {.pygments .highlight}
(spit "entries.edn" {:timestap 0 :entry ""} :append true)
```




Maybe overwriting the whole file is a little dirty, but that's the scripting life babyyyyy!


# Creating an interface for your script 


OK so in the last line we call `(add-entry (first *command-line-args*))`. `*command-line-args*` is a sequence containing, well, all the command line arguments that were passed to the script. If you were to create the file `args.clj` with the contents `*command-line-args*`, then ran `bb args.clj 1 2 3`, it would print `("1" "2" "3")`.


Our `journal` file is at the point where we can add an entry by calling `./journal "Flying\!\! But to Home Depot??"`. This is almost what we want; we actually want to call `./journal add --entry "Flying\!\! But to Home Depot??"`. The assumption here is that we'll want to have other commands like `./journal list` or `./journal delete`. (You have to escape the exclamation marks otherwise bash interprets them as history commands.)



To accomplish this, we'll need to handle the command line arguments in a more sophisticated way. The most obvious and least-effort way to do this would be to dispatch on the first argument to `*command-line-args*`, something like this:



``` {.pygments .highlight}
(let [[command _ entry] *command-line-args*]
  (case command
    "add" (add-entry entry)))
```




This might be totally fine for your use case, but sometimes you want something more robust. You might want your script to:



-   List valid commands

-   Give an intelligent error message when a user calls a command that doesn't exist (e.g. if the user calls `./journal add-dream` instead of `./journal add`)

-   Parse arguments, recognizing option flags and converting values to keywords, numbers, vectors, maps, etc



Generally speaking, **you want a clear and consistent way to define an interface for your script**. This interface is responsible for taking the data provided at the command line --- arguments passed to the script, as well as data piped in through `stdin` --- and using that data to handle these three responsibilities:


-   Dispatching to a Clojure function

-   Parsing command-line arguments into Clojure data, and passing that to the dispatched function

-   Providing feedback in cases where there's a problem performing the above responsibilities.



The broader Clojure ecosystem provides at least two libraries for handling argument parsing:



-   [clojure.tools.cli](https://github.com/clojure/tools.cli)

-   [nubank/docopt.clj](https://github.com/nubank/docopt.clj)



Babashka provides the [babashka.cli library](https://github.com/babashka/cli) for both parsing options and dispatches subcommands. We're going to focus just on babashka.cli.


## parsing options with babashka.cli 



The [babashka.cli docs](https://github.com/babashka/cli) do a good job of explaining how to use the library to meet all your command line parsing needs. Rather than going over every option, I'll just focus on what we need to build our dream journal. To parse options, we require the `babashka.cli` namespace and we define a *CLI spec*:



``` {.pygments .highlight}
(require '[babashka.cli :as cli])
(def cli-opts
  {:entry     {:alias   :e
               :desc    "Your dreams."
               :require true}
   :timestamp {:alias  :t
               :desc   "A unix timestamp, when you recorded this."
               :coerce {:timestamp :long}}})
```




A CLI spec is a map where each key is a keyword, and each value is an *option spec*. This key is the *long name* of your option; `:entry` corresponds to the flag `--entry` on the command line.


The option spec is a map you can use to further config the option.  `:alias` lets you specify a *short name* for you options, so that you can use e.g. `-e` instead of `--entry` at the command line. `:desc` is used to create a summary for your interface, and `:require` is used to enforce the presence of an option. `:coerce` is used to transform the option's value into some other data type.



We can experiment with this CLI spec in a REPL. There are many options for starting a Babashka REPL, and the most straightforward is simply typing `bb repl` at the command line. If you want to use CIDER, first add the file `bb.edn` and put an empty map, `{}`, in it. Then you can use `cider-jack-in`. After that, you can paste in the code from the snippet above, then paste in this snippet:




``` {.pygments .highlight}
(cli/parse-opts ["-e" "The more I mowed, the higher the grass got :("] {:spec cli-opts})
;; =>
{:entry "The more I mowed, the higher the grass got :("}
```




Note that `cli/parse-opts` returns a map with the parsed options, which will make it easy to use the options later.



Leaving out a required flag throws an exception:




``` {.pygments .highlight}
(cli/parse-opts [] {:spec cli-opts})
;; exception gets thrown, this gets printed:
: Required option: :entry user
```




`cli/parse-opts` is a great tool for building an interface for simple scripts! You can communicate that interface to the outside world with `cli/format-opts`. This function will take an option spec and return a string that you can print to aid people in using your program. Behold:




``` {.pygments .highlight}
(println (cli/format-opts {:spec cli-opts}))
;; =>
-e, --entry     Your dreams.
-t, --timestamp A unix timestamp, when you recorded this.
```






## dispatching subcommands with babashka.cli 



babashka.cli goes beyond option parsing to also giving you a way to dispatch subcommands, which is exactly what we want to get `./journal add --entry "…"` working. Here's the final version of `journal`:




``` {.pygments .highlight}
#!/usr/bin/env bb

(require '[babashka.cli :as cli])
(require '[babashka.fs :as fs])
(require '[clojure.edn :as edn])

(def ENTRIES-LOCATION "entries.edn")

(defn read-entries
  []
  (if (fs/exists? ENTRIES-LOCATION)
    (edn/read-string (slurp ENTRIES-LOCATION))
    []))

(defn add-entry
  [{:keys [opts]}]
  (let [entries (read-entries)]
    (spit ENTRIES-LOCATION
          (conj entries
                (merge {:timestamp (System/currentTimeMillis)} ;; default timestamp
                       opts)))))

(def cli-opts
  {:entry     {:alias   :e
               :desc    "Your dreams."
               :require true}
   :timestamp {:alias  :t
               :desc   "A unix timestamp, when you recorded this."
               :coerce {:timestamp :long}}})

(defn help
  [_]
  (println
   (str "add\n"
        (cli/format-opts {:spec cli-opts}))))

(def table
  [{:cmds ["add"] :fn add-entry :spec cli-opts}
   {:cmds [] :fn help}])

(cli/dispatch table *command-line-args*)
```




Try it out with the following at your terminal:




``` {.pygments .highlight}
./journal
./journal add -e "dreamt they did one more episode of Firefly, and I was in it"
```




The function `cli/dispatch` at the bottom takes a dispatch table as its first argument. `cli/dispatch` figures out which of the arguments you passed in at the command line correspond to commands, and then calls the corresponding `:fn`. If you type `./journal add …`, it will dispatch the `add-entry` function. If you just type `./journal` with no arguments, then the `help` function gets dispatched.



The dispatched function receives a map as its argument, and that map contains the `:opts` key. This is a map of parsed command line options, and we use it to build our dream journal entry in the `add-entry` function.



And that, my friends, is how you build an interface for your script!





## Summary 



-   For scripts of any complexity, you generally need to *parse* the command line options into Clojure data structures

-   The libraries `clojure.tools.cli` and `nubank/docopts` will parse command line arguments into options for you

-   I prefer using `babashka.cli` because it also handles subcommand dispatch, but really this decision is a matter of taste

-   `cli/parse-opts` takes an *options spec* and returns a map

-   `cli/format-opts` is useful for creating help text

-   Your script might provide *subcommands*, e.g. `add` in `journal add`, and you will need to map the command line arguments to the appropriate function in your script with `cli/dispatch`




# Organizing your project 


You can now record your subconscious's nightly improv routine. That's great! High on this accomplishment, you decide to kick things up a notch and add the ability to list your entries. You want to run `./journal list` and have your script return something like this:




``` {.pygments .highlight}
2022-12-07 08:03am
There were two versions of me, and one version baked the other into a pie and ate it.
Feeling both proud and disturbed.

2022-12-06 07:43am
Was on a boat, but the boat was powered by cucumber sandwiches, and I had to keep
making those sandwiches so I wouldn't get stranded at sea.
```




You read somewhere that source files should be AT MOST 25 lines long, so you decide that you want to split up your codebase and put this list functionality in its own file. How do you do that?



You can organize your Babashka projects just like your other Clojure projects, splitting your codebase into separate files, with each file defining a namespace and with namespaces corresponding to file names.  Let's reorganize our current codebase a bit, making sure everything still works, and then add a namespace for listing entries.


## File system structure 



One way to organize our dream journal project would be to create the following file structure:




``` {.pygments .highlight}
./journal
./src/journal/add.clj
./src/journal/utils.clj
```




Already, you can see that this looks both similar to typical Clojure project file structures, and a bit different. We're placing our namespaces in the `src/journal` directory, which lines up with what you'd see in JVM or ClojureScript projects. What's different in our Babashka project is that we're still using `./journal` to serve as the executable entry point for our program, rather than the convention of using `./src/journal/core.clj` or something like that. This might feel a little weird but it's valid and it's still Clojure.


And like other Clojure environments, you need to tell Babashka to look in the `src` directory when you require namespaces. You do that by creating the file `bb.edn` in the same directory as `journal` and putting this in it:



``` {.pygments .highlight}
{:paths ["src"]}
```




`bb.edn` is similar to a `deps.edn` file in that one of its responsibilities is telling Babashka how to construct your classpath.  The classpath is the set of the directories that Babashka should look in when you require namespaces, and by adding `"src"` to it you can use `(require '[journal.add])` in your project. Babashka will be able to find the corresponding file.


Note that there is nothing special about the `"src"` directory. You could use `"my-code"` or even `"."` if you wanted, and you can add more than one path. `"src"` is just the convention preferred by discerning Clojurians the world over.


With this in place, we'll now update `journal` so that it looks like this:




``` {.pygments .highlight}
#!/usr/bin/env bb

(require '[babashka.cli :as cli])
(require '[journal.add :as add])

(def cli-opts
  {:entry     {:alias   :e
               :desc    "Your dreams."
               :require true}
   :timestamp {:alias  :t
               :desc   "A unix timestamp, when you recorded this."
               :coerce {:timestamp :long}}})

(def table
  [{:cmds ["add"] :fn add/add-entry :spec cli-opts}])

(cli/dispatch table *command-line-args*)
```




Now the file is only responsible for parsing command line arguments and dispatching to the correct function. The add functionality has been moved to another namespace.





## Namespaces 



You can see on line 4 that we're requiring a new namespace, `journal.add`. The file corresponding to this namespace is `./src/journal/add.clj`. Here's what that looks like:




``` {.pygments .highlight}
(ns journal.add
  (:require
   [journal.utils :as utils]))

(defn add-entry
  [opts]
  (let [entries (utils/read-entries)]
    (spit utils/ENTRIES-LOCATION
          (conj entries
                (merge {:timestamp (System/currentTimeMillis)} ;; default timestamp
                       opts)))))
```




Look, it's a namespace declaration! And that namespace declaration has a `(:require …)` form. We know that when you write Babashka scripts, you can forego declaring a namespace if all your code is in one file, like in the original version of `journal`. However, once you start splitting your code into multiple files, the normal rules of Clojure project organization apply:



-   Namespace names must correspond to filesystem paths. If you want to name a namespace `journal.add`, Babashka must be able to find it at `journal/add.clj`.

-   You must tell Babashka where to look to find the files that correspond to namespaces. You do this by creating a `bb.edn` file and putting `{:paths ["src"]}` in it.


To finish our tour of our new project organization, here's `./src/journal/utils.clj`:




``` {.pygments .highlight}
(ns journal.utils
  (:require
   [babashka.fs :as fs]
   [clojure.edn :as edn]))

(def ENTRIES-LOCATION "entries.edn")

(defn read-entries
  []
  (if (fs/exists? ENTRIES-LOCATION)
    (edn/read-string (slurp ENTRIES-LOCATION))
    []))
```




If you call `./journal add -e "visited by the tooth fairy, except he was a balding 45-year-old man with a potbelly from Brooklyn"`, it should still work.



Now lets create a the `journal.list` namespace. Open the file `src/journal/list.clj` and put this in it:




``` {.pygments .highlight}
(ns journal.list
  (:require
   [journal.utils :as utils]))

(defn list-entries
  [_]
  (let [entries (utils/read-entries)]
    (doseq [{:keys [timestamp entry]} (reverse entries)]
      (println timestamp)
      (println entry "\n"))))
```




This doesn't format the timestamp, but other than that it lists our entries in reverse-chronologial order, just like we want. Yay!



To finish up, we need to add `journal.list/list-entries` to our dispatch table in the `journal` file. That file should now look like this:




``` {.pygments .highlight}
#!/usr/bin/env bb

(require '[babashka.cli :as cli])
(require '[journal.add :as add])
(require '[journal.list :as list])

(def cli-opts
  {:entry     {:alias   :e
               :desc    "Your dreams."
               :require true}
   :timestamp {:alias  :t
               :desc   "A unix timestamp, when you recorded this."
               :coerce {:timestamp :long}}})

(def table
  [{:cmds ["add"] :fn #(add/add-entry (:opts %)) :spec cli-opts}
   {:cmds ["list"] :fn #(list/list-entries %)}])

(cli/dispatch table *command-line-args*)
```






## Summary 



-   Namespaces work like they do in JVM Clojure and Clojurescript: namespace names must correspond to file system structure

-   Put the map `{:paths ["src"]}` in `bb.edn` to tell Babashka where to find the files for namespaces




# Adding dependencies 


You can add dependencies to your projects by adding a `:deps` key to your `bb.edn` file, resulting in something like this:




``` {.pygments .highlight}
{:paths ["src"]
 :deps {medley/medley {:mvn/version "1.3.0"}}}
```




What's cool about Babashka though is that you can also add deps directly in your script, or even in the repl, like so:




``` {.pygments .highlight}
(require '[babashka.deps :as deps])
(deps/add-deps '{:deps {medley/medley {:mvn/version "1.3.0"}}})
```




This is in keeping with the nature of a scripting language, which should enable quick, low-ceremony development.



At this point you should be fully equipped to start writing your own Clojure shell scripts with Babashka. Woohoo!



In the sections that follow, I'll cover aspects of Babashka that you might not need immediately but that will be useful to you as your love of Clojure scripting grows until it becomes all-consuming.

# Pods 


Babashka *pods* introduce a way to interact with external processes by calling Clojure functions, so that you can write code that looks and feels like Clojure (because it is) even when working with a process that's running outside your Clojure application, and even when that process is written in another language.



## Pod usage 



Let's look at what that means in more concrete terms. Suppose you want to encrypt your dream journal. You find out about [stash](https://github.com/rorokimdim/stash), \"a command line program for storing text data in encrypted form.\" This is exactly what you need! Except it's written in Haskell, and furthermore it has a *terminal user interface* (TUI) rather than a command-line interface.


That is, when you run `stash` from the command line it \"draws\" an ascii interface in your terminal, and you must provide additional input to store text. You can't store text directly from the command line with something like




``` {.pygments .highlight}
stash store dreams.stash \
      --key 20221210092035 \
      --value "was worried that something was wrong with the house's foundation,
               then the whole thing fell into a sinkhole that kept growing until
               it swallowed the whole neighborhood"
```




If that were possible, then you could use `stash` from within your Bashka project by using the `babashka.process/shell` function, like this:




``` {.pygments .highlight}
(require '[babashka.process :as bp])
(bp/shell "stash store dreams.stash --key 20221210092035 --value \"...\"")
```




`bp/shell` is lets you take advantage of a program's command-line interface; but again, `stash` doesn't provide that.



However, `stash` provides a *pod interface*, so we can use it like this in a Clojure file:




``` {.pygments .highlight}
(require '[babashka.pods :as pods])
(pods/load-pod 'rorokimdim/stash "0.3.1")
(require '[pod.rorokimdim.stash :as stash])

(stash/init {"encryption-key" "foo"
             "stash-path" "foo.stash"
             "create-stash-if-missing" true})

(stash/set 20221210092035 "dream entry")
```




Let's start at the last line, `(stash/set 20221210092035 "dream entry")`. This is the point of pods: they expose an external process's commands as Clojure functions. They allow these processes to have a *Clojure interface* so that you can interact with them by writing Clojure code, as opposed to having to shell out or make HTTP calls or something like that.


In the next section I'll explain the rest of the snippet above.





## Pod implementation 



Where does the `stash/set` function come from? Both the namespace `pod.rorokimdim.stash` and the functions in it are dynamically generated by the call `(pods/load-pod 'rorokimdim/stash "0.3.1")`.


For this to be possible, the external program has to be written to support the *pod protocol*. \"Protocol\" here does not refer to a Clojure protocol, it refers to a standard for exchanging information.  Your Clojure application and the external application need to have some way to communicate with each other given that they don't live in the same process and they could even be written in different languages.


By implementing the pod protocol, a program becomes a pod. In doing so, it gains the ability to tell the *client* Clojure application what namespaces and functions it has available. When the client application calls those functions, it encodes data and sends it to the pod as a message. The pod will be written such that it can listen to those messages, decode them, execute the desired command internally, and send a response message to the client.


The pod protocol is documented in [the pod GitHub repo](https://github.com/babashka/pods).





## Summary 



-   Babashka's pod system lets you interact with external processes using Clojure functions, as opposed to shelling out with `babashka.process/shell` or making HTTP requests, or something like that

-   Those external processes are called *pods* and must implement the *pod protocol* to tell client programs how to interact with them




# Other ways of executing code 


This tutorial has focused on helping you build a standalone script that you interact with like would a typical bash script script: you make it executable with `chmod +x` and you call it from the command line like `./journal add -e "dream entry"`.



There are other flavors (for lack of a better word) of shell scripting that bash supports:



-   Direct expression evaluation

-   Invoking a Clojure function

-   Naming tasks



## Direct Expression Evaluation 



You can give Babashka a Clojure expression and it will evaluate it and print the result:




``` {.pygments .highlight}
$ bb -e '(+ 1 2 3)'
9

$ bb -e '(map inc [1 2 3])'
(2 3 4)
```




Personally I haven't used this much myself, but it's there if you need it!





## Invoking a Clojure function 



If we wanted to call our `journal.add/add-entry` function directly, we could do this:




``` {.pygments .highlight}
bb -x journal.add/add-entry --entry "dreamt of foo"
```




When you use `bb -x`, you can specify the fully-qualified name of a function and Babashka will call it. It will parse command-line arguments using `babashka.cli` into a Clojure value and pass that to the specified function. See [the -x section of the Babashka docs](https://book.babashka.org/#_x) for more information.



You can also use `bb -m some-namespace/some-function` to call a function. The difference between this and `bb -x` is that with `bb -m`, each command line argument is passed unparsed to the Clojure function.  For example:




``` {.pygments .highlight}
$ bb -m clojure.core/identity 99
"99"

$ bb -m clojure.core/identity "[99 100]"
"[99 100]"

$ bb -m clojure.core/identity 99 100
----- Error --------------------------------------------------------------------
Type:     clojure.lang.ArityException
Message:  Wrong number of args (2) passed to: clojure.core/identity
Location: <expr>:1:37
```




When using `bb -m`, you can just pass in a namespace and Babashka will call the `-main` function for that namespace. Like, if we wanted our `journal.add` namespace to work with this flavor of invocation, we would write it like this:




``` {.pygments .highlight}
(ns journal.add
  (:require
   [journal.utils :as utils]))

(defn -main
  [entry-text]
  (let [entries (utils/read-entries)]
    (spit utils/ENTRIES-LOCATION
          (conj entries
                {:timestamp (System/currentTimeMillis)
                 :entry     entry-text}))))
```




And we could do this:




``` {.pygments .highlight}
$ bb -m journal.add "recurring foo dream"
```




Note that for `bb -x` or `bb -m` to work, you must set up your `bb.edn` file so that the namespace you're invoking is reachable on the classpath.




# Tasks 


Another flavor of running command line programs is to call them similarly to `make` and `npm`. In your travels as a programmer, you might have run these at the command line:



``` {.pygments .highlight}
make install
npm build
npm run build
npm run dev
```




Babashka allows you to write commands similarly. For our dream journal, we might want to be able to execute the following in a terminal:




``` {.pygments .highlight}
bb add -e "A monk told me the meaning of life. Woke up, for got it."
bb list
```




We're going to build up to that in small steps.



## A basic task 



First, let's look at a very basic task definition. Tasks are defined in your `bb.edn` file. Update yours to look like this:




``` {.pygments .highlight}
{:tasks {welcome (println "welcome to your dream journal")}}
```




Tasks are defined using a map under the `:tasks` keyword. Each key of the map names a task, and it should be a symbol. Each value should be a Clojure expression. In this example, the `welcome` names a task and the associated expression is `(println "welcome to your dream journal")`.



When you call `bb welcome`, it looks up the `welcome` key under `:tasks` and evaluates the associated expression. Note that you must explicitly print values if you want them to be sent to `stdout`; this wouldn't print anything:




``` {.pygments .highlight}
{:tasks {welcome "welcome to your dream journal"}}
```






## How to require namespaces for tasks 



Let's say you wanted to create a task to delete your journal entries.  Here's what that would looke like:




``` {.pygments .highlight}
{:tasks {welcome (println "welcome to your dream journal")
         clear   (shell "rm -rf entries.edn")}}
```




If you run `bb clear` it will delete your `entries.edn` file. This works because `shell` is automatically referred in namespaces, just `clojure.core` functions are.



If you wanted to delete your file in a cross-platform-friendly way, you could use the `babashka.fs/delete-if-exists` function. To do that, you must require the `babashka.fs` namespace. You might assume that you could update your `bb.edn` to look like this and it would work, but it wouldn't:




``` {.pygments .highlight}
{:tasks {clear (do (require '[babashka.fs :as fs])
                   (fs/delete-if-exists "entries.edn"))}}
```




Instead, to require namespaces you must do so like this:




``` {.pygments .highlight}
{:tasks {:requires ([babashka.fs :as fs])
         clear     (fs/delete-if-exists "entries.edn")}}
```






## Use `exec` to parse arguments and call a function 



We still want to be able to call `bb add` and `bb list`. We have what we need to implement `bb list`; we can just update `bb.edn` to look like this:




``` {.pygments .highlight}
{:paths ["src"]
 :tasks {:requires ([babashka.fs :as fs]
                    [journal.list :as list])
         clear     (fs/delete-if-exists "entries.edn")
         list      (list/list-entries nil)}}
```




In the previous task examples I excluded the `:paths` key because it wasn't needed, but we need to bring it back so that Babashka can find `journal.list` on the classpath. `journal.list/list-entries` takes one argument that gets ignored, so we can just pass in `nil` and it works.



`journal.add/add-entries`, however, takes a Clojure map with an `:entries` key. Thus we need some way of parsing the command line arguments into that map and then passing that to `journal.add/add-entries`. Babashka provides the `exec` function for this. Update your `bb.edn` like so, and everything should work:




``` {.pygments .highlight}
{:paths ["src"]
 :tasks {:requires ([babashka.fs :as fs]
                    [journal.list :as list])
         clear     (fs/delete-if-exists "entries.edn")
         list      (list/list-entries nil)
         add       (exec 'journal.add/add-entry)}}
```




Now we can call this, and it should work:




``` {.pygments .highlight}
$ bb add --entry "dreamt I was done writing a tutorial. bliss"

$ bb list
1670718856173
dreamt I was done writing a tutorial. bliss
```




The key here is the `exec` function. With `(exec 'journal.add/add-entry)`, it's as if you called this on the command line:




``` {.pygments .highlight}
$ bb -x journal.add/add-entry --entry "dreamt I was done writing a tutorial. bliss"
```




`exec` will parse command line arguments in the same way as `bb -x` does and pass the result to the designated function, which is `journal.add/add-entry` in this example.




## Task dependencies, parallel tasks, and more 



Babashka's task system has even more capabilities, which I'm not going to cover in detail but which you can read about in the [Task runner section of the Babashka docs](https://book.babashka.org/#tasks).


I do want to highlight two very useful features: *task dependencies* and *parallel task execution*.



Babashka let's you define task dependencies, meaning that you can define `task-a` to depend on `task-b` such that if you run `bb task-a`, internally `task-b` will be executed if needed. This is useful for creating compilation scripts. If you were building a web app, for example, you might have separate tasks for compiling a backend jar file and frontend javascript file. You could have the tasks `build-backend`, `build-frontend`, and then have a `build` task that depended on the other two. If you were to call `bb build`, Babashka would be able to determine which of the other two tasks needed to be run and only run them when necessary.


Parallel task execution will have Babashka running multiple tasks at the same time. In our build example, `bb build` could run `build-backend` and `build-frontend` at the same time, which could be a real time saver.




## Summary 



-   You define tasks in `bb.edn` under the `:tasks` key

-   Task definitions are key-value pairs where the key is a symbol naming the task, and the value is a Clojure expression

-   Add a `:requires` key under the `:tasks` key to require namespaces

-   `exec` executes functions as if invoked with `bb -x journal.add/add-entry`; it parses command line args before passing to the function

-   You can declare task dependencies

-   You can run tasks in parallel




# Additional Resources 


-   [Bash and Babashka equivalents](https://github.com/babashka/babashka/wiki/Bash-and-Babashka-equivalents) is indispensable for transferring your Bash knowledge to Babashka

# Acknowledgments 


The following people read drafts of this and gave feedback. Thank you!



-   Michiel Borkent \@borkdude

-   Marcela Poffalo

-   Gabriel Horner \@cldwalker

-   \@geraldodev

-   Andrew Patrick \@Ajpatri

-   Alex Gravem \@kartesus

-   Inge Solvoll \@ingesol

-   \@focaskater

-   @[[\[email protected\]]{.__cf_email__ cfemail="14797b7a7f716d2554727b6767607b707b7a3a7b6673"}](/cdn-cgi/l/email-protection#91fcfefffaf4e8a0d1f7fee2e2e5fef5feffbffee3f6)

-   Kira McLean


# Feedback 


If you have feedback, please open an issue at <https://github.com/braveclojure/babooka>. I can't promise I'll respond in a timely manner, or even at all, so I apologize in advice! I'm just not great at responding, it's one of my character flaws, but I appreciate the feedback!








[Follow \@nonrecursive](https://twitter.com/nonrecursive)

1.  [Find Clojure jobs](https://jobs.braveclojure.com){target="_blank"}
2.  [Contribute to beginner-friendly open source projects](http://open-source.braveclojure.com){target="_blank"}



Chapter Sections


1.  [Sponsor](/quests/babooka/babooka/#Sponsor)
2.  [What is Babashka?](/quests/babooka/babooka/#What_is_Babashka_)
3.  [Why should you use it?](/quests/babooka/babooka/#Why_should_you_use_it_)
4.  [Installation](/quests/babooka/babooka/#Installation)
5.  [Babashka's output](/quests/babooka/babooka/#Babashka_s_output)
6.  [Namespace is optional](/quests/babooka/babooka/#Namespace_is_optional)
7.  [What about requiring other namespaces?](/quests/babooka/babooka/#What_about_requiring_other_namespaces_)
8.  [Make your script executable](/quests/babooka/babooka/#Make_your_script_executable)
9.  [Summary](/quests/babooka/babooka/#Summary)
10. [parsing options with babashka.cli](/quests/babooka/babooka/#parsing_options_with_babashka_cli)
11. [dispatching subcommands with babashka.cli](/quests/babooka/babooka/#dispatching_subcommands_with_babashka_cli)
12. [Summary](/quests/babooka/babooka/#Summary)
13. [File system structure](/quests/babooka/babooka/#File_system_structure)
14. [Namespaces](/quests/babooka/babooka/#Namespaces)
15. [Summary](/quests/babooka/babooka/#Summary)
16. [Pod usage](/quests/babooka/babooka/#Pod_usage)
17. [Pod implementation](/quests/babooka/babooka/#Pod_implementation)
18. [Summary](/quests/babooka/babooka/#Summary)
19. [Direct Expression Evaluation](/quests/babooka/babooka/#Direct_Expression_Evaluation)
20. [Invoking a Clojure function](/quests/babooka/babooka/#Invoking_a_Clojure_function)
21. [A basic task](/quests/babooka/babooka/#A_basic_task)
22. [How to require namespaces for tasks](/quests/babooka/babooka/#How_to_require_namespaces_for_tasks)
23. [Use exec to parse arguments and call a function](/quests/babooka/babooka/#Use_exec_to_parse_arguments_and_call_a_function)
24. [Task dependencies, parallel tasks, and more](/quests/babooka/babooka/#Task_dependencies__parallel_tasks__and_more)
25. [Summary](/quests/babooka/babooka/#Summary)











© 2023 Daniel Higginbotham



