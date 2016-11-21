package com.dlut.justeda.classnote.note.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

import com.dlut.justeda.classnote.R;

/**
 * 笔记界面的自定义悬浮按钮
 * Created by 赵佳伟 on 2016/11/10.
 */
public class NoteArcMenu extends ViewGroup implements View.OnClickListener{

    private static final int POS_RIGHT_BOTTOM = 3;
    //菜单位置
    private Position mPosition = Position.RIGHT_BOTTOM;
    public enum Position
    {
        LEFT_TOP, LEFT_BOTTOM, RIGHT_TOP, RIGHT_BOTTOM
    }

    private int mRadius;
    //菜单的状态
    private Status mCurrentStatus = Status.CLOSE;
    public enum Status
    {
        OPEN, CLOSE
    }
    //菜单主按钮
    private View mCButton;
    //事件回掉接口
    private OnMenuItemClickListener mMenuItemClickListener;

    protected OnCreateNoteListener mOnCreateNoteListener;

    /**
     * 点击子菜单项的回调接口
     */
    public interface OnMenuItemClickListener {
        void onClick(View view, int pos);
    }

    /**
     * 单击加号建立一个文本笔记的回掉接口
     */
    public interface OnCreateNoteListener{
        void onCreateNoteClick();
    }

    public void setOnMenuItemClickListener(
            OnMenuItemClickListener mMenuItemClickListener) {
        this.mMenuItemClickListener = mMenuItemClickListener;
    }

    public void setOnCreateNoteListener(
            OnCreateNoteListener mOnCreateNoteListener){
        this.mOnCreateNoteListener = mOnCreateNoteListener;
    }

    public NoteArcMenu(Context context) {
        this(context, null);
    }
    public NoteArcMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NoteArcMenu(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        mRadius = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                100, getResources().getDisplayMetrics());

