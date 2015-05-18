/**
 * Created by James on 5/18/2015.
 */
public interface Structure <E>{

    public void add(E value);
    // post: value added to the tree
    public void contains(E value);
    // post: returns true iff value is found
    public E get(E value);
    // post: returns object found in tree, or null
    public E remove(E value);
    // post: removes one instance of value, if found

}
