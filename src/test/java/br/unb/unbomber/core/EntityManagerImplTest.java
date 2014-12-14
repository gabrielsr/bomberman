package br.unb.unbomber.core;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import br.unb.unbomber.component.Explosion;
import br.unb.unbomber.event.ExplosionStartedEvent;

public class EntityManagerImplTest {

	@Test
	public void initiTest() {
		EntityManager instance = EntityManagerImpl.getInstance();
		assertNotNull(instance);
	}

	@Test
	public void addEventTest() throws Exception {
		EntityManager instance = EntityManagerImpl.getInstance();
		Event event = new Event();
		instance.addEvent(event);
	}

	@Test
	public void addComponentTest() throws Exception {
		EntityManager instance = EntityManagerImpl.getInstance();
		Component component = new Component();
		instance.addComponent(component);
	}

	@Test
	public void createEntityTest() {
		EntityManager instance = EntityManagerImpl.getInstance();
		Entity entity = instance.createEntity();
		assertNotNull(entity);
	}

	@Test
	public void updateTest() throws Exception {
		EntityManager instance = EntityManagerImpl.getInstance();
		Component component = new Component();
		Entity entity = instance.createEntity();
		component.setEntityId(entity.getEntityId());
		instance.update(entity);
	}

	@Test
	public void getEventsTest() {
		EntityManager instance = EntityManagerImpl.getInstance();
		ExplosionStartedEvent event = new ExplosionStartedEvent();
		instance.addEvent(event);
		List<Event> events = instance.getEvents(ExplosionStartedEvent.class);
		assertNotNull(events);
	}

	@Test
	public void getComponentsTest() {
		EntityManager instance = EntityManagerImpl.getInstance();
		Explosion component = new Explosion();
		instance.addComponent(component);
		List<Component> components = instance.getComponents(Explosion.class);
		assertNotNull(components);
	}

	@Test
	public void getComponentTest() {
		EntityManager instance = EntityManagerImpl.getInstance();
		Explosion component = new Explosion();
		instance.addComponent(component);
		Component componentRetrieved = instance.getComponent(Explosion.class,
				component.getEntityId());
		assertEquals(componentRetrieved, component);
	}

	@Test
	public void removeEventTest() throws Exception {
		EntityManager instance = EntityManagerImpl.getInstance();
		Event event = new Event();
		instance.addEvent(event);
		instance.remove(event);
	}

	@Test
	public void removeComponentTest() throws Exception {
		EntityManager instance = EntityManagerImpl.getInstance();
		Component component = new Component();
		instance.addComponent(component);
		instance.remove(component);
	}

	@Test
	public void removeComponentById() throws Exception {
		EntityManager instance = EntityManagerImpl.getInstance();
		Component component = new Component();
		instance.addComponent(component);
		instance.removeComponentByEntityId(Component.class,
				component.getEntityId());
	}
	
	@Test
	public void removeEntityTest() throws Exception {
		EntityManager instance = EntityManagerImpl.getInstance();
		Component component = new Component();
		Entity entity = instance.createEntity();
		component.setEntityId(entity.getEntityId());
		instance.update(entity);
		instance.remove(entity);
	}
	
	@Test
	public void removeEntityById() throws Exception {
		EntityManager instance = EntityManagerImpl.getInstance();
		Component component = new Component();
		Entity entity = instance.createEntity();
		component.setEntityId(entity.getEntityId());
		instance.update(entity);
		instance.removeEntityById(entity.getEntityId());
	}
}
