
### 野生の中で

一般的なvarsはClojureコードを結合するモルタルなので、実際のコードではどこにでもあります。実際、defは[Clojureが起動する](https://github.com/clojure/clojure/blob/master/src/clj/clojure/core.clj)ときに最初に実行されるコードのいくつかに含まれています。
以下は、非常に早い段階で実行されるいくつかのdefの少し単純化されたバージョンです：

```clojure
(def second (fn second [x] (first (next x))))
(def ffirst (fn ffirst [x] (first (first x))))
```
   
これらは2つの便利な関数である： `second`はコレクションから2番目のアイテムを取り出し、`first`はコレクションから1番目のアイテムを取り出す。なぜこのコードでは、よりスマートな `defn` ではなく、わざわざ `def` と `fn` を使っているのか不思議に思うかもしれない。答えは簡単で、この2つの関数は `defn` の前に定義されているからだ。

動的なvarsはあまり一般的ではないが、まだ存在する。例えば、REPLで大きなコレクションを扱っている場合、それらを完全な形で表示したくないかもしれません。幸いにも、Clojureは`*print-length*`という動的なvarを提供します、これはコレクションが表示される量を制限します。想像がつくかもしれませんが、[Clojure本体の奥底](https://github.com/clojure/clojure/blob/master/src/clj/clojure/core_print.clj)のどこかに、`*print-length*`が動的varとして設定されています。ここでは、そのすべてのドキュメントを示します：



```clojure 
(def ^:dynamic
   ^{:doc "*print-length* controls how many items of each collection the
    printer will print. If it is bound to logical false, there is no
    limit. Otherwise, it must be bound to an integer indicating the maximum
    number of items of each collection to print. If a collection contains
    more items, the printer will print items up to the limit followed by
    '...' to represent the remaining items. The root binding is nil
    indicating no limit."
     :added "1.0"}
  *print-length* nil)
```

少し後、実際にプログラムを実行する直前に、Clojureは`*print-length*`の束縛を設定している：

```clojure
(binding [*print-length* nil]
  (run-your-code))
```

これにより `set!` が登場し、バインディングの「内側」から動的なvarの値を変更することができます。このベクターがあるとして、

```clojure
user=> (def books ["Emma" "2001" "Jaws" "Oliver Twist"])
```


そして、`*print-length*` を `2` に `set!`する：

```clojure
user=> (set! *print-length* 2)
```

すると、ベクターの最初の2項目だけが表示されます：

```clojure
user=> books
["Emma" "2001" ...]
```

他にも REPL での体験を向上させる動的な変数がいくつかある。例えば、`*1`（そう、これは左のイヤーマフしか持っていない）は常にREPLから得た最後の結果を束縛します。

```clojure
user=> (+ 2 2)
4
user=> *1
4
```

同様に、`*2`は最後から2番目の結果に、`*3`はその前の結果を束縛する：

```clojure
user=> "Austen"
"Austen"
user=> "King"
"King"
user=> "Orwell"
"Orwell"
user=> *3
"Austen"
```


最後に`*e`があり、これはREPLが最後の例外を束縛する：

```clojure
user=> (/ 1 0)
ArithmeticException Divide by zero clojure.lang.Numbers.divide
(Numbers.java:158)
user=> *e
#error {
 :cause "Divide by zero"
 :via
 [{:type java.lang.ArithmeticException
   :message "Divide by zero"
   :at [clojure.lang.Numbers divide "Numbers.java" 158]}]
 :trace
 [[clojure.lang.Numbers divide "Numbers.java" 158]
 << And so on for quite some time... >>
```


これで、前回のプログラムでの失敗をいつでも思い出すことができる！



