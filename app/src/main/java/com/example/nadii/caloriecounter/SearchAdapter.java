package com.example.nadii.caloriecounter;

import android.content.Context;
import android.graphics.Color;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;


public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {

    DatabaseReference databaseReference;
    private int selectedPos = RecyclerView.NO_POSITION;
    Context context;
    ArrayList<String> fullNameList;

    class SearchViewHolder extends RecyclerView.ViewHolder {
        TextView full_name;

        public SearchViewHolder(View itemView) {
            super(itemView);
            full_name = (TextView) itemView.findViewById(R.id.full_name);
        }
    }

    public SearchAdapter(Context context, ArrayList<String> fullNameList) {
        this.context = context;
        this.fullNameList = fullNameList;
    }

    @Override
    public SearchAdapter.SearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.search_list_items, parent, false);
        return new SearchAdapter.SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SearchViewHolder holder, int position) {

        holder.full_name.setText(fullNameList.get(position));
        holder.full_name.setBackgroundColor(selectedPos == position ? Color.GREEN : Color.LTGRAY);

        holder.full_name.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(final View v) {

                //creating a popup menu
                PopupMenu popup = new PopupMenu(context, holder.full_name);
                //inflating menu from xml resource
                popup.inflate(R.menu.test);
                //adding click listener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        //final String meal_type;
                        FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
                        String current_uid = current_user.getUid();
                        databaseReference = FirebaseDatabase.getInstance().getReference();
                        databaseReference = databaseReference.child("users").child(current_uid);
                        databaseReference = databaseReference.child("food");
                        HashMap<String , String> userMeal = new HashMap<>();
                        userMeal.put( ((TextView) v).getText().toString() , "IT WORKED!");


                        switch (item.getItemId()) {
                            case R.id.menu_item_1:
                                //handle menu1 click
                                databaseReference.child(item.toString()).setValue(userMeal);

                                return true;
                            case R.id.menu_item_2:
                                //handle menu2 click
                                databaseReference.child(item.toString()).setValue(userMeal);
                                return true;
                            case R.id.menu_item_3:
                                //handle menu3 click
                                databaseReference.child(item.toString()).setValue(userMeal);
                                return true;
                            case R.id.menu_item_4:
                                //handle menu3 click
                                databaseReference.child(item.toString()).setValue(userMeal);
                                return true;
                            default:
                                return false;
                        }


                    }
                });
                //displaying the popup
                popup.show();


                if (holder.getAdapterPosition() == RecyclerView.NO_POSITION) return;
                notifyItemChanged(selectedPos);
                selectedPos = holder.getLayoutPosition();
                notifyItemChanged(selectedPos);
               // Toast.makeText(context, ((TextView) v).getText(), Toast.LENGTH_SHORT).show(); //Here I get the text string

                //adding meal to database


                 //Storing the chosen food




            }
        });
    }

    @Override
    public int getItemCount() {
        return fullNameList.size();
    }
}