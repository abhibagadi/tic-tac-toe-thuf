package com.thuf.tic.tac.toe.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.thuf.tic.tac.toe.R;

public class MainActivity extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setListeners();
	}

	private void setListeners() {
		findViewById(R.id.button_single_play).setOnClickListener(this);
		findViewById(R.id.button_multy_play).setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		Intent intent = new Intent(MainActivity.this, GameActivity.class);
		switch (view.getId()) {
		case R.id.button_single_play:
			intent.putExtra("singleplayer", true);
			break;
		case R.id.button_multy_play:
			break;
		}
		startActivity(intent);
	}
}
