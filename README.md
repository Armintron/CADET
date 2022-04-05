# CADET

Concurrent implementation of fuzzy-searching

Implemented in Java

To compile:

Linux:`javac -cp ".:./lib/*" src/Main.java`<br>
Windows:`javac -cp ".;./lib/*" src/Main.java`

To run:

Linux:`java -cp ".:./lib/*" src/Main`<br>
Windows:`java -cp ".;./lib/*" src/Main`

## CliRunner.sh

Helper script that builds and runs the project from the command line

### Main
Runs the project from the command line, asking for input and using the default settings

    . cliRunner.sh main

### Gui
Runs the project inside of the gui. Different algorithms and corpuses can be specified

    . cliRunner.sh gui

### Clean
Removes the compiled files from the directory

    . cliRunner.sh clean
