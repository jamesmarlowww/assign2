public class SplayTree<T extends Comparable<T>> {

    private SplayTreeNode<T> root;

    public SplayTree() { }

    private void splay(SplayTreeNode<T> node) {
        while (node.parentExists()) {
            SplayTreeNode parent = node.getParent();
            if (!parent.parentExists()) {
                if (parent.getLeft() == node) {
                    rotateRight(parent);
                } else {
                    rotateLeft(parent);
                }
            } else {
                SplayTreeNode gparent = parent.getParent();
                if (parent.getLeft() == node && gparent.getLeft() == parent) {
                    rotateRight(gparent);
                    rotateRight(node.getParent());
                } else if (parent.getRight() == node &&
                        gparent.getRight() == parent) {
                    rotateLeft(gparent);
                    rotateLeft(node.getParent());
                } else if (parent.getLeft() == node &&
                        gparent.getRight() == parent) {
                    rotateRight(parent);
                    rotateLeft(node.getParent());
                } else {
                    rotateLeft(parent);
                    rotateRight(node.getParent());
                }
            }
        }
    }

    private void rotateLeft(SplayTreeNode<T> x) {
        SplayTreeNode y = x.getRight();
        x.setRight(y.getLeft());
        if (y.getLeft() != null) {
            y.getLeft().setParent(x);
        }
        y.setParent(x.getParent());
        if (x.getParent() == null) {
            root = y;
        } else {
            if (x == x.getParent().getLeft()) {
                x.getParent().setLeft(y);
            } else {
                x.getParent().setRight(y);
            }
        }
        y.setLeft(x);
        x.setParent(y);
    }

    private void rotateRight(SplayTreeNode<T> x) {
        SplayTreeNode y = x.getLeft();
        x.setLeft(y.getRight());
        if (y.getRight() != null) {
            y.getRight().setParent(x);
        }
        y.setParent(x.getParent());
        if (x.getParent() == null) {
            root = y;
        } else {
            if (x == x.getParent().getLeft()) {
                x.getParent().setLeft(y);
            } else {
                x.getParent().setRight(y);
            }
        }
        y.setRight(x);
        x.setParent(y);
    }

    public void add(T key) {
        if (root == null) {
            root = new SplayTreeNode(key, null);
            return;
        }

        add(key, root);
        get(key);
    }

    private void add(T key, SplayTreeNode<T> node) {
        if (key.compareTo( node.getKey() ) < 0) {
            if (node.leftExists()) {
                add(key, node.getLeft());
            } else {
                node.setLeft(new SplayTreeNode(key, node));
            }
        }

        if (key.compareTo(node.getKey())>0) {
            if (node.rightExists()) {
                add(key, node.getRight());
            } else {
                node.setRight(new SplayTreeNode(key, node));
            }
        }
    }

    public void remove(T key) {
        if (root == null) {
            return;
        }

        get(key);
        remove(key, root);
    }

    private void remove(T key, SplayTreeNode<T> node) {
        if (key.compareTo(node.getKey())< 0) {
            if (node.leftExists()) {
                remove(key, node.getLeft());
            }
            if (node.getLeft().isremoved()) {
                node.setLeft(null);
            }
            return;
        }

        if (key.compareTo(node.getKey()) > 0) {
            if (node.rightExists()) {
                remove(key, node.getRight());
            }
            if (node.getRight().isremoved()) {
                node.setRight(null);
            }
            return;
        }

        remove(node);
    }

    private void remove(SplayTreeNode<T> node) {
        if (!(node.leftExists() || node.rightExists())) {
            node.setremoved(true);
            return;
        }

        if (node.leftExists() && !node.rightExists()) {
            node.setKey(node.getLeft().getKey());
            if (node.getLeft().rightExists()) {
                node.setRight(node.getLeft().getRight());
            }
            if (node.getLeft().leftExists()) {
                node.setLeft(node.getLeft().getLeft());
            } else {
                node.setLeft(null);
            }
            return;
        }

        if (node.rightExists() && !node.leftExists()) {
            node.setKey(node.getRight().getKey());
            if (node.getRight().leftExists()) {
                node.setLeft(node.getLeft().getLeft());
            }
            if (node.getRight().rightExists()) {
                node.setRight(node.getLeft().getRight());
            } else {
                node.setRight(null);
            }
            return;
        }

        // both exist, replace with minimum from right sub-tree
        T min = findMin(node.getRight());
        node.setKey(min);
    }

    private T findMin(SplayTreeNode<T> node) {
        if (!node.leftExists()) {
            node.setremoved(true);
            return node.getKey();
        }

        T min = findMin(node.getLeft());
        if (node.getLeft().isremoved()) {
            node.setLeft(null);
        }
        return min;
    }

    public boolean get(T key) {
        if (root == null) {
            return false;
        }

        SplayTreeNode<T> node = get(key, root);
        splay(node);
        return node != null;
    }

    private SplayTreeNode<T> get(T key, SplayTreeNode<T> node) {
        if (key == node.getKey()) {
            return node;
        }

        if (key.compareTo(node.getKey()) < 0) {
            if (!node.leftExists()) {
                return null;
            }
            return get(key, node.getLeft());
        }

        if (key.compareTo(node.getKey()) > 0) {
            if (!node.rightExists()) {
                return null;
            }
            return get(key, node.getRight());
        }

        return null;
    }

    public String toString() {
        return root.toString();
    }

}
