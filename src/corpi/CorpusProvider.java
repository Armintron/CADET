package src.corpi;

public class CorpusProvider {

    public static String getCorpus(int selection) {
        switch (selection) {
            case 0:
                return BeeMovie.MOVIE;
            default:
                return "";
        }
    }

}
