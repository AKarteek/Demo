package com.example.orm;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.viewHolder> {
    private List<UserItemDB> userItemDBList;
    private Context mContext;

    public UserAdapter(List<UserItemDB> userItemDBList, Context mContext) {
        this.userItemDBList = userItemDBList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler, parent, false);
        return new viewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final viewHolder holder, int position) {
        final UserItemDB user = userItemDBList.get(position);
        holder.name.setText(user.getName());
        holder.age.setText(user.getAge());
        holder.index.setText(user.getIndex());
        holder.editLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mContext instanceof ListActivity) {
                    ((ListActivity) mContext).presentAlert(user.getName(), user.getAge(), user.getIndex());
                    notifyDataSetChanged();
                }
            }
        });
        holder.deleteLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presentAlertDelete(holder.getAdapterPosition(), user.getIndex());
            }
        });

    }

    public void updateUserList(List<UserItemDB> newlist) {
        userItemDBList.clear();
        userItemDBList.addAll(newlist);
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return userItemDBList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        public TextView name, age, index;
        public LinearLayout editLayout, deleteLayout;

        public viewHolder(@NonNull View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            age = (TextView) view.findViewById(R.id.age);
            index = (TextView) view.findViewById(R.id.index);
            editLayout = (LinearLayout) view.findViewById(R.id.edit_layout);
            deleteLayout = (LinearLayout) view.findViewById(R.id.delete_layout);
        }
    }

    public void presentAlertDelete(final int position, final String index) {

        AlertDialog dialog = new AlertDialog.Builder(mContext)
                .setTitle("Delete User")
                .setMessage("Are you sure to delete this user?")
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DatabaseManager.getInstance(mContext).deleteUser(index);
                        deleteUser(position);
                    }
                })
                .setNegativeButton("No", null)
                .create();
        dialog.show();

    }

    private void deleteUser(int position) {
        userItemDBList.remove(position);
        notifyItemRemoved(position);
        notifyItemChanged(position, userItemDBList.size());

    }
}
