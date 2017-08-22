package com.glorystudent.widget.residentview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.glorystudent.golflife.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/7/19.
 */

public class TestView extends View {
    private static final String TAG = "test";
    // TODO: 2017/7/20 绘画相关
    private Context context;
    private Paint paint;
    private Canvas mCanvas;
    private Bitmap mBitmap;//二级缓存

    // TODO: 2017/7/20 数据相关
    private List<BaseSpirit> mSpirit;
    private int width, height;//当前View高度，宽度
    private boolean isInit = false;//是否初始化过
    // TODO: 2017/7/19 拍照引导图
    private List<SpiritBitmapEntity> spiritBitmapEntityList;
    // TODO: 2017/7/19 当前选择模式
    private BaseSpirit.SpiritType drawType = BaseSpirit.SpiritType.SIDE;
    // TODO: 2017/7/20 交互数据
    private int position = -1;//当前选中的

    private Map<Integer, SpiritCircle> sidesSpirit;//存储侧面圆


    public TestView(Context context) {
        super(context);
        init(context);
    }

    public TestView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TestView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        mSpirit = new ArrayList<>();
        sidesSpirit = new ArrayMap<>();
        spiritBitmapEntityList = new ArrayList<>();
    }

    public List<BaseSpirit> getmSpirit() {
        return mSpirit;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
//        if(!isInit)
        initCanvas();


        //引导图
        for (SpiritBitmapEntity spiritBitmapEntity : spiritBitmapEntityList) {
            spiritBitmapEntity.onDraw(mCanvas, paint);
        }

        //各种其他元素
        for (BaseSpirit spirit : mSpirit) {
            spirit.onDraw(mCanvas, paint);
        }


        //圆圈
        for (Integer position : sidesSpirit.keySet()) {
            SpiritCircle sp = sidesSpirit.get(position);
            sp.onDraw(canvas, paint);
        }
        canvas.drawBitmap(mBitmap, 0, 0, paint);
        canvas.restore();
        Log.d(TAG, "onDraw: 引导图集合大小======" + spiritBitmapEntityList.size());
        Log.d(TAG, "onDraw: 画线图集合大小======" + mSpirit.size());
        Log.d(TAG, "onDraw: 圆圈图集合大小======" + sidesSpirit.size());
    }

    /**
     * 初始化画布
     */
    private void initCanvas() {

        width = getWidth();
        height = getHeight();
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.RED);

        //画布大小
        mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        mBitmap.eraseColor(Color.argb(0, 0, 0, 0));
        mCanvas = new Canvas(mBitmap);
        mCanvas.drawColor(Color.TRANSPARENT);

        if (!isInit)
            selectFrontOrSide(drawType);

        isInit = true;


    }

    /**
     * 选择侧面或者正面
     *
     * @param drawType
     */
    public void selectFrontOrSide(BaseSpirit.SpiritType drawType) {

        spiritBitmapEntityList.clear();
        sidesSpirit.clear();
        mSpirit.clear();
        this.drawType = drawType;
        Texture texture;
        int y = height - height / 10;
        SpiritBitmapEntity drawBitmapEntity;
        if (drawType == BaseSpirit.SpiritType.FRONT) {//正面
            texture = new Texture(context, R.drawable.icon_resident_front, width / 2, y);
            drawBitmapEntity = new SpiritBitmapEntity(texture, width / 4, height / 20);
        } else {
            texture = new Texture(context, R.drawable.icon_resident_sides, width / 2, y);
            drawBitmapEntity = new SpiritBitmapEntity(texture, width / 4, height / 20);
        }
        spiritBitmapEntityList.add(drawBitmapEntity);
        invalidate();

    }


    public void showLine() {
        Texture texture;
        if (drawType == BaseSpirit.SpiritType.FRONT) {//正面

        } else {
            texture = new Texture(width / 2, height);
            SpiritLineEntity lineEntity1 = new SpiritLineEntity(texture, width / 2, height - texture.getHeight());
            mSpirit.add(lineEntity1);
        }
    }

    public void showCirclesBySide() {
        sidesSpirit.clear();
        int y = height / 7;
        Texture texture = new Texture(5, 40);
        SpiritCircle spiritCircle1 = new SpiritCircle(texture, width / 2, y);
        SpiritCircle spiritCircle2 = new SpiritCircle(texture, width / 2, y * 2);
        SpiritCircle spiritCircle3 = new SpiritCircle(texture, width / 2, y * 3);
        SpiritCircle spiritCircle4 = new SpiritCircle(texture, width / 2, y * 4);
        SpiritCircle spiritCircle5 = new SpiritCircle(texture, width / 2, y * 5);

        sidesSpirit.put(0, spiritCircle1);
        sidesSpirit.put(1, spiritCircle2);
        sidesSpirit.put(2, spiritCircle3);
        sidesSpirit.put(3, spiritCircle4);
        sidesSpirit.put(4, spiritCircle5);

        Texture texture2 = new Texture(width, spiritCircle5.getY());
        SpiritLineEntity lineEntity2 = new SpiritLineEntity(texture2, spiritCircle5.getX(), spiritCircle5.getY());
        mSpirit.add(lineEntity2);

    }


    /**
     * 正面拍照规律
     * 1        3             （i+2）
     * 2        4
     * 3        5
     */
    public void showCirclesByFront() {
        sidesSpirit.clear();
        mSpirit.clear();
        Texture texture = new Texture(5, 40);
        int x = width / 4;
        int x2 = width - width / 4;
        int y = height / 6;
        SpiritCircle spiritCircle1 = new SpiritCircle(texture, x, y);
        SpiritCircle spiritCircle2 = new SpiritCircle(texture, x2, y);
        SpiritCircle spiritCircle3 = new SpiritCircle(texture, x, 2 * y);
        SpiritCircle spiritCircle4 = new SpiritCircle(texture, x2, 2 * y);
        SpiritCircle spiritCircle5 = new SpiritCircle(texture, x, 3 * y);
        SpiritCircle spiritCircle6 = new SpiritCircle(texture, x2, 3 * y);
        SpiritCircle spiritCircle7 = new SpiritCircle(texture, x, 4 * y);
        SpiritCircle spiritCircle8 = new SpiritCircle(texture, x2, 4 * y);


        sidesSpirit.put(0, spiritCircle1);
        sidesSpirit.put(1, spiritCircle2);
        sidesSpirit.put(2, spiritCircle3);
        sidesSpirit.put(3, spiritCircle4);
        sidesSpirit.put(4, spiritCircle5);
        sidesSpirit.put(5, spiritCircle6);
        sidesSpirit.put(6, spiritCircle7);
        sidesSpirit.put(7, spiritCircle8);


        //初始化划线
        for (Integer key : sidesSpirit.keySet()
                ) {
            lineToFront(key);
        }


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        PointF pointF = new PointF(event.getX(), event.getY());
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                findByPoint(pointF);
                break;
            case MotionEvent.ACTION_MOVE:
//                int position = findByPoint(pointF);
                if (position != -1) {
                    SpiritCircle spiritCircle = sidesSpirit.get(position);
                    if (spiritCircle != null) {
                        if (checkIsMove(spiritCircle, event)) {//检测移动边界
                            spiritCircle.moveTo(new PointF(event.getX(), event.getY()));
                            if (drawType == BaseSpirit.SpiritType.SIDE) {//侧面拍照
                                lineToSlide();
                            } else {//正面拍照
                                lineToFront(position);
                            }
                            invalidate();
                        }
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                position = -1;
                break;
        }
        return true;
    }

    /**
     * 检测是否在圆上
     *
     * @param pointF
     * @return
     */
    private int findByPoint(PointF pointF) {
        for (Integer position : sidesSpirit.keySet()) {
            SpiritCircle sp = sidesSpirit.get(position);
            if (sp.ifPointInShape(pointF)) {
                this.position = position;
                return position;
            }
        }
        return -1;
    }

    /**
     * 检测是否拖动边界
     *
     * @return
     */
    private boolean checkIsMove(SpiritCircle spiritCircle, MotionEvent event) {
        if ((event.getX() > spiritCircle.getHeight() && event.getX() < width - spiritCircle.getHeight()) && (event.getY() > spiritCircle.getHeight() && event.getY() < height - spiritCircle.getHeight()))
            return true;
        return false;
    }

    /**
     * 指向
     * 当前-1     ————→   当前
     * 当前       ————→   当前+1
     * 0     ————→   1
     */
    private void lineToSlide() {
        if (position != -1) {
            if (position == 0) {
                sidesSpirit.get(position).lineTo(null, new PointF(sidesSpirit.get(position + 1).getX(), sidesSpirit.get(position + 1).getY()));
            } else if (position == 4) {
                sidesSpirit.get(position - 1).lineTo(null, new PointF(sidesSpirit.get(position).getX(), sidesSpirit.get(position).getY()));
                mSpirit.get(0).moveTo(new PointF(sidesSpirit.get(position).getX(), sidesSpirit.get(position).getY()));
                mSpirit.get(1).moveTo(new PointF(sidesSpirit.get(position).getX(), sidesSpirit.get(position).getY()));
            } else {
                sidesSpirit.get(position).lineTo(null, new PointF(sidesSpirit.get(position + 1).getX(), sidesSpirit.get(position + 1).getY()));
                sidesSpirit.get(position - 1).lineTo(null, new PointF(sidesSpirit.get(position).getX(), sidesSpirit.get(position).getY()));
            }
        }
    }

    private void lineToFront(int position) {
        if (position != -1) {
            if ((position + 1) % 2 != 0) {//左侧
                sidesSpirit.get(position).lineTo(null, new PointF(sidesSpirit.get(position + 1).getX(), sidesSpirit.get(position + 1).getY()));
                sidesSpirit.get(position + 1).lineTo(null, new PointF(sidesSpirit.get(position).getX(), sidesSpirit.get(position).getY()));
            } else {
                sidesSpirit.get(position).lineTo(null, new PointF(sidesSpirit.get(position - 1).getX(), sidesSpirit.get(position - 1).getY()));
                sidesSpirit.get(position - 1).lineTo(null, new PointF(sidesSpirit.get(position).getX(), sidesSpirit.get(position).getY()));
            }
        }
    }


    /**
     * 隐藏引导图，显示标注
     */
    public void takePicture() {

        mSpirit.clear();
        spiritBitmapEntityList.clear();
        sidesSpirit.clear();
        if (drawType == BaseSpirit.SpiritType.FRONT) {//正面
            showCirclesByFront();
        } else {
            showCirclesBySide();
            showLine();

        }
    }


    public void setDrawType(BaseSpirit.SpiritType drawType) {
        this.drawType = drawType;
    }


    /**
     * 是否有外面大圆
     *
     * @param visible
     */
    public void disBigCircle(int visible) {
        for (Integer position : sidesSpirit.keySet()
                ) {
            SpiritCircle spiritCircle = sidesSpirit.get(position);
            spiritCircle.setVisible(visible);
        }
        invalidate();
    }
}
