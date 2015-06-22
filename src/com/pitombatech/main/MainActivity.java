package com.pitombatech.main;

import com.pitombatech.main.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {

	int letra = 0;
	boolean insert = true;
	TextView[] l = new TextView[5];

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.main_layout);

		l[0] = (TextView) findViewById(R.id.a1);
		l[1] = (TextView) findViewById(R.id.a2);
		l[2] = (TextView) findViewById(R.id.a3);
		l[3] = (TextView) findViewById(R.id.a4);
		l[4] = (TextView) findViewById(R.id.a5);

		super.onCreate(savedInstanceState);

	}

	public void add(View v){
		if(letra<=4 && insert==true){
			l[letra].setVisibility(TextView.VISIBLE);
			letra = letra+1;
			if(letra==5)
				insert=false;
		
		}
		else{
			letra = letra-1;
			l[letra].setVisibility(TextView.INVISIBLE);
			if(letra==0)
				insert=true;
		}
	}


}
