package com.hdmb.ireader.txtreader;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Toast;

import com.hdmb.ireader.txtreader.bean.Page;
import com.hdmb.ireader.txtreader.bean.TxtError;
import com.hdmb.ireader.txtreader.pipeline.ModeTransformer;

/**
 * 作者    武博
 * 时间    2016/8/5 0005 22:00
 * 文件    TbReader
 * 描述
 */
public class TxtReader extends View {
    public static final int PAGE_NONE = 0x00;
    private static final int PAGE_NEXT = 0x02;
    private static final int PAGE_PREV = 0x01;

    /**
     * 页面拖动状态
     */
    private int PAGE_DRAG_STATE = PAGE_NONE;
    /**
     * 阴影宽度
     */
    private int shadowWidth;
    private TxtManager manager;
    private TxtModel model;
    /**
     * 阴影画笔
     */
    private Paint shadowPaint;

    private Bitmap prevDrawBitmap;
    private Bitmap behiDrawBitmap;

    private float dividerPosition;
    private float startClickX, startMoveX;// 点击时最开始位置、移动时最开始位置

    private Boolean isOnAnimation = false;// 是否在执行动画
    private Boolean isAllowTounch = false;// 是否允许tounch事件
    private Boolean hasShowMenu = false;
    private Boolean isFirstPage = true;// 是否是在第一页
    private Boolean isLastPage = false;// 是否在最后一页
    private Boolean isActionDownDone = false;
    private Boolean isShowShadow = true;
    private int pageindex = 1;
    private TxtPageChangeListsner txtPageChangeListsner;
    private TxtViewOnTouchListener onTouchListener;
    private PageSeparateListener pageSeparateListener;
    private OnPartChangeListener partChangeListener;

    public TxtReader(Context context) {
        super(context);
    }

