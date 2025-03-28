
### レコードの利点

では、レコードが単なるマップにいくつかのフィールドを追加しただけのものだとしたら、わざわざレコードを作る価値があるのだろうか？いつものように、答えは場合による。レコードの具体的な利点のひとつは、ハードワイヤードされたフィールドにアクセスするのが、マップの同等のフィールドにアクセスするよりも速いということである。したがって、次のようなレコードがあったとする：

```clojure
(def irene {:name "Irene Adler"
            :appears-in "A Scandal in Bohemia"
            :author "Doyle"})
```
  
その方が速いだろう：
    
```clojure
(:name watson)
```

これよりも：

```clojure
(:name irene)
```

どのくらい速くなるのか？小説を数冊、あるいは数万冊扱うだけなら、確かに問題になるほどではない。しかし、本当に大量のデータを扱うのであれば、レコードのパフォーマンス上の利点は考慮すべき点である。

レコードを使うもう一つの理由は、この章の冒頭で触れた、コードをより明快にするのに役立つということだ。例えば、このコードを見てほしい：

```clojure
;; Define the record types.
(defrecord FictionalCharacter[name appears-in author])
(defrecord SuperComputer [cpu no-cpus storage-gb])
;; And create some records. 
(def watson-1 (->FictionalCharacter
                "John Watson"
                "Sign of the Four"
                "Doyle"))
(def watson-2 (->SuperComputer "Power7" 2880 4000))
```

これを見れば、`watson-1` が架空の探偵の助手であり、`watson-2` が不気味なほど知的なクイズ番組出演マシンであることは間違いない。そして、もしまだ自分がどんなものを手に入れたのかわからない場合は、いつでも `class` 関数を使うことができ、これはレコードの型を返してくれる。

```clojure
(class watson-1) ; user.FictionalCharacter
(class watson-2) ; user.SuperComputer
```

また、ある値が特定の型を持っているかどうかを調べるには `instance?`


```clojure
(instance? FictionalCharacter watson-1) ; True.
(instance? SuperComputer watson-2)      ; Nope.
```

なお、`class` や `instance?` は、REPLを使いこなすのに最適なツールではあるが、実際のコードで使うのは避けるべきであるということを覚えておいてほしい。

```clojure
;; Don't do this!
(defn process-thing [x]
  (if (= (instance? FictionalCharacter x))
    (process-fictional-character x)
    (process-computer x)))
```

それはスパゲッティ・コードと悲しみにあなたを導くに違いない。幸いなことに、Clojureには、この種の型に敏感なコードを扱うより良い方法があります：プロトコルです。

> [!NOTE]
>
> **Class?**
>
> `class` 関数はレコードだけでなく、すべての値に対して機能する。雨の日の良いプログラミング・プロジェクトは、`class`に値を入力して何が出てくるか見てみることだ。



