package com.example.fabmenulibrary;

import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
    FrameLayout layout;
    int menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        layout= findViewById(R.id.framelayout);
        menu = R.menu.menu_fab;

        FabMenu fabMenu = new FabMenu(this, layout, menu);
        fabMenu.createFabMenu(menu,60);

    }
}