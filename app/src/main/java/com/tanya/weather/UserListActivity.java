package com.tanya.weather;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;

public class UserListActivity extends AppCompatActivity implements DeleteUserFragment.DialogListener {
    Context context;
    MaterialToolbar materialToolbar;
    TextView pageTitle;
    ImageView backImg, addImg;
    RecyclerView recyclerView;
    LottieAnimationView lottieAnimationView;
    LinearLayoutManager layoutManager;
    ArrayList<UserModal> userModal;
    UserAdapter adapter;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        init();
        loadUsers();
        pageTitle.setText("User List");
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        addImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openUserList = new Intent(context, AddUserActivity.class);
                startActivity(openUserList);
                finish();

            }
        });


    }


    private void loadUsers() {
        userModal = databaseHelper.readAllUsers();

        if (userModal.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            lottieAnimationView.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            lottieAnimationView.setVisibility(View.GONE);
        }

        adapter = new UserAdapter(userModal, context, new UserAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(int pos, UserModal modal, View view) {
                switch (view.getId()) {
                    case R.id.cardUser:

                        Intent intent = new Intent(context, TodayWeatherActivity.class);
                        startActivity(intent);

                        break;
                }

            }
        });
        recyclerView.setAdapter(adapter);
       /* new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                // This method is called when we swipe our item to the left direction.
                // On the below lines, we are getting the item at a particular position.

                DeleteUserFragment dialogFragment = new DeleteUserFragment();
                Bundle args = new Bundle();
                args.putInt("adapterPosition", viewHolder.getAdapterPosition());
                dialogFragment.setArguments(args);
                dialogFragment.show(getSupportFragmentManager(), "dialogFragment");

            }
        }).attachToRecyclerView(recyclerView);*/

    }

    public void init() {
        context = this;
        userModal = new ArrayList<>();
        databaseHelper = new DatabaseHelper(context);
        materialToolbar = (MaterialToolbar) findViewById(R.id.toolbar);
        pageTitle = (TextView) findViewById(R.id.toolbarTitle);
        backImg = (ImageView) findViewById(R.id.back_toolbar);
        addImg = (ImageView) findViewById(R.id.more_toolbar);
        lottieAnimationView = (LottieAnimationView) findViewById(R.id.lottieAnimationView);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
    }

    @Override
    public void onDeleted(Boolean isDeleted, int position) {
        if (isDeleted) {
            // this method is called when item is swiped below line is to remove item from our array list.
            userModal.remove(position);
            // below line is to notify our item is removed from adapter.
            adapter.notifyItemRemoved(position);
            Toast.makeText(context, "User Deleted", Toast.LENGTH_SHORT).show();
        } else {
            recreate();
        }
    }
}