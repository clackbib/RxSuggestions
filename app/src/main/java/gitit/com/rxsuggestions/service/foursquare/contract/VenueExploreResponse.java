package gitit.com.rxsuggestions.service.foursquare.contract;


import java.util.List;

import gitit.com.rxsuggestions.service.foursquare.base.BaseFSResponse;

/**
 * 2015
 * Created by habibokanla on 22/01/15.
 */
public class VenueExploreResponse extends BaseFSResponse {


    public VenueExploreResponseWrapper response;

    public static class VenueExploreResponseWrapper {
        public List<VenueExploreGroup> groups;
    }

    public static class VenueExploreGroup {
        public String name;
        public String type;
        public List<VenueExploreItem> items;
    }

    public static class VenueExploreItem {
        public Venue venue;
    }

    public boolean isEmpty() {
        if (response == null) {
            return true;
        } else if (response.groups == null) {
            return true;
        } else {
            for (VenueExploreGroup group : response.groups) {
                if (!group.items.isEmpty()) {
                    return false;
                }
            }
            return true;
        }
    }
}
