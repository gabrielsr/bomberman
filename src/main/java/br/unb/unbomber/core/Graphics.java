package br.unb.unbomber.core;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;

public class Graphics implements ApplicationListener {
	
	
	public static int WIDTH;
	public static int HEIGHT;
	
	/**
	 * Esse método é chamado apenas uma vez quando o jogo é iniciado.
	 */
	public void create(){
		
		/**
		 * Pega o width e o height de bomberman-desktop
		 */
		WIDTH = Gdx.graphics.getWidth();
		HEIGHT = Gdx.graphics.getHeight();
		
	}
	
	/**
	 * Método do Game Loop. Chamado constantemente durante o jogo.
	 */
	public void render(){
		
		Gdx.gl.glClearColor(0,0,0,1);
		//Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
	}
	
	/**
	 * Altera o tamanho da tela do jogo.
	 */
	public void resize(int widt, int height){
		
	}
	
	/**
	 * Método responsável por pausar o jogo.
	 */
	public void pause(){
		
	}
	
	/**
	 * Método chamado após o pause(), retoma o jogo.
	 */
	public void resume(){
		
	}
	
	/**
	 * Método chamado apenas uma vez ao término do jogo.
	 */
	public void dispose(){
		
	}

}
