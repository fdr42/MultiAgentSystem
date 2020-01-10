package translator;

public class Constraint
{
    private int v1;

    private int v2;

    private boolean isDifference;

    private int value;

    private int cost;

    public int getV1()
    {
        return v1;
    }

    public void setV1(int v1)
    {
        this.v1 = v1;
    }

    public int getV2()
    {
        return v2;
    }

    public void setV2(int v2)
    {
        this.v2 = v2;
    }

    public boolean isDifference()
    {
        return isDifference;
    }

    public void setDifference(boolean difference)
    {
        this.isDifference = difference;
    }

    public int getValue()
    {
        return value;
    }

    public void setValue(int value)
    {
        this.value = value;
    }

    public int getCost()
    {
        return cost;
    }

    public void setCost(int cost)
    {
        this.cost = cost;
    }
}