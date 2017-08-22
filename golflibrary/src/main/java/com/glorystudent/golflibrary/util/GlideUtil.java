package com.glorystudent.golflibrary.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.glorystudent.golflibrary.R;


/**
 * Created by hyj on 2016/10/24.
 */
public class GlideUtil {
    /**
     * 加载圆形图片
     * @param context
     * @param url
     * @param imageView
     */
    public static void loadCircleImageView(final Context context, String url, final ImageView imageView){
        Glide.with(context).load(url).asBitmap().centerCrop()
                .placeholder(R.drawable.pic_placeholder)
                .into(new BitmapImageViewTarget(imageView) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                imageView.setImageDrawable(circularBitmapDrawable);
            }
        });
    }

    /**
     * 加载圆形图片
     * @param context
     * @param url
     * @param imageView
     */
    public static void loadCircleImageView(final Context context, String url, final ImageView imageView, int def){
        try {
            Glide.with(context).load(url).asBitmap().centerCrop()
                    .placeholder(def)
                    .into(new BitmapImageViewTarget(imageView) {
                        @Override
                        protected void setResource(Bitmap resource) {
                            RoundedBitmapDrawable circularBitmapDrawable =
                                    RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                            circularBitmapDrawable.setCircular(true);
                            imageView.setImageDrawable(circularBitmapDrawable);
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 加载圆角图片
     */
    public static void loadRoundImageView(final Context context, String url, final ImageView imageView,float r){
        Glide.with(context).load(url).diskCacheStrategy(DiskCacheStrategy.ALL)
                .transform(new CornersTransform(context, r))
                .placeholder(R.drawable.pic_placeholder)
                .into(imageView);
    }

    /**
     * 加载普通形状图片
     * @param context
     * @param url
     * @param imageView
     */
    public static void loadImageView(Context context, String url, ImageView imageView){
        Glide.with(context).load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.pic_placeholder)
                .into(imageView);
    }
    /**
     * 圆角效果的Transform
     * Created by Raye on 2016/5/10.
     */
    public static class CornersTransform  extends BitmapTransformation {
        private float radius;
        public CornersTransform(Context context) {
            super(context);
            radius = 10;
        }

        public CornersTransform(Context context,float radius){
            super(context);
            Log.d("print", "CornersTransform: -->"+radius);
            this.radius = radius;
        }

        @Override
        protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
            return cornersCrop(pool,toTransform);
        }
        private Bitmap cornersCrop(BitmapPool pool, Bitmap source) {
            if (source == null) return null;

            Bitmap result = pool.get(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
            if (result == null) {
                result = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
            }

            Canvas canvas = new Canvas(result);
            Paint paint = new Paint();
            paint.setShader(new BitmapShader(source, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
            paint.setAntiAlias(true);
            RectF rectF = new RectF(0f, 0f, source.getWidth(), source.getHeight());
            canvas.drawRoundRect(rectF, radius, radius, paint);
            return result;
        }

        @Override
        public String getId() {
            return getClass().getName();
        }
    }

}
