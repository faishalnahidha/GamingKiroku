package com.izzan.gamingkiroku;

import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;

import com.izzan.gamingkiroku.model.GameItem;

public class CreateItemActivity extends AppCompatActivity {

    private EditText editTextTitle;
    private AutoCompleteTextView actvGenre;
    private AutoCompleteTextView actvSubGenre;
    private AutoCompleteTextView actvPlatform;
    private CheckBox checkBoxFinished;
    private RatingBar ratingBar;

    private Button buttonSave;
    private ImageButton buttonCancel;

    private CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_item);

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.createCoordinationLayout);

        editTextTitle = (EditText) findViewById(R.id.editTextTitle);
        actvGenre = (AutoCompleteTextView) findViewById(R.id.actvGenre);
        actvSubGenre = (AutoCompleteTextView) findViewById(R.id.actvSubGenre);
        actvPlatform = (AutoCompleteTextView) findViewById(R.id.actvPlatform);
        checkBoxFinished = (CheckBox) findViewById(R.id.checkBoxFinished);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);

        buttonSave = (Button) findViewById(R.id.buttonSave);
        buttonCancel = (ImageButton) findViewById(R.id.buttonCancel);

        /**
         * set autocomplete item for genre
         */

        String[] genres = getResources().getStringArray(R.array.genre_list);
        ArrayAdapter<String> adapterGenre = new ArrayAdapter<>
                (this, android.R.layout.simple_list_item_1, genres);
        actvGenre.setAdapter(adapterGenre);


        /**
         * set autocomplete item for sub genre
         */
        actvGenre.setOnDismissListener(new AutoCompleteTextView.OnDismissListener() {
            @Override
            public void onDismiss() {

                String[] subGenres;
                String genre = actvGenre.getText().toString().trim();
                Log.d("GENRE", genre);

                if (genre.equalsIgnoreCase("action")) {
                    subGenres = getResources().getStringArray(R.array.action_sub_genre);
                } else if (genre.equalsIgnoreCase("adventure")) {
                    subGenres = getResources().getStringArray(R.array.adventure_sub_genre);
                } else if (genre.equalsIgnoreCase("rpg") || genre.equalsIgnoreCase("role-playing")
                        || genre.equalsIgnoreCase("role-playing / rpg")) {
                    subGenres = getResources().getStringArray(R.array.roleplaying_sub_genre);
                } else if (genre.equalsIgnoreCase("simulation")) {
                    subGenres = getResources().getStringArray(R.array.simulation_sub_genre);
                } else if (genre.equalsIgnoreCase("strategy")) {
                    subGenres = getResources().getStringArray(R.array.strategy_sub_genre);
                } else if (genre.equalsIgnoreCase("online")) {
                    subGenres = getResources().getStringArray(R.array.online_sub_genre);
                } else {
                    subGenres = null;
                }

                if (subGenres != null) {
                    updateSubGenreCompletionList(subGenres);
                }
            }
        });

        /**
         * set autocomplete item for platforms
         */
        String[] platforms = getResources().getStringArray(R.array.platform_list);
        ArrayAdapter<String> adapterPlatform = new ArrayAdapter<>
                (this, android.R.layout.simple_list_item_1, platforms);
        actvPlatform.setAdapter(adapterPlatform);


        /**
         *  listener for (X) button
         */
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        /**
         *  listener for SAVE button
         */
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String titleInput = editTextTitle.getText().toString().trim();

                if (titleInput.isEmpty() || titleInput.length() == 0 || titleInput.equals("")) {
                    //editTextTitle is empty
                    showSnackbar("Please input a game title.", Snackbar.LENGTH_SHORT);
                } else {
                    //editTextTitle is not empty
                    String genreInput = actvGenre.getText().toString().trim();

                    if (genreInput.isEmpty() || genreInput.length() == 0 || genreInput.equals("")) {
                        showSnackbar("Please input a genre.", Snackbar.LENGTH_SHORT);
                    } else {
                        saveGameItem();

                        Intent returnIntent = new Intent();
                        returnIntent.putExtra("NEW_GAME_TITLE", editTextTitle.getText().toString().trim());
                        setResult(RESULT_OK, returnIntent);

                        finish();
                    }
                }

            }

        });
    }

    public void updateSubGenreCompletionList(String[] subGenres) {
        ArrayAdapter<String> adapterSubGenre = new ArrayAdapter<>
                (this, android.R.layout.simple_list_item_1, subGenres);
        actvSubGenre.setAdapter(adapterSubGenre);
    }

    public long saveGameItem() {
        String title = editTextTitle.getText().toString().trim();
        String genre = actvGenre.getText().toString().trim();
        String subGenre = actvSubGenre.getText().toString().trim();
        String platform = actvPlatform.getText().toString().trim();
        boolean finished = checkBoxFinished.isChecked();
        float rating = ratingBar.getRating();

        GameItem game = new GameItem(title, genre, subGenre, platform, finished, rating);
        game.save();

        Log.i("NEW_GAME", "id = " + game.getId().toString()
                + ", title = " + game.getTitle().trim());

        return game.getId();
    }

    public void showSnackbar(String message, int duration) {
        Snackbar snackbar = Snackbar.make(
                coordinatorLayout, message, duration);

        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(
                ContextCompat.getColor(getApplicationContext(), R.color.colorAccent2));

        snackbar.show();
    }

}
