
### ネームスペースとプロジェクトのテスト

これまでのところ、テストを 1 つずつ実行してきましたが、これはすぐに古くなります。幸いなことに、`clojure.test` は `run-tests` 関数を提供しており、名前空間内のすべてのテストを簡単に実行できます：

test/inventory/dev/run_tests.clj
```clojure
;; Three ways to run the tests in a namespace.
(test/run-tests)
(test/run-tests *ns*)
(test/run-tests 'inventory.core-test)
```

引数なしで `run-tests` を呼び出すと、現在の名前空間のすべてのテストを実行します。名前空間の値か名前空間名を (シンボルとして) 渡すと、その名前空間内のすべてのテストを実行します。

さらによいことに、Leiningen はコマンドラインからプロジェクト内のすべての名前空間のテストを実行するタスクを提供しています：

```bash
$ lein test

Ran 1 tests containing 1 assertions.
1 failures, 0 errors.
{:test 1, :pass 1, :fail 0, :error 0, :type :summary}
```

さらに、テスト結果の要約も表示される。


