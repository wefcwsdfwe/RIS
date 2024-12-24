package index;

public class Index implements IIndex {

    private int index;

    public Index() {
        index = 0;
    }

    public int getIndex() {
        return index;
    }

    public void upIndex() {
        this.index++;
    }
}
