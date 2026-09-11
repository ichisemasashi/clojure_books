#!/usr/bin/env bash
# LearnMacro 制作物ビルド（工程9）
# 依存: pandoc 3.x
# 主成果物: HTML / EPUB（PDF は CJK フォント設定が必要で任意）
set -euo pipefail

ROOT="$(cd "$(dirname "$0")/.." && pwd)"
DIST="$ROOT/dist"
MANUSCRIPT="$ROOT/原稿"
PROD="$ROOT/制作"

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

  cat "$FRONT" "$src" > "$combined"

  echo "==> HTML ($label)"
  pandoc "$combined" \
    --from markdown \
    --to html5 \
    --standalone \
    --metadata-file="$PROD/メタデータ.yaml" \
    --toc --toc-depth=2 \
    --css=style.css \
    -o "$DIST/book-${label}.html"

  echo "==> EPUB ($label)"
  pandoc "$combined" \
    --from markdown \
    --to epub3 \
    --metadata-file="$PROD/メタデータ.yaml" \
    --toc --toc-depth=2 \
    -o "$DIST/book-${label}.epub"
}

build_one "full" "$MANUSCRIPT/通し原稿_完全版.md"
build_one "thin" "$MANUSCRIPT/通し原稿_通読版.md"

# PDF は任意（日本語フォントが必要）。LEARNMACRO_BUILD_PDF=1 で試行。
if [[ "${LEARNMACRO_BUILD_PDF:-}" == "1" ]]; then
  echo "==> PDF attempt (xelatex + Hiragino Sans)"
  for label in full thin; do
    pandoc "$DIST/_combined_${label}.md" \
      --metadata-file="$PROD/メタデータ.yaml" \
      --toc --toc-depth=2 \
      --pdf-engine=xelatex \
      -V CJKmainfont="Hiragino Sans" \
      -V mainfont="Hiragino Sans" \
      -o "$DIST/book-${label}.pdf" \
      && echo "    wrote book-${label}.pdf" \
      || echo "    PDF failed for $label"
  done
else
  echo "==> PDF skipped (set LEARNMACRO_BUILD_PDF=1 to try)."
  echo "    Primary deliverables are HTML and EPUB."
  rm -f "$DIST"/book-*.pdf
fi

rm -f "$DIST/_frontmatter.md" "$DIST/_combined_full.md" "$DIST/_combined_thin.md"
find "$DIST" -name '._*' -delete 2>/dev/null || true

echo
echo "Artifacts in $DIST:"
ls -la "$DIST"
