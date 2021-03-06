package com.policestrategies.calm_stop.officer;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
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
import com.policestrategies.calm_stop.officer.dashboard.DashboardActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mariavizcaino
 */
public class RatingActivity extends AppCompatActivity implements View.OnClickListener {


    List<String> comments_array = new ArrayList<>();
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

        mDatabase = FirebaseDatabase.getInstance().getReference();
        ratingDigits = (TextView) findViewById(R.id.star_rating_digits);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        //((BottomNavigationMenuView) bottomNavigationView.getChildAt(0)).getChildAt(1).performClick();
        Log.d(TAG, "comments array:" + comments_array);


        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            final String userId = user.getUid();

            // Get officer rating
            mDatabase.child("officer").child(userId).child("ratings").child("avg_rating").addListenerForSingleValueEvent(
                    new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Float officerStarRatingValue = dataSnapshot.getValue(Float.class);
                            Log.d(TAG, "rating = " + officerStarRatingValue);
                            if (officerStarRatingValue == null) {
                                officerStarRatingValue = (float) 0;
                            }
                            officerStarRating = (RatingBar) findViewById(R.id.officerStarRatingBar);
                            officerStarRating.setRating(officerStarRatingValue);
                            String ratingDigitsString = String.valueOf(officerStarRatingValue);
                            ratingDigits.setText(ratingDigitsString);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    }
            );


            // Get officer comments
            mDatabase.child("officer").child(userId).child("comments").addValueEventListener(
                    new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            // empty comments_array
                            List<String> comments_array = new ArrayList<>();
                            // add comments from Firebase to comments_array
                            for (DataSnapshot commentSnapshot : dataSnapshot.getChildren()) {
                                String comment = commentSnapshot.getValue(String.class);
                                comments_array.add(comment);
                            }
                            // if there are no comments, let officer know
                            if (comments_array.isEmpty()) {
                                comments_array.add("You have not received any comments yet.");
                            }
                            // comments_array used in listView
                            ArrayAdapter adapter = new ArrayAdapter<>(getBaseContext(),
                                    R.layout.listview_driver_comments, comments_array);
                            ListView listView = (ListView) findViewById(R.id.driver_comments);
                            listView.setAdapter(adapter);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    }
            );
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


        ArrayAdapter adapter = new ArrayAdapter<>(this,
                R.layout.listview_driver_comments, comments_array);

        ListView listView = (ListView) findViewById(R.id.driver_comments);
        listView.setAdapter(adapter);

    } // end onCreate

    @Override
    public void onResume() {
        super.onResume();
        updateNavigationMenuSelection(1);
    }

    @Override
    public void onClick(View v) {

        switch(v.getId()) {

        }
    }

    private void home() {
        Intent i = new Intent(getBaseContext(), DashboardActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION | Intent.FLAG_ACTIVITY_NEW_TASK |
                Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }

    private void ratings() {}

    private void history() {
        Intent i = new Intent(getBaseContext(), HistoryActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(i);
    }

    private void account() {
        Intent i = new Intent(getBaseContext(), AccountActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(i);
    }

    private void updateNavigationMenuSelection(int menu) {
        ((BottomNavigationMenuView) bottomNavigationView.getChildAt(0)).getChildAt(menu).performClick();
    }

} // end RatingActivity