    public TxtReader(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TxtReader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public TxtManager getTxtManager() {
        if (manager == null) {
            throw new NullPointerException("没有传入txtmanager----");
        }
        return manager;
    }

    public void setTxtManager(TxtManager txtManager) {
        this.manager = txtManager;
        init();
    }

    public void init() {
        shadowPaint = new Paint();
        manager = getTxtManager();
        shadowWidth = 5;
        model = new TxtModel(getContext(), manager, manager.getTxtFile());
        model.setModelTransformer(new ModeTransformer() {
            @Override
            public void postResult(Boolean t) {

            }

            @Override
            public void postError(TxtError txterror) {

            }

            @Override
            public void refreshView() {
                if (PAGE_DRAG_STATE == PAGE_PREV) {
                    onLoadPrevPage(isFirstPage);
                } else if (PAGE_DRAG_STATE == PAGE_NEXT) {
                    onLoadNextPage(isLastPage);
                } else {
                    onLoadFirstPage(isLastPage);
                }
            }

            @Override
            public void onLoadFirstPage(boolean isLast) {
                prevDrawBitmap = model.getBitmapCache().getMidBitmap();
                behiDrawBitmap = model.getBitmapCache().getNextBitmap();
                dividerPosition = getViewWith();
                PAGE_DRAG_STATE = PAGE_NONE;
                isFirstPage = true;
                isLastPage = isLast;
                isShowShadow = !isLastPage;
                postInvalidate();
            }

            @Override
            public void onLoadNextPage(boolean isLast) {
                prevDrawBitmap = model.getBitmapCache().getPrevBitmap();
                behiDrawBitmap = model.getBitmapCache().getMidBitmap();
                isFirstPage = false;
                dividerPosition = 0;
                isLastPage = isLast;
                isShowShadow = !isLastPage;
                postInvalidate();
            }

            @Override
            public void onLoadPrevPage(boolean isFirst) {
                prevDrawBitmap = model.getBitmapCache().getPrevBitmap();
                behiDrawBitmap = model.getBitmapCache().getMidBitmap();
                isFirstPage = isFirst;
                isLastPage = false;
                dividerPosition = 0;
                postInvalidate();
            }

            @Override
            public void onLoadPageFromIndex(boolean isFirst, boolean isLast) {
                prevDrawBitmap = model.getBitmapCache().getMidBitmap();
                behiDrawBitmap = model.getBitmapCache().getNextBitmap();
                dividerPosition = getViewWith();
                PAGE_DRAG_STATE = PAGE_NONE;
                isFirstPage = isFirst;
                isLastPage = isLast;
                isShowShadow = !isLastPage;
                postInvalidate();
            }

            @Override
            public void onLoadFileExecption() {
                isFirstPage = true;
                isLastPage = true;
                isShowShadow = false;
            }

            @Override
            public void onNoData() {
                isFirstPage = true;
                isLastPage = true;
                isShowShadow = false;
            }

            @Override
            public void onPageSeparateStart() {
                if (pageSeparateListener != null) {
                    pageSeparateListener.onSeparateStart();
                }
            }

            @Override
            public void onPageSeparateDone() {
                if (pageSeparateListener != null) {
                    pageSeparateListener.onSeparateDone();
                }
                if (txtPageChangeListsner != null) {
                    txtPageChangeListsner.onCurrentPage(pageindex, model.getPageNums());
                }
            }
        });
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Bitmap preBitmap = getPreBrawPageBitmap();

        if (preBitmap != null) {
            Rect srcRect = new Rect(0, 0, getViewWith(), preBitmap.getHeight());// 截取bmp1中的矩形区域
            Rect dstRect = new Rect(0, 0, getViewWith(), getViewHeigh());// bmp1在目标画布中的位置
            canvas.drawBitmap(preBitmap, srcRect, dstRect, getTextPaint());
            canvas.save();
        }

        if (isShowShadow) {
            int left = (int) (dividerPosition - shadowWidth);
            left = left > 0 ? left : 0;
            int top = 0;
            int right = (int) dividerPosition;
            int bottom = getViewHeigh();
            canvas.drawRect(left, top, right, bottom, getBgLightPaint());
            canvas.save();
        }
        Bitmap behideBitmap = getBehideBrawPageBitmap();

        if (behideBitmap != null) {
            int behi_x = 0;
            int behi_y = 0;

            float behi_w = getViewWith() - dividerPosition;

            int behi_h = behideBitmap.getHeight();
            Rect srcRect1 = new Rect(behi_x, behi_y, (int) behi_w, behi_h);// 截取bmp1中的矩形区域

            float behi_leftX = dividerPosition;
            int behi_rigthX = getViewWith();

            Rect dstRect1 = new Rect((int) behi_leftX, 0, behi_rigthX, getViewHeigh());// bmp1在目标画布中的位置

            canvas.drawBitmap(behideBitmap, srcRect1, dstRect1, getTextPaint());
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 执行动画过程中不允许手势操作
        if (isOnAnimation) {
            return true;
        }
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!hasShowMenu) {
                    if (inShowMeneArea(x, y)) {
                        if (onTouchListener != null) {
                            onTouchListener.onShowMenu();
                            hasShowMenu = true;
                            return true;
                        }
                    }
                } else {
                    if (onTouchListener != null) {
                        onTouchListener.onOutSideAreaTouch();
                        hasShowMenu = false;
                        return true;
                    }
                }
                startClickX = x;
                startMoveX = x;
                isActionDownDone = true;
                PAGE_DRAG_STATE = PAGE_NONE;
                break;
            case MotionEvent.ACTION_MOVE:
                if (!hasShowMenu && isActionDownDone && !isAllowTounch) {
                    doMove(event);
                }
                break;
            case MotionEvent.ACTION_UP:
                startClickX = startMoveX;
                if (!hasShowMenu && isActionDownDone && !isAllowTounch) {
                    doActionUp();
                    isActionDownDone = false;
                }
                break;
            default:
                break;
        }
        return true;
    }

    public Bitmap getPageBitmap() {
        return model.getBitmapCache().getPageBitmap();
    }

    public Paint getTextPaint() {
        return getTxtManager().getTextPaint();
    }

    private Paint getBgLightPaint() {
        if (shadowPaint == null)
            shadowPaint = new Paint();
        // int shadowwith1 = shadowWidth;
        LinearGradient gradient = new LinearGradient(dividerPosition - shadowWidth, 0, dividerPosition, 0,
                new int[]{Color.parseColor("#00666666"), Color.parseColor("#11666666"), Color.parseColor("#33666666"),
                        Color.parseColor("#44666666"), Color.parseColor("#88666666"), Color.parseColor("#ee666666")},
                null, LinearGradient.TileMode.CLAMP);
        shadowPaint.setShader(gradient);
        return shadowPaint;
    }

    private Bitmap getBehideBrawPageBitmap() {
        return behiDrawBitmap;
    }

    private Bitmap getPreBrawPageBitmap() {
        return prevDrawBitmap;
    }

