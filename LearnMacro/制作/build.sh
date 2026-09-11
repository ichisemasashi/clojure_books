#!/usr/bin/env bash
# LearnMacro 制作物ビルド（工程9–10）
# 依存: pandoc 3.x、PDF 時は xelatex + 日本語フォント（下記）
# 成果物: HTML / EPUB / PDF（完全版・通読版）
set -euo pipefail

ROOT="$(cd "$(dirname "$0")/.." && pwd)"
DIST="$ROOT/dist"
MANUSCRIPT="$ROOT/原稿"
PROD="$ROOT/制作"

# PDF 用フォント（Homebrew cask 推奨）
#   brew install --cask font-noto-sans-cjk-jp font-jetbrains-mono
# LEARNMACRO_BUILD_PDF=0 で PDF をスキップ。未設定または 1 なら生成を試みる。
CJK_FONT="${LEARNMACRO_CJK_FONT:-Noto Sans CJK JP}"
MONO_FONT="${LEARNMACRO_MONO_FONT:-JetBrains Mono}"
BUILD_PDF="${LEARNMACRO_BUILD_PDF:-1}"

mkdir -p "$DIST"

# macOS の AppleDouble を掃除
find "$DIST" -name '._*' -delete 2>/dev/null || true

FRONT="$DIST/_frontmatter.md"
{
  cat "$PROD/表紙.md"
  echo
  echo '---'
  echo
  cat "$PROD/奥付.md"
  echo
  echo '---'
  echo
} > "$FRONT"

cat > "$DIST/style.css" <<'CSS'
:root { color-scheme: light; }
body {
  max-width: 46rem;
  margin: 2rem auto;
  padding: 0 1.25rem 3rem;
  font-family: "Hiragino Sans", "Noto Sans JP", "Yu Gothic", sans-serif;
  line-height: 1.7;
  color: #1a1a1a;
}
h1, h2, h3 { line-height: 1.3; }
code, pre {
  font-family: "SF Mono", "Menlo", "Consolas", monospace;
  font-size: 0.92em;
}
pre {
  padding: 0.9rem 1rem;
  overflow-x: auto;
  background: #f4f4f5;
  border-radius: 6px;
}
table { border-collapse: collapse; width: 100%; margin: 1rem 0; }
th, td { border: 1px solid #ddd; padding: 0.4rem 0.6rem; text-align: left; }
nav#TOC { margin-bottom: 2rem; padding: 1rem; background: #f8f8f8; border-radius: 6px; }
CSS

build_one() {
  local label="$1"
  local src="$2"
  local combined="$DIST/_combined_${label}.md"
  local ebook="$DIST/_ebook_${label}.md"

  cat "$FRONT" "$src" > "$combined"
  python3 "$PROD/rewrite_ebook_links.py" "$combined" "$ebook"

  echo "==> HTML ($label)"
  pandoc "$ebook" \
    --from markdown \
    --to html5 \
    --standalone \
    --metadata-file="$PROD/メタデータ.yaml" \
    --toc --toc-depth=2 \
    --css=style.css \
    -o "$DIST/book-${label}.html"

  echo "==> EPUB ($label)"
  pandoc "$ebook" \
    --from markdown \
    --to epub3 \
    --metadata-file="$PROD/メタデータ.yaml" \
    --toc --toc-depth=2 \
    -o "$DIST/book-${label}.epub"
}

build_pdf() {
  local label="$1"
  local combined="$DIST/_combined_${label}.md"
  local ebook="$DIST/_ebook_${label}.md"
  local src_md="$ebook"
  [[ -f "$src_md" ]] || src_md="$combined"
  echo "==> PDF ($label) [xelatex / $CJK_FONT / $MONO_FONT]"
  pandoc "$src_md" \
    --from markdown \
    --metadata-file="$PROD/メタデータ.yaml" \
    --toc --toc-depth=2 \
    --pdf-engine=xelatex \
    -V CJKmainfont="$CJK_FONT" \
    -V CJKsansfont="$CJK_FONT" \
    -V mainfont="$CJK_FONT" \
    -V sansfont="$CJK_FONT" \
    -V monofont="$MONO_FONT" \
    -V geometry:margin=22mm \
    -V colorlinks=true \
    -o "$DIST/book-${label}.pdf"
  echo "    wrote book-${label}.pdf ($(wc -c < "$DIST/book-${label}.pdf") bytes)"
}

build_one "full" "$MANUSCRIPT/通し原稿_完全版.md"
build_one "thin" "$MANUSCRIPT/通し原稿_通読版.md"

if [[ "$BUILD_PDF" == "1" ]]; then
  if ! command -v xelatex >/dev/null 2>&1; then
    echo "ERROR: xelatex not found (install MacTeX / BasicTeX)." >&2
    exit 1
  fi
  for label in full thin; do
    build_pdf "$label"
  done
else
  echo "==> PDF skipped (LEARNMACRO_BUILD_PDF=0)."
  rm -f "$DIST"/book-*.pdf
fi

rm -f "$DIST/_frontmatter.md" "$DIST/_combined_full.md" "$DIST/_combined_thin.md" \
  "$DIST/_ebook_full.md" "$DIST/_ebook_thin.md"
find "$DIST" -name '._*' -delete 2>/dev/null || true

echo
echo "Artifacts in $DIST:"
ls -la "$DIST"
