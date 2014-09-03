package info.androidhive.slidingmenu;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import info.androidhive.slidingmenu.ClassFragment;
import info.androidhive.slidingmenu.R;
import info.androidhive.slidingmenu.adapter.ServiceHandler;

import android.app.ListFragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class KitchenFragment extends ListFragment {
	
	public KitchenFragment(){}
	
	private ProgressDialog pDialog;

	// URL to get contacts JSON
	private static String url = "https://raw.githubusercontent.com/batrlaluk/catvusa/master/newJSON";

	// JSON Node names
	private static final String TAG_BROADCAST = "kitchen";
	private static final String TAG_ID = "id";
	private static final String TAG_SHORT_DESCRIPTION = "description";
	private static final String TAG_NAME = "name";
	private static final String TAG_URI = "url";
	private static final String TAG_PIC_URI = "pic_url";
	private static final String TAG_DESCRIPTION = "description";
	
	// contacts JSONArray
	JSONArray broadcast = null;

	// Hashmap for ListView
	ArrayList<HashMap<String, String>> broadcastList;
	
	  @Override
	  public void onActivityCreated(Bundle savedInstanceState) {
	    super.onActivityCreated(savedInstanceState);
	    
	    broadcastList = new ArrayList<HashMap<String, String>>();
	    
	    ListView lv = getListView();
	    
	    // Listview on item click listener
	    lv.setOnItemClickListener(new OnItemClickListener() {
	    	public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// getting values from selected ListItem
				String name = ((TextView) view.findViewById(R.id.name))
						.getText().toString();
				String cost = ((TextView) view.findViewById(R.id.id))
						.getText().toString();
				String description = ((TextView) view.findViewById(R.id.description))
						.getText().toString();
				String uri = ((TextView) view.findViewById(R.id.uri))
						.getText().toString();
				String pic_uri = ((TextView) view.findViewById(R.id.pic_uri))
						.getText().toString();

				// Starting single contact activity
				Intent in = new Intent(getActivity(),
						DetailsActivity.class);
				in.putExtra(TAG_NAME, name);
				in.putExtra(TAG_ID, cost);
				in.putExtra(TAG_URI, uri);
				in.putExtra(TAG_PIC_URI, pic_uri);
				in.putExtra(TAG_DESCRIPTION, description);
				startActivity(in);

			}
		});	    
		// Calling async task to get json
		new GetBroadcast().execute();
	  }

	  @Override
	  public void onListItemClick(ListView l, View v, int position, long id) {
	    // do something with the data
	  }
	  
	  /**
		 * Async task class to get json by making HTTP call
		 * */
		private class GetBroadcast extends AsyncTask<Void, Void, Void> {

/**			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				// Showing progress dialog
				pDialog = new ProgressDialog(getActivity());
				pDialog.setMessage("Please wait...");
				pDialog.setCancelable(false);
				pDialog.show();

			}
**/
			@Override
			protected Void doInBackground(Void... arg0) {
				// Creating service handler class instance
				ServiceHandler sh = new ServiceHandler();

				// Making a request to url and getting response
				String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);

				Log.d("Response: ", "> " + jsonStr);

				if (jsonStr != null) {
					try {
						JSONObject jsonObj = new JSONObject(jsonStr);
						
						// Getting JSON Array node
						broadcast = jsonObj.getJSONArray(TAG_BROADCAST);

						// looping through All Contacts
						for (int i = 0; i < broadcast.length(); i++) {
							JSONObject c = broadcast.getJSONObject(i);
							
							String id = c.getString(TAG_ID);
							String short_description = c.getString(TAG_SHORT_DESCRIPTION);
							String name = c.getString(TAG_NAME);
							String uri = c.getString(TAG_URI);
							String pic_uri = c.getString(TAG_PIC_URI);
							String description = c.getString(TAG_DESCRIPTION);

							// tmp hashmap for single contact
							HashMap<String, String> broadcastUnit = new HashMap<String, String>();

							// adding each child node to HashMap key => value
							broadcastUnit.put(TAG_ID, id);
							broadcastUnit.put(TAG_SHORT_DESCRIPTION, short_description);
							broadcastUnit.put(TAG_NAME, name);
							broadcastUnit.put(TAG_URI, uri);
							broadcastUnit.put(TAG_PIC_URI, pic_uri);
							broadcastUnit.put(TAG_DESCRIPTION, description);

							// adding contact to contact list
							broadcastList.add(broadcastUnit);
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
						getActivity(), broadcastList,
						R.layout.list_item, new String[] { TAG_NAME,
								TAG_ID, TAG_DESCRIPTION, TAG_SHORT_DESCRIPTION, TAG_URI, TAG_PIC_URI }, new int[] { R.id.name,
								R.id.id, R.id.description, R.id.short_description, R.id.uri, R.id.pic_uri });

				setListAdapter(adapter);
			}

		}
	}
