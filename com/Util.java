package com.redone.pplr.common;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;

import org.codehaus.jackson.map.ObjectMapper;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;


import com.redone.pplr.R;


public class Util {
	
	public static void storeImage(String contentId, Bitmap image, Context context, String imagePath) {
	    File pictureFile = getOutputMediaFile(contentId, context, imagePath);
	    if (pictureFile == null) {

	        return;
	    } 
	    try {
	        FileOutputStream fos = new FileOutputStream(pictureFile);
	        image.compress(Bitmap.CompressFormat.PNG, 90, fos);
	        fos.close();
	    } catch (FileNotFoundException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	    	e.printStackTrace();
	    }  
	}
	
	public static File getOutputMediaFile(String contentId, Context context, String imagePath){
	    // To be safe, you should check that the SDCard is mounted
	    // using Environment.getExternalStorageState() before doing this. 
	    File mediaStorageDir = new File(Environment.getExternalStorageDirectory()
	            + imagePath
	            + context.getPackageName()); 

	    // This location works best if you want the created images to be shared
	    // between applications and persist after your app has been uninstalled.

	    // Create the storage directory if it does not exist
	    if (! mediaStorageDir.exists()){
	        if (! mediaStorageDir.mkdirs()){
	            return null;
	        }
	    } 
	    // Create a media file name
	    File mediaFile;
	    String mImageName="cover_" + contentId +".png";
	    mediaFile = new File(mediaStorageDir.getPath() + File.separator + mImageName);  
	    
	    return mediaFile;
	} 
	
	public static void setImageHeightFitDevice(ImageView imageView){
		try{
		DisplayMetrics dm = new DisplayMetrics();
        Activity activity = (Activity) imageView.getContext();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
		  int width = dm.widthPixels;
		  int height = width * ((BitmapDrawable) imageView.getDrawable()).getBitmap().getHeight() / ((BitmapDrawable) imageView.getDrawable()).getBitmap().getWidth(); 
		  //+1 to put in some gap between photos
		  height += 1;
    	
		  imageView.getLayoutParams().height = height;
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static String getStatus(String statusCode){
		String status = "";
		
		if(statusCode.equalsIgnoreCase("pending")){
			status = "Pending";
		}else if(statusCode.equalsIgnoreCase("pre_approved")){
			status = "Pre-Approved";
		}else if(statusCode.equalsIgnoreCase("accepted")){
			status = "Accepted";
		}else if(statusCode.equalsIgnoreCase("approved")){
			status = "Approved";
		}else if(statusCode.equalsIgnoreCase("not_qualified")){
			status = "Not Qualified";
		}else{
			status = "Canceled";
		}
		
		return status;
	}
	
	public static View createTabView(final Context context, final String text) {
    	View view = LayoutInflater.from(context).inflate(R.layout.tabs_bg, null);
    	TextView tv = (TextView) view.findViewById(R.id.tabsText);
    	tv.setText(text);
    	
    	ImageView image = (ImageView)view.findViewById(R.id.error_indicator);
    	image.setVisibility(View.GONE);
    	
    	return view;
    }
	
	public static boolean isNumeric(String str)
    {
      return str.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
    }
	
	//use to fill up spinner master data 
	/*public static void fillSpinnerMasterDataSession(Context context, Map<String, String> map, Spinner spinner){
		ArrayList<SpinnerItem> spinnerItemList = new ArrayList<SpinnerItem>();
		
		SpinnerItem spinnerDefaultItem = new SpinnerItem("", "--Please Select--");			 		
        spinnerItemList.add(spinnerDefaultItem);	
        
		for (Entry<String, String> entry  : map.entrySet()) {
			SpinnerItem spinnerItem = new SpinnerItem(entry.getKey(), entry.getValue());			     		        
	        spinnerItemList.add(spinnerItem);	        
		}
		
		ArrayAdapter<SpinnerItem> adapter = new ArrayAdapter<SpinnerItem>(context, android.R.layout.simple_spinner_item, spinnerItemList );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);				
	}
	
	//use to fill up spinner master data and might contain saved value from save as draft
	public static void fillSavedSpinnerMasterDataSession(Context context, Map<String, String> map, Spinner spinner, String savedValue){
		ArrayList<SpinnerItem> spinnerItemList = new ArrayList<SpinnerItem>();
		
		SpinnerItem spinnerDefaultItem = new SpinnerItem("", "--Please Select--");			 		
        spinnerItemList.add(spinnerDefaultItem);	
        
        int count = 0;        
        int pos = 0;
		for (Entry<String, String> entry  : map.entrySet()) {
			SpinnerItem spinnerItem = new SpinnerItem(entry.getKey(), entry.getValue());			     		        
	        spinnerItemList.add(spinnerItem);	    
	        
	        //check savedValue same then get pos and set spinner selected
	        if(savedValue != null && savedValue.equals(entry.getKey())){
	        	pos = count + 1;
	        }
	        
	        count++;
		}
		
		ArrayAdapter<SpinnerItem> adapter = new ArrayAdapter<SpinnerItem>(context, android.R.layout.simple_spinner_item, spinnerItemList );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);				
        spinner.setSelection(pos);	
	}
	
	//use to fill up auto textfield master data 
	public static void fillAutoTextFieldMasterDataSession(Context context, Map<String, String> map, AutoCompleteTextView textView){
		ArrayList<SpinnerItem> spinnerItemList = new ArrayList<SpinnerItem>();
		
		for (Entry<String, String> entry  : map.entrySet()) {
			SpinnerItem spinnerItem = new SpinnerItem(entry.getKey(), entry.getValue());			     		        
	        spinnerItemList.add(spinnerItem);
		}
		
		textView.setAdapter(new ArrayAdapter<SpinnerItem>(context, android.R.layout.simple_dropdown_item_1line, spinnerItemList));
	}
	
	public static String getKeyByValue(Map<String, String> map, String value) {
		if(map != null && value != null){
			for (Entry<String, String> entry : map.entrySet()) {
		        if (value.equals(entry.getValue())) {
		            return entry.getKey();
		        }
		    }
		}
	    
	    return null;
	}*/
}
