package e.aakriti.work.podcast_app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import e.aakriti.work.common.SharedData;
import e.aakriti.work.common.Utility;

public class Landing_PagerActivity extends Activity {

	ViewPager viewpager;
	String title[],data[],from_Back = "0";
	int drawable[],drawable1[];
	ImageView round1,round2,round3,imageView1;
	RelativeLayout roundRel;
	SharedData sharedData ;
	
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.landing_pager);
		
		viewpager = (ViewPager) findViewById(R.id.viewPager);
		sharedData = new SharedData(Landing_PagerActivity.this);
		String Listnerid = sharedData.getString("ListnerId", "");
		if(Utility.isNotNull(Listnerid))
		{
			Intent intent = new Intent(Landing_PagerActivity.this,MainActivity.class);
			startActivity(intent);
			finish();
					
		}
		
		round1 = (ImageView) findViewById(R.id.round1);
		round2 = (ImageView) findViewById(R.id.round2);
		round3 = (ImageView) findViewById(R.id.round3);
		roundRel = (RelativeLayout) findViewById(R.id.roundRel);
		
		title = new String[3];
		data = new String[3];
		drawable = new int[3];
		drawable1 = new int[3];
		
		from_Back = getIntent().getStringExtra("from_Back");
		
		title[0] = "Discover";
		title[1] = "Listen & Follow";
		title[2] = "Seeking Wisdom";
		data[0] = "Break with the mainstream. The future of Australian audio is here.";
		data[1]="Build a library of your favourite shows and interests, where fresh episodes load automatically.";
		data[2]="True wisdom is understanding life,ourselves and world around us.Replenish your mind.";
		drawable[0]=R.drawable.discover;
		drawable[1] = R.drawable.listen_follow;
		drawable[2] = R.drawable.seeking_wisdom;
		drawable1[0]=R.drawable.search1;
		drawable1[1] = R.drawable.headphone;
		drawable1[2] = android.R.color.transparent;
		
		viewpager.setAdapter(new SliderPageAdapter(getApplicationContext(), title, data, drawable,drawable1));
		
		if(from_Back != null && from_Back.equalsIgnoreCase("1"))
		{
			viewpager.setCurrentItem(3);
		}
		else
		{
			viewpager.setCurrentItem(0);
		}
		roundRel.bringToFront();
		round1.setVisibility(View.VISIBLE);
		round2.setVisibility(View.VISIBLE);
		round3.setVisibility(View.VISIBLE);
		
		viewpager.setOnPageChangeListener(new OnPageChangeListener() {
			
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				if(viewpager.getCurrentItem() == 0)
				{
					round1.setVisibility(View.VISIBLE);
					round2.setVisibility(View.VISIBLE);
					round3.setVisibility(View.VISIBLE);
					round1.setImageResource(R.drawable.active_round);
					round2.setImageResource(R.drawable.inactive_round);
					round3.setImageResource(R.drawable.inactive_round);
				}
				else if(viewpager.getCurrentItem() == 1)
				{
					round1.setVisibility(View.VISIBLE);
					round2.setVisibility(View.VISIBLE);
					round3.setVisibility(View.VISIBLE);
					round1.setImageResource(R.drawable.inactive_round);
					round2.setImageResource(R.drawable.active_round);
					round3.setImageResource(R.drawable.inactive_round);
				}
				else if(viewpager.getCurrentItem() == 2)
				{
					round1.setVisibility(View.VISIBLE);
					round2.setVisibility(View.VISIBLE);
					round3.setVisibility(View.VISIBLE);
					round1.setImageResource(R.drawable.inactive_round);
					round2.setImageResource(R.drawable.inactive_round);
					round3.setImageResource(R.drawable.active_round);
				}
				else
				{
					round1.setVisibility(View.GONE);
					round2.setVisibility(View.GONE);
					round3.setVisibility(View.GONE);
				}
			}
			
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
				
			}
			
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}

	class SliderPageAdapter extends PagerAdapter {
		
		String[] title, data;
		int[] drawable,drawable1;
		Context context;
		
		public SliderPageAdapter(Context context,String[] title,String[] data,int[] drawable, int[] drawable1) {
			//super();
			// TODO Auto-generated constructor stub
			this.title = title;
			this.data = data;
			this.drawable = drawable;
			this.drawable1 = drawable1;
			this.context = context;
		}
		@Override
		public int getCount() {
			//Return total pages, here one for each data item
			return 4;
		}
		//Create the given page (indicated by position)
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
			View page ;
			
			Log.e("position == ", ""+position);
			if(position == 3)
			{
				page = inflater.inflate(R.layout.pager_item_2, null);
				((Button)page.findViewById(R.id.loginBtn)).setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent intent = new Intent(Landing_PagerActivity.this,LoginActivity.class);
						startActivity(intent);
						finish();
					}
				});
				
				((Button)page.findViewById(R.id.registerBtn)).setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent intent = new Intent(Landing_PagerActivity.this,RegisterActivity.class);
						startActivity(intent);
						finish();
						
						//Toast.makeText(Landing_PagerActivity.this,"Register - Coming soon...!", Toast.LENGTH_LONG).show();
					}
				});
			}
			else
			{
				page = inflater.inflate(R.layout.pager_item, null);
				((TextView)page.findViewById(R.id.textTitle)).setText(title[position]);
				((TextView)page.findViewById(R.id.textContent)).setText(data[position]);
				((TextView)page.findViewById(R.id.textTitle)).setTypeface(Typeface.createFromAsset(context.getAssets(), "Lato-Bold.ttf"));
				((TextView)page.findViewById(R.id.textContent)).setTypeface(Typeface.createFromAsset(context.getAssets(), "Lato-Regular.ttf"));
				((RelativeLayout)page.findViewById(R.id.imageView)).setBackgroundResource(drawable[position]);
				((ImageView)page.findViewById(R.id.imageView1)).setBackgroundResource(drawable1[position]);
				//Add the page to the front of the queue
				
			}
			((ViewPager) container).addView(page, 0);
			return page;
		}
		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			//See if object from instantiateItem is related to the given view
			//required by API
			return arg0==(View)arg1;
		}
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
		  ((ViewPager) container).removeView((View) object);
		  object=null;
		}
	}
}
