# 付録 A — ClojureScript 補注 {#app-cljs}

**版:** N-10（確定版への追記）  
**位置づけ:** 本書本文は **JVM Clojure** を前提とする。CLJS でマクロを触るときの差分だけを短く置く。完全な CLJS ガイドではない。

---

## A.1 まず押さえること

1. **マクロはコンパイル時だけ**  
   ブラウザ上の実行時に `defmacro` が増えるわけではない。展開は cljs コンパイラ（または self-host のコンパイル段階）で起きる。
2. **マクロ定義の置き場**  
   よくある形は `.clj` / `.cljc` にマクロを置き、`.cljs` から `(:require-macros …)` または `.cljc` のリーダー条件で参照する。
3. **`.cljc` とリーダー条件**  
   `#?(:clj … :cljs …)` でホスト差を分ける。マクロ本体が両ホストで意味を持つか、clj 側だけの展開か、を意識する。

---

## A.2 JVM Clojure との差（実務で刺さる点）

| 点 | JVM Clojure（本書） | ClojureScript |
|----|---------------------|---------------|
| 評価の場 | 同じプロセスで REPL 展開しやすい | コンパイル単位・モジュール境界が強い |
| 名前空間 | `ns` とマクロが同居しやすい | マクロ専用 ns / `require-macros` が必要になりやすい |
| 相互運用 | Java クラス | JS オブジェクト・goog / npm |
| `macroexpand` | REPL で即試せる | ツールやコンパイルログ、cljs REPL の手順が環境依存 |
| 自己ホスト | 通常は不要 | self-hosted cljs では「誰が展開するか」がさらに分岐 |

本書の衛生・二重評価・展開を見る型は **そのまま使える**。変わるのは主に **モジュールとコンパイルの手続き** である。

---

## A.3 やってよい／控える（CLJS 文脈）

- **よい:** 薄い糖衣、評価制御、`.cljc` で共有する小さなマクロ  
- **控える:** JS ランタイムの都合を隠す巨大 DSL、マクロ感染で cljs 側の解析・最適化を壊すこと  
- **データ優先:** Hiccup 的 UI やクエリがデータで足りるなら、CLJS でもマクロにしない（第11・14章と同じ判断）

---

## A.4 最小スケッチ（イメージ）

```clojure
;; macros.cljc（概念例）
(ns learn-macro.cljs-sketch.macros)

(defmacro unless [test & body]
  `(if (not ~test) (do ~@body)))

;; usage.cljs（概念例）
(ns learn-macro.cljs-sketch.usage
  (:require-macros [learn-macro.cljs-sketch.macros :refer [unless]]))
```

実際のプロジェクトではビルドツール（shadow-cljs / figwheel 等）のマクロ解決ルールに従う。

---

## A.5 この付録の止め方

- analyzer やコンパイラ内部、高度な self-host は扱わない（企画の「書かないこと」）  
- 詳細は公式の ClojureScript ドキュメントと、利用中のビルドツールのマクロ節へ  

本文の演習・ケーススタディは JVM 上の `deps.edn` プロジェクトで続けるのが確実である。
