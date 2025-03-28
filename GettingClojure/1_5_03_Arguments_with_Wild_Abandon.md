
### 野放しの引数

`greet`のような、1つか2つ、あるいは他の明確な数の引数を取るような関数では、複数アリティの関数は問題ありません。しかし、完全に任意の数の引数を扱える関数を書きたい場合はどうすればいいでしょうか？良い知らせは、引数リストに & を戦略的に配置することでこれを実現できるということだ。

例えば、ここに任意の数の引数を受け取り、それらをすべて出力する関数がある：

```clojure
(defn print-any-args [& args]
  (println "My arguments are:" args))
```

誰かが `print-any-args` を呼び出すと、その引数は args パラメータ（アンパサンドの後）に「コレクションとして」現れる。そのため、`print-any-args`をこのように呼び出すと、次のようになる：

```clojure
(print-any-args 7 true nil)
```

これを見るだろう：

```
My arguments are as follows: (7 true nil)
```

同じ考え方で、最初の引数を返す関数を紹介しよう：

```clojure
(defn first-argument [& args]
  (first args))
```


さらに良いことに、`&`の前に普通の引数を置くことができるので、`first-argument`を次のように書き換えることができる：

```clojure
(defn new-first-argument [x & args] x)
```

このような `&` のマジックを利用した関数は「varargs」または「variadic」関数と呼ばれます。variadic関数と先ほど説明したmulti-arity関数の構文上の主な違いに注意してください：multi-arity関数は各引数セットに別々のボディを割くのに対し、variadic関数（`&`を持つもの）は単一の関数ボディを持ちます。


