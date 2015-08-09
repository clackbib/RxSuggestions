package gitit.com.rxsuggestions.quickutils;

import java.util.HashMap;

import gitit.com.rxsuggestions.service.IRetrofitFoursquareAPI;
import gitit.com.rxsuggestions.service.foursquare.config.FoursquareApiConfig;
import retrofit.RestAdapter;

/**
 * 2015
 * Created by habibokanla on 08/08/15.
 */
public class ApiUtils {

    private static final FoursquareApiConfig apiConfig = new FoursquareApiConfig();

    public static IRetrofitFoursquareAPI initAPI() {
        /** You'll have to provide your own config **/
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(apiConfig.getApiEndpoint())
                .setLogLevel(RestAdapter.LogLevel.BASIC)
                .build();
        return restAdapter.create(IRetrofitFoursquareAPI.class);
    }

    public static HashMap<String, String> getQueryParams(boolean needAuth) {
        HashMap<String, String> queryParams = new HashMap<>();
        if (needAuth) {
            queryParams.put("oauth_token", "");
        } else {
            queryParams.put("client_id", apiConfig.getClientID());
            queryParams.put("client_secret", apiConfig.getClientSecret());
        }
        queryParams.put("v", apiConfig.getVersionCode());
        return queryParams;
    }
}
