package gitit.com.rxsuggestions.app;

import android.app.Application;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * 2015
 * Created by habibokanla on 08/08/15.
 */
public class RxSuggestionsApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .writeDebugLogs()
                .build();
        ImageLoader.getInstance().init(config);
    }
}
