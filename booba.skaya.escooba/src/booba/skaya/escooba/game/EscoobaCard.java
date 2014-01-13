package booba.skaya.escooba.game;

import java.io.Serializable;

public class EscoobaCard implements Serializable{
	private static final long serialVersionUID = 1812631153041753584L;
	
	private final EscoobaColor _color;
	private final int _value;
	
	public EscoobaCard(EscoobaColor color, int value) {
		_color = color;
		_value = value;
	}

	@Override
	public boolean equals(Object o) {
		if(o instanceof EscoobaCard && o != null){
			return _color == ((EscoobaCard) o)._color && _value == ((EscoobaCard) o)._value;
		}
		return false;
	}
	


	public String toString(){
		return _value+""+_color.name().substring(0, 1);
	}

	public EscoobaColor getColor() {
		return _color;
	}
	
	public int getValue() {
		return _value;
	}
}
