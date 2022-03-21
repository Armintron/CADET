package src;

import org.apache.commons.codec.language.*;

public class Phonetic {

    // various encoders
    private Caverphone2 caverphone2;
    private DoubleMetaphone doubleMetaphone;
    private MatchRatingApproachEncoder matchRating;
    private Metaphone metaphone;
    private Nysiis nysiis;
    private RefinedSoundex refinedSoundex;
    private Soundex soundex;
    
    private int metaphone_maxCodeLen = 100;

    public enum Encoder
    {
        CaverPhone2,
        DoubleMetaphone,
        MatchRating,
        Metaphone,
        Nysiis,
        RefinedSoundex,
        Soundex
    }

    // initializes various encoders
    public Phonetic()
    {
        caverphone2 = new Caverphone2();

        doubleMetaphone = new DoubleMetaphone();
        doubleMetaphone.setMaxCodeLen(metaphone_maxCodeLen);

        matchRating = new MatchRatingApproachEncoder();

        metaphone = new Metaphone();
        metaphone.setMaxCodeLen(metaphone_maxCodeLen);

        nysiis = new Nysiis();

        refinedSoundex = new RefinedSoundex();

        soundex = new Soundex();
    }

    /**
     * Encode a string using the provided encoder
     * @param word word to encode
     * @param encoder encoder to use
     * @return encoded string
     */
    public String ToPhonetic(String word, Encoder encoder)
    {
        switch(encoder)
        {
            case CaverPhone2:
                return caverphone2.encode(word);
            case DoubleMetaphone:
                return doubleMetaphone.doubleMetaphone(word);
            case MatchRating:
                return matchRating.encode(word);
            case Metaphone:
                return metaphone.encode(word);
            case Nysiis:
                return nysiis.encode(word);
            case RefinedSoundex:
                return refinedSoundex.encode(word);
            case Soundex:
                return soundex.encode(word);
            default:
                return "NO ENCODER CHOSEN";
        }
    }

    /**
     * Maps the string array to its encoded counterpart, using the provided encoder
     * @param words words to encode
     * @param encoder encoder to use
     * @return array of encoded words
     */
    public String[] ToPhonetic(String[] words, Encoder encoder)
    {
        String[] ret = new String[words.length];
        for (int i = 0; i < words.length; i++)
        {
            ret[i] = ToPhonetic(words[i], encoder);
        }
        return ret;
    }
}