        // 获取自定义属性的值
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.ArcMenu, defStyle, 0);

        int pos = a.getInt(R.styleable.ArcMenu_position, POS_RIGHT_BOTTOM);
        switch (pos) {
            case POS_RIGHT_BOTTOM:
                mPosition = Position.RIGHT_BOTTOM;
                break;
        }
        mRadius = (int) a.getDimension(R.styleable.ArcMenu_radius, TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100,
                        getResources().getDisplayMetrics()));

        Log.e("TAG", "position = " + mPosition + " , radius =  " + mRadius);

        a.recycle();

    }

    /**
     * 设置子view的宽高
     * 设置自己的宽高
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        int count = getChildCount();
        for (int i = 0; i < count; i++)
        {
            // 测量child
            measureChild(getChildAt(i), widthMeasureSpec, heightMeasureSpec);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     *
     * @param changed
     * @param l
     * @param t
     * @param r
     * @param b
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b)
    {
        if (changed)
        {
            layoutCButton();

            int count = getChildCount();

            for (int i = 0; i < count - 1; i++)
            {
                View child = getChildAt(i + 1);

                child.setVisibility(View.GONE);

                int cl = (int) (mRadius * Math.sin(Math.PI / 2 / (count - 2)
                        * i));
                int ct = (int) (mRadius * Math.cos(Math.PI / 2 / (count - 2)
                        * i));

                int cWidth = child.getMeasuredWidth();
                int cHeight = child.getMeasuredHeight();

                // 如果菜单位置在底部 左下，右下
                if (mPosition == Position.LEFT_BOTTOM
                        || mPosition == Position.RIGHT_BOTTOM)
                {
                    ct = getMeasuredHeight() - cHeight - ct;
                }
                // 右上，右下
                if (mPosition == Position.RIGHT_TOP
                        || mPosition == Position.RIGHT_BOTTOM)
                {
                    cl = getMeasuredWidth() - cWidth - cl;
                }
                child.layout(cl, ct, cl + cWidth, ct + cHeight);

            }

        }

    }

    /**
     * 定位主菜单按钮及大小
     */
    private void layoutCButton()
    {
        mCButton = getChildAt(0);
        mCButton.setOnClickListener(this);

        int l = 0;
        int t = 0;

        int width = mCButton.getMeasuredWidth();
        int height = mCButton.getMeasuredHeight();

        switch (mPosition)
        {
            case RIGHT_BOTTOM:
                l = getMeasuredWidth() - width;
                t = getMeasuredHeight() - height;
                break;
        }
        mCButton.layout(l, t, l + width, t + width);
    }

    /**
     *主菜单的点击事件
     * @param v
     */
    @Override
    public void onClick(View v)
    {
        // mCButton = findViewById(R.id.id_button);
        // if(mCButton == null)
        // {
        // mCButton = getChildAt(0);
        // }

        //rotateCButton(v, 0f, 360f, 300);
        //所有动画响应200ms
        if (mOnCreateNoteListener != null) {
            mOnCreateNoteListener.onCreateNoteClick();
        }
    }

    public void onLongClick(View v){
        toggleMenu(250);
    }

    /**
     * 添加动画
     * @param duration
     */
    public void toggleMenu(int duration)
    {
        // 为menuItem添加平移动画和旋转动画
        int count = getChildCount();

        for (int i = 0; i < count - 1; i++)
        {
            final View childView = getChildAt(i + 1);
            childView.setVisibility(View.VISIBLE);

            // end 0 , 0
            // start
            int cl = (int) (mRadius * Math.sin(Math.PI / 2 / (count - 2) * i));
            int ct = (int) (mRadius * Math.cos(Math.PI / 2 / (count - 2) * i));

            int xflag = 1;
            int yflag = 1;

            AnimationSet animset = new AnimationSet(true);
            Animation tranAnim = null;

            // to open
            if (mCurrentStatus == Status.CLOSE)
            {
                tranAnim = new TranslateAnimation(xflag * cl, 0, yflag * ct, 0);
                childView.setClickable(true);
                childView.setFocusable(true);

            } else
            // to close
            {
                tranAnim = new TranslateAnimation(0, xflag * cl, 0, yflag * ct);
                childView.setClickable(false);
                childView.setFocusable(false);
            }
            tranAnim.setFillAfter(true);
            tranAnim.setDuration(duration);
            tranAnim.setStartOffset((i * 100) / count);

            tranAnim.setAnimationListener(new Animation.AnimationListener()
            {

                @Override
                public void onAnimationStart(Animation animation)
                {

                }

                @Override
                public void onAnimationRepeat(Animation animation)
                {

                }

                @Override
                public void onAnimationEnd(Animation animation)
                {
                    if (mCurrentStatus == Status.CLOSE)
                    {
                        childView.setVisibility(View.GONE);
                    }
                }
            });

            // 旋转动画
            RotateAnimation rotateAnim = new RotateAnimation(0, 720,
                    Animation.RELATIVE_TO_SELF, 0.5f,
                    Animation.RELATIVE_TO_SELF, 0.5f);
            rotateAnim.setDuration(duration);
            rotateAnim.setFillAfter(true);

            animset.addAnimation(rotateAnim);
            animset.addAnimation(tranAnim);
            childView.startAnimation(animset);

            final int pos = i + 1;
            //子控件的点击事件——用到接口回调机制
            childView.setOnClickListener(new OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    if (mMenuItemClickListener != null)
                        mMenuItemClickListener.onClick(childView, pos);

                    menuItemAnim(pos - 1);
                    changeStatus();

                }
            });
        }
        // 切换菜单状态
        changeStatus();
    }

    /**
     * 添加menuItem的点击动画
     *
     * @param pos
     */
    private void menuItemAnim(int pos)
    {
        for (int i = 0; i < getChildCount() - 1; i++)
        {

            View childView = getChildAt(i + 1);
            if (i == pos)
            {
                childView.startAnimation(scaleBigAnim(300));
            } else
            {

                childView.startAnimation(scaleSmallAnim(300));
            }

            childView.setClickable(false);
            childView.setFocusable(false);

        }

    }

    /**
     * 为未点击的item设置变小的和透明度降低的动画
     * @param duration
     * @return
     */
    private Animation scaleSmallAnim(int duration)
    {

        AnimationSet animationSet = new AnimationSet(true);

        ScaleAnimation scaleAnim = new ScaleAnimation(1.0f, 0.0f, 1.0f, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        AlphaAnimation alphaAnim = new AlphaAnimation(1f, 0.0f);
        animationSet.addAnimation(scaleAnim);
        animationSet.addAnimation(alphaAnim);
        animationSet.setDuration(duration);
        animationSet.setFillAfter(true);
        return animationSet;

    }

    /**
     * 为当前点击的Item设置变大和透明度降低的动画
     *
     * @param duration
     * @return
     */
    private Animation scaleBigAnim(int duration)
    {
        AnimationSet animationSet = new AnimationSet(true);

        ScaleAnimation scaleAnim = new ScaleAnimation(1.0f, 4.0f, 1.0f, 4.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        AlphaAnimation alphaAnim = new AlphaAnimation(1f, 0.0f);

        animationSet.addAnimation(scaleAnim);
        animationSet.addAnimation(alphaAnim);

        animationSet.setDuration(duration);
        animationSet.setFillAfter(true);
        return animationSet;
    }

    /**
     * 切换菜单状态
     */
    private void changeStatus()
    {
        mCurrentStatus = (mCurrentStatus == Status.CLOSE ? Status.OPEN
                : Status.CLOSE);
    }

    public boolean isOpen()
    {
        return mCurrentStatus == Status.OPEN;
    }

    /**
     * 为主菜单添加自身旋转动画——暂时取消使用
     * @param v
     * @param start
     * @param end
     * @param duration
     */
    private void rotateCButton(View v, float start, float end, int duration)
    {

        RotateAnimation anim = new RotateAnimation(start, end,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        anim.setDuration(duration);
        anim.setFillAfter(true);
        v.startAnimation(anim);
    }
}
