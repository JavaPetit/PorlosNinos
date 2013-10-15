package pe.javapetit.apps.porlosNinos.gui.control;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created with IntelliJ IDEA.
 * User: JavaPetit
 * Date: 07/10/13
 * Time: 10:40 PM
 * To change this template use File | Settings | File Templates.
 */
public class NoScrollListView extends ListView {

    public NoScrollListView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(MeasureSpec.UNSPECIFIED, 0));

        // here I assume that height's being calculated for one-child only, seen it in ListView's source which is actually a bad idea
        int childHeight = getMeasuredHeight() - (getListPaddingTop() + getListPaddingBottom() + getVerticalFadingEdgeLength() * 2);

        int fullHeight = getListPaddingTop() + getListPaddingBottom() + childHeight * (getCount());

        setMeasuredDimension(getMeasuredWidth(), fullHeight);
    }
}
