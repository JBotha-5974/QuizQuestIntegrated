package wrrv.quizquest;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class EqualSpaceItem extends RecyclerView.ItemDecoration{
    private final int space;
    public EqualSpaceItem(int space){this.space = space;}
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                               RecyclerView.State state) {
        outRect.bottom = space;
        outRect.top = space;
        outRect.left = space;
        outRect.right = space;
    }
}
