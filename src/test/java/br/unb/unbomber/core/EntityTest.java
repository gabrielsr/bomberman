package br.unb.unbomber.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class EntityTest {

	@Test
	public void test_addComponent() {
		Component cp = new Component();
		Entity et = new Entity();

		et.setEntityId(10);

		et.addComponent(cp);
		List<Component> lt = et.getComponents();
		assertEquals(lt.get(0).getEntityId(), 10);
	}

	@Test
	public void test_getComponents() {

		Entity et = new Entity();
		List<Component> lc = new ArrayList<Component>();

		assertSame(lc.getClass(), et.getComponents().getClass());
	}

	@Test
	public void test_getEntityId() {

		Entity et = new Entity();

		assertEquals(0, et.getEntityId());
	}

	@Test
	public void test_setEntityId() {

		Entity et = new Entity();
		Component cp = new Component();
		Component cp2 = new Component();
		Component cp3 = new Component();
		et.addComponent(cp);
		et.addComponent(cp2);
		et.addComponent(cp3);

		et.setEntityId(10);

		for (Component component : et.getComponents()) {
			assertEquals(10, component.getEntityId());
		}
	}

	@Test
	public void test_getOnwnerId() {

		Entity et = new Entity();

		assertEquals(0, et.getOwnerId());
	}

	@Test
	public void test_setOnwnerId() {

		Entity et = new Entity();
		et.setOwnerId(10);

		assertEquals(10, et.getOwnerId());
	}

}
