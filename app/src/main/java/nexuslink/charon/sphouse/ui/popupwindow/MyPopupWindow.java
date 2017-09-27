package nexuslink.charon.sphouse.ui.popupwindow;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.io.File;

import nexuslink.charon.sphouse.R;
import nexuslink.charon.sphouse.ui.activities.MainActivity;

import static nexuslink.charon.sphouse.config.Constant.CAMERA;
import static nexuslink.charon.sphouse.config.Constant.DOG_IMAGE_0;
import static nexuslink.charon.sphouse.config.Constant.DOG_IMAGE_1;
import static nexuslink.charon.sphouse.config.Constant.PHONE;

/**
 * 项目名称：SPHouse
 * 类描述：
 * 创建人：Charon
 * 创建时间：2017/9/27 10:40
 * 修改人：Charon
 * 修改时间：2017/9/27 10:40
 * 修改备注：
 */

public class MyPopupWindow extends PopupWindow implements View.OnClickListener {

    private int type;
    private Activity activity;

    private File file;
    private Uri ImgUri;

    private TextView mTakePhoto, mAlbumPhoto, mCancel;

    public MyPopupWindow(Context context, Activity activity) {
        initView(context);
        this.activity = activity;
    }

    private void initView(Context mContext) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.popup_main,
                null);
        setContentView(v);

        mTakePhoto = (TextView) v.findViewById(R.id.photo_take);
        mAlbumPhoto = (TextView) v.findViewById(R.id.photo_album);
        mCancel = (TextView) v.findViewById(R.id.photo_cancel);

        mTakePhoto.setOnClickListener(this);
        mAlbumPhoto.setOnClickListener(this);
        mCancel.setOnClickListener(this);

        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);

        // 设置SelectPicPopupWindow弹出窗体可点�?
        this.setTouchable(true);
        this.setFocusable(true);
        this.setOutsideTouchable(true);

        // 刷新状�?
        this.update();
        // 设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.popupwindow_from_bottom);
        // 实例化一个ColorDrawable颜色为半透明
        //ColorDrawable dw = new ColorDrawable(0xffffff);
        // 设置SelectPicPopupWindow弹出窗体的背景
        //this.setBackgroundDrawable(dw);
    }

    public void showPopupWindow(View parent) {

        if (!this.isShowing()) {
            this.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
        } else {
            this.dismiss();
        }
    }

    @Override
    public void onClick(View arg0) {
        switch (arg0.getId()) {
            case R.id.photo_take:
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (MainActivity.mCurrentPager == 0)
                    //文件路径
                    file = new File(Environment.getExternalStorageDirectory(),
                            DOG_IMAGE_0);
                else file = new File(Environment.getExternalStorageDirectory(),
                        DOG_IMAGE_1);

                Log.d("123", "file" + file.getPath());
                ImgUri = Uri.fromFile(file);
                intent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, ImgUri);
                Log.d("123", "take photo");
                activity.startActivityForResult(intent, 1);
                Log.d("123", "take photo");
                type = CAMERA;
                if (listener != null) {
                    listener.getType(type);
                    listener.getImgUri(ImgUri, file);
                }

                this.dismiss();
                break;
            case R.id.photo_album:
                Intent intent2 = new Intent("android.intent.action.PICK");
                intent2.setType("image/*");
                Log.d("123", "album");
                activity.startActivityForResult(intent2, 2);
                Log.d("123", "album");
                type = PHONE;
                if (listener != null) {
                    listener.getType(type);
                }
                this.dismiss();
                break;
            case R.id.photo_cancel:
                this.dismiss();
                break;
            default:
                break;
        }
    }

    public void onPhoto(Uri uri, int outputX, int outputY) {
        Intent intent = null;

        intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");

        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 13);
        intent.putExtra("aspectY", 10);
        intent.putExtra("outputX", outputX);
        intent.putExtra("outputY", outputY);
        intent.putExtra("scale", true);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        intent.putExtra("return-data", false);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true); // no face detection
        activity.startActivityForResult(intent, 3);
    }

    public interface onGetTypeClickListener {
        void getType(int type);

        void getImgUri(Uri imgUri, File file);
    }

    private onGetTypeClickListener listener;

    public void setOnGetTypeClickListener(onGetTypeClickListener listener) {
        this.listener = listener;
    }
}
