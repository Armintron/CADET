#!/bin/sh

if [ "$OSTYPE" == "linux-gnu" ]; then
    LIB=".:./lib/*"
elif [ "$OSTYPE" == "msys" ]; then
    LIB=".;./lib/*"
elif [ "$OSTYPE" == "cygwin" ]; then
    LIB=".;./lib/*"
fi

clean() {
    rm src/*.class
    rm src/gui/*.class
}
main() {
    javac -cp $LIB src/Main.java
    java -cp $LIB src/Main
}
gui() {
    javac -cp $LIB src/gui/GUI.java
    java -cp $LIB src/gui/GUI
}

case "$1" in
    "main"|"Main")
        main
        ;;

    "gui"|"Gui"|"GUI")
        gui
        ;;

    "clean"|"Clean")
        clean
        ;;
    *)
        echo "Usage: . sh.sh <mode>"
        echo "Supported modes: main, gui, and clean."
        ;;
esac