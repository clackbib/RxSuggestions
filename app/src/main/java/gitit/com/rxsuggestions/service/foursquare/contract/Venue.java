package gitit.com.rxsuggestions.service.foursquare.contract;


import java.text.DecimalFormat;

/**
 * 2015
 * Created by habibokanla on 03/01/15.
 */
public class Venue {

    public String id;
    public int color;
    public String name;
    public VenueLocation location;
    public boolean verified;
    public String url;
    public VenueHours hours;
    public double rating;

    public int pickColorResId() {
//        if (rating > 8.5) {
//            return R.color.ratingHigh;
//        } else if (rating > 6.5) {
//            return R.color.ratingMedHigh;
//        } else if (rating > 5.0) {
//            return R.color.ratingMed;
//        } else if (rating > 3.5) {
//            return R.color.ratingLow;
//        } else {
//            return R.color.ratingBad;
//        }
        return android.R.color.black;

    }

    //
    public String formattedDistance() {
        if (location != null) {
            DecimalFormat decimalFormat = new DecimalFormat("#0.##");
            return decimalFormat.format(0.000621371 * location.distance) + "mi";
        }
        return "";

    }


    //    @Override
//    public String describeYourself() {
//        return name;
//    }
//
//    public String getFormattedAddress() {
//        String output = "No Address Available";
//        if (location != null && location.address != null) {
//            output = location.address + "\n";
//            output = appendIfNotNull(output, location.city);
//            output = appendIfNotNull(output, location.state);
//            output = appendIfNotNull(output, location.postalCode);
//            output = appendIfNotNull(output, location.country);
//        }
//        return output;
//    }
//
//    public String getFormattedFullHours() {
//        String output = "No Hours Available";
//        if (hours != null && hours.timeframes != null && !hours.timeframes.isEmpty()) {
//            StringBuilder builder = new StringBuilder();
//            for (VenueTimeFrame timeFrame : hours.timeframes) {
//                if (timeFrame.days != null && !timeFrame.days.isEmpty()) {
//                    if (timeFrame.open != null && !timeFrame.open.isEmpty()) {
//                        builder.append(timeFrame.days);
//                        for (VenueOpenHour openTime : timeFrame.open) {
//                            builder.append(" ");
//                            if (openTime.renderedTime != null && !openTime.renderedTime.isEmpty()) {
//                                builder.append(openTime.renderedTime);
//                            } else {
//                                builder.append("No Hours Available.");
//                            }
//                        }
//                        builder.append("\n");
//                    }
//                }
//            }
//            return builder.toString();
//        }
//        return output;
//    }
//
    public String getTodayHours() {
        if (hours != null && hours.status != null && !hours.status.isEmpty()) {
            return hours.status;
        } else {
            return "Contact them!";
        }
    }
//
//    private String appendIfNotNull(String original, String appendix) {
//        if (appendix != null && !appendix.isEmpty()) {
//            return original + appendix + " ";
//        }
//        return original;
//
//    }
}
