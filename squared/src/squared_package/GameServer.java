package squared_package;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.IOException;

import java.io.InputStreamReader;

import java.io.PrintWriter;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Random;



import squared_package.Game.Player;







/**
 * Classe che implementa la parte server del gioco
 * @author Alessandro
 *
 */

public class GameServer{

	

	/*
	 * Metodo main della classe GameServer
	 * Accetta in ingresso i 2 giocatori e crea la partita
	 */
	public static void main (String[] args) throws Exception{
		int num=1;
		ServerSocket server=new ServerSocket(8001);
		System.out.println("Server on");
		
		try{
			while(true){

				
				Game game=new Game();
				game.setNum(num);

				game.setDim();
				game.setQuotaVittoria();
				
				Player player1= game.new Player(server.accept(), '1');
				System.out.println("player 1 game #" + num + " CONNECTED");



				Player player2 = game.new Player(server.accept(), '2');
				System.out.println("player 2 game #" + num + " CONNECTED");

				player1.setOpponent(player2);
				player2.setOpponent(player1);


				game.currentPlayer = player1;
				player1.start();
				player2.start();



				num++;






			}
		}finally{
			System.out.println("SERVER OFF");
			server.close();
		}
	}
	
		
	


}
/**
 * Classe interna
 * E' il model del gioco
 * @author Alessandro
 *
 */
class Game{

	private HashMap<String, Segmento> listaSegmenti=new HashMap<>();
	private int dim;
	private int quotaVittoria;
	private Cella chiusa1;
	private Cella chiusa2;
	protected Player currentPlayer;
	
	private int num=0;

	public void setNum(int num){
		this.num=num;
	}

	public void setDim(){
		Random random=new Random();
		int num=(random.nextInt(3)+2)*2;
		//int num=4;
		this.dim=num;
	}

	public void setQuotaVittoria(){
		this.quotaVittoria=(int)((Math.pow(dim-1, 2))/2)+1;
	}

	public boolean hasWon(){
		if(currentPlayer.punti>=quotaVittoria){
			return true;
		}return false;
	}

	public void addSegmento (Segmento nuovo){
		listaSegmenti.put(nuovo.id, nuovo);
	}
	/**
	 * Algoritmo che testa, dato un nuovo segmento, che sia o meno stata chiusa una o più celle
	 * @param input:Segmento
	 * @return boolean
	 */
	public boolean isCella(Segmento input){
		boolean cond=false;
		chiusa1 = null;
		chiusa2=null;
		if(listaSegmenti.size()<3) return false;
		Segmento [] paralleli=new Segmento[2];
		paralleli=input.findParalleli();
		Segmento [] paralleli_trovati=new Segmento[2];
		for(int i=0;i<2;i++){
			if(listaSegmenti.containsKey(paralleli[i].id)){
				paralleli_trovati[i]=paralleli[i];
			}
		}


		if(paralleli_trovati[0]!=null){
			Segmento paral_found_sopra=paralleli_trovati[0];
			Segmento cong1=new Segmento(paral_found_sopra.estremoA, input.estremoA);
			if(listaSegmenti.containsKey(cong1.id)){
				Segmento cong2=new Segmento(paral_found_sopra.estremoB, input.estremoB);
				if(listaSegmenti.containsKey(cong2.id)){
					chiusa1= new Cella(input, paral_found_sopra, cong1, cong2);
					cond=true;
				}
			}
		}
		if(paralleli_trovati[1]!=null){
			Segmento paral_found_sotto=paralleli_trovati[1];
			Segmento cong1=new Segmento(input.estremoA, paral_found_sotto.estremoA);
			if(listaSegmenti.containsKey(cong1.id)){
				Segmento cong2=new Segmento(input.estremoB, paral_found_sotto.estremoB);
				if(listaSegmenti.containsKey(cong2.id)){
					chiusa2=new Cella(input,paral_found_sotto,cong1,cong2);
					cond=true;
				}
			}
		}return cond;


	}

	

public Cella[] returnCella(){
		Cella [] ritornate={chiusa1,chiusa2};
		return ritornate;
	}

/**
 * Metodo synchronized che gestisce le operazioni di output dei socket
 * @param segmento:Segmento
 * @param player:Player
 * @return void
 */
	public synchronized void legalMove(Segmento segmento, Player player) {
		if (player == currentPlayer ) {

			if(isCella(segmento)){



				currentPlayer.samePlayerTurn(segmento);

				Cella [] celleChiuse;
				celleChiuse=returnCella();
				StringBuffer celle_buffer=new StringBuffer();
				for(int i=0;i<2;i++){
					if(celleChiuse[i]!=null){
						currentPlayer.inviaCella(celleChiuse[i]);
						currentPlayer.addPunto();
						celle_buffer.append(celleChiuse[i].toStringPuntoChiave()+ "player:" + currentPlayer.mark);
					}
				}



				String celle_id = celle_buffer.toString();
				currentPlayer.opponent.waitTurn(segmento,celle_id);

				if(hasWon()){
					currentPlayer.haVinto();
					currentPlayer.opponent.haPerso();
				}
			}
			else{
				currentPlayer = currentPlayer.opponent;
				currentPlayer.otherPlayerMoved(segmento);
			}


			//return true;
		}
	//return false;
	}




	

