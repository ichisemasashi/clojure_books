::: {#header}
# Babashka book

::: details
[Michiel Borkent]{#author .author}\
:::

::: {#toc .toc2}
::: {#toctitle}
Table of Contents
:::

-   [Introduction](#introduction)
    -   [Target audience](#_target_audience)
    -   [Setting expectations](#_setting_expectations)
-   [Getting started](#getting_started)
    -   [Installation](#_installation)
    -   [Building from source](#_building_from_source)
    -   [Running babashka](#_running_babashka)
-   [Usage](#usage)
    -   [Running a script](#_running_a_script)
    -   [Current file path](#_current_file_path)
    -   [Parsing command line
        arguments](#_parsing_command_line_arguments)
    -   [Classpath](#_classpath)
    -   [Invoking a main function](#main-function)
    -   [Preloads](#_preloads)
    -   [Running a REPL](#repl)
    -   [Input and output flags](#_input_and_output_flags)
    -   [Uberscript](#_uberscript)
    -   [Uberjar](#_uberjar)
    -   [System properties](#_system_properties)
    -   [Data readers](#_data_readers)
    -   [Reader conditionals](#_reader_conditionals)
    -   [Invoking clojure](#_invoking_clojure)
-   [Project setup](#project-setup)
    -   [bb.edn](#_bb_edn)
    -   [:paths and :deps](#_paths_and_deps)
    -   [:min-bb-version](#_min_bb_version)
    -   [:tasks](#_tasks)
    -   [Script-adjacent bb.edn](#_script_adjacent_bb_edn)
-   [Task runner](#tasks)
    -   [Introduction](#_introduction)
    -   [Talk](#_talk)
    -   [Task-local options](#_task_local_options)
    -   [Discoverability](#_discoverability)
    -   [Command line arguments](#_command_line_arguments)
    -   [Run](#_run)
    -   [Hooks](#_hooks)
    -   [Tasks API](#_tasks_api)
    -   [Dependencies between tasks](#_dependencies_between_tasks)
    -   [Parallel tasks](#parallel)
    -   [Invoking a main function](#_invoking_a_main_function)
    -   [REPL](#_repl_2)
    -   [Real world examples](#_real_world_examples)
    -   [Naming](#_naming)
    -   [Syntax](#_syntax)
-   [Babashka CLI](#cli)
    -   [-x](#_x)
    -   [exec](#cli:exec)
-   [Libraries](#libraries)
    -   [Built-in namespaces](#built-in-namespaces)
    -   [Babashka namespaces](#_babashka_namespaces)
    -   [Projects](#_projects)
-   [Pods](#pods)
    -   [Pod registry](#_pod_registry)
    -   [Pods in bb.edn](#_pods_in_bb_edn)
-   [Style](#style)
    -   [Explicit requires](#_explicit_requires)
-   [Child processes](#child_processes)
-   [Recipes](#recipes)
    -   [Running tests](#_running_tests)
    -   [Main file](#main_file)
    -   [Shutdown hook](#_shutdown_hook)
    -   [Printing returned values](#_printing_returned_values)
    -   [Core.async](#core_async)
    -   [Interacting with an nREPL
        server](#_interacting_with_an_nrepl_server)
    -   [Running from Cygwin/Git Bash](#_running_from_cygwingit_bash)
-   [Differences with Clojure](#differences-with-clojure)
-   [Resources](#resources)
    -   [Books](#_books)
-   [Contributing](#contributing)
-   [License](#license)
:::
:::

::: {#content}
::: {#preamble}
::: sectionbody
:::
:::

::: sect1
## [Introduction](#introduction){.link}

::: sectionbody
::: paragraph
Welcome reader! This is a book about scripting with Clojure and
babashka. [Clojure](https://www.clojure.org) is a functional, dynamic
programming language from the Lisp family which runs on the JVM.
Babashka is a scripting environment made with Clojure, compiled to
native with [GraalVM](https://www.graalvm.org). The primary benefits of
using babashka for scripting compared to the JVM are fast startup time
and low memory consumption. Babashka comes with batteries included and
packs libraries like `clojure.tools.cli` for parsing command line
arguments and `cheshire` for working with JSON. Moreover, it can be
installed just by downloading a self-contained binary.
:::

::: sect2
### [Target audience](#_target_audience){.link} {#_target_audience}

::: paragraph
Babashka is written for developers who are familiar with Clojure on the
JVM. This book assumes familiarity with Clojure and is not a Clojure
tutorial. If you aren't that familiar with Clojure but you're curious to
learn, check out
[this](https://gist.github.com/yogthos/be323be0361c589570a6da4ccc85f58f)
list of beginner resources.
:::
:::

::: sect2
### [Setting expectations](#_setting_expectations){.link} {#_setting_expectations}

::: paragraph
Babashka uses [SCI](https://github.com/babashka/SCI) for interpreting
Clojure. SCI implements a substantial subset of Clojure. Interpreting
code is in general not as performant as executing compiled code. If your
script takes more than a few seconds to run or has lots of loops,
Clojure on the JVM may be a better fit, as the performance on JVM is
going to outweigh its startup time penalty. Read more about the
differences with Clojure [here](#differences-with-clojure).
:::
:::
:::
:::

::: sect1
## [Getting started](#getting_started){.link} {#getting_started}

::: sectionbody
::: sect2
### [Installation](#_installation){.link} {#_installation}

::: paragraph
Installing babashka is as simple as downloading the binary for your
platform and placing it on your path. Pre-built binaries are provided on
the [releases](https://github.com/borkdude/babashka/releases) page of
babashka's [Github repo](https://github.com/borkdude/babashka). Babashka
is also available in various package managers like `brew` for macOS and
linux and `scoop` for Windows. See
[here](https://github.com/borkdude/babashka#installation) for details.
:::
:::

::: sect2
### [Building from source](#_building_from_source){.link} {#_building_from_source}

::: paragraph
If you would rather build babashka from source, download a copy of
GraalVM and set the `GRAALVM_HOME` environment variable. Also make sure
you have [lein](https://leiningen.org) installed. Then run:
:::

::: listingblock
::: content
``` {.rouge .highlight}
$ git clone https://github.com/borkdude/babashka --recursive
$ script/uberjar && script/compile
```
:::
:::

::: paragraph
See the babashka
[build.md](https://github.com/borkdude/babashka/blob/master/doc/build.md)
page for details.
:::
:::

::: sect2
### [Running babashka](#_running_babashka){.link} {#_running_babashka}

::: paragraph
The babashka executable is called `bb`. You can either provide it with a
Clojure expression directly:
:::

::: listingblock
::: content
``` {.rouge .highlight}
$ bb -e '(+ 1 2 3)'
6
```
:::
:::

::: paragraph
or run a script:
:::

::: listingblock
::: title
script.clj
:::

::: content
``` {.rouge .highlight}
(println (+ 1 2 3))
```
:::
:::

::: listingblock
::: content
``` {.rouge .highlight}
$ bb -f script.clj
6
```
:::
:::

::: paragraph
The `-e` flag is optional when the argument starts with a paren. In that
case babashka will treat it automatically as an expression:
:::

::: listingblock
::: content
``` {.rouge .highlight}
$ bb '(+ 1 2 3)'
6
```
:::
:::

::: paragraph
Similarly, the `-f` flag is optional when the argument is a filename:
:::

::: listingblock
::: content
``` {.rouge .highlight}
$ bb script.clj
6
```
:::
:::

::: paragraph
Commonly, scripts have shebangs so you can invoke them with their
filename only:
:::

::: listingblock
::: title
script.clj
:::

::: content
``` {.rouge .highlight}
#!/usr/bin/env bb
(println (+ 1 2 3))
```
:::
:::
:::
:::
:::

::: sect1
## [Usage](#usage){.link}

::: sectionbody
::: paragraph
Typing `bb help` from the command line will print all the available
command line options which should give you a sense of the available
features in babashka.
:::

::: listingblock
::: content
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
:::
:::

::: sect2
### [Running a script](#_running_a_script){.link} {#_running_a_script}

::: paragraph
Scripts may be executed from a file using `-f` or `--file`:
:::

::: listingblock
::: content
``` {.rouge .highlight}
bb -f download_html.clj
```
:::
:::

::: paragraph
The file may also be passed directly, without `-f`:
:::

::: listingblock
::: content
``` {.rouge .highlight}
bb download_html.clj
```
:::
:::

::: paragraph
Using `bb` with a shebang also works:
:::

::: listingblock
::: content
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
:::
:::

::: listingblock
::: content
``` {.rouge .highlight}
$ ./download_html.clj
Usage: <url> <file>

$ ./download_html.clj https://www.clojure.org /tmp/clojure.org.html
Downloading url: https://www.clojure.org
Writing file: /tmp/clojure.org.html
```
:::
:::

::: paragraph
If `/usr/bin/env` doesn't work for you, you can use the following
workaround:
:::

::: listingblock
::: content
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
:::
:::
:::

::: sect2
### [Current file path](#_current_file_path){.link} {#_current_file_path}

::: paragraph
The var `*file*` contains the full path of the file that is currently
being executed:
:::

::: listingblock
::: content
``` {.rouge .highlight}
$ cat example.clj
(prn *file*)

$ bb example.clj
"/Users/borkdude/example.clj"
```
:::
:::
:::

::: sect2
### [Parsing command line arguments](#_parsing_command_line_arguments){.link} {#_parsing_command_line_arguments}

::: paragraph
Command-line arguments can be retrieved using `*command-line-args*`. If
you want to parse command line arguments, you can use the built-in
[`babashka.cli`](https://github.com/babashka/cli) namespace:
:::

::: listingblock
::: content
``` {.rouge .highlight}
(require '[babashka.cli :as cli])

(def cli-options {:port {:default 80 :coerce :long}
                  :help {:coerce :boolean}})

(prn (cli/parse-opts *command-line-args* {:spec cli-options}))
```
:::
:::

::: listingblock
::: content
``` {.rouge .highlight}
$ bb script.clj
{:port 80}
$ bb script.clj --port 1223
{:port 1223}
$ bb script.clj --help
{:port 80, :help true}
```
:::
:::

::: paragraph
Note that [clojure.tools.cli](https://github.com/clojure/tools.cli) is
also built-in to babashka.
:::
:::

::: sect2
### [Classpath](#_classpath){.link} {#_classpath}

::: paragraph
It is recommended to use `bb.edn` to control what directories and
libraries are included on babashka's classpath. See [Project
setup](#project-setup)
:::

::: paragraph
If you want a lower level to control babashka's classpath, without the
usage of `bb.edn` you can use the `--classpath` option that will
override the classpath. Say we have a file `script/my/namespace.clj`:
:::

::: listingblock
::: content
``` {.rouge .highlight}
(ns my.namespace)
(defn -main [& args]
  (apply println "Hello from my namespace!" args))
```
:::
:::

::: paragraph
Now we can execute this main function with:
:::

::: listingblock
::: content
``` {.rouge .highlight}
$ bb --classpath script --main my.namespace 1 2 3
Hello from my namespace! 1 2 3
```
:::
:::

::: paragraph
If you have a larger script with a classic Clojure project layout like
:::

::: listingblock
::: content
``` {.rouge .highlight}
$ tree -L 3
├── deps.edn
├── README
├── src
│   └── project_namespace
│       ├── main.clj
│       └── utilities.clj
└── test
    └── project_namespace
        ├── test_main.clj
        └── test_utilities.clj
```
:::
:::

::: paragraph
then you can tell babashka to include both the `src` and `test` folders
in the classpath and start a socket REPL by running:
:::

::: listingblock
::: content
``` {.rouge .highlight}
$ bb --classpath src:test socket-repl 1666
```
:::
:::

::: paragraph
If there is no `--classpath` argument, the `BABASHKA_CLASSPATH`
environment variable will be used. If that variable isn't set either,
babashka will use `:deps` and `:paths` from `bb.edn`.
:::

::: paragraph
Also see the [babashka.classpath](#babashka_classpath) namespace which
allows dynamically adding to the classpath.
:::

::: paragraph
The namespace [babashka.deps](#babashkadeps) integrates
[tools.deps](https://github.com/clojure/tools.deps.alpha) with babashka
and allows you to set the classpath using a `deps.edn` map.
:::
:::

::: sect2
### [Invoking a main function](#main-function){.link} {#main-function}

::: paragraph
A main function can be invoked with `-m` or `--main` like shown above.
When given the argument `foo.bar`, the namespace `foo.bar` will be
required and the function `foo.bar/-main` will be called with command
line arguments as strings.
:::

::: paragraph
Since babashka 0.3.1 you may pass a fully qualified symbol to `-m`:
:::

::: listingblock
::: content
``` {.rouge .highlight}
$ bb -m clojure.core/prn 1 2 3
"1" "2" "3"
```
:::
:::

::: paragraph
so you can execute any function as a main function, as long as it
accepts the number of provided arguments.
:::

::: paragraph
When invoking `bb` with a main function, the expression
`(System/getProperty "babashka.main")` will return the name of the main
function.
:::
:::

::: sect2
### [Preloads](#_preloads){.link} {#_preloads}

::: paragraph
The environment variable `BABASHKA_PRELOADS` allows to define code that
will be available in all subsequent usages of babashka.
:::

::: listingblock
::: content
``` {.rouge .highlight}
BABASHKA_PRELOADS='(defn foo [x] (+ x 2))'
BABASHKA_PRELOADS=$BABASHKA_PRELOADS' (defn bar [x] (* x 2))'
export BABASHKA_PRELOADS
```
:::
:::

::: paragraph
Note that you can concatenate multiple expressions. Now you can use
these functions in babashka:
:::

::: listingblock
::: content
``` {.rouge .highlight}
$ bb '(-> (foo *input*) bar)' <<< 1
6
```
:::
:::

::: paragraph
You can also preload an entire file using `load-file`:
:::

::: listingblock
::: content
``` {.rouge .highlight}
export BABASHKA_PRELOADS='(load-file "my_awesome_prelude.clj")'
```
:::
:::

::: paragraph
Note that `*input*` is not available in preloads.
:::
:::

::: sect2
### [Running a REPL](#repl){.link} {#repl}

::: paragraph
Babashka supports running a REPL, a socket REPL and an nREPL server.
:::

::: sect3
#### [REPL](#_repl){.link} {#_repl}

::: paragraph
To start a REPL, type:
:::

::: listingblock
::: content
``` {.rouge .highlight}
$ bb repl
```
:::
:::

::: paragraph
To get history with up and down arrows, use
[rlwrap](https://github.com/hanslub42/rlwrap):
:::

::: listingblock
::: content
``` {.rouge .highlight}
$ rlwrap bb repl
```
:::
:::
:::

::: sect3
#### [Socket REPL](#_socket_repl){.link} {#_socket_repl}

::: paragraph
To start a socket REPL on port `1666`:
:::

::: listingblock
::: content
``` {.rouge .highlight}
$ bb socket-repl 1666
Babashka socket REPL started at localhost:1666
```
:::
:::

::: paragraph
Now you can connect with your favorite socket REPL client:
:::

::: listingblock
::: content
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
:::
:::

::: paragraph
The `--socket-repl` option takes options similar to the
`clojure.server.repl` Java property option in Clojure:
:::

::: listingblock
::: content
``` {.rouge .highlight}
$ bb socket-repl '{:address "0.0.0.0" :accept clojure.core.server/repl :port 1666}'
```
:::
:::

::: paragraph
Editor plugins and tools known to work with a babashka socket REPL:
:::

::: ulist
-   Emacs: [inf-clojure](https://github.com/clojure-emacs/inf-clojure):

    ::: paragraph
    To connect:
    :::

    ::: paragraph
    `M-x inf-clojure-connect <RET> localhost <RET> 1666`
    :::

    ::: paragraph
    Before evaluating from a Clojure buffer:
    :::

    ::: paragraph
    `M-x inf-clojure-minor-mode`
    :::

-   Atom: [Chlorine](https://github.com/mauricioszabo/atom-chlorine)

-   Vim: [vim-iced](https://github.com/liquidz/vim-iced)

-   IntelliJ IDEA: [Cursive](https://cursive-ide.com/)

    ::: paragraph
    Note: you will have to use a workaround via
    [tubular](https://github.com/mfikes/tubular). For more info, look
    [here](https://cursive-ide.com/userguide/repl.html#repl-types).
    :::
:::
:::

::: sect3
#### [pREPL](#_prepl){.link} {#_prepl}

::: paragraph
Launching a prepl can be done as follows:
:::

::: listingblock
::: content
``` {.rouge .highlight}
$ bb socket-repl '{:address "0.0.0.0" :accept clojure.core.server/io-prepl :port 1666}'
```
:::
:::

::: paragraph
or programmatically:
:::

::: listingblock
::: content
``` {.rouge .highlight}
$ bb -e '(clojure.core.server/io-prepl)'
(+ 1 2 3)
{:tag :ret, :val "6", :ns "user", :ms 0, :form "(+ 1 2 3)"}
```
:::
:::
:::

::: sect3
#### [nREPL](#_nrepl){.link} {#_nrepl}

::: paragraph
To start an nREPL server:
:::

::: listingblock
::: content
``` {.rouge .highlight}
$ bb nrepl-server 1667
```
:::
:::

::: paragraph
or programmatically:
:::

::: listingblock
::: content
``` {.rouge .highlight}
$ bb -e "(babashka.nrepl.server/start-server\!) (deref (promise))"
Started nREPL server at 0.0.0.0:1667
```
:::
:::

::: paragraph
Then connect with your favorite nREPL client:
:::

::: listingblock
::: content
``` {.rouge .highlight}
$ lein repl :connect 1667
Connecting to nREPL at 127.0.0.1:1667
user=> (+ 1 2 3)
6
user=>
```
:::
:::

::: paragraph
Editor plugins and tools known to work with the babashka nREPL server:
:::

::: ulist
-   Emacs: [CIDER](https://docs.cider.mx/cider/platforms/babashka.html)

-   `lein repl :connect`

-   VSCode: [Calva](http://calva.io/)

-   Atom: [Chlorine](https://github.com/mauricioszabo/atom-chlorine)

-   (Neo)Vim: [vim-iced](https://github.com/liquidz/vim-iced),
    [conjure](https://github.com/Olical/conjure),
    [fireplace](https://github.com/tpope/vim-fireplace)
:::

::: paragraph
The babashka nREPL server currently does not write an `.nrepl-port` file
at startup. Using the following `nrepl` task, defined in a `bb.edn`, you
can accomplish the same:
:::

::: listingblock
::: content
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
:::
:::

::: paragraph
The `babashka.nrepl.server` API is exposed since version 0.8.157.
:::

::: sect4
##### [Debugging the nREPL server](#_debugging_the_nrepl_server){.link} {#_debugging_the_nrepl_server}

::: paragraph
To debug the nREPL server from the binary you can run:
:::

::: listingblock
::: content
``` {.rouge .highlight}
$ BABASHKA_DEV=true bb nrepl-server 1667
```
:::
:::

::: paragraph
This will print all the incoming messages.
:::

::: paragraph
To debug the nREPL server from source:
:::

::: listingblock
::: content
``` {.rouge .highlight}
$ git clone https://github.com/borkdude/babashka --recurse-submodules
$ cd babashka
$ BABASHKA_DEV=true clojure -A:main --nrepl-server 1667
```
:::
:::
:::
:::

::: sect3
#### [REPL server port](#_repl_server_port){.link} {#_repl_server_port}

::: paragraph
For the socket REPL, pREPL, or nREPL, if a randomized port is needed, 0
can be used anywhere a port argument is accepted.
:::
:::
:::

::: sect2
### [Input and output flags](#_input_and_output_flags){.link} {#_input_and_output_flags}

::: paragraph
In one-liners the `*input*` value may come in handy. It contains the
input read from stdin as EDN by default. If you want to read in text,
use the `-i` flag, which binds `*input*` to a lazy seq of lines of text.
If you want to read multiple EDN values, use the `-I` flag. The `-o`
option prints the result as lines of text. The `-O` option prints the
result as lines of EDN values.
:::

::: {.admonitionblock .note}
  -- ------------------------------------------------------------------------------------------------------------------------------
     `*input*` is only available in the `user` namespace, designed for one-liners. For writing scripts, see [Scripts](#_scripts).
  -- ------------------------------------------------------------------------------------------------------------------------------
:::

::: paragraph
The following table illustrates the combination of options for commands
of the form
:::

::: literalblock
::: content
    echo "{{Input}}" | bb {{Input flags}} {{Output flags}} "*input*"
:::
:::

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

::: paragraph
When combined with the `--stream` option, the expression is executed for
each value in the input:
:::

::: listingblock
::: content
``` {.rouge .highlight}
$ echo '{:a 1} {:a 2}' | bb --stream '*input*'
{:a 1}
{:a 2}
```
:::
:::

::: sect3
#### [Scripts](#_scripts){.link} {#_scripts}

::: paragraph
When writing scripts instead of one-liners on the command line, it is
not recommended to use `*input*`. Here is how you can rewrite to
standard Clojure code.
:::
:::

::: sect3
#### [EDN input](#_edn_input){.link} {#_edn_input}

::: paragraph
Reading a single EDN value from stdin:
:::

::: listingblock
::: content
``` {.rouge .highlight}
(ns script
 (:require [clojure.edn :as edn]))

(edn/read *in*)
```
:::
:::

::: paragraph
Reading multiple EDN values from stdin (the `-I` flag):
:::

::: listingblock
::: content
``` {.rouge .highlight}
(ns script
 (:require [clojure.edn :as edn]
           [clojure.java.io :as io]))

(let [reader  (java.io.PushbackReader. (io/reader *in*))]
  (take-while #(not (identical? ::eof %)) (repeatedly #(edn/read {:eof ::eof} reader))))
```
:::
:::
:::

::: sect3
#### [Text input](#_text_input){.link} {#_text_input}

::: paragraph
Reading text from stdin can be done with `(slurp *in*)`. To get a lazy
seq of lines (the `-i` flag), you can use:
:::

::: listingblock
::: content
``` {.rouge .highlight}
(ns script
 (:require [clojure.java.io :as io]))

(line-seq (io/reader *in*))
```
:::
:::
:::

::: sect3
#### [Output](#_output){.link} {#_output}

::: paragraph
To print to stdout, use `println` for text and `prn` for EDN values.
:::
:::
:::

::: sect2
### [Uberscript](#_uberscript){.link} {#_uberscript}

::: paragraph
The `--uberscript` option collects the expressions in
`BABASHKA_PRELOADS`, the command line expression or file, the main
entrypoint and all required namespaces from the classpath into a single
file. This can be convenient for debugging and deployment.
:::

::: paragraph
Here is an example that uses a function from the
[clj-commons/fs](https://github.com/clj-commons/fs) library.
:::

::: paragraph
Let's first set the classpath:
:::

::: listingblock
::: content
``` {.rouge .highlight}
$ export BABASHKA_CLASSPATH=$(clojure -Spath -Sdeps '{:deps {clj-commons/fs {:mvn/version "1.6.307"}}}')
```
:::
:::

::: paragraph
Write a little script, say `glob.clj`:
:::

::: listingblock
::: content
``` {.rouge .highlight}
(ns glob (:require [me.raynes.fs :as fs]))

(run! (comp println str)
      (fs/glob (first *command-line-args*)))
```
:::
:::

::: paragraph
For testing, we'll make a file which we will find using the glob
function:
:::

::: listingblock
::: content
``` {.rouge .highlight}
$ touch README.md
```
:::
:::

::: paragraph
Now we can execute the script which uses the library:
:::

::: listingblock
::: content
``` {.rouge .highlight}
$ time bb glob.clj '*.md'
/private/tmp/glob/README.md
bb glob.clj '*.md'   0.03s  user 0.01s system 88% cpu 0.047 total
```
:::
:::

::: paragraph
Producing an uberscript with all required code:
:::

::: listingblock
::: content
``` {.rouge .highlight}
$ bb uberscript glob-uberscript.clj glob.clj
```
:::
:::

::: paragraph
To prove that we don't need the classpath anymore:
:::

::: listingblock
::: content
``` {.rouge .highlight}
$ unset BABASHKA_CLASSPATH
$ time bb glob-uberscript.clj '*.md'
/private/tmp/glob/README.md
bb glob-uberscript.clj '*.md'   0.03s  user 0.02s system 93% cpu 0.049 total
```
:::
:::

::: paragraph
Caveats:
:::

::: ulist
-   *Dynamic requires*. Building uberscripts works by running top-level
    `ns` and `require` forms. The rest of the code is not evaluated.
    Code that relies on dynamic requires may not work in an uberscript.

-   *Resources*. The usage of `io/resource` assumes a classpath, so when
    this is used in your uberscript, you still have to set a classpath
    and bring the resources along.
:::

::: paragraph
If any of the above is problematic for your project, using an
[uberjar](#uberjar) is a good alternative.
:::

::: sect3
#### [Carve](#_carve){.link} {#_carve}

::: paragraph
Uberscripts can be optimized by cutting out unused vars with
[carve](https://github.com/borkdude/carve).
:::

::: listingblock
::: content
``` {.rouge .highlight}
$ wc -l glob-uberscript.clj
     583 glob-uberscript.clj
$ carve --opts '{:paths ["glob-uberscript.clj"] :aggressive true :silent true}'
$ wc -l glob-uberscript.clj
     105 glob-uberscript.clj
```
:::
:::

::: paragraph
Note that the uberscript became 72% shorter. This has a beneficial
effect on execution time:
:::

::: listingblock
::: content
``` {.rouge .highlight}
$ time bb glob-uberscript.clj '*.md'
/private/tmp/glob/README.md
bb glob-uberscript.clj '*.md'   0.02s  user 0.01s system 84% cpu 0.034 total
```
:::
:::
:::
:::

::: sect2
### [Uberjar](#_uberjar){.link} {#_uberjar}

::: paragraph
Babashka can create uberjars from a given classpath and optionally a
main method:
:::

::: listingblock
::: content
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
:::
:::
:::

::: sect2
### [System properties](#_system_properties){.link} {#_system_properties}

::: paragraph
Babashka sets the following system properties:
:::

::: ulist
-   `babashka.version`: the version string, e.g. `"1.2.0"`

-   `babashka.main`: the `--main` argument

-   `babashka.file`: the `--file` argument (normalized using
    `.getAbsolutePath`)
:::
:::

::: sect2
### [Data readers](#_data_readers){.link} {#_data_readers}

::: paragraph
Data readers can be enabled by setting `*data-readers*` to a hashmap of
symbols to functions or vars:
:::

::: listingblock
::: content
``` {.rouge .highlight}
$ bb -e "(set! *data-readers* {'t/tag inc}) #t/tag 1"
2
```
:::
:::

::: paragraph
To preserve good startup time, babashka does not scan the classpath for
`data_readers.clj` files.
:::
:::

::: sect2
### [Reader conditionals](#_reader_conditionals){.link} {#_reader_conditionals}

::: paragraph
Babashka supports reader conditionals by taking either the `:bb` or
`:clj` branch, whichever comes first. NOTE: the `:clj` branch behavior
was added in version 0.0.71, before that version the `:clj` branch was
ignored.
:::

::: listingblock
::: content
``` {.rouge .highlight}
$ bb -e "#?(:bb :hello :clj :bye)"
:hello

$ bb -e "#?(:clj :bye :bb :hello)"
:bye

$ bb -e "[1 2 #?@(:bb [] :clj [1])]"
[1 2]
```
:::
:::
:::

::: sect2
### [Invoking clojure](#_invoking_clojure){.link} {#_invoking_clojure}

::: paragraph
Babashka bundles [deps.clj](https://github.com/borkdude/deps.clj) for
invoking a `clojure` JVM process:
:::

::: listingblock
::: content
``` {.rouge .highlight}
$ bb clojure -M -e "*clojure-version*"
{:major 1, :minor 10, :incremental 1, :qualifier nil}
```
:::
:::

::: paragraph
See the [clojure](#_clojure) function in the
[babashka.deps](#babashkadeps) namespace for programmatically invoking
clojure.
:::
:::
:::
:::

::: sect1
## [Project setup](#project-setup){.link}

::: sectionbody
::: sect2
### [bb.edn](#_bb_edn){.link} {#_bb_edn}

::: paragraph
Since version 0.3.1, babashka supports a local `bb.edn` file to manage a
project.
:::
:::

::: sect2
### [:paths and :deps](#_paths_and_deps){.link} {#_paths_and_deps}

::: paragraph
You can declare one or multiple paths and dependencies so they are
automatically added to the classpath:
:::

::: listingblock
::: content
``` {.rouge .highlight}
{:paths ["bb"]
 :deps {medley/medley {:mvn/version "1.3.0"}}}
```
:::
:::

::: paragraph
If we have a project that has a `deps.edn` and would like to reuse those
deps in `bb.edn`:
:::

::: listingblock
::: content
``` {.rouge .highlight}
{:deps {your-org/your-project {:local/root "."}}}
```
:::
:::

::: paragraph
`bb.edn` applies to the local project, and dependencies defined in this
files are never shared with other projects. This is typically what you
want when writing a script or tool. By contrast, `deps.edn` is useful
when creating libraries that are used by other projects.
:::

::: {.admonitionblock .note}
  -- -----------------------------------------------------------------------------------------------------------------------------------------------------
     Use a unique name to refer to your project's `deps.edn`, the same name that you would otherwise use when referring to your project as a dependency.
  -- -----------------------------------------------------------------------------------------------------------------------------------------------------
:::

::: paragraph
If we have a main function in a file called `bb/my_project/main.clj`
like:
:::

::: listingblock
::: content
    (ns my-project.main
      (:require [medley.core :as m]))

    (defn -main [& _args]
      (prn (m/index-by :id [{:id 1} {:id 2}])))
:::
:::

::: paragraph
we can invoke it like:
:::

::: listingblock
::: content
``` {.rouge .highlight}
$ bb -m my-project.main
{1 {:id 1}, 2 {:id 2}}
```
:::
:::

::: paragraph
See [Invoking a main function](#main-function) for more details on how
to invoke a function from the command line.
:::

::: paragraph
The `:deps` entry is managed by
[deps.clj](https://github.com/borkdude/deps.clj) and requires a `java`
installation to resolve and download dependencies.
:::
:::

::: sect2
### [:min-bb-version](#_min_bb_version){.link} {#_min_bb_version}

::: paragraph
Since version 0.3.6, babashka supports the `:min-bb-version` where the
minimal babashka version can be declared:
:::

::: listingblock
::: content
``` {.rouge .highlight}
{:paths ["src"]
 :deps {medley/medley {:mvn/version "1.3.0"}}
 :min-bb-version "0.3.7"}
```
:::
:::

::: paragraph
When using an older bb version (that supports `:min-bb-version`),
babashka will print a warning:
:::

::: listingblock
::: content
``` {.rouge .highlight}
WARNING: this project requires babashka 0.3.7 or newer, but you have: 0.3.6
```
:::
:::
:::

::: sect2
### [:tasks](#_tasks){.link} {#_tasks}

::: paragraph
Since babashka 0.4.0 the `bb.edn` file supports the `:tasks` entry which
describes tasks that you can run in the current project. The tasks
feature is similar to what people use `Makefile`, `Justfile` or
`npm run` for. See [Task runner](#tasks) for more details.
:::
:::

::: sect2
### [Script-adjacent bb.edn](#_script_adjacent_bb_edn){.link} {#_script_adjacent_bb_edn}

::: paragraph
Since babashka 1.3.177 a `bb.edn` file relative to the invoked file is
respected. This makes writing system-global scripts with dependencies
easier.
:::

::: paragraph
Given a `bb.edn`:
:::

::: listingblock
::: content
``` {.rouge .highlight}
{:deps {medley/medley {:mvn/version "1.3.0"}}}
```
:::
:::

::: paragraph
and a script `medley.bb`:
:::

::: listingblock
::: content
``` {.rouge .highlight}
#!/usr/bin/env bb

(ns medley
  (:require [medley.core :as medley]))

(prn (medley/index-by :id [{:id 1}]))
```
:::
:::

::: paragraph
Assuming that `medley.bb` is executable (`chmod +x medley.bb`), you can
directly execute it in the current directory:
:::

::: listingblock
::: content
``` {.rouge .highlight}
~/my_project $ ./medley.bb
{1 {:id 1}}
```
:::
:::

::: paragraph
To execute this script from anywhere on the system, you just have to add
it to the `PATH`:
:::

::: listingblock
::: content
``` {.rouge .highlight}
/tmp $ export PATH=$PATH:~/my_project # ensure script is on path
/tmp $ medley.bb # works, respects ~/my_project/bb.edn file with :deps
{1 {:id 1}}
```
:::
:::

::: paragraph
Of course you can just call your script `medley` without the `.bb`
extension.
:::

::: sect3
#### [Windows](#script-adjacent-bb-edn-windows){.link} {#script-adjacent-bb-edn-windows}

::: paragraph
On Windows bash shebangs are not supported. An alternative is to create
a script-adjacent `.bat` file, e.g `medley.bat`:
:::

::: listingblock
::: content
``` {.rouge .highlight}
@echo off
set ARGS=%*
set SCRIPT=%~dp0medley.bb
bb %SCRIPT% %ARGS%
```
:::
:::

::: paragraph
Then add this script to your `%PATH%`:
:::

::: listingblock
::: content
``` {.rouge .highlight}
C:\Temp> set PATH=%PATH%;c:\my_project
C:\Temp> medley
{1 {:id 1}}
```
:::
:::
:::
:::
:::
:::

::: sect1
## [Task runner](#tasks){.link} {#tasks}

::: sectionbody
::: sect2
### [Introduction](#_introduction){.link} {#_introduction}

::: paragraph
People often use a `Makefile`, `Justfile`, `npm scripts` or `lein`
aliases in their (clojure) projects to remember complex invocations and
to create shortcuts for them. Since version 0.4.0, babashka supports a
similar feature as part of the `bb.edn` project configuration file. For
a general overview of what's available in `bb.edn`, go to [Project
setup](#project-setup).
:::

::: paragraph
The tasks configuration lives under the `:tasks` key and can be used
together with `:paths` and `:deps`:
:::

::: listingblock
::: content
``` {.rouge .highlight}
{:paths ["script"]
 :deps {medley/medley {:mvn/version "1.3.0"}}
 :min-bb-version "0.4.0"
 :tasks
 {clean (shell "rm -rf target")
 ...}
 }
```
:::
:::

::: paragraph
In the above example we see a simple task called `clean` which invokes
the `shell` command, to remove the `target` directory. You can invoke
this task from the command line with:
:::

::: listingblock
::: content
``` {.rouge .highlight}
$ bb run clean
```
:::
:::

::: paragraph
Babashka also accepts a task name without explicitly mentioning `run`:
:::

::: listingblock
::: content
``` {.rouge .highlight}
$ bb clean
```
:::
:::

::: paragraph
To make your tasks more cross-platform friendly, you can use the
built-in [babashka.fs](https://github.com/babashka/fs) library. To use
libraries in tasks, use the `:requires` option:
:::

::: listingblock
::: content
``` {.rouge .highlight}
{:tasks
 {:requires ([babashka.fs :as fs])
  clean (fs/delete-tree "target")
  }
 }
```
:::
:::

::: paragraph
Tasks accept arbitrary Clojure expressions. E.g. you can print something
when executing the task:
:::

::: listingblock
::: content
``` {.rouge .highlight}
{:tasks
 {:requires ([babashka.fs :as fs])
  clean (do (println "Removing target folder.")
            (fs/delete-tree "target"))
  }
 }
```
:::
:::

::: listingblock
::: content
``` {.rouge .highlight}
$ bb clean
Removing target folder.
```
:::
:::
:::

::: sect2
### [Talk](#_talk){.link} {#_talk}

::: paragraph
Go
[here](https://www.youtube.com/watch?v=u5ECoR7KT1Y&ab_channel=LondonClojurians)
if you would like to watch a talk on babashka tasks.
:::
:::

::: sect2
### [Task-local options](#_task_local_options){.link} {#_task_local_options}

::: paragraph
Instead of naked expressions, tasks can be defined as maps with options.
The task expression should then be moved to the `:task` key:
:::

::: listingblock
::: content
``` {.rouge .highlight}
{:tasks
 {
  clean {:doc "Removes target folder"
         :requires ([babashka.fs :as fs])
         :task (fs/delete-tree "target")}
  }
 }
```
:::
:::

::: paragraph
Tasks support the `:doc` option which gives it a docstring which is
printed when invoking `bb tasks` on the command line. Other options
include:
:::

::: ulist
-   `:requires`: task-specific namespace requires.

-   `:extra-paths`: add paths to the classpath.

-   `:extra-deps`: add extra dependencies to the classpath.

-   `:enter`, `:leave`: override the global `:enter`/`:leave` hook.

-   `:override-builtin`: override the name of a built-in babashka
    command.
:::
:::

::: sect2
### [Discoverability](#_discoverability){.link} {#_discoverability}

::: paragraph
When invoking `bb tasks`, babashka prints a list of all tasks found in
`bb.edn` in the order of appearance. E.g. in the
[clj-kondo.lsp](https://github.com/clj-kondo/clj-kondo.lsp) project it
prints:
:::

::: listingblock
::: content
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
:::
:::
:::

::: sect2
### [Command line arguments](#_command_line_arguments){.link} {#_command_line_arguments}

::: paragraph
Command line arguments are available as `*command-line-args*`, just like
in Clojure. Since version `0.9.160`, you can use
[babashka.cli](https://github.com/babashka/cli) in tasks via the
[exec](#cli:exec) function to deal with command line arguments in a
concise way. See the chapter on [babashka CLI](#cli).
:::

::: paragraph
Of course, you are free to parse command line arguments using the
built-in `tools.cli` library or just handle them manually.
:::

::: paragraph
You can re-bind `*command-line-args*` to ensure functions see a
different set of arguments:
:::

::: listingblock
::: content
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
:::
:::

::: listingblock
::: content
``` {.rouge .highlight}
$ bb foo 1 2 3
foo ("1" "2" "3")
bar ("2" "3")
```
:::
:::

::: sect3
#### [Terminal tab-completion](#_terminal_tab_completion){.link} {#_terminal_tab_completion}

::: sect4
##### [zsh](#_zsh){.link} {#_zsh}

::: paragraph
Add this to your `.zshrc` to get tab-complete feature on ZSH.
:::

::: listingblock
::: content
``` {.rouge .highlight}
_bb_tasks() {
    local matches=(`bb tasks |tail -n +3 |cut -f1 -d ' '`)
    compadd -a matches
    _files # autocomplete filenames as well
}
compdef _bb_tasks bb
```
:::
:::
:::

::: sect4
##### [bash](#_bash){.link} {#_bash}

::: paragraph
Add this to your `.bashrc` to get tab-complete feature on bash.
:::

::: listingblock
::: content
``` {.rouge .highlight}
_bb_tasks() {
    COMPREPLY=( $(compgen -W "$(bb tasks |tail -n +3 |cut -f1 -d ' ')" -- ${COMP_WORDS[COMP_CWORD]}) );
}
# autocomplete filenames as well
complete -f -F _bb_tasks bb
```
:::
:::
:::

::: sect4
##### [fish](#_fish){.link} {#_fish}

::: paragraph
Add this to your `.config/fish/completions/bb.fish` to get tab-complete
feature on Fish shell.
:::

::: listingblock
::: content
``` {.rouge .highlight}
function __bb_complete_tasks
  if not test "$__bb_tasks"
    set -g __bb_tasks (bb tasks |tail -n +3 |cut -f1 -d ' ')
  end

  printf "%s\n" $__bb_tasks
end

complete -c bb -a "(__bb_complete_tasks)" -d 'tasks'
```
:::
:::
:::
:::
:::

::: sect2
### [Run](#_run){.link} {#_run}

::: paragraph
You can execute tasks using `bb <task-name>`. The babashka `run`
subcommand can be used to execute with some additinoal options:
:::

::: ulist
-   `--parallel`: invoke task dependencies in parallel.

    ::: listingblock
    ::: content
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
    :::
    :::

    ::: listingblock
    ::: content
    ``` {.rouge .highlight}
    $ bb run --parallel d
    d: #inst "2021-05-08T14:14:56.322-00:00"
    a: #inst "2021-05-08T14:14:56.357-00:00"
    b: #inst "2021-05-08T14:14:56.360-00:00"
    c: #inst "2021-05-08T14:15:01.366-00:00"
    "Elapsed time: 5023.894512 msecs"
    ```
    :::
    :::

    ::: paragraph
    Also see [Parallel tasks](#parallel).
    :::

-   `--prn`: print the result from the task expression:

    ::: listingblock
    ::: content
    ``` {.rouge .highlight}
    {:tasks {sum (+ 1 2 3)}}
    ```
    :::
    :::

    ::: listingblock
    ::: content
    ``` {.rouge .highlight}
    $ bb run --prn sum
    6
    ```
    :::
    :::

    ::: paragraph
    Unlike scripts, babashka tasks do not print their return value.
    :::
:::
:::

::: sect2
### [Hooks](#_hooks){.link} {#_hooks}

::: paragraph
The task runner exposes the following hooks:
:::

::: sect3
#### [:init](#_init){.link} {#_init}

::: paragraph
The `:init` is for expressions that are executed before any of the tasks
are executed. It is typically used for defining helper functions and
constants:
:::

::: listingblock
::: content
``` {.rouge .highlight}
{:tasks
 {:init (defn env [s] (System/getenv s))
  print-env (println (env (first *command-line-args*)))
  }
 }
```
:::
:::

::: listingblock
::: content
``` {.rouge .highlight}
$ FOO=1 bb print-env FOO
1
```
:::
:::
:::

::: sect3
#### [:enter, :leave](#_enter_leave){.link} {#_enter_leave}

::: paragraph
The `:enter` hook is executed before each task. This is typically used
to print the name of a task, which can be obtained using the
`current-task` function:
:::

::: listingblock
::: content
``` {.rouge .highlight}
{:tasks
 {:init (defn env [s] (System/getenv s))
  :enter (println "Entering:" (:name (current-task)))
  print-env (println (env (first *command-line-args*)))
  }
 }
```
:::
:::

::: listingblock
::: content
``` {.rouge .highlight}
$ FOO=1 bb print-env FOO
Entering: print-env
1
```
:::
:::

::: paragraph
The `:leave` hook is similar to `:enter` but is executed after each
task.
:::

::: paragraph
Both hooks can be overriden as task-local options. Setting them to `nil`
will disable them for specific tasks (see [Task-local
options](#_task_local_options)).
:::
:::
:::

::: sect2
### [Tasks API](#_tasks_api){.link} {#_tasks_api}

::: paragraph
The `babashka.tasks` namespace exposes the following functions: `run`,
`shell`, `clojure` and `current-task`. They are implicitly imported,
thus available without a namespace prefix.
:::

::: sect3
#### [run](#_run_2){.link} {#_run_2}

::: paragraph
Tasks provide the `run` function to explicitly invoke another task:
:::

::: listingblock
::: content
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
:::
:::

::: paragraph
When running `bb uberjar:clean`, first the `clean` task is executed and
the `uberjar`:
:::

::: listingblock
::: content
``` {.rouge .highlight}
$ bb uberjar:clean
Removing target folder.
Making uberjar
```
:::
:::

::: paragraph
The `clojure` function in the above example executes a clojure process
using [deps.clj](https://github.com/borkdude/deps.clj). See
[clojure](#tasks:clojure) for more info
:::

::: paragraph
The `run` function accepts an additional map with options:
:::

::: sect4
##### [:parallel](#_parallel){.link} {#_parallel}

::: paragraph
The `:parallel` option executes dependencies of the invoked task in
parallel (when possible). See [Parallel tasks](#parallel).
:::
:::
:::

::: sect3
#### [shell](#_shell){.link} {#_shell}

::: paragraph
Both `shell` and `clojure` return a
[process](https://github.com/babashka/babashka.process) object which
returns the `:exit` code among other info. By default these functions
will throw an exception when a non-zero exit code was returned and they
will inherit the stdin/stdout/stderr from the babashka process.
:::

::: listingblock
::: content
``` {.rouge .highlight}
{:tasks
 {
  ls (shell "ls foo")
 }
}
```
:::
:::

::: listingblock
::: content
``` {.rouge .highlight}
$ bb ls
ls: foo: No such file or directory
Error while executing task: ls
$ echo $?
1
```
:::
:::

::: paragraph
You can opt out of this behavior by using the `:continue` option:
:::

::: listingblock
::: content
``` {.rouge .highlight}
{:tasks
 {
  ls (shell {:continue true} "ls foo")
 }
}
```
:::
:::

::: listingblock
::: content
``` {.rouge .highlight}
$ bb ls
ls: foo: No such file or directory
$ echo $?
0
```
:::
:::

::: paragraph
When you want to redirect output to a file instead, you can provide the
`:out` option.
:::

::: listingblock
::: content
``` {.rouge .highlight}
(shell {:out "file.txt"} "echo hello")
```
:::
:::

::: paragraph
To run a program in another directory, you can use the `:dir` option:
:::

::: listingblock
::: content
``` {.rouge .highlight}
(shell {:dir "subproject"} "ls")
```
:::
:::

::: paragraph
To set environment variables with `shell` or `clojure`:
:::

::: listingblock
::: content
``` {.rouge .highlight}
(shell {:extra-env {"FOO" "BAR"}} "printenv FOO")
```
:::
:::

::: paragraph
Other supported options are similar to those of
[`babashka.process/process`](https://github.com/babashka/babashka.process).
:::

::: paragraph
The process is executed synchronously: i.e. babashka will wait for the
process to finish before executing the next expression. If this doesn't
fit your use case, you can use
[`babashka.process/process`](https://github.com/babashka/babashka.process)
directly instead. These two invocations are roughly equivalent:
:::

::: listingblock
::: content
``` {.rouge .highlight}
(require '[babashka.process :as p :refer [process]]
         '[babashka.tasks :as tasks])

(tasks/shell {:dir "subproject"} "npm install")

(-> (process {:dir "subproject" :inherit true} "npm install")
    (p/check))
```
:::
:::

::: paragraph
Note that the first string argument to `shell` it tokenized (broken into
multiple parts) and the trailing arguments are not:
:::

::: paragraph
Correct:
:::

::: listingblock
::: content
``` {.rouge .highlight}
(shell "npm install" "-g" "nbb")
```
:::
:::

::: paragraph
Not correct (`-g nbb` within the same string):
:::

::: listingblock
::: content
``` {.rouge .highlight}
(shell "npm install" "-g nbb")
```
:::
:::

::: paragraph
Note that the varargs signature plays well with feeding
`*command-line-args*`:
:::

::: listingblock
::: content
``` {.rouge .highlight}
(apply shell "npm install" *command-line-args*)
```
:::
:::

::: paragraph
Note that `shell` does not invoke a shell but just shells out to an
external program. As such, `shell` does not understand bash specific
syntax. The following does not work: `(shell "rm -rf target/*")`. To
invoke a specific shell, you should do that explicitly with:
:::

::: listingblock
::: content
``` {.rouge .highlight}
(shell "bash -c" "rm -rf target/*")
```
:::
:::

::: paragraph
Also see the docstring of `shell`
[here](https://github.com/babashka/process/blob/master/API.md#shell).
:::
:::

::: sect3
#### [clojure](#tasks:clojure){.link} {#tasks:clojure}

::: paragraph
The `clojure` function starts a Clojure process using
[deps.clj](https://github.com/borkdude/deps.clj). The interface is
exactly the same as the clojure CLI. E.g. to evaluate an expression:
:::

::: listingblock
::: content
``` {.rouge .highlight}
{:tasks {eval (clojure "-M -e '(+ 1 2 3)'")}}
```
:::
:::

::: paragraph
or to invoke clj-kondo as a library:
:::

::: listingblock
::: content
``` {.rouge .highlight}
{:tasks {eval (clojure {:dir "subproject"} "-M:clj-kondo")}}
```
:::
:::

::: paragraph
The `clojure` task function behaves similar to `shell` with respect to
the exit code, return value and supported options, except when it comes
to features that do not start a process, but only do some printing. E.g.
you can capture the classpath using:
:::

::: listingblock
::: content
``` {.rouge .highlight}
(with-out-str (clojure "-Spath"))
```
:::
:::

::: paragraph
because this operation doesn't start a process but prints to `*out*`.
:::

::: paragraph
To run a `clojure` task in another directory:
:::

::: listingblock
::: content
``` {.rouge .highlight}
{:tasks {eval (clojure {:dir "subproject"} "-M:clj-kondo")}}
```
:::
:::
:::

::: sect3
#### [current-task](#current-task){.link}

::: paragraph
The `current-task` function returns a map representing the currently
running task. This function is typically used in the `:enter` and
`:leave` hooks.
:::
:::

::: sect3
#### [exec](#_exec){.link} {#_exec}

::: paragraph
See [exec](#cli:exec).
:::
:::
:::

::: sect2
### [Dependencies between tasks](#_dependencies_between_tasks){.link} {#_dependencies_between_tasks}

::: paragraph
Dependencies between tasks can be declared using `:depends`:
:::

::: listingblock
::: content
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
:::
:::

::: paragraph
The `fs/modified-since` function returns a seq of all newer files
compared to a target, which can be used to prevent rebuilding artifacts
when not necessary.
:::

::: paragraph
Alternatively you can use the `:init` hook to define vars, require
namespaces, etc.:
:::

::: listingblock
::: content
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
:::
:::

::: paragraph
It is common to define tasks that only serve as a helper to other tasks.
To not expose these tasks in the output of `bb tasks`, you can start
their name with a hyphen.
:::
:::

::: sect2
### [Parallel tasks](#parallel){.link} {#parallel}

::: paragraph
The `:parallel` option executes dependencies of the invoked task in
parallel (when possible). This can be used to speed up execution, but
also to have multiple tasks running in parallel for development:
:::

::: listingblock
::: content
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
:::
:::

::: {.colist .arabic}
  ------- ---------------------------------------------------------------------------------
  **1**   The `dev` task invokes the (private) `-dev` task in parallel
  **2**   The `-dev` task depends on three other tasks which are executed simultaneously.
  ------- ---------------------------------------------------------------------------------
:::
:::

::: sect2
### [Invoking a main function](#_invoking_a_main_function){.link} {#_invoking_a_main_function}

::: paragraph
Invoking a main function can be done by providing a fully qualified
symbol:
:::

::: listingblock
::: content
``` {.rouge .highlight}
{:tasks
  {foo-bar foo.bar/-main}}
```
:::
:::

::: paragraph
You can use any fully qualified symbol, not just ones that end in
`-main` (so e.g. `foo.bar/baz` is fine). You can also have multiple main
functions in one namespace.
:::

::: paragraph
The namespace `foo.bar` will be automatically required and the function
will be invoked with `*command-line-args*`:
:::

::: listingblock
::: content
``` {.rouge .highlight}
$ bb foo-bar 1 2 3
```
:::
:::
:::

::: sect2
### [REPL](#_repl_2){.link} {#_repl_2}

::: paragraph
To get a REPL within a task, you can use `clojure.main/repl`:
:::

::: listingblock
::: content
``` {.rouge .highlight}
{:tasks {repl (clojure.main/repl)}}
```
:::
:::

::: paragraph
Alternatively, you can use `babashka.tasks/run` to invoke a task from a
REPL.
:::

::: paragraph
For REPL- and linting-friendliness, it's recommended to move task code
longer than a couple of lines to a `.clj` or `.bb` file.
:::
:::

::: sect2
### [Real world examples](#_real_world_examples){.link} {#_real_world_examples}

::: ulist
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
:::
:::

::: sect2
### [Naming](#_naming){.link} {#_naming}

::: sect3
#### [Valid names](#_valid_names){.link} {#_valid_names}

::: paragraph
When running a task, babashka assembles a small program which defines
vars bound to the return values of tasks. This brings the limitation
that you can only choose names for your tasks that are valid as var
names. You can't name your task `foo/bar` for this reason. If you want
to use delimiters to indicate some sort of grouping, you can do it like
`foo-bar`, `foo:bar` or `foo_bar`.
:::

::: paragraph
Names starting with a `-` are considered \"private\" and not listed in
the `bb tasks` output.
:::
:::

::: sect3
#### [Conflicting file / task / subcommand names](#_conflicting_file_task_subcommand_names){.link} {#_conflicting_file_task_subcommand_names}

::: paragraph
`bb <option>` is resolved in the order of file \> task \> subcommand.
:::

::: paragraph
Escape hatches in case of conflicts:
:::

::: ulist
-   execute relative file as `bb ./foo`

-   execute task as `bb run foo`

-   execute subcommand as `bb --foo`
:::

::: paragraph
When choosing a task name that overrides a babashka builtin subcommand,
you have to provide the `:override-builtin` option to get rid of the
warning that appears when running babashka:
:::

::: listingblock
::: content
``` {.rouge .highlight}
$ bb -Sdeps '{:tasks {help {:task (prn :help)}}}' help
[babashka] WARNING: task(s) 'help' override built-in command(s).
:help
```
:::
:::

::: listingblock
::: content
``` {.rouge .highlight}
$ bb -Sdeps '{:tasks {help {:task (prn :help) :override-builtin true}}}' help
:help
```
:::
:::
:::

::: sect3
#### [Conflicting task and clojure.core var names](#_conflicting_task_and_clojure_core_var_names){.link} {#_conflicting_task_and_clojure_core_var_names}

::: paragraph
You can name a task similar to a core var, let's say: `format`. If you
want to refer to the core var, it is recommended to use the fully
qualified `clojure.core/format` in that case, to avoid conflicts in
`:enter` and `:leave` expressions and when using the `format` task as a
dependency.
:::
:::
:::

::: sect2
### [Syntax](#_syntax){.link} {#_syntax}

::: paragraph
Because `bb.edn` is an EDN file, you cannot use all of Clojure's syntax
in expressions. Most notably:
:::

::: ulist
-   You cannot use `#(foo %)`, but you can use `(fn [x] (foo x))`

-   You cannot use `@(foo)` but you can use `(deref foo)`

-   You cannot use `#"re"` but you can use `(re-pattern "re")`

-   Single quotes are accidentally supported in some places, but are
    better avoided: `{:task '(foo)}` does not work, but
    `{:task (quote (foo))` does work. When requiring namespaces, use the
    `:requires` feature in favor of doing it manually using
    `(require '[foo])`.
:::
:::
:::
:::

::: sect1
## [Babashka CLI](#cli){.link} {#cli}

::: sectionbody
::: paragraph
In version `0.9.160` of babashka, the [babashka
CLI](https://github.com/babashka/cli) added as a built-in library
together with task integration.
:::

::: sect2
### [-x](#_x){.link} {#_x}

::: paragraph
For invoking functions from the command line, you can use the new `-x`
flag (a pun to Clojure's `-X` of course!):
:::

::: listingblock
::: content
``` {.rouge .highlight}
bb -x clojure.core/prn --hello there
{:hello "there"}
```
:::
:::

::: paragraph
What we see in the above snippet is that a map `{:hello "there"}` is
constructed by babashka CLI and then fed to the `prn` function. After
that the result is printed to the console.
:::

::: paragraph
What if we want to influence how things are parsed by babashka CLI and
provide some defaults? This can be done using metadata. Let's create a
`bb.edn` and make a file available on the classpath:
:::

::: paragraph
`bb.edn`:
:::

::: listingblock
::: content
``` {.rouge .highlight}
{:paths ["."]}
```
:::
:::

::: paragraph
`tasks.clj`:
:::

::: listingblock
::: content
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
:::
:::

::: paragraph
Now let's invoke:
:::

::: listingblock
::: content
``` {.rouge .highlight}
$ bb -x tasks/my-function -n 1 2
{:ns-data 1, :fn-data 1, :num [1 2]}
```
:::
:::

::: paragraph
As you can see, the namespace options are merged with the function
options. Defaults can be provided with `:exec-args`, like you're used to
from the clojure CLI.
:::
:::

::: sect2
### [exec](#cli:exec){.link} {#cli:exec}

::: paragraph
What about task integration? Let's adapt our `bb.edn`:
:::

::: listingblock
::: content
``` {.rouge .highlight}
{:paths ["."]
 :tasks {doit {:task (let [x (exec 'tasks/my-function)]
                       (prn :x x))
               :exec-args {:task-data 1234}}
         }}
```
:::
:::

::: paragraph
and invoke the task:
:::

::: listingblock
::: content
``` {.rouge .highlight}
$ bb doit --cli-option :yeah -n 1 2 3
:x {:ns-data 1, :fn-data 1, :task-data 1234, :cli-option :yeah, :num [1 2 3]}
```
:::
:::

::: paragraph
As you can see it works similar to `-x`, but you can provide another set
of defaults on the task level with `:exec-args`. Executing a function
through babashka CLI is done using the `babashka.task/exec` function,
available by default in tasks.
:::

::: paragraph
To add `:exec-args` that should be evaluated you can pass an extra map
to `exec` as follows:
:::

::: listingblock
::: content
``` {.rouge .highlight}
{:paths ["."]
 :tasks {doit {:task (let [x (exec 'tasks/my-function {:exec-args {:foo (+ 1 2 3)}})]
                       (prn :x x))
               :exec-args {:task-data 1234}}
         }}
```
:::
:::

::: listingblock
::: content
``` {.rouge .highlight}
$ bb doit --cli-option :yeah -n 1 2 3
:x {:ns-data 1, :fn-data 1, :task-data 1234, :cli-option :yeah, :num [1 2 3] :foo 6}
```
:::
:::
:::
:::
:::

::: sect1
## [Libraries](#libraries){.link}

::: sectionbody
::: sect2
### [Built-in namespaces](#built-in-namespaces){.link}

::: paragraph
In addition to `clojure.core`, the following libraries / namespaces are
available in babashka. Some are available through pre-defined aliases in
the `user` namespace, which can be handy for one-liners. If not all vars
are available, they are enumerated explicitly. If some important var is
missing, an issue or PR is welcome.
:::

::: paragraph
From Clojure:
:::

::: ulist
-   `clojure.core`

-   `clojure.core.protocols`: `Datafiable`, `Navigable`

-   `clojure.data`

-   `clojure.datafy`

-   `clojure.edn` aliased as `edn`

-   `clojure.math`

-   `clojure.java.browse`

-   `clojure.java.io` aliased as `io`:

    ::: ulist
    -   `as-relative-path`, `as-url`, `copy`, `delete-file`, `file`,
        `input-stream`, `make-parents`, `output-stream`, `reader`,
        `resource`, `writer`
    :::

-   `clojure.java.shell` aliased as `shell`

-   `clojure.main`: `demunge`, `repl`, `repl-requires`

-   `clojure.pprint`: `pprint`, `cl-format`

-   `clojure.set` aliased as `set`

-   `clojure.string` aliased as `str`

-   `clojure.stacktrace`

-   `clojure.test`

-   `clojure.walk`

-   `clojure.zip`
:::

::: paragraph
Additional libraries:
:::

::: ulist
-   [`babashka.cli`](https://github.com/babashka/cli): CLI arg parsing

-   [`babashka.http-client`](https://github.com/babashka/http-client):
    making HTTP requests

-   [`babashka.process`](https://github.com/babashka/process): shelling
    out to external processes

-   [`babashka.fs`](https://github.com/babashka/fs): file system
    manipulation

-   [`bencode.core`](https://github.com/nrepl/bencode) aliased as
    `bencode`: `read-bencode`, `write-bencode`

-   [`cheshire.core`](https://github.com/dakrone/cheshire) aliased as
    `json`: dealing with JSON

-   [`clojure.core.async`](https://clojure.github.io/core.async/)
    aliased as `async`.

-   [`clojure.data.csv`](https://github.com/clojure/data.csv) aliased as
    `csv`

-   [`clojure.data.xml`](https://github.com/clojure/data.xml) aliased as
    `xml`

-   [`clojure.tools.cli`](https://github.com/clojure/tools.cli) aliased
    as `tools.cli`

-   [`clj-yaml.core`](https://github.com/clj-commons/clj-yaml) alias as
    `yaml`

-   [`cognitect.transit`](https://github.com/cognitect/transit-clj)
    aliased as `transit`

-   [`org.httpkit.client`](https://github.com/http-kit/http-kit)

-   [`org.httpkit.server`](https://github.com/http-kit/http-kit)

-   [`clojure.core.match`](https://github.com/clojure/core.match)

-   [`hiccup.core`](https://github.com/weavejester/hiccup/) and
    `hiccup2.core`

-   [`clojure.test.check`](https://github.com/clojure/test.check):

    ::: ulist
    -   `clojure.test.check`

    -   `clojure.test.check.generators`

    -   `clojure.test.check.properties`
    :::

-   [`rewrite-clj`](https://github.com/clj-commons/rewrite-clj):

    ::: ulist
    -   `rewrite-clj.parser`

    -   `rewrite-clj.node`

    -   `rewrite-clj.zip`

    -   `rewrite-clj.paredit`
    :::

-   [`Selmer`](https://github.com/yogthos/Selmer):

    ::: ulist
    -   `selmer.parser`
    :::

-   [`clojure.tools.logging`](https://github.com/clojure/tools.logging)

-   [`timbre`](https://github.com/ptaoussanis/timbre): logging

-   [`edamame`](https://github.com/borkdude/edamame): Clojure parser

-   [`core.rrb-vector`](https://github.com/clojure/core.rrb-vector)
:::

::: paragraph
Check out the [babashka toolbox](https://babashka.org/toolbox/) and
[projects](https://github.com/borkdude/babashka/blob/master/doc/projects.md)
page for libraries that are not built-in, but which you can load as an
external dependency in [`bb.edn`](https://book.babashka.org/#_bb_edn).
:::

::: paragraph
See the
[build](https://github.com/borkdude/babashka/blob/master/doc/build.md)
page for built-in libraries that can be enabled via feature flags, if
you want to compile babashka yourself.
:::

::: paragraph
A selection of Java classes are available, see
[`babashka/impl/classes.clj`](https://github.com/babashka/babashka/blob/master/src/babashka/impl/classes.clj)
in babashka's git repo.
:::
:::

::: sect2
### [Babashka namespaces](#_babashka_namespaces){.link} {#_babashka_namespaces}

::: sect3
#### [babashka.classpath](#babashka_classpath){.link} {#babashka_classpath}

::: paragraph
Available functions:
:::

::: ulist
-   `add-classpath`

-   `get-classpath`

-   `split-classpath`
:::

::: sect4
##### [add-classpath](#_add_classpath){.link} {#_add_classpath}

::: paragraph
The function `add-classpath` which can be used to add to the classpath
dynamically:
:::

::: listingblock
::: content
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
:::
:::
:::

::: sect4
##### [get-classpath](#_get_classpath){.link} {#_get_classpath}

::: paragraph
The function `get-classpath` returns the classpath as set by
`--classpath`, `BABASHKA_CLASSPATH` and `add-classpath`.
:::
:::

::: sect4
##### [split-classpath](#_split_classpath){.link} {#_split_classpath}

::: paragraph
Given a classpath, returns a seq of strings as the result of splitting
the classpath by the platform specific path separatator.
:::
:::
:::

::: sect3
#### [babashka.deps](#babashkadeps){.link} {#babashkadeps}

::: paragraph
Available functions:
:::

::: ulist
-   `add-deps`

-   `clojure`

-   `merge-deps`
:::

::: sect4
##### [add-deps](#_add_deps){.link} {#_add_deps}

::: paragraph
The function `add-deps` takes a deps edn map like
`{:deps {medley/medley {:mvn/version "1.3.0"}}}`, resolves it using
[deps.clj](https://github.com/borkdude/deps.clj) and then adds to the
babashka classpath accordingly.
:::

::: paragraph
Example:
:::

::: listingblock
::: content
``` {.rouge .highlight}
(require '[babashka.deps :as deps])

(deps/add-deps '{:deps {medley/medley {:mvn/version "1.3.0"}}})

(require '[medley.core :as m])
(m/index-by :id [{:id 1} {:id 2}])
```
:::
:::

::: paragraph
Optionally, `add-deps` takes a second arg with options. Currently the
only option is `:aliases` which will affect how deps are resolved:
:::

::: paragraph
Example:
:::

::: listingblock
::: content
``` {.rouge .highlight}
(deps/add-deps '{:aliases {:medley {:extra-deps {medley/medley {:mvn/version "1.3.0"}}}}}
               {:aliases [:medley]})
```
:::
:::
:::

::: sect4
##### [clojure](#_clojure){.link} {#_clojure}

::: paragraph
The function `clojure` takes a sequential collection of arguments,
similar to the clojure CLI. The arguments are then passed to
[deps.clj](https://github.com/borkdude/deps.clj). The `clojure` function
returns `nil` and prints to `*out*` for commands like `-Stree`, and
`-Spath`. For `-M`, `-X` and `-A` it invokes `java` with
`babashka.process/process` (see [babashka.process](#babashkaprocess))
and returns the associated record. For more details, read the docstring
with:
:::

::: listingblock
::: content
``` {.rouge .highlight}
(require '[clojure.repl :refer [doc]])
(doc babashka.deps/clojure)
```
:::
:::

::: paragraph
Example:
:::

::: paragraph
The following script passes through command line arguments to clojure,
while adding the medley dependency:
:::

::: listingblock
::: content
``` {.rouge .highlight}
(require '[babashka.deps :as deps])

(def deps '{:deps {medley/medley {:mvn/version "1.3.0"}}})
(def clojure-args (list* "-Sdeps" deps  *command-line-args*))

(if-let [proc (deps/clojure clojure-args)]
  (-> @proc :exit (System/exit))
  (System/exit 0))
```
:::
:::
:::
:::

::: sect3
#### [babashka.wait](#babashkawait){.link} {#babashkawait}

::: paragraph
Contains the functions: `wait-for-port` and `wait-for-path`.
:::

::: paragraph
Usage of `wait-for-port`:
:::

::: listingblock
::: content
``` {.rouge .highlight}
(wait/wait-for-port "localhost" 8080)
(wait/wait-for-port "localhost" 8080 {:timeout 1000 :pause 1000})
```
:::
:::

::: paragraph
Waits for TCP connection to be available on host and port. Options map
supports `:timeout` and `:pause`. If `:timeout` is provided and reached,
`:default`\'s value (if any) is returned. The `:pause` option determines
the time waited between retries.
:::

::: paragraph
Usage of `wait-for-path`:
:::

::: listingblock
::: content
``` {.rouge .highlight}
(wait/wait-for-path "/tmp/wait-path-test")
(wait/wait-for-path "/tmp/wait-path-test" {:timeout 1000 :pause 1000})
```
:::
:::

::: paragraph
Waits for file path to be available. Options map supports `:default`,
`:timeout` and `:pause`. If `:timeout` is provided and reached,
`:default`\'s value (if any) is returned. The `:pause` option determines
the time waited between retries.
:::

::: paragraph
The namespace `babashka.wait` is aliased as `wait` in the `user`
namespace.
:::
:::

::: sect3
#### [babashka.signal](#babashkasignal){.link} {#babashkasignal}

::: paragraph
Contains the function `signal/pipe-signal-received?`. Usage:
:::

::: listingblock
::: content
``` {.rouge .highlight}
(signal/pipe-signal-received?)
```
:::
:::

::: paragraph
Returns true if `PIPE` signal was received. Example:
:::

::: listingblock
::: content
``` {.rouge .highlight}
$ bb -e '((fn [x] (println x) (when (not (signal/pipe-signal-received?)) (recur (inc x)))) 0)' | head -n2
1
2
```
:::
:::

::: paragraph
The namespace `babashka.signal` is aliased as `signal` in the `user`
namespace.
:::
:::

::: sect3
#### [babashka.http-client](#babashkahttpclient){.link} {#babashkahttpclient}

::: paragraph
The `babashka.http-client` library for making HTTP requests. See
[babashka.http-client](https://github.com/babashka/http-client) for how
to use it.
:::
:::

::: sect3
#### [babashka.process](#babashkaprocess){.link} {#babashkaprocess}

::: paragraph
The `babashka.process` library. See the
[process](https://github.com/babashka/process) repo for API docs.
:::
:::

::: sect3
#### [babashka.fs](#babashkafs){.link} {#babashkafs}

::: paragraph
The `babashka.fs` library offers file system utilities. See the
[fs](https://github.com/babashka/fs) repo for API docs.
:::
:::

::: sect3
#### [babashka.cli](#babashkacli){.link} {#babashkacli}

::: paragraph
The `babashka.cli` library allows you to turn functions into CLIs. See
the [cli](https://github.com/babashka/cli) repo for API docs and check
out the [babashka CLI](https://book.babashka.org/#_babashka_cli) chapter
on how to use it from the command line or with
[tasks](https://book.babashka.org/#tasks).
:::
:::
:::

::: sect2
### [Projects](#_projects){.link} {#_projects}

::: paragraph
Babashka is able to run Clojure projects from source, if they are
compatible with the subset of Clojure that sci is capable of running.
:::

::: paragraph
Check this
[page](https://github.com/borkdude/babashka/blob/master/doc/projects.md)
for projects that are known to work with babashka.
:::

::: paragraph
Do you have a library that is compatible with babashka? Add the official
badge to give some flair to your repo!
:::

::: paragraph
[[![svg+xml;base64,PHN2ZyB3aWR0aD0iMTQiIGhlaWdodD0iMTQiIHZpZXdCb3g9IjAgMCAxNDcyIDE0NzIiIGZpbGw9Im5vbmUiIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyI+CjxwYXRoIGZpbGwtcnVsZT0iZXZlbm9kZCIgY2xpcC1ydWxlPSJldmVub2RkIiBkPSJNOTk1LjkxMSAxMTkzLjg5QzExMTAuOTMgMTExMi4zOCAxMTg2IDk3OC4yMDYgMTE4NiA4MjYuNUMxMTg2IDU3Ny45NzIgMTAxNCAyNTQuNSA3MzYgMTEzQzQ2MC41IDI2MiAyODYgNTc3Ljk3MiAyODYgODI2LjVDMjg2IDk3OC4yMDYgMzYxLjA3IDExMTIuMzggNDc2LjA4OSAxMTkzLjg5QzQ3MS45ODMgMTE5NC40NCA0NjcuOTQ5IDExOTUuMTQgNDY0IDExOTZDMzc2LjYxMSAxMjE1LjA3IDI3MCAxMzU5LjA1IDI3MCAxMzU5LjA1SDUzNEM1ODAuOTcyIDEzNTkuMDUgNjI1LjYzMSAxMzQxLjQxIDY2MC45NTYgMTMyMS42MkM2NzguMTE1IDEzNDQuMzIgNzA1LjM0NCAxMzU5IDczNiAxMzU5Qzc2Ni42NTYgMTM1OSA3OTMuODg1IDEzNDQuMzIgODExLjA0NCAxMzIxLjYyQzg0Ni4zNjkgMTM0MS40MSA4OTEuMDI4IDEzNTkuMDUgOTM4IDEzNTkuMDVIMTIwMkMxMjAyIDEzNTkuMDUgMTA5NS4zOSAxMjE1LjA3IDEwMDggMTE5NkMxMDA0LjA1IDExOTUuMTQgMTAwMC4wMiAxMTk0LjQ0IDk5NS45MTEgMTE5My44OVoiIGZpbGw9InVybCgjcGFpbnQwX2xpbmVhcikiLz4KPGNpcmNsZSBjeD0iNzM1LjUiIGN5PSI4NTIuNSIgcj0iMzExLjUiIGZpbGw9IndoaXRlIi8+CjxwYXRoIGQ9Ik00NDMgNzUwLjQxN0M2NjIgNzY1Ljg2NiA3OTEuMzgyIDcxMi4zODggODgxIDU3OS44ODFDODgxIDU3OS44ODEgNzA4LjI5MyA1MjEuNjQ5IDYwMy4yNDQgNTc5Ljg4MUM0OTguNTUgNjM3LjkxNiA0NDMgNzUwLjQxNyA0NDMgNzUwLjQxN1oiIGZpbGw9IiNCNEI0QjQiLz4KPHBhdGggZD0iTTEwMzkuOTkgNzQ4LjI4MkM5MTQuODYyIDczNC43NDUgODAzLjI2NCA2OTQuMTM1IDc1MCA1NDFDNzUwIDU0MSA4NjAuNjMzIDU2Ni4yNzIgOTcyLjM1NSA2NjguMTAxQzEwODguMzkgNzczLjg1NyAxMDM5Ljk5IDc0OC4yODIgMTAzOS45OSA3NDguMjgyWiIgZmlsbD0iI0I0QjRCNCIvPgo8Y2lyY2xlIGN4PSI3MzUuNSIgY3k9Ijg1Mi41IiByPSIzMTEuNSIgc3Ryb2tlPSIjMEEwMDAwIiBzdHJva2Utd2lkdGg9IjQwIi8+CjxwYXRoIGQ9Ik03OTcgNzE5SDY3NEw3MDQgNzQ5VjgzOUM3MDguNSA4MDkuNSA3NjQuNSA4MDguNSA3NjcuNSA4MzlMNzc1IDc0OUw3OTcgNzE5WiIgZmlsbD0iYmxhY2siLz4KPHBhdGggZD0iTTM1MS4xMTcgNzU5QzM1MS4xMTcgNzM2LjkwOSAzNjkuMDI2IDcxOSAzOTEuMTE3IDcxOUg2NzYuMDE4QzcwMC4yIDcxOSA3MTguODUyIDc0MC4yOTIgNzE1LjY3IDc2NC4yNjRMNjkwLjkxIDk1MC43NjRDNjg4LjI3IDk3MC42NDYgNjcxLjMxNCA5ODUuNSA2NTEuMjU4IDk4NS41SDM5MS4xMTdDMzY5LjAyNiA5ODUuNSAzNTEuMTE3IDk2Ny41OTEgMzUxLjExNyA5NDUuNVY3NTlaIiBmaWxsPSJibGFjayIvPgo8cGF0aCBkPSJNNzU2LjAxMiA3NjQuMjY3Qzc1Mi44MjggNzQwLjI5NCA3NzEuNDgxIDcxOSA3OTUuNjY0IDcxOUgxMDgwQzExMDIuMDkgNzE5IDExMjAgNzM2LjkwOSAxMTIwIDc1OVY5NDUuMDk2QzExMjAgOTY3LjE4NyAxMTAyLjA5IDk4NS4wOTYgMTA4MCA5ODUuMDk2SDgyMC4zODFDODAwLjMyNSA5ODUuMDk2IDc4My4zNyA5NzAuMjQ0IDc4MC43MjkgOTUwLjM2Mkw3NTYuMDEyIDc2NC4yNjdaIiBmaWxsPSJibGFjayIvPgo8cGF0aCBkPSJNMTAyNCA5NTJWOTIxLjQ3SDEwMTMuNzFDMTAwNy4wMyA5MjEuNDcgMTAwMi41OCA5MTYuNzUyIDEwMDAuMzUgOTA5LjgxM0w5NTguMDY0IDc4Mi42OTdDOTU1LjI4MiA3NzQuNjQ5IDk1MS45NDQgNzY4LjgyIDk0Ny40OTIgNzY0LjM3OUM5MzkuMTQ2IDc1NS43NzUgOTI4LjI5NiA3NTMgOTE2LjA1NCA3NTNIOTAyLjdWNzg0LjkxOEg5MTEuNjAzQzkxOS4xMTUgNzg0LjkxOCA5MjQuOTU3IDc4Ny42OTMgOTI3LjQ2MSA3OTYuNTc1TDkzMy4zMDMgODE3LjExM0w4ODEgOTUySDkxOS4xMTVMOTUwLjU1MyA4NjMuMTg1TDk2Ny4yNDUgOTE2Ljc1MkM5NzMuNjQ0IDkzNy41NjggOTg0LjQ5NCA5NTIgMTAwOC45OCA5NTJIMTAyNFoiIGZpbGw9IndoaXRlIi8+CjxwYXRoIGQ9Ik01OTAgOTUyVjkyMS40N0g1NzkuNzA2QzU3My4wMjkgOTIxLjQ3IDU2OC41NzggOTE2Ljc1MiA1NjYuMzUyIDkwOS44MTNMNTI0LjA2NCA3ODIuNjk3QzUyMS4yODIgNzc0LjY0OSA1MTcuOTQ0IDc2OC44MiA1MTMuNDkyIDc2NC4zNzlDNTA1LjE0NiA3NTUuNzc1IDQ5NC4yOTYgNzUzIDQ4Mi4wNTQgNzUzSDQ2OC43Vjc4NC45MThINDc3LjYwM0M0ODUuMTE1IDc4NC45MTggNDkwLjk1NyA3ODcuNjkzIDQ5My40NjEgNzk2LjU3NUw0OTkuMzAzIDgxNy4xMTNMNDQ3IDk1Mkg0ODUuMTE1TDUxNi41NTMgODYzLjE4NUw1MzMuMjQ1IDkxNi43NTJDNTM5LjY0NCA5MzcuNTY4IDU1MC40OTQgOTUyIDU3NC45NzcgOTUySDU5MFoiIGZpbGw9IndoaXRlIi8+CjxkZWZzPgo8bGluZWFyR3JhZGllbnQgaWQ9InBhaW50MF9saW5lYXIiIHgxPSI3NTIiIHkxPSIxMTMiIHgyPSI3NTIiIHkyPSIxMzU5LjUyIiBncmFkaWVudFVuaXRzPSJ1c2VyU3BhY2VPblVzZSI+CjxzdG9wIHN0b3AtY29sb3I9IiNFNDFGMjYiLz4KPHN0b3Agb2Zmc2V0PSIxIiBzdG9wLWNvbG9yPSIjQjcwMDAwIi8+CjwvbGluZWFyR3JhZGllbnQ+CjwvZGVmcz4KPC9zdmc+Cg==](https://img.shields.io/badge/babashka-compatible-green?logo=data:image/svg+xml;base64,PHN2ZyB3aWR0aD0iMTQiIGhlaWdodD0iMTQiIHZpZXdCb3g9IjAgMCAxNDcyIDE0NzIiIGZpbGw9Im5vbmUiIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyI+CjxwYXRoIGZpbGwtcnVsZT0iZXZlbm9kZCIgY2xpcC1ydWxlPSJldmVub2RkIiBkPSJNOTk1LjkxMSAxMTkzLjg5QzExMTAuOTMgMTExMi4zOCAxMTg2IDk3OC4yMDYgMTE4NiA4MjYuNUMxMTg2IDU3Ny45NzIgMTAxNCAyNTQuNSA3MzYgMTEzQzQ2MC41IDI2MiAyODYgNTc3Ljk3MiAyODYgODI2LjVDMjg2IDk3OC4yMDYgMzYxLjA3IDExMTIuMzggNDc2LjA4OSAxMTkzLjg5QzQ3MS45ODMgMTE5NC40NCA0NjcuOTQ5IDExOTUuMTQgNDY0IDExOTZDMzc2LjYxMSAxMjE1LjA3IDI3MCAxMzU5LjA1IDI3MCAxMzU5LjA1SDUzNEM1ODAuOTcyIDEzNTkuMDUgNjI1LjYzMSAxMzQxLjQxIDY2MC45NTYgMTMyMS42MkM2NzguMTE1IDEzNDQuMzIgNzA1LjM0NCAxMzU5IDczNiAxMzU5Qzc2Ni42NTYgMTM1OSA3OTMuODg1IDEzNDQuMzIgODExLjA0NCAxMzIxLjYyQzg0Ni4zNjkgMTM0MS40MSA4OTEuMDI4IDEzNTkuMDUgOTM4IDEzNTkuMDVIMTIwMkMxMjAyIDEzNTkuMDUgMTA5NS4zOSAxMjE1LjA3IDEwMDggMTE5NkMxMDA0LjA1IDExOTUuMTQgMTAwMC4wMiAxMTk0LjQ0IDk5NS45MTEgMTE5My44OVoiIGZpbGw9InVybCgjcGFpbnQwX2xpbmVhcikiLz4KPGNpcmNsZSBjeD0iNzM1LjUiIGN5PSI4NTIuNSIgcj0iMzExLjUiIGZpbGw9IndoaXRlIi8+CjxwYXRoIGQ9Ik00NDMgNzUwLjQxN0M2NjIgNzY1Ljg2NiA3OTEuMzgyIDcxMi4zODggODgxIDU3OS44ODFDODgxIDU3OS44ODEgNzA4LjI5MyA1MjEuNjQ5IDYwMy4yNDQgNTc5Ljg4MUM0OTguNTUgNjM3LjkxNiA0NDMgNzUwLjQxNyA0NDMgNzUwLjQxN1oiIGZpbGw9IiNCNEI0QjQiLz4KPHBhdGggZD0iTTEwMzkuOTkgNzQ4LjI4MkM5MTQuODYyIDczNC43NDUgODAzLjI2NCA2OTQuMTM1IDc1MCA1NDFDNzUwIDU0MSA4NjAuNjMzIDU2Ni4yNzIgOTcyLjM1NSA2NjguMTAxQzEwODguMzkgNzczLjg1NyAxMDM5Ljk5IDc0OC4yODIgMTAzOS45OSA3NDguMjgyWiIgZmlsbD0iI0I0QjRCNCIvPgo8Y2lyY2xlIGN4PSI3MzUuNSIgY3k9Ijg1Mi41IiByPSIzMTEuNSIgc3Ryb2tlPSIjMEEwMDAwIiBzdHJva2Utd2lkdGg9IjQwIi8+CjxwYXRoIGQ9Ik03OTcgNzE5SDY3NEw3MDQgNzQ5VjgzOUM3MDguNSA4MDkuNSA3NjQuNSA4MDguNSA3NjcuNSA4MzlMNzc1IDc0OUw3OTcgNzE5WiIgZmlsbD0iYmxhY2siLz4KPHBhdGggZD0iTTM1MS4xMTcgNzU5QzM1MS4xMTcgNzM2LjkwOSAzNjkuMDI2IDcxOSAzOTEuMTE3IDcxOUg2NzYuMDE4QzcwMC4yIDcxOSA3MTguODUyIDc0MC4yOTIgNzE1LjY3IDc2NC4yNjRMNjkwLjkxIDk1MC43NjRDNjg4LjI3IDk3MC42NDYgNjcxLjMxNCA5ODUuNSA2NTEuMjU4IDk4NS41SDM5MS4xMTdDMzY5LjAyNiA5ODUuNSAzNTEuMTE3IDk2Ny41OTEgMzUxLjExNyA5NDUuNVY3NTlaIiBmaWxsPSJibGFjayIvPgo8cGF0aCBkPSJNNzU2LjAxMiA3NjQuMjY3Qzc1Mi44MjggNzQwLjI5NCA3NzEuNDgxIDcxOSA3OTUuNjY0IDcxOUgxMDgwQzExMDIuMDkgNzE5IDExMjAgNzM2LjkwOSAxMTIwIDc1OVY5NDUuMDk2QzExMjAgOTY3LjE4NyAxMTAyLjA5IDk4NS4wOTYgMTA4MCA5ODUuMDk2SDgyMC4zODFDODAwLjMyNSA5ODUuMDk2IDc4My4zNyA5NzAuMjQ0IDc4MC43MjkgOTUwLjM2Mkw3NTYuMDEyIDc2NC4yNjdaIiBmaWxsPSJibGFjayIvPgo8cGF0aCBkPSJNMTAyNCA5NTJWOTIxLjQ3SDEwMTMuNzFDMTAwNy4wMyA5MjEuNDcgMTAwMi41OCA5MTYuNzUyIDEwMDAuMzUgOTA5LjgxM0w5NTguMDY0IDc4Mi42OTdDOTU1LjI4MiA3NzQuNjQ5IDk1MS45NDQgNzY4LjgyIDk0Ny40OTIgNzY0LjM3OUM5MzkuMTQ2IDc1NS43NzUgOTI4LjI5NiA3NTMgOTE2LjA1NCA3NTNIOTAyLjdWNzg0LjkxOEg5MTEuNjAzQzkxOS4xMTUgNzg0LjkxOCA5MjQuOTU3IDc4Ny42OTMgOTI3LjQ2MSA3OTYuNTc1TDkzMy4zMDMgODE3LjExM0w4ODEgOTUySDkxOS4xMTVMOTUwLjU1MyA4NjMuMTg1TDk2Ny4yNDUgOTE2Ljc1MkM5NzMuNjQ0IDkzNy41NjggOTg0LjQ5NCA5NTIgMTAwOC45OCA5NTJIMTAyNFoiIGZpbGw9IndoaXRlIi8+CjxwYXRoIGQ9Ik01OTAgOTUyVjkyMS40N0g1NzkuNzA2QzU3My4wMjkgOTIxLjQ3IDU2OC41NzggOTE2Ljc1MiA1NjYuMzUyIDkwOS44MTNMNTI0LjA2NCA3ODIuNjk3QzUyMS4yODIgNzc0LjY0OSA1MTcuOTQ0IDc2OC44MiA1MTMuNDkyIDc2NC4zNzlDNTA1LjE0NiA3NTUuNzc1IDQ5NC4yOTYgNzUzIDQ4Mi4wNTQgNzUzSDQ2OC43Vjc4NC45MThINDc3LjYwM0M0ODUuMTE1IDc4NC45MTggNDkwLjk1NyA3ODcuNjkzIDQ5My40NjEgNzk2LjU3NUw0OTkuMzAzIDgxNy4xMTNMNDQ3IDk1Mkg0ODUuMTE1TDUxNi41NTMgODYzLjE4NUw1MzMuMjQ1IDkxNi43NTJDNTM5LjY0NCA5MzcuNTY4IDU1MC40OTQgOTUyIDU3NC45NzcgOTUySDU5MFoiIGZpbGw9IndoaXRlIi8+CjxkZWZzPgo8bGluZWFyR3JhZGllbnQgaWQ9InBhaW50MF9saW5lYXIiIHgxPSI3NTIiIHkxPSIxMTMiIHgyPSI3NTIiIHkyPSIxMzU5LjUyIiBncmFkaWVudFVuaXRzPSJ1c2VyU3BhY2VPblVzZSI+CjxzdG9wIHN0b3AtY29sb3I9IiNFNDFGMjYiLz4KPHN0b3Agb2Zmc2V0PSIxIiBzdG9wLWNvbG9yPSIjQjcwMDAwIi8+CjwvbGluZWFyR3JhZGllbnQ+CjwvZGVmcz4KPC9zdmc+Cg== "bb compatible")](https://babashka.org){.image}]{.image}
:::
:::
:::
:::

::: sect1
## [Pods](#pods){.link}

::: sectionbody
::: paragraph
Pods are programs that can be used as a Clojure library by babashka.
Documentation is available in the [library
repo](https://github.com/babashka/babashka.pods).
:::

::: paragraph
A list of available pods can be found
[here](https://github.com/borkdude/babashka/blob/master/doc/projects.md#pods).
:::

::: sect2
### [Pod registry](#_pod_registry){.link} {#_pod_registry}

::: paragraph
Since bb 0.2.6 pods can be obtained via the
[pod-registry](https://github.com/babashka/pod-registry).
:::

::: paragraph
This is an example script which uses the
[fswatcher](https://github.com/babashka/pod-babashka-fswatcher) pod to
watch a directory for changes:
:::

::: listingblock
::: content
``` {.rouge .highlight}
#!/usr/bin/env bb

(require '[babashka.pods :as pods])
(pods/load-pod 'org.babashka/fswatcher "0.0.3")

(require '[pod.babashka.fswatcher :as fw])

(fw/watch "." prn {:delay-ms 5000})

(println "Watching current directory for changes... Press Ctrl-C to quit.")

@(promise)
```
:::
:::
:::

::: sect2
### [Pods in bb.edn](#_pods_in_bb_edn){.link} {#_pods_in_bb_edn}

::: paragraph
Since bb 0.8.0 pods can be declared in `bb.edn`:
:::

::: listingblock
::: content
``` {.rouge .highlight}
{:paths ["bb"]
 :pods {org.babashka/go-sqlite3 {:version "0.1.0"}}}
```
:::
:::

::: paragraph
Given the file `bb/my_project/db.clj`:
:::

::: listingblock
::: content
``` {.rouge .highlight}
(ns my-project.db
  (:require [pod.babashka.go-sqlite3 :as sqlite]))

(defn -main [& _args]
  (prn (sqlite/query ":memory:" ["SELECT 1 + 1 AS sum"])))
```
:::
:::

::: paragraph
you can then execute the main function, without calling `load-pod`
manually:
:::

::: listingblock
::: content
``` {.rouge .highlight}
$ bb -m my-project.db
[{:sum 2}]
```
:::
:::
:::
:::
:::

::: sect1
## [Style](#style){.link}

::: sectionbody
::: paragraph
A note on style. Babashka recommends the following:
:::

::: sect2
### [Explicit requires](#_explicit_requires){.link} {#_explicit_requires}

::: paragraph
Use explicit requires with namespace aliases in scripts, unless you're
writing one-liners.
:::

::: paragraph
Do this:
:::

::: listingblock
::: content
``` {.rouge .highlight}
$ ls | bb -i '(-> *input* first (str/includes? "m"))'
true
```
:::
:::

::: paragraph
But not this:
:::

::: paragraph
script.clj:
:::

::: listingblock
::: content
``` {.rouge .highlight}
(-> *input* first (str/includes? "m"))
```
:::
:::

::: paragraph
Rather do this:
:::

::: paragraph
script.clj:
:::

::: listingblock
::: content
``` {.rouge .highlight}
(ns script
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))
(-> (io/reader *in*) line-seq first (str/includes? "m"))
```
:::
:::

::: paragraph
Some reasons for this:
:::

::: ulist
-   Linters like clj-kondo work better with code that uses namespace
    forms, explicit requires, and known Clojure constructs

-   Editor tooling works better with namespace forms (sorting requires,
    etc).

-   Writing compatible code gives you the option to run the same script
    with `clojure`
:::
:::
:::
:::

::: sect1
## [Child processes](#child_processes){.link} {#child_processes}

::: sectionbody
::: paragraph
For child processes, the babashka
[process](https://github.com/babashka/process) library is recommended.
It is built into babashka. Check out the README which gives a good
introduction into the library.
:::
:::
:::

::: sect1
## [Recipes](#recipes){.link}

::: sectionbody
::: sect2
### [Running tests](#_running_tests){.link} {#_running_tests}

::: paragraph
Babashka bundles `clojure.test`. To run tests you can write a test
runner script. Given the following project structure:
:::

::: listingblock
::: content
``` {.rouge .highlight}
.
├── src
│   └──...
└── test
    └── your
        ├── test_a.clj
        └── test_b.clj
```
:::
:::

::: listingblock
::: title
test-runner.clj
:::

::: content
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
:::
:::

::: {.colist .arabic}
  ------- ----------------------------------------------------------------------------------
  **1**   Add sources and tests to the classpath
  **2**   Require the test namespaces
  **3**   Run all tests in the test namespaces
  **4**   Exit the test script with a non-zero exit code when there are failures or errors
  ------- ----------------------------------------------------------------------------------
:::
:::

::: sect2
### [Main file](#main_file){.link} {#main_file}

::: paragraph
In Python scripts there is a well-known pattern to check if the current
file was the file invoked from the command line, or loaded from another
file: the `__name__ == "__main__"` pattern. In babashka this pattern can
be implemented with:
:::

::: listingblock
::: content
``` {.rouge .highlight}
(= *file* (System/getProperty "babashka.file"))
```
:::
:::

::: paragraph
Combining this with a conditional invocation of `-main` creates a script
file that is safe to load at a REPL, and easy to invoke at the CLI.
:::

::: listingblock
::: content
``` {.rouge .highlight}
#!/usr/bin/env bb

;; Various functions defined here

(defn -main [& args]
;; Implementation of main
)

(when (= *file* (System/getProperty "babashka.file"))
  (apply -main *command-line-args*))
```
:::
:::

::: paragraph
This can be exceedingly handy for editing complex scripts interactively,
while not being able to adjust how they are invoked by other tools.
:::
:::

::: sect2
### [Shutdown hook](#_shutdown_hook){.link} {#_shutdown_hook}

::: paragraph
Adding a shutdown hook allows you to execute some code before the script
exits.
:::

::: listingblock
::: content
``` {.rouge .highlight}
$ bb -e '(-> (Runtime/getRuntime) (.addShutdownHook (Thread. #(println "bye"))))'
bye
```
:::
:::

::: paragraph
This also works when the script is interrupted with ctrl-c.
:::
:::

::: sect2
### [Printing returned values](#_printing_returned_values){.link} {#_printing_returned_values}

::: paragraph
Babashka doesn't print a returned `nil` as lots of scripts end in
something side-effecting.
:::

::: listingblock
::: content
``` {.rouge .highlight}
$ bb -e '(:a {:a 5})'
5
$ bb -e '(:b {:a 5})'
$
```
:::
:::

::: paragraph
If you really want to print the nil, you can use `(prn ..)` instead.
:::

::: sect3
#### [HTTP over Unix sockets](#_http_over_unix_sockets){.link} {#_http_over_unix_sockets}

::: paragraph
This can be useful for talking to Docker:
:::

::: listingblock
::: content
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
:::
:::
:::
:::

::: sect2
### [Core.async](#core_async){.link} {#core_async}

::: paragraph
In addition to `future`, `pmap`, `promise` and friends, you may use the
`clojure.core.async` namespace for asynchronous scripting. The following
example shows how to get first available value from two different
processes:
:::

::: listingblock
::: content
``` {.rouge .highlight}
bb -e '
(defn async-command [& args]
  (async/thread (apply shell/sh "bash" "-c" args)))

(-> (async/alts!! [(async-command "sleep 2 && echo process 1")
                   (async-command "sleep 1 && echo process 2")])
    first :out str/trim println)'
process 2
```
:::
:::

::: paragraph
Caveat: currently the `go` macro is available for compatibility with JVM
programs, but the implementation maps to `clojure.core.async/thread` and
the single exclamation mark operations (`<!`, `>!`, etc.) map to the
double exclamation mark operations (`<!!`, `>!!`, etc.). It will not
\"park\" threads, like on the JVM.
:::

::: paragraph
Examples like the following may still work, but will take a lot more
system resources than on the JVM and will break down for some high value
of `n`:
:::

::: listingblock
::: content
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
:::
:::
:::

::: sect2
### [Interacting with an nREPL server](#_interacting_with_an_nrepl_server){.link} {#_interacting_with_an_nrepl_server}

::: paragraph
Babashka comes with the
[nrepl/bencode](https://github.com/nrepl/bencode) library which allows
you to read and write bencode messages to a socket. A simple example
which evaluates a Clojure expression on an nREPL server started with
`lein repl`:
:::

::: listingblock
::: content
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
:::
:::
:::

::: sect2
### [Running from Cygwin/Git Bash](#_running_from_cygwingit_bash){.link} {#_running_from_cygwingit_bash}

::: paragraph
On Windows, `bb` can be invoked from the bash shell directly:
:::

::: listingblock
::: content
``` {.rouge .highlight}
$ bb -e '(+ 1 2 3)'
6
```
:::
:::

::: paragraph
However, creating a script that invokes `bb` via a shebang leads to an
error if the script is not in the current directory. Suppose you had the
following script named `hello` on your path:
:::

::: listingblock
::: content
``` {.rouge .highlight}
#!/usr/bin/env bb
(println "Hello, world!")
```
:::
:::

::: listingblock
::: content
``` {.rouge .highlight}
$ hello
----- Error --------------------------------------------------------------------
Type:     java.lang.Exception
Message:  File does not exist: /cygdrive/c/path/to/hello
```
:::
:::

::: paragraph
The problem here is that the shell is passing a Cygwin-style path to
`bb`, but `bb` can't recognize it because it wasn't compiled with
Cygwin.
:::

::: paragraph
The solution is to create a wrapper script that converts the
Cygwin-style path to a Windows-style path before invoking `bb`. Put the
following into a script called `bbwrap` somewhere on your Cygwin path,
say in `/usr/local/bin/bbwrap`:
:::

::: listingblock
::: content
``` {.rouge .highlight}
#!/bin/bash
SCRIPT=$1
shift
bb.exe $(cygpath -w $SCRIPT) $@
```
:::
:::

::: paragraph
Make sure to fix your original script to invoke `bbwrap` instead of `bb`
directly:
:::

::: listingblock
::: content
``` {.rouge .highlight}
#!/usr/bin/env bbwrap
(println "Hello, world!")
```
:::
:::
:::
:::
:::

::: sect1
## [Differences with Clojure](#differences-with-clojure){.link}

::: sectionbody
::: paragraph
Babashka is implemented using the [Small Clojure
Interpreter](https://github.com/borkdude/sci). This means that a snippet
or script is not compiled to JVM bytecode, but executed form by form by
a runtime which implements a substantial subset of Clojure. Babashka is
compiled to a native binary using
[GraalVM](https://github.com/oracle/graal). It comes with a selection of
built-in namespaces and functions from Clojure and other useful
libraries. The data types (numbers, strings, persistent collections) are
the same. Multi-threading is supported (`pmap`, `future`).
:::

::: paragraph
Differences with Clojure:
:::

::: ulist
-   A pre-selected set of Java classes are supported. You cannot add
    Java classes at runtime.

-   Interpretation comes with overhead. Therefore loops are slower than
    in Clojure on the JVM. In general interpretation yields slower
    programs than compiled programs.

-   No `deftype`, `definterface` and unboxed math.

-   `defprotocol` and `defrecord` are implemented using multimethods and
    regular maps. Ostensibly they work the same, but under the hood
    there are no Java classes that correspond to them.

-   Currently `reify` works only for one class at a time

-   The `clojure.core.async/go` macro is not (yet) supported. For
    compatibility it currently maps to `clojure.core.async/thread`. More
    info [here](#core_async).
:::
:::
:::

::: sect1
## [Resources](#resources){.link}

::: sectionbody
::: paragraph
Check out the list of resources in babashka's
[README.md](https://github.com/babashka/babashka#articles-podcasts-and-videos).
:::

::: sect2
### [Books](#_books){.link} {#_books}

::: sect3
#### [](#_babashka_babooka){.link}[Babashka Babooka](https://www.braveclojure.com/quests/babooka/) {#_babashka_babooka}

::: paragraph
If you're a fan of [Clojure for the Brave and
True](https://www.braveclojure.com/clojure-for-the-brave-and-true/), you
might enjoy [Babashka
Babooka](https://www.braveclojure.com/quests/babooka/), a book by the
same author, Daniel Higginbotham!
:::
:::
:::
:::
:::

::: sect1
## [Contributing](#contributing){.link}

::: sectionbody
::: paragraph
Visit Babashka book's [Github
repository](https://github.com/babashka/book) and make an issue and/or
PR.
:::
:::
:::

::: sect1
## [License](#license){.link}

::: sectionbody
::: paragraph
Copyright © 2020-2021 Michiel Borkent
:::

::: paragraph
Licensed under [CC BY-SA
4.0](https://creativecommons.org/licenses/by-sa/4.0).
:::
:::
:::
:::

::: {#footer}
::: {#footer-text}
Last updated 2023-01-23 15:27:14 +0100
:::
:::
