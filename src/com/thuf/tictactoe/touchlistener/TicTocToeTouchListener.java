package com.thuf.tictactoe.touchlistener;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

import com.thuf.tic.tac.toe.Position;
import com.thuf.tic.tac.toe.Selection;
import com.thuf.tic.tac.toe.game.GameboardView;

public class TicTocToeTouchListener implements OnTouchListener {

	@Override
	public boolean onTouch(View view, MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			int x = (int) event.getX();
			int y = (int) event.getY();
			Selection selection = new Selection(new Position(x, y));
			GameboardView.addSelection(selection);
			break;
		}
		return true;
	}

}
