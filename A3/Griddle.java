
public class Griddle {
    int MAXSIZE;
    int size,waitsize,nextTime;
    myqueue queue;
    Heap wqueue;
    myqueue PreparedOrders;    
    int k;
    Griddle(int m,int k){
        MAXSIZE = m;
        this.k=k;
        size = 0;
        waitsize=0;
        nextTime=0;
        queue=new myqueue();
        wqueue=new Heap(new MINMAX());
        PreparedOrders=new myqueue();
    }

    int getSize(){
        return size;
    }

    void add(Customer c, int t){
        wqueue.push(new Order(c,t,c.burgerLeft));
        waitsize+=c.burgerLeft;
        c.state =k+ 1;
    }
    void add(Order o)
    {
        if(MAXSIZE > size){
            if(MAXSIZE<(size+o.burgers))
            {
                int temp=o.burgers-MAXSIZE+size;
                waitsize+=temp;
                wqueue.push(new Order(o.c,o.t,temp));
            }
            int added=min(MAXSIZE -size, o.burgers);
            Order no=new Order(o.c,-min(-o.t,-nextTime),added);
            size+=added;
            queue.enqueue(no);
            
            // System.out.println("here");
        }
        else{
            waitsize+=o.burgers;
            wqueue.push(o);
        }
        o.c.state=k+1;
        
        // System.out.println("here");
    }

    void update(int time){
        while(PreparedOrders.size()>0)
        {
            try{
           Customer c=(Customer)(PreparedOrders.headOfQueue().element);
           c.state=k+2;
            PreparedOrders.dequeue(PreparedOrders.headOfQueue());
            }
            catch(Exception e){System.out.print("PO");return;}
        }
        while(size<MAXSIZE)
        {
            if(wqueue.size>0)
            {
                    // System.out.println(wqueue.size);
                try {
                    
                    Order new1=(Order)wqueue.top();
                    wqueue.pop();
                    waitsize-=new1.burgers;
                    // new1.t=nextTime;
                    add(new1);
                } catch (Exception e) {
                    
                    System.out.println("dve");
                    return;
                }
                
            }
            else break;
        }
        while(queue.size() > 0){
            Order top = (Order)queue.headOfQueue().element;
            if(top.t + 10 <= time){
                try {
                    queue.dequeue((Object)top);
                    size-=top.burgers;
                } catch (Exception e) {
                    System.out.print("dvPO");
                    return;
                }
                top.c.burgerLeft -= top.burgers;
                if(top.c.burgerLeft == 0){
                    top.c.endtime=top.t+11;
                    if(top.c.endtime<=time)
                        top.c.state =k+2;
                    else
                        PreparedOrders.enqueue(top.c);
                }
                nextTime=top.t+10;
                // System.out.println("xfvdvsf"+nextTime);
                while(size<MAXSIZE)
                {
                    if(wqueue.size>0)
                    {
                        try {
                            Order new1=(Order)wqueue.top();
                            wqueue.pop();
                            waitsize-=new1.burgers;
                            add(new1);
                        } catch (Exception e) {
                            System.out.println("dvzd");
                            return;
                        }
                        
                    }
                    else break;
                }
            }
            else{
                break;
            }
        }
    }

    private int min(int a, int b){
        return (a<=b) ? a : b;
    }
}
