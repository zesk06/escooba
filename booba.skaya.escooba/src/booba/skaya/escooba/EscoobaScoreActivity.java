package booba.skaya.escooba;

import java.util.ArrayList;
import java.util.Arrays;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import booba.skaya.escooba.util.Strings;

public class EscoobaScoreActivity extends Activity {

	static String SCORE_EXTRA = "booba.skaya.extra.score";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_score);
		setupActionBar();
		Intent intent = getIntent();
		if(intent != null ){
			@SuppressWarnings("unchecked")
			ArrayList<ArrayList<Integer>> scores = (ArrayList<ArrayList<Integer>>) intent.getSerializableExtra(SCORE_EXTRA);
			TextView text = (TextView) findViewById(R.id.score_text);
			String scoreText = "P0\tP1\tP2\tP3\n";
			scoreText +=       "________________________\n";
			Integer[] total = new Integer[scores.get(0).size()];
			Arrays.fill(total, 0);
			for(ArrayList<Integer> score : scores){
				scoreText += Strings.join(" | ", score) +"\n";
				total[0] += score.get(0);
				total[1] += score.get(1);
				total[2] += score.get(2);
				total[3] += score.get(3);
				scoreText += "________________________\n";
			}
			scoreText += "====================\n";
			scoreText += Strings.join(" | ", Arrays.asList(total));
			text.setText(scoreText);
		}
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {
		getActionBar().setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.score, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			setResult(RESULT_OK);
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void againButtonAction(View view){
		setResult(RESULT_OK);
		finish();
	}
}
