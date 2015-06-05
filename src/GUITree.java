import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by James on 5/20/2015.
 */
public class GUITree extends JPanel {
    BinarySearchTree<String> binarySearchTree = new BinarySearchTree<String>();
    SplayTree<String> splayTree = new SplayTree<>();
    BinaryTree root;
    private boolean isBst;

    public void setIsBst(boolean isBst) {
        this.isBst = isBst;
    }



    public static void main(String[] args) {
        chooseBstOrSplay();

    }


    //used to assign bst to the class BST. Only used in the after tree has been created. with the GUITree constructor
    public GUITree(BinarySearchTree bst) {
        this.binarySearchTree = bst;
        isBst = true;
    }

    public GUITree(SplayTree st) {
        this.splayTree = st;
        isBst = false;
    }


    //splay tree frame and panel
    public GUITree(int preferredSize, SplayTree st) {
        isBst = false;
        final JPanel panel = new JPanel();
        final JFrame frame = new JFrame();
        splayTree = st;


        frame.setBounds(100, 100, 600, 400);

        final JTextField textField = new JTextField();
        textField.setPreferredSize(new Dimension(150, 24));

        final JTextArea printArea = new JTextArea(50, 50);

        panel.add(textField);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(new GUITree(st));

        frame.setPreferredSize(new Dimension(preferredSize, preferredSize));
        frame.add(panel, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);

        final Graphics g = panel.getGraphics();
        g.setColor(Color.BLACK);

        JButton go = new JButton("Go");
        panel.add(go);
        go.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String command = textField.getText();

                executeCommandSplay(command);

                frame.setVisible(false);
                frame.dispose();

                new GUITree(1000, splayTree);
            }
        });

    }


    public GUITree(int preferredSize, BinarySearchTree bst) {
        isBst = true;
        final JPanel panel = new JPanel();
        final JFrame frame = new JFrame();
        binarySearchTree = bst;


        frame.setBounds(100, 100, 600, 400);

        final JTextField textField = new JTextField();
        textField.setPreferredSize(new Dimension(150, 24));

        final JTextArea printArea = new JTextArea(50, 50);

        panel.add(textField);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(new GUITree(bst));

        frame.setPreferredSize(new Dimension(preferredSize, preferredSize));
        frame.add(panel, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);

        final Graphics g = panel.getGraphics();
        g.setColor(Color.BLACK);

        JButton go = new JButton("Go");
        panel.add(go);
        go.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String command = textField.getText();

                executeCommand(command);

                frame.setVisible(false);
                frame.dispose();

                new GUITree(1000, binarySearchTree);
            }
        });

    }


    public void executeCommand(String command) {
        String[] s = command.split(" ");

        if (!isCommandValid(s)) {
            pleaseEnterValid();
            return;
        }
        if (s[0].equals("add")) {
            int mark = Integer.parseInt(s[2]);         //validity has already been checked in isCommandValid()
            binarySearchTree.add(s[1], mark);
            return;
        }
        if (s[0].equals("search")) {
            if (binarySearchTree.contains(s[1])) {
                popUpDisplayStr("Tree contains value");
            } else {
                popUpDisplayStr("Tree doesn't contain value");
            }
            return;
        }

        if (s[0].equals("remove")) {
            binarySearchTree.remove(s[1]);
            System.out.println("in remove");
            return;
        }

        if (s[0].equals("contains")) {
            if (binarySearchTree.contains(s[1])) {
                popUpDisplayStr("Tree contains value");
            } else {
                popUpDisplayStr("Tree doesn't contain value");
            }
            return;
        }

        if (s[0].equals("change")) {
            int mark = Integer.parseInt(s[3]);
            if (binarySearchTree.change(s[1], s[2], mark)) {
                binarySearchTree.change(s[1], s[2], mark);
            } else {
                pleaseEnterValid();
            }

            return;
        }

        System.out.println("before bottom of execute command");
        pleaseEnterValid();
    }


    public void executeCommandSplay(String command) {
        String[] s = command.split(" ");

        if (!isCommandValid(s)) {
            pleaseEnterValid();
            return;
        }


        if (s[0].equals("add")) {
            int mark = Integer.parseInt(s[2]);         //validity has already been checked in isCommandValid()
            splayTree.add(s[1], mark);
            return;
        }
        if (s[0].equals("search")) {
            if (splayTree.get(s[1])) {
                popUpDisplayStr("Tree contains value");
            } else {
                popUpDisplayStr("Tree doesn't contain value");
            }
            return;
        }

        if (s[0].equals("remove")) {
            splayTree.remove(s[1]);
            return;
        }

        if (s[0].equals("contains")) {
            if (splayTree.get(s[1])) {
                popUpDisplayStr("Tree contains value");
            } else {
                popUpDisplayStr("Tree doesn't contain value");
            }
            return;
        }

        if (s[0].equals("change")) {
            int mark = Integer.parseInt(s[3]);
            if (binarySearchTree.change(s[1], s[2], mark)) {
                binarySearchTree.change(s[1], s[2], mark);
            } else {
                pleaseEnterValid();
            }
            return;
        }

        System.out.println("before bottom of execute command");
        pleaseEnterValid();
    }

    public boolean isCommandValid(String[] string) {
        boolean result = true;
        if (string.length > 4 || string.length < 0 || string == null) {
            System.out.println(string.length);
            result = false;
        }

        if (string[0].equals("change")) {
            try {
                int mark = Integer.parseInt(string[3]);
            } catch (Exception e) {
                result = false;
            }
        } else if (string.length > 2) {
            try {
                int mark = Integer.parseInt(string[2]);
            } catch (Exception e) {
                result = false;
            }
        }
        return result;

    }

    // pop up or something
    public void pleaseEnterValid() {

        JButton button = new JButton("Please enter a valid command");
        JLabel format1 = new JLabel("Enter a command: search/add/remove/contains/add");
        JLabel format2 = new JLabel("Enter some text                                       ");
        JLabel format3 = new JLabel("Enter a number                                           ");
        JLabel format4 = new JLabel("Leave a space between each of the commands and variables");


        JPanel panel = new JPanel();
        final JFrame frame = new JFrame();

        final JTextField textField = new JTextField();
        frame.setPreferredSize(new Dimension(400, 200));
        frame.add(panel, BorderLayout.CENTER);
        frame.pack();
        panel.add(button);
        panel.add(format1);
        panel.add(format2);
        panel.add(format3);
        panel.add(format4);
        frame.setVisible(true);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
            }
        });


    }


    public static void chooseBstOrSplay() {

        JButton button = new JButton("Binary search tree");
        JButton button1 = new JButton("Splay tree");

//        JLabel format1 = new JLabel("Please select ");


        JPanel panel = new JPanel();
        final JFrame frame = new JFrame();

        final JTextField textField = new JTextField();
        frame.setPreferredSize(new Dimension(400, 200));
        frame.add(panel, BorderLayout.CENTER);
        frame.pack();
        panel.add(button);
        panel.add(button1);

        frame.setVisible(true);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BinarySearchTree<String> binarySearchTree = new BinarySearchTree<>();
                frame.setVisible(false);
                frame.dispose();
                new GUITree(1000, binarySearchTree);

            }
        });

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SplayTree<String> st = new SplayTree<String>();
                frame.setVisible(false);
                frame.dispose();
                new GUITree(1000, st);
            }
        });


    }


    public void popUpDisplayStr(String str) {
        JButton button = new JButton("Close");
        JLabel format1 = new JLabel(str);


        JPanel panel = new JPanel();
        final JFrame frame = new JFrame();

        final JTextField textField = new JTextField();
        frame.setPreferredSize(new Dimension(400, 200));
        frame.add(panel, BorderLayout.CENTER);
        frame.pack();
        panel.add(button);
        panel.add(format1);

        frame.setVisible(true);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
            }
        });


    }


    //method is called in GUITree()
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        g.setColor(Color.BLACK);


        if (isBst == true) {
            binarySearchTree.drawTree((Graphics2D) g, getWidth() / 2, 100, binarySearchTree.getRoot(), 150);
        } else {
            splayTree.drawTree((Graphics2D) g, getWidth() / 2, 100, splayTree.getRoot(), 150);

        }
    }


    public void drawTree(Graphics2D g2, int xPos, int yPos, BinaryTree root) {
        String s = root.toString();
        if (root.getVal() == null) return;

        g2.drawString(s, xPos, yPos);  // Print like "value : key"
//        System.out.println("---" + s);

        if (root.right.getVal() != null) {
            g2.drawLine(xPos, yPos, xPos + 100, yPos + 100);
            drawTree((Graphics2D) g2, xPos + 100, yPos + 100, root.getRight());
        }
        if (root.left.getVal() != null) {
            g2.drawLine(xPos, yPos, xPos - 100, yPos + 100);
            drawTree((Graphics2D) g2, xPos - 100, yPos + 100, root.getLeft());
        }

    }


}
