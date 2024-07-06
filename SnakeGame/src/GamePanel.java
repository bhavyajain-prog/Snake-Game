import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;

public class GamePanel extends JPanel implements ActionListener {

	static final int width = 600;
	static final int height = 600;
	static final int size = 25;
	static final int units = width * height / size;
	static final int delay = 75;
	final int[] x = new int[units];
	final int[] y = new int[units];
	int bodyParts = 5;
	int score = 0;
	int appleX;
	int appleY;
	char dir = 'R';
	boolean running = false;
	Timer timer;
	Random random;

	GamePanel() {
		random = new Random();
		this.setPreferredSize(new Dimension(width, height));
		this.setBackground(Color.BLACK);
		this.setFocusable(true);
		this.addKeyListener(new myKeyAdapter());
		startGame();
	}

	public void startGame() {
		newApple();
		running = true;
		timer = new Timer(delay, this);
		timer.start();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}

	public void draw(Graphics g) {
		if (running) {
			// grid
			// g.setColor(Color.gray);
			// for (int i = 0; i < width / size; i++) {
			// g.drawLine(i * size, 0, i * size, height);
			// g.drawLine(0, i * size, width, i * size);
			// }

			// draw apple
			g.setColor(Color.red);
			g.fillOval(appleX, appleY, size, size);

			// snake
			for (int i = 0; i < bodyParts; i++) {
				if (i == 0) {
					g.setColor(new Color(13, 146, 192));
					g.fillRect(x[i], y[i], size, size);
				} else {
					g.setColor(new Color(91, 181, 255));
					g.fillRect(x[i], y[i], size, size);
				}
			}

			// score
			g.setColor(Color.red);
			g.setFont(new Font("Ink Free", Font.BOLD, 40));
			FontMetrics metrics = getFontMetrics(g.getFont());
			g.drawString("Score: " + score, (width - metrics.stringWidth("Score: " + score)) / 2,
					g.getFont().getSize());

		} else
			gameOver(g);
	}

	public void newApple() {
		appleX = random.nextInt((int) (width / size)) * size;
		appleY = random.nextInt((int) (height / size)) * size;
	}

	public void move() {
		for (int i = bodyParts; i > 0; i--) {
			x[i] = x[i - 1];
			y[i] = y[i - 1];
		}
		switch (dir) {
			case 'R':
				x[0] = x[0] + size;
				break;
			case 'L':
				x[0] = x[0] - size;
				break;
			case 'U':
				y[0] = y[0] - size;
				break;
			case 'D':
				y[0] = y[0] + size;
				break;
		}
	}

	public void checkApple() {
		if (x[0] == appleX && y[0] == appleY) {
			bodyParts++;
			score++;
			newApple();
		}
	}

	public void checkCollisions() {
		// collides with body
		for (int i = bodyParts; i > 0; i--) {
			if (x[0] == x[i] && y[0] == y[i]) {
				running = false;
			}
		}
		// collides with border
		if (x[0] < 0)
			running = false;
		if (x[0] > width)
			running = false;
		if (y[0] < 0)
			running = false;
		if (y[0] > height)
			running = false;

		if (!running)
			timer.stop();
	}

	public void gameOver(Graphics g) {
		// gameover
		g.setColor(Color.red);
		g.setFont(new Font("Ink Free", Font.BOLD, 75));
		FontMetrics metrics1 = getFontMetrics(g.getFont());
		g.drawString("GAME OVER!", (width - metrics1.stringWidth("GAME OVER!")) / 2, height / 2);

		// score
		g.setColor(Color.red);
		g.setFont(new Font("Ink Free", Font.BOLD, 40));
		FontMetrics metrics = getFontMetrics(g.getFont());
		g.drawString("Score: " + score, (width - metrics.stringWidth("Score: " + score)) / 2,
				g.getFont().getSize() + height/2 + size);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (running) {
			move();
			checkApple();
			checkCollisions();
		}
		repaint();

	}

	public class myKeyAdapter extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			switch (e.getKeyCode()) {
				case KeyEvent.VK_LEFT:
					if (dir != 'R')
						dir = 'L';
					break;
				case KeyEvent.VK_RIGHT:
					if (dir != 'L')
						dir = 'R';
					break;
				case KeyEvent.VK_UP:
					if (dir != 'D')
						dir = 'U';
					break;
				case KeyEvent.VK_DOWN:
					if (dir != 'U')
						dir = 'D';
					break;
			}
		}
	}

}
