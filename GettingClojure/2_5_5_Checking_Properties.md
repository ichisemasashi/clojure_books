  
### プロパティのチェック 
      
プロパティ・ベースのテスト・パズルの最後のピースは、プロパティを表現することです。

幸いなことに、`test.check` はそのためのすばらしい構文を提供してくれます。簡単な例から始めると、ここでは各正整数が次の正整数より小さいことを述べている：

test/sample_generators.clj
```clojure
(prop/for-all [i gen/pos-int]
  (< i (inc i)))
```
  
しかし、まだ終わってはいない。定理ではなくテストを定義しているのだから、試す整数の数に制限を与える必要がある。ここでは、無限に続く正の整数を `pos-int` ジェネレーターが生成する最初の50個に絞る：

```clojure 
(tc/quick-check 50
  (prop/for-all [i gen/pos-int] 
    (< i (inc i))))
```

まず、`quick-check`関数が、ランダムに生成された50のケースについて、指定したプロパティをチェックします。この`quick-check`関数は、次のような結果をマップとして返す：

```clojure
{:result true, :num-tests 50, :seed 1509151628189}
```

これで（やっと！）インベントリー・コードのテストを書くことができる：

test/inventory/test/inventory/core_gen_test.clj
```clojure
(tc/quick-check 50
  (prop/for-all [i-and-b inventory-and-book-gen]
    (= (i/find-by-title (-> i-and-b :book :title) (:inventory i-and-b))
      (:book i-and-b))))
```

概念的には、「インベントリとブックのすべての組み合わせが生成される場合、インベントリから、指定されたタイトルの本を探すと、そのタイトルの本が生成されるはずです」と言います。

また、別の名前空間である `clojure.test.check.clojure-test` にある `defspec` という形で `clojure.test` とスムーズに統合できます。なので、こうしてください：

```clojure
(ctest/defspec find-by-title-finds-books 50
  (prop/for-all [i-and-b inventory-and-book-gen]
    (= (i/find-by-title (-> i-and-b :book :title) (:inventory i-and-b))
      (:book i-and-b))))
```

最終的に、プロパティテストを実行する `clojure.test` テストが作成されます。

ご想像の通り、`test.check`にはもっとたくさんの機能がありますが、これが基本的なアイデアです: (ジェネレータの形で) データを定義し、プロパティに関するステートメントと組み合わせ、全体をテストに組み込みます。

