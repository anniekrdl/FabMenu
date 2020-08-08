package com.example.fabmenu;

import android.content.Context;
import android.content.res.ColorStateList;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.core.view.ViewCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class FabMenu {

    private Context context;
    private FrameLayout frameLayout;
    private FloatingActionButton fab;
    private boolean isFabOpen = false;

    //Changeable variables
    int menu;
    int color = android.R.color.holo_orange_light;
    int colorMiniFab = android.R.color.holo_orange_light;

    public FabMenu(Context context, FrameLayout frameLayout, int menu) {
        this.context =context;
        this.frameLayout = frameLayout;
        frameLayout.setClipChildren(false);
        this.menu = menu;
    }

    public void setColor(int color) {
        this.color = color;
        this.colorMiniFab = color;
    }

    public void setColor(int color, int colorMiniFab) {
        this.color = color;
        this.colorMiniFab = colorMiniFab;
    }

    public void setIcon(int icon) {
    }

    public void createFabMenu(int menu, int margin) {
        Float scaleFab = 0.8f;
        Menu menuFAB = getMenu(menu);

        for (int i =0; i<menuFAB.size(); i++) {
            // Make linearlayout
            LinearLayout linearLayout = new LinearLayout(context);
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            FrameLayout.LayoutParams linearParams = new FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.WRAP_CONTENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT);
            linearParams.gravity = Gravity.BOTTOM|Gravity.END;
            linearParams.setMargins(margin,margin,margin,margin);

            linearLayout.setLayoutParams(linearParams);
            linearLayout.setId(menuFAB.getItem(i).getItemId());

            //Create Textview in Linearlayout
            TextView textView = new TextView(context);
            textView.setText(menuFAB.getItem(i).getTitle());
            textView.setId(menuFAB.getItem(i).getItemId());
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.gravity = Gravity.CENTER_VERTICAL;
            textView.setLayoutParams(layoutParams);
            ((LinearLayout) linearLayout).addView(textView);
            linearLayout.setVisibility(View.INVISIBLE);

            //Make FAB
            FloatingActionButton floatingActionButton = new FloatingActionButton(context);
            floatingActionButton.setId(menuFAB.getItem(i).getItemId());
            floatingActionButton.setImageDrawable(menuFAB.getItem(i).getIcon());
            floatingActionButton.setScaleY(scaleFab);
            floatingActionButton.setScaleX(scaleFab);
            LinearLayout.LayoutParams layoutParamsFAB = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            floatingActionButton.setLayoutParams(layoutParamsFAB);
            floatingActionButton.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(colorMiniFab)));

            //Add fab to linearlayout
            ((LinearLayout) linearLayout).addView(floatingActionButton);

            frameLayout.addView(linearLayout);

        }

        FabButton(margin);
    }

    private void FabButton(int margin) {

        // Make linearlayout
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        FrameLayout.LayoutParams linearParams = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT);
        linearParams.gravity = Gravity.BOTTOM|Gravity.END;
        linearParams.setMargins(margin,margin,margin,margin);
        linearLayout.setLayoutParams(linearParams);

        //Create Textview in Linearlayout
        TextView textView = new TextView(context);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER_VERTICAL;
        textView.setLayoutParams(layoutParams);
        ((LinearLayout) linearLayout).addView(textView);

        //Make FAB
        fab = new FloatingActionButton(context);

        fab.setImageResource(R.drawable.ic_baseline_add_24);
        LinearLayout.LayoutParams layoutParamsFAB = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        fab.setLayoutParams(layoutParamsFAB);

        //Add fab to linearlayout
        ((LinearLayout) linearLayout).addView(fab);

        frameLayout.addView(linearLayout);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isFabOpen) {
                    showFABMenu(menu);
                } else {
                    closeFABMenu(menu);
                }
            }
        });
        fab.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(color)));

    }

    private Menu getMenu(int menu) {
        PopupMenu popupMenu = new PopupMenu(context, null);
        Menu menuFab = popupMenu.getMenu();
        popupMenu.getMenuInflater().inflate(menu, menuFab);
        return menuFab;
    }

    private void rotateFabForward() {
        ViewCompat.animate(fab).rotation(135.0f).withLayer().setDuration(300L).setInterpolator(new OvershootInterpolator(10.0F)).start();
    }

    private void rotateFabBackward() {
        ViewCompat.animate(fab).rotation(0.0f).withLayer().setDuration(300L).setInterpolator(new OvershootInterpolator(10.0F)).start();
    }
    private void showFABMenu(int menu){
        Menu menuFAB = getMenu(menu);
        for (int i=0; i<menuFAB.size(); i++) {
            LinearLayout linearLayout = frameLayout.findViewById(menuFAB.getItem(i).getItemId());
            linearLayout.animate().translationY((i+1)*convertDpToPixels(context,-65));
            linearLayout.setVisibility(View.VISIBLE);
        }
        rotateFabForward();
        isFabOpen =true;

    }

    private float convertDpToPixels(Context context, int dp) {
        return (int) (dp * context.getResources().getDisplayMetrics().density);
    }

    private void closeFABMenu(int menu){
        Menu menuFAB = getMenu(menu);
        for (int i=0; i<menuFAB.size(); i++) {
            LinearLayout linearLayout = frameLayout.findViewById(menuFAB.getItem(i).getItemId());
            linearLayout.animate().translationY(0);
            linearLayout.setVisibility(View.INVISIBLE);
        }
        rotateFabBackward();
        isFabOpen =false;

    }
}
