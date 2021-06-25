


Shadow CLJS User’s Guide

[原文サイト](https://shadow-cljs.github.io/docs/UsersGuide.html)

# 1. Introduction

`shadow-cljs` は、あなたの ClojureScript プロジェクトをコンパイルするために必要なすべてを、シンプルさと使いやすさに重点を置いて提供します。提供されているビルドターゲットは、手動による設定のほとんどを抽象化し、ビルドに必要なものだけを設定することができます。各ターゲットはそれぞれの環境に最適なデフォルトを提供し、開発時やリリースビルド時に最適なエクスペリエンスを得ることができます。

## 1.1. High-Level Overview

`shadow-cljs`は2つの部分で構成されています。

- 実際の作業をすべて処理する shadow-cljs Clojure ライブラリです。
- shadow-cljs `npm` パッケージは、ビルド機能のほとんどをコマンドラインから直接実行するための便利なインターフェイスを提供します。

必要であれば、`shadow-cljs` Clojure ライブラリを他の Clojure/JVM ビルドツール (例: leiningen や Clojure CLI ツール) に簡単に統合することができます。

`npm` パッケージを使用することをお勧めします。`npm` パッケージは、CLJS 開発に合わせてより最適化された開発エクスペリエンスを提供します。

## 1.2. Basic Workflow

`shadow-cljs` を使用する際には、`shadow-cljs.edn` 設定ファイルで 1 つまたは複数のビルドを定義します。各ビルドは、ターゲット環境（例：ブラウザ、node.js アプリケーション、または Chrome 拡張機能）に最適化された構成プリセットを表す `:target` プロパティを持ちます。

各ビルドは、コンパイルのトリガーとなったコマンドに応じて、開発用またはリリース用の出力を生成します。標準的なビルドコマンドは、`compile`、`watch`、`release`です。

### 1.2.1. Development Mode

開発ビルドは、一度コンパイルするか、ソースファイルを監視して自動的に再コンパイルする（必要に応じてコードをライブリロードする）ウォッチプロセスを実行することができます。

すべての開発ビルドは、迅速なフィードバックサイクルや、実行中のコードを直接操作するためのREPLなどの機能を備え、開発者の経験に合わせて最適化されています。

開発ビルドは非常に大きくなる可能性があり、また `:target` に応じてコンパイルされたマシンでしか動作しない可能性があるため、決して一般に出荷してはいけません。

### 1.2.2. Release Mode

リリース・ビルドを作成すると、開発関連のコードがすべて取り除かれ、最後にClosure Compilerにコードを通します。これはJavaScript用の最適化コンパイラで、コードの全体的なサイズを大幅に削減します。

## 1.3. Important Concepts

shadow-cljs を使用する際には、いくつかの重要なコンセプトを理解する必要があります。これらは、すべてがどのように組み合わされるのか、また、このツールがあなたのコードとどのように連携するのかを理解するために不可欠です。

### 1.3.1. The Classpath

shadow-cljs は、ファイルを扱う際に、Java Virtual Machine (JVM) とその "classpath" を使用します。これは、多くのクラスパス・エントリで構成される仮想ファイルシステムです。各エントリは次のいずれかです。

- ローカルファイルシステムのディレクトリで、設定の `:source-paths` エントリで管理されます。
- あるいは、Clojure(Script)やJVMのライブラリを表す、`.jar`ファイルです。これらは、多くのファイルを含む圧縮されたアーカイブです（基本的には、単なる.zipファイル）。これらは、`:dependencies`によって追加されます。

Clojure(Script)では、すべてが名前付きで、それぞれの名前がファイルに解決することが期待されています。もし`(ns demo.app)`という名前空間があれば、コンパイラはクラスパス上に`demo/app.cljs`(または.cljc)があることを期待します。クラスパスは、それが見つかるまで順番に検索されます。例えば、`:source-paths ["src/main" "src/test"]`を設定したとすると、コンパイラはまず`src/main/demo/app.cljs`を探し、次に`src/test/demo/app.cljs`を探します。どのソースパスにもファイルが見つからない場合、JVMはクラスパス上の.jarファイルを探し始めます。そして、いずれかのライブラリのルートに「demo/app.cljs」が見つかると、そのファイルが使用されます。

> 重要事項
> ファイル名がクラスパス上に複数回存在する場合、最初のものだけが使用されます。JVMとClojure(Script)上のすべてのものは、このような衝突を避けるために名前空間が設けられています。各パッケージが一意の名前を持たなければならないnpmと非常によく似ています。

そのため、名前の選択には細心の注意を払い、すべてのものに適切なネームスペースを設けることをお勧めします。(ns components.foo)`よりも`(ns your-company.components.foo)`を常に使用することは反復的に見えるかもしれませんが、後で多くの頭痛の種から解放されるでしょう。

これはnpmとは異なり、パッケージ名自体はパッケージ内では使用されず、相対パスのみが使用されます。

### 1.3.2. Server Mode

shadow-cljs は "server" モードで起動することができ、これは `watch` のような長時間実行するタスクに必要です。これは、`watch` のような長時間稼働するタスクに必要です。`watch` は、サーバーインスタンスがまだ起動していなければ、暗黙のうちに起動します。サーバは、nREPL、Socket REPL、および開発用 HTTP サーバの他のすべてのエンドポイントと同様に、ビルドが接続する Websocket エンドポイントを提供します。

shadow-cljs CLI を使用する場合、すべてのコマンドは、新しい JVM を起動する代わりに、実行中のサーバー・インスタンス JVM を再利用します。これは、起動時間がかなり遅くなることがあるため、実質的に高速です。

しかし、いったんサーバが動作してしまえば、`:dependencies` が変更されたときに再起動するだけでよく、他のことはすべて REPL を介して行うことができます。

### 1.3.3. REPL

REPLは、すべてのClojure(Script)開発の中心であり、すべてのCLIコマンドはREPLから直接使用することもできます。コマンドラインの方が馴染みがあるように見えても、REPLを使いこなすことは絶対に価値があります。

## 1.4. About this Book

### 1.4.1. Work in Progress

これは現在進行中の作品です。エラーを発見した場合は、それを修正するためのPR、または問題の詳細を記載したissueを提出してください。

### 1.4.2. Contributing

この本のソースは[Github](https://github.com/shadow-cljs/shadow-cljs.github.io)でホストされています。

### 1.4.3. Conventions Used

この本にはたくさんの例題があります。ここで使われているほとんどのものは、文脈から明らかなはずですが、誤解を防ぐためには、著者の意図を知ることが重要です。

コマンドラインの例を示すときには、BASHのコメント（#で始まる）を含めることがあります。また、通常は、コマンドとその出力が分離されていることを示すために、標準的なユーザーのUNIXプロンプトである$を含めます。

```bash
# A comment. This command lists files:
$ ls -l
shadow-cljs.edn
project.clj
...
```

例題の多くは、コンパイラの設定ファイルのものです。このファイルには、EDNマップが含まれています。必要なオプションについてすでに説明している場合は、わかりやすくするために省略することがあります。この場合、「必要だが、現在の焦点ではない内容」を示す省略記号を入れるのが普通です。


Example 1. Specify dependencies
```Clojure
{:dependencies [[lib "1.0"]]}
```


Example 2. Add source paths
```Clojure
{...
 :source-paths ["src"]
 ...}
```

これにより、対象となる構成の入れ子を理解するのに十分なコンテキストを簡潔に含めることができます。


Example 3. Nested option
```Clojure
{...
 :builds {:build-id {...
                     :output-dir "resources/public/js"}}}
```

コード例も同様に短くすることができます。


# 2. Installation

## 2.1. Standalone via `npm`

以下が必要となります。

- `node.js` (v6.0.0+, 最新バージョンが望ましい)
- `npm`または`yarn`
- 任意のJava SDK（バージョン8以上）。OpenJDKまたはOracle

プロジェクトのディレクトリには、`package.json`が必要です。まだ持っていない場合は、`npm init -y`を実行して作成できます。まだプロジェクトディレクトリがない場合は、次のようにして作成します。

```bash
$ npx create-cljs-project my-project
```

これで必要な基本ファイルがすべて作成されるので、以下のコマンドは省略できます。

すでに `package.json` があり、shadow-cljs を追加したいだけの場合は、次のコマンドを実行してください。

NPM
```bash
$ npm install -save-dev shadow-cljs
```

Yarn
```bash
$ yarn add --dev shadow-cljs
```

便宜上、`npm install -g shadow-cljs` または `yarn global add shadow-cljs` を実行することができます。これにより、後で shadow-cljs コマンドを直接実行できるようになります。あなたのプロジェクトには常に shadow-cljs の最新バージョンがインストールされているべきで、グローバルインストールはオプションです。

## 2.2. Library

npm を通してスタンドアロン版を実行することが推奨されますが、他の Clojure JVM ツール（例：lein、boot、...）に shadow-cljs を組み込むこともできます。

アーティファクトは以下の場所にあります。

[shadow-cljs.svg](imgs/shadow-cljs.svg)

[shadow-cljs2.svg](imgs/shadow-cljs2.svg)

# 3. Usage

shadow-cljs は多くの異なる方法で使用することができますが、一般的なワークフローは同じままです。

開発中には、ビルドを一度「コンパイル」するか、ソースファイルの変更を監視して自動的に再コンパイルする`watch`ワーカーを実行するかのオプションがあります。このワーカーはソースファイルの変更を監視し、自動的に再コンパイルします。このワーカーを有効にすると、コードをホットリロードし、REPLを提供します。開発時には、迅速なフィードバックサイクルによる開発者の経験を重視しています。開発コードは決して一般に出荷してはいけません。

本格的に開発を行う場合は、生産に適した最適化されたビルドを作成する `release` を作成します。これにはClosure Compilerが使われます。これは、利用可能な最も最適な出力を作成するために、コードに深刻な `:advanced` 最適化を適用します。ネイティブJavaScriptとのインターロップを多用する場合には、適切に動作させるためのチューニングが必要になるかもしれませんが、ClojureScript（およびClosure Libraryのコード）では完璧に動作しています。

## 3.1. Command Line

グローバルにインストールされている場合は、shadow-cljsコマンドを直接使用することができます。

```bash
$ shadow-cljs help
```

ローカルにインストールしたnpmのみを使用したい場合は、npxやyarnを介して起動することができます。

```bash
# npm
$ npx shadow-cljs help

# yarn
$ yarn shadow-cljs help

# manually
$ ./node_modules/.bin/shadow-cljs help
```

このガイドでは、例を短くするためにグローバルインストールを前提としていますが、これは必須ではありません。

開発時によく使われるshadow-cljsコマンド
```bash
# compile a build once and exit
$ shadow-cljs compile app

# compile and watch
$ shadow-cljs watch app

# connect to REPL for the build (available while watch is running)
$ shadow-cljs cljs-repl app

# connect to standalone node repl
$ shadow-cljs node-repl
```

本番使用に最適化されたリリースビルドを実行します。

```bash
$ shadow-cljs release app
```

時には、`:advanced`のコンパイルが原因で、リリース上の問題に遭遇することがあります。これらのコマンドは、その原因を追跡するのに役立ちます。

リリースのデバッグコマンドです。
```bash
$ shadow-cljs check app
$ shadow-cljs release app --debug
```

### 3.1.1. Server Mode

shadow-cljs コマンドは、起動にかなり時間がかかります。これを改善するために、shadow-cljs は "サーバーモード" で動作させることができます。これは、新しい JVM/Clojure インスタンスを起動する必要がないため、他のすべてのコマンドがより速く実行するために使用できる専用のプロセスが開始されることを意味します。

長時間実行される処理を行うコマンドは、暗黙的にサーバーインスタンスを起動しますが（例：watch）、専用のサーバープロセスを起動することが望ましい場合もあります。

専用のターミナルでフォアグラウンドでプロセスを実行することができます。サーバを終了させるには `CTRL+C` を使います。

```bash
$ shadow-cljs server

# or (if you'd like REPL to control the server process)
$ shadow-cljs clj-repl
```

また、一般的な`start`|`stop`|`restart`関数を使って、バックグランドでサーバーを制御して動かすこともできます。

```bash
$ shadow-cljs start
$ shadow-cljs stop
$ shadow-cljs restart
```

いずれかのサーバーが稼働していれば、他のすべてのコマンドはそのサーバーを使用し、より高速に動作します。

## 3.2. Build Tool Integration

`shadow-cljs` は他の Clojure ツールと統合することができます。なぜなら、主要な配布物は Clojars を通して利用可能な `.jar` ファイルだけだからです。デフォルトでは、あなたの `:dependencies` は `shadow-cljs.edn` を通して管理されますが、あなたは依存関係を管理するために他のビルドツールを使用することもできます。

> 注意
> スタンドアロン版の shadow-cljs を使用することを強くお勧めします。このコマンドは、ユーザーエクスペリエンスを最適化するために、 他のツールでは行われない多くのことを行います (例えば、起動の高速化など)。また、依存関係の衝突やその他の関連するエラーに対処するための多くの頭痛の種を自分自身で取り除くことができるでしょう。

### 3.2.1. Leiningen

依存関係の管理に Leiningen を使用したい場合は、`shadow-cljs.edn` 設定に `:lein` エントリを追加することで可能になります。この設定により、`shadow-cljs` コマンドは JVM を起動する際に `lein` を使用し、`shadow-cljs.edn` 内の `:source-paths` や `:dependencies` は一切無視し、代わりに `project.clj` から設定された `lein` を使用します。

```Clojure
{:lein true
 ; :source-paths and :dependencies are now ignored in this file
 ; configure them via project.clj
 :builds { ... }}
```

専用の `lein` プロファイルの使用
```Clojure
{:lein {:profile "+cljs"}
 :builds {...}}
```

Sample project.clj
```Clojure
(defproject my-awesome-project
  ...
  :profiles
  {:cljs
   {:source-paths ["src/cljs"]
    :dependencies [[thheller/shadow-cljs "..."]
                   [reagent "0.8.1"]]}})
```

project.clj` を使って `:dependencies` を管理する場合は、thheller/shadow-cljs アーティファクトを `:dependencies` に手動で（直接またはプロファイルで）含める必要があります。

> 重要
> `shadow-cljs` の起動時やコンパイル・ビルド時に奇妙な Java Stackstraces に遭遇した場合、依存関係の衝突が考えられます。`shadow-cljs` を使用する際には、適切なバージョンの `org.clojure/clojurescript` と `closure-compiler` を使用することが非常に重要です。これは、`lein deps :tree` で確認することができ、必要なバージョンは clojars (右側) にリストアップされています。

#### Running Tasks Directly From Leiningen

また、`shadow-cljs`コマンド自体を使いたくない場合は、`lein`を使って`shadow-cljs`コマンドを直接実行することもできます。

> 重要
> `shadow-cljs`コマンドを使用してコマンドを実行することをお勧めします。なぜなら、実行中のサーバーモードのインスタンスを最大限に活用できるからです。これにより、`lein` を直接使用して追加の JVM を起動するよりも大幅に速くコマンドを実行することができます。

REPLやライブリロードは不要で、`:dev`モードを一度コンパイルするだけ
```bash
$ lein run -m shadow.cljs.devtools.cli compile build-id
```

`release`モードに最適化されたビルドを作成します。
```bash
$ lein run -m shadow.cljs.devtools.cli release build-id
```

### 3.2.2. tools.deps / deps.edn

新しい deps.edn は、組み込みのメソッドや `lein` を使用する代わりに、`:dependencies` や `:source-paths` を管理するためにも使用できます。すべての shadow-cljs コマンドは、代わりに新しい clojure ユーティリティを介して起動されます。

> 重要な点
> `tools.deps` はまだ頻繁に変更されています。最新のバージョンを使用していることを確認してください。

これを使用するには、コンフィグで `:deps true` プロパティを設定します。また、どの `deps.edn` のエイリアスを使用するかを設定することもできます。

この場合、`thheller/shadow-cljs` というアーティファクトを手動で `deps.edn` に追加する必要があります。


Simple `shadow-cljs.edn` example
```Clojure
{:deps true
 :builds ...}
```

Simple `deps.edn` example
```Clojure
{:paths [...]
 :deps {thheller/shadow-cljs {:mvn/version <latest>}}}
```

Example `shadow-cljs.edn` with :cljs alias
```Clojure
{:deps {:aliases [:cljs]}
 :builds ...}
```

Example `deps.edn`
```Clojure
{:paths [...]
 :deps {...}
 :aliases
 {:cljs
  {:extra-deps {thheller/shadow-cljs {:mvn/version <latest>}}}}}
```

cljで直接実行します。

```Clojure
{:paths [...]
 :deps {...}
 :aliases
 {:shadow-cljs
  {:extra-deps {thheller/shadow-cljs {:mvn/version <latest>}}
   :main-opts ["-m" "shadow.cljs.devtools.cli"]}}}
```

```bash
clj -A:shadow-cljs watch app
```

また、`shadow-cljs -A:foo:bar ...`のように、コマンドラインで`-A`を使って追加のエイリアスを指定することもできます。


> 重要
> エイリアスは、新しいインスタンス/サーバを起動したときにのみ適用されます。`shadow-cljs` コマンドを使って稼働中のサーバーに接続する際には適用されません。`clj`で起動すると、常に新しいJVMが起動し、サーバーモードはサポートされません。

### 3.2.3. Boot

著者は、Boot の経験がほとんどないため、この章は貢献を必要としています。我々は、Boot では、関数からツールチェーンを構築できることを理解しています。shadow-cljs は通常の JVM ライブラリなので、タスクを呼び出すためにその中の関数を呼び出すことができます。

いくつかのブートタスクは、ここで利用可能です： https://github.com/jgdavey/boot-shadow-cljs

## 3.3. Running Clojure Code

コマンドラインから特定のClojure関数を呼び出すために、`shadow-cljs`というCLIを使うことができます。これは、あるタスクの前/後にいくつかのコードを実行したいときに便利です。例えば、`release`ビルドの出力をリモートサーバに`rsync`したいとします。

Example Clojure Namespace in `src/my/build.clj`
```Clojure
(ns my.build
  (:require
    [shadow.cljs.devtools.api :as shadow]
    [clojure.java.shell :refer (sh)]))

(defn release []
  (shadow/release :my-build)
  (sh "rsync" "-arzt" "path/to/output-dir" "my@server.com:some/path"))
```


Running the `release` function
```bash
$ shadow-cljs clj-run my.build/release
# or
$ shadow-cljs run my.build/release
```

呼び出された関数には、コマンドラインから引数を渡すことができます。


Using arguments via normal Clojure fn args
```Clojure
...
(defn release [server]
  (shadow/release :my-build)
  (sh "rsync" "-arzt" "path/to/output-dir" server))
```

Passing the server from the command line
```bash
$ shadow-cljs clj-run my.build/release my@server.com:some/path
```

> ヒント
> tools.cliのようなもので引数を解析したい場合は、通常の`(defn release [& args])`構造も機能します。

ここでは、Clojureのフルパワーを利用することができます。望むならば、この上にツール全体を構築することができます。おまけに、この方法で書いたものはすべて、Clojure REPLで直接利用できます。

> 重要事項
> サーバーが実行されているとき、名前空間は自動的に再読み込みされません、それは一度だけ読み込まれます。REPLを使用して開発を行い、通常通りファイルをリロードすることをお勧めします(例: `(require 'my.build :reload)`)。`shadow-cljs clj-eval "(require 'my.build :reload)"`を実行して、コマンドラインから手動でリロードすることもできます。

### 3.3.1. Calling watch via clj-run

デフォルトでは、`clj-run` から呼び出された関数は、`compile`, `release` やその他の Clojure 機能を実行するのに十分な、最小限の `shadow-cljs` ランタイムにしかアクセスできません。JVMは、あなたの関数が完了すると終了します。

特定のビルドに対して `watch` を開始したい場合は、呼び出している関数が完全なサーバーを必要としていることを宣言する必要があります。これにより、あなたが明示的に `(shadow.cljs.devtools.server/stop!)` を呼び出すか、`CTRL+C` でプロセスを終了させるまで、プロセスは生き続けることになります。

```Clojure
(ns demo.run
  (:require [shadow.cljs.devtools.api :as shadow]))

;; 完全なサーバー・インスタンスがないために失敗します。
(defn foo
  [& args]
  (shadow/watch :my-build))

;; このメタデータは、watchが動作するようにサーバーを起動することを保証するものです。
(defn foo
  {:shadow/requires-server true}
  [& args]
  (shadow/watch :my-build))
```

# 4. REPL

REPLは、Clojure(Script)コードを扱うときに持つべき非常に強力なツールです。 shadow-cljsは、あなたの標準的なビルドに統合されているバリアントと同様に、あなたがすぐに始めることができるいくつかの組み込みバリアントを提供します。

すぐにいくつかのコードをテストしたい場合は、組み込みの REPL で十分です。もし、自分自身で何かをするような、より複雑なセットアップが必要な場合には、実際のビルドを使用することが最善です。

## 4.1. ClojureScript REPL

デフォルトでは、`node-repl`と`browser-repl`のどちらかを選択できます。どちらも似たような動作をしますが、異なる点は、一方がマネージドな `node.js` プロセスで動作するのに対し、他方は実際のコードを評価するために使用されるブラウザウィンドウを開くことです。

### 4.1.1. Node REPL

```bash
$ shadow-cljs node-repl
```

これは、すでに接続されている `node` プロセスで、空の CLJS REPL を開始します。

> 重要
> Node REPLを終了すると、`node`プロセスもキルされます!

`node-repl`では、追加の設定をすることなく開始することができます。node-replは、通常の手段、すなわち `(require '[your.core :as x])` を通じて、あなたの全てのコードにアクセスします。ビルドに接続されていないので、ファイルが変更されてもコードの自動再構築は行われず、ホットリロードも提供しません。

### 4.1.2. Browser REPL

```bash
$ shadow-cljs browser-repl
```

これは空のCLJS REPLを起動し、コードが実行される関連するブラウザ・ウィンドウを開きます。ブラウザ上で実行されるだけでなく、上記の `node-repl` と同じ機能を持っています。

> 重要
> ブラウザ・ウィンドウを閉じると、REPLは動作しなくなります。

### 4.1.3. Build-specific REPL

`node-repl`と`browser-repl`は、特定のビルド構成なしに動作します。つまり、あなたが指示したことだけを実行しますが、それだけでは何もできません。

特定のものをビルドしたい場合は、提供されているビルドターゲットの1つを使ってビルドを設定する必要があります。ほとんどのターゲットは、ClojureScript REPLに必要なコードを自動的に注入します。追加の設定は必要ありません。ビルドCLJS REPLが動作するためには、次の2つが必要です。

1. ビルド用の実行中の `watch` 。
2. `:target`のJSランタイムに接続する。つまり、`:browser`ターゲットを使用している場合は、生成されたJSがロードされたブラウザを開く必要があります。node.jsのビルドの場合は、`node`プロセスを実行することになります。


両方が揃ったら、コマンドラインまたはClojure REPLからCLJS REPLに接続できます。


CLI
```bash
$ shadow-cljs watch build-id
...

# different terminal
$ shadow-cljs cljs-repl build-id
shadow-cljs - connected to server
[3:1]~cljs.user=>
```

REPL
```bash
$ shadow-cljs clj-repl
...
[2:0]~shadow.user=> (shadow/watch :browser)
[:browser] Configuring build.
[:browser] Compiling ...
[:browser] Build completed. (341 files, 1 compiled, 0 warnings, 3,19s)
:watching
[2:0]~shadow.user=> (shadow/repl :browser)
[2:1]~cljs.user=>
```

> ヒント
> REPLを終了するには、`:repl/quit`と入力してください。これは REPL を終了するだけで、`watch` は引き続き実行されます。

> ヒント
> 複数の `watch` 「ワーカー」を並行して実行し、いつでもそれらの REPL に接続/切断することができます。


No connected runtime error.
```Clojure
[3:1]~cljs.user=> (js/alert "foo")
There is no connected JS runtime.
```

この表示が出た場合は、ブラウザでアプリを開くか、`node`プロセスを開始する必要があります。

## 4.2. Clojure REPL

提供されている ClojureScript REPL に加えて、Clojure REPL も提供されています。これは、`shadow-cljs`プロセスを制御し、他のすべてのビルドコマンドを実行するために使用できます。Clojure REPLから始めて、いつでもCLJS REPLにアップグレードすることができます（そして元に戻すこともできます）。

Running from the CLI
```bash
$ shadow-cljs clj-repl
...
shadow-cljs - REPL - see (help), :repl/quit to exit
[1:0]~shadow.user=>
```

`shadow.cljs.devtools.api` 名前空間には、CLI とほぼ 1:1 で対応する関数があります。デフォルトでは `shadow` という名前でエイリアスされています。

Example commands
```Clojure
;; shadow-cljs watch foo
(shadow.cljs.devtools.api/watch :foo)
;; this is identical, due to the provided ns alias
(shadow/watch :foo)
;; shadow-cljs watch foo --verbose
(shadow/watch :foo {:verbose true})
;; shadow-cljs compile foo
(shadow/compile :foo)
;; shadow-cljs release foo
(shadow/release :foo)

;; shadow-cljs browser-repl
(shadow/browser-repl)
;; shadow-cljs node-repl
(shadow/node-repl)
;; shadow-cljs cljs-repl foo
(shadow/repl :foo)

;; Once you are in a CLJS REPL you can use
:repl/quit
;; or
:cljs/quit
;; to drop back down to CLJ.
```

### 4.2.1. Embedded

また、他の CLJ プロセスの中から完全に `shadow-cljs` を使用することも可能です。クラスパスに `thheller/shadow-cljs` がロードされていれば、問題ありません。

Example using lein repl
```Clojure
$ lein repl
nREPL server started on port 57098 on host 127.0.0.1 - nrepl://127.0.0.1:57098
REPL-y 0.4.3, nREPL 0.6.0
Clojure 1.10.0
...

user=> (require '[shadow.cljs.devtools.server :as server])
nil
user=> (server/start!)
...
:shadow.cljs.devtools.server/started
user=> (require '[shadow.cljs.devtools.api :as shadow])
nil
user=> (shadow/compile :foo)
...
```

`(shadow.cljs.devtools.server/stop!)`を実行することで、組み込みサーバを停止することができます。これにより、実行中のすべてのビルドプロセスも停止します。

> 重要事項
> CLJS REPL に切り替えたい場合は、サーバーの起動に使用したツールで追加の設定が必要になる場合があります。`lein` はデフォルトで nREPL を使用するので、追加の nREPL `:middleware` を設定する必要があります。`clj`を使用する場合は、nREPLを使用しないので、問題ありません。

# 5. Configuration

`shadow-cljs` の設定は、プロジェクトのルートディレクトリにある `shadow-cljs.edn` ファイルで行います。デフォルトのファイルは `shadow-cljs init` を実行することで作成することができます。このファイルには、いくつかのグローバルな設定を含むマップと、すべてのビルドのための `:builds` エントリが含まれています。

```Clojure
{:source-paths [...]
 :dependencies [...]
 :builds {...}}
 ```

 設定例は以下のようになります。

 ```Clojure
{:dependencies
 [[reagent "0.8.0-alpha2"]]

 :source-paths
 ["src"]

 :builds
 {:app {:target :browser
        :output-dir "public/js"
        :asset-path "/js"
        :modules {:main {:entries [my.app]}}}}}
 ```

この例のファイル構造は次のようになります。

```
.
├── package.json
├── shadow-cljs.edn
└── src
    └── my
        └── app.cljs
```

## 5.1. Source Paths

`:source-paths` は、JVMのクラスパスを設定します。コンパイラはこの設定を使って、Clojure(Script)のソースファイル(例:`.cljs`)を探します。

すべてのファイルを1つのソースパスに入れることは問題ありませんが、ソースファイルを特定の方法で「グループ化」したい場合は、複数のパスを使用することができます。例えば、テストを別々にしたい場合などに便利です。



Using multiple source paths
```Clojure
{:source-paths ["src/main" "src/test"]
 ...}
```


File Structure
```
.
├── package.json
├── shadow-cljs.edn
└── src
    └── main
        └── my
            └── app.cljs
    └── test
        └── my
            └── app_test.cljs
```

ソースファイルを拡張子で分けることはお勧めしません（例：`src/clj`, `src/cljs`, `src/cljc`）。なぜかCLJSプロジェクトのテンプレートではこの方法が広く使われていますが、かえって使いづらくなってしまいます。

## 5.2. Dependencies
### 5.2.1. Clojure(Script)

依存関係は、`shadow-cljs.edn` 設定ファイルのルートにある `:dependencies` キーで管理されます。依存関係は `lein` や `boot` のような他の Clojure ツールが使用するのと同じ記法で宣言されます。

それぞれの依存関係は `[library-name "version-string"]` を使ったベクターとして書かれ、1つの外側のベクターに入れ子になっています。


Example :dependencies
```Clojure
{:source-paths ["src"]
 :dependencies [[reagent "0.9.1"]]
 :builds ...}
```

ソースパスは設定全体で一度しか指定されていないことに注意してください。システムは、名前空間の依存関係グラフを使って、あるビルドの最終出力にどのコードが必要かを判断します。

### 5.2.2. JavaScript

`shadow-cljs` は JavaScript の依存関係を管理するために `npm` エコシステムと完全に統合されています。

依存関係の管理には `npm` や `yarn` を使用することができますが、それぞれのドキュメントを参照してください。


npm 	https://docs.npmjs.com/

yarn 	https://yarnpkg.com/en/docs

どちらも、プロジェクトディレクトリ内の `package.json` ファイルで依存関係を管理します。`npm` で入手できるほとんどすべてのパッケージには、そのインストール方法が説明されています。これらの説明は、現在では `shadow-cljs` にも適用されています。


Installing a JavaScript package
```bash
# npm
$ npm install the-thing

# yarn
$ yarn add the-thing
```

それ以上は何も必要ありません。依存関係は `package.json` ファイルに追加され、これを使って管理されます。

> ヒント
> もし、まだ `package.json` がない場合は、コマンドラインから `npm init` を実行してください。

#### Missing JS Dependency?

JSの依存関係の欠落に関連するエラーに遭遇するかもしれません。ほとんどのClojureScriptライブラリは、CLJSJSを使用することを想定しているため、使用する`npm`パッケージをまだ宣言していません。つまり、ライブラリが適切に `:npm-deps` を宣言するまで、`npm` パッケージを手動でインストールする必要があります。

```
The required JS dependency "react" is not available, it was required by ...
```

つまり、`npm install react`をしなければならないということです。

> ヒント
> `react`の場合は、おそらく以下の3つのパッケージが必要です。`npm install react react-dom create-react-class`. 

## 5.3. User Configuration

ほとんどの設定はプロジェクト自身で `shadow-cljs.edn` を通して行われますが、いくつかの設定はユーザに依存します。CIDER のようなツールは、追加の `cider-nrepl` 依存関係を必要とするかもしれませんが、`shadow-cljs.edn` を通してその依存関係を追加した場合、Cursive を使用している別のチームメンバーにとっては無意味なものとなります。

制限付きのコンフィグオプションのセットを `~/.shadow-cljs/config.edn` に追加することができ、このユーザーのマシン上でビルドされたすべてのプロジェクトに適用されます。

依存関係を追加するには、通常の `:dependencies` キーを使用します。ここで追加された依存関係は、すべてのプロジェクトに適用されることに注意してください。依存関係は最小限にして、ツール関連の依存関係だけをここに置くようにしてください。ビルドに関連するものはすべて `shadow-cljs.edn` に置いておくべきで、そうしないと他のユーザーがコンパイルできない可能性があります。これらの依存関係は、`deps.edn` や `lein` を使用する際にも自動的に追加されます。


Example ~/.shadow-cljs/config.edn
```Clojure
{:dependencies
 [[cider/cider-nrepl "0.21.1"]]}
;; このバージョンは古くなっている可能性があります。利用可能なものを確認してください。
```

`deps.edn` を使って依存関係を解決する際に、追加のエイリアスを有効にしたい場合があります。これは `:deps-aliases` で行うことができます。

```Clojure
;; shadow-cljs.edn in project
{:deps {:aliases [:cljs]}}

;; ~/.shadow-cljs/config.edn
{:deps-aliases [:cider]}
```

これにより、`deps.edn` を使用しているプロジェクトでは、`shadow-cljs` コマンドが `[:cider :cljs]` のエイリアスを使用するようになります。これは、あなたの `~/.clojure/deps.edn` に追加の `:cider` エイリアスがある場合に便利かもしれません。

デフォルトでは、`shadow-cljs` サーバーモードでは、組み込みの nREPL サーバーを起動しますが、これは必要ないかもしれません。ユーザコンフィグで `:nrepl false` を設定することで、これを無効にすることができます。

ユーザーコンフィグで現在受け入れられている唯一の値は :open-file-command です。他のオプションは現在のところ何の効果もありません。

## 5.4. Server Options

このセクションでは、`shadow-cljs` サーバーインスタンスを構成するその他のオプションについて説明します。これらはオプションです。

### nREPL

`shadow-cljs`サーバはTCP経由でnREPLサーバを提供しています。起動時のメッセージを見ると、nREPLのポートが表示されており、そのポートは`target/shadow-cljs/nrepl.port`にも格納されています。

```
$ shadow-cljs watch app
shadow-cljs - HTTP server available at http://localhost:8600
shadow-cljs - server version: <version> running at http://localhost:9630
shadow-cljs - nREPL server started on port 64967
shadow-cljs - watching build :app
[:app] Configuring build.
[:app] Compiling ...
```

`shadow-cljs.edn`でポートや追加のミドルウェアを設定することができます。

```Clojure
{...
 :nrepl {:port 9000
         :middleware []} ; 名前空間で修飾されたシンボルのオプションリスト
 ...}
```

`~/.nrepl/nrepl.edn` にあるデフォルトのグローバル設定ファイルや、ローカルの `.nrepl.edn` も起動時に読み込まれ、`:middleware` の設定に使用できます。
ポピュラーなミドルウェアである cider-nrepl がクラスパス上にある場合 (例: `:dependencies` に含まれている場合)、自動的に使用されます。追加の設定は必要ありません。これを無効にするには、`:nrepl {:cider false}`を設定します。

`nrepl` オプションで `:init-ns` を設定することで、接続時に起動する名前空間を設定することができます。デフォルトでは `shadow.user` となります。

```Clojure
{...
 :nrepl {:init-ns my.repl}
 ...}
```

nREPLサーバーは、`:nrepl false`を設定することで無効にすることができます。

#### nREPL Usage

nREPLサーバーに接続すると、接続は常にClojure REPLとして開始されます。CLJS REPLへの切り替えは、非nREPLバージョンと同様に動作します。まず、与えられたビルドの `watch` を開始する必要があり、次に、現在の nREPL セッションをそのビルドに切り替えるために、このビルドを選択する必要があります。ビルドを選択すると、すべてがClojureではなくClojureScriptで評価されます。

```Clojure
(shadow/watch :the-build)
(shadow/repl :the-build)
```

> ヒント
> Clojureに戻るには、`:cljs/quit`を使う。

#### Embedded nREPL Server

独自のnREPLサーバを提供する他のツール（例：`lein`）に`shadow-cljs`を組み込んで使用する場合には、`shadow-cljs`ミドルウェアを設定する必要があります。そうしないと、CLJ と CLJS の REPL 間で切り替えることができません。


Example Leiningen project.clj
```Clojure
(defproject my-amazing-project "1.0.0"
  ...
  :repl-options
  {:init-ns shadow.user ;; or any of your choosing
   :nrepl-middleware
   [shadow.cljs.devtools.server.nrepl/middleware]}
  ...)
```

> ヒント
> CLJS REPLを使用する前に、手動で埋め込みサーバーを起動する必要があります。

### 5.4.2. Socket REPL

Clojure Socket REPLは、サーバーモードで自動的に起動され、デフォルトでランダムなポートを使用します。ツールは、ポート番号を含む `.shadow-cljs/socket-repl.port` をチェックすることで、起動されたポートを見つけることができます。

また、`shadow-cljs.edn`を使って固定のポートを設定することもできます。

```Clojure
{...
 :socket-repl
 {:port 9000}
 ...}
```

Socket REPLは、`:socket-repl false`を設定することで無効にすることができます。

### 5.4.3. SSL

`shadow-cljs`のHTTPサーバはSSLをサポートしています。これには、一致する秘密鍵と証明書を提供する Java Keystore が必要です。


`shadow-cljs.edn` with SSL configured
```Clojure
{...
 :ssl {:keystore "ssl/keystore.jks"
       :password "shadow-cljs"}
 ...}
```

上記はデフォルトですので、これらを使用したい場合は、`:ssl {}`を設定するだけで問題ありません。

java `keytool` コマンドでキーストアを作成することができます。信頼できる自己署名証明書を作成することも可能ですが、やや複雑です。

- LinuxとWindows用の[OpenSSL](https://gist.github.com/jchandra74/36d5f8d0e11960dd8f80260801109ab0)の説明(WSL経由)
-  [macOS](https://certsimple.com/blog/localhost-ssl-fix)の手順

作成した `Certificates.p12` (macOS) または `localhost.pfx` (Linux, Windows) ファイルは、`keytool` ユーティリティーを使って、必要な `keystore.jks` にすることができます。

```bash
$ keytool -importkeystore -destkeystore keystore.jks -srcstoretype PKCS12 -srckeystore localhost.pfx
```

> 重要
> 証明書は、"localhost"（または使用するホスト）のSAN（Subject Alternative Name）で生成する必要があります。Chrome が証明書を信頼して警告を表示しないようにするには、SAN が必要です。エクスポート時に使用するパスワードは、キーストアに割り当てられたパスワードと一致する必要があります。

### 5.4.4. Primary HTTP(S)







www.DeepL.com/Translator（無料版）で翻訳しました。

