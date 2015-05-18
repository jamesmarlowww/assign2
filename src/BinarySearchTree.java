import sun.invoke.empty.Empty;

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

    protected BinaryTree<E> locate(BinaryTree<E> root, E val){
        E rootVal=root.getVal();
        BinaryTree<E> child;
        if(rootVal.equals(val)) return root;
        if(val.compareTo(rootVal)<0) child=root.left();
        else child=root.right();
        if (child.isEmpty()) return root;
        else return locate(child, val);
    }

    public boolean contains(E value){
        if (root.isEmpty()) return false;
        BinaryTree<E> loc = locate(root, value);
        return value.equals(loc.getVal());
    }
    public E get(E value){
        if (root.isEmpty()) return null;
        BinaryTree<E> loc = locate(root, value);
        if (!value.equals(loc.getVal())) return null;
        else return loc.getVal();
    }

    public void add(E value) {
        BinaryTree<E> addLoc = locate(root, value);
        if(value.compareTo(addLoc.getVal()) == 0) {
            return;
        }

        BinaryTree<E> newNode = new BinaryTree<E>(value);
        if(root.isEmpty()) {
            root = newNode;
        } else {
            if(value.compareTo(addLoc.getVal())>0) {
                addLoc.setRight(newNode);
            } else {
                addLoc.setLeft(newNode);
            }
        }

    }

}
