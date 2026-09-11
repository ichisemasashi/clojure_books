#!/usr/bin/env python3
"""Rewrite relative Markdown links for single-file EPUB/HTML builds (N-08).

- ./NN_....md / 00_....md -> #chNN（章見出しの安定 ID）
- 目次・用語集など通しに含まれない原稿リンク -> 太字ラベルのみ
- ../notes/... などリポジトリ相対 -> コード表記（パスのまま）
"""
from __future__ import annotations

import re
import sys
from pathlib import Path

MD_LINK = re.compile(r"\[([^\]]+)\]\(([^)]+)\)")

APPENDIX = {
    "目次.md",
    "用語集.md",
    "索引.md",
    "図表一覧.md",
    "用語統一表.md",
    "通し原稿_通読版.md",
    "通し原稿_完全版.md",
}


def chapter_id(filename: str) -> str | None:
    m = re.match(r"(00|0[1-9]|1[0-4])_", filename)
    if not m:
        return None
    return f"ch{m.group(1)}"


def rewrite(text: str) -> str:
    def full_repl(m: re.Match[str]) -> str:
        label, url = m.group(1), m.group(2).strip()
        if url.startswith(("http://", "https://", "mailto:", "#")):
            return m.group(0)
        path_part, _, _frag = url.partition("#")
        path_part = path_part.strip()
        name = Path(path_part).name
        cid = chapter_id(name)
        if cid:
            return f"[{label}](#{cid})"
        if name in APPENDIX:
            return f"**{label}**"
        if path_part.startswith("../") or name.endswith(".md"):
            return f"`{path_part or name}`"
        return m.group(0)

    return MD_LINK.sub(full_repl, text)


def main() -> None:
    if len(sys.argv) != 3:
        print("usage: rewrite_ebook_links.py IN.md OUT.md", file=sys.stderr)
        sys.exit(2)
    src, dst = Path(sys.argv[1]), Path(sys.argv[2])
    dst.write_text(rewrite(src.read_text(encoding="utf-8")), encoding="utf-8")


if __name__ == "__main__":
    main()
