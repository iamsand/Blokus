package framework;

import java.util.ArrayList;
import java.util.Arrays;

// This represents the shapes a player possess.
public class Hand {

	public static final ArrayList<Shape>	allShapes	= new ArrayList(Arrays.asList(Shape.values()));

	private Color							c;
	private ArrayList<Shape>				hand		= new ArrayList<Shape>();

	public Hand(Color c) {
		this.c = c;
		hand = (ArrayList<Shape>) allShapes.clone();
	}
	
	public void remove(int index){
		if (index < hand.size())
			hand.remove(index);
	}
}
