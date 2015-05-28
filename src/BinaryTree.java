import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by James on 5/5/2015.
 * james marlow
 * 1249807
 *
 *i got this class straight from the lab so there is some extra stuff we dont need
 */

public class BinaryTree<E> {
    private E val;
    private int mark;
    private BinaryTree<E> parent;
    public BinaryTree<E> left, right;


    public E getVal() {
        return val;
    }
    public int getMark() {return mark;  }
    public void setVal(E newVal) {val = newVal;}
    public void setMark(int mark) {this.mark = mark;}

    public BinaryTree() {
        val = null;
        parent = null;
        left = right = this;

    }

    public BinaryTree(E value, int mark) {
        //// Write your code below
        val = value;
        this.mark = mark;
        right = left = new BinaryTree<E>();
        setLeft(left);
        setRight(right);
        //// Write your code above
    }

    public BinaryTree(E value, BinaryTree<E> left, BinaryTree<E> right) {
        //// Write your code below
        val = value;
        if (left == null) {
            left = new BinaryTree<E>();
        }
        setLeft(left);
        if (right == null) {
            right = new BinaryTree<E>();
        }
        setRight(right);
        //// Write your code above
    }

    public boolean isEmpty() {
        if(val ==null) {
            return true;
        } else {
            return false;
        }
    }

    public void setLeft(BinaryTree<E> newLeft) {
        if (isEmpty()) {
            return;
        }
        if (left != null && left.parent() == this) {
            left.setParent(null);
        }
        left = newLeft;
        left.setParent(this);
    }

    public void setRight(BinaryTree<E> newRight) {
        if (isEmpty()) {
            return;
        }
        if (right != null && right.parent() == this) {
            left.setParent(null);
        }
        right = newRight;
        right.setParent(this);
    }

    public void preorderTraverseTree() {

        if (val != null) {

            System.out.println(val);

            right.preorderTraverseTree();
            left.preorderTraverseTree();

        }

    }


    //the toStringPre()  prints with the correct bracket format
    public Queue<BinaryTree<E>> levelOrderTraversal() {
        Queue<BinaryTree<E>> level  = new LinkedList<>();
        level.add(this);
        while(!level.isEmpty()){
            BinaryTree node = level.poll();
            System.out.print(node.val + " ");
            if(node.left.val!= null)
                level.add(node.left);
            if(node.right.val!= null)
                level.add(node.right);
        }

        return level;

    }

    public String displayTree() {
        return toStringPre();
    }


//preorder
    public String toStringPre() {
        //// Write your code below
        String s = "";
        if (val != null) {
            s= "("+val+":"+ right.toStringPre()+") ("+ left.toStringPre()+")";

        }
        return s;
        //// Write your code above
    }


    public int height() {
        if (val == null) {
            return 0;
        } else {
            return 1 + Math.max(left.height(), right.height());
        }
    }

    public void setParent(BinaryTree<E> newParent) {
        if(!isEmpty()){
            parent=newParent;
        }
    }

    public BinaryTree<E> left() {

        return left;
    }



    public BinaryTree<E> right() {
        return right;
    }

    public BinaryTree<E> parent() {
        return parent;
    }

    public void addRight(BinaryTree<E> newTree) {
        right = newTree;
    }

    public void addLeft(BinaryTree<E> newTree) {
        left = newTree;
    }


    //Return the level of the node
    public int level() {
        //// Write your code below
        if (parent() == null) {
            return 0;
        }
        return 1 + parent().level();

        //// Write your code above
    }


    public static void main(String[] args) {
        BinaryTree<String> binaryTree = new BinaryTree<>("A", 2);
        BinaryTree<String> b = new BinaryTree<>("B", 2);
        BinaryTree<String> c = new BinaryTree<>("C",4);
        BinaryTree<String> d = new BinaryTree<>("D", 7);

        binaryTree.setLeft(b);
        binaryTree.setRight(c);
        c.setLeft(d);

        System.out.println(binaryTree.levelOrderTraversal().toString());

    }

}
