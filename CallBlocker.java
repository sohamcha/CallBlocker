package com.soham.app1;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.RemoteException;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;
import android.app.AlertDialog.Builder;
import java.io.*;
import java.lang.reflect.Method;
import java.lang.reflect.*;


public class CallBlocker extends Activity implements OnClickListener {
	
	TextView tv,tv2,tv3;
	EditText et;
	Button bt,bt2,bt3;
	Builder alert;
	//BufferedWriter wr;
	//FileWriter file;
	String str;
	FileOutputStream file;
	FileInputStream filein;
	OutputStreamWriter wr;
	InputStreamReader rd;
	TelephonyManager tm;
	int blockedno;
	com.android.internal.telephony.ITelephony telephone;
	//RadioButton rb;
    /** Called when the activity is first created. */
    @Override
    
    
        public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        tv = (TextView) findViewById(R.id.TextView01);
        tv2 = (TextView) findViewById(R.id.TextView02);
        tv3 = (TextView) findViewById(R.id.TextView05);
        et = (EditText) findViewById(R.id.EditText01);
        bt = (Button) findViewById(R.id.Button01);
        bt2 = (Button) findViewById(R.id.Button02);
        bt2.setText("SHOW");
        bt3 = (Button) findViewById(R.id.Button03);
        bt3.setOnClickListener((android.view.View.OnClickListener)this);
        bt2.setOnClickListener((android.view.View.OnClickListener)this);
        bt.setOnClickListener((android.view.View.OnClickListener)this);
        et.setText("");
        this.blockedno = 0;
		
        tv.setText("WELCOME TO ANTI-CRAP");
        bt.setText("BLOCK");
        tv2.setVisibility(4);
        tv2.setText("Blocked");
        tv3.setText("");
        
        tm = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
		
        try {
            // Java reflection to gain access to TelephonyManager's
            // ITelephony getter
           // Log.v(TAG, "Get getTeleService...");
            Class c = Class.forName(tm.getClass().getName());
            Method m = c.getDeclaredMethod("getITelephony");
            m.setAccessible(true);
          telephone = (com.android.internal.telephony.ITelephony)m.invoke(tm);
        } catch (Exception e) {
            e.printStackTrace();
          
        }
        
        
        tm.listen(new phonelisten(), PhoneStateListener.LISTEN_CALL_STATE);
        
        
   
		
		
    }
	public void onClick(View v) {
		// TODO Auto-generated method stub
		char c='A';
		int p=0,noc=0;
		
		if(v.getId()==R.id.Button03)
		{
			alert = new Builder(this);
	        alert.setTitle("NOTE FROM SOHAM !");
	        alert.setMessage("Friends, this application was created by me because I was so annoyed by those crappy sales-commercial calls coming from different numbers. So I used a special search to identify the common part and Block the Whole Bunch of Shit! Hope you like it as I'm  Loving it !!!");
	       alert.setPositiveButton("OK",new DialogInterface.OnClickListener() {
	    	   public void onClick(DialogInterface dialog, int whichButton) {
	    		 }
	    		 });
			
		   alert.show();
		}
		
		if(v.getId()==R.id.Button01)
		{
		
			alert = new Builder(this);
	        alert.setTitle("MESSAGE ! ! !");
	        alert.setMessage("YOUR NUMBER IS ADDED TO DATABASE");
	       alert.setPositiveButton("OK",new DialogInterface.OnClickListener() {
	    	   public void onClick(DialogInterface dialog, int whichButton) {
	    		 }
	    		 });
	        
			
			
			try {
			file = openFileOutput("num.txt",MODE_APPEND);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		wr = new OutputStreamWriter(file);
		str = et.getText().toString();
	    if(str.charAt(0)=='0')
	    {
	    	str = str.replace('0','1');
	    	str = "+9"+str;
	    }
	    	
	    else {	
	    	if(str.charAt(0)!='+')
	    	str = "+91"+str;
	    	 }
		try {
	    	
			wr.write(str+'\n');
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
		alert.show();
		try {
			wr.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			file.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		
		if(v.getId()==R.id.Button02)
		{
			
	        
			try {
				filein = openFileInput("num.txt");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				//alert.show();
				e.printStackTrace();
			}
			
			rd = new InputStreamReader(filein);
			str="";
			//char[] buff = new char[5];
			
			while(p!=-1)
			{
			try {
				p=rd.read();
				c=(char)p;
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				alert.show();
				e1.printStackTrace();
			}
			if(p!=-1)
			{
				str=str+c;
			    noc++;
			}
			}
		       
			alert = new Builder(this);
	        alert.setTitle("MESSAGE ! ! !");
	        alert.setMessage("THE NUMBERS IN DATABASE ARE : \n"+str);
	       alert.setPositiveButton("OK",new DialogInterface.OnClickListener() {
	    	   public void onClick(DialogInterface dialog, int whichButton) {
	    		 }
	    		 });
			alert.show();
			
			// tv.setText(str+"  NO. OF CHAR. PRESENT : "+noc);
			
		 try {
			rd.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//alert.show();
			e.printStackTrace();
			
		}	
		try {
			filein.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//alert.show();
			e.printStackTrace(); 
		}  
		
		}
		
		
	}
    
    
	
	class phonelisten extends PhoneStateListener
	{
         
		
		
		 @Override
		   public void onCallStateChanged(int state,String number)
		 {
			 int p=0,notreject=0,size=0;
	         char c='A';
			 
			 super.onCallStateChanged(state,number);
		  
		  if(state == TelephonyManager.CALL_STATE_RINGING)
	      {
			  try {
					filein = openFileInput("num.txt");
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					//alert.show();
					e.printStackTrace();
				}
				
				rd = new InputStreamReader(filein);
				str="";
				//char[] buff = new char[5];
				
				while(p!=-1)
				{
				try {
					p=rd.read();
					c=(char)p;
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					//alert.show();
					e1.printStackTrace();
				}
				if(p!=-1)
				{
					if(c!='\n')
					str=str+c;
				}
				if(c=='\n')
				{
			//	if(str.compareTo(number)==0)  --> Change the checking
				
				/*while(size<str.length())	
				{
				if(str.charAt(size)==number.charAt(size))
						{
						
					    str.
						size++;
					    continue;
						
						}
				notreject=1;
				break;
				
				}  */
				
				if(number.indexOf(str,0)==0)
						notreject=1;
				else
					notreject=0;
				
				if(notreject==1)
					break;
				
				str="";
				}
				
				}
				try {
					rd.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					//alert.show();
					e.printStackTrace();
					
				}	
				try {
					filein.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					//alert.show();
					e.printStackTrace(); 
				} 
		 
	      
	      
	           if(notreject==1)
	           {
	        	  try {
					telephone.endCall();
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					tv3.setText("COULDn't REJECT : ERROR");
				}
	        	blockedno++;  
				tv3.setText("No. Of Calls Blocked : "+blockedno);
	        	 
	        	  notreject = 0; 
	        	
	           }
	      
	      
	      }
		  
		  
		 }
		 
		
	}
	
}



	


	

	
	



