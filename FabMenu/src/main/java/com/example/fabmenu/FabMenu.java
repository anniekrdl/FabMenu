package com.example.fabmenu;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.ViewCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class FabMenu {

    private Context context;
    private FrameLayout frameLayout;
    private FloatingActionButton fab;
    private boolean isFabOpen = false;
    private OnMenuItemClickListener onMenuItemClickListener;

    //Changeable variables
    int menu;
    int color = android.R.color.holo_orange_light;
    int colorMiniFab = android.R.color.holo_orange_light;
    int iconColor = android.R.color.white;
    int menuId;

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

    public void setIconColor(int iconColor) {
        this.iconColor = iconColor;

    }



    public void createFabMenu(final int menu, int margin) {
        Float scaleFab = 0.8f;
        final Menu menuFAB = getMenu(menu);
        int marginCardView = (int) convertDpToPixels(context, 9);
        int marginCardViewVertical = (int) convertDpToPixels(context,6);
        int radius = (int) convertDpToPixels(context,3);

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

            //Create Textview
            TextView textView = new TextView(context);
            textView.setText(menuFAB.getItem(i).getTitle());
            textView.setId(menuFAB.getItem(i).getItemId());
            textView.setTextSize(16);
            CardView.LayoutParams layoutParams = new CardView.LayoutParams(CardView.LayoutParams.WRAP_CONTENT, CardView.LayoutParams.WRAP_CONTENT);
            layoutParams.gravity = Gravity.CENTER_VERTICAL;
            layoutParams.setMargins(marginCardView, marginCardViewVertical, marginCardView, marginCardViewVertical);
            textView.setLayoutParams(layoutParams);


            //Create a Cardview with Textview
            CardView cardView = new CardView(context);
            cardView.setId(menuFAB.getItem(i).getItemId());
            LinearLayout.LayoutParams layoutParamsCard = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParamsCard.gravity = Gravity.CENTER_VERTICAL;
            cardView.setRadius(radius);
            cardView.setLayoutParams(layoutParamsCard);
            ((CardView) cardView).addView(textView);
            ((LinearLayout) linearLayout).addView(cardView);

            linearLayout.setVisibility(View.INVISIBLE);

            //Make FAB
            FloatingActionButton floatingActionButton = new FloatingActionButton(context);
            floatingActionButton.setId(menuFAB.getItem(i).getItemId());
            try {
                DrawableCompat.setTint(menuFAB.getItem(i).getIcon(),context.getResources().getColor(iconColor));
                floatingActionButton.setImageDrawable(menuFAB.getItem(i).getIcon());
            } catch (Exception e) {
                e.printStackTrace();
            }

            floatingActionButton.setScaleY(scaleFab);
            floatingActionButton.setScaleX(scaleFab);
            LinearLayout.LayoutParams layoutParamsFAB = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParamsFAB.setMarginStart(margin/2);
            floatingActionButton.setLayoutParams(layoutParamsFAB);
            floatingActionButton.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(colorMiniFab)));

            floatingActionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onMenuItemClickListener != null) {
                        onMenuItemClickListener.onMenuItemClick((FloatingActionButton) v,v.getId());
                    }
                }
            });

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

        Drawable addDrawable = context.getResources().getDrawable(R.drawable.ic_baseline_add_24);
        PorterDuff.Mode mode = PorterDuff.Mode.SRC_ATOP;
        addDrawable.setColorFilter(context.getResources().getColor(iconColor),mode);
        fab.setImageDrawable(addDrawable);
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

    public void setOnMenuItemClickListener(OnMenuItemClickListener listener) {
        this.onMenuItemClickListener = listener;
    }

    public interface OnMenuItemClickListener {
        void onMenuItemClick(FloatingActionButton button, int btnId);
    }

}
