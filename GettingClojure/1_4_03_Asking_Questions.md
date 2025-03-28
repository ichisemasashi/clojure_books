
### 質問をする

明示的に真か偽で分岐できることは、`if`がプログラミングの主力であることの半分でしかない。残りの半分は、ブール値に評価される質問をできることです。私たちがプログラムで尋ねる最も一般的な質問は、おそらく "このことはこの他のことと等しいですか？" です。幸いなことに、Clojureの等式テスト関数は非常にわかりやすい名前を持っています。

単に1つの等号です：

```clojure
(= 1 1)                         ; True!
(= 2 (+ 1 1))                   ; True again!
(= "Anna Karenina" "Jane Eyre") ; Nope.
(= "Emma" "Emma")               ; Yes!
```

また、 `+` や `*` と同様に、 `=` 関数も演算子のように見えますが、実際には単なる関数です。

そして、 `+` や `*` と同じように、 `=` 関数も任意の数の引数を取ることができる：

```clojure
(= (+ 2 2) 4 (/ 40 10) (* 2 2) (- 5 1)) ; True!
(= 2 2 2 2 3 2 2 2 2 2) ; False! There's a 3 in there.
```

> [!NOTE]
> 
>**Equality**
>  
>`=` 関数は構造的に等しいという考え方に基づいて作られていることに注意してください：大雑把に言うと、2つの値が同じ値であれば `=`によって等しいことになります。つまり、2つの値が同じ値であれば、 `=`によって等しくなります。内部では、 `=` はJavaの `equals` メソッドと同じです。


二つのものが等しくないかどうかを調べることができる：


```clojure
(not= "Anna Karenina" "Jane Eyre")     ; Yes!
(not= "Anna Karenina" "Anna Karenina") ; No!
```

ご想像の通り、Clojureには `=` 以外にも様々なブール値を返す関数（述語）があります。例えば、`>`と`<`で2つの数字のどちらが大きいかを調べることができます：

```clojure
(if (> a b)
  (println "a is bigger than b"))
(if (< b c)
  (println "b is smaller than c"))
```

もし `>` と `<`式を頭の中で整理するのが難しい場合は、まず `(a > b)` または `(b < c)` のようなinfixバージョンを考え、次に演算子を前に移動させると `(> a b)` と `(< b c)` ができる。また、`<`と`>`に付随するものとして、`<=`と`>=`があり、これらは期待通りの働きをする。

また、様々な "これはあれですか？"関数もある：

```clojure
(number? 1984)             ; Yes!
(number? "Anna Karenina")  ; "Anna Karenina" isn't a number.
(string? "Anna Karenina")  ; Yes, it is a string.
(keyword? "Anna Karenina") ; Not a keyword.
(keyword? :anna-karenina)  ; Yes a keyword.
(map? :anna-karenina)      ; Not a map.
(map? {:title 1984})       ; Yes!
(vector? 1984)             ; Nope.
(vector? [1984])           ; Yes!
```

これらは、値が数値なのか、文字列なのか、キーワードなのか、マップなのか、ベクターなのかを教えてくれる。


Clojureには、より複雑なブール論理を行うための通常の記号も用意されている。例えば `not` 関数があり、`(not true)` は `false` で、`(not false)` は意外にも `true` となる。また、より大きなブール式を組み立てるための `and` や `or` もある：

```clojure
;; Charge extra if it's an express order or oversized
;; and they are not a preferred customer.
(defn shipping-surcharge? [preferred-customer express oversized]
  (and (not preferred-customer) (or express oversized)))
```

ここで重要なのは、`and` と `or` は評価をショートカットすることである：彼らは結果を出すために十分な引数のみを評価するのである。したがって、最後の例では、優先顧客(preferred-customer)を扱っている場合、この関数は注文が特急(express)で発送されるのか、それとも特大サイズ(oversized)なのかさえ考慮しません。


