package com.tom_004;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Iterator;

import com.tom_004.R;


import android.graphics.Color;
import android.hardware.usb.UsbConstants;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbManager;
import android.hardware.usb.UsbRequest;
import android.os.Bundle;
import android.app.Activity;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.text.method.KeyListener;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputConnection ;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.content.Context;

public class MainActivity extends Activity {

	int i,j,k;
	String str1;
	
	
	
	
	private boolean forceClaim = true;
	UsbDeviceConnection connection;
	
	UsbDevice device ;//设备
    UsbEndpoint endpoint;//中点
   	UsbInterface intf;//借口
	
   	UsbEndpoint epIN = null;//输出终点
    UsbEndpoint epOUT = null;//输入终点
   	
   	
    int f;
    int g;
   	
   	String str1_;//接收
   	String str2_=null;//发送
	
	byte[] Byte = new byte[7];
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		getWindow().setSoftInputMode(BIND_IMPORTANT);
		
		
		EditText t1=(EditText)findViewById(R.id.editText1);
		EditText t2=(EditText)findViewById(R.id.editText2);
		t1.setBackgroundColor(Color.BLACK);
		t1.setTextColor(Color.GREEN);
		t1.setFocusable(false);
		t2.setBackgroundColor(Color.BLACK);
		t2.setTextColor(Color.BLACK);
		t2.setCursorVisible(false);
		
		Editable ea= (Editable) t1.getText();  //定光标位置
		Selection.setSelection(ea, ea.length());
		

