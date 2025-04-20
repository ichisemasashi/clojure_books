rm Br.pdf;
cd md;
pandoc *-ja.md -o Br.pdf --pdf-engine=lualatex -V documentclass=bxjsarticle -V classoption=pandoc -V fontsize=12pt --highlight-style=espresso -s -V papersize:a4;
mv Br.pdf ../;
cd ..;
