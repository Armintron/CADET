# CADET

Concurrent implementation of fuzzy string searching

Implemented using Java SE 17

## CliRunner.sh

Helper script that builds and runs the project from the command line

### Benchmarking
Runs the benchmarking suite from the command line

    . cliRunner.sh main

### Gui
Launches the GUI. Different algorithms and corpuses can be specified

    . cliRunner.sh gui

### Clean
Removes the compiled files from the directory

    . cliRunner.sh clean

## To compile / run manually:

***Replace*** `[OPTION]` ***with either*** `Main` ***or*** `GUI` ***to compile / run the benchmarking suite / GUI, respectively.***

To compile:

Linux:`javac -cp ".:./lib/*" src/[OPTION].java`<br>
Windows:`javac -cp ".;./lib/*" src/[OPTION].java`

To run:

Linux:`java -cp ".:./lib/*" src/[OPTION]`<br>
Windows:`java -cp ".;./lib/*" src/[OPTION]`


