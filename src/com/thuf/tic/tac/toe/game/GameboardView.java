package com.thuf.tic.tac.toe.game;

import java.util.Random;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import com.thuf.tic.tac.toe.Position;
import com.thuf.tic.tac.toe.R;
import com.thuf.tic.tac.toe.Selection;

public class GameboardView extends SurfaceView implements Runnable {
	private static Context context;
	private static boolean SINGLEPLAYER_MODE;
	private Thread thread;
	private boolean isVisible;
	private SurfaceHolder holder;
	private Paint background;
	private Paint line;
	private static Selection[][] gameboard;

	private static Bitmap xMark;
	private static Bitmap oMark;
	private static Checker checker;

	public static Selection[][] getGameboard() {
		return gameboard;
	}

	public Checker getChecker() {
		return checker;
	}

	public GameboardView(Context context, boolean singleplayer) {
		super(context);
		GameboardView.context = context;
		GameboardView.SINGLEPLAYER_MODE = singleplayer;
		checker = new Checker();
		thread = new Thread(this);
		isVisible = true;
		holder = getHolder();

		background = new Paint(Paint.ANTI_ALIAS_FLAG);
		line = new Paint(Paint.ANTI_ALIAS_FLAG);

		background.setARGB(255, 214, 255, 248);
		line.setARGB(255, 0, 100, 80);
		line.setStyle(Style.FILL_AND_STROKE);

		gameboard = new Selection[3][3];
		xMark = BitmapFactory.decodeResource(getResources(), R.drawable.x_select);
		oMark = BitmapFactory.decodeResource(getResources(), R.drawable.o_select);
	}

	public GameboardView(Context context) {
		this(context, false);
	}

	public void pause() {
		isVisible = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		thread = null;
	}

	public void resume() {
		isVisible = true;
		thread = new Thread(this);
		thread.start();
	}

	@Override
	public void run() {
		while (isVisible) {
			if (!holder.getSurface().isValid()) {
				continue;
			}
			Canvas canvas = holder.lockCanvas();
			onDraw(canvas);
			holder.unlockCanvasAndPost(canvas);
		}
	}

	private static int x;
	private static int y;

	@Override
	protected void onDraw(Canvas canvas) {
		drawBoard(canvas);
		drawSelects(canvas);
	}

	private void drawBoard(Canvas canvas) {
		canvas.drawPaint(background);
		int width = canvas.getWidth();
		int height = canvas.getHeight();
		x = width / 3;
		y = height / 3;

		for (int i = 0; i < 4; i++) {
			canvas.drawLine(0, y * i, width, y * i, line);
			canvas.drawLine(x * i, 0, x * i, height, line);
		}
	}

	private void drawSelects(Canvas canvas) {
		int canvasX = canvas.getWidth() / 3;
		int canvasY = canvas.getHeight() / 3;
		Rect dst = new Rect();
		Bitmap mark;
		for (int x = 0; x < 3; x++) {
			for (int y = 0; y < 3; y++) {
				Selection next = gameboard[y][x];
				if (next == null) {
					continue;
				}
				int startX = x * canvasX;
				int startY = y * canvasY;
				int endX = (x + 1) * (canvasX);
				int endY = (y + 1) * (canvasY);

				dst.set(startX, startY, endX, endY);
				mark = next.getSelectionBitmap();
				canvas.drawBitmap(mark, null, dst, null);
			}
		}

	}

	public static void addSelection(Selection selection) {
		boolean isValidPlayerMove = putSelectionInArray(selection);
		checker.checkEndOfGame();
		if (SINGLEPLAYER_MODE && isValidPlayerMove) {
			Random rand = new Random();
			boolean isValidBotMove = false;
			do {
				int botX = rand.nextInt(3) * x;
				int botY = rand.nextInt(3) * y;
				Selection botSelection = new Selection(new Position(botX, botY));
				isValidBotMove = putSelectionInArray(botSelection);
			} while (!isValidBotMove);
		}

		if (!isValidPlayerMove) {
			Toast.makeText(GameboardView.context, "Invalid move", Toast.LENGTH_SHORT).show();
		}
		checker.checkEndOfGame();
	}

	private static boolean putSelectionInArray(Selection selection) {
		int currentX = selection.getPosition().getX() / x;
		int currentY = selection.getPosition().getY() / y;

		if (gameboard[currentY][currentX] == null) {
			int totalSelects = Selection.totalSelects;
			if (totalSelects % 2 == 0) {
				selection.setSelectionBitmap(xMark);
			} else {
				selection.setSelectionBitmap(oMark);
			}
			gameboard[currentY][currentX] = selection;
			return true;
		}

		Selection.invalidateSelection();
		boolean isFullBoard = true;
		for (int y = 0; y < 3; y++) {
			for (int x = 0; x < 3; x++) {
				if (gameboard[y][x] == null) {
					isFullBoard = false;
					break;
				}
			}
		}
		return isFullBoard;
	}
}
