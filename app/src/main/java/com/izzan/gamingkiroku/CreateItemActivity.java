package com.izzan.gamingkiroku;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;

public class CreateItemActivity extends AppCompatActivity {

    private EditText editTextTitle;
    private AutoCompleteTextView actvGenre;
    private AutoCompleteTextView actvSubGenre;
    private AutoCompleteTextView actvPlatform;
    private CheckBox checkBoxFinished;
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

        Log.d("GENRE", actvGenre.getText().toString());

        if (actvGenre.getText().toString().equalsIgnoreCase("action")) {
            String[] subGenres = getResources().getStringArray(R.array.action_sub_genre);
            ArrayAdapter<String> adapterSubGenre = new ArrayAdapter<>
                    (this, android.R.layout.simple_list_item_1, subGenres);
            actvSubGenre.setAdapter(adapterSubGenre);
        }

//        } else{
//            subGenres = null;
//        }

//        if(subGenres != null){
//            ArrayAdapter<String> adapterSubGenre = new ArrayAdapter<>
//                    (this, android.R.layout.simple_list_item_1, subGenres);
//            actvSubGenre.setAdapter(adapterSubGenre);
//        }

        /**
         * set autocomplete item for platforms
         */
        String[] platforms = getResources().getStringArray(R.array.platform_list);
        ArrayAdapter<String> adapterPlatform = new ArrayAdapter<>
                (this, android.R.layout.simple_list_item_1, platforms);
        actvPlatform.setAdapter(adapterPlatform);


    }
}
