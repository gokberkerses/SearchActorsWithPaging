package com.example.searchactorswithpaging.ui;

import android.arch.paging.PagedListAdapter;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.searchactorswithpaging.R;
import com.example.searchactorswithpaging.model.Actor;

public class ActorItemAdapter extends PagedListAdapter<Actor, ActorItemAdapter.ActorViewHolder> {

    private static final String TAG = "ActorItemAdapter";
    private Context mContext;

    public ActorItemAdapter(Context context) {
        super(DIFF_CALLBACK);
        this.mContext = context;
    }

    @NonNull
    @Override
    public ActorViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        Log.d(TAG, "onCreateViewHolder: ");
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.actor_item, viewGroup, false );
        return new ActorViewHolder(view) ;
    }

    @Override
    public void onBindViewHolder(@NonNull ActorViewHolder viewHolder, int position) {
        Actor actor = getItem(position); //get item?

        if (actor!= null) {
            String BASE_URL = "https://image.tmdb.org/t/p/w500"; // play with size, for performance

            Glide.with(mContext)
                    .asBitmap()
                    .load(BASE_URL + actor.getProfilePath())
                    .into(viewHolder.actorImage) ;

            //name
            viewHolder.actorName.setText(actor.getName()) ;

            //popularity
            viewHolder.actorPopularity.setText(actor.getPopularity());
        }
        else {
            Toast.makeText(mContext, "Item is null", Toast.LENGTH_LONG).show();
        }
    }

    private static DiffUtil.ItemCallback<Actor> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Actor>() {
                @Override
                public boolean areItemsTheSame(Actor oldActor, Actor newActor) {
                    return oldActor.getName() == newActor.getName() ;
                }

                @Override
                public boolean areContentsTheSame(Actor oldActor, Actor newActor) {
                    return oldActor.equals(newActor) ;
                }
            };

    class ActorViewHolder extends RecyclerView.ViewHolder {
        private static final String TAG = "ActorViewHolder";
        RelativeLayout actorLayout;

        ImageView actorImage;
        TextView actorName;
        TextView actorPopularity;

        public ActorViewHolder(@NonNull View itemView) {
            super(itemView);

            this.actorLayout = itemView.findViewById(R.id.actor_layout);

            this.actorImage = itemView.findViewById(R.id.actor_image);
            this.actorName = itemView.findViewById(R.id.actor_name);
            this.actorPopularity = itemView.findViewById(R.id.actor_popularity);

            Log.d(TAG, "ActorViewHolder: ");
        }

    }

}