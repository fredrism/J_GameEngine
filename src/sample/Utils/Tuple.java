package sample.Utils;

public class Tuple<A, B> {
    public A a;
    public B b;

    public Tuple(A a, B b)
    {
        this.a = a;
        this.b = b;
    }

    public boolean Equals(Tuple<A, B> other)
    {
        return(this.a == other.a && this.b == other.b);
    }
}
