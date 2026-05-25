package main;

import java.awt.Rectangle;
import java.util.Objects;

public class EventRect extends Rectangle{
		
	int defaultX, defaultY, col, row;
	boolean isOneTimeEvent;
	
	public EventRect(int col, int row) {
		this.col = col;
		this.row = row;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(col, row);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		EventRect other = (EventRect) obj;
		return col == other.col && row == other.row;
	}
	
	
	
}
