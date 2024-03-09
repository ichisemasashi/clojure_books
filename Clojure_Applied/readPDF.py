# ライブラリ設定
import fitz
 
# PDFを読み込む
filename = ''
doc = fitz.open(filename)
 
# １ページずつテキストを抽出して出力
for page in range(len(doc)):
    text = doc[page].get_text()
    print(text)

