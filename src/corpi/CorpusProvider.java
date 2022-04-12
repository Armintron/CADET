package src.corpi;

public class CorpusProvider {

    public static String getCorpus(int selection) {
        switch (selection) {
            case 0:
                return AllWords.getCorpus();
            case 1:
                return BeeMovie.MOVIE;
            case 2:
                return Dracula.getCorpus();
            case 3:
                return EdgarAllanPoe.getCorpus();
            case 4:
                return Frankenstein.getCorpus();
            case 5:
                return Mobydick.getCorpus();
            case 6:
                return WinneThePooh.getCorpus();
            default:
                return "";
        }
    }

}
