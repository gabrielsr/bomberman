package br.unb.unbomber.match;

import java.util.Date;

import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.utils.ImmutableBag;

public class GameMatchTestCase {

	
	/**
	 * TODO test the rate control
	 * @param args
	 */
	public void frameRateControlTestCase(){
		
		GameMatch testMatch = new TargetFrameRateMatch(1);
		
		/** A Sysout System */
		EntitySystem printSystem = new EntitySystem(null){

			@Override
			protected void processEntities(ImmutableBag<Entity> entities) {
				System.out.println(new Date());
				
			}
		};
		
		testMatch.addSystem(printSystem);
		
		for(int i = 0; i<1000; i++){
			testMatch.update(0.0f);
		}
	}

}
