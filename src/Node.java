/**
 * Created by James on 5/5/2015.
 */
public class Node<E> {
    private E name;
    private int finalMark;
    private Node<E> nextElement;
    public Node<E> left;
    public Node<E> right;



    public Node(E v){
        name = v;
    }
    public Node<E> next(){
        return nextElement;
    }
    public void setNext(Node<E> next){
        nextElement = next;
    }

    public E value(){
        return name;
    }

    public void setValue(E value){
        name =value;
    }
}
