# 第8章 制御構文を自分で書く — `unless` 再訪

**状態:** 粗稿 v0.1（通読版は `unless` のみ）  
**サンプル:** `learn-macro.ch08-unless`（実装は `ch04-defmacro`）

## 8.1 動機

第4章で作った `unless` を、**設計手順つきで見直し**、doc と展開の読みやすさを確認する。  
通読版ではここに留め、独自 `cond` や `cond->` は完全版へ送る。

## 8.2 設計手順（再掲）

1. 動機を一文で書く（評価制御か？）  
2. 関数＋thunk で足りるか試す  
3. **先に展開形を書く**  
4. `defmacro` でテンプレート化する  
5. `macroexpand-1` と実行で検証する  

## 8.3 再確認

```clojure
(unless (seq [1]) :hidden) ; => nil
(unless (seq []) :shown)   ; => :shown
```

展開:

```clojure
(if (clojure.core/not (seq [])) (do :shown))
```

docstring が付いていることも確認する（`-> #'unless meta :doc`）。

## 8.4 やりすぎの兆候

- 似た分岐マクロがチーム内に増殖している  
- 展開結果が読めない  
- テストが実行時挙動だけで、展開を見ていない  

そのときは第14章のチェックリストに戻る。

## 8.5 演習

第4章の `when-not-lite` を、本章の設計手順でレビューせよ（展開・doc・命名）。

## 8.6 まとめ — 関数でよいか？

`unless` は教育用にはよいが、実務では `when-not` で足りることがほとんどである。  
**既存の core に無い評価制御が本当に必要か**を、追加前に疑え。

**サンプル:** `(learn-macro.ch08-unless/demo)`

**TODO（完全版）:** 8.3 以降の条件マクロ、`doseq` 読解、例外糖衣。
