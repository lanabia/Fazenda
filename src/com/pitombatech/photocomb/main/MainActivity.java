package com.pitombatech.photocomb.main;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pitombatech.photocomb.R;
import com.pitombatech.photocomb.tools.AnimText;

public class MainActivity extends Activity implements OnClickListener,OnTouchListener{

	String name;
	LinearLayout ll_to_click,ll_letters,teste;
	TextView[] list;
	AnimText anim;

	@SuppressLint("DefaultLocale")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.main_layout);

		// set name
		name = new String("Car".toUpperCase());

		int numberLetters = name.length()-1;

		// set animation
		anim = new AnimText();
		//ll_to_click = new LinearLayout(getApplicationContext());
		//ll_to_click = (LinearLayout) findViewById(R.id.viewToClick);
		//ll_to_click.setOnClickListener(this);
		teste = new LinearLayout(getApplicationContext());
		teste = (LinearLayout) findViewById(R.id.teste);
		teste.setOnTouchListener(this);

		// set list of TextViews with letters of the name
		ll_letters = new LinearLayout(getApplicationContext());
		ll_letters = (LinearLayout) findViewById(R.id.ll_letters);
		list = createTexViewtList(numberLetters,name);

		super.onCreate(savedInstanceState);

	}

	private TextView[] createTexViewtList(int numberLetters, String name){	

		TextView[] list = new TextView[numberLetters];
		String s;
		LinearLayout.LayoutParams layoutParams;
		layoutParams = new LinearLayout.LayoutParams(0,LayoutParams.MATCH_PARENT,1.2f);

		for(int i=0; i<numberLetters; i++){
			list[i] = new TextView(this);
			ll_letters.addView(list[i]);
			s = name.substring(i+1, i+2);
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
		//anim.anima(v, list);

	}

	@Override
	public boolean onTouch(View arg0, MotionEvent arg1) {
		move(teste);
		return false;
	}

	private void move(View v){
		int[] loc = new int[2];
		v.getLocationOnScreen(loc);
		Float x =new Float(loc[0]+10);
		Float y =new Float(loc[1]+10);
		Log.d("Lana","Location on Screen: "+ loc[0] + " " + loc[1]);
		
		v.setX(x);
		v.setY(y);
	}


}
