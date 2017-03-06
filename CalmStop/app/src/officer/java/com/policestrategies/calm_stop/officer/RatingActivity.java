package com.policestrategies.calm_stop.officer;

import android.content.Intent;
import android.media.Rating;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.policestrategies.calm_stop.R;

import static android.R.attr.rating;
import static android.R.attr.value;
import static com.policestrategies.calm_stop.R.id.Name;
// import static com.policestrategies.calm_stop.R.id.ratingBar;



/**
 * Created by mariavizcaino on 2/27/17.
 */

public class RatingActivity extends AppCompatActivity implements View.OnClickListener {


    String[] comments_array = {"\"Officer was very friendly.\"","\"I felt unfairly treated by the officer.\"","\"Officer gave me unnecessarily long lecture on why tail lights are important.\"","\"The traffic stop was professionally conducted.\"","\"I don't think I should have been pulled over for going 7 over the speed limit.\""};

    RatingBar officerStarRating;

    float officerStarRatingValue;

    private DatabaseReference mDatabase;

    private BottomNavigationView bottomNavigationView;




    private FirebaseUser user;

    private static final String TAG = "RatingActivity";
    private TextView ratingDigits;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_rating);
        ratingDigits = (TextView)findViewById(R.id.star_rating_digits);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);

        bottomNavigationView.bringToFront();

        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Read from the database
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("officer/14566/AIxgvUpkVnW1t0irg1taKZg5ZZl2/profile/rating");

            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    officerStarRatingValue = dataSnapshot.getValue(Float.class);
                    Log.d(TAG, "Rating is: " + officerStarRatingValue);
                    officerStarRating=(RatingBar)findViewById(R.id.officerStarRatingBar);
                    officerStarRating.setRating(officerStarRatingValue);
                    String ratingDigitsString = String.valueOf(officerStarRatingValue);
                    ratingDigits.setText(ratingDigitsString);

                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.w(TAG, "Failed to read value.", error.toException());
                }
            });
        }

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_home:
                                home();
                                break;

                            case R.id.action_ratings:
                                ratings();
                                break;

                            case R.id.action_history:
                                history();
                                break;

                            case R.id.action_account:
                                account();
                                break;

                        }
                        return true;
                    }
                });


        ArrayAdapter adapter = new ArrayAdapter<String>(this,
                R.layout.activity_drivercommentslistview, comments_array);

        ListView listView = (ListView) findViewById(R.id.driver_comments);
        listView.setAdapter(adapter);

    }

    @Override
    public void onClick(View v) {

        switch(v.getId()) {

        }
    }

    private void home() {
        Intent i = new Intent(getBaseContext(), HomepageActivity.class);
        startActivity(i);
        finish();

    }

    private void ratings() {
        Intent i = new Intent(getBaseContext(), RatingActivity.class);
        startActivity(i);

    }
    private void account() {
        Intent i = new Intent(getBaseContext(), AccountActivity.class);
        startActivity(i);

    }
    private void history() {
        Intent i = new Intent(getBaseContext(), HistoryActivity.class);
        startActivity(i);

    }

}
