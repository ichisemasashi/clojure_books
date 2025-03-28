
### レコードはマップである

レコードのインスタンスをどのように作成しても、いったんレコードが手元にあれば、キーワードのキーを持つマップと同じように扱うことができる：

```clojure
(:name elizabeth)    ; => "Elizabeth Bennet"
(:appears-in watson) ; => "Sign of the Four"
```

レコードとマップの類似性は、見た目(あるいはキーワード？) 、マップを扱う関数はレコードも扱うことができる：

The resemblance between records and maps is much more than skin- (or keyword?) deep: Any function that works with a map will also work with a record:

```clojure
(count elizabeth) ; => 3
(keys watson)     ; => (:name :appears-in :author)
```

また`assoc`を使ってレコードの値を変更することもできる：

```clojure
(def specific-watson (assoc watson :appears-in "Sign of the Four"))
```

また、レコードのインスタンスに、レコードにないタイプの新しいキーを`assoc`することもできる：

```clojure
(def more-about-watson (assoc watson :address "221B Baker Street"))
```

すると、3つの定義済みフィールドと新しい `:address` エントリを持つ新しい `FictionalCharacter` インスタンスが返されます。レコードに `assoc` した余分な値は、まさに余分な値であることに注意してください。それらは他のマップフィールドと同じように扱われますが、レコードの型には何の影響も与えませんし、組み込みフィールドのような魔法のようなスピードアップもありません。

