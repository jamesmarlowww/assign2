import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;

/**
 * Created by James on 5/20/2015.
 */
public class GUITree extends JPanel {
    BinarySearchTree<String> binarySearchTree;

    public static void main(String[] args) {
        GUITree t = new GUITree();


    }


    public GUITree() {
        JPanel panel = new JPanel();
        JFrame frame = new JFrame();

        final JTextField textField = new JTextField();
        textField.setPreferredSize(new Dimension(150, 24));
        final JTextArea printArea = new JTextArea(20, 40);


        panel.add(textField);

        frame.setPreferredSize(new Dimension(500, 415));
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

                printArea.setText(binarySearchTree.toStringPreOrder(binarySearchTree.getRoot()));


            }
        });

        panel.add(go);
        panel.add(printArea);
        printArea.setText(binarySearchTree.toStringPreOrder(binarySearchTree.getRoot()));


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
            return;
        }

        System.out.println("before bottom of execute command");
        pleaseEnterValid();


    }

    public boolean isCommandValid(String[] string) {
        boolean result = true;
        if (string.length > 3 || string.length < 0 || string == null) {
            System.out.println(string.length);
            result = false;
        }

        if (string.length > 2) {
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

    public void paintComponent(Graphics g) {
        binarySearchTree.drawTree(g);

//        super.paintComponent(g);
//        Graphics2D g2d = (Graphics2D) g;
//        g2d.setColor(Color.BLACK);
//        System.out.println("W:" + getSize().width + ", H:" + getSize().height);
//        g2d.fillRect(0, 0, getSize().width, getSize().height);


    }
}
