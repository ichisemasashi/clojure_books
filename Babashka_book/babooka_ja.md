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




# ファイルを扱う 


シェルスクリプトはしばしばコマンドラインから入力を読み込み、どこかで出力を生成する必要がありますが、今回の夢日記ユーティリティも例外ではありません。エントリーは `entries.edn` というファイルに保存されます。日記はベクターになり、各エントリーは `:timestamp` と `:entry` をキーとするマップになります（読みやすくするためにエントリーは改行されます）：



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




日記に書き込むには、コマンド `./journal add --entry "Hamsters. Hamsters everywhere. Again"`.その結果、ベクターにマップが追加される。



では早速やってみよう。ファイル `journal` を作成し、`chmod +x journal` で実行可能な状態にする、そして次のようにする：




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




ここでは、`babashka.fs`と`clojure.edn`の2つの名前空間が必要である。 `babashka.fs` はファイルシステムを操作するための関数群で、[API docs](https://github.com/babashka/fs) を参照してほしい。 シェルスクリプトを書くときには、ファイルシステムを扱うことが多いので、この名前空間はとても便利です。


なぜなら `slurp` は渡されたパスのファイルが見つからないと例外を投げるから、その前に `entries.edn` が存在するかどうかをチェックするために `fs/exists?` 関数を使用している。


関数 `add-entry` は `read-entries` を使用してエントリのベクトルを取得し、`conj` を使用してエントリを追加し、`spit` を使用して `entries.edn` に書き戻す。デフォルトでは `spit` はファイルを上書きする；もし追加したい場合は次のように呼び出す。




``` {.pygments .highlight}
(spit "entries.edn" {:timestap 0 :entry ""} :append true)
```




ファイル全体を上書きするのは少し汚いかもしれないが、これがスクリプトの世界なのだ！


# スクリプトのインターフェースを作る 


さて、最後の行では `(add-entry (first *command-line-args*))` を呼び出している。`*command-line-args*`は、スクリプトに渡されたすべてのコマンドライン引数を含むシーケンスである。もし`*command-line-args*`という内容で`args.clj`というファイルを作成し、`bb args.clj 1 2 3`と実行すると、`("1" "2" "3")`と表示される。


我々の `journal` ファイルは、`./journal “Flying\!\! But to Home Depot??"`を呼び出すことでエントリーを追加できる。これはほぼ我々が望んでいることである ；実際には、`./journal add --entry “Flying\!\! But to Home Depot??"`を呼びたい。ここでの前提は、`./journal list`や`./journal delete`のような他のコマンドも欲しいということだ。(感嘆符をエスケープしないと、bashはこれらをヒストリーコマンドとして解釈してしまう)。



これを実現するには、コマンドライン引数をより洗練された方法で処理する必要がある。最も簡単でわかりやすい方法は、`*command-line-args*`の第一引数をディスパッチすることである：



``` {.pygments .highlight}
(let [[command _ entry] *command-line-args*]
  (case command
    "add" (add-entry entry)))
```




このような使い方でも問題ないかもしれないが、もっと堅牢なものが必要な場合もある。次のようなスクリプトが必要かもしれない：



- 有効なコマンドをリストアップする。

- ユーザが存在しないコマンドを呼び出したときに、適切なエラーメッセージを表示する (例: `./journal add` ではなく `./journal add-dream` を呼び出した場合)

- 引数を解析し、オプションフラグを認識し、値をキーワード、数値、ベクター、マップなどに変換する。



一般的に言えば、**スクリプトのインターフェイスを定義するための明確で一貫性のある方法** が必要です。このインターフェイスは、コマンドラインで提供されたデータ --- スクリプトに渡された引数、および `stdin` を通してパイプインされたデータ --- を受け取り、そのデータを使用してこれら3つの役割を処理します：


- Clojure関数へのディスパッチ

- コマンドライン引数をClojureデータに解析し、それをディスパッチされた関数に渡す

- 上記の役割を果たすのに問題がある場合、フィードバックを提供する。



広範なClojureエコシステムは、引数解析を処理するための少なくとも2つのライブラリを提供します：



- [clojure.tools.cli](https://github.com/clojure/tools.cli)

- [nubank/docopt.clj](https://github.com/nubank/docopt.clj)



Babashkaは[babashka.cliライブラリ](https://github.com/babashka/cli)をオプションの解析とサブコマンドのディスパッチの両方に提供しています。ここでは、babashka. cliに絞って説明します。


## babashka.cliでオプションを解析する 



[babashka.cliドキュメント](https://github.com/babashka/cli)は、コマンドライン解析のニーズを満たすライブラリの使い方をよく説明している。すべてのオプションを説明するのではなく、私たちの夢日記を作るために必要なものだけに焦点を当てます。オプションを解析するには、`babashka.cli`名前空間が必要で、*CLI spec*を定義します：



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




CLI spec はマップであり、各キーはキーワードで、各値は *option spec* である。このキーはオプションの *長い名前* で、`:entry` はコマンドラインのフラグ `--entry` に対応する。


option spec はオプションをより詳細に設定するためのマップである。 `:alias` はオプションの *短い名前* を指定するもので、例えばコマンドラインで `--entry` の代わりに `-e` を使うことができる。`:desc` はインターフェースの概要を作成するために使用し、 `:require` はオプションの存在を必須にするために使用する。`:coerce` はオプションの値を他のデータ型に変換するために使用する。



このCLI specをREPLで試すことができる。Babashka REPLを起動するためのオプションはたくさんあるが、一番簡単なのはコマンドラインで`bb repl`と入力することだ。CIDERを使いたい場合は、まず`bb.edn`ファイルを追加し、その中に空のマップ`{}`を置く。それから`cider-jack-in`を使う。その後、上のスニペットのコードを貼り付け、次に以下のスニペットを貼り付ける：




``` {.pygments .highlight}
(cli/parse-opts ["-e" "The more I mowed, the higher the grass got :("] {:spec cli-opts})
;; =>
{:entry "The more I mowed, the higher the grass got :("}
```




`cli/parse-opts`はパースされたオプションをマップとして返すので、後で簡単にオプションを使うことができる。



必須のフラグを省略すると例外がスローされる：




``` {.pygments .highlight}
(cli/parse-opts [] {:spec cli-opts})
;; exception gets thrown, this gets printed:
: Required option: :entry user
```




`cli/parse-opts` はシンプルなスクリプトのインターフェースを構築するための素晴らしいツールである！`cli/format-opts` を使えば、そのインターフェイスを外部とやり取りすることができる。この関数は option spec を受け取って文字列を返す。ご覧あれ：




``` {.pygments .highlight}
(println (cli/format-opts {:spec cli-opts}))
;; =>
-e, --entry     Your dreams.
-t, --timestamp A unix timestamp, when you recorded this.
```






## babashka.cliでサブコマンドをディスパッチする 



babashka.cliはオプションのパースだけでなく、サブコマンドをディスパッチする方法も提供している、まさに`./journal add --entry "..."`を動作させたいのだ。これが `journal` の最終バージョンだ：




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




お使いの端末で次のように試してみてください：




``` {.pygments .highlight}
./journal
./journal add -e "dreamt they did one more episode of Firefly, and I was in it"
```




一番下の関数 `cli/dispatch` はディスパッチテーブルを最初の引数として受け取る。`cli/dispatch`はコマンドラインで渡された引数のどれがコマンドに対応するかを判断し、対応する `:fn` を呼び出します。また `./journal add ...` と入力すると、`add-entry` 関数をディスパッチする。もし引数なしで `./journal` とタイプすると、 `help` 関数がディスパッチされます。



ディスパッチされた関数は引数としてマップを受け取り、そのマップには `:opts` キーが含まれる。これは解析されたコマンドラインオプションのマップであり、それを使って `add-entry` 関数で夢日記を作成する。



これがスクリプトのインターフェイスを構築する方法だ！





## まとめ 



- どんな複雑なスクリプトでも、コマンドラインオプションをClojureデータ構造に*パース*する必要があります。

- ライブラリ `clojure.tools.cli` と `nubank/docopts` は、コマンドライン引数をオプションにパースしてくれます。

- このライブラリはサブコマンドのディスパッチも行うので、私は `babashka.cli` を使用することを好みますが、これは好みの問題です。

-`cli/parse-opts` は *options spec* を受け取り、マップを返す。

- `cli/format-opts` はヘルプテキストを作成するのに便利である。

- 例えば `journal add` の `add` のように、あなたのスクリプトは *サブコマンド* を提供するかもしれないので、 `cli/dispatch` を使ってコマンドライン引数をスクリプト内の適切な関数にマップする必要がある。




# プロジェクトを管理する 


潜在意識の毎晩の即興ルーティンを記録できるようになった。素晴らしいことだ！この達成感から、あなたはさらにステップアップして、エントリーをリストアップする機能を追加することにした。そして、`./journal list`を実行し、スクリプトが次のようなものを返すようにしたい：




``` {.pygments .highlight}
2022-12-07 08:03am
There were two versions of me, and one version baked the other into a pie and ate it.
Feeling both proud and disturbed.

2022-12-06 07:43am
Was on a boat, but the boat was powered by cucumber sandwiches, and I had to keep
making those sandwiches so I wouldn't get stranded at sea.
```




ソースファイルの長さはせいぜい25行程度にすべきだとどこかで読んだので、コードベースを分割して、このリスト機能を独自のファイルに置きたいと考えた。どうすればいいのでしょう？



他のClojureプロジェクトと同じように、コードベースを別々のファイルに分割し、それぞれのファイルで名前空間を定義し、名前空間をファイル名に対応させることで、Babashkaプロジェクトを管理することができます。 現在のコードベースを少し再編成して、すべてがまだ動作することを確認してから、一覧を表示するための名前空間を追加してみましょう。


## ファイルシステムの構造 



夢日記プロジェクトを整理する一つの方法は、次のようなファイル構造を作ることだろう：




``` {.pygments .highlight}
./journal
./src/journal/add.clj
./src/journal/utils.clj
```




すでに、これが典型的なClojureプロジェクトのファイルの構造に似ていると同時に、少し異なっていることがわかるでしょう。`src/journal` ディレクトリに名前空間を配置していますが、これはJVMやClojureScriptプロジェクトで見られるものと同じです。私たちのBabashkaプロジェクトにおいて異なっているのは、プログラムの実行可能なエントリーポイントとして、`./src/journal/core.clj`などを使用するのではなく、`./journal`を使用していることです。これは少し奇妙に感じるかもしれませんが、妥当であり、依然としてClojureなのです。


他のClojure環境と同じように、名前空間が必要な場合は`src`ディレクトリを探すようにBabashkaに指示する必要があります。そのためには、`journal`と同じディレクトリに`bb.edn`というファイルを作成し、このように記述します：



``` {.pygments .highlight}
{:paths ["src"]}
```




`bb.edn`は`deps.edn`ファイルと似ていて、クラスパスの構築方法をBabashkaに指示します。 クラスパスとは、名前空間をrequireするときにBabashkaが参照するディレクトリのことで、これに`"src"`を追加することで、プロジェクト内で`(require '[journal.add])`を使うことができます。Babashkaは対応するファイルを見つけることができます。


なお、`"src"`ディレクトリは特別なものではありません。必要であれば `"my-code"` や `"."` を使うこともできますし、複数のパスを追加することもできます。`"src"` は、世界中の目の肥えた Clojurian が好む慣例に過ぎません。


これで、`journal`を次のように更新する：




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




これで、このファイルはコマンドライン引数のパースと正しい関数へのディスパッチだけを担当するようになった。追加機能は別の名前空間に移された。





## 名前空間 



4行目で、`journal.add`という新しい名前空間が必要であることがわかる。この名前空間に対応するファイルは `./src/journal/add.clj` です。これがそのファイルだ：




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




これは名前空間宣言だ！そして、この名前空間宣言には `(:require ...)` という形式があります。Babashkaスクリプトを書くとき、`journal`のオリジナルバージョンのように、すべてのコードが1つのファイルにまとまっていれば、名前空間宣言を省略できることは知っています。しかし、コードを複数のファイルに分割し始めると、通常のClojureプロジェクト構成のルールが適用されます：



- 名前空間 の名前はファイルシステムのパスに対応しなければなりません。名前空間に `journal.add` という名前を付けたい場合、Babashkaは `journal/add.clj` にある名前空間を見つけられなければなりません。

- 名前空間に対応するファイルを探すには、Babashkaに場所を教える必要があります。そのためには、`bb.edn`ファイルを作成し、その中に`{:paths ["src"]}`と記述します。


最後に、新しいプロジェクト構成について説明するために、`./src/journal/utils.clj`をご覧ください：




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




`./journal add -e "visited by the tooth fairy, except he was a balding 45-year-old man with a potbelly from Brooklyn"`を呼び出しても動作するはずだ。



では、`journal.list`ネームスペースを作ってみよう。まず、`src/journal/list.clj`ファイルを開き、次のように記述する：




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




これはタイムスタンプをフォーマットしないが、それ以外は私たちが望むように、逆時系列順にエントリーをリストアップしてくれる。やった！



最後に、`journal`ファイルのディスパッチテーブルに`journal.list/list-entries`を追加する必要がある。このファイルはこのようになるはずだ：




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






## まとめ 



- 名前空間はJVM ClojureやClojurescriptと同じように動作する：名前空間名はファイルシステム構造に対応しなければならない。

- `bb.edn`に`{:paths ["src"]}`というマップを入れて、名前空間のファイルがどこにあるかをBabashkaに伝える。




# 依存関係の追加 


プロジェクトに依存関係を追加するには、`bb.edn` ファイルに `:deps` キーを追加します：




``` {.pygments .highlight}
{:paths ["src"]
 :deps {medley/medley {:mvn/version "1.3.0"}}}
```




しかし、Babashkaのクールなところは、スクリプトに直接depを追加したり、あるいはreplにdepを追加することもできることだ：




``` {.pygments .highlight}
(require '[babashka.deps :as deps])
(deps/add-deps '{:deps {medley/medley {:mvn/version "1.3.0"}}})
```




これはスクリプト言語の性質に沿ったものであり、迅速で堅苦しくない開発を可能にするものです。



この時点で、Babashkaを使って独自のClojureシェル・スクリプトを書き始めるための準備が完全に整っているはずです。うっほー！



この後のセクションでは、すぐに必要ではないかもしれないが、Clojureスクリプティングへの愛が大きくなり、それがすべてを覆い尽くすまでになったときに役に立つBabashkaの側面を取り上げます。

# Pods 


Babashka *pods*は、Clojure関数を呼び出すことで外部プロセスと対話する方法を導入しており、Clojureアプリケーションの外部で実行されているプロセスで作業しているときでも、またそのプロセスが他の言語で書かれているときでも、Clojureのように見え、そう感じるコードを書くことができます。



## Podの使い方 



より具体的にどういうことか見てみよう。夢日記を暗号化したいとします。あなたは[stash](https://github.com/rorokimdim/stash)という、"テキストデータを暗号化して保存するためのコマンドライン・プログラム "を見つけたとします。これはまさにあなたが必要としているものです！ただし、これはHaskellで書かれていて、しかもコマンドライン・インターフェイスというよりむしろ*ターミナル・ユーザー・インターフェイス*(TUI)を持っている。


つまり、コマンドラインから `stash` を実行すると、ターミナルに ascii インターフェースが描画されます ので、テキストを保存するには追加の入力を行う必要があります。次のようなコマンドラインから直接テキストを保存することはできません。




``` {.pygments .highlight}
stash store dreams.stash \
      --key 20221210092035 \
      --value "was worried that something was wrong with the house's foundation,
               then the whole thing fell into a sinkhole that kept growing until
               it swallowed the whole neighborhood"
```




もしそれが可能であれば、`babashka.process/shell`関数を使うことで、Bashkaプロジェクトの中から`stash`を使うことができる：




``` {.pygments .highlight}
(require '[babashka.process :as bp])
(bp/shell "stash store dreams.stash --key 20221210092035 --value \"...\"")
```




`bp/shell`はプログラムのコマンドラインインターフェイスを利用できるものだが、やはり`stash`はそれを提供していない。



しかし、`stash`は*podインターフェイス*を提供するので、Clojureファイルでこのように使うことができる：




``` {.pygments .highlight}
(require '[babashka.pods :as pods])
(pods/load-pod 'rorokimdim/stash "0.3.1")
(require '[pod.rorokimdim.stash :as stash])

(stash/init {"encryption-key" "foo"
             "stash-path" "foo.stash"
             "create-stash-if-missing" true})

(stash/set 20221210092035 "dream entry")
```




最後の行、`(stash/set 20221210092035 "dream entry")`から始めましょう。これがPodのポイントです: 外部プロセスのコマンドをClojure関数として公開します。Podは、これらのプロセスが*Clojureインターフェイス*を持つことを可能にするので、Clojureコードを書くことで、シェルアウトしたりHTTPコールをしたりするのとは対照的に、これらのプロセスと対話することができます。


次のセクションでは、上のスニペットの残りを説明します。





## Podの実装 



関数 `stash/set` はどこから来るのか？名前空間 `pod.rorokimdim.stash` とその中の関数は `(pods/load-pod 'rorokimdim/stash "0.3.1")` の呼び出しによって動的に生成されます。


これを可能にするためには、外部プログラムが *pod protocol* をサポートするように書かれている必要があります。ここでの「プロトコル」はClojureプロトコルを指すのではなく、情報を交換するための標準を指します。 Clojureアプリケーションと外部アプリケーションは、同じプロセス内に存在せず、異なる言語で書かれている可能性もあるため、互いに通信する何らかの方法が必要です。


Pod protocolを実装することで、プログラムはPodになります。そうすることで、*クライアント*であるClojureアプリケーションに、利用可能な名前空間と関数を伝えることができるようになります。それらの関数をクライアント・アプリケーションが呼び出すと、データをエンコードしてメッセージとしてpodに送信します。Podは、それらのメッセージを受信し、デコードし、必要なコマンドを内部で実行し、応答メッセージをクライアントに送信できるように作成されます。


Podプロトコルは[the pod GitHub repo](https://github.com/babashka/pods)に文書化されている。





## まとめ 



- Babashkaのpodシステムを使うと、`babashka.process/shell`でシェルアウトしたり、HTTPリクエストしたりするのではなく、Clojure関数を使って外部プロセスとやりとりすることができます。

- これらの外部プロセスは *pods* と呼ばれ、クライアントプログラムに対話方法を伝えるために *pod protocol* を実装する必要があります。




# コードを実行する他の方法 


このチュートリアルでは、典型的なbashスクリプトと同じように操作できるスタンドアローンのスクリプトを作ることに焦点をあててきました。`chmod +x` で実行可能にして、`./journal add -e "dream entry"` のようにコマンドラインから呼び出します。



bashがサポートしているシェルスクリプトには、他にもいろいろな（言葉は悪いが）フレーバーがある：



- 式の直接評価

- Clojure関数の呼び出し

- タスクの命名



## 式の直接評価 



BabashkaにClojure式を与えると、それを評価して結果を表示します：




``` {.pygments .highlight}
$ bb -e '(+ 1 2 3)'
9

$ bb -e '(map inc [1 2 3])'
(2 3 4)
```




個人的にはあまり使ったことはないが、必要であれば使える！





## Clojure 関数の呼び出し 



`journal.add/add-entry`関数を直接呼び出したければ、こうすればいい：




``` {.pygments .highlight}
bb -x journal.add/add-entry --entry "dreamt of foo"
```




`bb -x` を使うと、関数の完全修飾名を指定でき、Babashka がその関数を呼び出します。`babashka.cli` を使ってコマンドライン引数をClojure値にパースし、指定した関数に渡します。詳しくは[Babashkaドキュメントの-xセクション](https://book.babashka.org/#_x)を参照してください。



また、`bb -m some-namespace/some-function` を使って関数を呼び出すこともできます。bb -x` との違いは、`bb -m` では、各コマンドライン引数はパースされずにClojure関数に渡される。 例えば




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




`bb -m` を使うと、名前空間を渡すだけで、Babashka がその名前空間の `-main` 関数を呼び出してくれる。例えば、`journal.add` 名前空間をこのような呼び出し方で動作させたい場合は、次のように書く：




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




そして、こんなこともできる：




``` {.pygments .highlight}
$ bb -m journal.add "recurring foo dream"
```




ただし、`bb -x` または `bb -m` が動作するためには、`bb.edn` ファイルをセットアップして、呼び出すネームスペースがクラスパスで到達できるようにする必要がある。




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




© 2023 Daniel Higginbotham



