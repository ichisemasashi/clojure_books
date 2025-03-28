
### トラブルに巻き込まれないために

新しいプログラミング言語を習得することの皮肉は、完璧なコードを書くことが目標である一方で、重要なスキルは、途中で犯すことになる無数のミスに対処する方法を学ぶことです。そのために、いくつかの間違いを犯し、それに対するClojureの反応を見てみましょう。

例えば、古典的なゼロ除算の失敗をしたらどうなるでしょうか？

```clojure
(/ 100 0)
```

ゼロで割ったときに何が表示されるかは、Clojureコードを実行している環境によって異なります。例えば

```
ArithmeticException Divide by zero
clojure.lang.Numbers.divide (Numbers.java:156)
```

あるいは、もっと広範なメッセージが表示されるかもしれない：

```
java.lang.ArithmeticException: Divide by zero
Numbers.java:156 clojure.lang.Numbers.divide
Numbers.java:3731 clojure.lang.Numbers.divide
/Users/russolsen/projects/clojure/quill1/foo.clj:3 user/eval6197
Compiler.java:6703 clojure.lang.Compiler.eval
Compiler.java:6666 clojure.lang.Compiler.eval
core.clj:2927 clojure.core/eval
eval.clj:77 lighttable.nrepl.eval/->result
AFn.java:156 clojure.lang.AFn.applyToHelper
<<and on and on and on>>
ThreadPoolExecutor.java:1145
java.util.concurrent.ThreadPoolExecutor.runWorker
ThreadPoolExecutor.java:615
java.util.concurrent.ThreadPoolExecutor$Worker.run
Thread.java:745 java.lang.Thread.run
```

そう、ちょっと圧倒されるかもしれないが、それは善意によるものだ。この巨大なスタック・トレースのアイデアは、何がうまくいかなかったのか、特にどこでうまくいかなくなったのかを、最大限に説明することだ。しかし、怖気づく必要はない。このコンピューティングの残骸のフィールドの最初の行に集中すればいい。そこに最も重要な情報が隠れているのだ：

```
java.lang.ArithmeticException: Divide by zero
```

また、その例外の `java` の部分に怯える必要はありません。Clojureは-少なくともここで扱っているバージョンは-Javaの上に構築されており、時折、特に例外が飛び交うとき、 Javaから漏れるのが見えるでしょう。慌てないでください：Javaを知っていることは、Clojureプログラマであるための前提条件ではありません。

次に、記号のスペルを間違えた場合、おそらく `chatty-average` ではなく `catty-average` とタイプした場合にどうなるか見てみよう：

```
CompilerException java.lang.RuntimeException:
Unable to resolve symbol: catty-average in this context,
compiling:(NO_SOURCE_PATH:0:0)
```

あるいは、80行のビッグブラザーを手に入れるかもしれない。いずれにせよ、関数名を修正するだけです。 Clojure初心者はまた、括弧で苦労する傾向があります。最も簡単に診断できる括弧の問題は、末尾に1つ追加しすぎることです。

次のようにすると

```clojure
(+ (* 2 2) 10))
```

というストレートなメッセージが返ってくる：

```
RuntimeException Unmatched delimiter: )
clojure.lang.Util.runtimeException (Util.java:221)
```

一方、次のような閉じ括弧を忘れた場合はどうだろう：

```clojure
(+ (* 2 2) 10
```

REPLを介して対話的にClojureを使用している場合は、何もありません。REPLは、あなたが最後の括弧を供給して思考を完了するのを辛抱強く待ちます。ですから、経験則では、REPLに座っていて何も起こらない場合は、閉じ括弧を追加する必要があると考えてください。
あるいは6つの閉じ括弧が必要かもしれない。

Clojureコーディングに足を突っ込むときに覚えておくべきもう1つのことは、Clojure関数はそれ自体が値であるということです。つまり、REPLに`first-name`と入力して`"Russ"`と返されるのとまったく同じように、括弧なしで関数名を入力することで、関数の値を取得することもできます。次のようにしてください：

```
chatty-average
```

すると、関数の値が表示される：

```
#object[user$chatty_average 0x39fcbef6 "user$chatty_average@39fcbef6"]
```

実はこの動作にはかなり深い理由があるのですが（これについては「第6章 関数的なもの（63ページ）」で説明します）、初心者が括弧を忘れると混乱しがちです。要するに、もしプログラム中に`#object[user$chatty_average...`のような予期しない値が現れたら、おそらくどこかで括弧を書き損じたのでしょう。括弧の多さにイライラしても、我慢してください。そう、Clojureの構文はちょっと変わっている。しかし、単純でもあります。いつの間にか、すべての括弧が古い友人のように思えるようになり、括弧なしでどうやってうまくやってきたのかと不思議に思うことでしょう。


