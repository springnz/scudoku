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

To run the app, execute one of the generated scripts from the command-line, e.g.

`target/universal/stage/bin/scudoku`.

As you will see, you need to specify some options to do anything useful.

`target/universal/stage/bin/scudoku -h` shows the help text for the wrapper script, which includes various options, mainly related to the JVM.

To pass options to scudoku, you ned to add a `--` before the options. e.g.

`target/universal/stage/bin/scudoku -- -h` shows the scudoku help  
`target/universal/stage/bin/scudoku -- generate` generates a puzzle and writes it to std out  
`target/universal/stage/bin/scudoku -- generate -o generated.txt` generates a puzzle and writes it to the specified file  
`target/universal/stage/bin/scudoku -- solve -i generated.txt` solves a puzzle found in the specified file

The application can also be run via sbt, e.g.

`sbt "run -h"`  
`sbt "run generate"`  
`sbt "run generate -o generated.txt"`  
`sbt "run solve -i generated.txt"`
