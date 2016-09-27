package cellsociety_team13;

public class Parameter {
    private String name;
    private int minVal, maxVal, currentVal;

    public Parameter(String pName, int min, int max) {
        name = pName;
        minVal = min;
        maxVal = max;
        currentVal = (minVal + maxVal) / 2;
    }

    public Parameter(String pName, int min, int max, int current) {
        this(pName, min, max);
        currentVal = current;
    }

    public String getName() {
        return name;
    }

    public int getMinVal() {
        return minVal;
    }

    public int getMaxVal() {
        return maxVal;
    }

    public int getCurrentVal() {
        return currentVal;
    }

    public void setCurrentVal(int val) {
        if (val < minVal || val > maxVal) {
            return;
        }
        currentVal = val;
    }

}
