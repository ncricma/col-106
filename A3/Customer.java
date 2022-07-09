// STATE: 
// 0 : NOT PRESENT
// k : IN QUEUE
// k+1 : WAITING FOR ORDER
// k+2 : SATISFIED

public class Customer{
    int id,burgerLeft,queueNum,state,t,endtime;
}