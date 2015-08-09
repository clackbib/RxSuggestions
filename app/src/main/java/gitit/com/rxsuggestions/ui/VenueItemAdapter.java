package gitit.com.rxsuggestions.ui;

import android.app.Activity;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.util.HashMap;
import java.util.List;

import gitit.com.rxsuggestions.R;
import gitit.com.rxsuggestions.service.foursquare.contract.PhotoResponse;
import gitit.com.rxsuggestions.service.IRetrofitFoursquareAPI;
import gitit.com.rxsuggestions.service.foursquare.contract.PhotoItem;
import gitit.com.rxsuggestions.service.foursquare.contract.Venue;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import rx.Observable;

/**
 * 2015
 * Created by habibokanla on 08/08/15.
 */
public class VenueItemAdapter extends RecyclerView.Adapter<VenueViewHolder> {

    private final IRetrofitFoursquareAPI foursquareApi;
    private final StreamBinder binder;
    private HashMap<String, String> params;

    public interface StreamBinder {
        Observable<Venue> bindDeleteStream(Button button);
    }

    private boolean hideRating = false;


    private final DisplayImageOptions options = new DisplayImageOptions.Builder()
            .cacheOnDisk(true)
            .cacheInMemory(true)
            .showImageOnFail(R.drawable.black_image)
            .showImageForEmptyUri(R.drawable.black_image)
            .displayer(new FadeInBitmapDisplayer(200))
            .resetViewBeforeLoading(true)
            .build();
    ;


    private List<Venue> venues;
    private HashMap<Venue, String> venuePhotoUrlHashMap;
    private Activity activity;


    public VenueItemAdapter(List<Venue> venues, Activity activity, IRetrofitFoursquareAPI api, HashMap<String, String> params, StreamBinder binder) {
        this.venuePhotoUrlHashMap = new HashMap<>();
        this.activity = activity;
        this.foursquareApi = api;
        this.params = params;
        this.venues = venues;
        this.binder = binder;
    }


    @Override
    public VenueViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new VenueViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.venue_card_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(final VenueViewHolder holder, int position) {
        final Venue venue = venues.get(position);
        holder.label.setText(venue.name);
        holder.hoursLabel.setText(venue.getTodayHours());
        holder.distanceLabel.setText(venue.formattedDistance());
        holder.ratingsLabel.setText(venue.rating + "");
        holder.ratingsLabel.setVisibility(hideRating ? View.INVISIBLE : View.VISIBLE);
        LayerDrawable layerDrawable = (LayerDrawable) holder.ratingsLabel.getBackground();
        GradientDrawable gradientDrawable = (GradientDrawable) layerDrawable.findDrawableByLayerId(R.id.rating_layer_color);
        gradientDrawable.setColor(activity.getResources().getColor(venue.pickColorResId()));
        // Image loading logic.
        if (venuePhotoUrlHashMap.containsKey(venue)) {
            String uri = venuePhotoUrlHashMap.get(venue);
            ImageLoader.getInstance().displayImage(uri, holder.venueImage, options);
        } else {
            foursquareApi.getVenuePhotos(params, venue.id, new Callback<PhotoResponse>() {
                @Override
                public void success(PhotoResponse photoResponse, Response response) {
                    List<PhotoItem> photoItem = photoResponse.response.photos.items;
                    if (photoItem.isEmpty()) {
                        venuePhotoUrlHashMap.put(venue, "");
                        return;
                    }
                    String uri = photoItem.get(0).buildPhotoUrl(500, 500);
                    venuePhotoUrlHashMap.put(venue, uri);
                    ImageLoader.getInstance().displayImage(uri, holder.venueImage, options);
                }

                @Override
                public void failure(RetrofitError error) {

                }

            });


            binder.bindDeleteStream(holder.moreInfoButton).subscribe(newVenue -> {
                venues.remove(venue);
                notifyItemRemoved(holder.getAdapterPosition());
                venues.add(newVenue);
                notifyItemInserted(venues.size() - 1);
            });
        }
    }


    @Override
    public int getItemCount() {
        return venues.size();
    }
}
