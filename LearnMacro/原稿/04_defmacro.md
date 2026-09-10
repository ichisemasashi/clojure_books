# 第4章 はじめての `defmacro`

**状態:** 粗稿 v0.1  
**サンプル:** `learn-macro.ch04-defmacro`

## 4.1 動機

クォートでフォームを組めるようになったら、次はそれを **呼び出しのたびに差し込む** 仕組みが `defmacro` である。

## 4.2 最小のマクロ: `unless`

```clojure
(defmacro unless
  "test が偽のとき body を評価する。"
  [test & body]
  `(if (not ~test)
     (do ~@body)))
```

ポイント:

- 引数 `test` や `body` は **まだ評価されていないフォーム**  
- 戻り値は、コンパイラが代わりに使う **展開後フォーム**

### 展開前後

```clojure
;; before
(unless false :ok)

;; after macroexpand-1
(if (clojure.core/not false) (do :ok))
```

### 実行

```clojure
(unless true :no)   ; => nil（本体は走らない）
(unless false :yes) ; => :yes
```

## 4.3 関数ではなぜ足りないか

```clojure
(defn unless-fn [test body-fn]
  (when-not test (body-fn)))

(unless-fn false (fn [] :yes))
```

動くが、呼び出し側が常に `(fn [] …)` を強いられる。  
**構文として `unless` と書きたい**ならマクロ（または同等の特殊形式）が要る。

## 4.4 ドキュメント

`defmacro` にも docstring を付ける。利用者と未来の自分が展開の意図を追うときの手がかりになる。

## 4.5 演習

1. **書く:** `when-not-lite`（`when-not` 相当）を実装し、展開と実行を確認せよ。  
2. **判断:** その糖衣は、チームのコードベースで関数 API のままの方がよいか？

骨組み: `exercises/ch04.clj`／解答: `answers/ch04.clj`

## 4.6 まとめ — 関数でよいか？

呼び出し側に遅延（thunk）を強いてもよいなら関数で足りることが多い。  
言語に新しい分岐構文を足したいときだけ `defmacro` を検討する。

**サンプル:** `(learn-macro.ch04-defmacro/demo)`
