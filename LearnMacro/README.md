# LearnMacro — Clojure マクロ教科書

**題名:** Clojure マクロを読み、書き、控える  
**副題:** 評価・衛生・DSL までを一冊で身につける教科書  
**版:** 確定版 v1.0（`v1.0.0`）

リポジトリ: https://github.com/ichisemasashi/LearnMacro  
フィードバック: [Issues](https://github.com/ichisemasashi/LearnMacro/issues)

## 配布物

| ファイル | 内容 |
|----------|------|
| [dist/book-full.pdf](./dist/book-full.pdf) | 完全版 PDF |
| [dist/book-thin.pdf](./dist/book-thin.pdf) | 通読版 PDF |
| [dist/book-full.html](./dist/book-full.html) / [epub](./dist/book-full.epub) | 完全版 |
| [dist/book-thin.html](./dist/book-thin.html) / [epub](./dist/book-thin.epub) | 通読版 |
| [CHANGELOG.md](./CHANGELOG.md) | 変更履歴 |
| [正誤表.md](./正誤表.md) | 正誤表 |
| [次版バックログ.md](./次版バックログ.md) | 次版課題 |

再ビルド: `./制作/build.sh`  
Release: https://github.com/ichisemasashi/LearnMacro/releases

## ライセンス

| 対象 | ライセンス |
|------|------------|
| サンプルコード | [MIT](./LICENSE) |
| 本文・企画・ノート | [CC BY-NC-SA 4.0](./LICENSE-TEXT.md) |

## 文書

| 文書 | 役割 |
|------|------|
| [企画提案書.md](./企画提案書.md) | 企画の正本 |
| [工程表.md](./工程表.md) | 工程と進捗 |
| [公開.md](./公開.md) | 工程11（公開記録） |
| [校正.md](./校正.md) | 工程10 |
| [制作.md](./制作.md) | 工程9 |
| [原稿/目次.md](./原稿/目次.md) | 目次 |

## 環境

- Clojure **1.12.x**（`deps.edn` は 1.12.1）
- 推奨 JDK **21**

## サンプルの実行

```bash
clojure -M:run    # 章デモ一括（展開前後）
clojure -M:test   # ケース A/B/C
clojure -M:verify # 調査時スイート
```

詳細は [プロトタイプ.md](./プロトタイプ.md)。本文は [原稿/](./原稿/)。
