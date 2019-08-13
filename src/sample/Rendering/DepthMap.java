package sample.Rendering;

public class DepthMap {
    private int width, height;
    private double[] values;

    public DepthMap(int width, int height)
    {
        this.width = width;
        this.height = height;
        values = new double[width*height];
        fill(Double.MAX_VALUE);
    }

    public void Clear()
    {
        fill(Double.MAX_VALUE);
    }

    void fill(double newValue)
    {
        for(int y = 0; y < height; y++)
        {
            for(int x = 0; x < width; x++)
            {
                values[y*width + x] = newValue;
            }
        }
    }

    public boolean TestDepth(int x, int y, double value)
    {
        if(values[y*width + x] > value)
        {
            values[y*width + x] = value;
            return true;
        }
        return false;
    }

    public double GetDepth(int x, int y)
    {
        return values[y*width + x];
    }
}
