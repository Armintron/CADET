import java.util.concurrent.ConcurrentHashMap;
import info.debatty.java.stringsimilarity.interfaces.*;
import info.debatty.java.stringsimilarity.*;

public class AlgRunner implements Runnable {

    Levenshtein dsa
    ConcurrentHashMap<String, Integer> scoreMap;

    public AlgRunner() {
        this.scoreMap = new ConcurrentHashMap<>();

    }

    @Override
    public void run() {
        // TODO Auto-generated method stub

    }

}
