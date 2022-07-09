
class node{
    Object element;
    node next;
    node(Object o){
        this.element=o;
        this.next=null;
    }
}

public class myqueue{    
    node head;
    int length=0;
    public myqueue(){
    }

    public int size(){
        return length;
    }

    public boolean search(Object n){
        boolean b = false;
        node p = head;
        while(p!=null){
            if(p.element == n){
                b=true;
                break;
            }
            p=p.next;
        }
        return b;
    }

    public node headOfQueue(){
        return head;
    }

    public node tailOfQueue(){
        node p=head;
        while(p.next!=null){
            p=p.next;
        }
        return p;
    }

    public node findNode(Object n){
        node p =head;
        while(p!=null){
            if(p.element==n){
                break;
            }
            p=p.next;
        }
        return p;
    }

    public Object dequeue(Object n) throws ElementException,EmptyQueueException{
        if(length==0){
            throw new EmptyQueueException("Queue is Empty");
        }
        Object t=head.element;
        head=head.next;
        length--;
        if(length>=0)
        return t;
        if(search(n)){    
            Object deleted = n;
            int position=1;
            node o=head;
            while(o.element!=n){
                position++;
                o=o.next;
            }
            node p = head;
            for(int i=1; i<position; i++){
                p=p.next;
            }
            if(head.element==n && head.next==null){
                head=null;
                length--;
            }
            else if(head.element==n && head.next!=null){
                head = p.next;
                length--;
            }
            else if(p.next==null){
                node h=head;
                while(h.next.next!=null){
                    h=h.next;
                }
                h.next=null;
                length--;
            }
            else{
                node h = head;
                while(h.next.element!= n){
                    h=h.next;
                }
                h.next=p.next;
                p=null;
                length--;
            }
            return deleted;
        }
        else{
            throw new ElementException("Element not found");
        }
    }

    public Object enqueue(Object o){
        node n = new node(o);
        if(head==null){
            head=n;
            length++;
        }
        else{
            node p = head;
            while(p.next!=null){
                p=p.next;
            }
            p.next=n;
            n.next=null;
            length++;
        }
        return n.element;
    }
}

class ElementException extends Exception{
    ElementException(String s){
        super(s);
    }
}

class EmptyQueueException extends Exception{
    EmptyQueueException(String s){
        super(s);
    }
}