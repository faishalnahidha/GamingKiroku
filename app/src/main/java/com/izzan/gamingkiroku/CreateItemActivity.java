package com.izzan.gamingkiroku;

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
import android.widget.Toast;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_item);

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
                String genre = actvGenre.getText().toString();
                Log.d("GENRE", genre);

                if (genre.equalsIgnoreCase("action")) {
                    subGenres = getResources().getStringArray(R.array.action_sub_genre);
                } else if (genre.equalsIgnoreCase("adventure")) {
                    subGenres = getResources().getStringArray(R.array.adventure_sub_genre);
                } else if (genre.equalsIgnoreCase("rpg") || genre.equalsIgnoreCase("role-playing")
                        || genre.equalsIgnoreCase("role-playng / rpg")) {
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
                    //EditText is empty
                    Toast.makeText(getApplicationContext(),
                            "Please input a game title", Toast.LENGTH_LONG).show();
                } else {
                    //EditText is not empty
                    //saveGameItem();
                    finish();
                }

            }

        });
    }

    public void updateSubGenreCompletionList(String[] subGenres) {
        ArrayAdapter<String> adapterSubGenre = new ArrayAdapter<>
                (this, android.R.layout.simple_list_item_1, subGenres);
        actvSubGenre.setAdapter(adapterSubGenre);
    }

    public void saveGameItem() {
        String title = editTextTitle.getText().toString();
        String genre = actvGenre.getText().toString();
        String subGenre = actvSubGenre.getText().toString();
        String platform = actvPlatform.getText().toString();
        boolean finished = checkBoxFinished.isChecked();
        float rating = ratingBar.getRating();

        GameItem game = new GameItem(1, title, genre, subGenre, platform, finished, rating);
        game.save();
        Toast.makeText(getApplicationContext(),
                "Game is sucessfully saved", Toast.LENGTH_SHORT).show();
    }

}
