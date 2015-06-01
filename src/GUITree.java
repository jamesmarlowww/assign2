import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by James on 5/20/2015.
 */
public class GUITree extends JPanel {
    BinarySearchTree<String> binarySearchTree;
    BinaryTree root;



    public static void main(String[] args) {

        GUITree guiTreePanel = new GUITree(1000);

    }


    public GUITree(){}



    // is the panel
    public GUITree(int preferredSize) {

        JPanel panel = new JPanel();
        JFrame frame = new JFrame();


        frame.setBounds(100, 100, 600, 400);

        final JTextField textField = new JTextField();
        textField.setPreferredSize(new Dimension(150, 24));


        panel.add(textField);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(new GUITree());
        frame.setPreferredSize(new Dimension(preferredSize, preferredSize));
        frame.add(panel, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);


        binarySearchTree = new BinarySearchTree<>();
        binarySearchTree.add("marlow", 4);
        binarySearchTree.add("a", 3);
        binarySearchTree.add("b", 1);
        binarySearchTree.add("yolo", 5);
        binarySearchTree.add("zuse", 9);


        JButton go = new JButton("Go");
        go.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String command = textField.getText();

                executeCommand(command);

                //printArea.setText(binarySearchTree.toStringPreOrder(binarySearchTree.getRoot()));

            }
        });

        panel.add(go);
        //panel.add(printArea);
        //printArea.setText(binarySearchTree.toStringPreOrder(binarySearchTree.getRoot()));
//        binarySearchTree.toStringPreOrder(binarySearchTree.getRoot());

        Graphics g = panel.getGraphics();
        g.setColor(Color.blue);
        printComponent(g);
        root = binarySearchTree.getRoot();

    }

    public static class MainPanel extends JPanel {
        MainPanel() {
        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(Color.BLACK);
            int width = getWidth();
            int height = getHeight();
            drawBoard((Graphics2D) g, width, height);
        }

        private void drawBoard(Graphics2D g2, int width, int height) {
            g2.drawLine(10,10, 100,100);
        }

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
    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        g.setColor(Color.BLACK);

        int width = getWidth()/2; //middle of panel
        int height = 100; // just below the go button


//        drawTree((Graphics2D) g, width, height, binarySearchTree.root);

        //the ui doesnt load without a try catch. Should see full error message without try catch
        try{
            drawTree((Graphics2D) g, width, height, binarySearchTree.root);   // null pointer using binarySearchTree.root
        } catch (NullPointerException e) {
            System.out.println("null pointer: "+e);
        }

        //binarySearchTree.drawTree((Graphics2D) g, width, height);
    }

    private void drawTree(Graphics2D g2, int xPos, int yPos, BinaryTree root) {
        //just trying different things, seeing if they work.
//        if(binarySearchTree.getRoot().left()!= null) System.out.println(binarySearchTree.toStringPreOrder(binarySearchTree.root));
//        System.out.println(root.toString());
//
//        g2.drawLine(xPos, yPos, xPos + 100, yPos + 100);
//        g2.drawLine(xPos,yPos, xPos-100, yPos+100);



        //// I planed to print out the tree like this but haven't been able to run it. Keep getting null pointers, have looked into it much yet tho
        ///  Not sure if it will work , dont know if using g2 recursivly will work.



        g2.drawString(root.toString(), xPos, yPos);  // Print like "value : key"

        if(root.right.getVal()!= null) { // if there is a right node, draw line,
            g2.drawLine(xPos,yPos, xPos+100,yPos+100);
            drawTree((Graphics2D)g2, xPos+100,yPos+100, root.right);
        }
        if(root.left.getVal()!= null) {
            g2.drawLine(xPos, yPos, xPos - 100, yPos + 100);
            drawTree((Graphics2D)g2, xPos-100,yPos+100, root.left);
        }




    }






}
