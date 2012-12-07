package com.thuf.tic.tac.toe.activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.thuf.tic.tac.toe.R;

public class EndOfGameActivity extends Activity implements Runnable {
	private Thread thread;
	private TextView tvDisplay;

	@Override
	protected void onPause() {
		super.onPause();
		if (thread != null) {
			try {
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			thread = null;
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		thread = new Thread(this);
		thread.start();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_end_of_game);
		
		String message = getIntent().getExtras().getString("message");
		tvDisplay = (TextView) findViewById(R.id.tv_end_of_game);
		tvDisplay.setText(message);
	}

	@Override
	public void run() {
		while (true) {
			if (tvDisplay != null) {
				break;
			}
		}

		try {
			Thread.sleep(2500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		finish();
	}
}
