package gitit.com.rxsuggestions.service.foursquare.contract;


import gitit.com.rxsuggestions.service.foursquare.base.BaseFSResponse;

/**
 * 2015
 * Created by habibokanla on 31/01/15.
 */
public class PhotoResponse extends BaseFSResponse {

    public PhotoResponseWrapper response;


    public static class PhotoResponseWrapper {
        public FoursquarePhotos photos;
    }
}
