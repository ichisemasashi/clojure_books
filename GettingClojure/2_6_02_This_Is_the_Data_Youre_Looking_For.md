
### これがあなたが探しているデータです

`clojure.spec`の動作を把握するために、ブックストアの例に戻り、おなじみのbookマップ（次のような値）を処理するコードを書くことを想像しましょう：

```clojure
{:title "Getting Clojure" :author "Olsen" :copies 1000000}
```

さらに、書籍データを信頼性の低いソースから取得していると仮定し、最初に確認すべきことは、この値が「書籍である」と主張する値が実際に書籍の形式を持った値であるかどうかを検証することだとします。明らかに、次のような関数を記述できます：

spec/inventory/src/inventory/core.clj
```clojure
(defn book? [x]
  (and
    (map? x)
    (string? (:author x))
    (string? (:title x))
    (pos-int? (:copies x))))
``` 

この方法は機能しますが、データ検証を「手動でコードを書く」アプローチではスケーラビリティに欠けます。大規模なシステムでは、複雑なデータ構造が数多く存在し、それぞれに対してこのような関数を書き続けることはすぐに煩雑になります。

このようなデータ検証を構築するために、［`clojure.spec`］（https://clojure.org/about/spec）が存在します。 最も基本的なレベルで、`clojure.spec`はClojureデータ用の正規表現のような機能です。正規表現でパターン（例えばAに続いて任意の数のB）を表現し、それが特定の文字列と一致するかしないかを判定するように、`clojure.spec`ではパターン（例えば数値のみからなるコレクション）を表現し、それが特定のClojureデータと一致するかしないかを判定できます。

動作を確認するには、`clojure.spec.alpha` 名前空間をロードする必要があります：


```clojure
(ns inventory.core
(:require [clojure.spec.alpha :as s]))
```

spec が提供する主要な機能は `valid?` です：

```clojure
(s/valid? number? 44)     ; Returns true.
(s/valid? number? :hello) ; Returns false.
```

ご覧の通り、`valid?` は述語関数と値を受け取り、その値が関数が定義するテストに合格するかどうかを返します。これがそれほど印象的でないように思えるなら、その通りです。しかし、これはまだ始まりに過ぎません。

> [!NOTE]
>
> **Clojure Dot Spec Dot Alpha?**
>
> この文章を書いている時点では、`clojure.spec` は最終段階の作業中です。これが名前空間名に `alpha` が付いている理由です。この文章を読んでいる時期によっては、その `alpha` が削除されている可能性があります。また、`clojure.spec`はClojureとよく統合されていますが、別々のライブラリとして提供されています。したがって、Leiningenを使用している場合は、プロジェクトファイルに追加の依存関係エントリが必要です。

例えば、`clojure.spec/and` を使用すると、複数の述語を組み合わせて、値が数値でありかつ10より大きいことをテストする式を作成できます：

```clojure
(def n-gt-10 (s/and number? #(> % 10)))

(s/valid? n-gt-10 1)  ; Nope.
(s/valid? n-gt-10 10) ; Still nope.
(s/valid? n-gt-10 11) ; True.
```

幸いなことに、`and` は2つの述語に限定されません：

```clojure
(def n-gt-10-lt-100
  (s/and number? #(> % 10) #(< % 100)))
```

注意すべき点の一つは、用語がやや混乱しやすい点です。一般的には、パターンマッチングの値を「spec」と呼びます。しかし、`clojure.spec` ライブラリ自体を「spec」と呼ぶ場合もあります。明確さを保つため、ここではライブラリを `clojure.spec`、値を「spec」と呼ぶことにします。

`clojure.spec` には `and` だけでなく `or` も提供されており、これにより「これかあれか」を一致させる spec を作成できます。例えば、数値か文字列のどちらかに一致する spec を作成したい場合、次のように書くことができます：

```clojure
(def n-or-s (s/or :a-number number? :a-string string?))

(s/valid? n-or-s "Hello!") ; Yes!
(s/valid? n-or-s 99)       ; Yes!
(s/valid? n-or-s 'foo)     ; No, it's a symbol.
```

`or` の使用におけるわずかな違いに注意してください。この演算子は引数をペアで要求し、キーワードに続いて述語を指定する必要があります。キーワードは、仕様が一致しない場合に一貫したフィードバックを生成するために必要です。

より重要な点は、`and` と `or` の両方が、仕様だけでなく単純な述語関数も引数として受け付けることです。これにより、任意に複雑な仕様を構築することができ、例えば「10より大きい数、または任意のシンボル」を受け付ける仕様を定義できます。

```clojure
(def n-gt-10-or-s (s/or :greater-10 n-gt-10 :a-symbol symbol?))
```
