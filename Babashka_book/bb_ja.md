# Babashka book

[Michiel Borkent]{#author .author}\



## はじめに

ようこそ読者！本書はClojureとbabashkaを使ったスクリプティングについての本です。[Clojure](https://www.clojure.org)は、JVM上で動作するLispファミリーの関数型動的プログラミング言語です。 BabashkaはClojureで作られたスクリプト環境で、[GraalVM](https://www.graalvm.org)でネイティブにコンパイルさ れています。JVMと比較して、スクリプティングにBabashkaを使用する主な利点は、起動時間の速さとメモリ消費量の少なさです。Babashkaにはバッテリーが付属しており、コマンドライン引数をパースするための`clojure.tools.cli`やJSONを扱うための`cheshire`のようなライブラリがパックされている。さらに、自己完結型のバイナリをダウンロードするだけでインストールできる。


### 対象読者


Babashkaは、JVM上のClojureに精通している開発者向けに書かれています。本書はClojureに精通していることを前提としており、Clojureのチュートリアルではありません。Clojureにそれほど詳しくないが、学びたいという方は、[こちら](https://gist.github.com/yogthos/be323be0361c589570a6da4ccc85f58f)の初心者向けリソースのリストをチェックしてください。




### 想定される設定


BabashkaはClojureを解釈するために[SCI](https://github.com/babashka/SCI)を使用します。SCIはClojureの実質的なサブセットを実装しています。一般的に、コードの解釈はコンパイルされたコードを実行するほどパフォーマンスは高くありません。スクリプトの実行に数秒以上かかるか、ループが多い場合は、JVM上のClojureの方が適しているかもしれません。Clojureとの違いについて、詳しくは[こちら](#differences-with-clojure)をお読みください。






## はじめに



### インストール


babashkaのインストールは、プラットフォーム用のバイナリをダウンロードし、パスに置くだけの簡単なものです。ビルド済みのバイナリは、babashkaの[Github repo](https://github.com/borkdude/babashka)の[releases](https://github.com/borkdude/babashka/releases)ページで提供されている。babashkaはmacOSやlinuxの`brew`やWindowsの`scoop`のような様々なパッケージマネージャーでも利用できる。詳細は[こちら](https://github.com/borkdude/babashka#installation)を参照。




### ソースからのビルド


ソースからbabashkaをビルドしたい場合は、GraalVMのコピーをダウンロードし、`GRAALVM_HOME`環境変数を設定してください。また、[lein](https://leiningen.org)がインストールされていることを確認してください。それから実行してください：



``` {.rouge .highlight}
$ git clone https://github.com/borkdude/babashka --recursive
$ script/uberjar && script/compile
```




詳細はbabashka [build.md](https://github.com/borkdude/babashka/blob/master/doc/build.md)ページを参照。




### babashkaの実行


babashka実行ファイルは`bb`と呼ばれる。Clojure 式を直接入力することができる：




``` {.rouge .highlight}
$ bb -e '(+ 1 2 3)'
6
```




またはスクリプトを実行する：




*script.clj*
``` {.rouge .highlight}
(println (+ 1 2 3))
```

``` {.rouge .highlight}
$ bb -f script.clj
6
```




`-e` フラグは、引数が括弧で始まる場合はオプションである。その場合、babashkaは自動的にそれを式として扱う：




``` {.rouge .highlight}
$ bb '(+ 1 2 3)'
6
```




同様に、引数がファイル名の場合は `-f` フラグはオプションである：




``` {.rouge .highlight}
$ bb script.clj
6
```




一般的に、スクリプトはファイル名だけで呼び出せるようにシェバングを持っている：




*script.clj*
``` {.rouge .highlight}
#!/usr/bin/env bb
(println (+ 1 2 3))
```







## 使い方



コマンドラインから`bb help`と入力すると、babashkaの利用可能な機能を知ることができる、コマンドラインオプションが表示される。



```
    Babashka v1.3.181

    Usage: bb [svm-opts] [global-opts] [eval opts] [cmdline args]
    or:    bb [svm-opts] [global-opts] file [cmdline args]
    or:    bb [svm-opts] [global-opts] task [cmdline args]
    or:    bb [svm-opts] [global-opts] subcommand [subcommand opts] [cmdline args]

    Substrate VM opts:

      -Xmx<size>[g|G|m|M|k|K]  Set a maximum heap size (e.g. -Xmx256M to limit the heap to 256MB).
      -XX:PrintFlags=          Print all Substrate VM options.

    Global opts:

      -cp, --classpath  Classpath to use. Overrides bb.edn classpath.
      --debug           Print debug information and internal stacktrace in case of exception.
      --init <file>     Load file after any preloads and prior to evaluation/subcommands.
      --config <file>   Replace bb.edn with file. Defaults to bb.edn adjacent to invoked file or bb.edn in current dir. Relative paths are resolved relative to bb.edn.
      --deps-root <dir> Treat dir as root of relative paths in config.
      --prn             Print result via clojure.core/prn
      -Sforce           Force recalculation of the classpath (don't use the cache)
      -Sdeps            Deps data to use as the last deps file to be merged
      -f, --file <path> Run file
      --jar <path>      Run uberjar

    Help:

      help, -h or -?     Print this help text.
      version            Print the current version of babashka.
      describe           Print an EDN map with information about this version of babashka.
      doc <var|ns>       Print docstring of var or namespace. Requires namespace if necessary.

    Evaluation:

      -e, --eval <expr>    Evaluate an expression.
      -m, --main <ns|var>  Call the -main function from a namespace or call a fully qualified var.
      -x, --exec <var>     Call the fully qualified var. Args are parsed by babashka CLI.

    REPL:

      repl                 Start REPL. Use rlwrap for history.
      socket-repl  [addr]  Start a socket REPL. Address defaults to localhost:1666.
      nrepl-server [addr]  Start nREPL server. Address defaults to localhost:1667.

    Tasks:

      tasks       Print list of available tasks.
      run <task>  Run task. See run --help for more details.

    Clojure:

      clojure [args...]  Invokes clojure. Takes same args as the official clojure CLI.

    Packaging:

      uberscript <file> [eval-opt]  Collect all required namespaces from the classpath into a single file. Accepts additional eval opts, like `-m`.
      uberjar    <jar>  [eval-opt]  Similar to uberscript but creates jar file.
      prepare                       Download deps & pods defined in bb.edn and cache their metadata. Only an optimization, this will happen on demand when needed.

    In- and output flags (only to be used with -e one-liners):

      -i                 Bind *input* to a lazy seq of lines from stdin.
      -I                 Bind *input* to a lazy seq of EDN values from stdin.
      -o                 Write lines to stdout.
      -O                 Write EDN values to stdout.
      --stream           Stream over lines or EDN values from stdin. Combined with -i or -I *input* becomes a single value per iteration.

    Tooling:

      print-deps [--format <deps | classpath>]: prints a deps.edn map or classpath
        with built-in deps and deps from bb.edn.

    File names take precedence over subcommand names.
    Remaining arguments are bound to *command-line-args*.
    Use -- to separate script command line args from bb command line args.
    When no eval opts or subcommand is provided, the implicit subcommand is repl.
```



### スクリプトの実行


スクリプトは `-f` または `--file` を使ってファイルから実行することができる：




``` {.rouge .highlight}
bb -f download_html.clj
```




ファイルは `-f` なしで直接渡すこともできる：




``` {.rouge .highlight}
bb download_html.clj
```




`bb` をシェバングと一緒に使ってもうまくいく：




``` {.rouge .highlight}
#!/usr/bin/env bb

(require '[babashka.http-client :as http])

(defn get-url [url]
  (println "Downloading url:" url)
  (http/get url))

(defn write-html [file html]
  (println "Writing file:" file)
  (spit file html))

(let [[url file] *command-line-args*]
  (when (or (empty? url) (empty? file))
    (println "Usage: <url> <file>")
    (System/exit 1))
  (write-html file (:body (get-url url))))
```





``` {.rouge .highlight}
$ ./download_html.clj
Usage: <url> <file>

$ ./download_html.clj https://www.clojure.org /tmp/clojure.org.html
Downloading url: https://www.clojure.org
Writing file: /tmp/clojure.org.html
```




`usr/bin/env`が機能しない場合は、以下の回避策を使うことができる：




``` {.rouge .highlight}
$ cat script.clj
#!/bin/sh

#_(
   "exec" "bb" "$0" hello "$@"
   )

(prn *command-line-args*)

./script.clj 1 2 3
("hello" "1" "2" "3")
```





### 現在のファイルパス


var `*file*` には、現在実行されているファイルのフルパスが格納される：




``` {.rouge .highlight}
$ cat example.clj
(prn *file*)

$ bb example.clj
"/Users/borkdude/example.clj"
```





### コマンドライン引数のパース


コマンドライン引数は `*command-line-args*` を使って取得できます。コマンドライン引数をパースしたい場合は、組み込みの [`babashka.cli`](https://github.com/babashka/cli) 名前空間を使用することができます：



``` {.rouge .highlight}
(require '[babashka.cli :as cli])

(def cli-options {:port {:default 80 :coerce :long}
                  :help {:coerce :boolean}})

(prn (cli/parse-opts *command-line-args* {:spec cli-options}))
```





``` {.rouge .highlight}
$ bb script.clj
{:port 80}
$ bb script.clj --port 1223
{:port 1223}
$ bb script.clj --help
{:port 80, :help true}
```




なお、[clojure.tools.cli](https://github.com/clojure/tools.cli)もbabashkaに組み込まれています。




### クラスパス


babashkaのクラスパスに含まれるディレクトリとライブラリを制御するために、`bb.edn`を使用することを推奨します。”プロジェクトセットアップ"を参照してください。



もし、`bb.edn`を使用せずに、より低いレベルでbabashkaのクラスパスをコントロールしたい場合、クラスパスをオーバーライドする`--classpath`オプションを使用することができる。ファイル`script/my/namespace.clj`があるとする：




``` {.rouge .highlight}
(ns my.namespace)
(defn -main [& args]
  (apply println "Hello from my namespace!" args))
```




これで、このメイン関数を次のように実行できる：




``` {.rouge .highlight}
$ bb --classpath script --main my.namespace 1 2 3
Hello from my namespace! 1 2 3
```




次のような古典的なClojureプロジェクトのレイアウトを持つ大きなスクリプトがある場合。




``` {.rouge .highlight}
$ tree -L 3
├── deps.edn
├── README
├── src
│   └── project_namespace
│       ├── main.clj
│       └── utilities.clj
└── test
    └── project_namespace
        ├── test_main.clj
        └── test_utilities.clj
```




そして、クラスパスに`src`と`test`フォルダの両方を含めるようにbabashkaに指示し、ソケットREPLを起動することができる：




``` {.rouge .highlight}
$ bb --classpath src:test socket-repl 1666
```




引数 `--classpath` がない場合、環境変数 `BABASHKA_CLASSPATH` が使用される。この変数も設定されていない場合、babashkaは`bb.edn`の`:deps`と`:paths`を使用する。



クラスパスに動的に追加できる”babashka.classpath"名前空間も参照してください。



名前空間”babashka.deps"は[tools.deps](https://github.com/clojure/tools.deps.alpha)をbabashkaと統合し、`deps.edn`マップを使用してクラスパスを設定できるようにする。




### メイン関数の呼び出し


main関数は上記のように `-m` または `--main` で呼び出すことができる。 引数`foo.bar`が与えられると、名前空間`foo.bar`が必要となり、コマンドライン引数を文字列として関数`foo.bar/-main`が呼び出される。



babashka 0.3.1以降、完全修飾シンボルを `-m` に渡すことができる：




``` {.rouge .highlight}
$ bb -m clojure.core/prn 1 2 3
"1" "2" "3"
```




so you can execute any function as a main function, as long as it accepts the number of provided arguments.



When invoking `bb` with a main function, the expression `(System/getProperty "babashka.main")` will return the name of the main function.




### Preloads


The environment variable `BABASHKA_PRELOADS` allows to define code that will be available in all subsequent usages of babashka.




``` {.rouge .highlight}
BABASHKA_PRELOADS='(defn foo [x] (+ x 2))'
BABASHKA_PRELOADS=$BABASHKA_PRELOADS' (defn bar [x] (* x 2))'
export BABASHKA_PRELOADS
```




Note that you can concatenate multiple expressions. Now you can use these functions in babashka:




``` {.rouge .highlight}
$ bb '(-> (foo *input*) bar)' <<< 1
6
```




You can also preload an entire file using `load-file`:




``` {.rouge .highlight}
export BABASHKA_PRELOADS='(load-file "my_awesome_prelude.clj")'
```




Note that `*input*` is not available in preloads.




### Running a REPL


Babashka supports running a REPL, a socket REPL and an nREPL server.



#### REPL


To start a REPL, type:




``` {.rouge .highlight}
$ bb repl
```




To get history with up and down arrows, use [rlwrap](https://github.com/hanslub42/rlwrap):




``` {.rouge .highlight}
$ rlwrap bb repl
```





#### Socket REPL


To start a socket REPL on port `1666`:




``` {.rouge .highlight}
$ bb socket-repl 1666
Babashka socket REPL started at localhost:1666
```




Now you can connect with your favorite socket REPL client:




``` {.rouge .highlight}
$ rlwrap nc 127.0.0.1 1666
Babashka v0.0.14 REPL.
Use :repl/quit or :repl/exit to quit the REPL.
Clojure rocks, Bash reaches.

bb=> (+ 1 2 3)
6
bb=> :repl/quit
$
```




The `--socket-repl` option takes options similar to the `clojure.server.repl` Java property option in Clojure:




``` {.rouge .highlight}
$ bb socket-repl '{:address "0.0.0.0" :accept clojure.core.server/repl :port 1666}'
```




Editor plugins and tools known to work with a babashka socket REPL:



-   Emacs: [inf-clojure](https://github.com/clojure-emacs/inf-clojure):

    To connect:

    `M-x inf-clojure-connect <RET> localhost <RET> 1666`

    Before evaluating from a Clojure buffer:

    `M-x inf-clojure-minor-mode`

-   Atom: [Chlorine](https://github.com/mauricioszabo/atom-chlorine)

-   Vim: [vim-iced](https://github.com/liquidz/vim-iced)

-   IntelliJ IDEA: [Cursive](https://cursive-ide.com/)

    Note: you will have to use a workaround via [tubular](https://github.com/mfikes/tubular). For more info, look [here](https://cursive-ide.com/userguide/repl.html#repl-types).




#### pREPL


Launching a prepl can be done as follows:




``` {.rouge .highlight}
$ bb socket-repl '{:address "0.0.0.0" :accept clojure.core.server/io-prepl :port 1666}'
```




or programmatically:




``` {.rouge .highlight}
$ bb -e '(clojure.core.server/io-prepl)'
(+ 1 2 3)
{:tag :ret, :val "6", :ns "user", :ms 0, :form "(+ 1 2 3)"}
```





#### nREPL


To start an nREPL server:




``` {.rouge .highlight}
$ bb nrepl-server 1667
```




or programmatically:




``` {.rouge .highlight}
$ bb -e "(babashka.nrepl.server/start-server\!) (deref (promise))"
Started nREPL server at 0.0.0.0:1667
```




Then connect with your favorite nREPL client:




``` {.rouge .highlight}
$ lein repl :connect 1667
Connecting to nREPL at 127.0.0.1:1667
user=> (+ 1 2 3)
6
user=>
```




Editor plugins and tools known to work with the babashka nREPL server:



-   Emacs: [CIDER](https://docs.cider.mx/cider/platforms/babashka.html)

-   `lein repl :connect`

-   VSCode: [Calva](http://calva.io/)

-   Atom: [Chlorine](https://github.com/mauricioszabo/atom-chlorine)

-   (Neo)Vim: [vim-iced](https://github.com/liquidz/vim-iced), [conjure](https://github.com/Olical/conjure), [fireplace](https://github.com/tpope/vim-fireplace)



The babashka nREPL server currently does not write an `.nrepl-port` file at startup. Using the following `nrepl` task, defined in a `bb.edn`, you can accomplish the same:



``` {.rouge .highlight}
{:tasks
 {nrepl
  {:requires ([babashka.fs :as fs]
              [babashka.nrepl.server :as srv])
   :task (do (srv/start-server! {:host "localhost"
                                 :port 1339})
             (spit ".nrepl-port" "1339")
             (-> (Runtime/getRuntime)
                 (.addShutdownHook
                  (Thread. (fn [] (fs/delete ".nrepl-port")))))
             (deref (promise)))}}}
```




The `babashka.nrepl.server` API is exposed since version 0.8.157.



##### Debugging the nREPL server


To debug the nREPL server from the binary you can run:




``` {.rouge .highlight}
$ BABASHKA_DEV=true bb nrepl-server 1667
```




This will print all the incoming messages.



To debug the nREPL server from source:




``` {.rouge .highlight}
$ git clone https://github.com/borkdude/babashka --recurse-submodules
$ cd babashka
$ BABASHKA_DEV=true clojure -A:main --nrepl-server 1667
```






#### REPL server port


For the socket REPL, pREPL, or nREPL, if a randomized port is needed, 0 can be used anywhere a port argument is accepted.




### Input and output flags


In one-liners the `*input*` value may come in handy. It contains the input read from stdin as EDN by default. If you want to read in text, use the `-i` flag, which binds `*input*` to a lazy seq of lines of text.  If you want to read multiple EDN values, use the `-I` flag. The `-o` option prints the result as lines of text. The `-O` option prints the result as lines of EDN values.


  -- ------------------------------------------------------------------------------------------------------------------------------
     `*input*` is only available in the `user` namespace, designed for one-liners. For writing scripts, see [Scripts](#_scripts).
  -- ------------------------------------------------------------------------------------------------------------------------------



The following table illustrates the combination of options for commands of the form



    echo "{{Input}}" | bb {{Input flags}} {{Output flags}} "*input*"



+-------------+-------------+-------------+-------------+-------------+
| Input       | Input flags | Output flag | `*input*`   | Output      |
+=============+=============+=============+=============+=============+
| `{:a 1}`    |             |             | `{:a 1}`    | `{:a 1}`    |
| `{:a 2}`    |             |             |             |             |
+-------------+-------------+-------------+-------------+-------------+
| hello\      | `-i`        |             | `("hel      | `("hel      |
| bye         |             |             | lo" "bye")` | lo" "bye")` |
+-------------+-------------+-------------+-------------+-------------+
| hello\      | `-i`        | `-o`        | `("hel      | hello\      |
| bye         |             |             | lo" "bye")` | bye         |
+-------------+-------------+-------------+-------------+-------------+
| `{:a        | `-I`        |             | `({:a       | `({:a       |
|  1} {:a 2}` |             |             | 1} {:a 2})` | 1} {:a 2})` |
+-------------+-------------+-------------+-------------+-------------+
| `{:a        | `-I`        | `-O`        | `({:a       | `{:a        |
|  1} {:a 2}` |             |             | 1} {:a 2})` |  1} {:a 2}` |
+-------------+-------------+-------------+-------------+-------------+


When combined with the `--stream` option, the expression is executed for each value in the input:




``` {.rouge .highlight}
$ echo '{:a 1} {:a 2}' | bb --stream '*input*'
{:a 1}
{:a 2}
```




#### Scripts


When writing scripts instead of one-liners on the command line, it is not recommended to use `*input*`. Here is how you can rewrite to standard Clojure code.




#### EDN input


Reading a single EDN value from stdin:




``` {.rouge .highlight}
(ns script
 (:require [clojure.edn :as edn]))

(edn/read *in*)
```




Reading multiple EDN values from stdin (the `-I` flag):




``` {.rouge .highlight}
(ns script
 (:require [clojure.edn :as edn]
           [clojure.java.io :as io]))

(let [reader  (java.io.PushbackReader. (io/reader *in*))]
  (take-while #(not (identical? ::eof %)) (repeatedly #(edn/read {:eof ::eof} reader))))
```





#### Text input


Reading text from stdin can be done with `(slurp *in*)`. To get a lazy seq of lines (the `-i` flag), you can use:




``` {.rouge .highlight}
(ns script
 (:require [clojure.java.io :as io]))

(line-seq (io/reader *in*))
```





#### Output


To print to stdout, use `println` for text and `prn` for EDN values.





### Uberscript


The `--uberscript` option collects the expressions in `BABASHKA_PRELOADS`, the command line expression or file, the main entrypoint and all required namespaces from the classpath into a single file. This can be convenient for debugging and deployment.



Here is an example that uses a function from the [clj-commons/fs](https://github.com/clj-commons/fs) library.



Let's first set the classpath:




``` {.rouge .highlight}
$ export BABASHKA_CLASSPATH=$(clojure -Spath -Sdeps '{:deps {clj-commons/fs {:mvn/version "1.6.307"}}}')
```




Write a little script, say `glob.clj`:




``` {.rouge .highlight}
(ns glob (:require [me.raynes.fs :as fs]))

(run! (comp println str)
      (fs/glob (first *command-line-args*)))
```




For testing, we'll make a file which we will find using the glob function:




``` {.rouge .highlight}
$ touch README.md
```




Now we can execute the script which uses the library:




``` {.rouge .highlight}
$ time bb glob.clj '*.md'
/private/tmp/glob/README.md
bb glob.clj '*.md'   0.03s  user 0.01s system 88% cpu 0.047 total
```




Producing an uberscript with all required code:




``` {.rouge .highlight}
$ bb uberscript glob-uberscript.clj glob.clj
```




To prove that we don't need the classpath anymore:




``` {.rouge .highlight}
$ unset BABASHKA_CLASSPATH
$ time bb glob-uberscript.clj '*.md'
/private/tmp/glob/README.md
bb glob-uberscript.clj '*.md'   0.03s  user 0.02s system 93% cpu 0.049 total
```




Caveats:



-   *Dynamic requires*. Building uberscripts works by running top-level `ns` and `require` forms. The rest of the code is not evaluated.  Code that relies on dynamic requires may not work in an uberscript.

-   *Resources*. The usage of `io/resource` assumes a classpath, so when this is used in your uberscript, you still have to set a classpath and bring the resources along.



If any of the above is problematic for your project, using an [uberjar](#uberjar) is a good alternative.



#### Carve


Uberscripts can be optimized by cutting out unused vars with [carve](https://github.com/borkdude/carve).




``` {.rouge .highlight}
$ wc -l glob-uberscript.clj
     583 glob-uberscript.clj
$ carve --opts '{:paths ["glob-uberscript.clj"] :aggressive true :silent true}'
$ wc -l glob-uberscript.clj
     105 glob-uberscript.clj
```




Note that the uberscript became 72% shorter. This has a beneficial effect on execution time:




``` {.rouge .highlight}
$ time bb glob-uberscript.clj '*.md'
/private/tmp/glob/README.md
bb glob-uberscript.clj '*.md'   0.02s  user 0.01s system 84% cpu 0.034 total
```






### Uberjar


Babashka can create uberjars from a given classpath and optionally a main method:




``` {.rouge .highlight}
$ cat bb/foo.clj
(ns foo)
(defn -main [& args] (prn :hello))
$ cat bb.edn
{:paths ["bb"]}
$ bb uberjar foo.jar -m foo
$ bb foo.jar
:hello
```





### System properties


Babashka sets the following system properties:



-   `babashka.version`: the version string, e.g. `"1.2.0"`

-   `babashka.main`: the `--main` argument

-   `babashka.file`: the `--file` argument (normalized using `.getAbsolutePath`)




### Data readers


Data readers can be enabled by setting `*data-readers*` to a hashmap of symbols to functions or vars:




``` {.rouge .highlight}
$ bb -e "(set! *data-readers* {'t/tag inc}) #t/tag 1"
2
```




To preserve good startup time, babashka does not scan the classpath for `data_readers.clj` files.




### Reader conditionals


Babashka supports reader conditionals by taking either the `:bb` or `:clj` branch, whichever comes first. NOTE: the `:clj` branch behavior was added in version 0.0.71, before that version the `:clj` branch was ignored.




``` {.rouge .highlight}
$ bb -e "#?(:bb :hello :clj :bye)"
:hello

$ bb -e "#?(:clj :bye :bb :hello)"
:bye

$ bb -e "[1 2 #?@(:bb [] :clj [1])]"
[1 2]
```





### Invoking clojure


Babashka bundles [deps.clj](https://github.com/borkdude/deps.clj) for invoking a `clojure` JVM process:




``` {.rouge .highlight}
$ bb clojure -M -e "*clojure-version*"
{:major 1, :minor 10, :incremental 1, :qualifier nil}
```




See the [clojure](#_clojure) function in the [babashka.deps](#babashkadeps) namespace for programmatically invoking clojure.






## Project setup



### bb.edn


Since version 0.3.1, babashka supports a local `bb.edn` file to manage a project.




### :paths and :deps


You can declare one or multiple paths and dependencies so they are automatically added to the classpath:




``` {.rouge .highlight}
{:paths ["bb"]
 :deps {medley/medley {:mvn/version "1.3.0"}}}
```




If we have a project that has a `deps.edn` and would like to reuse those deps in `bb.edn`:




``` {.rouge .highlight}
{:deps {your-org/your-project {:local/root "."}}}
```




`bb.edn` applies to the local project, and dependencies defined in this files are never shared with other projects. This is typically what you want when writing a script or tool. By contrast, `deps.edn` is useful when creating libraries that are used by other projects.



  -- -----------------------------------------------------------------------------------------------------------------------------------------------------
     Use a unique name to refer to your project's `deps.edn`, the same name that you would otherwise use when referring to your project as a dependency.
  -- -----------------------------------------------------------------------------------------------------------------------------------------------------



If we have a main function in a file called `bb/my_project/main.clj` like:




```
    (ns my-project.main
      (:require [medley.core :as m]))

    (defn -main [& _args]
      (prn (m/index-by :id [{:id 1} {:id 2}])))
```




we can invoke it like:




``` {.rouge .highlight}
$ bb -m my-project.main
{1 {:id 1}, 2 {:id 2}}
```




See "Invoking a main function" for more details on how to invoke a function from the command line.



The `:deps` entry is managed by [deps.clj](https://github.com/borkdude/deps.clj) and requires a `java` installation to resolve and download dependencies.




### :min-bb-version


Since version 0.3.6, babashka supports the `:min-bb-version` where the minimal babashka version can be declared:




``` {.rouge .highlight}
{:paths ["src"]
 :deps {medley/medley {:mvn/version "1.3.0"}}
 :min-bb-version "0.3.7"}
```




When using an older bb version (that supports `:min-bb-version`), babashka will print a warning:




``` {.rouge .highlight}
WARNING: this project requires babashka 0.3.7 or newer, but you have: 0.3.6
```





### :tasks


Since babashka 0.4.0 the `bb.edn` file supports the `:tasks` entry which describes tasks that you can run in the current project. The tasks feature is similar to what people use `Makefile`, `Justfile` or `npm run` for. See [Task runner](#tasks) for more details.




### Script-adjacent bb.edn


Since babashka 1.3.177 a `bb.edn` file relative to the invoked file is respected. This makes writing system-global scripts with dependencies easier.



Given a `bb.edn`:




``` {.rouge .highlight}
{:deps {medley/medley {:mvn/version "1.3.0"}}}
```




and a script `medley.bb`:




``` {.rouge .highlight}
#!/usr/bin/env bb

(ns medley
  (:require [medley.core :as medley]))

(prn (medley/index-by :id [{:id 1}]))
```




Assuming that `medley.bb` is executable (`chmod +x medley.bb`), you can directly execute it in the current directory:




``` {.rouge .highlight}
~/my_project $ ./medley.bb
{1 {:id 1}}
```




To execute this script from anywhere on the system, you just have to add it to the `PATH`:




``` {.rouge .highlight}
/tmp $ export PATH=$PATH:~/my_project # ensure script is on path
/tmp $ medley.bb # works, respects ~/my_project/bb.edn file with :deps
{1 {:id 1}}
```




Of course you can just call your script `medley` without the `.bb` extension.



#### Windows


On Windows bash shebangs are not supported. An alternative is to create a script-adjacent `.bat` file, e.g `medley.bat`:




``` {.rouge .highlight}
@echo off
set ARGS=%*
set SCRIPT=%~dp0medley.bb
bb %SCRIPT% %ARGS%
```




Then add this script to your `%PATH%`:




``` {.rouge .highlight}
C:\Temp> set PATH=%PATH%;c:\my_project
C:\Temp> medley
{1 {:id 1}}
```








## Task runner



### Introduction


People often use a `Makefile`, `Justfile`, `npm scripts` or `lein` aliases in their (clojure) projects to remember complex invocations and to create shortcuts for them. Since version 0.4.0, babashka supports a similar feature as part of the `bb.edn` project configuration file. For a general overview of what's available in `bb.edn`, go to [Project setup](#project-setup).



The tasks configuration lives under the `:tasks` key and can be used together with `:paths` and `:deps`:




``` {.rouge .highlight}
{:paths ["script"]
 :deps {medley/medley {:mvn/version "1.3.0"}}
 :min-bb-version "0.4.0"
 :tasks
 {clean (shell "rm -rf target")
 ...}
 }
```




In the above example we see a simple task called `clean` which invokes the `shell` command, to remove the `target` directory. You can invoke this task from the command line with:



``` {.rouge .highlight}
$ bb run clean
```




Babashka also accepts a task name without explicitly mentioning `run`:




``` {.rouge .highlight}
$ bb clean
```




To make your tasks more cross-platform friendly, you can use the built-in [babashka.fs](https://github.com/babashka/fs) library. To use libraries in tasks, use the `:requires` option:




``` {.rouge .highlight}
{:tasks
 {:requires ([babashka.fs :as fs])
  clean (fs/delete-tree "target")
  }
 }
```




Tasks accept arbitrary Clojure expressions. E.g. you can print something when executing the task:




``` {.rouge .highlight}
{:tasks
 {:requires ([babashka.fs :as fs])
  clean (do (println "Removing target folder.")
            (fs/delete-tree "target"))
  }
 }
```





``` {.rouge .highlight}
$ bb clean
Removing target folder.
```





### Talk


Go [here](https://www.youtube.com/watch?v=u5ECoR7KT1Y&ab_channel=LondonClojurians) if you would like to watch a talk on babashka tasks.




### Task-local options


Instead of naked expressions, tasks can be defined as maps with options.  The task expression should then be moved to the `:task` key:




``` {.rouge .highlight}
{:tasks
 {
  clean {:doc "Removes target folder"
         :requires ([babashka.fs :as fs])
         :task (fs/delete-tree "target")}
  }
 }
```




Tasks support the `:doc` option which gives it a docstring which is printed when invoking `bb tasks` on the command line. Other options include:



-   `:requires`: task-specific namespace requires.

-   `:extra-paths`: add paths to the classpath.

-   `:extra-deps`: add extra dependencies to the classpath.

-   `:enter`, `:leave`: override the global `:enter`/`:leave` hook.

-   `:override-builtin`: override the name of a built-in babashka command.




### Discoverability


When invoking `bb tasks`, babashka prints a list of all tasks found in `bb.edn` in the order of appearance. E.g. in the [clj-kondo.lsp](https://github.com/clj-kondo/clj-kondo.lsp) project it prints:




``` {.rouge .highlight}
$ bb tasks
The following tasks are available:

recent-clj-kondo   Detects most recent clj-kondo version from clojars
update-project-clj Updates project.clj with most recent clj-kondo version
java1.8            Asserts that we are using java 1.8
build-server       Produces lsp server standalone jar
lsp-jar            Copies renamed jar for upload to clj-kondo repo
upload-jar         Uploads standalone lsp server jar to clj-kondo repo
vscode-server      Copied lsp server jar to vscode extension
vscode-version     Prepares package.json with up to date clj-kondo version
vscode-publish     Publishes vscode extension to marketplace
ovsx-publish       Publishes vscode extension to ovsx thing
publish            The mother of all tasks: publishes everything needed for new release
```





### Command line arguments


Command line arguments are available as `*command-line-args*`, just like in Clojure. Since version `0.9.160`, you can use [babashka.cli](https://github.com/babashka/cli) in tasks via the [exec](#cli:exec) function to deal with command line arguments in a concise way. See the chapter on [babashka CLI](#cli).



Of course, you are free to parse command line arguments using the built-in `tools.cli` library or just handle them manually.



You can re-bind `*command-line-args*` to ensure functions see a different set of arguments:




``` {.rouge .highlight}
{:tasks
 {:init (do (defn print-args []
              (prn (:name (current-task))
                   *command-line-args*)))
  bar (print-args)
  foo (do (print-args)
          (binding [*command-line-args* (next *command-line-args*)]
            (run 'bar)))}}
```





``` {.rouge .highlight}
$ bb foo 1 2 3
foo ("1" "2" "3")
bar ("2" "3")
```




#### Terminal tab-completion


##### zsh


Add this to your `.zshrc` to get tab-complete feature on ZSH.




``` {.rouge .highlight}
_bb_tasks() {
    local matches=(`bb tasks |tail -n +3 |cut -f1 -d ' '`)
    compadd -a matches
    _files # autocomplete filenames as well
}
compdef _bb_tasks bb
```





##### bash


Add this to your `.bashrc` to get tab-complete feature on bash.




``` {.rouge .highlight}
_bb_tasks() {
    COMPREPLY=( $(compgen -W "$(bb tasks |tail -n +3 |cut -f1 -d ' ')" -- ${COMP_WORDS[COMP_CWORD]}) );
}
# autocomplete filenames as well
complete -f -F _bb_tasks bb
```





##### fish


Add this to your `.config/fish/completions/bb.fish` to get tab-complete feature on Fish shell.




``` {.rouge .highlight}
function __bb_complete_tasks
  if not test "$__bb_tasks"
    set -g __bb_tasks (bb tasks |tail -n +3 |cut -f1 -d ' ')
  end

  printf "%s\n" $__bb_tasks
end

complete -c bb -a "(__bb_complete_tasks)" -d 'tasks'
```







### Run


You can execute tasks using `bb <task-name>`. The babashka `run` subcommand can be used to execute with some additinoal options:



-   `--parallel`: invoke task dependencies in parallel.

    ``` {.rouge .highlight}
    {:tasks
     {:init (def log (Object.))
      :enter (locking log
               (println (str (:name (current-task))
                             ":")
                        (java.util.Date.)))
      a (Thread/sleep 5000)
      b (Thread/sleep 5000)
      c {:depends [a b]}
      d {:task (time (run 'c))}}}
    ```

    ``` {.rouge .highlight}
    $ bb run --parallel d
    d: #inst "2021-05-08T14:14:56.322-00:00"
    a: #inst "2021-05-08T14:14:56.357-00:00"
    b: #inst "2021-05-08T14:14:56.360-00:00"
    c: #inst "2021-05-08T14:15:01.366-00:00"
    "Elapsed time: 5023.894512 msecs"
    ```

    Also see [Parallel tasks](#parallel).

-   `--prn`: print the result from the task expression:

    ``` {.rouge .highlight}
    {:tasks {sum (+ 1 2 3)}}
    ```

    ``` {.rouge .highlight}
    $ bb run --prn sum
    6
    ```

    Unlike scripts, babashka tasks do not print their return value.




### Hooks


The task runner exposes the following hooks:



#### :init


The `:init` is for expressions that are executed before any of the tasks are executed. It is typically used for defining helper functions and constants:




``` {.rouge .highlight}
{:tasks
 {:init (defn env [s] (System/getenv s))
  print-env (println (env (first *command-line-args*)))
  }
 }
```





``` {.rouge .highlight}
$ FOO=1 bb print-env FOO
1
```





#### :enter, :leave


The `:enter` hook is executed before each task. This is typically used to print the name of a task, which can be obtained using the `current-task` function:




``` {.rouge .highlight}
{:tasks
 {:init (defn env [s] (System/getenv s))
  :enter (println "Entering:" (:name (current-task)))
  print-env (println (env (first *command-line-args*)))
  }
 }
```





``` {.rouge .highlight}
$ FOO=1 bb print-env FOO
Entering: print-env
1
```




The `:leave` hook is similar to `:enter` but is executed after each task.



Both hooks can be overriden as task-local options. Setting them to `nil` will disable them for specific tasks (see [Task-local options](#_task_local_options)).





### Tasks API


The `babashka.tasks` namespace exposes the following functions: `run`, `shell`, `clojure` and `current-task`. They are implicitly imported, thus available without a namespace prefix.



#### run


Tasks provide the `run` function to explicitly invoke another task:




``` {.rouge .highlight}
{:tasks
 {:requires ([babashka.fs :as fs])

  clean (do
          (println "Removing target folder.")
          (fs/delete-tree "target"))
  uberjar (do
            (println "Making uberjar")
            (clojure "-X:uberjar"))
  uberjar:clean (do (run 'clean)
                    (run 'uberjar))}
 }
```




When running `bb uberjar:clean`, first the `clean` task is executed and the `uberjar`:




``` {.rouge .highlight}
$ bb uberjar:clean
Removing target folder.
Making uberjar
```




The `clojure` function in the above example executes a clojure process using [deps.clj](https://github.com/borkdude/deps.clj). See [clojure](#tasks:clojure) for more info.



The `run` function accepts an additional map with options:



##### :parallel


The `:parallel` option executes dependencies of the invoked task in parallel (when possible). See [Parallel tasks](#parallel).




#### shell


Both `shell` and `clojure` return a [process](https://github.com/babashka/babashka.process) object which returns the `:exit` code among other info. By default these functions will throw an exception when a non-zero exit code was returned and they will inherit the stdin/stdout/stderr from the babashka process.



``` {.rouge .highlight}
{:tasks
 {
  ls (shell "ls foo")
 }
}
```





``` {.rouge .highlight}
$ bb ls
ls: foo: No such file or directory
Error while executing task: ls
$ echo $?
1
```




You can opt out of this behavior by using the `:continue` option:




``` {.rouge .highlight}
{:tasks
 {
  ls (shell {:continue true} "ls foo")
 }
}
```





``` {.rouge .highlight}
$ bb ls
ls: foo: No such file or directory
$ echo $?
0
```




When you want to redirect output to a file instead, you can provide the `:out` option.




``` {.rouge .highlight}
(shell {:out "file.txt"} "echo hello")
```




To run a program in another directory, you can use the `:dir` option:




``` {.rouge .highlight}
(shell {:dir "subproject"} "ls")
```




To set environment variables with `shell` or `clojure`:




``` {.rouge .highlight}
(shell {:extra-env {"FOO" "BAR"}} "printenv FOO")
```




Other supported options are similar to those of [`babashka.process/process`](https://github.com/babashka/babashka.process).


The process is executed synchronously: i.e. babashka will wait for the process to finish before executing the next expression. If this doesn't fit your use case, you can use [`babashka.process/process`](https://github.com/babashka/babashka.process) directly instead. These two invocations are roughly equivalent:




``` {.rouge .highlight}
(require '[babashka.process :as p :refer [process]]
         '[babashka.tasks :as tasks])

(tasks/shell {:dir "subproject"} "npm install")

(-> (process {:dir "subproject" :inherit true} "npm install")
    (p/check))
```




Note that the first string argument to `shell` it tokenized (broken into multiple parts) and the trailing arguments are not:



Correct:




``` {.rouge .highlight}
(shell "npm install" "-g" "nbb")
```




Not correct (`-g nbb` within the same string):




``` {.rouge .highlight}
(shell "npm install" "-g nbb")
```




Note that the varargs signature plays well with feeding `*command-line-args*`:




``` {.rouge .highlight}
(apply shell "npm install" *command-line-args*)
```




Note that `shell` does not invoke a shell but just shells out to an external program. As such, `shell` does not understand bash specific syntax. The following does not work: `(shell "rm -rf target/*")`. To invoke a specific shell, you should do that explicitly with:




``` {.rouge .highlight}
(shell "bash -c" "rm -rf target/*")
```




Also see the docstring of `shell` [here](https://github.com/babashka/process/blob/master/API.md#shell).




#### clojure


The `clojure` function starts a Clojure process using [deps.clj](https://github.com/borkdude/deps.clj). The interface is exactly the same as the clojure CLI. E.g. to evaluate an expression:




``` {.rouge .highlight}
{:tasks {eval (clojure "-M -e '(+ 1 2 3)'")}}
```




or to invoke clj-kondo as a library:




``` {.rouge .highlight}
{:tasks {eval (clojure {:dir "subproject"} "-M:clj-kondo")}}
```




The `clojure` task function behaves similar to `shell` with respect to the exit code, return value and supported options, except when it comes to features that do not start a process, but only do some printing. E.g.  you can capture the classpath using:




``` {.rouge .highlight}
(with-out-str (clojure "-Spath"))
```




because this operation doesn't start a process but prints to `*out*`.



To run a `clojure` task in another directory:




``` {.rouge .highlight}
{:tasks {eval (clojure {:dir "subproject"} "-M:clj-kondo")}}
```





#### current-task


The `current-task` function returns a map representing the currently running task. This function is typically used in the `:enter` and `:leave` hooks.




#### exec


See [exec](#cli:exec).





### Dependencies between tasks


Dependencies between tasks can be declared using `:depends`:




``` {.rouge .highlight}
{:tasks {:requires ([babashka.fs :as fs])
         -target-dir "target"
         -target {:depends [-target-dir]
                  :task (fs/create-dirs -target-dir)}
         -jar-file {:depends [-target]
                    :task "target/foo.jar"}

         jar {:depends [-target -jar-file]
              :task (when (seq (fs/modified-since -jar-file
                                             (fs/glob "src" "**.clj")))
                      (spit -jar-file "test")
                      (println "made jar!"))}
         uberjar {:depends [jar]
                  :task (println "creating uberjar!")}}}
```




The `fs/modified-since` function returns a seq of all newer files compared to a target, which can be used to prevent rebuilding artifacts when not necessary.



Alternatively you can use the `:init` hook to define vars, require namespaces, etc.:




``` {.rouge .highlight}
{:tasks {:requires ([babashka.fs :as fs])
         :init (do (def target-dir  "target")
                   (def jar-file "target/foo.jar"))
         -target {:task (fs/create-dirs target-dir)}
         jar {:depends [-target]
              :task (when (seq (fs/modified-since jar-file
                                             (fs/glob "src" "**.clj")))
                      (spit jar-file "test")
                      (println "made jar!"))}
         uberjar {:depends [jar]
                  :task (println "creating uberjar!")}}}
```




It is common to define tasks that only serve as a helper to other tasks.  To not expose these tasks in the output of `bb tasks`, you can start their name with a hyphen.



### Parallel tasks


The `:parallel` option executes dependencies of the invoked task in parallel (when possible). This can be used to speed up execution, but also to have multiple tasks running in parallel for development:




``` {.rouge .highlight}
dev         {:doc  "Runs app in dev mode. Compiles cljs, less and runs JVM app in parallel."
             :task (run '-dev {:parallel true})}       (1)
-dev        {:depends [dev:cljs dev:less dev:backend]} (2)
dev:cljs    {:doc  "Runs front-end compilation"
             :task (clojure "-M:frontend:cljs/dev")}
dev:less    {:doc  "Compiles less"
             :task (clojure "-M:frontend:less/dev")}
dev:backend {:doc  "Runs backend in dev mode"
             :task (clojure (str "-A:backend:backend/dev:" platform-alias)
                            "-X" "dre.standalone/start")}
```




  ------- ---------------------------------------------------------------------------------
  **1**   The `dev` task invokes the (private) `-dev` task in parallel
  **2**   The `-dev` task depends on three other tasks which are executed simultaneously.
  ------- ---------------------------------------------------------------------------------




### Invoking a main function


Invoking a main function can be done by providing a fully qualified symbol:




``` {.rouge .highlight}
{:tasks
  {foo-bar foo.bar/-main}}
```




You can use any fully qualified symbol, not just ones that end in `-main` (so e.g. `foo.bar/baz` is fine). You can also have multiple main functions in one namespace.



The namespace `foo.bar` will be automatically required and the function will be invoked with `*command-line-args*`:




``` {.rouge .highlight}
$ bb foo-bar 1 2 3
```





### REPL


To get a REPL within a task, you can use `clojure.main/repl`:




``` {.rouge .highlight}
{:tasks {repl (clojure.main/repl)}}
```




Alternatively, you can use `babashka.tasks/run` to invoke a task from a REPL.



For REPL- and linting-friendliness, it's recommended to move task code longer than a couple of lines to a `.clj` or `.bb` file.




### Real world examples


-   [antq](https://github.com/borkdude/antq/blob/bb-run/bb.edn)

-   [mach](https://github.com/borkdude/mach/blob/bb-run/examples/app/bb.edn)

-   [bb.edn at Doctor
    Evidence](https://gist.github.com/borkdude/35bc0a20bd4c112dec2c5645f67250e3)

-   [clj-kondo.lsp](https://github.com/clj-kondo/clj-kondo.lsp/blob/master/bb.edn)

-   [pathom](https://github.com/wilkerlucio/pathom-viz/blob/master/bb.edn)

-   [rssyslib](https://github.com/redstarssystems/rssyslib/blob/develop/bb.edn)

-   [rewrite-clj](https://github.com/clj-commons/rewrite-clj/blob/main/bb.edn)

-   [https://gist.github.com/delyada/9f50fa7466358e55f27e4e6b4314242f](https://gist.github.com/delyada/9f50fa7466358e55f27e4e6b4314242f){.bare}

-   [jirazzz](https://github.com/rwstauner/jirazzz/blob/main/bb.edn)




### Naming


#### Valid names


When running a task, babashka assembles a small program which defines vars bound to the return values of tasks. This brings the limitation that you can only choose names for your tasks that are valid as var names. You can't name your task `foo/bar` for this reason. If you want to use delimiters to indicate some sort of grouping, you can do it like `foo-bar`, `foo:bar` or `foo_bar`.


Names starting with a `-` are considered \"private\" and not listed in the `bb tasks` output.




#### Conflicting file / task / subcommand names


`bb <option>` is resolved in the order of file \> task \> subcommand.



Escape hatches in case of conflicts:



-   execute relative file as `bb ./foo`

-   execute task as `bb run foo`

-   execute subcommand as `bb --foo`



When choosing a task name that overrides a babashka builtin subcommand, you have to provide the `:override-builtin` option to get rid of the warning that appears when running babashka:



``` {.rouge .highlight}
$ bb -Sdeps '{:tasks {help {:task (prn :help)}}}' help
[babashka] WARNING: task(s) 'help' override built-in command(s).
:help
```





``` {.rouge .highlight}
$ bb -Sdeps '{:tasks {help {:task (prn :help) :override-builtin true}}}' help
:help
```





#### Conflicting task and clojure.core var names


You can name a task similar to a core var, let's say: `format`. If you want to refer to the core var, it is recommended to use the fully qualified `clojure.core/format` in that case, to avoid conflicts in `:enter` and `:leave` expressions and when using the `format` task as a dependency.





### Syntax


Because `bb.edn` is an EDN file, you cannot use all of Clojure's syntax in expressions. Most notably:



-   You cannot use `#(foo %)`, but you can use `(fn [x] (foo x))`

-   You cannot use `@(foo)` but you can use `(deref foo)`

-   You cannot use `#"re"` but you can use `(re-pattern "re")`

-   Single quotes are accidentally supported in some places, but are better avoided: `{:task '(foo)}` does not work, but `{:task (quote (foo))` does work. When requiring namespaces, use the `:requires` feature in favor of doing it manually using `(require '[foo])`.






## Babashka CLI



In version `0.9.160` of babashka, the [babashka CLI](https://github.com/babashka/cli) added as a built-in library together with task integration.



### -x


For invoking functions from the command line, you can use the new `-x` flag (a pun to Clojure's `-X` of course!):




``` {.rouge .highlight}
bb -x clojure.core/prn --hello there
{:hello "there"}
```




What we see in the above snippet is that a map `{:hello "there"}` is constructed by babashka CLI and then fed to the `prn` function. After that the result is printed to the console.



What if we want to influence how things are parsed by babashka CLI and provide some defaults? This can be done using metadata. Let's create a `bb.edn` and make a file available on the classpath:


`bb.edn`:




``` {.rouge .highlight}
{:paths ["."]}
```




`tasks.clj`:




``` {.rouge .highlight}
(ns tasks
  {:org.babashka/cli {:exec-args {:ns-data 1}}})

(defn my-function
  {:org.babashka/cli {:exec-args {:fn-data 1}
                      :coerce {:num [:int]}
                      :alias {:n :num}}}
  [m]
  (prn m))
```




Now let's invoke:




``` {.rouge .highlight}
$ bb -x tasks/my-function -n 1 2
{:ns-data 1, :fn-data 1, :num [1 2]}
```




As you can see, the namespace options are merged with the function options. Defaults can be provided with `:exec-args`, like you're used to from the clojure CLI.




### exec


What about task integration? Let's adapt our `bb.edn`:




``` {.rouge .highlight}
{:paths ["."]
 :tasks {doit {:task (let [x (exec 'tasks/my-function)]
                       (prn :x x))
               :exec-args {:task-data 1234}}
         }}
```




and invoke the task:




``` {.rouge .highlight}
$ bb doit --cli-option :yeah -n 1 2 3
:x {:ns-data 1, :fn-data 1, :task-data 1234, :cli-option :yeah, :num [1 2 3]}
```




As you can see it works similar to `-x`, but you can provide another set of defaults on the task level with `:exec-args`. Executing a function through babashka CLI is done using the `babashka.task/exec` function, available by default in tasks.



To add `:exec-args` that should be evaluated you can pass an extra map to `exec` as follows:




``` {.rouge .highlight}
{:paths ["."]
 :tasks {doit {:task (let [x (exec 'tasks/my-function {:exec-args {:foo (+ 1 2 3)}})]
                       (prn :x x))
               :exec-args {:task-data 1234}}
         }}
```





``` {.rouge .highlight}
$ bb doit --cli-option :yeah -n 1 2 3
:x {:ns-data 1, :fn-data 1, :task-data 1234, :cli-option :yeah, :num [1 2 3] :foo 6}
```







## Libraries



### Built-in namespaces


In addition to `clojure.core`, the following libraries / namespaces are available in babashka. Some are available through pre-defined aliases in the `user` namespace, which can be handy for one-liners. If not all vars are available, they are enumerated explicitly. If some important var is missing, an issue or PR is welcome.



From Clojure:



-   `clojure.core`

-   `clojure.core.protocols`: `Datafiable`, `Navigable`

-   `clojure.data`

-   `clojure.datafy`

-   `clojure.edn` aliased as `edn`

-   `clojure.math`

-   `clojure.java.browse`

-   `clojure.java.io` aliased as `io`:

    -   `as-relative-path`, `as-url`, `copy`, `delete-file`, `file`, `input-stream`, `make-parents`, `output-stream`, `reader`, `resource`, `writer`

-   `clojure.java.shell` aliased as `shell`

-   `clojure.main`: `demunge`, `repl`, `repl-requires`

-   `clojure.pprint`: `pprint`, `cl-format`

-   `clojure.set` aliased as `set`

-   `clojure.string` aliased as `str`

-   `clojure.stacktrace`

-   `clojure.test`

-   `clojure.walk`

-   `clojure.zip`



Additional libraries:



-   [`babashka.cli`](https://github.com/babashka/cli): CLI arg parsing

-   [`babashka.http-client`](https://github.com/babashka/http-client): making HTTP requests

-   [`babashka.process`](https://github.com/babashka/process): shelling out to external processes

-   [`babashka.fs`](https://github.com/babashka/fs): file system manipulation

-   [`bencode.core`](https://github.com/nrepl/bencode) aliased as `bencode`: `read-bencode`, `write-bencode`

-   [`cheshire.core`](https://github.com/dakrone/cheshire) aliased as `json`: dealing with JSON

-   [`clojure.core.async`](https://clojure.github.io/core.async/) aliased as `async`.

-   [`clojure.data.csv`](https://github.com/clojure/data.csv) aliased as `csv`

-   [`clojure.data.xml`](https://github.com/clojure/data.xml) aliased as `xml`

-   [`clojure.tools.cli`](https://github.com/clojure/tools.cli) aliased as `tools.cli`

-   [`clj-yaml.core`](https://github.com/clj-commons/clj-yaml) alias as `yaml`

-   [`cognitect.transit`](https://github.com/cognitect/transit-clj) aliased as `transit`

-   [`org.httpkit.client`](https://github.com/http-kit/http-kit)

-   [`org.httpkit.server`](https://github.com/http-kit/http-kit)

-   [`clojure.core.match`](https://github.com/clojure/core.match)

-   [`hiccup.core`](https://github.com/weavejester/hiccup/) and `hiccup2.core`

-   [`clojure.test.check`](https://github.com/clojure/test.check):

    -   `clojure.test.check`

    -   `clojure.test.check.generators`

    -   `clojure.test.check.properties`

-   [`rewrite-clj`](https://github.com/clj-commons/rewrite-clj):

    -   `rewrite-clj.parser`

    -   `rewrite-clj.node`

    -   `rewrite-clj.zip`

    -   `rewrite-clj.paredit`

-   [`Selmer`](https://github.com/yogthos/Selmer):

    -   `selmer.parser`

-   [`clojure.tools.logging`](https://github.com/clojure/tools.logging)

-   [`timbre`](https://github.com/ptaoussanis/timbre): logging

-   [`edamame`](https://github.com/borkdude/edamame): Clojure parser

-   [`core.rrb-vector`](https://github.com/clojure/core.rrb-vector)



Check out the [babashka toolbox](https://babashka.org/toolbox/) and [projects](https://github.com/borkdude/babashka/blob/master/doc/projects.md) page for libraries that are not built-in, but which you can load as an external dependency in [`bb.edn`](https://book.babashka.org/#_bb_edn).



See the [build](https://github.com/borkdude/babashka/blob/master/doc/build.md) page for built-in libraries that can be enabled via feature flags, if you want to compile babashka yourself.



A selection of Java classes are available, see [`babashka/impl/classes.clj`](https://github.com/babashka/babashka/blob/master/src/babashka/impl/classes.clj) in babashka's git repo.



### Babashka namespaces


#### babashka.classpath


Available functions:



-   `add-classpath`

-   `get-classpath`

-   `split-classpath`



##### add-classpath


The function `add-classpath` which can be used to add to the classpath dynamically:




``` {.rouge .highlight}
(require '[babashka.classpath :refer [add-classpath]]
         '[clojure.java.shell :refer [sh]]
         '[clojure.string :as str])

(def medley-dep '{:deps {medley {:git/url "https://github.com/borkdude/medley"
                                 :sha "91adfb5da33f8d23f75f0894da1defe567a625c0"}}})
(def cp (-> (sh "clojure" "-Spath" "-Sdeps" (str medley-dep)) :out str/trim))
(add-classpath cp)
(require '[medley.core :as m])
(m/index-by :id [{:id 1} {:id 2}]) ;;=> {1 {:id 1}, 2 {:id 2}}
```





##### get-classpath


The function `get-classpath` returns the classpath as set by `--classpath`, `BABASHKA_CLASSPATH` and `add-classpath`.




##### split-classpath


Given a classpath, returns a seq of strings as the result of splitting the classpath by the platform specific path separatator.





#### babashka.deps


Available functions:



-   `add-deps`

-   `clojure`

-   `merge-deps`



##### add-deps


The function `add-deps` takes a deps edn map like `{:deps {medley/medley {:mvn/version "1.3.0"}}}`, resolves it using [deps.clj](https://github.com/borkdude/deps.clj) and then adds to the babashka classpath accordingly.



Example:




``` {.rouge .highlight}
(require '[babashka.deps :as deps])

(deps/add-deps '{:deps {medley/medley {:mvn/version "1.3.0"}}})

(require '[medley.core :as m])
(m/index-by :id [{:id 1} {:id 2}])
```




Optionally, `add-deps` takes a second arg with options. Currently the only option is `:aliases` which will affect how deps are resolved:



Example:




``` {.rouge .highlight}
(deps/add-deps '{:aliases {:medley {:extra-deps {medley/medley {:mvn/version "1.3.0"}}}}}
               {:aliases [:medley]})
```





##### clojure


The function `clojure` takes a sequential collection of arguments, similar to the clojure CLI. The arguments are then passed to [deps.clj](https://github.com/borkdude/deps.clj). The `clojure` function returns `nil` and prints to `*out*` for commands like `-Stree`, and `-Spath`. For `-M`, `-X` and `-A` it invokes `java` with `babashka.process/process` (see [babashka.process](#babashkaprocess)) and returns the associated record. For more details, read the docstring with:




``` {.rouge .highlight}
(require '[clojure.repl :refer [doc]])
(doc babashka.deps/clojure)
```




Example:



The following script passes through command line arguments to clojure, while adding the medley dependency:




``` {.rouge .highlight}
(require '[babashka.deps :as deps])

(def deps '{:deps {medley/medley {:mvn/version "1.3.0"}}})
(def clojure-args (list* "-Sdeps" deps  *command-line-args*))

(if-let [proc (deps/clojure clojure-args)]
  (-> @proc :exit (System/exit))
  (System/exit 0))
```






#### babashka.wait


Contains the functions: `wait-for-port` and `wait-for-path`.



Usage of `wait-for-port`:




``` {.rouge .highlight}
(wait/wait-for-port "localhost" 8080)
(wait/wait-for-port "localhost" 8080 {:timeout 1000 :pause 1000})
```




Waits for TCP connection to be available on host and port. Options map supports `:timeout` and `:pause`. If `:timeout` is provided and reached, `:default`\'s value (if any) is returned. The `:pause` option determines the time waited between retries.



Usage of `wait-for-path`:




``` {.rouge .highlight}
(wait/wait-for-path "/tmp/wait-path-test")
(wait/wait-for-path "/tmp/wait-path-test" {:timeout 1000 :pause 1000})
```




Waits for file path to be available. Options map supports `:default`, `:timeout` and `:pause`. If `:timeout` is provided and reached, `:default`\'s value (if any) is returned. The `:pause` option determines the time waited between retries.



The namespace `babashka.wait` is aliased as `wait` in the `user` namespace.




#### babashka.signal


Contains the function `signal/pipe-signal-received?`. Usage:




``` {.rouge .highlight}
(signal/pipe-signal-received?)
```




Returns true if `PIPE` signal was received. Example:




``` {.rouge .highlight}
$ bb -e '((fn [x] (println x) (when (not (signal/pipe-signal-received?)) (recur (inc x)))) 0)' | head -n2
1
2
```




The namespace `babashka.signal` is aliased as `signal` in the `user` namespace.




#### babashka.http-client


The `babashka.http-client` library for making HTTP requests. See [babashka.http-client](https://github.com/babashka/http-client) for how to use it.




#### babashka.process


The `babashka.process` library. See the [process](https://github.com/babashka/process) repo for API docs.




#### babashka.fs


The `babashka.fs` library offers file system utilities. See the [fs](https://github.com/babashka/fs) repo for API docs.




#### babashka.cli


The `babashka.cli` library allows you to turn functions into CLIs. See the [cli](https://github.com/babashka/cli) repo for API docs and check out the [babashka CLI](https://book.babashka.org/#_babashka_cli) chapter on how to use it from the command line or with [tasks](https://book.babashka.org/#tasks).





### Projects


Babashka is able to run Clojure projects from source, if they are compatible with the subset of Clojure that sci is capable of running.



Check this [page](https://github.com/borkdude/babashka/blob/master/doc/projects.md) for projects that are known to work with babashka.



Do you have a library that is compatible with babashka? Add the official badge to give some flair to your repo!



[[![svg+xml;base64,PHN2ZyB3aWR0aD0iMTQiIGhlaWdodD0iMTQiIHZpZXdCb3g9IjAgMCAxNDcyIDE0NzIiIGZpbGw9Im5vbmUiIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyI+CjxwYXRoIGZpbGwtcnVsZT0iZXZlbm9kZCIgY2xpcC1ydWxlPSJldmVub2RkIiBkPSJNOTk1LjkxMSAxMTkzLjg5QzExMTAuOTMgMTExMi4zOCAxMTg2IDk3OC4yMDYgMTE4NiA4MjYuNUMxMTg2IDU3Ny45NzIgMTAxNCAyNTQuNSA3MzYgMTEzQzQ2MC41IDI2MiAyODYgNTc3Ljk3MiAyODYgODI2LjVDMjg2IDk3OC4yMDYgMzYxLjA3IDExMTIuMzggNDc2LjA4OSAxMTkzLjg5QzQ3MS45ODMgMTE5NC40NCA0NjcuOTQ5IDExOTUuMTQgNDY0IDExOTZDMzc2LjYxMSAxMjE1LjA3IDI3MCAxMzU5LjA1IDI3MCAxMzU5LjA1SDUzNEM1ODAuOTcyIDEzNTkuMDUgNjI1LjYzMSAxMzQxLjQxIDY2MC45NTYgMTMyMS42MkM2NzguMTE1IDEzNDQuMzIgNzA1LjM0NCAxMzU5IDczNiAxMzU5Qzc2Ni42NTYgMTM1OSA3OTMuODg1IDEzNDQuMzIgODExLjA0NCAxMzIxLjYyQzg0Ni4zNjkgMTM0MS40MSA4OTEuMDI4IDEzNTkuMDUgOTM4IDEzNTkuMDVIMTIwMkMxMjAyIDEzNTkuMDUgMTA5NS4zOSAxMjE1LjA3IDEwMDggMTE5NkMxMDA0LjA1IDExOTUuMTQgMTAwMC4wMiAxMTk0LjQ0IDk5NS45MTEgMTE5My44OVoiIGZpbGw9InVybCgjcGFpbnQwX2xpbmVhcikiLz4KPGNpcmNsZSBjeD0iNzM1LjUiIGN5PSI4NTIuNSIgcj0iMzExLjUiIGZpbGw9IndoaXRlIi8+CjxwYXRoIGQ9Ik00NDMgNzUwLjQxN0M2NjIgNzY1Ljg2NiA3OTEuMzgyIDcxMi4zODggODgxIDU3OS44ODFDODgxIDU3OS44ODEgNzA4LjI5MyA1MjEuNjQ5IDYwMy4yNDQgNTc5Ljg4MUM0OTguNTUgNjM3LjkxNiA0NDMgNzUwLjQxNyA0NDMgNzUwLjQxN1oiIGZpbGw9IiNCNEI0QjQiLz4KPHBhdGggZD0iTTEwMzkuOTkgNzQ4LjI4MkM5MTQuODYyIDczNC43NDUgODAzLjI2NCA2OTQuMTM1IDc1MCA1NDFDNzUwIDU0MSA4NjAuNjMzIDU2Ni4yNzIgOTcyLjM1NSA2NjguMTAxQzEwODguMzkgNzczLjg1NyAxMDM5Ljk5IDc0OC4yODIgMTAzOS45OSA3NDguMjgyWiIgZmlsbD0iI0I0QjRCNCIvPgo8Y2lyY2xlIGN4PSI3MzUuNSIgY3k9Ijg1Mi41IiByPSIzMTEuNSIgc3Ryb2tlPSIjMEEwMDAwIiBzdHJva2Utd2lkdGg9IjQwIi8+CjxwYXRoIGQ9Ik03OTcgNzE5SDY3NEw3MDQgNzQ5VjgzOUM3MDguNSA4MDkuNSA3NjQuNSA4MDguNSA3NjcuNSA4MzlMNzc1IDc0OUw3OTcgNzE5WiIgZmlsbD0iYmxhY2siLz4KPHBhdGggZD0iTTM1MS4xMTcgNzU5QzM1MS4xMTcgNzM2LjkwOSAzNjkuMDI2IDcxOSAzOTEuMTE3IDcxOUg2NzYuMDE4QzcwMC4yIDcxOSA3MTguODUyIDc0MC4yOTIgNzE1LjY3IDc2NC4yNjRMNjkwLjkxIDk1MC43NjRDNjg4LjI3IDk3MC42NDYgNjcxLjMxNCA5ODUuNSA2NTEuMjU4IDk4NS41SDM5MS4xMTdDMzY5LjAyNiA5ODUuNSAzNTEuMTE3IDk2Ny41OTEgMzUxLjExNyA5NDUuNVY3NTlaIiBmaWxsPSJibGFjayIvPgo8cGF0aCBkPSJNNzU2LjAxMiA3NjQuMjY3Qzc1Mi44MjggNzQwLjI5NCA3NzEuNDgxIDcxOSA3OTUuNjY0IDcxOUgxMDgwQzExMDIuMDkgNzE5IDExMjAgNzM2LjkwOSAxMTIwIDc1OVY5NDUuMDk2QzExMjAgOTY3LjE4NyAxMTAyLjA5IDk4NS4wOTYgMTA4MCA5ODUuMDk2SDgyMC4zODFDODAwLjMyNSA5ODUuMDk2IDc4My4zNyA5NzAuMjQ0IDc4MC43MjkgOTUwLjM2Mkw3NTYuMDEyIDc2NC4yNjdaIiBmaWxsPSJibGFjayIvPgo8cGF0aCBkPSJNMTAyNCA5NTJWOTIxLjQ3SDEwMTMuNzFDMTAwNy4wMyA5MjEuNDcgMTAwMi41OCA5MTYuNzUyIDEwMDAuMzUgOTA5LjgxM0w5NTguMDY0IDc4Mi42OTdDOTU1LjI4MiA3NzQuNjQ5IDk1MS45NDQgNzY4LjgyIDk0Ny40OTIgNzY0LjM3OUM5MzkuMTQ2IDc1NS43NzUgOTI4LjI5NiA3NTMgOTE2LjA1NCA3NTNIOTAyLjdWNzg0LjkxOEg5MTEuNjAzQzkxOS4xMTUgNzg0LjkxOCA5MjQuOTU3IDc4Ny42OTMgOTI3LjQ2MSA3OTYuNTc1TDkzMy4zMDMgODE3LjExM0w4ODEgOTUySDkxOS4xMTVMOTUwLjU1MyA4NjMuMTg1TDk2Ny4yNDUgOTE2Ljc1MkM5NzMuNjQ0IDkzNy41NjggOTg0LjQ5NCA5NTIgMTAwOC45OCA5NTJIMTAyNFoiIGZpbGw9IndoaXRlIi8+CjxwYXRoIGQ9Ik01OTAgOTUyVjkyMS40N0g1NzkuNzA2QzU3My4wMjkgOTIxLjQ3IDU2OC41NzggOTE2Ljc1MiA1NjYuMzUyIDkwOS44MTNMNTI0LjA2NCA3ODIuNjk3QzUyMS4yODIgNzc0LjY0OSA1MTcuOTQ0IDc2OC44MiA1MTMuNDkyIDc2NC4zNzlDNTA1LjE0NiA3NTUuNzc1IDQ5NC4yOTYgNzUzIDQ4Mi4wNTQgNzUzSDQ2OC43Vjc4NC45MThINDc3LjYwM0M0ODUuMTE1IDc4NC45MTggNDkwLjk1NyA3ODcuNjkzIDQ5My40NjEgNzk2LjU3NUw0OTkuMzAzIDgxNy4xMTNMNDQ3IDk1Mkg0ODUuMTE1TDUxNi41NTMgODYzLjE4NUw1MzMuMjQ1IDkxNi43NTJDNTM5LjY0NCA5MzcuNTY4IDU1MC40OTQgOTUyIDU3NC45NzcgOTUySDU5MFoiIGZpbGw9IndoaXRlIi8+CjxkZWZzPgo8bGluZWFyR3JhZGllbnQgaWQ9InBhaW50MF9saW5lYXIiIHgxPSI3NTIiIHkxPSIxMTMiIHgyPSI3NTIiIHkyPSIxMzU5LjUyIiBncmFkaWVudFVuaXRzPSJ1c2VyU3BhY2VPblVzZSI+CjxzdG9wIHN0b3AtY29sb3I9IiNFNDFGMjYiLz4KPHN0b3Agb2Zmc2V0PSIxIiBzdG9wLWNvbG9yPSIjQjcwMDAwIi8+CjwvbGluZWFyR3JhZGllbnQ+CjwvZGVmcz4KPC9zdmc+Cg==](https://img.shields.io/badge/babashka-compatible-green?logo=data:image/svg+xml;base64,PHN2ZyB3aWR0aD0iMTQiIGhlaWdodD0iMTQiIHZpZXdCb3g9IjAgMCAxNDcyIDE0NzIiIGZpbGw9Im5vbmUiIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyI+CjxwYXRoIGZpbGwtcnVsZT0iZXZlbm9kZCIgY2xpcC1ydWxlPSJldmVub2RkIiBkPSJNOTk1LjkxMSAxMTkzLjg5QzExMTAuOTMgMTExMi4zOCAxMTg2IDk3OC4yMDYgMTE4NiA4MjYuNUMxMTg2IDU3Ny45NzIgMTAxNCAyNTQuNSA3MzYgMTEzQzQ2MC41IDI2MiAyODYgNTc3Ljk3MiAyODYgODI2LjVDMjg2IDk3OC4yMDYgMzYxLjA3IDExMTIuMzggNDc2LjA4OSAxMTkzLjg5QzQ3MS45ODMgMTE5NC40NCA0NjcuOTQ5IDExOTUuMTQgNDY0IDExOTZDMzc2LjYxMSAxMjE1LjA3IDI3MCAxMzU5LjA1IDI3MCAxMzU5LjA1SDUzNEM1ODAuOTcyIDEzNTkuMDUgNjI1LjYzMSAxMzQxLjQxIDY2MC45NTYgMTMyMS42MkM2NzguMTE1IDEzNDQuMzIgNzA1LjM0NCAxMzU5IDczNiAxMzU5Qzc2Ni42NTYgMTM1OSA3OTMuODg1IDEzNDQuMzIgODExLjA0NCAxMzIxLjYyQzg0Ni4zNjkgMTM0MS40MSA4OTEuMDI4IDEzNTkuMDUgOTM4IDEzNTkuMDVIMTIwMkMxMjAyIDEzNTkuMDUgMTA5NS4zOSAxMjE1LjA3IDEwMDggMTE5NkMxMDA0LjA1IDExOTUuMTQgMTAwMC4wMiAxMTk0LjQ0IDk5NS45MTEgMTE5My44OVoiIGZpbGw9InVybCgjcGFpbnQwX2xpbmVhcikiLz4KPGNpcmNsZSBjeD0iNzM1LjUiIGN5PSI4NTIuNSIgcj0iMzExLjUiIGZpbGw9IndoaXRlIi8+CjxwYXRoIGQ9Ik00NDMgNzUwLjQxN0M2NjIgNzY1Ljg2NiA3OTEuMzgyIDcxMi4zODggODgxIDU3OS44ODFDODgxIDU3OS44ODEgNzA4LjI5MyA1MjEuNjQ5IDYwMy4yNDQgNTc5Ljg4MUM0OTguNTUgNjM3LjkxNiA0NDMgNzUwLjQxNyA0NDMgNzUwLjQxN1oiIGZpbGw9IiNCNEI0QjQiLz4KPHBhdGggZD0iTTEwMzkuOTkgNzQ4LjI4MkM5MTQuODYyIDczNC43NDUgODAzLjI2NCA2OTQuMTM1IDc1MCA1NDFDNzUwIDU0MSA4NjAuNjMzIDU2Ni4yNzIgOTcyLjM1NSA2NjguMTAxQzEwODguMzkgNzczLjg1NyAxMDM5Ljk5IDc0OC4yODIgMTAzOS45OSA3NDguMjgyWiIgZmlsbD0iI0I0QjRCNCIvPgo8Y2lyY2xlIGN4PSI3MzUuNSIgY3k9Ijg1Mi41IiByPSIzMTEuNSIgc3Ryb2tlPSIjMEEwMDAwIiBzdHJva2Utd2lkdGg9IjQwIi8+CjxwYXRoIGQ9Ik03OTcgNzE5SDY3NEw3MDQgNzQ5VjgzOUM3MDguNSA4MDkuNSA3NjQuNSA4MDguNSA3NjcuNSA4MzlMNzc1IDc0OUw3OTcgNzE5WiIgZmlsbD0iYmxhY2siLz4KPHBhdGggZD0iTTM1MS4xMTcgNzU5QzM1MS4xMTcgNzM2LjkwOSAzNjkuMDI2IDcxOSAzOTEuMTE3IDcxOUg2NzYuMDE4QzcwMC4yIDcxOSA3MTguODUyIDc0MC4yOTIgNzE1LjY3IDc2NC4yNjRMNjkwLjkxIDk1MC43NjRDNjg4LjI3IDk3MC42NDYgNjcxLjMxNCA5ODUuNSA2NTEuMjU4IDk4NS41SDM5MS4xMTdDMzY5LjAyNiA5ODUuNSAzNTEuMTE3IDk2Ny41OTEgMzUxLjExNyA5NDUuNVY3NTlaIiBmaWxsPSJibGFjayIvPgo8cGF0aCBkPSJNNzU2LjAxMiA3NjQuMjY3Qzc1Mi44MjggNzQwLjI5NCA3NzEuNDgxIDcxOSA3OTUuNjY0IDcxOUgxMDgwQzExMDIuMDkgNzE5IDExMjAgNzM2LjkwOSAxMTIwIDc1OVY5NDUuMDk2QzExMjAgOTY3LjE4NyAxMTAyLjA5IDk4NS4wOTYgMTA4MCA5ODUuMDk2SDgyMC4zODFDODAwLjMyNSA5ODUuMDk2IDc4My4zNyA5NzAuMjQ0IDc4MC43MjkgOTUwLjM2Mkw3NTYuMDEyIDc2NC4yNjdaIiBmaWxsPSJibGFjayIvPgo8cGF0aCBkPSJNMTAyNCA5NTJWOTIxLjQ3SDEwMTMuNzFDMTAwNy4wMyA5MjEuNDcgMTAwMi41OCA5MTYuNzUyIDEwMDAuMzUgOTA5LjgxM0w5NTguMDY0IDc4Mi42OTdDOTU1LjI4MiA3NzQuNjQ5IDk1MS45NDQgNzY4LjgyIDk0Ny40OTIgNzY0LjM3OUM5MzkuMTQ2IDc1NS43NzUgOTI4LjI5NiA3NTMgOTE2LjA1NCA3NTNIOTAyLjdWNzg0LjkxOEg5MTEuNjAzQzkxOS4xMTUgNzg0LjkxOCA5MjQuOTU3IDc4Ny42OTMgOTI3LjQ2MSA3OTYuNTc1TDkzMy4zMDMgODE3LjExM0w4ODEgOTUySDkxOS4xMTVMOTUwLjU1MyA4NjMuMTg1TDk2Ny4yNDUgOTE2Ljc1MkM5NzMuNjQ0IDkzNy41NjggOTg0LjQ5NCA5NTIgMTAwOC45OCA5NTJIMTAyNFoiIGZpbGw9IndoaXRlIi8+CjxwYXRoIGQ9Ik01OTAgOTUyVjkyMS40N0g1NzkuNzA2QzU3My4wMjkgOTIxLjQ3IDU2OC41NzggOTE2Ljc1MiA1NjYuMzUyIDkwOS44MTNMNTI0LjA2NCA3ODIuNjk3QzUyMS4yODIgNzc0LjY0OSA1MTcuOTQ0IDc2OC44MiA1MTMuNDkyIDc2NC4zNzlDNTA1LjE0NiA3NTUuNzc1IDQ5NC4yOTYgNzUzIDQ4Mi4wNTQgNzUzSDQ2OC43Vjc4NC45MThINDc3LjYwM0M0ODUuMTE1IDc4NC45MTggNDkwLjk1NyA3ODcuNjkzIDQ5My40NjEgNzk2LjU3NUw0OTkuMzAzIDgxNy4xMTNMNDQ3IDk1Mkg0ODUuMTE1TDUxNi41NTMgODYzLjE4NUw1MzMuMjQ1IDkxNi43NTJDNTM5LjY0NCA5MzcuNTY4IDU1MC40OTQgOTUyIDU3NC45NzcgOTUySDU5MFoiIGZpbGw9IndoaXRlIi8+CjxkZWZzPgo8bGluZWFyR3JhZGllbnQgaWQ9InBhaW50MF9saW5lYXIiIHgxPSI3NTIiIHkxPSIxMTMiIHgyPSI3NTIiIHkyPSIxMzU5LjUyIiBncmFkaWVudFVuaXRzPSJ1c2VyU3BhY2VPblVzZSI+CjxzdG9wIHN0b3AtY29sb3I9IiNFNDFGMjYiLz4KPHN0b3Agb2Zmc2V0PSIxIiBzdG9wLWNvbG9yPSIjQjcwMDAwIi8+CjwvbGluZWFyR3JhZGllbnQ+CjwvZGVmcz4KPC9zdmc+Cg== "bb compatible")](https://babashka.org){.image}]{.image}






## Pods



Pods are programs that can be used as a Clojure library by babashka.  Documentation is available in the [library repo](https://github.com/babashka/babashka.pods).


A list of available pods can be found [here](https://github.com/borkdude/babashka/blob/master/doc/projects.md#pods).



### [Pod registry](#_pod_registry) {#_pod_registry}


Since bb 0.2.6 pods can be obtained via the [pod-registry](https://github.com/babashka/pod-registry).



This is an example script which uses the [fswatcher](https://github.com/babashka/pod-babashka-fswatcher) pod to watch a directory for changes:




``` {.rouge .highlight}
#!/usr/bin/env bb

(require '[babashka.pods :as pods])
(pods/load-pod 'org.babashka/fswatcher "0.0.3")

(require '[pod.babashka.fswatcher :as fw])

(fw/watch "." prn {:delay-ms 5000})

(println "Watching current directory for changes... Press Ctrl-C to quit.")

@(promise)
```





### Pods in bb.edn


Since bb 0.8.0 pods can be declared in `bb.edn`:




``` {.rouge .highlight}
{:paths ["bb"]
 :pods {org.babashka/go-sqlite3 {:version "0.1.0"}}}
```




Given the file `bb/my_project/db.clj`:




``` {.rouge .highlight}
(ns my-project.db
  (:require [pod.babashka.go-sqlite3 :as sqlite]))

(defn -main [& _args]
  (prn (sqlite/query ":memory:" ["SELECT 1 + 1 AS sum"])))
```




you can then execute the main function, without calling `load-pod` manually:




``` {.rouge .highlight}
$ bb -m my-project.db
[{:sum 2}]
```







## Style



A note on style. Babashka recommends the following:



### Explicit requires


Use explicit requires with namespace aliases in scripts, unless you're writing one-liners.



Do this:




``` {.rouge .highlight}
$ ls | bb -i '(-> *input* first (str/includes? "m"))'
true
```




But not this:



script.clj:




``` {.rouge .highlight}
(-> *input* first (str/includes? "m"))
```




Rather do this:



script.clj:




``` {.rouge .highlight}
(ns script
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))
(-> (io/reader *in*) line-seq first (str/includes? "m"))
```




Some reasons for this:



-   Linters like clj-kondo work better with code that uses namespace forms, explicit requires, and known Clojure constructs

-   Editor tooling works better with namespace forms (sorting requires, etc).

-   Writing compatible code gives you the option to run the same script with `clojure`






## Child processes



For child processes, the babashka [process](https://github.com/babashka/process) library is recommended.  It is built into babashka. Check out the README which gives a good introduction into the library.





## Recipes



### Running tests


Babashka bundles `clojure.test`. To run tests you can write a test runner script. Given the following project structure:




``` {.rouge .highlight}
.
├── src
│   └──...
└── test
    └── your
        ├── test_a.clj
        └── test_b.clj
```





*test-runner.clj*
``` {.rouge .highlight}
#!/usr/bin/env bb

(require '[clojure.test :as t]
         '[babashka.classpath :as cp])

(cp/add-classpath "src:test")                        (1)

(require 'your.test-a 'your.test-b)                  (2)

(def test-results
  (t/run-tests 'your.test-a 'your.test-b))           (3)

(let [{:keys [fail error]} test-results]
  (when (pos? (+ fail error))
    (System/exit 1)))                                (4)
```




  ------- ----------------------------------------------------------------------------------
  **1**   Add sources and tests to the classpath
  **2**   Require the test namespaces
  **3**   Run all tests in the test namespaces
  **4**   Exit the test script with a non-zero exit code when there are failures or errors
  ------- ----------------------------------------------------------------------------------




### Main file


In Python scripts there is a well-known pattern to check if the current file was the file invoked from the command line, or loaded from another file: the `__name__ == "__main__"` pattern. In babashka this pattern can be implemented with:




``` {.rouge .highlight}
(= *file* (System/getProperty "babashka.file"))
```




Combining this with a conditional invocation of `-main` creates a script file that is safe to load at a REPL, and easy to invoke at the CLI.




``` {.rouge .highlight}
#!/usr/bin/env bb

;; Various functions defined here

(defn -main [& args]
;; Implementation of main
)

(when (= *file* (System/getProperty "babashka.file"))
  (apply -main *command-line-args*))
```




This can be exceedingly handy for editing complex scripts interactively, while not being able to adjust how they are invoked by other tools.




### Shutdown hook


Adding a shutdown hook allows you to execute some code before the script exits.




``` {.rouge .highlight}
$ bb -e '(-> (Runtime/getRuntime) (.addShutdownHook (Thread. #(println "bye"))))'
bye
```




This also works when the script is interrupted with ctrl-c.




### Printing returned values


Babashka doesn't print a returned `nil` as lots of scripts end in something side-effecting.




``` {.rouge .highlight}
$ bb -e '(:a {:a 5})'
5
$ bb -e '(:b {:a 5})'
$
```




If you really want to print the nil, you can use `(prn ..)` instead.



#### HTTP over Unix sockets


This can be useful for talking to Docker:




``` {.rouge .highlight}
(require '[clojure.java.shell :refer [sh]])
(require '[cheshire.core :as json])
(-> (sh "curl" "--silent"
        "--no-buffer" "--unix-socket"
        "/var/run/docker.sock"
        "http://localhost/images/json")
    :out
    (json/parse-string true)
    first
    :RepoTags) ;;=> ["borkdude/babashka:latest"]
```






### Core.async


In addition to `future`, `pmap`, `promise` and friends, you may use the `clojure.core.async` namespace for asynchronous scripting. The following example shows how to get first available value from two different processes:




``` {.rouge .highlight}
bb -e '
(defn async-command [& args]
  (async/thread (apply shell/sh "bash" "-c" args)))

(-> (async/alts!! [(async-command "sleep 2 && echo process 1")
                   (async-command "sleep 1 && echo process 2")])
    first :out str/trim println)'
process 2
```




Caveat: currently the `go` macro is available for compatibility with JVM programs, but the implementation maps to `clojure.core.async/thread` and the single exclamation mark operations (`<!`, `>!`, etc.) map to the double exclamation mark operations (`<!!`, `>!!`, etc.). It will not \"park\" threads, like on the JVM.



Examples like the following may still work, but will take a lot more system resources than on the JVM and will break down for some high value of `n`:




``` {.rouge .highlight}
(require '[clojure.core.async :as async])

(def n 1000)

(let [cs (repeatedly n async/chan)
      begin (System/currentTimeMillis)]
  (doseq [c cs] (async/go (async/>! c "hi")))
  (dotimes [_ n]
    (let [[v _] (async/alts!! cs)]
      (assert (= "hi" v))))
  (println "Read" n "msgs in" (- (System/currentTimeMillis) begin) "ms"))
```





### Interacting with an nREPL server


Babashka comes with the [nrepl/bencode](https://github.com/nrepl/bencode) library which allows you to read and write bencode messages to a socket. A simple example which evaluates a Clojure expression on an nREPL server started with `lein repl`:




``` {.rouge .highlight}
(ns nrepl-client
  (:require [bencode.core :as b]))

(defn nrepl-eval [port expr]
  (let [s (java.net.Socket. "localhost" port)
        out (.getOutputStream s)
        in (java.io.PushbackInputStream. (.getInputStream s))
        _ (b/write-bencode out {"op" "eval" "code" expr})
        bytes (get (b/read-bencode in) "value")]
    (String. bytes)))

(nrepl-eval 52054 "(+ 1 2 3)") ;;=> "6"
```





### Running from Cygwin/Git Bash


On Windows, `bb` can be invoked from the bash shell directly:




``` {.rouge .highlight}
$ bb -e '(+ 1 2 3)'
6
```




However, creating a script that invokes `bb` via a shebang leads to an error if the script is not in the current directory. Suppose you had the following script named `hello` on your path:




``` {.rouge .highlight}
#!/usr/bin/env bb
(println "Hello, world!")
```





``` {.rouge .highlight}
$ hello
----- Error --------------------------------------------------------------------
Type:     java.lang.Exception
Message:  File does not exist: /cygdrive/c/path/to/hello
```




The problem here is that the shell is passing a Cygwin-style path to `bb`, but `bb` can't recognize it because it wasn't compiled with Cygwin.



The solution is to create a wrapper script that converts the Cygwin-style path to a Windows-style path before invoking `bb`. Put the following into a script called `bbwrap` somewhere on your Cygwin path, say in `/usr/local/bin/bbwrap`:




``` {.rouge .highlight}
#!/bin/bash
SCRIPT=$1
shift
bb.exe $(cygpath -w $SCRIPT) $@
```




Make sure to fix your original script to invoke `bbwrap` instead of `bb` directly:




``` {.rouge .highlight}
#!/usr/bin/env bbwrap
(println "Hello, world!")
```







## Differences with Clojure



Babashka is implemented using the [Small Clojure Interpreter](https://github.com/borkdude/sci). This means that a snippet or script is not compiled to JVM bytecode, but executed form by form by a runtime which implements a substantial subset of Clojure. Babashka is compiled to a native binary using [GraalVM](https://github.com/oracle/graal). It comes with a selection of built-in namespaces and functions from Clojure and other useful libraries. The data types (numbers, strings, persistent collections) are the same. Multi-threading is supported (`pmap`, `future`).



Differences with Clojure:



-   A pre-selected set of Java classes are supported. You cannot add Java classes at runtime.

-   Interpretation comes with overhead. Therefore loops are slower than in Clojure on the JVM. In general interpretation yields slower programs than compiled programs.

-   No `deftype`, `definterface` and unboxed math.

-   `defprotocol` and `defrecord` are implemented using multimethods and regular maps. Ostensibly they work the same, but under the hood there are no Java classes that correspond to them.

-   Currently `reify` works only for one class at a time

-   The `clojure.core.async/go` macro is not (yet) supported. For compatibility it currently maps to `clojure.core.async/thread`. More info [here](#core_async).





## Resources



Check out the list of resources in babashka's [README.md](https://github.com/babashka/babashka#articles-podcasts-and-videos).



### Books


#### [](#_babashka_babooka)[Babashka Babooka](https://www.braveclojure.com/quests/babooka/)


If you're a fan of [Clojure for the Brave and True](https://www.braveclojure.com/clojure-for-the-brave-and-true/), you might enjoy [Babashka Babooka](https://www.braveclojure.com/quests/babooka/), a book by the same author, Daniel Higginbotham!







## Contributing



Visit Babashka book's [Github repository](https://github.com/babashka/book) and make an issue and/or PR.





## License



Copyright © 2020-2021 Michiel Borkent



Licensed under [CC BY-SA 4.0](https://creativecommons.org/licenses/by-sa/4.0).







