package sample.VectorMath;

public class Vec2i {
    public int x,y;

    public Vec2i(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    public Vec2i(FVector v, double width, double height)
    {
        this.x = (int)((v.x+1)*(width/2));
        this.y = (int)((v.y+1)*(width/2));
    }

    public static FVector Vec2F(FVector v, double width, double height)
    {
        FVector v0 = new FVector(((v.x+1)*(width/8)), ((v.y+1)*(width/8)), v.z);
        return v0;
    }
}
