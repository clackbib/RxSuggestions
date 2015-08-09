package gitit.com.rxsuggestions.ui;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import gitit.com.rxsuggestions.R;


/**
 * 2015
 * Created by habibokanla on 01/06/15.
 */
public class VenueViewHolder extends RecyclerView.ViewHolder {
    public TextView label;
    public ImageView venueImage;
    public TextView hoursLabel;
    public TextView ratingsLabel;
    public TextView distanceLabel;
    public Button moreInfoButton;


    public VenueViewHolder(View itemView) {
        super(itemView);
        label = (TextView) itemView.findViewById(R.id.label_id);
        venueImage = (ImageView) itemView.findViewById(R.id.venue_image);
        hoursLabel = (TextView) itemView.findViewById(R.id.open_hours_label);
        ratingsLabel = (TextView) itemView.findViewById(R.id.ratings_label);
        distanceLabel = (TextView) itemView.findViewById(R.id.distance_label);
        moreInfoButton = (Button) itemView.findViewById(R.id.more_info_button);
    }

    public void clickOnMore() {
        moreInfoButton.performClick();
    }
}
