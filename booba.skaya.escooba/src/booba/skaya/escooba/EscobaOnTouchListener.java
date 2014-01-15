package booba.skaya.escooba;

import booba.skaya.escooba.view.CardView;
import android.view.View;
import android.view.View.OnClickListener;



public class EscobaOnTouchListener implements OnClickListener {

	
	private Escooba _escooba;

	EscobaOnTouchListener(Escooba escooba){
		_escooba = escooba;
	}
	
	@Override
	public void onClick(View cardAsImageView) {
		((CardView) cardAsImageView).toggle();
		_escooba.cardToggled();
	}
}
