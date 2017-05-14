package com.example.jikeu.base.utils;

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

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ImageUtils {

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
		ImageUtils.imageUriFromCamera = ImageUtils.createImagePathUri(activity);

		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		// MediaStore.EXTRA_OUTPUT参数不设置时,系统会自动生成一个uri,但是只会返回一个缩略图
		// 返回图片在onActivityResult中通过以下代码获取
		// Bitmap bitmap = (Bitmap) data.getExtras().get("data");
		// intent.putExtra(MediaStore.EXTRA_OUTPUT,
		// ImageUtils.imageUriFromCamera);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, ImageUtils.imageUriFromCamera);
		activity.startActivityForResult(intent, ImageUtils.GET_IMAGE_BY_CAMERA);
	}

	public static void openLocalImage(final Activity activity) {
		Intent intent = new Intent();
		intent.setType("image/*");
		intent.setAction(Intent.ACTION_GET_CONTENT);
		activity.startActivityForResult(intent, ImageUtils.GET_IMAGE_FROM_PHONE);
	}

	/**
	 * 開店專用
	 * @param fg
	 */
	public static void openCameraImage(final Fragment fg) {
		ImageUtils.imageUriFromCamera = ImageUtils.createImagePathUri(fg
				.getActivity());
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		fg.startActivityForResult(intent, ImageUtils.GET_IMAGE_BY_CAMERA
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
		fg.startActivityForResult(intent, ImageUtils.GET_IMAGE_FROM_PHONE
				+ selectedType);
	}
	
	/**
	 * 通用
	 * @param fg
	 * @param b 隨便傳
	 */
	public static void openCameraImage(final Fragment fg,Boolean b) {
		ImageUtils.imageUriFromCamera = ImageUtils.createImagePathUri(fg
				.getActivity());
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		fg.startActivityForResult(intent, ImageUtils.GET_IMAGE_BY_CAMERA);
	}

	public static void openLocalImage(final Fragment fg,Boolean b) {
		Intent intent = new Intent();
		intent.setType("image/*");
		intent.setAction(Intent.ACTION_PICK);
		//跑下
		fg.startActivityForResult(intent, ImageUtils.GET_IMAGE_FROM_PHONE);
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
							ImageUtils.openCameraImage(acty);
							break;
						case 1:
							ImageUtils.openLocalImage(acty);
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
							ImageUtils.openCameraImage(fg);
							break;
						case 1:
							ImageUtils.openLocalImage(fg);
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

							ImageUtils.openCameraImage(fg,true);
							break;
						case 1:
							ImageUtils.openLocalImage(fg,true);
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
			//奇怪了，你換個手機試試看 嗯 等等
			
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


	public static void cropImage(Activity activity, Uri srcUri) {
		ImageUtils.cropImageUri = ImageUtils.createImagePathUri(activity);

		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(srcUri, "image/*");
		intent.putExtra("crop", "true");

		// //////////////////////////////////////////////////////////////
		// 1.宽高和比例都不设置时,裁剪框可以自行调整(比例和大小都可以随意调整)
		// //////////////////////////////////////////////////////////////
		// 2.只设置裁剪框宽高比(aspect)后,裁剪框比例固定不可调整,只能调整大小
		// //////////////////////////////////////////////////////////////
		// 3.裁剪后生成图片宽高(output)的设置和裁剪框无关,只决定最终生成图片大小
		// //////////////////////////////////////////////////////////////
		// 4.裁剪框宽高比例(aspect)可以和裁剪后生成图片比例(output)不同,此时,
		// 会以裁剪框的宽为准,按照裁剪宽高比例生成一个图片,该图和框选部分可能不同,
		// 不同的情况可能是截取框选的一部分,也可能超出框选部分,向下延伸补足
		// //////////////////////////////////////////////////////////////

		// aspectX aspectY 是裁剪框宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY 是裁剪后生成图片的宽高
		// intent.putExtra("outputX", 300);
		// intent.putExtra("outputY", 100);

		// return-data为true时,会直接返回bitmap数据,但是大图裁剪时会出现问题,推荐下面为false时的方式
		// return-data为false时,不会返回bitmap,但需要指定一个MediaStore.EXTRA_OUTPUT保存图片uri
		intent.putExtra(MediaStore.EXTRA_OUTPUT, ImageUtils.cropImageUri);
		intent.putExtra("return-data", false);

		activity.startActivityForResult(intent, CROP_IMAGE);
	}
	/**
	 * @param activity
	 * @param srcUri
	 * @param width
	 * 剪切比例自定义
	 */
	public static void cropImage(Activity activity, Uri srcUri,int width,int height) {
		ImageUtils.cropImageUri = ImageUtils.createImagePathUri(activity);

		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(srcUri, "image/*");
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", width);
		intent.putExtra("aspectY", height);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, ImageUtils.cropImageUri);
		intent.putExtra("return-data", false);

		activity.startActivityForResult(intent, CROP_IMAGE);
	}

//
//	public static String UritoStringpath(Activity activity,Uri uri){
//		Cursor cursor = activity.managedQuery(uri,new String[]{MediaStore.Images.Media.DATA},null,null,null);
//		int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//		cursor.moveToFirst();
//		return cursor.getString(column_index);
//	}

}
