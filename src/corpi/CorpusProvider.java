package src.corpi;

public class CorpusProvider {

    public static String getCorpus(int selection) {
        switch (selection) {
            case 0:
                return AllWords.CORPUS;
            case 1:
                return BeeMovie.MOVIE;
            case 2:
                return BeeMovie.MOVIE;
            case 3:
                return BeeMovie.MOVIE;
            case 4:
                return Frankenstein.CORPUS;
            case 5:
                return BeeMovie.MOVIE;
            case 6:
                return BeeMovie.MOVIE;
            default:
                return "";
        }
    }

}
