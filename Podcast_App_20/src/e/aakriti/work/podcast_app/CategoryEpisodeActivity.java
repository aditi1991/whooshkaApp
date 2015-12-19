package e.aakriti.work.podcast_app;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import e.aakriti.work.adapter.CategoryEpisodesAdapter;
import e.aakriti.work.common.RestApi;
import e.aakriti.work.common.SharedData;
import e.aakriti.work.common.Utility;
import e.aakriti.work.objects.CategoryEpisodes;

public class CategoryEpisodeActivity extends Activity{
	
	String title,cat_id,cat_name,user_id;
	GridView category_episodes;
	View header;
	TextView title_Txt;
	ImageView menuImg,searchImg;
	List<CategoryEpisodes> CategoryEpisodes;
	CategoryEpisodesAdapter categoryEpisodesAdapter;
	String listner_id = "";
	SharedData sharedData;
	Utility utility;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.category_episode);
		
		utility = new Utility(CategoryEpisodeActivity.this);
		sharedData = new SharedData(CategoryEpisodeActivity.this);
		listner_id = sharedData.getString("ListnerId", "");
		
		header = (View) findViewById(R.id.layout_header);
		title_Txt = (TextView) header.findViewById(R.id.titleTxt);
		menuImg = (ImageView) header.findViewById(R.id.menuImg);
		searchImg = (ImageView) header.findViewById(R.id.searchImg);
		category_episodes = (GridView) findViewById(R.id.gridView);
		
		searchImg.setVisibility(View.INVISIBLE);
		menuImg.setImageResource(R.drawable.back);
		cat_id = getIntent().getStringExtra("Category_id");
		cat_name = getIntent().getStringExtra("Category_name");
		title_Txt.setText(cat_name);
		//title_Txt.setGravity(Gravity.LEFT);
		
		menuImg.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onBackPressed();
			}
		});
		
		new getCategoryContents(category_episodes,CategoryEpisodeActivity.this,categoryEpisodesAdapter).execute();
	}
	
	public void onBackPressed() 
	{
		finish();
	};
	
	public class getCategoryContents extends AsyncTask<Void, Void, String> {

		private ProgressDialog mLoader;
		private String result = null, errorMessage = "",response = "";
		private int errorCode = 0;
		GridView gridViewq;
		Context context;
		//ArrayList<Categories> allCategories;
		CategoryEpisodesAdapter adapter;
		Utility utility;
		
		public getCategoryContents(GridView gridViewq,Context c,CategoryEpisodesAdapter adapter) {
			// TODO Auto-generated constructor stub
			this.gridViewq = gridViewq;
			this.context = c;
			//this.allCategories = (ArrayList<Categories>) MainActivity.allCategories;
			this.adapter = adapter;
			utility = new Utility(context);
		}
		
		

		@Override
		protected void onPreExecute() {
			mLoader = new ProgressDialog(context);
			mLoader.setMessage("Loading");
			mLoader.setCancelable(false);
			mLoader.show();

			super.onPreExecute();
		}

		@Override
		protected void onPostExecute(String result) {
			if (mLoader.isShowing())
				mLoader.dismiss();
			
			adapter = new CategoryEpisodesAdapter(context,CategoryEpisodes);
			gridViewq.setAdapter(adapter);
	        
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
								String uri = RestApi.createURI(RestApi.GetCategoryContents+"/cat_id/"+cat_id+"/user/"+listner_id);
								//String uri = "http://www.whooshkaa.com/index.php?r=api/LoginDevice&user_name="
									//	+ userName + "&password=" + passWord;
								result = RestApi.getDataFromURLWithoutParam(uri);

								if (Utility.isNotNull(result)) {
									final JSONObject objRes = new JSONObject(result);
									response = objRes.optString("response");
									Log.e("result", response);
									if (!response.equalsIgnoreCase("")) {
										((Activity) context).runOnUiThread(new Runnable() {

											@Override
											public void run() {
												// TODO Auto-generated method
												// stub
												
												try {
													JSONArray jsonArray = new JSONArray(response);
													CategoryEpisodes = new ArrayList<CategoryEpisodes>(jsonArray.length());
													for (int i =0 ;i<jsonArray.length();i++)
													{
														JSONObject obj = jsonArray.getJSONObject(i);
														CategoryEpisodes episode = new CategoryEpisodes(obj);
														CategoryEpisodes.add(episode);
													}
													Log.e("categoryEpisodes", ""+CategoryEpisodes.size());
												} catch (JSONException e) {
													// TODO Auto-generated catch block
													e.printStackTrace();
												}
												
											}
										});
									} else {
										((Activity) context).runOnUiThread(new Runnable() {

											@Override
											public void run() {
												// TODO Auto-generated method
												// stub

												errorMessage = objRes.optString("msg");
												Toast.makeText(context, "" + errorMessage, Toast.LENGTH_LONG)
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
					((Activity) context).runOnUiThread(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							Toast.makeText(context, "Please check your Internet connection",
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
	
	
	
}
