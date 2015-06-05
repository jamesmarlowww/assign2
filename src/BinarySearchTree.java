import sun.invoke.empty.Empty;

import java.awt.*;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by James on 5/18/2015.
 */
public class BinarySearchTree<E extends Comparable<E>> {
    public BinaryTree<E> root;
    private int size;


    public BinarySearchTree() {
        root = new BinaryTree<E>();
        size = 0;
    }



    protected BinaryTree<E> locate(BinaryTree<E> rootNode, E val) {
        E rootVal = rootNode.getVal();
        BinaryTree<E> child;
        if (rootVal == null) {
            return rootNode;
        }
        if (rootVal.equals(val))
            return rootNode;
        if (val.compareTo(rootVal) < 0) child = rootNode.left();
        else child = rootNode.right();
        if (child.isEmpty()) return rootNode;
        else return locate(child, val);
    }

    public boolean contains(E value) {
        if (root.isEmpty()) return false;
        BinaryTree<E> loc = locate(root, value);
        return value.equals(loc.getVal());
    }
    private boolean contains(String value) {
        if (root.isEmpty()) return false;
        E valueE =  (E) value;
        BinaryTree<E> loc = locate(root, valueE);
        return value.equals(loc.getVal());
    }

    public E get(E value) {
        if (root.isEmpty()) return null;
        BinaryTree<E> loc = locate(root, value);
        if (!value.equals(loc.getVal())) return null;
        else return loc.getVal();
    }

    public void add(E value, int mark) {
        BinaryTree<E> addLoc = locate(root, value);

        int x = 1;
        String valueStr = (String) value;
        if(contains(value)) {
            while(contains(valueStr+"("+String.valueOf(x)+")")) {
                x++;
            }
            value = (E) (valueStr+"("+String.valueOf(x)+")");
        }

        BinaryTree<E> newNode = new BinaryTree<E>(value, mark);
        if (root.isEmpty()) {
            root = newNode;
        } else {
            if (value.compareTo(addLoc.getVal()) == 0) {
                return;
            }
            if (value.compareTo(addLoc.getVal()) > 0) {
                addLoc.setRight(newNode);
            }
            if (value.compareTo(addLoc.getVal()) < 0) {
                addLoc.setLeft(newNode);
            }


        }
    }

    public <I, O> O convert(I input, Class<O> outputClass) throws Exception {
        return input == null ? null : outputClass.getConstructor(String.class).newInstance(input.toString());
    }

    public boolean change(E existingValue, E newValue, int newMark) {
        boolean result = false;

        if(contains(existingValue)) {
            BinaryTree<E> loc = locate(root, existingValue);
            if(loc!= null) {
                loc.setVal(newValue);
                loc.setMark(newMark);
                result = true;
            }
        }

        return result;
    }

    public void remove(E v) {
        //Remove the first occurrence of v
        if (!contains(v)) return;
        BinaryTree<E> locNode = locate(root, v);
        // Find the first occurrence of v
        if (locNode.equals(root)) {
            root = removeNode(locNode);
        } else if (locNode.equals(locNode.parent().left())) {
            locNode.parent().setLeft(removeNode(locNode));
        } else if (locNode.equals(locNode.parent().right())) {
            locNode.parent().setRight(removeNode(locNode));
        }
    }


    public BinaryTree<E> predecessor(BinaryTree<E> tree) {
        BinaryTree<E> match = null;
        BinaryTree<E> current = root;
        BinaryTree<E> predecessor = null;
// walk the tree until we find our exact match....
// Our predecessor node will follow us whenever we take a right branch....
        while (current != null) {
            if (current.getVal() == tree.getVal()) {
                match = current;
                break;
            }
            if (tree.getVal().compareTo(current.getVal()) < 0) {// used to be (num < current.key) {
                current = current.left;
            } else {
                predecessor = current;
                current = current.right;
            }
        }

        if (match == null) {
            return null;
        }
        if (match.left != null) {
            predecessor = match.left;
            while (predecessor.right.getVal() != null) {
                predecessor = predecessor.right;
            }
        }
        if (predecessor == null) {
            return null;
        }
        return predecessor;
    }


    public BinaryTree<E> removeNode(BinaryTree<E> k){
// Return the resulting tree after k is removed
        if(k.left().isEmpty() && k.right().isEmpty())
            return (new BinaryTree<E>());   //returns new binary tree instead of EMPTY. new tree has null value
        else if(k.left().isEmpty())
            return k.right();
        else if(k.right().isEmpty())
            return k.left();
        else{
            BinaryTree<E> pre=predecessor(k);
            pre.parent().setRight(pre.left());
            BinaryTree<E> le=k.left();
            BinaryTree<E> ri=k.right();
            k.setLeft(new BinaryTree<E>());    //returns new binary tree instead of EMPTY. new tree has null value
            k.setRight(new BinaryTree<E>());   //returns new binary tree instead of EMPTY. new tree has null value
            pre.setLeft(le);
            pre.setRight(ri);
            return pre;
        }
    }


    public String toString(BinaryTree root) {
        return root.getVal()+" : "+ root.getMark();
    }

    public String toStringPreOrder(BinaryTree root) {
        //// Write your code below
        String s = "";
        if (root.getVal() != null) {
            s = "(" + root.getVal()+", "+root.getMark() + ":" + toStringPreOrder(root.left) + ")(" + toStringPreOrder(root.right) + ")";
        }
        return s;
        //// Write your code above
    }

    public BinaryTree<E> getRoot() {
        return root;
    }

    public void drawTree(Graphics2D g2, int xPos, int yPos, BinaryTree root, int len) {

        String s = root.toString();
        if(root.getVal() == null) return;

        g2.drawString(s, xPos, yPos);  // Print like "value : key"

//        System.out.println("---" + s);

        if(root.right.getVal()!= null) {
            g2.drawLine(xPos,yPos, xPos+len,yPos+len);
            drawTree((Graphics2D) g2, xPos + len, yPos + len, root.getRight(), len-25);
        }
        if(root.left.getVal()!= null) {
            g2.drawLine(xPos, yPos, xPos - len, yPos + len);
            drawTree((Graphics2D) g2, xPos - len, yPos + len, root.getLeft(), len-25);
        }

    }


    public static void main(String[] args) {
        BinarySearchTree<String> binarySearchTree = new BinarySearchTree<>();
        binarySearchTree.add("h", 3);
        binarySearchTree.add("h(1)", 4);
        binarySearchTree.add("h(2)", 4);
        binarySearchTree.add("z", 2);
        binarySearchTree.add("e", 5);
        binarySearchTree.add("i", 10);
        binarySearchTree.add("q", 7);


        binarySearchTree.remove("e");
        //System.out.println(binarySearchTree.toStringPreOrder(binarySearchTree.root));
    }


}
