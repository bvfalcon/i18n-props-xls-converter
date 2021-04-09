i18n-props-xls-converter
========================

Original documentation: http://www.everit.org/i18n-props-xls-converter

#### Changes:

1. Export command is now
```
java -jar org.everit.i18n.propsxlsconverter-{version}-jar-with-dependencies.jar -f export -xls translations.xls -wd /tmp/ -langs hu,de,us -r .*\.properties
```
2. Import command now works and creates *.property-files in UTF-8 (Java9+) format
