
import javax.swing.JFrame;
import javax.swing.JScrollPane;

public class Lab3 {

	public static void main(String[] args) {
//		GUITree guiTreePanel = new GUITree(1000);
//		guiTreePanel.init(1000);
//		guiTreePanel.drawTriangle(3);

		JFrame frame = new JFrame();


		TurtlePanel panel = new TurtlePanel(1000);
		frame.add(panel);
		frame.setVisible(true);
		frame.pack();
//
//		panel.triangle();
//
//		// panel.tree(4);
//
//		//panel.treeQ5(4);
//		System.out.println(gcdBrute(50, 10));
		
		

	}
	
	public static int gcd(int a, int b) {
		if (a == 0)
			return b;

		while (b != 0) {
			if (a > b)
				a = a - b;
			else
				b = b - a;
		}
		return a;

	}

	public static int gcdBrute(int a, int b) {
		if (b == 0)
			return a;
		return gcdBrute(b, a % b);
	}

}
