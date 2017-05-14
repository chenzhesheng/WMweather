package weather.wm.com.wmweather.common.units;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CameraUtils {

	public static final int GET_IMAGE_BY_CAMERA = 5001;
	public static final int GET_IMAGE_FROM_PHONE = 6002;
	public static final int CROP_IMAGE = 5003;
	public static final int SELECT_CARD_FORWARD = 101;
	public static final int SELECT_CARD_BACK = 102;
	public static final int SELECT_CARD_LICENCE = 103;
	public static final String SELECT_TYPE = "selectType";

	public static Uri imageUriFromCamera;
	public static Uri cropImageUri;

	private static int selectedType = 0;

	public static void openCameraImage(final Activity activity) {
		CameraUtils.imageUriFromCamera = CameraUtils.createImagePathUri(activity);

		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		// MediaStore.EXTRA_OUTPUT参数不设置时,系统会自动生成一个uri,但是只会返回一个缩略图
		// 返回图片在onActivityResult中通过以下代码获取
		// Bitmap bitmap = (Bitmap) data.getExtras().get("data");
		// intent.putExtra(MediaStore.EXTRA_OUTPUT,
		// CameraUtils.imageUriFromCamera);
		activity.startActivityForResult(intent, CameraUtils.GET_IMAGE_BY_CAMERA);
	}

	public static void openLocalImage(final Activity activity) {
		Intent intent = new Intent();
		intent.setType("image/*");
		intent.setAction(Intent.ACTION_GET_CONTENT);
		activity.startActivityForResult(intent, CameraUtils.GET_IMAGE_FROM_PHONE);
	}

	/**
	 * 開店專用
	 * @param fg
	 */
	public static void openCameraImage(final Fragment fg) {
		CameraUtils.imageUriFromCamera = CameraUtils.createImagePathUri(fg
				.getActivity());
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		fg.startActivityForResult(intent, CameraUtils.GET_IMAGE_BY_CAMERA
				+ selectedType);
	}
	/**
	 * 開店專用
	 * @param fg
	 */
	public static void openLocalImage(final Fragment fg) {
		Intent intent = new Intent();
		intent.setType("image/*");
		intent.setAction(Intent.ACTION_GET_CONTENT);
		fg.startActivityForResult(intent, CameraUtils.GET_IMAGE_FROM_PHONE
				+ selectedType);
	}
	
	/**
	 * 通用
	 * @param fg
	 * @param b 隨便傳
	 */
	public static void openCameraImage(final Fragment fg,Boolean b) {
		CameraUtils.imageUriFromCamera = CameraUtils.createImagePathUri(fg
				.getActivity());
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		fg.startActivityForResult(intent, CameraUtils.GET_IMAGE_BY_CAMERA);
	}

	public static void openLocalImage(final Fragment fg,Boolean b) {
		Intent intent = new Intent();
		intent.setType("image/*");
		intent.setAction(Intent.ACTION_PICK);
		//跑下
		fg.startActivityForResult(intent, CameraUtils.GET_IMAGE_FROM_PHONE);
	}
	/** 
	 * 创建一条图片地址uri,用于保存拍照后的照片
	 * 
	 * @param context
	 * @return 图片的uri
	 */
	private static Uri createImagePathUri(Context context) {
		Uri imageFilePath = null;
		String status = Environment.getExternalStorageState();
		SimpleDateFormat timeFormatter = new SimpleDateFormat(
				"yyyyMMdd_HHmmss", Locale.CHINA);
		long time = System.currentTimeMillis();
		String imageName = timeFormatter.format(new Date(time));
		// ContentValues是我们希望这条记录被创建时包含的数据信息
		ContentValues values = new ContentValues(3);
		values.put(MediaStore.Images.Media.DISPLAY_NAME, imageName);
		values.put(MediaStore.Images.Media.DATE_TAKEN, time);
		values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
		if (status.equals(Environment.MEDIA_MOUNTED)) {// 判断是否有SD卡,优先使用SD卡存储,当没有SD卡时使用手机存储
			imageFilePath = context.getContentResolver().insert(
					MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
		} else {
			imageFilePath = context.getContentResolver().insert(
					MediaStore.Images.Media.INTERNAL_CONTENT_URI, values);
		}
		return imageFilePath;
	}

	/**
	 * @说明：选择图片
	 * @param acty
	 */
	public static void selectImage(final Activity acty) {
		String title = "获取图片方式";
		String[] choices = new String[] { "拍照", "从手机中选择" };

		new AlertDialog.Builder(acty).setTitle(title)
				.setItems(choices, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						switch (which) {
						case 0:
							CameraUtils.openCameraImage(acty);
							break;
						case 1:
							CameraUtils.openLocalImage(acty);
							break;
						}
					}
				}).setNegativeButton("返回", null).show();
	}

	public static void selectImage(final Fragment fg, int type) {
		selectedType = type;
		String title = "获取图片方式";
		String[] choices = new String[] { "拍照", "从手机中选择" };

		new AlertDialog.Builder(fg.getActivity()).setTitle(title)
				.setItems(choices, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						switch (which) {
						case 0:
							CameraUtils.openCameraImage(fg);
							break;
						case 1:
							CameraUtils.openLocalImage(fg);
							break;
						}
					}
				}).setNegativeButton("返回", null).show();
	}
	public static void selectImage(final Fragment fg) {
		String title = "获取图片方式";
		String[] choices = new String[] { "拍照", "从手机中选择" };

		new AlertDialog.Builder(fg.getActivity()).setTitle(title)
				.setItems(choices, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						switch (which) {
						case 0:
							CameraUtils.openCameraImage(fg,true);
							break;
						case 1:
							CameraUtils.openLocalImage(fg,true);
							break;
						}
					}
				}).setNegativeButton("返回", null).show();
	}
	/**
	 * @param contentUri
	 * @return
	 */
	public static String getRealPathFromURI(Uri contentUri, Activity actry) {
		String res = null;
		String[] proj = { MediaStore.Images.Media.DATA };
		Cursor cursor = actry.getContentResolver().query(contentUri, proj,
				null, null, null);
		if (cursor == null)
			return contentUri.getEncodedPath();
		if (cursor.moveToFirst()) {
			int column_index = cursor
					.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			res = cursor.getString(column_index);

		}
		cursor.close();
		return res;
	}

	// 计算图片的缩放值
	public static int calculateInSampleSize(BitmapFactory.Options options,
			int reqWidth, int reqHeight) {
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {
			final int heightRatio = Math.round((float) height
					/ (float) reqHeight);
			final int widthRatio = Math.round((float) width / (float) reqWidth);
			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
		}
		return inSampleSize;
	}

	// 根据路径获得图片并压缩，返回bitmap用于显示
	public static Bitmap getSmallBitmap(String filePath) {
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(filePath, options);

		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, 480, 800);

		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;

		return BitmapFactory.decodeFile(filePath, options);
	}

	public static String saveBitmap(String filePath) {
		try {
			Bitmap bm = getSmallBitmap(filePath);
			if(bm == null){
				Log.i("camere","null");
				return null;
			}
			// ByteArrayOutputStream baos = new ByteArrayOutputStream();
			// 以时间戳命名
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			String str = sdf.format(date);
			String imgPath = "/sdcard/" + str + ".jpg";

			File myCaptureFile = new File(imgPath);
			BufferedOutputStream bos = new BufferedOutputStream(
					new FileOutputStream(myCaptureFile));
			// 100表示不进行压缩，70表示压缩率为30%
			bm.compress(Bitmap.CompressFormat.JPEG, 10, bos);
			bos.flush();
			bos.close();
			return myCaptureFile.getPath();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressLint("SimpleDateFormat")
	public static String saveBitmap(Bitmap bm) {
		try {
			// 以时间戳命名
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			String str = sdf.format(date);
			String imgPath = "/sdcard/" + str + ".jpg";

			File myCaptureFile = new File(imgPath);
			BufferedOutputStream bos = new BufferedOutputStream(
					new FileOutputStream(myCaptureFile));
			// 100表示不进行压缩，70表示压缩率为30%
			bm.compress(Bitmap.CompressFormat.JPEG, 10, bos);
			bos.flush();
			bos.close();
			return myCaptureFile.getPath();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
