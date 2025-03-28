
### 名前空間、シンボル、キーワード

シンボルや vars のように、名前空間は普通の Clojure 値で、Clojure プログラマがアクセスできます。例えば、カレントの名前空間を取得することができます: それは常にシンボル `*ns*` にバインドされています。

```clojure
(println "Current ns:" *ns*)
```

は次のように表示されます

```clojure
Current ns: #object[clojure.lang.Namespace 0x76c706bf user]
```

既存の名前空間を名前で検索することもできる：

```clojure
(find-ns 'user) ; Get the namespace called 'user.
```

名前空間が手に入れば、その名前空間で定義されているすべてのものを発見することができるようになる：このように：

```clojure
(ns-map (find-ns 'user)) ; Includes all the predefined vars.
```
 
を使うと、シンボルとvarsの非常に大きなマップを得ることができる：

```clojure
{primitives-classnames #'clojure.core/primitives-classnames,
 +' #'clojure.core/+',
 Enum java.lang.Enum,
 decimal? #'clojure.core/decimal?,
 << and on and on >>
}
```

便利なことに、`ns-map`はシンボルを渡すと名前空間を見つけてくれる：

```clojure
(ns-map 'user)
```

そして、その名前空間が認識しているすべてのマップを得ることができる。

名前空間とシンボルには興味深い関係がある。これまで見てきたように、名前空間名の後にスラッシュをつけ、その後に`pricing/discount-price`というようにシンボルを続けることで、完全修飾されたシンボルを書くことができます。名前空間は実はシンボルの一部で、`namespace`関数で取得できる部分です：

```clojure
;; Gives us "pricing".
(namespace 'pricing/discount-print)
```

シンボルの名前空間部分について覚えておくべきことは、それは単なる名前であって、名前空間の値への参照ではないということです。したがって存在しない名前空間を持つシンボルを作ることができ、仮に `narnia` という名前空間が実際に存在しなくても、`'narnia/caspian` は正しいシンボルである。もちろん、`narnia/caspian` のように引用符を取り除くと、`narnia` 名前空間で `caspian` を探そうとするため、`narnia` 名前空間は存在する方がよい。

キーワードにも名前空間を指定するスペースがあり、名前空間を明示的に呼び出すことで追加することができます：

```clojure
:blottsbooks.pricing/author
```


あるいは、手前のコロンを二重にする：

```clojure
::author
```

コロンを二重 にすると、キーワードはカレント名前空間を使用するようになる。

キーワードの自動検索がないことを考えると、キーワードに名前空間を追加するのは主にキーワードの衝突を防ぐためです。したがって、自分の`:book`が他の人の`:book`と混同されることを心配する場合は、いつでもコロンを追加することができる： ::book`とすればよい。実際には、ほとんどのキーワードは名前空間を使用しない。


