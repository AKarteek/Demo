package com.transvision.demo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder> {

    //this is private global varaible
    private List<User> userList;



    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView FirstName, LastName, Gender,Language,Skill;

        public MyViewHolder(@NonNull View itemView) {
            super (itemView);
            FirstName = itemView.findViewById (R.id.FirstName);
            LastName = itemView.findViewById (R.id.LastName);
            Gender = itemView.findViewById (R.id.Gender);
            Language = itemView.findViewById (R.id.Langauge);
            Skill=itemView.findViewById (R.id.Skill);
        }
    }
    //this is taking list of items in List like firstname ,lastname etc.
    public UserAdapter(List<User> userList) {
        this.userList = userList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                 View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycle_row, parent, false);

        return new MyViewHolder(itemView);
    }


    //this will display the data at specified position.
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        User user = userList.get(position);
        holder.FirstName.setText(user.getFirstName());
        holder.LastName.setText(user.getLastName());
        holder.Gender.setText(user.getGender());
        holder.Language.setText(user.getLanguage());
        holder.Skill.setText(user.getSkill());
    }


    //this is getting the data from database
    @Override
    public int getItemCount() {
        return userList.size();
    }

}
