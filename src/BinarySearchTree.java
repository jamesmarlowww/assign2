import sun.invoke.empty.Empty;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by James on 5/18/2015.
 */
public class BinarySearchTree<E extends Comparable<E>> {
    private BinaryTree<E> root;
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

    public E get(E value) {
        if (root.isEmpty()) return null;
        BinaryTree<E> loc = locate(root, value);
        if (!value.equals(loc.getVal())) return null;
        else return loc.getVal();
    }

    public void add(E value) {
        BinaryTree<E> addLoc = locate(root, value);

        BinaryTree<E> newNode = new BinaryTree<E>(value);
        if (root.isEmpty()) {
            root = newNode;
        } else {
            if (value.compareTo(addLoc.getVal()) == 0) {
                return;
            }
            if (value.compareTo(addLoc.getVal()) > 0) {
                addLoc.setRight(newNode);
            }
            if(value.compareTo(addLoc.getVal()) < 0) {
                addLoc.setLeft(newNode);
            }


        }
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
        } else if(locNode.equals(locNode.parent().right())){
            locNode.parent().setRight(removeNode(locNode));
        }

    }


    //slides had return empty instead of null
    public BinaryTree<E> removeNode(BinaryTree<E> k) {
        // Return the resulting tree after k is removed
        if (k.left().isEmpty() && k.right().isEmpty())
            return null;
        else if (k.left().isEmpty())
            return k.right();
        else if (k.right().isEmpty())
            return k.left();
        else {
            //BinaryTree<E> pre=predecessor(k);
            BinaryTree<E> pre = k.parent();      //added instead of above line. likely wrong
            pre.parent().setRight(pre.left());
            BinaryTree<E> le = k.left();
            BinaryTree<E> ri = k.right();
            k.setLeft(null);
            k.setRight(null);
            pre.setLeft(le);
            pre.setRight(ri);
            return pre;
        }
    }




    public String toStringPreOrder(BinaryTree root) {
        //// Write your code below
        String s = "";
        if (root.getVal() != null) {
            s= "("+root.getVal()+":"+ toStringPreOrder(root.left)+")("+ toStringPreOrder(root.right)+")";
        }
        return s;
        //// Write your code above
    }

    public static void main(String[] args) {
        BinarySearchTree<Integer> binarySearchTree = new BinarySearchTree<>();
        binarySearchTree.add(3);
        binarySearchTree.add(1);
        binarySearchTree.add(2);
        binarySearchTree.add(5);
        binarySearchTree.add(10);

        binarySearchTree.remove(2);
        System.out.println(binarySearchTree.toStringPreOrder(binarySearchTree.root));
    }


}
