import static org.assertj.core.api.Assertions.assertThat;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.easymock.EasyMock;
import org.easymock.IAnswer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class NoteTest {

	NotesStorage storage;
	NotesService service;

	@BeforeEach
	public void setup() {
		storage = createMock(NotesStorage.class);
		service = createMock(NotesService.class);
	}

	@AfterEach
	public void cleanup() {
		storage = null;
		service = null;
	}

	@Test
	public void trivial() {
		assertTrue(true);
	}

	@Test
	public void testGetAllNotesMock() {
		NotesStorage storage = createMock(NotesStorage.class);
		List<Note> expected = Arrays.asList(new Note[]{
				Note.of("Andrew",5),
				Note.of("Andrew", 4)
		});

		expect(storage.getAllNotesOf("Andrew")).andReturn(expected);
		replay(storage);
		assertThat(storage.getAllNotesOf("Andrew")).hasSize(2).containsAll(expected);

		EasyMock.verify(storage);
	}


	@Test
	public void testAverageGrade() {
		float expected = 3.7f;
		expect(service.averageOf("Andrew")).andReturn(expected);
		replay(service);

		assertEquals(expected, service.averageOf("Andrew"));
		EasyMock.verify(service);
	}

	@Test
	public void testAverageGradeNotExisting() {
		EasyMock.expect(service.averageOf("Ox")).andThrow(new IllegalArgumentException());
		EasyMock.replay(service);
		assertThrows(IllegalArgumentException.class, () -> {
			service.averageOf("Ox");
		});

		EasyMock.verify(service);

	}

	@Test
	public void testAverageGradeEmpty() {

		EasyMock.expect(service.averageOf("")).andThrow(new IllegalArgumentException("Imię ucznia nie może być puste"));
		EasyMock.replay(service);
		assertThrows(
				IllegalArgumentException.class,
				() -> { service.averageOf("");},
				"Imię ucznia nie może być puste"
				);

		EasyMock.verify(service);
	}

	@Test
	public void testAverageGradeNull() {

		EasyMock.expect(service.averageOf(null)).andThrow(new IllegalArgumentException("Imię ucznia nie może być null"));
		EasyMock.replay(service);
		assertThrows(
				IllegalArgumentException.class,
				() -> { service.averageOf(null);},
				"Imię ucznia nie może być null"
				);

		EasyMock.verify(service);
	}




	@Test
	public  void testClear(){
		//Prepare
		ArrayList<Note> allnotes = new ArrayList<Note>();
		allnotes.add(Note.of("John", 5.0f));
		boolean expected = true;

		//Act
		service.clear();
		EasyMock.expectLastCall().andAnswer(() -> {
			allnotes.clear();
			return null;
		}).times(1);

		replay(service);

		service.clear();
		//assert
		assertTrue(allnotes.isEmpty());
		EasyMock.verify(service);
	}

	@Test
	public  void testAddTwice(){
		//Prepare
		Note input = Note.of("Lmal", 3.0f);
		ArrayList<Note> expected = new ArrayList<Note>();		

		//Act
		storage.add(input);
		EasyMock.expectLastCall().andAnswer(() -> {
			expected.add(input);
			return null;
		}).times(2);
		replay(storage);

		storage.add(input);
		storage.add(input);

		//Assert
		assertEquals("Lmal", expected.get(0).getName());
		EasyMock.verify(storage);
	}

	@Test
	public void testAdd() {
		//Prepare
		Note input = Note.of("Lmal", 3.0f);
		ArrayList<Note> expected = new ArrayList<Note>();		

		//Act
		storage.add(input);
		EasyMock.expectLastCall().andAnswer(() -> {
			expected.add(input);
			return null;
		}).times(1);
		replay(storage);

		storage.add(input);

		//Assert
		assertEquals("Lmal", expected.get(0).getName());
		EasyMock.verify(storage);
	}


}