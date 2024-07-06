import javax.swing.JFrame;

public class Components extends JFrame {

	Components() {

		this.add(new GamePanel());
		this.setTitle("SnakeGame");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.pack();

		this.setVisible(true);
		this.setLocationRelativeTo(null);

	}
}
