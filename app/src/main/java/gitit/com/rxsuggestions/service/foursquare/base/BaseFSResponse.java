package gitit.com.rxsuggestions.service.foursquare.base;

/**
 * 2015
 * Created by habibokanla on 03/01/15.
 */
public class BaseFSResponse {

    public FourSquareMetaData meta;


    static class FourSquareMetaData {
        public int code;
        public int errorType;
        public String errorDetail;
    }
}
