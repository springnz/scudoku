# Scudoku

A sudoku solver and generator written in Scala, inspired by [https://github.com/jetpants/judoku](https://github.com/jetpants/judoku), although not so impressively optimised!

The solver uses the algorithm outlined in [http://norvig.com/sudoku.html](http://norvig.com/sudoku.html)

## Requirements

The project is built with sbt 1.2.4, which you'll need to install.  See [https://www.scala-sbt.org/1.x/docs/Setup.html](https://www.scala-sbt.org/1.x/docs/Setup.html).

You'll also need to have Java 8 installed.

## Building

From the command-line, in the root directory of the project, run `sbt stage` to package the application and produce executable scripts for Linux/OSX and Windows.

These are output to `<project_root>/target/universal/stage/bin/`.

# Running

The script has its own arguments, so arguments to be passed to scudoku must follow a `--`. e.g.

`target/universal/stage/bin/scudoku -- generate -o generated.txt`
`target/universal/stage/bin/scudoku -- solve -i generated.txt`


The application can also be run via sbt, e.g. `sbt "run generate"`
