public class SplayTreeNode<T extends Comparable<T>> {

    private final String nullNodeString = "_";
    public SplayTreeNode<T> left;
    public SplayTreeNode<T> right;
    private SplayTreeNode<T> parent;

    public T key;
    private int value;
    private boolean isremoved = false;

    public int getMark() {
        return value;
    }

    public SplayTreeNode(T key, SplayTreeNode<T> parent, int value) {
        this.key = key;
        this.parent = parent;
        this.value = value;
    }

    @Override
    public String toString() {

        return key.toString() + " : " + value;
    }

    public String toStringOrignal() {
        return key + " : { " +
                (leftExists() ? left.toString() : nullNodeString) + " , " +
                (rightExists() ? right.toString() : nullNodeString) + " }";
    }

    public boolean leftExists() {
        return left != null;
    }

    public boolean rightExists() {
        return right != null;
    }

    public boolean parentExists() {
        boolean result = false;
        try {
            if (Boolean.TRUE.equals(parent)) {
                result = true;
            }
        } catch (NullPointerException e) {
//            result = false;
        }

        return result;
    }

    public T getKey() {
        return key;
    }

    public void setKey(T key) {
        this.key = key;
    }

    public SplayTreeNode<T> getLeft() {
        return left;
    }

    public void setLeft(SplayTreeNode<T> left) {
        this.left = left;
    }

    public SplayTreeNode<T> getRight() {
        return right;
    }

    public void setRight(SplayTreeNode<T> right) {
        this.right = right;
    }

    public boolean isremoved() {
        return isremoved;
    }

    public void setremoved(boolean isDeleted) {
        this.isremoved = isremoved;
    }

    public SplayTreeNode<T> getParent() {
        return parent;
    }

    public void setParent(SplayTreeNode<T> parent) {
        this.parent = parent;
    }

}
