rm GC.pdf;
pandoc *.md -o GC.pdf --pdf-engine=lualatex -V documentclass=bxjsarticle -V classoption=pandoc -V fontsize=12pt --highlight-style=espresso
