package info.androidhive.slidingmenu;

import info.androidhive.slidingmenu.R;
import info.androidhive.slidingmenu.DetailsActivity;
import info.androidhive.slidingmenu.VideoViewActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class DetailsActivity extends Activity {
	
	// JSON node keys
	private static final String TAG_NAME = "name";
	private static final String TAG_URI = "url";
	private static final String TAG_PIC_URI = "pic_url";
	private static final String TAG_DESCRIPTION = "description";
//	private static final String TAG_LINK = "link";
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        
        // getting intent data
        Intent in = getIntent();
        
        // Get JSON values from previous intent
        String name = in.getStringExtra(TAG_NAME);
        final String uri = in.getStringExtra(TAG_URI);
        String description = in.getStringExtra(TAG_DESCRIPTION);
//        final String pic_uri = in.getStringExtra(TAG_PIC_URI);
        
        // Displaying all values on the screen
        TextView lblName = (TextView) findViewById(R.id.name_label);
//        TextView lblUri = (TextView) findViewById(R.id.uri_label);
        TextView lblDescription = (TextView) findViewById(R.id.description_label);
        
        lblName.setText(name);
//        lblUri.setText(uri);
        lblDescription.setText(description);
        
/**		Button btn = (Button) findViewById(R.id.link_clickme);
        btn.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent myWebLink = new Intent(android.content.Intent.ACTION_VIEW);
                myWebLink.setData(Uri.parse(link));
                    startActivity(myWebLink);
             }
        });   
**/        
        Button videobtn = (Button) findViewById(R.id.video_clickme);
        videobtn.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent myVideoLink = new Intent(DetailsActivity.this,
                		VideoViewActivity.class);
                myVideoLink.putExtra(TAG_URI, uri);
                startActivity(myVideoLink);
             }
        });         
        
    }
}
