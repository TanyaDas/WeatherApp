package com.tanya.weather;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    ArrayList<UserModal> modals;
    UserAdapter.OnItemClickListener listener;
    private Context mContext;

    public UserAdapter(ArrayList<UserModal> modals, Context mContext, OnItemClickListener listener) {
        this.modals = modals;
        this.mContext = mContext;
        this.listener = listener;
    }

    @NonNull
    @Override
    public UserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new UserAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.ViewHolder holder, int position) {
        final UserModal userModal = modals.get(position);
        holder.setIsRecyclable(false);
        try {
            holder.bind(position, userModal, listener);
            holder.emailTxt.setText(userModal.getEmail());
            holder.fullNameTxt.setText(userModal.getFirstName() + " " + userModal.getLastName());

        } catch (Exception e) {
            e.getMessage();
        }
    }

    @Override
    public int getItemCount() {
        return modals.size();
    }

    public interface OnItemClickListener {
        void onItemClick(int pos, UserModal modal, View view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView fullNameTxt, emailTxt;
        CardView mainCardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            fullNameTxt = (TextView) itemView.findViewById(R.id.textView6);
            emailTxt = (TextView) itemView.findViewById(R.id.textView7);
            mainCardView = (CardView) itemView.findViewById(R.id.cardUser);
        }

        public void bind(final int pos, final UserModal modal, final UserAdapter.OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(pos, modal, v);
                }
            });
            mainCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(pos, modal, v);
                }
            });

        }
    }
}