    private void doActionUp() {
        switch (PAGE_DRAG_STATE) {
            case PAGE_PREV:
                if (!isFirstPage) {
                    DoPageUpAnimation();
                } else {
                    if (manager.hasPrevPart()) {
                        if (partChangeListener != null)
                            partChangeListener.onPartChange(manager.getPart() - 1);
                        else
                            Toast.makeText(getContext(), "已经是第一节第一页了！", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case PAGE_NEXT:
                if (!isLastPage) {
                    DoPageDownAnimation();
                } else {
                    if (manager.hasNextPart()) {
                        if (partChangeListener != null)
                            partChangeListener.onPartChange(manager.getPart() + 1);
                        else
                            Toast.makeText(getContext(), "已经是最后一节最后一页了！", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            default:
                if (startClickX < getViewWith() / 2) {// 执行上一页点击事件
                    if (isFirstPage) {
//                        if (dividerPosition >= getViewWith()) {
//                            dividerPosition = 0;
//                            justslidtoleft();
//                        }
//                        DoPageUpAnimation();
//                    } else {
                        if (manager.hasPrevPart()) {
                            if (partChangeListener != null)
                                partChangeListener.onPartChange(manager.getPart() - 1);
                            else
                                Toast.makeText(getContext(), "已经是第一节第一页了！", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {// 下一页点击事件
                    if (isLastPage) {
//                        if (dividerPosition <= 0) {
//                            dividerPosition = getViewWith();
//                            justslidtoright();
//                        }
//                        DoPageDownAnimation();
//                    } else {
                        if (manager.hasNextPart()) {
                            if (partChangeListener != null)
                                partChangeListener.onPartChange(manager.getPart() + 1);
                            else
                                Toast.makeText(getContext(), "已经是最后一节最后一页了！", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                break;
        }
    }

    private void doMove(MotionEvent event) {
        float movex = event.getX() - startMoveX;
        startMoveX = event.getX();
        if (PAGE_DRAG_STATE == PAGE_PREV && startMoveX < startClickX) {
            PAGE_DRAG_STATE = PAGE_NONE;
            return;
        }

        if (PAGE_DRAG_STATE == PAGE_NEXT && startMoveX > startClickX) {
            PAGE_DRAG_STATE = PAGE_NONE;
            return;
        }

        if (PAGE_DRAG_STATE == PAGE_NONE) {
            if (event.getX() - startClickX > 1) {// 右滑
                // ，就是翻上一页,大于-5或者小于-5是为了避免只是点击而不滑动的情况
                PAGE_DRAG_STATE = PAGE_PREV;
            } else if (event.getX() - startClickX < -1) {
                PAGE_DRAG_STATE = PAGE_NEXT;
                // 如果分界线在最左，还是进行右移动，那么就是执行滑动到上一页状态
            } else {
                PAGE_DRAG_STATE = PAGE_NONE;
            }
//            if (event.getX() - startMoveX >1){// 右滑
//                PAGE_DRAG_STATE = PAGE_PREV;
//            } else if (event.getX() - startMoveX <-1) {
//                PAGE_DRAG_STATE = PAGE_NEXT;
//            } else {
//                PAGE_DRAG_STATE = PAGE_NONE;
//            }
//            if (movex > 0) {// 右滑
//                PAGE_DRAG_STATE = PAGE_PREV;
//            } else if (movex < 0) {
//                PAGE_DRAG_STATE = PAGE_NEXT;
//            } else {
//                PAGE_DRAG_STATE = PAGE_NONE;
//            }
        }

        if ((PAGE_DRAG_STATE == PAGE_PREV || PAGE_DRAG_STATE == PAGE_NONE) && isFirstPage) {// 第一页不允许执行前一页操作
            PAGE_DRAG_STATE = PAGE_NONE;
            System.out.println("this is the firstpage");
            return;
        }

        if ((PAGE_DRAG_STATE == PAGE_NEXT || PAGE_DRAG_STATE == PAGE_NONE) && isLastPage) {// 最好一页不允许执行下一页操作
            PAGE_DRAG_STATE = PAGE_NONE;
            System.out.println("this is the lastpage");
            return;
        }

        // 如果状态为执行下一页时，通过边界判断来改变页面，并且改变边界，确保 movex < 0，就是确定执行的是
        // 执行下一页操作，因为有种情况是忽然改变手势方向，就是movex可能为>0,这样画面就变了
        if (PAGE_DRAG_STATE == PAGE_NEXT && dividerPosition <= 0 && movex < 0) {
            dividerPosition = getViewWith();
            justslidtoright();
            return;
        }

        if (PAGE_DRAG_STATE == PAGE_PREV && dividerPosition >= getViewWith() && movex > 0) {
            dividerPosition = 0;
            justslidtoleft();
            return;
        }

        dividerPosition = (int) (dividerPosition + movex);
        dividerPosition = dividerPosition <= 0 ? 0 : dividerPosition;
        dividerPosition = dividerPosition >= getViewWith() ? getViewWith() : dividerPosition;
        postInvalidate();
    }

    /**
     * TODO执行显示切换上一页 下午2:40:03
     */
    private void justslidtoleft() {
        prevDrawBitmap = model.getBitmapCache().getPrevBitmap();
        behiDrawBitmap = model.getBitmapCache().getMidBitmap();
    }

    /**
     * TODO 执行显示切换下一页 下午2:39:14
     */
    private void justslidtoright() {
        prevDrawBitmap = model.getBitmapCache().getMidBitmap();
        behiDrawBitmap = model.getBitmapCache().getNextBitmap();

    }

    private void DoPageDownAnimation() {
        isOnAnimation = true;

        ValueAnimator animator = ValueAnimator.ofFloat(0, 1);
        animator.setDuration(500);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener((ValueAnimator animation) -> {
            float f = 1 - animation.getAnimatedFraction();
            float p = dividerPosition * f;

            if (p > 0) {
                dividerPosition = (int) p;
                postInvalidate();
            } else {
                dividerPosition = 0;
                postInvalidate();

                animation.cancel();
                PAGE_DRAG_STATE = PAGE_NONE;
                if (!isLastPage) {
                    model.loadNextPage();
                }

                if (txtPageChangeListsner != null) {
                    Page midpage = model.getMiddPage();
                    pageindex = midpage == null ? 0 : midpage.getPageIndex();
                    txtPageChangeListsner.onCurrentPage(pageindex, model.getPageNums());
                }
                isOnAnimation = false;
            }
        });
        animator.start();
    }

    private void DoPageUpAnimation() {
        isOnAnimation = true;
        ValueAnimator animator = ValueAnimator.ofFloat(0, 1);
        animator.setDuration(500);
        animator.setInterpolator(new LinearInterpolator());
        final float leftwith = getViewWith() - dividerPosition;
        animator.addUpdateListener((ValueAnimator animation) -> {
            float f = animation.getAnimatedFraction();
            float ls = leftwith * f;
            if (dividerPosition < getViewWith() - 5) {
                dividerPosition = dividerPosition + ls;
                postInvalidate();
            } else {
                dividerPosition = getViewWith();
                postInvalidate();
                animation.cancel();
                PAGE_DRAG_STATE = PAGE_NONE;
                if (!isFirstPage) {
                    model.loadPrevPage();
                }
                if (txtPageChangeListsner != null) {
                    Page predpage = model.getPrevPage();
                    pageindex = predpage == null ? 1 : predpage.getPageIndex();
                    txtPageChangeListsner.onCurrentPage(pageindex, model.getPageNums());
                }
                isOnAnimation = false;
            }
        });
        animator.start();
    }

    private boolean inShowMeneArea(float x, float y) {
        return InXArea(x) && InYArea(y);
    }

    private boolean InYArea(float yposition) {
        return yposition > getBorderTop() && yposition < getBorderBottom();
    }

    private boolean InXArea(float xposition) {
        return xposition > getBordeleft() && xposition < getBorderRight();
    }

    private int getBorderBottom() {
        return getViewHeigh() * 3 / 5;
    }

    private int getBorderTop() {
        return getViewHeigh() * 2 / 5;
    }

    private float getBorderRight() {
        return getViewWith() * 3 / 5 + 5;
    }

    private float getBordeleft() {
        return getViewWith() * 2 / 5 - 5;
    }

    private int getViewWith() {
        if (manager != null)
            return manager.getWidth();
        return 0;
    }

    private int getViewHeigh() {
        if (manager != null)
            return manager.getHeight();
        return 0;
    }

    public void setTxtPageChangeListsner(TxtPageChangeListsner listener) {
        this.txtPageChangeListsner = listener;
    }

    public void setTxtViewOnTouchListener(TxtViewOnTouchListener listener) {
        this.onTouchListener = listener;
    }

    public void setPageSeparateListener(PageSeparateListener listener) {
        this.pageSeparateListener = listener;
    }

    public void setOnPartChangeListener(OnPartChangeListener listener) {
        this.partChangeListener = listener;
    }
}
