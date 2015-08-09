package gitit.com.rxsuggestions.service;

import java.util.HashMap;

import gitit.com.rxsuggestions.service.foursquare.contract.PhotoResponse;
import gitit.com.rxsuggestions.service.foursquare.contract.VenueExploreResponse;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;
import retrofit.http.QueryMap;
import rx.Observable;

public interface IRetrofitFoursquareAPI {


    @GET("/venues/explore")
    Observable<VenueExploreResponse> exploreNearbyVenues(@QueryMap HashMap<String, String> defaultQueryParams,
                                                         @Query("ll") String latLng,
                                                         @Query("query") String query,
                                                         @Query("limit") int limit,
                                                         @Query("sortByDistance") boolean sortByDistance);

    @GET("/venues/{id}/photos")
    void getVenuePhotos(@QueryMap HashMap<String, String> defaultQueryParams,
                        @Path("id") String venueId,
                        Callback<PhotoResponse> baseResponseCallback);

}