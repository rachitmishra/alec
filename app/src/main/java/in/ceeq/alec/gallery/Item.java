package in.ceeq.alec.gallery;

public class Item {

    private String mTitle;
    private int mDrawableRes;

    public Item(int drawable, String title) {
        mDrawableRes = drawable;
        mTitle = title;
    }

    public int getDrawableResource() {
        return mDrawableRes;
    }

    public String getTitle() {
        return mTitle;
    }
}
