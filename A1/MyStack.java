public class MyStack implements StackInterface {
    
    private Object[] Arr;
    private int t;
    public MyStack(){
        Arr = new Object[1];
        t=-1;
    }

    public void push(Object o){
        if(o == null){

        }
        else if(t == Arr.length-1){      // if array is full then grow
            Object[] temp = new Object[2*(t+1)];
            for(int i=0; i<=t; i++){
                temp[i] = Arr[i];
            }
            Arr = temp;
            t=t+1;
            Arr[t]=o;
        }
        else{
            t=t+1;
            Arr[t] = o;
        }
    }

    public Object pop() throws EmptyStackException{
        if(isEmpty()){
            throw new EmptyStackException("");
        }
        else{
            Object o = Arr[t];
            Arr[t] = null;      // dereference to help garbage collection
            t = t-1;
            return o;
        }
    }

    public Object top() throws EmptyStackException{
        if(isEmpty()){
            throw new EmptyStackException("");
        }
        else{
            return Arr[t];
        }
    }

    public boolean isEmpty(){
        return t==-1;       // if t = -1 then stack is empty
    }

    public int size(){
        return t+1;
    }

    public String toString(){
        if(isEmpty()){          // if stack is empty then "[]"
            String ans = "[]";
            return ans;
        }
        else if(t==0){          // if stack has only one element
            String ans = "[" + Arr[0] + "]";
            return ans;
        }
        else{
            String ans = "[";
            int x = t;
            while(x != 0){
                ans = ans + Arr[x] + ", ";
                x = x-1;
                if(x==0){
                    ans = ans + Arr[0];
                }
            }
            ans+="]";
            return ans;
        }
    }
}
