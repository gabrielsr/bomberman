//Classe: KeyListenerEvent
//Versão: 1.00
//Feito pelo Grupo 7

package br.unb.unbomber.event;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JTextArea;

public class KeyListenerEvent{
	
	//Definição das contantes dos valores no teclado para jogadores canhotos.
	final int MOVE_UP_LEFT_HAND = 87;
	final int MOVE_LEFT_LEFT_HAND = 65;
	final int MOVE_DOWN_LEFT_HAND = 83;
	final int MOVE_RIGHT_LEFT_HAND = 68;
	final int DROP_BOMB_LEFT_HAND = 74;
	
	//Definição das contantes dos valores no teclado para jogadores destro.
	final int MOVE_UP_RIGHT_HAND = 38;
	final int MOVE_LEFT_RIGHT_HAND = 37;
	final int MOVE_DOWN_RIGHT_HAND = 40;
	final int MOVE_RIGHT_RIGHT_HAND = 39;
	
	//Definição dos tipos de ação capturados e
	//NULL, quando não há nada sendo pressionada
	public enum KeyType{MOVE_UP,MOVE_RIGHT,MOVE_DOWN,MOVE_LEFT, DROP_BOMB, NULL}
	
	// Irá guardar a tecla pressionada.
	private KeyType Key;
	
	// Áreas de texto que receberão e mostrarão as teclas pressionadas
	JTextArea ReceiveKey;
	JTextArea DisplayKey;
	
	/**
	 * Method KeyListenerEvent construtor da classe
	 */
	public KeyListenerEvent(){
		
		Key = KeyType.NULL;
		JFrame gui = new JFrame();
		
		
		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gui.setTitle("Bomberman's KeyListenerEvent");
		gui.setSize(700,200);
		
		gui.setLocationRelativeTo(null);
		
		DisplayKey = new JTextArea();
		ReceiveKey = new JTextArea();
		
		ReceiveKey.addKeyListener(new KeyListener(){
			
			/**
			 * Method keyPressed define o swicth cases de cada 
			 * butão e seu respectivo valor no teclado. 
			 */
			@Override
			public void keyPressed(KeyEvent e) {
				switch(e.getKeyCode()){
					case MOVE_UP_LEFT_HAND:
					case MOVE_UP_RIGHT_HAND:
						Key = KeyType.MOVE_UP;
						break;
					case MOVE_LEFT_LEFT_HAND:
					case MOVE_LEFT_RIGHT_HAND:
						Key = KeyType.MOVE_LEFT;
						break;
					case MOVE_DOWN_LEFT_HAND:
					case MOVE_DOWN_RIGHT_HAND:
						Key = KeyType.MOVE_DOWN;
						break;
					case MOVE_RIGHT_LEFT_HAND:
					case MOVE_RIGHT_RIGHT_HAND:
						Key = KeyType.MOVE_RIGHT;
						break;
					case DROP_BOMB_LEFT_HAND:
						Key = KeyType.DROP_BOMB;
						break;
					default:
						Key = KeyType.NULL;
				}
				getKey();
			}

			/**
			 *  Method keyReleased retorna um valor NULL quando nenhuma tecla está pressionada
			 */
			@Override
			public void keyReleased(KeyEvent e) {
				Key = KeyType.NULL;
			}

			@Override
			public void keyTyped(KeyEvent e) {
			}
			
		});
		
		gui.add(ReceiveKey, BorderLayout.NORTH);
		gui.add(DisplayKey, BorderLayout.CENTER);
		gui.setVisible(true);
	}
	
	/**
	 * Setter e Getter do KeyType Key, variável que armazená a tecla pressionada. 
	 */
	public KeyType getKey(){
		return Key;
	}
	
	public void setKey(KeyType Key){
		this.Key = Key;
	}
	
}

/*
*  ------- KEY CODES -------
*   -------  W  =  87 ------- 
*   -------  A  =  65 -------
*   -------  S  =  83 -------
*   -------  D  =  68 -------
*   -------  up =  38 -------
*   -----  left =  37 -------
*   -----  down =  40 -------
*   ----  right =  39 -------
*   -------  J  =  74 -------   DROP BOMB!
*/