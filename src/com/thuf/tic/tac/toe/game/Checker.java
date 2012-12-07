package com.thuf.tic.tac.toe.game;

import com.thuf.tic.tac.toe.Selection;

public class Checker {
	private Selection[][] gameboard;
	private String message;
	private boolean isPlaying = true;

	public Checker() {
		gameboard = null;
		message = null;
		isPlaying = true;
	}

	public String getMessage() {
		return message;
	}

	public boolean isPlaying() {
		return isPlaying;
	}

	public void checkEndOfGame() {
		gameboard = GameboardView.getGameboard();
		checkNoWinner();
		checkHorizontally();
		checkVertically();
		checkDiagonally();
		if (message != null) {
			isPlaying = false;
		}
	}

	private void checkNoWinner() {
		Selection next = null;
		for (int y = 0; y < 3; y++) {
			for (int x = 0; x < 3; x++) {
				next = gameboard[y][x];
				if (next == null) {
					return;
				}
			}
		}
		message = "No one is winning :)";
	}

	private void checkHorizontally() {
		for (int y = 0; y < 3; y++) {
			Selection s1 = gameboard[y][0];
			Selection s2 = gameboard[y][1];
			Selection s3 = gameboard[y][2];

			if (s1 == null || s2 == null || s3 == null) {
				continue;
			}

			checkSelections(s1, s2, s3);
		}
	}

	private void checkVertically() {
		for (int x = 0; x < 3; x++) {
			Selection s1 = gameboard[0][x];
			Selection s2 = gameboard[1][x];
			Selection s3 = gameboard[2][x];

			if (s1 == null || s2 == null || s3 == null) {
				continue;
			}

			checkSelections(s1, s2, s3);
		}
	}

	private void checkDiagonally() {
		Selection s1 = gameboard[0][0];
		Selection s2 = gameboard[1][1];
		Selection s3 = gameboard[2][2];
		if (s1 != null && s2 != null && s3 != null) {
			checkSelections(s1, s2, s3);
		}

		Selection s4 = gameboard[2][0];
		Selection s5 = gameboard[1][1];
		Selection s6 = gameboard[0][2];
		if (s4 != null && s5 != null && s6 != null) {
			checkSelections(s4, s5, s6);
		}

	}

	private void checkSelections(Selection s1, Selection s2, Selection s3) {
		boolean s1State = s1.isXSelection();
		boolean s2State = s2.isXSelection();
		boolean s3State = s3.isXSelection();

		if (s1State == true && s2State == true && s3State == true) {
			// X is winning
			message = "X is winning";
		}
		if (s1State == false && s2State == false && s3State == false) {
			// O is winning
			message = "O is winning";
		}
	}

}
