
### 関数をその場で作成

関数値でできることは他にもある。(+ 2 3)` や `(* 5 x)` で新しい数値を作るのと同じように、`fn` を使って新しい関数を作ることができる。例えば、ここでは `fn` を使って、引数を2倍にする新しい関数を作っている：

```clojure
(fn [n] (* 2 n)) 
```


例からわかるように、`fn`を使うことは、名前を省くことを除けば、`defn`を使うこととよく似ている。`defn`と同じように、`fn`は新しい関数を作り、基本的にはパッケージ化されたビットコードになる。`fn`と`defn`の違いは、`fn`は生まれたばかりのコードの束を名前に束縛しないことだ。では、関数の値を使って何ができるのか？他のあらゆる値でできることだ。例えば、それを出力することができる：


```clojure
(println "A function:" (fn [n] (* 2 n)))
``` 

またはシンボルに束縛する：

```clojure
(def double-it (fn [n] (* 2 n))) 
```

そして最も重要なのは、それを呼ぶことだ：

```clojure
(double-it 10)        ; Gives you 20.
((fn [n] (* 2 n)) 10) ; Also gives you 20.
```


本の例に戻ると、以下は`cheap?`と同じことをする名前のない関数である：

```clojure
(fn [book]
  (when (<= (:price book) 9.99)
    book))
```

`fn`を使えば、関数を生成する関数を書くことができる：

```clojure
(defn cheaper-f [max-price]
  (fn [book]
    (when (<= (:price book) max-price)
      book)))
```

ここで重要なのは、どれだけメタなことをしたかを理解することだ： `cheaper-f`は、バーゲンを構成するものについての独自の考え方を持つ、バーゲンスポット関数の全ファミリーを生成する関数である。

```clojure
;; Define some helpful functions.
(def real-cheap? (cheaper-f 1.00))
(def kind-of-cheap? (cheaper-f 1.99))
(def marginally-cheap? (cheaper-f 5.99))

;; And use them.
(real-cheap? dracula)       ; Nope.
(kind-of-cheap? dracula)    ; Yes.
(marginally-cheap? dracula) ; Indeed.
```


もし、これがあまり壮大でないように見えたら、もう一度見てほしい。注意しなければならないのは、`fn`によって生成された関数は、`fn`が実行されたときのパラメータを覚えているということだ。つまり、最後の例では、`(cheaper-f 1.00)` を呼び出したときに生成される関数は `max-price` が `1.00` であることを記憶し、`(cheaper-f 5.99)` が生成する関数は `max-price` を `5.99` と記憶する。

さらに一歩進んで、`both?`のような関数を作る関数を書くこともできる：

```clojure
(defn both-f [predicate-f-1 predicate-f-2]
  (fn [book]
    (when (and (predicate-f-1 book) (predicate-f-2 book))
      book)))
```

`both-f`を使えば、本を分別する関数の一群を作ることができる：

```clojure
(def cheap-horror? (both-f cheap? horror?))
(def real-cheap-adventure? (both-f real-cheap? adventure?))
(def real-cheap-horror? (both-f real-cheap? horror?))
```

そして、さらにもう一段階メタのレベルを上げる：

```clojure
(def cheap-horror-possession?
  (both-f cheap-horror?
    (fn [book] (= (:title book) "Possession"))))
```

このように、ある関数が、その関数が生まれたときに存在していた束縛をつかんで記憶しておくという考え方を「クロージャー」と呼ぶ。関数が定義されたスコープを「close（閉じる）」と言います。何よりも、値としての関数と "クロージャ "という2つの考え方が、Clojureをプログラミング言語たらしめている核心であり、名前の由来にもなっています。
