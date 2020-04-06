import org.easymock.EasyMock;
import org.easymock.Mock;
import org.easymock.MockType;
import org.easymock.TestSubject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.assertj.core.api.Assertions.assertThat;
import static org.easymock.EasyMock.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ExtendWith(EasyMockExtension.class)
public class FriendshipsMongoEasyMockTest {

	@TestSubject
	FriendshipsMongo friendships = new FriendshipsMongo();

	//A nice mock expects recorded calls in any order and returning null for other calls
	@Mock(type = MockType.NICE)
	FriendsCollection friends;

	@Test
	public void mockingWorksAsExpected(){
		Person joe = new Person("Joe");
		//Zapisanie zachowania - co sie ma stac
		expect(friends.findByName("Joe")).andReturn(joe);
		//Odpalenie obiektu do sprawdzenia zachowania
		replay(friends);
		assertThat(friends.findByName("Joe")).isEqualTo(joe);
	}

	@Test
	public void alexDoesNotHaveFriends(){
		assertThat(friendships.getFriendsList("Alex")).isEmpty();
	}

	@Test
	public void joeHas5Friends(){
		List<String> expected = Arrays.asList(new String[]{"Karol","Dawid","Maciej","Tomek","Adam"});
		Person joe = createMock(Person.class);
		expect(friends.findByName("Joe")).andReturn(joe);
		expect(joe.getFriends()).andReturn(expected);
		replay(friends);
		replay(joe);
		assertThat(friendships.getFriendsList("Joe")).hasSize(5).containsOnly("Karol","Dawid","Maciej","Tomek","Adam");
	}

	///NOWE TESTY
	//1
	@Test
	public void joeHasNoFriends(){
		List<String> expected = Arrays.asList(new String[]{});
		Person joe = createMock(Person.class);
		expect(friends.findByName("Joe")).andReturn(joe);
		expect(joe.getFriends()).andReturn(expected);
		replay(friends);
		replay(joe);
		assertThat(friendships.getFriendsList("Joe")).isEmpty();
	}
	//2
	@Test
	public void testGetName(){
		Person joe = createMock(Person.class);
		expect(joe.getName()).andReturn("Joe");
		replay(joe);
		assertThat(joe.getName()).isEqualTo("Joe");
	}
	//3
	@Test
	public void testAreFriends(){
		Person joe = createMock(Person.class);
		List<String> expected = Arrays.asList(new String[]{"Bob","Dawid","Maciej","Tomek","Adam"});

		expect(friends.findByName("Joe")).andReturn(joe);
		expect(joe.getFriends()).andReturn(expected);
		replay(friends);
		replay(joe);

		assertThat(friendships.areFriends("Joe", "Bob")).isTrue();

	}
	//4
	@Test
	public void testaddfriends(){
		ArrayList<String> expected = new ArrayList<String>();
		expected.add("Karol");
		Person joe = createMock(Person.class);
		
		//Act
		joe.addFriend(anyString());
		EasyMock.expectLastCall().andAnswer(() -> {
			expected.add(""+getCurrentArguments()[0]);
			return null;
		}).times(2);
		
		expect(friends.findByName("Joe")).andReturn(joe);
		expect(joe.getFriends()).andReturn(expected);
		replay(friends);
		replay(joe);

		joe.addFriend("Mon");
		joe.addFriend("Yoh");
		assertThat(friendships.getFriendsList("Joe")).hasSize(3).containsOnly("Karol","Mon","Yoh");
	}
	//5
	@Test
	public void testaddfriend(){
		Person joe = createMock(Person.class);
		List<String> expected = new ArrayList<String>();

		//Act
		joe.addFriend(anyString());
		EasyMock.expectLastCall().andAnswer(() -> {
			expected.add(""+getCurrentArguments()[0]);
			return null;
		}).times(1);

		expect(friends.findByName("Joe")).andReturn(joe);
		expect(joe.getFriends()).andReturn(expected);
		replay(friends);
		replay(joe);

		joe.addFriend("Mon");
		assertThat(friendships.areFriends("Joe", "Mon")).isTrue();

	}


}