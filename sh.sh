#!/bin/sh

# Globals
curDir="$PWD"
pkgs=()

echo $OSTYPE

if [ "$OSTYPE" == "linux-gnu" ]; then
    echo "Linux"
    LIB=".:./lib/*"
elif [ "$OSTYPE" == "msys" || "$OSTYPE" == "cygwin" ]; then
    echo "Windows"
    LIB=".;./lib/*"
fi

main() {
    javac -cp $LIB src/Main.java
    java -cp $LIB src/Main
}

gui() {
    javac -cp $LIB src/gui/GUI.java
    java -cp $LIB src/gui/GUI
}

clean() {
    rm src/*.class
    rm src/gui/*.class
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
        echo "Supported modes: main, clean, and gui."
        ;;
esac