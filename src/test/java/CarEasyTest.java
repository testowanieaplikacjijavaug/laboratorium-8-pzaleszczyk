import org.easymock.EasyMock;
import org.easymock.*;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import static org.assertj.core.api.Assertions.*;
import org.assertj.core.api.SoftAssertions;


public class CarEasyTest {
	private Car myFerrari = EasyMock.createMock(Car.class);

	@Test
	public void test_instance_car(){
		assertTrue(myFerrari instanceof Car);
	}

	@Test
	public void test_default_behavior_needsFuel(){
		assertFalse(myFerrari.needsFuel(), "New test double should return False as boolean");
	}

	@Test
	public void test_default_behavior_temperature(){
		assertEquals(0.0, myFerrari.getEngineTemperature(), "New test double should return 0.0");
	}

	@Test
	public void test_stubbing_mock(){
		EasyMock.expect(myFerrari.needsFuel()).andReturn(true);
		EasyMock.replay(myFerrari);
		assertTrue(myFerrari.needsFuel());

	}

	@Test
	public void test_exception(){
		EasyMock.expect(myFerrari.needsFuel()).andThrow(new RuntimeException());
		EasyMock.replay(myFerrari);
		assertThrows(RuntimeException.class, () -> {
			myFerrari.needsFuel();
		});
	}

	//// NOWE TESTY
	//    boolean needsFuel();
	//    double getEngineTemperature();
	//    void driveTo(String destination);

	//1
	@Test
	public void test_velocity() {
		EasyMock.expect(myFerrari.getVelocity()).andReturn(100);
		EasyMock.expect(myFerrari.getVelocity()).andReturn(120);
		EasyMock.expect(myFerrari.getVelocity()).andReturn(130);
		EasyMock.replay(myFerrari);

		SoftAssertions softly = new SoftAssertions();
		softly.assertThat(myFerrari.getVelocity()).isEqualTo(100);
		softly.assertThat(myFerrari.getVelocity()).isEqualTo(120);
		softly.assertThat(myFerrari.getVelocity()).isEqualTo(130);
		softly.assertAll();

		EasyMock.verify(myFerrari);
	}
	//2
	@Test
	public void test_velocity_exception_1() {
		EasyMock.expect(myFerrari.getVelocity()).andThrow(new IllegalArgumentException());
		EasyMock.replay(myFerrari);

		assertThatThrownBy(() -> { myFerrari.getVelocity(); })
		.isInstanceOf(IllegalArgumentException.class);
		
		EasyMock.verify(myFerrari);
	}
	//3
	@Test
	public void test_velocity_exception_2() {
		EasyMock.expect(myFerrari.getVelocity()).andThrow(new IndexOutOfBoundsException());
		EasyMock.replay(myFerrari);

		assertThatThrownBy(() -> { myFerrari.getVelocity(); })
		.isInstanceOf(IndexOutOfBoundsException.class);
		
		EasyMock.verify(myFerrari);
	}
	//4
	@Test
	public void test_enginetemp() {
		EasyMock.expect(myFerrari.getEngineTemperature()).andReturn(100.0);
		EasyMock.replay(myFerrari);
		assertThat(myFerrari.getEngineTemperature()).isEqualTo(100.0);
		
		EasyMock.verify(myFerrari);
	}
	//5
	@Test
	public void test_enginetemp_exception() {
		EasyMock.expect(myFerrari.getEngineTemperature()).andThrow(new RuntimeException());
		EasyMock.replay(myFerrari);
		assertThrows(RuntimeException.class, () -> {
			myFerrari.getEngineTemperature();
		});
		
		EasyMock.verify(myFerrari);
	}

}