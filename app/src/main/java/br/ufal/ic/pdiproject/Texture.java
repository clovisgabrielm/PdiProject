package br.ufal.ic.pdiproject;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.TextureView;


import com.googlecode.tesseract.android.TessBaseAPI;

import java.io.IOException;

public class Texture extends Activity implements TextureView.SurfaceTextureListener {
    private Camera mCamera;
    public TextureView texture;
    static public Boolean killHandler=false;
    static public Boolean inUse = false;
    Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void run(TextureView myTexture){
        killHandler = false;
        texture = myTexture;
        texture.setSurfaceTextureListener(this);
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        Log.i("Err: ", "chegou aqui ## onSurfaceTextureAvailable");
        mCamera = Camera.open();

        Camera.Size previewSize = mCamera.getParameters().getPreviewSize();
        Camera.Parameters params = mCamera.getParameters();
        params.setFocusMode("continuous-picture");
        mCamera.setParameters(params);

        try {
            mCamera.setPreviewTexture(surface);
        } catch (IOException t) {
            Log.d("Err:","Cannot set Preview Texture");
        }

        mCamera.startPreview();
        texture.setRotation(90.0f);

        mHandler = new Handler();
        mHandler.postDelayed(getTextureBmap ,20);

    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        mCamera.stopPreview();
        mCamera.release();
        killHandler = true;
        MainActivity.killHandler=true;
        return true;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {

    }

    public Runnable getTextureBmap = new Runnable() {
        @Override
        public void run() {

            if(!inUse) {
                Log.i("Err: ", "chegou aqui ## getTextureBmap inuse");
                Bitmap bmp = texture.getBitmap();
                if(bmp==null){
                    killHandler=true;
                    MainActivity.killHandler=true;
                    return;
                }
                Log.i("Err: ", "chegou aqui ## getTextureBmap before opencv");

                OpenCV opencv = new OpenCV();
                Log.i("Err: ", "chegou aqui ## getTextureBmap after opencv");
                TessBaseAPI tess = new TessBaseAPI();
                Log.i("Err: ", "chegou aqui##  getTextureBmap after tess and opencv");

                Matrix matrix = new Matrix();
                matrix.postRotate(90);
                bmp = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), matrix, true);
                Log.i("Err: ", "chegou aquiiiii getTextureBmap after matrix");

                Bitmap processedBitmap = opencv.imgConvert(bmp);
                if (bmp != null) {
                    new ImagemProcess().execute(processedBitmap, bmp, tess);
                } else {
                    Log.d("runnable: ", "bitmap is null");
                }

                Log.i("Err: ", "chegou aqui ## getTextureBmap after Imgprocess");
            }
            if(killHandler == false) {
                mHandler.postDelayed(this, 20);
            }
            Log.i("Err: ", "chegou aqui ## getTextureBmap ends");
        }

    };

}