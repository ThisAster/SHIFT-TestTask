# SHIFT-TestTask

* Java: 21
* Build tool: Maven (Version 3.9.8)
* Other libraries:
  - Lombok (Version 1.18.36)
  - Apache Commons CLI (Version 1.9.0)

## Запуск:

### Собираем jar-файл:
    mvn clean package

### Выбор режима:
    java -jar target/util-1.0-SNAPSHOT-jar-with-dependencies.jar [additional parameters] 

## Доступные режимы:
- s (Short statistics);
- f (Full statistics);
- a (Append mode);
- p (Prefix for output files);
- o (Output directory for result files);

### Пример запуска:
    java -jar target/util-1.0-SNAPSHOT-jar-with-dependencies.jar -f -s -p sample- in1.txt in2.txt



