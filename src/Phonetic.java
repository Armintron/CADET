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
