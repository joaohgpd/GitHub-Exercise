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
import com.example.joao.myapplication.domain.Repository;
import com.example.joao.myapplication.domain.User;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;


public class RepAdapter extends RecyclerView.Adapter<RepAdapter.RepsViewHolder> {
    protected static final String TAG = "github";
    private final List<Repository> reps;
    private final Context context;

    private RepOnClickListener repOnClickListener;

    public RepAdapter(Context context, List<Repository> reps, RepOnClickListener RepOnClickListener) {
        this.context = context;
        this.reps = reps;
        this.repOnClickListener = RepOnClickListener;
    }

    @Override
    public int getItemCount() {
        return this.reps != null ? this.reps.size() : 0;
    }

    @Override
    public RepsViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Infla a view do layout
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_rep_adapter, viewGroup, false);

        CardView cardView = (CardView) view.findViewById(R.id.card_view);

        // Cria o ViewHolder
        RepsViewHolder holder = new RepsViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final RepsViewHolder holder, final int position) {
        // Atualiza a view
        Repository rep = reps.get(position);

        holder.tName.setText(rep.getName());
        holder.tUser.setText(rep.getOwner());
        holder.tDescription.setText("Description: "+rep.getDescription());
        holder.progress.setVisibility(View.VISIBLE);

        Picasso.with(context).load(rep.getAvatarUrl()).fit().into(holder.img, new Callback() {
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
        if (repOnClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    repOnClickListener.onClickRep(holder.itemView, position); // A variável position é final
                }
            });
        }
    }

    public interface RepOnClickListener {
        void onClickRep(View view, int idx);
    }

    // ViewHolder com as views
    public static class RepsViewHolder extends RecyclerView.ViewHolder {
        public TextView tName;
        public TextView tUser;
        public TextView tDescription;
        ImageView img;
        ProgressBar progress;

        public RepsViewHolder(View view) {
            super(view);
            // Cria as views para salvar no ViewHolder
            tUser = (TextView) view.findViewById(R.id.userName);
            tName = (TextView) view.findViewById(R.id.text);
            tDescription = (TextView) view.findViewById(R.id.text2);
            img = (ImageView) view.findViewById(R.id.img);
            progress = (ProgressBar) view.findViewById(R.id.progressImg);
        }
    }
}
