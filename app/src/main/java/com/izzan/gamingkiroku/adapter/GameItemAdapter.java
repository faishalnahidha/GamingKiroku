package com.izzan.gamingkiroku.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.izzan.gamingkiroku.R;
import com.izzan.gamingkiroku.model.GameItem;

import java.util.List;

/**
 * Created by Aizen on 10 Jun 2017.
 */

public class GameItemAdapter extends RecyclerView.Adapter<GameItemAdapter.MyViewHolder> {

    private List<GameItem> gameItemList;
    private Context context;
    private int position;

    public class MyViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener, View.OnCreateContextMenuListener {

        public TextView gameTitle, genre, platform;
        public ImageView finished;

        public MyViewHolder(View itemView) {
            super(itemView);
            gameTitle = (TextView) itemView.findViewById(R.id.textViewGameTitle);
            genre = (TextView) itemView.findViewById(R.id.textViewGenre);
            platform = (TextView) itemView.findViewById(R.id.textViewPlatform);
            finished = (ImageView) itemView.findViewById(R.id.iconFinished);

            itemView.setOnCreateContextMenuListener(this);
            //itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

            menu.setHeaderTitle(gameTitle.getText().toString());
            menu.add(Menu.NONE, 1, 1, "View/Edit");
            menu.add(Menu.NONE, 2, 2, "Delete");
        }

    }

    public GameItemAdapter(List<GameItem> gameItemList, Context context) {
        this.gameItemList = gameItemList;
        this.context = context;
    }

    public GameItem getItem(int position) {
        return gameItemList.get(position);
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.game_item_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        GameItem game = gameItemList.get(position);
        holder.gameTitle.setText(game.getTitle());

        if (TextUtils.isEmpty(game.getSubGenre().trim())) {
            holder.genre.setText(game.getGenre());
        } else {
            holder.genre.setText(game.getGenre() + " | " + game.getSubGenre());
        }

        if (TextUtils.isEmpty(game.getPlatform().trim())) {
            holder.platform.setText("n/a");
        } else {
            holder.platform.setText(game.getPlatform());
        }

        if (game.isFinished()) {
            holder.finished.setVisibility(View.VISIBLE);
        } else {
            holder.finished.setVisibility(View.INVISIBLE);
        }

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                setPosition(holder.getLayoutPosition());
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return gameItemList.size();
    }


}
