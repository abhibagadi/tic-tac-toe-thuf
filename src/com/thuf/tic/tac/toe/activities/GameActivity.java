package com.thuf.tic.tac.toe.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.thuf.tic.tac.toe.game.Checker;
import com.thuf.tic.tac.toe.game.GameboardView;
import com.thuf.tictactoe.touchlistener.TicTocToeTouchListener;

public class GameActivity extends Activity implements Runnable {
	private GameboardView gameboard;
	private Thread thread;
	private Checker checker;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		boolean singleplayerMode = getIntent().hasExtra("singleplayer");
		gameboard = new GameboardView(this, singleplayerMode);

		setContentView(gameboard);

		gameboard.setOnTouchListener(new TicTocToeTouchListener());
		checker = gameboard.getChecker();
	}

	@Override
	protected void onPause() {
		super.onPause();
		gameboard.pause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		gameboard.resume();
		thread = new Thread(this);
		thread.start();
	}

	@Override
	public void run() {
		while (true) {
			if (checker == null) {
				continue;
			}
			break;
		}
		while (true) {
			boolean isPlaying = checker.isPlaying();
			if (!isPlaying) {
				Intent intent = new Intent(GameActivity.this, EndOfGameActivity.class);
				intent.putExtra("message", checker.getMessage());
				startActivity(intent);
				GameActivity.this.finish();
				break;
			}
		}
	}

}
