class HeapNode{
    HeapNode left;
    HeapNode right;
    HeapNode parent;
    HeapNode prevTail;
    Object data;

    HeapNode(Object data){
        this.data = data;
    }
};

interface Comp {
    public boolean func(Object p1, Object p2);
};
    
class MINMAX implements Comp {
    public boolean func(Object p1, Object p2) {
        Order a = (Order)p1;
        Order b = (Order)p2;
        if(a.t > b.t)return true;
        else if(a.t < b.t)return false;
        else{
            return a.c.queueNum < b.c.queueNum;
        }
    }
};

class MINMIN implements Comp {
    public boolean func(Object p1, Object p2) {
        int a[] = (int[])p1;
        int b[] = (int[])p2;
        if(a[0] > b[0])return true;
        else if(a[0] < b[0])return false;
        else{
            return a[1] > b[1];
        }
    }
};


public class Heap {
        HeapNode root;
        HeapNode tail;
        int size;
        Comp comp;

        Heap(Comp c){
            comp = c;
        }

        Object top(){
            // System.out.println(root);
            return root.data;
        }
        HeapNode topNode(){
            return root;
        }

        void setTail(HeapNode HeapNode){

            /*
              If we reach this stage that means a level is completely filled
              and we need to proceed to the next level by going to the extreme left.
             */
            if(HeapNode.parent == null){
                tail = HeapNode;
                while(tail.left != null){
                    tail = tail.left;
                }
            }
            /*
              If current HeapNode is the left HeapNode, go to the right HeapNode and
              proceed left from there to reach the left most HeapNode.
             */
            else if(HeapNode.parent.left == HeapNode){
                tail = HeapNode.parent.right;
                while(tail.left != null){
                    tail = tail.left;
                }
            }
            else if(HeapNode.parent.right == HeapNode){
                setTail(HeapNode.parent);
            }
        }

        void push(Object data){
            HeapNode newNode=new HeapNode(data);
            pushNode(newNode);
        }
        void pushNode(HeapNode newNode)
        {
            if(root == null){
                root = newNode;
                tail = root;
            }
            else if(tail.left == null){
                tail.left = newNode;
                tail.left.parent = tail;
                minHeapify(tail.left);
            }else {
                tail.right = newNode;
                tail.right.parent = tail;
                minHeapify(tail.right);
                HeapNode prevTail = tail;
                setTail(tail);
                tail.prevTail = prevTail;
            }
            size++;
        }

        HeapNode pop(){
            if(root == null) {
                System.out.println("MinHeap is empty");
                return null;
            }
            HeapNode temp=null;
            if(tail == null){
                temp=root;
                root = null;
            }
            else {
                if (tail.right != null){
                    swapHeapNodeData(tail.right, root);
                    temp=tail.right;
                    tail.right = null;
                    revMinHeapify(root);
                }
                else if(tail.left != null) {
                    swapHeapNodeData(tail.left, root);
                    temp=tail.left;
                    tail.left = null;
                    revMinHeapify(root);
                }
                else {
                    tail = tail.prevTail;
                    temp=pop();
                    size++;
                }
            }
            size--;
            return temp;
        }
        HeapNode pop(HeapNode del)
        {
            if(root == null) {
                System.out.println("MinHeap is empty");
                return null;
            }
            HeapNode temp=null;
            if(tail == null){
                temp=root;
                root = null;
                return temp;
            }
            else {
                if (tail.right != null){
                    swapHeapNodeData(tail.right, del);
                    temp=tail.right;
                    tail.right = null;
                    revMinHeapify(del);
                }
                else if(tail.left != null) {
                    swapHeapNodeData(tail.left, del);
                    temp=tail.left;
                    tail.left = null;
                    revMinHeapify(del);
                }
                else {
                    tail = tail.prevTail;
                    temp=pop(del);
                    size++;
                }
            }
            size--;
            return temp;
        }

        void swapHeapNodeData(HeapNode a, HeapNode b){
            Object temp = a.data;
            a.data = b.data;
            b.data = temp;
        }

        void minHeapify(HeapNode HeapNode){
            if(HeapNode.parent != null){
                if( comp.func(HeapNode.parent.data, HeapNode.data) ){
                    swapHeapNodeData(HeapNode.parent, HeapNode);
                    minHeapify(HeapNode.parent);
                }
            }
        }

        void revMinHeapify(HeapNode HeapNode){
            if(HeapNode == null || HeapNode.left == null)
                return;
            HeapNode min = HeapNode.left;
            if(HeapNode.right != null && comp.func(min.data, HeapNode.right.data) ){
                min = HeapNode.right;
            }
            if(comp.func(HeapNode.data, min.data)){
                swapHeapNodeData(HeapNode, min);
                revMinHeapify(min);
            }
        }
}