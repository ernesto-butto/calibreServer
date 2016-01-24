# calibreServer

```
mvn package
screen -L -S calibreServer java -jar  target/calibre-server-0.1.0.jar --output-folder=/home/ubuntu/production/calibreServerFiles
```

### IOS
Para correr el servicio desde calibre
```
java -jar  target/calibre-server-0.1.0.jar --output-folder=/Users/poolebu/Desktop/ebookContentDir --ebook-convert-location=/Applications/calibre.app/Contents/console.app/Contents/MacOS/ 
```

### PDF
Para transformar PDF se ha instalado http://wkhtmltopdf.org/

Usar con este comando
`/usr/local/bin/wkhtmltopdf.sh ENTRADA.html|URL SALIDA.pdf`