	/*
	 * Classe controller per il giocatore che accede alla partita
	 */
	class Player extends Thread{

		private boolean isConnected=true;
		private char mark;
		private Player opponent;
		private int punti;
		private Socket socket;
		private BufferedReader input;
		private PrintWriter output;



		public Player(Socket socket, char mark) {
			this.socket = socket;
			this.mark = mark;
			try {
				input = new BufferedReader(
						new InputStreamReader(socket.getInputStream()));
				output = new PrintWriter(socket.getOutputStream(), true);
				output.println("WELCOME " + mark + " " + dim + " " + num );
				output.println("MESSAGE Waiting for opponent to connect");
			} catch (IOException e) {
				System.out.println("Player died: " + e);
				isConnected=false;
			}
		}

		public void setOpponent(Player opponent) {
			this.opponent = opponent;
		}


/**
 * Metodo run()
 * override dalla classe Thread
 * Gestisce lo scambio di informazioni tra client e server
 */
		public void run() {
			try {
				// The thread is only started after everyone connects.
				output.println("MESSAGE All players connected " + num + " " + quotaVittoria );

				// Tell the first player that it is her turn.
				if (mark == '1') {
					output.println("MESSAGE Your move" );
				}

				// Repeatedly get commands from the client and process them.
				while (true) {
					String command = input.readLine();
					if (command.startsWith("Segmento")) {
						int x1 = Integer.parseInt(command.substring(11,12));
						int y1 = Integer.parseInt(command.substring(13,14));
						Point a=new Point(x1, y1);

						int x2= Integer.parseInt(command.substring(17,18));
						int y2= Integer.parseInt(command.substring(19,20));
						Point b=new Point(x2, y2);

						Segmento nuovo=new Segmento(a, b);

						listaSegmenti.put(nuovo.id,nuovo);
						output.println("VALID_MOVE");

						legalMove(nuovo, currentPlayer);








					}
					if(command.startsWith("QUIT")){
						opponent.output.println("Disconnected");
						return;
					}
					if(command.startsWith("ok?")){
						if(!opponent.isConnected){
							output.println("Error");
						}
					}
				}
			} catch (Exception e) {
				System.out.println("Player died: " + e);
				isConnected=false;

			} finally {
				try {
					this.interrupt();
					socket.close();
				} catch (IOException e) {
					System.out.println("Connection closed");
				}
			}
		}


		public void otherPlayerMoved( Segmento segmento) {
			output.println("OPPONENT_MOVED " + segmento.id );

		}



		public void samePlayerTurn(Segmento segmento){
			output.println("Ripeti " + segmento.id );
		}

		public void waitTurn(Segmento segmento, String celle){
			output.println("Wait "+ segmento.id + celle );
		}

		public void haVinto(){
			output.println("You won");

		}
		public void haPerso(){
			output.println("You lost");
		}


		public void addPunto(){
			this.punti++;
		}

		public void inviaCella(Cella cella){
			Point puntoChiave= cella.findPuntoChiave();

			output.println("NEW SQUARE "+ puntoChiave.toString() + " player:" + currentPlayer.mark + " punti: " + opponent.punti);
		}

		public void opponentExit(){
			output.println("Opponent exit");
		}

	}


}






