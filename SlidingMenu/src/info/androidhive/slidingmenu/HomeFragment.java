package info.androidhive.slidingmenu;

import info.androidhive.slidingmenu.adapter.ServiceHandler;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListFragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class HomeFragment extends ListFragment {
		
	public HomeFragment(){}
	
	// URL to get contacts JSON
	private static String url = "https://raw.githubusercontent.com/batrlaluk/catvusa/master/newJSON";

	// JSON Node names
	private static final String TAG_HOME = "homepage";
	private static final String TAG_ID_HOME = "id";
	private static final String TAG_SHORT_DESCRIPTION_HOME = "short_description";
	private static final String TAG_NAME_HOME = "name";
	private static final String TAG_URI_HOME = "url";
	private static final String TAG_PIC_URI_HOME = "pic_url";
	private static final String TAG_DESCRIPTION_HOME = "description";
		
	// home page JSONArray
	JSONArray home = null;

	// Hashmap for ListView
	ArrayList<HashMap<String, String>> homeList;
	
	  @Override
	  public void onActivityCreated(Bundle savedInstanceState) {
	    super.onActivityCreated(savedInstanceState);
	    
	    homeList = new ArrayList<HashMap<String, String>>(); 
	    
		// Calling async task to get json
		new GetHome().execute();
		
		
	  }
	  

	  @Override
	  public void onListItemClick(ListView l, View v, int position, long id) {
	    // do something with the data
	  }
	
	  /**
		 * Async task class to get json by making HTTP call
		 * */
	private class GetHome extends AsyncTask<Void, Void, Void> {

			@Override
		protected Void doInBackground(Void... arg0) {
			// Creating service handler class instance
			ServiceHandler serviceHandler = new ServiceHandler();

			// Making a request to url and getting response
			String jsonString = serviceHandler.makeServiceCall(url, ServiceHandler.GET);

			Log.d("Response: ", "> " + jsonString);

			if (jsonString != null) {
				try {
					JSONObject jsonObject = new JSONObject(jsonString);
						
					// Getting JSON Array node
					home = jsonObject.getJSONArray(TAG_HOME);

					// looping through All Contacts
					for (int i = 0; i < home.length(); i++) {
						JSONObject h = home.getJSONObject(i);
							
						String id = h.getString(TAG_ID_HOME);
						String short_description = h.getString(TAG_NAME_HOME);
						String name = h.getString(TAG_SHORT_DESCRIPTION_HOME);
						String uri = h.getString(TAG_URI_HOME);
						String pic_uri = h.getString(TAG_PIC_URI_HOME);
						String description = h.getString(TAG_DESCRIPTION_HOME);

						// tmp hashmap for single contact
						HashMap<String, String> homeUnit = new HashMap<String, String>();

						// adding each child node to HashMap key => value
						homeUnit.put(TAG_ID_HOME, id);
						homeUnit.put(TAG_SHORT_DESCRIPTION_HOME, short_description);
						homeUnit.put(TAG_NAME_HOME, name);
						homeUnit.put(TAG_URI_HOME, uri);
						homeUnit.put(TAG_PIC_URI_HOME, pic_uri);
						homeUnit.put(TAG_DESCRIPTION_HOME, description);

						// adding contact to contact list
						homeList.add(homeUnit);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			} else {
				Log.e("ServiceHandler", "Couldn't get any data from the url");
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			// Dismiss the progress dialog
//				if (pDialog.isShowing())
//					pDialog.dismiss();
			/**
			 * Updating parsed JSON data into ListView
			 * */
			ListAdapter adapter = new SimpleAdapter(
					getActivity(), homeList,
					R.layout.fragment_home, new String[] {
						TAG_SHORT_DESCRIPTION_HOME,
						TAG_NAME_HOME,
						TAG_ID_HOME,
						TAG_DESCRIPTION_HOME,
						TAG_URI_HOME,
						TAG_PIC_URI_HOME },
						new int[] {
						R.id.short_description_label,
						R.id.name_label,
						R.id.id_label,
						R.id.description_label,
						R.id.uri_label,
						R.id.pic_uri_label });

			setListAdapter(adapter);
		}

	}
}
