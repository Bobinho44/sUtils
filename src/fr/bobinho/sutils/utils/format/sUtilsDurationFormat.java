package fr.bobinho.sutils.utils.format;

public class sUtilsDurationFormat {

    /**
     * Gets a duration string in seconds format
     *
     * @param durationInSecond the duration in second to format
     * @return the formatted string in seconds format
     */
    public static String getAsSecondFormat(long durationInSecond) {
        return durationInSecond + "s ";
    }

}
