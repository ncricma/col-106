public class Order{
    int t;
    Customer c;
    int burgers;
    Order(Customer c, int t, int burgers)
    {
        this.c=c;
        this.t=t;
        this.burgers = burgers;
    }
}
