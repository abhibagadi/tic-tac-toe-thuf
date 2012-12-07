package com.thuf.tic.tac.toe;

import android.graphics.Bitmap;

public class Selection {
	public static int totalSelects = 0;
	private Bitmap selectionBitmap;
	private Position position;
	private boolean xSelection;

	public Selection(Position position) {
		totalSelects++;
		this.position = position;
		if (totalSelects % 2 == 0) {
			xSelection = true;
		} else {
			xSelection = false;
		}
	}

	public void setSelectionBitmap(Bitmap selectionBitmap) {
		this.selectionBitmap = selectionBitmap;
	}

	public Bitmap getSelectionBitmap() {
		return selectionBitmap;
	}

	public Position getPosition() {
		return position;
	}

	public boolean isXSelection() {
		return xSelection;
	}

	public static void invalidateSelection() {
		totalSelects--;
	}

}
