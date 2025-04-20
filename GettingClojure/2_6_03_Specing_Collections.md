
### コレクションのSpecの作成

さまざまなコレクションのSpecを作成することも可能です。最も基本的なものは`coll-of`で、これは何かの一連のコレクションを指定します：

```clojure
;; Something like '("Alice" "In" "Wonderland").
(def coll-of-strings (s/coll-of string?))
;; Or a collection of numbers or strings, perhaps ["Emma" 1815 "Jaws" 1974]
(def coll-of-n-or-s (s/coll-of n-or-s))
```

コレクションの要件をさらに厳格化するには、`cat` を使用できます。`cat` は、コレクション内で要素が特定の順序で続くように指定する機能を提供します。例えば、文字列と数字が交互に並ぶ4要素のコレクションのみを一致させたい場合、次のように指定できます：

```clojure
(def s-n-s-n (s/cat :s1 string? :n1 number? :s2 string? :n2 number?))
(s/valid? s-n-s-n ["Emma" 1815 "Jaws" 1974]) ; Yes!
```

注意：`or` 同様、`cat` には説明的なキーワードが必要です。

マップのspecを記述するには、`keys` 関数を使用できます。例えば、以下の例は、おなじみの書籍マップのspecです：

```clojure
(def book-s 
  (s/keys :req-un [:inventory.core/title
          :inventory.core/author
          :inventory.core/copies]))
```

その例示のspecは、`:title`、`:author`、および`:copies`キーを持つ任意のマップと一致します：
  
```clojure
;; Yes!
(s/valid? book-s {:title "Emma" :author "Austen" :copies 10})
;; No! :author missing.
(s/valid? book-s {:title "Arabian Nights" :copies 17})
;; Yes! Additional entries are OK:
(s/valid? book-s {:title "2001" :author "Clarke" :copies 1 :published 1968})
```

注意：`keys` 関数に少し奇妙な点があります。Spec では名前空間修飾されたキーを指定しています：`:inventory.core/title` ではなく `:title` です。 しかし、名前空間修飾されたキーワードを指定したため、`:req-un` の `-un` 部分では、一致処理時に仕様がマップ内で名前空間修飾されていないキーワードキーを検索するようになります。この名前空間の混乱には理由がありますが、それを理解するためには、まず仕様を登録する方法について説明する必要があります。


