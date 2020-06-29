package com.emmanuelmess.tictactoe;

import android.os.Bundle;
import android.widget.RelativeLayout;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.emmanuelmess.tictactoe.TicTacToeGame;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.useAccelerometer = false;
		config.useCompass = false;
		RelativeLayout layout = new RelativeLayout(this);
		layout.addView(initializeForView(new TicTacToeGame(), config));
		setContentView(layout);
	}
}
