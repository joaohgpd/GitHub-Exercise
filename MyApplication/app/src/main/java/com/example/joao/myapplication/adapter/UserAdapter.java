package com.example.joao.myapplication.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.joao.myapplication.R;
import com.example.joao.myapplication.domain.User;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;


public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UsersViewHolder> {
    protected static final String TAG = "github";
    private final List<User> users;
    private final Context context;

    private UserOnClickListener userOnClickListener;

    public UserAdapter(Context context, List<User> users, UserOnClickListener UserOnClickListener) {
        this.context = context;
        this.users = users;
        this.userOnClickListener = UserOnClickListener;
    }

    @Override
    public int getItemCount() {
        return this.users != null ? this.users.size() : 0;
    }

    @Override
    public UsersViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Infla a view do layout
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_user_adapter, viewGroup, false);

        CardView cardView = (CardView) view.findViewById(R.id.card_view);

        // Cria o ViewHolder
        UsersViewHolder holder = new UsersViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final UsersViewHolder holder, final int position) {
        // Atualiza a view
        User user = users.get(position);

        holder.tNome.setText(user.name);
        holder.tScore.setText("Score: "+user.score);
        holder.progress.setVisibility(View.VISIBLE);

        Picasso.with(context).load(user.getUrlFoto()).fit().into(holder.img, new Callback() {
            @Override
            public void onSuccess() {
                holder.progress.setVisibility(View.GONE);
            }

            @Override
            public void onError() {
                holder.progress.setVisibility(View.GONE);
            }
        });

        // Click
        if (userOnClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    userOnClickListener.onClickUser(holder.itemView, position); // A variável position é final
                }
            });
        }
    }

    public interface UserOnClickListener {
        void onClickUser(View view, int idx);
    }

    // ViewHolder com as views
    public static class UsersViewHolder extends RecyclerView.ViewHolder {
        public TextView tNome;
        public TextView tScore;
        ImageView img;
        ProgressBar progress;

        public UsersViewHolder(View view) {
            super(view);
            // Cria as views para salvar no ViewHolder
            tNome = (TextView) view.findViewById(R.id.text);
            tScore = (TextView) view.findViewById(R.id.text2);
            img = (ImageView) view.findViewById(R.id.img);
            progress = (ProgressBar) view.findViewById(R.id.progressImg);
        }
    }
}
