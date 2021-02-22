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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
//import android.text.TextUtils;
import android.util.Log;

import com.example.tscdll.TscWifiActivity;
//import com.example.tscdll.TSCActivity;

public class tscprintwifi extends CordovaPlugin {
	
	public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);
        // Create an instance( LabelPrinter class )
        //printer = new LabelPrinter();
    }

	public void printBmp(String printIp,int printPort,String path){
		//https://www.freesion.com/article/54671081479/
		try{
			//声明以太网连接实例
			//if(TscEthernetDll==null){
				//TscEthernetDll = new TscWifiActivity();
			//}
			TscWifiActivity TscEthernetDll = new TscWifiActivity();
			//打开指定IP和端口号
			TscEthernetDll.openport(printIp, printPort);

			TscEthernetDll.downloadbmp(path);
			
			//String cacheDir = getCacheDir().getAbsolutePath();

			//Toast.makeText(this.cordova.getActivity(), cacheDir, Toast.LENGTH_LONG).show();
			
			//设置标签的寬度、高度、列印速度、列印浓度、感应器类别、gap/black mark 垂直间距、gap/black mark 偏移距离)
			TscEthernetDll.setup(100, 150, 4, 4, 0, 0, 0);

			TscEthernetDll.clearbuffer();

			//打印二维码的参数和内容
			//String command = "QRCODE 120,90,Q,8,A,0,M1,S7,\"" + qrCode+"\"";
			//传送指令
			//TscEthernetDll.sendcommand(command);
			//图片路径
			//String path = "/sdcard/Download/" + "qrPic" + ".png";
			//设置图片在标签纸的坐标
			//TscEthernetDll.sendpicture(0,20,path);

			//TscEthernetDll.sendcommand("PUTBMP 100,520,\"path\"\n");

			TscEthernetDll.sendcommand("PUTBMP 0,0,\""+path+", 8\"\n");

			//延迟一秒等待装载完了再打印
			Thread.sleep(1000);


			/*	說明: 列印标签內容
			參數:
			a: 字串型別，设定列印标签式数(set)
			b: 字串型別，设定列印标签份数(copy)*/
			TscEthernetDll.printlabel(1, 1);
			TscEthernetDll.closeport();
			Toast.makeText(this.cordova.getActivity(), path, Toast.LENGTH_LONG).show();
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
/*
			TscWifiActivity TscEthernetDll = new TscWifiActivity();
			TscEthernetDll.openport("192.168.221.12", 9100);
			TscEthernetDll.sendcommand("SIZE 3,1\r\n");
			TscEthernetDll.sendcommand("GAP 0,0\r\n");
			TscEthernetDll.sendcommand("CLS\r\n");
			TscEthernetDll.sendcommand("TEXT 100,100,\"3\",0,1,1,\"123456\"\r\n");
			TscEthernetDll.sendcommand("PRINT 1\r\n");
			TscEthernetDll.closeport(3000);
			Toast.makeText(this.cordova.getActivity(), "Printed!", Toast.LENGTH_LONG).show();
*/

			printBmp(address, 9100, message);

			//https://www.tscprinters.com/EN/DownloadFile/readpdf/support/2044/Android_SDK_instruction_E.pdf?file_type=0
			
			//https://www.tscprinters.com/EN/DownloadFile/readpdf/support/2044/Android_SDK_instruction_E.pdf?file_type=0

			// https://www.programmersought.com/article/58914782937/


			//TscEthernetDll.sendcommand("SIZE 150 mm, 100 mm\r\n");

			//Set the size of the paper (same size as thermal paper) 100 * 150
			//TscEthernetDll.setup(100, 150, 4, 4, 0, 0, 0);
			
			//String path = "/sdcard/Download/" + fileName + IMG_TYPE; 
			
			//Set the coordinates of the picture on the label paper
			//TscEthernetDll.sendpicture(0, 200, message); //path
			//String status = TscEthernetDll.status();


			//TscEthernetDll.downloadbmp(message); // image > message
			//TscEthernetDll.sendcommand("PUTBMP 100,520,\"Triangle.bmp\"\n");

			//Thread.sleep(1000); //Delay for one second to wait for loading and then print

   			//TscEthernetDll.printlabel(1, 1);//Print out the data in the buffer, the first parameter is the score printed, and the second is the number of sheets printed without a copy
   			//TscEthernetDll.closeport();

// this is for align from app, beta settings
//TscEthernetDll.sendcommand("GAP 2 mm, 0 mm\r\n");

			//TscEthernetDll.downloadbmp("Triangle.bmp"); // image > message
			//TscEthernetDll.sendcommand("PUTBMP 100,520,\"Triangle.bmp\"\n");
			//String status = TscEthernetDll.printerstatus();
			//text1.setText(status);

			//Toast.makeText(this.cordova.getActivity(), "Printed!", Toast.LENGTH_LONG).show();

		} else if(action.equals("status")) {

			TscWifiActivity TscEthernetDll = new TscWifiActivity();
			TscEthernetDll.openport("192.168.221.12", 9100);

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