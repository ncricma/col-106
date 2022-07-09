public interface StackInterface{
    public void push(Object o);
    public Object pop() throws EmptyStackException;
    public Object top() throws EmptyStackException;
    public boolean isEmpty();
    public String toString();
    public int size();
}

class EmptyStackException extends Exception{
    EmptyStackException(String s){
        super(s);
    }
}