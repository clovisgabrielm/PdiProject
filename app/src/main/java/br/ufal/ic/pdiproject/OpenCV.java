package br.ufal.ic.pdiproject;

import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.core.MatOfFloat;
import org.opencv.core.MatOfInt;
import org.opencv.core.Point;
import org.opencv.core.Core;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.imgproc.Imgproc;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.MenuItem;

import java.util.List;
//import org.joda.time.DateTime;
//import org.joda.time.format.DateTimeFormat;

public class OpenCV extends Activity {
    public static final int      VIEW_MODE_RGBA      = 0;
    public static final int      VIEW_MODE_HIST      = 1;
    public static final int      VIEW_MODE_CANNY     = 2;
    public static final int      VIEW_MODE_SEPIA     = 3;
    public static final int      VIEW_MODE_SOBEL     = 4;
    public static final int      VIEW_MODE_ZOOM      = 5;
    public static final int      VIEW_MODE_PIXELIZE  = 6;
    public static final int      VIEW_MODE_POSTERIZE = 7;

    private Size                 mSize0;

    private Mat                  mIntermediateMat;
    private Mat                  mMat0;
    private MatOfInt             mChannels[];
    private MatOfInt             mHistSize;
    private int                  mHistSizeNum = 25;
    private MatOfFloat           mRanges;
    private Scalar               mColorsRGB[];
    private Scalar               mColorsHue[];
    private Scalar               mWhilte;
    private Point                mP1;
    private Point                mP2;
    private float                mBuff[];
    private Mat                  mSepiaKernel;

    public static int           viewMode = VIEW_MODE_RGBA;

    protected Bitmap imgConvert(Bitmap img) {
        Mat image = new Mat();
        Utils.bitmapToMat(img,image);
        Mat gray_image = new Mat();
        Size shrankSize = new Size();
        shrankSize.height = 1500;
        shrankSize.width = (float) shrankSize.height / image.height() * image.width();
        if(image.height() > 1500) { Imgproc.resize(image, image, shrankSize);}
        Imgproc.cvtColor(image,gray_image,Imgproc.COLOR_RGB2GRAY);
        Imgproc.threshold(gray_image,gray_image,127,255,0);
        Bitmap bmp = Bitmap.createBitmap(gray_image.width(),gray_image.height(), Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(gray_image,bmp);
        return bmp;
     }

    static public Bitmap getMini(Bitmap bmp,Integer x,Integer y,Integer w,Integer h) {
        Mat mat = new Mat();
        Bitmap bmp2 = Bitmap.createBitmap(w,h, Bitmap.Config.ARGB_8888);
        Utils.bitmapToMat(bmp,mat);
        Mat mat2 = mat.submat(mat.height()-(y+h),mat.height()-y,x,x+w);
        Utils.matToBitmap(mat2,bmp2);
        return bmp2;
    }

    static public Bitmap drawRect(Bitmap src, List<rectLetter> boxes,Integer offsetX,Integer offsetY){
        Mat matSrc = new Mat();
        Utils.bitmapToMat(src,matSrc);
        for(rectLetter box:boxes) {
            //Core.rectangle(matSrc,new Point(offsetX+box.x1,matSrc.height()-(offsetY +box.y1)), new Point(offsetX+box.x2,matSrc.height()-(offsetY+box.y2)),new Scalar(255,0,0),2);

        }
        Utils.matToBitmap(matSrc,src);
        return src;
    }


}
