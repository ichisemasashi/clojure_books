
### トラブルに巻き込まれないために
    
Clojure関数の驚くべき点の1つは、"気をつければ " variadic `&` を混ぜて複数アリティの関数にできることです。ここで私たちが気をつけているのは
    
```clojure
(defn one-two-or-more
  ([a] (println "One arg:" a))
  ([a b] (println "Two args:" a b))
  ([a b & more] (println "More than two:" a b more)))
```

Clojureは鋭いので、重複する引数を持つ複数アリティの関数を定義させないことを覚えておく必要がある。例えば、最後の関数を

```clojure 
;; Oh no!
(defn one-two-or-more
  ([a] (println "One arg:" a))
  ([a b] (println "Two args:" a b))
  ([& more] (println "More than two:" more)))
```
  
であれば、Clojureコンパイラを通過できない。この問題は、2つのパラメータを持つ関数を呼び出したときに、どのアリティが評価されるべきかが不明確なことです。

また、このような「ボディに複数の式」関数を混同しないように注意する必要があります：


```clojure
(defn chatty-average
  ([a b]
  (println "chatty-average function called with 2 arguments")
  (println "** first argument:" a)
  (println "** second argument:" b)
  (/ (+ a b) 2.0)))
```

これをマルチアリティファンクションとするには：

```clojure
(defn chatty-multi-average
  ([a b]
    (println "chatty-average function called with 2 arguments")
    (/ (+ a b) 2.0))
  ([a b c]
    (println "chatty-average function called with 3 arguments")
    (/ (+ a b c) 3.0)))
```

重要なのはパラメータを探すことで、それによってどの種類の関数なのかがわかります。


最後に、variadic関数を定義するときに覚えておいてほしいのは、`&` は普通の1文字の記号で、関数の引数を定義する文脈では特別な意味を持つということです。つまり、次のような意味です：

```clojure
(defn print-any-args [& args]
  (println "My arguments are:" args))
```

は任意の数の引数を取る関数であり：

```clojure
(defn print-any-args [&args]
  (println "My arguments are:" args))
```

はコンパイルできない。なぜか？もう一度見て、`&`と`args`の間に空白がないことに注目してください。つまり、`&args`という一つの引数で関数を定義しようとしているのです。この関数は束縛されていないシンボル`args`を使おうとして、コンパイラーエラーになる。私たちが書きたかったのは、`&`、空白、そして捕捉引数：`[& args]`である。


