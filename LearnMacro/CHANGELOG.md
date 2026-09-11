# CHANGELOG

本書『Clojure マクロを読み、書き、控える』の公開版の変更履歴。

形式は [Keep a Changelog](https://keepachangelog.com/ja/1.1.0/) に近い。版番号はセマンティック（文書・配布物向け）。

## [1.0.1] — 2026-09-11

### Added

- JDK 21 再検証ログ（`notes/JDK21_実行ログ.txt`）
- GitHub Actions CI（JDK 21 で test / run / verify）
- 演習・解答の拡充（ch02 / 03 / 05 / 07 / 08 / 13 / 14）
- 第7章に `cond` / `->` の展開観察とソース検索方針
- 選択ケース・anaphoric の次版メモ（`notes/選択ケースとanaphoricメモ.md`）

## [1.0.0] — 2026-09-11

### Added

- 確定版本文（通読版・完全版）と HTML / EPUB / PDF 配布物
- サンプルコード（MIT）と章別デモ・ケース A/B/C テスト
- 正誤表・次版バックログ・公開記録
- 本文ライセンス: CC BY-NC-SA 4.0（[`LICENSE-TEXT.md`](./LICENSE-TEXT.md)）

### Changed

- 原稿を RC v1.0 から **確定版 v1.0** へ
- PDF ビルドを既定化（Noto Sans CJK JP + JetBrains Mono / xelatex）

### Known limitations

- 検証実測は Java 26 の場合あり（推奨は JDK 21）
- 一部章の演習解答未整備（次版バックログ参照）
- pandoc 簡易組版であり商業入稿品質ではない

## [0.9.0] — 2026-09-11

### Added

- 制作物（HTML / EPUB）、MIT ライセンス、タグ `LearnMacro-v0.9.0`（工程9）

[1.0.1]: https://github.com/ichisemasashi/LearnMacro/compare/v1.0.0...HEAD
[1.0.0]: https://github.com/ichisemasashi/LearnMacro/releases/tag/v1.0.0
[0.9.0]: https://github.com/ichisemasashi/clojure_books/releases/tag/LearnMacro-v0.9.0
