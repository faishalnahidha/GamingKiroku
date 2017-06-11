package com.izzan.gamingkiroku;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;

import com.activeandroid.query.Select;
import com.izzan.gamingkiroku.model.GameItem;

public class ViewItemActivity extends AppCompatActivity {

    private TextView textViewTitle;
    private TextView textViewGenre;
    private TextView textViewSubGenre;
    private TextView textViewPlatform;
    private TextView textViewFinish;
    private RatingBar ratingBar;

    private ImageButton buttonBack;
    private FloatingActionButton fab;
    private CoordinatorLayout coordinatorLayout;

    private GameItem mGameItem;
    public static final int EDIT_REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_item);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            Long gameItemId = extras.getLong("GAME_ITEM_ID");
            mGameItem = GameItem.load(GameItem.class, gameItemId);
        }

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.view_coordinationLayout);

        textViewTitle = (TextView) findViewById(R.id.view_textViewTitle);
        textViewGenre = (TextView) findViewById(R.id.view_textViewGenre);
        textViewSubGenre = (TextView) findViewById(R.id.view_textViewSubGenre);
        textViewPlatform = (TextView) findViewById(R.id.view_textViewPlatform);
        textViewFinish = (TextView) findViewById(R.id.view_textViewFinish);
        ratingBar = (RatingBar) findViewById(R.id.view_ratingBar);

        buttonBack = (ImageButton) findViewById(R.id.view_buttonBack);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        fab = (FloatingActionButton) findViewById(R.id.view_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewItemActivity.this, EditItemActivity.class);
                intent.putExtra("GAME_ITEM_ID_EDIT", mGameItem.getId());
                startActivityForResult(intent, EDIT_REQUEST_CODE);
            }
        });

        setViewComponent();

    }

    private void setViewComponent() {
        textViewTitle.setText(mGameItem.getTitle());
        textViewGenre.setText(mGameItem.getGenre());

        if(!TextUtils.isEmpty(mGameItem.getSubGenre())){
            textViewSubGenre.setText(mGameItem.getSubGenre());
            textViewSubGenre.setTextColor(ContextCompat.getColor(
                    getApplicationContext(), android.R.color.secondary_text_dark));
            textViewSubGenre.setTypeface(null, Typeface.NORMAL);
        } else {
            textViewSubGenre.setText("no sub-genre");
            textViewSubGenre.setTextColor(ContextCompat.getColor(
                    getApplicationContext(), android.R.color.tertiary_text_dark));
            textViewSubGenre.setTypeface(null, Typeface.ITALIC);
        }

        if(!TextUtils.isEmpty(mGameItem.getPlatform())){
            textViewPlatform.setText(mGameItem.getPlatform());
            textViewPlatform.setTextColor(ContextCompat.getColor(
                    getApplicationContext(), android.R.color.secondary_text_dark));
            textViewPlatform.setTypeface(null, Typeface.NORMAL);
        } else {
            textViewPlatform.setText("no platform");
            textViewPlatform.setTextColor(ContextCompat.getColor(
                    getApplicationContext(), android.R.color.tertiary_text_dark));
            textViewPlatform.setTypeface(null, Typeface.ITALIC);
        }

        if(mGameItem.isFinished()){
            textViewFinish.setText("Finished");
        } else {
            textViewFinish.setText("Unfinished");
        }

        ratingBar.setRating(mGameItem.getRating());
    }

    public void showSnackbar(String message, int duration) {
        Snackbar snackbar = Snackbar.make(
                coordinatorLayout, message, duration);

        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(
                ContextCompat.getColor(getApplicationContext(), R.color.colorAccent2));

        snackbar.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == EDIT_REQUEST_CODE && resultCode == RESULT_OK) {
            String gameTitle = data.getStringExtra("GAME_TITLE_UPDATED");
            showSnackbar(gameTitle + " has been successfully updated.", Snackbar.LENGTH_LONG);

            mGameItem = GameItem.load(GameItem.class, mGameItem.getId());
            setViewComponent();
        }
    }
}
