package com.pitombatech.photocomb.main;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pitombatech.photocomb.R;
import com.pitombatech.photocomb.tools.AnimText;

public class MainActivity extends Activity implements OnClickListener {

    String name;
    LinearLayout ll_to_click, ll_letters, teste;
    TextView[] list;
    AnimText anim;
    BackImage backImage;
    FrameLayout backLayout;
    float dx = 0, dy = 0, x = 0, y = 0;

    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.main_layout);

        // set name
        name = new String("Car".toUpperCase());

        int numberLetters = name.length() - 1;

        // set animation
        anim = new AnimText();

        // set scaled and moved image on background
        backImage = new BackImage(this);
        backImage.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT));
        backLayout = new FrameLayout(this);
        backLayout = (FrameLayout) findViewById(R.id.frameLayout);
        backLayout.addView(backImage, 0);

        // set list of TextViews with letters of the name
        ll_letters = new LinearLayout(getApplicationContext());
        ll_letters = (LinearLayout) findViewById(R.id.ll_letters);
        list = createTexViewtList(numberLetters, name);

        super.onCreate(savedInstanceState);

    }

    private TextView[] createTexViewtList(int numberLetters, String name) {

        TextView[] list = new TextView[numberLetters];
        String s;
        LinearLayout.LayoutParams layoutParams;
        layoutParams = new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT, 1.2f);

        for (int i = 0; i < numberLetters; i++) {
            list[i] = new TextView(this);
            ll_letters.addView(list[i]);
            s = name.substring(i + 1, i + 2);
            list[i].setText(s);
            list[i].setLayoutParams(layoutParams);
            list[i].setTextColor(Color.WHITE);
            list[i].setGravity(Gravity.CENTER);
            list[i].setTextSize(50);
            //list[i].setVisibility(View.INVISIBLE);
        }

        return list;
    }

    @Override
    public void onClick(View v) {
        // does not working
        anim.anima(v, list);
        Log.d("Lana", "Clicando Anim");
    }

}
