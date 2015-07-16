package com.pitombatech.photocomb.tools;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class AnimText{

	int letra = 0;
	boolean insert = true;
	
	public AnimText(){
			
	}
	
	public void anima(View v,TextView[] l){
		int numberLetters = l.length;
		
		if(letra<= (numberLetters-1) && insert==true){
			Log.d("Lana", " + number: "+ numberLetters+ " TextView: " + l[letra].getText());
			l[letra].setVisibility(TextView.VISIBLE);
			letra = letra+1;
			if(letra==numberLetters)
				insert=false;
		
		}
		else{
			letra = letra-1;
			Log.d("Lana", " - number: "+ numberLetters+ "TextView: " + l[letra]);
			l[letra].setVisibility(TextView.INVISIBLE);
			if(letra==0)
				insert=true;
		}
	}

}
