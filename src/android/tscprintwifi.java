package com.justapplications.cordova.plugin;

// The native Toast API
import android.widget.Toast;

// Cordova-required packages
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.CordovaInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;

import android.app.Activity;

import android.os.Environment;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
//import android.text.TextUtils;
import android.util.Log;

import com.example.tscdll.TscWifiActivity;
import com.example.tscdll.TSCActivity;

public class tscprintwifi extends CordovaPlugin {
	
	public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);

		//Activity activity = cordova.getActivity();
    	//String packageName = activity.getPackageName();
    }

	public void printBmp(String printIp, String path){
		//https://www.freesion.com/article/54671081479/

		try{
			//声明以太网连接实例
			//if(TscEthernetDll==null){
				//TscEthernetDll = new TscWifiActivity();
			//}
			
			TscWifiActivity TscEthernetDll = new TscWifiActivity();

			TscEthernetDll.openport(printIp, 9100);

			//TscEthernetDll.downloadbmp(path);
												 //1
			TscEthernetDll.setup(100, 150, 3, 15, 0, 0, 0);
			//setup(int width, int height, int speed, int density, int sensor, int sensor_distance, int sensor_offset)

			TscEthernetDll.clearbuffer();

			// x, y, path
			TscEthernetDll.sendpicture(0, 0, path);

			//TscEthernetDll.sendcommand("GAP 0,0\r\n");
			//TscEthernetDll.sendcommand("CLS\r\n");
			//TscEthernetDll.sendcommand("PUTBMP 100,520,\""+path+"\"\r\n");

			//延迟一秒等待装载完了再打印
			Thread.sleep(1000);
			TscEthernetDll.sendcommand("PRINT 1\r\n");

			//String status = TscEthernetDll.printerstatus();

			//TscEthernetDll.printlabel(1, 1);
			// quantity / copy
			
			TscEthernetDll.closeport();
			
			Toast.makeText(this.cordova.getActivity(), "Printed", Toast.LENGTH_LONG).show();

		} catch(Exception e){
			//Log.e(TAG, "打印异常->" + e.getMessage());
			//callbackContext.error("Error encountered: " + e.getMessage());
			Toast.makeText(this.cordova.getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
		}
	}

	// methods
	//public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
	@Override
	public boolean execute(String action, JSONArray args, final CallbackContext callbackContext) {
		
		// Verify that the user sent a correct action
		String message;
		String address;

		try {
            JSONObject options = args.getJSONObject(0);
            message = options.getString("message");
            address = options.getString("address");


        } catch (JSONException e) {
            callbackContext.error("Error encountered: " + e.getMessage());
            return false;
        }

		if(action.equals("print")) {

			printBmp(address, message);

			//https://www.tscprinters.com/EN/DownloadFile/readpdf/support/2044/Android_SDK_instruction_E.pdf?file_type=0
			
			//https://www.tscprinters.com/EN/DownloadFile/readpdf/support/2044/Android_SDK_instruction_E.pdf?file_type=0

			// https://www.programmersought.com/article/58914782937/

		} else if(action.equals("status")) {

			TscWifiActivity TscEthernetDll = new TscWifiActivity();
			TscEthernetDll.openport(address, 9100);

			//Note: 00 = Idle, 01 = Head Opened
			String status = TscEthernetDll.printerstatus(); //printerstatus(int timeout)
			String msg = "";
			
			if(status == "00"){
				msg = "Idle";
			}
			if(status == "01"){
				msg = "Head Opened";
			}
			if(status == "-1"){
				msg = "Disconnected";
			}
			if(status == "0") {
				msg = "OK";
			}

			Toast.makeText(cordova.getActivity(), "Status data : " + msg, Toast.LENGTH_LONG).show();
			//TscEthernetDll.closeport(5000);
			
		} else {
            callbackContext.error("\"" + action + "\" is not a recognized action.");
            return false;
        }
		// Send a positive result to the callbackContext
		PluginResult pluginResult = new PluginResult(PluginResult.Status.OK);
		callbackContext.sendPluginResult(pluginResult);
		return true;
    }
}