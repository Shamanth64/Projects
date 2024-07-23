package com.nkm90.HearMeWhenYouCanNotSeeMe.basic;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.SurfaceTexture;
import android.os.Bundle;
import android.util.Log;
import android.util.Size;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.app.AppCompatActivity;
import com.nkm90.HearMeWhenYouCanNotSeeMe.R;
import com.google.mediapipe.components.CameraHelper;
import com.google.mediapipe.components.CameraXPreviewHelper;
import com.google.mediapipe.components.ExternalTextureConverter;
import com.google.mediapipe.components.FrameProcessor;
import com.google.mediapipe.components.PermissionHelper;
import com.google.mediapipe.framework.AndroidAssetUtil;
import com.google.mediapipe.glutil.EglManager;

public class BasicActivity extends AppCompatActivity {
  private static final String TAG = "MainActivity";
  private static final boolean FLIP_FRAMES_VERTICALLY = true;
  boolean first = true;

  static {
    System.loadLibrary("mediapipe_jni");
    try {
      System.loadLibrary("opencv_java3");
    } catch (UnsatisfiedLinkError e) {
      System.loadLibrary("opencv_java4");
    }
  }
  protected FrameProcessor processor;
  protected CameraXPreviewHelper cameraHelper;
  private SurfaceTexture previewFrameTexture;
  private SurfaceView previewDisplayView;
  private EglManager eglManager;
  private ExternalTextureConverter converter;
  private ApplicationInfo applicationInfo;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_mediapipe);

    try {
      applicationInfo =
          getPackageManager().getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA);
    } catch (NameNotFoundException e) {
      Log.e(TAG, "Cannot find application info: " + e);
    }

    previewDisplayView = new SurfaceView(this);
    setupPreviewDisplayView();
    AndroidAssetUtil.initializeNativeAssetManager(this);
    eglManager = new EglManager(null);
    processor =
        new FrameProcessor(
            this,
            eglManager.getNativeContext(),
            applicationInfo.metaData.getString("binaryGraphName"),
            applicationInfo.metaData.getString("inputVideoStreamName"),
            applicationInfo.metaData.getString("outputVideoStreamName"));

    processor
        .getVideoSurfaceOutput()
        .setFlipY(
            applicationInfo.metaData.getBoolean("flipFramesVertically", FLIP_FRAMES_VERTICALLY));

    PermissionHelper.checkAndRequestCameraPermissions(this);
  }
  @Override
  protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    Log.d("ActivityLifeCycle", "MediaPipe Activity - SaveInstanceState");
  }
  @Override
  protected void onStart()
  {
    super.onStart();
    Log.d("ActivityLifeCycle", "MediaPipe Activity - Start");
  }
  @Override
  protected void onRestart()
  {
    Log.d("ActivityLifeCycle", "MediaPipe Activity - Restart");
    super.onRestart();
  }
  @Override
  protected void onResume() {
    Log.d("ActivityLifeCycle", "MediaPipe Activity - Resume");
    super.onResume();
    converter = new ExternalTextureConverter(eglManager.getContext());
    converter.setFlipY(
            applicationInfo.metaData.getBoolean("flipFramesVertically", FLIP_FRAMES_VERTICALLY));
    converter.setConsumer(processor);
    if (PermissionHelper.cameraPermissionsGranted(this)) {
      startCamera();
    }
  }
  @Override
  protected void onPause() {
    Log.d("ActivityLifeCycle", "MediaPipe Activity - Pause");
    super.onPause();
  }
  @Override
  protected void onStop()
  {
    Log.d("ActivityLifeCycle", "MediaPipe Activity - Stop");
    super.onStop();
  }
  @Override
  protected void onDestroy()
  {
    Log.d("ActivityLifeCycle", "MediaPipe Activity - Destroy");
    super.onDestroy();
  }
  @Override
  public void onRequestPermissionsResult(
      int requestCode, String[] permissions, int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    PermissionHelper.onRequestPermissionsResult(requestCode, permissions, grantResults);
  }
  protected void onCameraStarted(SurfaceTexture surfaceTexture) {
    previewFrameTexture = surfaceTexture;
    previewDisplayView.setVisibility(View.VISIBLE);
  }
  protected Size cameraTargetResolution() {
    return null;
  }
  public void startCamera() {
    cameraHelper = new CameraXPreviewHelper();
    cameraHelper.setOnCameraStartedListener(
        surfaceTexture -> {
          onCameraStarted(surfaceTexture);
        });
    CameraHelper.CameraFacing cameraFacing =
        applicationInfo.metaData.getBoolean("cameraFacingFront", false)
            ? CameraHelper.CameraFacing.FRONT
            : CameraHelper.CameraFacing.BACK;
    if (first)
    {
      first = false;
      cameraHelper.startCamera(this, cameraFacing, null, cameraTargetResolution());
    }
  }
  protected Size computeViewSize(int width, int height) {
    return new Size(width, height);
  }

  protected void onPreviewDisplaySurfaceChanged(
      SurfaceHolder holder, int format, int width, int height) {
    Size viewSize = computeViewSize(width, height);
    Size displaySize = cameraHelper.computeDisplaySizeFromViewSize(viewSize);
    boolean isCameraRotated = cameraHelper.isCameraRotated();
    converter.setSurfaceTextureAndAttachToGLContext(
        previewFrameTexture,
        isCameraRotated ? displaySize.getHeight() : displaySize.getWidth(),
        isCameraRotated ? displaySize.getWidth() : displaySize.getHeight());
  }
  private void setupPreviewDisplayView() {
    previewDisplayView.setVisibility(View.GONE);
    ViewGroup viewGroup = findViewById(R.id.preview_display_layout);
    viewGroup.addView(previewDisplayView);
    previewDisplayView
        .getHolder()
        .addCallback(
            new SurfaceHolder.Callback() {
              @Override
              public void surfaceCreated(SurfaceHolder holder) {
                processor.getVideoSurfaceOutput().setSurface(holder.getSurface());
              }
              @Override
              public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                onPreviewDisplaySurfaceChanged(holder, format, width, height);
              }
              @Override
              public void surfaceDestroyed(SurfaceHolder holder) {
                processor.getVideoSurfaceOutput().setSurface(null);
              }
            });
  }
}
