package com.example.vinfoexperttest;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.app.Activity;
import android.app.AlertDialog;
import android.util.Log;
import android.widget.EditText;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.HttpVersion;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;


public class MainActivity extends Activity {
	
	private DefaultHttpClient client=null; 
	private EditText user=null;
	private EditText password=null; 
	private EditText status=null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState); 
		setContentView(R.layout.activity_main);
//		user=(EditText)findViewById(R.id.user); 
//		password=(EditText)findViewById(R.id.password); status=(EditText)findViewById(R.id.status);
		Button accident=(Button)findViewById(R.id.accident);
		Button condition=(Button)findViewById(R.id.condition);
		Button control=(Button)findViewById(R.id.control);
		Button operation=(Button)findViewById(R.id.operation);

		accident.setOnClickListener(onSend);
		condition.setOnClickListener(onSend);
		control.setOnClickListener(onSend);
		operation.setOnClickListener(onSend);
		
		client=new DefaultHttpClient(); 
	}
	
	@Override
	public void onDestroy() { 
		super.onDestroy();
		client.getConnectionManager().shutdown(); 
	}
	
	private String getCredentials() {
		String u=user.getText().toString(); 
		String p=password.getText().toString();
		return "fdsf";
//		return(Base64.encodeBytes((u+":"+p).getBytes())); 
	}
	
	private void updateStatus() { 
		try {
			String s=status.getText().toString();
			HttpPost post=new HttpPost("https://identi.ca/api/statuses/update.json");
			post.addHeader("Authorization", "Basic "+getCredentials());
			List<NameValuePair> form=new ArrayList<NameValuePair>(); 
			form.add(new BasicNameValuePair("status", s));
			post.setEntity(new UrlEncodedFormEntity(form, HTTP.UTF_8)); 
			ResponseHandler<String> responseHandler=new BasicResponseHandler();
			String responseBody=client.execute(post, responseHandler);
			JSONObject response=new JSONObject(responseBody); 
		}
		catch (Throwable t) {
			Log.e("Patchy", "Exception in updateStatus()", t);
			goBlooey(t); 
		}
	}

	
	private void goBlooey(Throwable t) {
		AlertDialog.Builder builder=new AlertDialog.Builder(this);
		builder.setTitle("Exception!") .setMessage(t.toString()) .setPositiveButton("OK", null) .show();	
	}
	
	private View.OnClickListener onSend=new View.OnClickListener() { 
		public void onClick(View v) {
		updateStatus(); 
		}
	};

}