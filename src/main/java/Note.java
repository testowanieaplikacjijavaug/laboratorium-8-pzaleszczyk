import java.io.Serializable;
import java.util.List;


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
		org.assertj.core.util.Preconditions.checkArgument(name != null, "Imi? ucznia nie mo?e by? null");
		name = name.trim();
		org.assertj.core.util.Preconditions.checkArgument(!name.trim().isEmpty(), "Imi? ucznia nie mo?e by? puste");
		org.assertj.core.util.Preconditions.checkArgument(note >= 2.0f && note <= 6.0f, "Niew?a?ciwa ocena");
		
		this.name = name;
		this.note = note;
	}
	
}