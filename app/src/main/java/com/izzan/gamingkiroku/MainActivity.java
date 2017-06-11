package com.izzan.gamingkiroku;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.izzan.gamingkiroku.adapter.GameItemAdapter;
import com.izzan.gamingkiroku.model.GameItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<GameItem> gameItemList = new ArrayList<>();

    private RecyclerView recyclerView;
    private GameItemAdapter mAdapter;

    public static final int CREATE_REQUEST_CODE = 100;
    public static final int VIEW_REQUEST_CODE = 200;
    CoordinatorLayout coordinatorLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id
                .mainCoordinationLayout);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CreateItemActivity.class);
                startActivityForResult(intent, CREATE_REQUEST_CODE);

/*                Snackbar snackbar = Snackbar
                        .make(coordinatorLayout, "Welcome to AndroidHive", Snackbar.LENGTH_LONG);
                View snackbarView = snackbar.getView();
                TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                textView.setTextColor(ContextCompat.getColor(
                        getApplicationContext(), android.R.color.primary_text_light));
                snackbarView.setBackgroundColor(
                        ContextCompat.getColor(getApplicationContext(), R.color.colorAccent2));

                snackbar.show();*/
            }
        });

        mAdapter = new GameItemAdapter(gameItemList, getApplicationContext());


        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewGameItem);
        recyclerView.setHasFixedSize(false);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext()));
        recyclerView.setAdapter(mAdapter);

        registerForContextMenu(recyclerView);

        reloadData();
    }

    private void reloadData() {
        List<GameItem> ls = GameItem.getAll();
        gameItemList.clear();
        gameItemList.addAll(ls);
        mAdapter.notifyDataSetChanged();
    }

    private void deleteGameItem(GameItem gameItem) {
        GameItem.delete(GameItem.class, gameItem.getId());
        reloadData();
    }

    public void showSnackbar(String message, int duration) {
        Snackbar snackbar = Snackbar.make(
                coordinatorLayout, message, duration);

        View snackbarView = snackbar.getView();
/*        TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(ContextCompat.getColor(
                getApplicationContext(), android.R.color.primary_text_light));*/
        snackbarView.setBackgroundColor(
                ContextCompat.getColor(getApplicationContext(), R.color.colorAccent2));

        snackbar.show();
    }

    private void showAboutDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        //set title dialog
        alertDialogBuilder.setTitle("About");

        //set pesan dari dialog
        alertDialogBuilder
                .setMessage("Gaming Kiroku v1.0.0" +
                        "\nCopyright \u00a9 2017 by Faishal Nahidha"
                        + "\n\nPlease visit : " +
                        "\n\t faishalnahidha.890.com" +
                        "\n\t izzan.carbonmade.com " +
                        "\n\t www.persona.my.id\n")
                .setIcon(R.mipmap.ic_launcher)
                .setCancelable(false)
                .setPositiveButton("THANK YOU", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id){
                        dialog.dismiss();
                    }
                });

        //membuat alert dialog dari builder
        AlertDialog alertDialog = alertDialogBuilder.create();

        //menampilkan alert dialog
        alertDialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_about) {
            showAboutDialog();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int position = -1;
        try {
            position = mAdapter.getPosition();

        } catch (Exception e) {
//            Log.d(TAG, e.getLocalizedMessage(), e);
            return super.onContextItemSelected(item);
        }

        if (position != -1) {
            GameItem mGameItem = mAdapter.getItem(position);
            switch (item.getItemId()) {
                case 1:
                    Intent intent = new Intent(MainActivity.this, ViewItemActivity.class);
                    intent.putExtra("GAME_ITEM_ID", mGameItem.getId());
                    startActivityForResult(intent, VIEW_REQUEST_CODE);
                    break;
                case 2:
                    deleteGameItem(mGameItem);
                    showSnackbar(mGameItem.getTitle() + " has been deleted.", Snackbar.LENGTH_LONG);
                    break;
            }
        }

        return super.onContextItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CREATE_REQUEST_CODE && resultCode == RESULT_OK) {
            String newGameTitle = data.getStringExtra("NEW_GAME_TITLE");
            showSnackbar(newGameTitle + " has been successfully saved.", Snackbar.LENGTH_LONG);

            reloadData();
        }

        if (requestCode == VIEW_REQUEST_CODE) {
            reloadData();
        }
    }
}
