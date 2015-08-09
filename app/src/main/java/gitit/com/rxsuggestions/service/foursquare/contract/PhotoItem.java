package gitit.com.rxsuggestions.service.foursquare.contract;

/**
 * 2015
 * Created by habibokanla on 31/01/15.
 */
public class PhotoItem {
    public String id;
    public Integer createdAt;
    public String prefix;
    public String suffix;
    public Integer width;
    public Integer height;
    public String visibility;


    public String buildPhotoUrl(int width, int height) {
        return (prefix + width + "x" + height + suffix);
    }
}
