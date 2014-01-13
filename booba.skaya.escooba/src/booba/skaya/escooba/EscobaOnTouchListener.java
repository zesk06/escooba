package booba.skaya.escooba;

import booba.skaya.escooba.view.CardView;
import android.view.View;
import android.view.View.OnClickListener;



public class EscobaOnTouchListener implements OnClickListener {

	@Override
	public void onClick(View cardAsImageView) {
		((CardView) cardAsImageView).toggle();
	}
}
