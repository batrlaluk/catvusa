package info.androidhive.slidingmenu;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.MediaController;
import android.widget.VideoView;

public class VideoViewActivity extends Activity {

	private static final String TAG_URI = "url";
	
	// Declare variables
	ProgressDialog pDialog;
	VideoView videoview;

	// Insert your Video URL
//	String VideoURL = "http://catvusa.com/video/spots/uvod.logo.draw.mp4";
			//"http://www.androidbegin.com/tutorial/AndroidCommercial.3gp";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// getting intent data
        Intent myVideoLink = getIntent();
     // Get JSON values from previous intent
        String uri = myVideoLink.getStringExtra(TAG_URI);
        
		// Get the layout from video_main.xml
		setContentView(R.layout.activity_video_view);
		// Find your VideoView in your video_main.xml layout
		videoview = (VideoView) findViewById(R.id.videoView);
		// Execute StreamVideo AsyncTask

		// Create a progressbar
		pDialog = new ProgressDialog(VideoViewActivity.this);
		// Set progressbar title
		pDialog.setTitle("Android Video Streaming");
		// Set progressbar message
		pDialog.setMessage("Buffering...");
		pDialog.setIndeterminate(false);
		pDialog.setCancelable(false);
		// Show progressbar
		pDialog.show();

		try {
			// Start the MediaController
			MediaController mediacontroller = new MediaController(
					VideoViewActivity.this);
			mediacontroller.setAnchorView(videoview);
			// Get the URL from String VideoURL
			Uri video = Uri.parse(uri);
			videoview.setMediaController(mediacontroller);
			videoview.setVideoURI(video);

		} catch (Exception e) {
			Log.e("Error", e.getMessage());
			e.printStackTrace();
		}

		videoview.requestFocus();
		videoview.setOnPreparedListener(new OnPreparedListener() {
			// Close the progress bar and play the video
			public void onPrepared(MediaPlayer mp) {
				pDialog.dismiss();
				videoview.start();
			}
		});

	}
}
