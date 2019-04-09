package site.qinyong.citypicker;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.Random;

/**
 * Created by Administrator on 2019/4/9.
 */
public class IndexView extends View {
    private int itemWidth;
    private int itemHeight;

    private Paint paint;


    private String[] words = {"GSP","热门","全国","A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z"};

    public IndexView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setColor(Color.WHITE);//设置颜色
        paint.setAntiAlias(true);//设置抗锯齿
        paint.setTextSize(50);
        paint.setTypeface(Typeface.DEFAULT_BOLD);//设置粗体字
    }

    /**
     * 测量方法
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        itemWidth = getMeasuredWidth();
        itemHeight = getMeasuredHeight()/words.length;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for(int i = 0; i < words.length; i++) {

            Random random = new Random();
            int ranColor = 0xff000000 | random.nextInt(0x00ffffff);
            paint.setColor(ranColor);
            Rect r = new Rect();
            r.left = 0;
            r.top = itemHeight * i;
            r.right = itemWidth;
            r.bottom = itemHeight * (i + 1);
            canvas.drawRect(r, paint);


            if(touchIndex == i) {
                paint.setColor(Color.GRAY);
            } else {
                paint.setColor(Color.WHITE);
            }

            String word = words[i];
            Rect rect = new Rect();
            //从0到1取一个字母
            paint.getTextBounds(word, 0 , word.length(), rect);
            int wordWidth = rect.width();
            int wordHeight = rect.height();
            //计算每个字母在视图上的坐标位置
            float wordX = itemWidth/2 - wordWidth/2;
            float wordY = itemHeight/2 + i * itemHeight + wordHeight/2;
            //绘制
            canvas.drawText(word, wordX, wordY, paint);


        }
    }

    private int touchIndex = -1;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                float Y = event.getY();
                int index = (int) (Y/itemHeight);   //获取字母索引
                if(index != touchIndex && index >=0) {
                    touchIndex = index;
                    invalidate();       //强制绘制会调用onDraw
                    if (mOnIndexChangeListener != null && touchIndex < words.length) {
                        mOnIndexChangeListener.onIndexChange(words[touchIndex]);
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                touchIndex = -1;
                invalidate();
                break;
        }
        return true;
    }

    private OnIndexChangeListener mOnIndexChangeListener;
    /**
     * 字母下标索引变化的监听器
     */
    public  interface  OnIndexChangeListener {
        /**
         * 当字母下表位置发生变化的时候的回调
         * @param word 字母（A ~ Z）
         */
        void onIndexChange(String word);
    }

    /**
     * 设置字母下表索引变化的监听
     * @param onIndexChangeListener
     */
    public void setOnIndexChangeListener(OnIndexChangeListener onIndexChangeListener) {
        mOnIndexChangeListener = onIndexChangeListener;
    }
}
