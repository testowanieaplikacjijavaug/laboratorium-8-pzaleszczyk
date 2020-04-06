import java.io.Serializable;
import java.util.List;

import org.junit.platform.commons.util.Preconditions;


public class Note implements Serializable {

	private final String name;
	private final float note;
	
	public static Note of(final String name, final float note) {
		return new Note(name, note);
	}
	
	public String getName() {
		return name;
	}
	
	public float getNote() {
		return note;
	}
	
	private Note(String name, final float note) {
		Preconditions.condition(name != null, "Imi? ucznia nie mo?e by? null");
		name = name.trim();
		Preconditions.condition(!name.trim().isEmpty(), "Imi? ucznia nie mo?e by? puste");
		Preconditions.condition(note >= 2.0f && note <= 6.0f, "Niew?a?ciwa ocena");
		
		this.name = name;
		this.note = note;
	}
	
}