package e.aakriti.work.podcast_app;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import e.aakriti.work.common.RestApi;
import e.aakriti.work.common.SharedData;
import e.aakriti.work.common.Utility;

public class RegisterActivity extends Activity{
	
	EditText usernameEt,passwordEt,emailEt;
	Button submitBtn;
	TextView backTxt;
	ImageView fbLogin, gpLogin;
	Utility utility;
	String userName, passWord,email;
	SharedData sharedData;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		
		utility = new Utility(RegisterActivity.this);
		submitBtn = (Button) findViewById(R.id.submitBtn);
		backTxt = (TextView) findViewById(R.id.backTxt);
		fbLogin = (ImageView) findViewById(R.id.fbLoginBtn);
		gpLogin = (ImageView) findViewById(R.id.gpLoginBtn);
		emailEt = (EditText) findViewById(R.id.emailEt);
		passwordEt = (EditText) findViewById(R.id.passwordEt);
		usernameEt = (EditText) findViewById(R.id.usernameEt);

		sharedData = new SharedData(RegisterActivity.this);
		
		backTxt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(RegisterActivity.this, Landing_PagerActivity.class);
				intent.putExtra("from_Back", "1");
				startActivity(intent);
				finish();
			}
		});
		submitBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// Toast.makeText(LoginActivity.this, "Submit - Coming
				// Soon...!", Toast.LENGTH_LONG).show();

				userName = usernameEt.getText().toString();
				passWord = passwordEt.getText().toString();
				email = emailEt.getText().toString();
				
				if (Utility.isNotNull(userName) && Utility.isNotNull(passWord)&& Utility.isNotNull(email) && Utility.checkEmail(email)) {
					new registerWSCall(userName, passWord).execute();
					
				} else {
					if(!Utility.isNotNull(userName))
					{
						Toast.makeText(RegisterActivity.this, "Please enter your UserName", Toast.LENGTH_LONG).show();
					}
					else if(!Utility.isNotNull(email))
					{
						Toast.makeText(RegisterActivity.this, "Please enter your Email address", Toast.LENGTH_LONG).show();
					}
					else if(!Utility.checkEmail(email))
					{
						Toast.makeText(RegisterActivity.this, "Please enter Valid Email address", Toast.LENGTH_LONG).show();
					}
					else if(!Utility.isNotNull(passWord))
					{
						Toast.makeText(RegisterActivity.this, "Please provide your Password", Toast.LENGTH_LONG).show();
					}
					
				}
				// }
			}
		});

		

		fbLogin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(RegisterActivity.this, "FB Login - Coming Soon...!", Toast.LENGTH_LONG).show();
			}
		});

		gpLogin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(RegisterActivity.this, "Google Login - Coming Soon...!", Toast.LENGTH_LONG).show();
			}
		});
		
	}
	
	public class registerWSCall extends AsyncTask<Void, Void, String> {

		private ProgressDialog mLoader;
		private String result = null, errorMessage = "",user_name;
		private int errorCode = 0;

		registerWSCall(String user_email, String user_password) {
		}

		@Override
		protected void onPreExecute() {
			mLoader = new ProgressDialog(RegisterActivity.this);
			mLoader.setMessage("Loading");
			mLoader.setCancelable(false);
			mLoader.show();

			super.onPreExecute();
		}

		@Override
		protected void onPostExecute(String result) {
			if (mLoader.isShowing())
				mLoader.dismiss();

			if (errorCode == 0) {

			} else {

			}
			super.onPostExecute(result);
		}

		@Override
		protected String doInBackground(Void... params) {
			try {
				if (utility.isNetworkAvailable()) {
					Thread th = new Thread(new Runnable() {
						@Override
						public void run() {
							try {
								String uri = RestApi.createURI(RestApi.Register_WS)+"/user_name/"+userName + "/email/"+email+"/password/" + passWord;
								//String uri = "http://www.whooshkaa.com/index.php?r=api/LoginDevice&user_name="
									//	+ userName + "&password=" + passWord;
								result = RestApi.getDataFromURLWithoutParam(uri);

								if (Utility.isNotNull(result)) {
									final JSONObject objRes = new JSONObject(result);
									errorCode = Integer.parseInt(objRes.optString("response"));

									if (errorCode == 1) {
										runOnUiThread(new Runnable() {

											@Override
											public void run() {
												// TODO Auto-generated method
												// stub
												user_name = objRes.optString("username");
												if(user_name.contains("email="))
												{
													user_name = user_name.substring(0,user_name.indexOf("email="));
												}
												
												emailEt.setText("");
												passwordEt.setText("");
												usernameEt.setText("");
												
												sharedData.putString("ListnerId", objRes.optString("id"));
												sharedData.putString("ListnerName", user_name);
												sharedData.putString("ListnerProfilePic", objRes.optString("pic"));
												
												
												Intent intent = new Intent(RegisterActivity.this,QuestionListingActivity.class);
												
												startActivity(intent);
												//finish();
											}
										});
									} else {
										runOnUiThread(new Runnable() {

											@Override
											public void run() {
												// TODO Auto-generated method
												// stub

												errorMessage = objRes.optString("msg");
												Toast.makeText(RegisterActivity.this, "" + errorMessage, Toast.LENGTH_LONG)
														.show();
											}
										});
									}
								}
							} catch (ConnectException e) {
								Log.e("", "" + e.toString());
							} catch (Exception e) {
								Log.e("", "" + e.toString());
							}
						}
					});
					th.start();
					th.join();
				} else {
					runOnUiThread(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							Toast.makeText(RegisterActivity.this, "Please check your Internet connection",
									Toast.LENGTH_LONG).show();
						}
					});
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return result;
		}
	}


}manish	



