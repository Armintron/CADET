package src;

import org.apache.commons.codec.language.*;

public class Phonetic {

    // various encoders
    private static Caverphone2 caverphone2;
    private static DoubleMetaphone doubleMetaphone;
    private static MatchRatingApproachEncoder matchRating;
    private static Metaphone metaphone;
    private static Nysiis nysiis;
    private static RefinedSoundex refinedSoundex;
    private static Soundex soundex;
    
    private int metaphone_maxCodeLen = 100;

    public enum Encoder {
        CaverPhone2,
        DoubleMetaphone,
        MatchRating,
        Metaphone,
        Nysiis,
        RefinedSoundex,
        Soundex
    }
    
    public Encoder encoder;

    // initializes various encoders
    public Phonetic(Encoder encoder)
    {
        this.encoder = encoder;
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
     * Encode a string using this object's encoder. See {@link #ToPhonetic(String, Encoder) ToPhonetic}
     */
    public String ToPhonetic(String word)
    {
        return ToPhonetic(word, this.encoder);
    }

    /**
     * Map a string array to its encoded counterpart. See {@link #ToPhonetic(String[], Encoder) ToPhonetic}
     */
    public String[] ToPhonetic(String[] words)
    {
        return ToPhonetic(words, encoder);
    }

    /**
     * Encode a string using the provided encoder
     * @param word word to encode
     * @param encoder encoder to use
     * @return encoded string
     */
    public static String ToPhonetic(String word, Encoder encoder)
    {
        String ret = "";
        switch (encoder) {
            case CaverPhone2:
                ret = caverphone2.encode(word);
                break;
            case DoubleMetaphone:
                ret = doubleMetaphone.doubleMetaphone(word);
                break;
            case MatchRating:
                ret = matchRating.encode(word);
                break;
            case Metaphone:
                ret = metaphone.encode(word);
                break;
            case Nysiis:
                ret = nysiis.encode(word);
                break;
            case RefinedSoundex:
                ret = refinedSoundex.encode(word);
                break;
            case Soundex:
                ret = soundex.encode(word);
                break;
            default:
                ret =  "NO ENCODER CHOSEN";
        }
        if (ret == null || ret.isEmpty())
            return "";
        return ret;
    }

    /**
     * Maps the string array to its encoded counterpart, using the provided encoder
     * @param words words to encode
     * @param encoder encoder to use
     * @return array of encoded words
     */
    public static String[] ToPhonetic(String[] words, Encoder encoder)
    {
        String[] ret = new String[words.length];
        for (int i = 0; i < words.length; i++)
        {
            ret[i] = ToPhonetic(words[i], encoder);
        }
        return ret;
    }
}