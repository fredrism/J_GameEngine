package sample.VectorMath;

public class Matrix implements java.io.Serializable{
    private double[][] values;

    public Matrix()
    {
        values = new double[4][4];
    }

    public static Matrix ScaleMatrix(double x, double y, double z)
    {
        Matrix m = new Matrix();
        m.values = new double[][]{{x, 0, 0, 0},
                                {0, y, 0, 0},
                                {0, 0, z, 0},
                                {0, 0, 0, 1}};
        return m;
    }

    public static Matrix TranslateMatrix(double x, double y, double z)
    {
        Matrix m = new Matrix();
        m.values = new double[][]{{1, 0, 0, x},
                                  {0, 1, 0, y},
                                  {0, 0, 1, z},
                                  {0, 0, 0, 1}};
        return m;
    }

    void SetColumn(int r, double a, double b, double c, double d)
    {
        values[r][0] = a;
        values[r][1] = b;
        values[r][2] = c;
        values[r][3] = d;
    }

    void SetRow(int r, double a, double b, double c, double d)
    {
        values[0][r] = a;
        values[1][r] = b;
        values[2][r] = c;
        values[3][r] = d;
    }

    public static Matrix RotationMatrix(double x, double y, double z)
    {
        Matrix m = new Matrix();
        double sinX = Math.sin(x);
        double cosX = Math.cos(x);
        double sinY = Math.sin(y);
        double cosY = Math.cos(y);
        double sinZ = Math.sin(z);
        double cosZ = Math.cos(z);

        m.SetColumn(0,cosY*cosZ,
                        cosX*sinZ+sinX*sinY*cosZ,
                            sinX*sinZ-cosX*sinY*cosZ,
                         0);

        m.SetColumn(1, -cosY*sinZ,
                            cosX*cosZ-sinX*sinY*sinZ,
                            sinX*cosZ+cosX*sinY*sinZ,
                            0);

        m.SetColumn(2, sinY,
                            -sinX*cosY,
                                cosX*cosY,
                                0);

        m.SetColumn(3, 0,0,0, 1);

        return m;
    }

    public static Matrix ProjectionMatrix(double dFar, double dNear, double FOVRad, double aspect)
    {
        Matrix m = new Matrix();
        m.values = new double[][]{{FOVRad, 0, 0, 0},
                                  {0, FOVRad, 0, 0},
                                  {0, 0, (-dFar)/(dFar-dNear), -1},
                                  {0, 0, (-dFar*dNear)/(dFar-dNear), 0}};
        return m;
    }

    public static Matrix SimpleProjection(double FOVRad, double width, double height)
    {
        Matrix m = new Matrix();
        m.SetColumn(0, 1, 0, 0, 0);
        m.SetColumn(1, 0, 1, 0, 0);
        m.SetColumn(2, 0,0,1,0);
        m.SetColumn(3,0,0,FOVRad,1);
        return m;
    }

    public static Matrix viewportMatrix(double x, double y, double w, double h, double d)
    {
        Matrix m = new Matrix();
        m.SetColumn(0, w/2, 0, 0, x+(w/2));
        m.SetColumn(1, 0, h/2, 0, y+(h/2));
        m.SetColumn(2, 0,0,d/2,d/2);
        m.SetColumn(3,0,0,0,1);
        return m;
    }

    public static FVector MulPoint(FVector vec, Matrix m, boolean divW)
    {
        double x = vec.x*m.values[0][0] + vec.y*m.values[0][1] + vec.z*m.values[0][2]+vec.w*m.values[0][3];
        double y = vec.x*m.values[1][0] + vec.y*m.values[1][1] + vec.z*m.values[1][2]+vec.w*m.values[1][3];
        double z = vec.x*m.values[2][0] + vec.y*m.values[2][1] + vec.z*m.values[2][2]+vec.w*m.values[2][3];
        double w = vec.x*m.values[3][0] + vec.y*m.values[3][1] + vec.z*m.values[3][2]+vec.w*m.values[3][3];

        if(w != 0 && divW)
        {
            x /= w;
            y /= w;
            z /= w;
            w /= w;
        }
        return new FVector(x,y,z,w);
    }

    public static Matrix MulMatrix(Matrix A, Matrix B)
    {
        Matrix matrix = new Matrix();
        for (int c = 0; c < 4; c++)
            for (int r = 0; r < 4; r++)
                matrix.values[r][c] = A.values[0][r] * B.values[c][0] + A.values[1][r] * B.values[c][1] + A.values[2][r] * B.values[c][2] + A.values[3][r] * B.values[c][3];
        return matrix;
    }

    public static Matrix MulHuh(Matrix A, Matrix B)
    {
        Matrix C = new Matrix();
        for (int i = 0; i < 4; i++)
        {
            for (int j = 0; j < 4; j++)
            {
                double sum = 0;
                for(int k = 0; k < 4; k++)
                {
                    sum += B.values[i][k]*A.values[k][j];
                }
                C.values[i][j] = sum;
            }
        }
        return C;
    }

    public Matrix Inverse()
    {
        Matrix matrix = new Matrix();
        matrix.values[0][0] = values[0][0]; matrix.values[1][0] = values[0][1]; matrix.values[2][0] = values[0][2]; matrix.values[3][0] = 0.0f;
        matrix.values[0][1] = values[1][0]; matrix.values[1][1] = values[1][1]; matrix.values[2][1] = values[1][2]; matrix.values[3][1] = 0.0f;
        matrix.values[0][2] = values[2][0]; matrix.values[1][2] = values[2][1]; matrix.values[2][2] = values[2][2]; matrix.values[3][2] = 0.0f;
        matrix.values[0][3] = -(values[0][3] * matrix.values[0][0] + values[1][3] * matrix.values[0][1] + values[2][3] * matrix.values[0][2]);
        matrix.values[1][3] = -(values[0][3] * matrix.values[1][0] + values[1][3] * matrix.values[1][1] + values[2][3] * matrix.values[1][2]);
        matrix.values[2][3] = -(values[0][3] * matrix.values[2][0] + values[1][3] * matrix.values[2][1] + values[2][3] * matrix.values[2][2]);
        matrix.values[3][3] = 1.0f;
        return matrix;
    }
}
