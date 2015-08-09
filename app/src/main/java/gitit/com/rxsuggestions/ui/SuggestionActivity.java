package gitit.com.rxsuggestions.ui;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.widget.Toast;

import com.jakewharton.rxbinding.view.RxView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import gitit.com.rxsuggestions.R;
import gitit.com.rxsuggestions.quickutils.ApiUtils;
import gitit.com.rxsuggestions.service.IRetrofitFoursquareAPI;
import gitit.com.rxsuggestions.service.foursquare.contract.Venue;
import gitit.com.rxsuggestions.service.foursquare.contract.VenueExploreResponse;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

public class SuggestionActivity extends AppCompatActivity {

    /**
     * Just for the purpose of showing how short observable can be, I'm not worried about unclean inits
     **/
    private IRetrofitFoursquareAPI api = ApiUtils.initAPI();

    private SwipeRefreshLayout swipeRefreshLayout;
    private Subject<Boolean, Boolean> swipeRefreshSubject = new SerializedSubject<>(PublishSubject.<Boolean>create());

    private VenueItemAdapter.StreamBinder binder;
    private VenueItemAdapter adapter;
    private RecyclerView recyclerView;
    private Subscription subscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggestion);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_layout);
        recyclerView = (RecyclerView) findViewById(R.id.rv_venues);
        initSwipeDismiss();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        initStreams();

    }

    /**
     * Observable code START
     **/


    private void initStreams() {
        /** Use a subject to listen for swipe/refresh event **/
        swipeRefreshLayout.setOnRefreshListener(() -> swipeRefreshSubject.onNext(true));
        /** Merge swipe refresh events with Observable.just(null) <--- User for triggering an api call the first time the activity is created*/
        Observable<VenueExploreResponse> responseObservable = Observable.merge(Observable.just(null), swipeRefreshSubject)
                .flatMap(anObject -> {
                    /** Convert it to an observable for a list of venues **/
                    String ll = 33.751106 + "," + -84.387982;
                    return api.exploreNearbyVenues(ApiUtils.getQueryParams(false), ll, "popular", 100, true);
                }).doOnError(throwable ->{}
                                //Toast.makeText(SuggestionActivity.this, "Error!", Toast.LENGTH_SHORT).show()
                ).observeOn(AndroidSchedulers.mainThread());

        /** Create a binder that will retrieve the latest value of the api call when a button is clicked, and return a random venue **/
        binder = button1 -> Observable.combineLatest(RxView.clicks(button1), responseObservable, (o, venueExploreResponse) -> {
                    int size = venueExploreResponse.response.groups.get(0).items.size();
                    size--;
                    Random rn = new Random();
                    int idx = rn.nextInt(size + 1);
                    return venueExploreResponse.response.groups.get(0).items.get(idx).venue;
                }
        ).observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable -> Toast.makeText(SuggestionActivity.this, "Error!", Toast.LENGTH_SHORT).show()
                );
        /** Subscribe the adapter to listen for stream updates from the response observable (triggered by inital start),
         * OR, swipe to refresh.
         */
        subscription = responseObservable.subscribe(venueExploreResponse -> {
            if (venueExploreResponse != null) {
                List<VenueExploreResponse.VenueExploreItem> items = venueExploreResponse.response.groups.get(0).items;
                List<Venue> venues = new ArrayList<>();
                int size = items.size();
                size--;
                Random rn = new Random();
                /** Pick 3 random venues and update the adapter **/
                venues.add(items.get(rn.nextInt(size + 1)).venue);
                venues.add(items.get(rn.nextInt(size + 1)).venue);
                venues.add(items.get(rn.nextInt(size + 1)).venue);
                adapter = new VenueItemAdapter(venues, this, api, ApiUtils.getQueryParams(false), binder);
                recyclerView.setAdapter(adapter);
                swipeRefreshLayout.setRefreshing(false);
            }
        });

    }

    /**
     *
     * Observable code FINISH!
     *
     **/


    /**
     * Swipe dismiss handling
     */
    private void initSwipeDismiss() {
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                ((VenueViewHolder) viewHolder).clickOnMore();
            }

        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }
}
