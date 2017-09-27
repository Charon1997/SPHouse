package nexuslink.charon.sphouse.config;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListenerAdapter;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationSet;

/**
 * 项目名称：SPHouse
 * 类描述：
 * 创建人：Charon
 * 创建时间：2017/8/31 11:47
 * 修改人：Charon
 * 修改时间：2017/8/31 11:47
 * 修改备注：
 */

public class ScrollAwareFABBehaviorDefault extends FloatingActionButton.Behavior {
    private static final String TAG = ScrollAwareFABBehaviorDefault.class.getSimpleName();

    public ScrollAwareFABBehaviorDefault(Context context, AttributeSet attrs) {
        super();
    }

    boolean isAnimatingOut = false;
    private FastOutLinearInInterpolator folistener = new FastOutLinearInInterpolator();


    @Override
    public boolean onStartNestedScroll(final CoordinatorLayout coordinatorLayout, final FloatingActionButton child,
                                       final View directTargetChild, final View target, final int nestedScrollAxes) {
        // Ensure we react to vertical scrolling
        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL
                || super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, nestedScrollAxes);
    }

    @Override
    public void onNestedScroll(final CoordinatorLayout coordinatorLayout, final FloatingActionButton child,
                               final View target, final int dxConsumed, final int dyConsumed,
                               final int dxUnconsumed, final int dyUnconsumed) {
        //super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
        if (dyConsumed > 0 && !isAnimatingOut) {
            // User scrolled down and the FAB is currently visible -> hide the FAB
            //child.hide();
            ObjectAnimator ob1 = ObjectAnimator.ofFloat(child, "ScaleX", 1.0f, 0.0f);
            ObjectAnimator ob2 = ObjectAnimator.ofFloat(child, "ScaleY", 1.0f, 0.0f);
            ObjectAnimator ob3 = ObjectAnimator.ofFloat(child, "Alpha", 1.0f, 0.0f);
            AnimatorSet set = new AnimatorSet();
            set.playTogether(ob1, ob2, ob3);
            set.setDuration(300);
            isAnimatingOut = true;
            set.start();
        } else if (dyConsumed < 0 && isAnimatingOut) {
            // User scrolled up and the FAB is currently not visible -> show the FAB
            ObjectAnimator ob1 = ObjectAnimator.ofFloat(child, "ScaleX", 0.0f, 1.0f);
            ObjectAnimator ob2 = ObjectAnimator.ofFloat(child, "ScaleY", 0.0f, 1.0f);
            ObjectAnimator ob3 = ObjectAnimator.ofFloat(child, "Alpha", 0.0f, 1.0f);
            AnimatorSet set = new AnimatorSet();
            set.playTogether(ob1, ob2, ob3);
            set.setDuration(300);
            isAnimatingOut = false;
            set.start();
            //child.show();
        }


    }


}
