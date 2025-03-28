
### 基本中の基本

始めるには開発ツールをインストールする必要があります。バージョン1.9からClojureに同梱されている[`clj`ツール](https://clojure.org/guides/deps_and_cli)から、[IntelliJベース](https://www.jetbrains.com/idea) [Cursive](https://cursive-ide.com)、[Emacs](https://www.gnu.org/software/emacs)、[Cider](https://github.com/clojure-emacs/cider)、[boot](https://github.com/boot-clj/boot)まで、Clojure開発環境とビルドツールの幅広い選択肢があります。しかし、この本では、人気のあるClojure開発ツールLeiningenにほとんどこだわるつもりです。もしまだなら、[Leiningen](https://leiningen.org)のウェブサイトへ行き、あなたのオペレーティングシステム用のインストール手順に従ってください。 そこにいる間に、LINE-ing-enと発音することにも気づくかもしれない。

伝統的に、プログラミング言語を学ぶときに最初に書くプログラムは、単に挨拶を表示します。これがClojureバージョンだ：

hello/examples.clj
```clojure
(println "Hello, world!")
; Say hi.
```

物事をシンプルに保つために、Clojure REPLでHello, Worldに初挑戦することにしよう。Clojure REPLは、コードを入力し、それが今ここで評価されるのを見ることができる便利なユーティリティだ。LeiningenでREPLを起動するコマンドは以下の通り：

```bash
$ lein repl
```

REPLを起動したら、このコードを入力することができる：

```
user=> (println "Hello, world!")
; Say hi.
```

そして、お馴染みの挨拶を目にする：

```
Hello, world!
nil
```

`nil` のことは気にしないでほしい。これは `println` が実行した後に返される値で、REPLが親切にも表示してくれたものだ。

> [!NOTE]
> **REPL Who?**
>
> REPLは、その名前がアルゴリズムである数少ないプログラムの一つである。 REPLがすることは、あなたが入力したコードを読み、そのコードを評価し、結果を表示し、ループバックしてさらにコードを読むだけです： Read. Evaluate. Print. Loop.


Hello,Worldが最初のプログラムとしてこれほど人気があるのは、その1行のコードからどれだけのことを学べるかということだ。ClojureのHello, Worldを見ると、Clojureの文字列は「二重引用符でくくられる」ことがわかる。

コメントはセミコロンで始まり、行末まで続くこともわかる。通常、Clojureプログラマは、私たちが例でやったように、あるコードで行末にコメントを追加するとき、1つのセミコロンを使いますが、コメントがそれ自身の行に単独である場合は、セミコロンを2重にします：

hello/examples.clj
```clojure
;; Do two semicolons add up to a whole colon?
(println "Hello, world!")
; Say hi
```

もっと微妙なことに、Clojureは`println`のようなシンプルで飾り気のない名前を、参照されるものを識別するものとして扱うと推測できる。したがって、私たちの小さなプログラムは、`println`が定義済みの関数の名前であり、Clojure自身のおかげで私たちにもたらされたものであるため、動作しただけである。ご想像の通り、Clojureは他にも便利な関数をたくさん定義済みだ。例えば、`str`があり、任意の数の値を受け取り、文字列に変換し、全体を連結する：


```clojure
(str "Clo" "jure")                ; Returns "Clojure".
(str "Hello," " " "world" "!")    ; Returns the string "Hello, world!"
(str 3 " " 2 " " 1 " Blast off!") ; Fly me to the Moon!
```

文字列の長さを教えてくれる`count`もある：

```clojure
(count "Hello, world") ; Returns 12.
(count "Hello")        ; Returns 5.
(count "")             ; Returns 0.
```

Clojureには定義済みの定数も多数用意されている。例えば、 `true` と `false` というブール値の兄弟がある：

```clojure
(println true)  ; Prints true...
(println false) ; ...and prints false.
```

また、 `nil` もあります。これは、Clojure の "nobody's home" 値のバージョンで、いくつかの言語では `null` または `None` として知られています：

```clojure
(println "Nobody's home:" nil) ; Prints Nobody's home: nil
```

なお、`println`はどんなものでも表示するため、これを実行すると次のようになる：


```clojure
(println "We can print many things:" true false nil)
```

結果はこのとおり。

```
We can print many things: true false nil
```

Clojure関数呼び出しの括弧について、奇妙なことにお気づきでしょう：括弧は外側にあります。それは

```clojure
(println "Hello, world!")
```

以下のようではない。

```
println("Hello, world!")
```

より伝統的なプログラミング言語からClojureに来ている場合、これらの括弧は場違いに見えるでしょう。Clojure構文の狂気には方法があります。今のところ、関数を呼び出すなど、何かを起こすためのClojure構文は、丸括弧で何かを囲んで、次に進むということだけ覚えておこう。



