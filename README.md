# Scudoku

A sudoku solver and generator written in Scala, inspired by [https://github.com/jetpants/judoku], although not so impressively optimised!

The solver uses the algorithm outlined in [http://norvig.com/sudoku.html]

## Building and running

The project is built with [sbt 1.2.4]("https://www.scala-sbt.org/download.html")

Run `sbt stage` to package the application and produce an executable script: `target/universal/stage/bin/scudoku` (and `scudoku.bat` for windows)

The script has its own arguments, so argument to be passed to scudoku must follow a `--`. e.g.

`./scudoku -- generate -o generated.txt`
`./scudoku -- solve -i generated.txt`


The application can also be run via sbt, e.g. `sbt run generate`