		t1.setText("Hello");
		t2.setText("");
		
		
//*/

		
		
		
		
		

	
		
		
		
		
		
		
		
		
		
		
		

	}

	void huo(String str)//整理界面
	{
		
		EditText t1=(EditText)findViewById(R.id.editText1);
		
		

		send(str);
		
		t1.setText(str1_);
		Editable ea= (Editable) t1.getText();  //定光标位置
		Selection.setSelection(ea, ea.length());
			
	}
	
	
	
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	
	
	@Override 
	public boolean onOptionsItemSelected(MenuItem item) {  
		
		final EditText t1=(EditText)findViewById(R.id.editText1);
//		EditText t2=(EditText)findViewById(R.id.editText2);
	    switch (item.getItemId()) {  
	    case R.id.menu_settings:
        	
        	t1.setText("ccie");
        	UsbManager manager = (UsbManager) getSystemService(Context.USB_SERVICE);

    		
    		HashMap<String, UsbDevice> deviceList = manager.getDeviceList();
    		Iterator<UsbDevice> deviceIterator = deviceList.values().iterator();
    		

        	
        	
        	
    		while(deviceIterator.hasNext()){
    		    device = deviceIterator.next();
    		    i=device.getDeviceId();
    		    j=device.getVendorId();
    		    k=device.getProductId();
    		    str1=device.getDeviceName();
    		    
            	t1.setText(t1.getText()+String.valueOf(i)+" "+
            			String.valueOf(j)+" "+
            			String.valueOf(k)+" "+
            			String.valueOf(deviceList.size())+" "+
            			str1+"\n");
    		    
           	 intf = device.getInterface(0);
            	
            	endpoint = intf.getEndpoint(0);
            	
            	connection = manager.openDevice(device); 
            	
            	connection.claimInterface(intf, forceClaim);
    		}

    		
        	return true;
	    
	    
	        case R.id.menu_connect:


        		
	        	new Thread(mLoop).start();
	        	
        		
        		
	        	//*/
	        	
	            return true;  
	        
	        case R.id.menu_info:
	        	

	        	

	        	
	        	return true; 
	        	
	        		
		default:  
	            return super.onOptionsItemSelected(item);  
	    }  
	}
	
	void duoduo(StringBuilder str)//连接字符串
	{
		

		
		
		str1_=str1_+str.toString();
		
		
	}
	
	void ftdi()//配置
	{
        connection.controlTransfer(0x40, 0, 0, 0, null, 0, 0);// reset
        // mConnection.controlTransfer(0×40,
        // 0, 1, 0, null, 0,
        // 0);//clear Rx
        connection.controlTransfer(0x40, 0, 2, 0, null, 0, 0);// clear Tx
        connection.controlTransfer(0x40, 0x02, 0x0000, 0, null, 0, 0);// flow
                // control
                // none
        connection.controlTransfer(0x40, 0x03, 0x4138, 0, null, 0, 0);// baudrate
                // 9600
        connection.controlTransfer(0x40, 0x04, 0x0008, 0, null, 0, 0);// data bit
                // 8, parity
                // none,
                // stop bit
                // 1, tx off
		

	
	}
	
	void prolific()//配置
	{
		byte[] arrayOfByte1 = new byte[7];
		
		arrayOfByte1[0]=0x2;
		arrayOfByte1[1]=0x7;
		arrayOfByte1[2]=0x1;
		arrayOfByte1[3]=0x0;
		arrayOfByte1[4]=0x0;
		arrayOfByte1[5]=0x0;
		arrayOfByte1[6]=0x0;

		
		connection.controlTransfer(64, 1, 1028, 0, null, 0, 100);
		connection.controlTransfer(64, 1, 1028, 1, null, 0, 100);
		connection.controlTransfer(64, 1, 0, 1, null, 0, 100);
		connection.controlTransfer(64, 1, 1, 0, null, 0, 100);
		connection.controlTransfer(64, 1, 2, 68, null, 0, 100);
		connection.controlTransfer(33, 32, 0, 0, arrayOfByte1, 7, 100);
		connection.controlTransfer(33, 34, 3, 0, null, 0, 100);
		connection.controlTransfer(33, 34, 3, 0, null, 0, 100);
		connection.controlTransfer(33, 32, 0, 0, arrayOfByte1, 7, 100);
		connection.controlTransfer(64, 1, 0, 0, null, 0, 100);
	
	}
	
	void ftdi_received()//接收
	{
		byte[] buffer = new byte[1024];

        StringBuilder str = new StringBuilder();
      

        
        if (connection.bulkTransfer(epIN, buffer, buffer.length, 100) >= 0)
        {
            for (int i = 2; i < 1024; i++) {
                if (buffer[i] != 0) {
                    str.append((char) buffer[i]);
                } else {
                	duoduo(str);
                    break;
                }
            }

        }
	}
	
	void prolific_recevied()//接收
	{
		byte[] buffer = new byte[1024];

        StringBuilder str = new StringBuilder();
      

        
        if (connection.bulkTransfer(epIN, buffer, buffer.length, 100) >= 0)
        {
            for (int i = 0; i < 1024; i++) {
                if (buffer[i] != 0) {
                    str.append((char) buffer[i]);
                } else {
                	duoduo(str);
                    break;
                }
            }

        }
	}
	
	
	
	

	private Runnable mLoop = new Runnable() //线程 开始接受消息
{

        public void run() {
        	


        	ftdi();
     //  	prolific();
        	
        	
        	
            

     //       byte counter = 0;

 
            for (int i = 0; i < intf.getEndpointCount(); i++) {
      /*     duoduo("EP: "
                        + String.format("0x%02X", intf.getEndpoint(i)
                                .getAddress()));
           //*/
                if (intf.getEndpoint(i).getType() == UsbConstants.USB_ENDPOINT_XFER_BULK) {
          //      	t1.setText("Bulk Endpoint");
                    if (intf.getEndpoint(i).getDirection() == UsbConstants.USB_DIR_IN)
                        epIN = intf.getEndpoint(i);
                    else
                        epOUT = intf.getEndpoint(i);
                } else {
           //     	t1.setText(t1.getText()+"Not Bulk");
                }
            }
            
            for (;;) {// this is the main loop for transferring
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            
                
 
                
                
                
               ftdi_received();
                
                
                
            //    prolific_recevied();
                
                
                
                
                final EditText t1=(EditText)findViewById(R.id.editText1);//线程UI

	            t1.post		(new Runnable() {//另外一种更简洁的发送消息给ui线程的方法。             
	            	public void run()


	            	{//run()方法会在ui线程执行
	            		
	            		t1.setText(str1_);
	            		Editable ea= (Editable) t1.getText();  //定光标位置
	            		Selection.setSelection(ea, ea.length());
	            	}

	            	});

	        	//*/
                
                
                

            }
        }


    };
	

	
    void send(String str)//发送
    {

    	
    	ByteBuffer localByteBuffer 
    	= ByteBuffer.wrap(str.toString().getBytes());
    	UsbRequest localUsbRequest = new UsbRequest();
    	localUsbRequest.initialize(connection,epOUT);
    	localUsbRequest.setClientData("Send Request");
    	i = localByteBuffer.capacity();
    	localUsbRequest.queue(localByteBuffer, i);

    	
    }

	

   

  
	public boolean onKeyUp(int keyCode, KeyEvent event) {

	//	EditText t2=(EditText)findViewById(R.id.editText2);
	//	t2.setText("");
	
		
		
		
    	
    	
   // 	t2.setText("");t2.setText(KeyEvent.keyCodeToString(keyCode));
    	String str=keyCode+"";
    	
    	
    	switch (keyCode) {
    	
    	case KeyEvent.KEYCODE_A:huo("a");break;
    	case KeyEvent.KEYCODE_B:huo("b");break;
    	case KeyEvent.KEYCODE_C:huo("c");break;
    	case KeyEvent.KEYCODE_D:huo("d");break;
    	case KeyEvent.KEYCODE_E:huo("e");break;
    	case KeyEvent.KEYCODE_F:huo("f");break;
    	case KeyEvent.KEYCODE_G:huo("g");break;
    	case KeyEvent.KEYCODE_H:huo("h");break;
    	case KeyEvent.KEYCODE_I:huo("i");break;
    	case KeyEvent.KEYCODE_J:huo("j");break;
    	case KeyEvent.KEYCODE_K:huo("k");break;
    	case KeyEvent.KEYCODE_L:huo("l");break;
    	case KeyEvent.KEYCODE_M:huo("m");break;
    	case KeyEvent.KEYCODE_N:huo("n");break;
    	case KeyEvent.KEYCODE_O:huo("o");break;
    	case KeyEvent.KEYCODE_P:huo("p");break;
    	case KeyEvent.KEYCODE_Q:huo("q");break;
    	case KeyEvent.KEYCODE_R:huo("r");break;
    	case KeyEvent.KEYCODE_S:huo("s");break;
    	case KeyEvent.KEYCODE_T:huo("t");break;
    	case KeyEvent.KEYCODE_U:huo("u");break;
    	case KeyEvent.KEYCODE_V:huo("v");break;
    	case KeyEvent.KEYCODE_W:huo("w");break;
    	case KeyEvent.KEYCODE_X:huo("x");break;
    	case KeyEvent.KEYCODE_Y:huo("y");break;
    	case KeyEvent.KEYCODE_Z:huo("z");break;
    	case KeyEvent.KEYCODE_0:huo("0");break;
    	case KeyEvent.KEYCODE_1:huo("1");break;
    	case KeyEvent.KEYCODE_2:huo("2");break;
    	case KeyEvent.KEYCODE_3:huo("3");break;
    	case KeyEvent.KEYCODE_4:huo("4");break;
    	case KeyEvent.KEYCODE_5:huo("5");break;
    	case KeyEvent.KEYCODE_6:huo("6");break;
    	case KeyEvent.KEYCODE_7:huo("7");break;
    	case KeyEvent.KEYCODE_8:huo("8");break;
    	case KeyEvent.KEYCODE_9:huo("9");break;
    	case KeyEvent.KEYCODE_SPACE:huo(" ");break;
    	case 57:huo("");break;
    	case KeyEvent.KEYCODE_BREAK:huo("");break;
    	case 55:huo(",");break;
    	case 56:huo(".");break;
    	
    	case 68:huo("`");break;
    	case 76:huo("/");break;
    	case 73:huo("\'");break;
    	case KeyEvent.KEYCODE_ENTER:huo("\n");break;
	//	default:huo(str);
    	}
    	
    	//*/
    	

    	return true;
    	 
    }
	
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		//	EditText t2=(EditText)findViewById(R.id.editText2);
		//	t2.setText("");
		
			
			
			
	    	
	    	
	   // 	t2.setText("");t2.setText(KeyEvent.keyCodeToString(keyCode));
	    	String str=keyCode+"";
	    	
	    	
	    	switch (keyCode) {
	    	
	    	case 61:huo("\t");break;
	    	case 113:huo("?");break;
			default:huo(str);
	    	}
	    	
	    	//*/
	    	

	    	return true;
	    	 
	    }

}


























