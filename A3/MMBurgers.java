public class MMBurgers implements MMBurgersInterface {
    int time = 0;
    int initialtime = 0;
    int K,M;
    Heap h;
    myqueue q[];
    myqueue waiting;
    myqueue recieved;
    myqueue customers;
    HeapNode[] nodes;
    
    Griddle g;
    public boolean isEmpty(){
        //your implementation
        if(g.size>0 || g.waitsize>0 || ((int[])h.top())[0]>0 || g.PreparedOrders.size()>0) 
            return false;
        else
            return true;
    } 
    
    public void setK(int k) throws IllegalNumberException{
        if(k<=0) throw new IllegalNumberException("Invalid Value of k");
        K = k;
        q = new myqueue[K+1];
        for (int i = 0; i < K+1; i++){
            q[i]= new myqueue();
        }
        h = new Heap(new MINMIN());
        nodes=new HeapNode[k];
        for(int i=k-1;i>=0;i--)
        {
            int t[]={0,i};
            h.push(t);
            nodes[i]=h.topNode();
        }
        waiting = new myqueue();
        recieved = new myqueue();
        customers=new myqueue();
    }   
    
    public void setM(int m) throws IllegalNumberException{
        if(m<=0) throw new IllegalNumberException("Invalid value of m");
        M = m;
        g=new Griddle(m,K);
    } 

    public void advanceTime(int t) throws IllegalNumberException{
        
        int differenceInTime = t - time;
        if(differenceInTime<0){
            throw new IllegalNumberException("Please Enter valid time!");
        }
        else time=t;
        for(myqueue i:q)
        {
            while(true)
            {
                if(i.size()<=0) break;
                Customer c=(Customer)(i.headOfQueue().element);
                if((c.t+c.queueNum)<=t)
                {
                    c.state=K+1;
                    try{
                    i.dequeue(i.headOfQueue());
                    HeapNode deleted=h.pop(nodes[c.queueNum-1]);
                    
                    int[] deletedData=(int[]) deleted.data;
                    deletedData[0]--;
                    h.pushNode(deleted);
                    g.add(c,c.t+c.queueNum);
                    }
                    catch(Exception e)
                    {
                        System.out.println(e);
                        return;
                    }
                }
                else break;
            }
        }
        g.update(t);
    } 

    public void arriveCustomer(int id, int t, int numb) throws IllegalNumberException{
        advanceTime(t);
        int[] temp=(int[])h.top();
        int minindex = temp[1];
        Customer c=new Customer();
        c.id=id;
        c.burgerLeft=numb;
        c.queueNum=minindex+1;
        c.state=minindex+1;
        c.t=t;
        q[minindex].enqueue(c);
        HeapNode top=h.pop();
        int[] topD=((int[])(top.data));
        topD[0]+=1;
        h.pushNode(top);
        
        customers.enqueue(c);
    } 
    public Customer findNode(int id){
        node p =customers.head;
        while(p!=null){
            if(((Customer)p.element).id==id){
                return (Customer)p.element;
            }
            p=p.next;
        }
        return null;
    }
    public int customerState(int id, int t) throws IllegalNumberException{
        advanceTime(t);
        Customer temp=findNode(id);
        if(temp==null) return 0;
        return temp.state;
    } 

    public int griddleState(int t) throws IllegalNumberException{
        advanceTime(t);
        return g.size;
    } 

    public int griddleWait(int t) throws IllegalNumberException{
        advanceTime(t);
        return g.waitsize;
    } 

    public int customerWaitTime(int id) throws IllegalNumberException{
        
        Customer temp=findNode(id);
        if(temp==null) return 0;

        return temp.endtime-temp.t;
    } 

	public float avgWaitTime(){

        node curr=customers.head;
        int s=0,count=0;
        while(curr!=null)
        {
            s+=((Customer)curr.element).endtime-((Customer)curr.element).t;
            curr=curr.next;
            count++;
        }
        if(count==0)
            return 0.0f;
        else
            return (1.0f*s)/count;
    } 

    
}
