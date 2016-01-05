package com.werdpressed.partisan.autoexpandingappbar;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telecom.Call;
import android.util.AttributeSet;
import android.util.Log;

import java.lang.ref.WeakReference;

public class ScrollFeedbackRecyclerView extends RecyclerView {

    private WeakReference<Callbacks> mCallbacks;

    public ScrollFeedbackRecyclerView(Context context) {
        super(context);
        attachCallbacks(context);
    }

    public ScrollFeedbackRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        attachCallbacks(context);
    }

    @Override
    public void onScrolled(int dx, int dy) {
        if(((LinearLayoutManager)getLayoutManager()).findFirstCompletelyVisibleItemPosition() == 0) {
            Log.e(getClass().getSimpleName(), "index 0 visible");
            if(mCallbacks.get().isAppBarCollapsed()) {
                mCallbacks.get().setExpanded(true);
            }
        }
        super.onScrolled(dx, dy);
    }

    @Override
    public void setLayoutManager(LayoutManager layout) {
        if(!(layout instanceof LinearLayoutManager)) {
            throw new IllegalArgumentException(layout.toString() + " must be of type LinearLayoutManager");
        }
        super.setLayoutManager(layout);
    }

    private void attachCallbacks(Context context) {

        try {
            mCallbacks = new WeakReference<>((Callbacks)context);
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement " +
                    "ScrollFeedbackRecyclerView.Callbacks");
        }

    }

    interface Callbacks {

        boolean isAppBarCollapsed();
        void setExpanded(boolean expanded);

    }
}
