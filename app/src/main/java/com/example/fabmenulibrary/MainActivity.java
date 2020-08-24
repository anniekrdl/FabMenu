package com.example.fabmenulibrary;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class MainActivity extends AppCompatActivity {
    CoordinatorLayout layout;
    int menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        layout= findViewById(R.id.layout);
        menu = R.menu.menu_fab;

        final FabMenuTest fabMenu = new FabMenuTest(this, layout, menu);
        //fabMenu.setIconColor(android.R.color.holo_red_dark);
        //fabMenu.setColor(R.color.colorAccent,R.color.colorPrimary);
        fabMenu.createFabMenu(menu,60);
        fabMenu.setOnMenuItemClickListener(new FabMenuTest.OnMenuItemClickListener() {
            @Override
            public void onMenuItemClick(FloatingActionButton button, int btnId) {
                Log.i("button Id",Integer.toString(btnId));
                Log.i("button Id from resource", Integer.toString(R.id.mood));

            }
        });


    }


}