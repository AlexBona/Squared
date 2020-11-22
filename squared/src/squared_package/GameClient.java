package squared_package;

import java.awt.Color;

import java.awt.Cursor;
import java.awt.Dimension;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import java.net.Socket;
import java.util.Locale;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.Font;

import java.awt.BorderLayout;

import javax.imageio.ImageIO;

import javax.swing.JButton;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.awt.image.BufferedImage;

import java.awt.Point;


import javax.swing.JLabel;
import javax.swing.JOptionPane;


import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;




import javax.swing.event.ChangeListener;

import javax.swing.event.ChangeEvent;

import javax.swing.JEditorPane;

import javax.swing.JSlider;
import net.miginfocom.swing.MigLayout;


import javax.swing.UIManager;

import javax.swing.ImageIcon;

/**
 * Classe che implementa la parte Client del gioco
 * @author Alessandro
 *
 */
public class GameClient extends  JFrame {
	
	    
	    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;



		JFrame frame=new JFrame("game");

	   

	    private static int PORT = 8001;
	    private Socket socket;
	    private BufferedReader in;
	    private PrintWriter out;
	    
	    private final static int startHeight=400;

	    private final static int startWidth=550;
	   
	 

	    private char mark;
		
	    PanelGame panelGame;
	    Segmento nuovo;

	    private BufferedImage audio;

		private JLabel messageLabel;



		private JProgressBar progressBar_1;

		private Color backgroundGame=Color.YELLOW;
		private Color backgroundSettings=new Color(255, 215, 0);
		private Color writes=Color.BLACK;

		int dim;



		private JLabel editorPane_1;



		private JLabel editorPane_2;



		private JProgressBar progressBar_2;



		private JSlider slider_volume;

		
	   

	   

		private static int livVolume;



		private final static int minHeight=300;



		private final static int minWidth=500;
		private JPanel panel_2;
		private JPanel panel_3;
		private JPanel panel_5;
		private JPanel panel_7;
		private JPanel panel_8;
		private JPanel panel_6;
		private JPanel panel_10;
		private JPanel panel_1;
		private JPanel panel_punti;
		private JPanel punti_1;
		private JPanel punti_2;
		private JButton btn_volume;
		private JLabel dtrpnViews;
		private JPanel panel_gm;



		private Locale locale;

		
	
	/**
	 * Costruttore della classe GameClient
	 */
	public GameClient(String serverAddress) throws Exception {
		
		

		locale = new Locale("en");
		setLocale(locale);
		
		Dimension preferredProgressBar=new Dimension(80, 600);
		Dimension prefferedPanelSotto=new Dimension(80, 100);
		
		
		
		
		
		
		
		
		socket = new Socket(serverAddress, PORT);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));

        in = new BufferedReader(new InputStreamReader(
        		socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
		
        startSound();
		
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
		        closingWindow();
		    }
		});
		
		
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		messageLabel = new JLabel("New label");
		messageLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel.add(messageLabel, BorderLayout.SOUTH);
		
		panel_1 = new JPanel();
		
		panel_1.repaint();
		
		panel.add(panel_1);
		panel_1.setLayout(new MigLayout("", "[50.00%,grow][50%,grow]", "[100%,grow]"));
		
	
		
		panel_gm = new JPanel();
		
		panel_1.add(panel_gm, "cell 0 0,grow");
		panel_gm.setLayout(new BorderLayout(0, 0));
		//panel_gm.setPreferredSize(new Dimension(200,400));
		panel_gm.setMinimumSize(new Dimension(startWidth/3, startWidth/3));
		
		panelGame = new PanelGame(this);
		panel_gm.add(panelGame);
		panelGame.setEnabled(false);
		panelGame.addNotify();
		
		panel_2 = new JPanel();
		
		panel_gm.add(panel_2, BorderLayout.NORTH);
		
		panel_5 = new JPanel();
		
		panel_gm.add(panel_5, BorderLayout.SOUTH);
		
		panel_3 = new JPanel();
		
		panel_gm.add(panel_3, BorderLayout.WEST);
		
		panel_7 = new JPanel();
		
		panel_gm.add(panel_7, BorderLayout.EAST);
		
		
		
		panel_punti = new JPanel();
		
		
		panel_1.add(panel_punti, "cell 1 0,alignx center,aligny center, grow");
		
		panel_punti.setLayout(new MigLayout("", "[50%,grow][50%]", "[100%,grow]"));
		
		
		punti_1 = new JPanel();
		punti_1.repaint();
		
		
		
		panel_punti.add(punti_1, "cell 0 0,alignx center,aligny center,grow");
		
		punti_1.setLayout(new MigLayout("", "[100%,grow,center]", "[grow][70%,grow][10%,grow,center][5%,grow]"));
		
		
		
		progressBar_1 = new JProgressBar();
		
		progressBar_1.setMinimum(0);
		
		progressBar_1.setPreferredSize(preferredProgressBar);
		progressBar_1.setOrientation(SwingConstants.VERTICAL);
		
		editorPane_1 = new JLabel();
		
		editorPane_1.setFont(UIManager.getFont("Viewport.font"));
		editorPane_1.setText("       YOU");
		
		
		punti_1.add(editorPane_1, "cell 0 2,alignx center,aligny center");
		editorPane_1.setPreferredSize(prefferedPanelSotto);
		
		panel_6 = new JPanel();
		
		punti_1.add(panel_6, "cell 0 3");
		panel_6.setPreferredSize(prefferedPanelSotto);
		
		
	
		
		
		punti_2 = new JPanel();
		panel_punti.add(punti_2, "cell 1 0,alignx center,grow");
		punti_2.repaint();
		
		punti_2.setLayout(new MigLayout("", "[100%,grow,center]", "[grow][70%,grow][10%,grow,center][5%,grow]"));
		
		progressBar_2 = new JProgressBar();
		
		progressBar_2.setMinimum(0);
		progressBar_2.setPreferredSize(preferredProgressBar);
		progressBar_2.setOrientation(SwingConstants.VERTICAL);
		
		
			punti_1.add(progressBar_1, "cell 0 1,alignx center,aligny center");
			punti_2.add(progressBar_2, "cell 0 1,alignx center,aligny center");
		
		editorPane_2 = new JLabel();
		
		editorPane_2.setFont(UIManager.getFont("Viewport.font"));
		
		editorPane_2.setPreferredSize(new Dimension(80, 100));
		editorPane_2.setText("  OPPONENT");
		
		punti_2.add(editorPane_2, "cell 0 2");
		
		
		panel_10 = new JPanel();
		
		punti_2.add(panel_10, "cell 0 3");
		panel_10.setPreferredSize(prefferedPanelSotto);
		
		panel_8 = new JPanel();
		
		panel.add(panel_8, BorderLayout.NORTH);
		
		try {
			audio= (BufferedImage)ImageIO.read(new File("resources/audio.png"));
		} catch (Exception e1) {
		
			e1.printStackTrace();
		}
		
				
		
		btn_volume = new JButton("\r\n");
		btn_volume.setBorderPainted(false);
		
		btn_volume.setIcon(new ImageIcon(audio));
		
		panel_8.add(btn_volume, "cell 0 0,alignx right,aligny center");
		btn_volume.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				slider_volume.setValue(-60);
				
			}
		});
		
		
		slider_volume = new JSlider();
		slider_volume.setBorder(UIManager.getBorder("EditorPane.border"));
		
		panel_8.add(slider_volume, "cell 1 0,alignx left,aligny top");
		slider_volume.setMinimum(-60);
		slider_volume.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				livVolume=slider_volume.getValue();	
				
			}
		});
		
		
		
		
	
		
		
			
		
		slider_volume.setValue(-30);
		slider_volume.setMaximum(0);
		slider_volume.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		
		dtrpnViews = new JLabel();
		panel_8.add(dtrpnViews);
		
		
				dtrpnViews.setFont(UIManager.getFont("Viewport.font"));
				dtrpnViews.setText("VIEWS     ");
		
	
				JComboBox<Views> jComboBox = new JComboBox<Views>(Views.values());
				JComboBox<Views> comboBox = jComboBox;
				comboBox.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						Views item= (Views) comboBox.getSelectedItem();
						Color cl1=item.getCella1();
						Color cl2=item.getCella2();
						backgroundGame=item.getBackgroundGame();
						backgroundSettings=item.getBackgroundSettings();
						writes=item.getWrites();
						colorize();
						frame.repaint();
						
						panelGame.setPlColor(cl1, '1');
						panelGame.setPlColor(cl2, '2');
						panelGame.backgroundGame=item.getBackgroundGame();
						panelGame.lines=item.getLines();
						panelGame.lastLine=item.getLastLine();
						
						panelGame.repaint();
						
						progressBar_1.setForeground(cl1);
						progressBar_2.setForeground(cl2);
						
					}
				});
				
				panel_8.add(comboBox);
		
				colorize();

     
	}
	/**
	 * Metodo che colora i pannelli, cambia a seconda della view selezionata dalla combobox
	 */
	public void colorize(){
		panel_1.setBackground(backgroundSettings);
		panel_2.setBackground(backgroundSettings);
		panel_5.setBackground(backgroundSettings);
		panel_3.setBackground(backgroundSettings);
		panel_7.setBackground(backgroundSettings);
		panel_punti.setBackground(backgroundSettings);
		punti_1.setBackground(backgroundGame);
		progressBar_1.setForeground(Color.BLUE);
		editorPane_1.setBackground(backgroundGame);
		editorPane_1.setForeground(writes);
		panel_6.setBackground(backgroundGame);
		progressBar_2.setForeground(Color.RED);
		editorPane_2.setForeground(writes);
		editorPane_2.setBackground(backgroundGame);
		panel_10.setBackground(backgroundGame);
		panel_8.setBackground(backgroundSettings);
		btn_volume.setForeground(backgroundSettings);
		btn_volume.setBackground(backgroundSettings);
		slider_volume.setBackground(backgroundSettings);
		punti_2.setBackground(backgroundGame);
		dtrpnViews.setBackground(backgroundSettings);
		panel_gm.setBackground(backgroundSettings);
		progressBar_1.setBackground(backgroundSettings);
		progressBar_2.setBackground(backgroundSettings);
		dtrpnViews.setForeground(writes);
	}
	
	
	/**
	 * Metodo che sceglie come nominare le progressBar
	 */
	
	
	public void closingWindow(){
		JOptionPane.setDefaultLocale(locale);
		if (JOptionPane.showConfirmDialog(frame, 
	            "Are you sure to close this window?", "Really Closing?", 
	            JOptionPane.YES_NO_OPTION,
	            JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
	        	out.println("QUIT");
	            System.exit(0);
	            
	        }
	}
	
	public void setProgressBars(){
		if(this.mark=='1'){
			punti_1.add(progressBar_1, "cell 0 1,alignx center,aligny center");
			punti_2.add(progressBar_2, "cell 0 1,alignx center,aligny center");
		}if (this.mark=='2'){
			punti_2.add(progressBar_1, "cell 0 1,alignx center,aligny center");
			punti_1.add(progressBar_2, "cell 0 1,alignx center,aligny center");
		}
		
	}
	
/**
 * Metodo che gestisce la comunicazione client-server
 * @throws Exception
 */
	public void play() throws Exception{
		
		 String response;
	        try {
	            response = in.readLine();
	            if (response.startsWith("WELCOME")) {
	                mark = response.charAt(8);
	                setProgressBars();
	                int num=Integer.parseInt(response.substring(12));
	               
	                int dim=Integer.parseInt(response.substring(10, 11));
	                this.dim=dim;
	                int quota= (int)((Math.pow(dim-1, 2))/2)+1;
	                progressBar_1.setMaximum(quota);
	                progressBar_2.setMaximum(quota);
	                panelGame.dim=dim;
	                if(mark=='1'){
	                	panelGame.setEnabled(true);
	                	panelGame.setStato(true);
	                	
	                	
	                	panelGame.requestFocus();
	                	frame.toFront();
	                	frame.setAlwaysOnTop(true);
	                }
	                if(mark=='2'){
	                	out.println("ok?");
	                }
	                frame.setTitle("Squared - Player " + mark + " Game # " + num);
	            }
	            while (true) {
	            	panelGame.ottieniPoints();
	            	
	            	
	                response = in.readLine();
	                if (response.startsWith("VALID_MOVE")) {
	                	
	                	messageLabel.setText("Valid move, please wait");
	                	
	                	
	                  
	                } else if (response.startsWith("OPPONENT_MOVED")) {
	                	panelGame.setEnabled(true);
                		panelGame.setStato(true);
                		
                		
                		panelGame.requestFocus();
                		frame.toFront();
                		frame.setAlwaysOnTop(true);
	                	messageLabel.setText(response);
	                	int x1 = Integer.parseInt(response.substring(26,27));
                		int y1 = Integer.parseInt(response.substring(28,29));
                		Point a=new Point(x1, y1);

                		int x2= Integer.parseInt(response.substring(32,33));
                		int y2= Integer.parseInt(response.substring(34,35));
                		Point b=new Point(x2, y2);

                		Segmento nuovo=new Segmento(a, b);
                		panelGame.addSegmenti(nuovo);
                		panelGame.addToStack(nuovo);
                		

	                    messageLabel.setText("Opponent moved, your turn");
	                }
	                else if(response.startsWith("Ripeti")){
	                	messageLabel.setText("Your turn again");
	                	panelGame.setEnabled(true);
	                	panelGame.setStato(true);
	                	
	                }
	                else if(response.startsWith("Wait")){
	                	
	                	panelGame.setEnabled(false);
	                	panelGame.setStato(false);
	                	messageLabel.setText("Please " + response.substring(0,5));
	                	int x1 = Integer.parseInt(response.substring(16,17));
                		int y1 = Integer.parseInt(response.substring(18,19));
                		Point a=new Point(x1, y1);

                		int x2= Integer.parseInt(response.substring(22,23));
                		int y2= Integer.parseInt(response.substring(24,25));
                		Point b=new Point(x2, y2);

                		Segmento nuovo=new Segmento(a, b);
                		panelGame.addSegmenti(nuovo);
                		panelGame.addToStack(nuovo);
                		
                		int xc1= Integer.parseInt(response.substring(39,40));
                		int yc1= Integer.parseInt(response.substring(42,43));
                		
                		
                		
                		
                		
                		Cella cellaChiusa1=new Cella(xc1, yc1,otherMark(mark));
                		panelGame.addCelle(cellaChiusa1);
                		
                		if(response.length()>55){
                			int xc2= Integer.parseInt(response.substring(65,66));
                    		int yc2= Integer.parseInt(response.substring(68,69));
                    		

                    		Cella cellaChiusa2=new Cella(xc2, yc2,otherMark(mark));
                    		panelGame.addCelle(cellaChiusa2);
                		}
                		
                		progressBar_1.setValue(panelGame.getPunti(mark));
                		
                		repaint();
                		progressBar_2.setValue(panelGame.getPunti(otherMark(mark)));
                		repaint();
	                }
	                else if(response.startsWith("NEW")){
	                	cellClosedSound();
	                	int x= Integer.parseInt(response.substring(28, 29));
	                	int y=Integer.parseInt(response.substring(32, 33));
	                	char mark=response.charAt(42);
	                	Cella nuovo=new Cella(x, y,mark);
	                

	                	progressBar_1.setValue(panelGame.getPunti('1'));
	                	progressBar_2.setValue(panelGame.getPunti('2'));
	                	repaint();
	                	panelGame.addCelle(nuovo);
	                }

	                else if(response.startsWith("You")){
	                	messageLabel.setText(response);
	                	break;
	                	
	                } 
	                else if (response.startsWith("MESSAGE")) {

	                	if(response.length()>30){
	                		messageLabel.setText(response.substring(8,29));
	                	}
	                	else{

		                	messageLabel.setText(response.substring(8));

	                	}

	                }
	                else if(response.startsWith("Disconnected")){
	                	messageLabel.setText("Opponent disconnencted");
	                	connectionClosed();
	                	break;
	                }
	                else if(response.startsWith("Error")){
	                	messageLabel.setText("No players available");
	                	errorMessage();
	                	break;
	                }
	               

	            }
	            out.println("quit");
	        }
	        catch(Exception e){
	        	
	        	e.getMessage();
	        }

	       
	        
	        finally {
	            socket.close();
	        }
	}
	
	private void errorMessage() {
		JOptionPane.setDefaultLocale(locale);
		JOptionPane.showMessageDialog(frame, "No players available, try later", "Sorry...", JOptionPane.INFORMATION_MESSAGE);
	
}
	public void connectionClosed(){
		JOptionPane.setDefaultLocale(locale);
		JOptionPane.showMessageDialog(frame, "Opponent gave up!", "Warning", JOptionPane.ERROR_MESSAGE);
	}
	
	public void setProgressBars(int [] points){
		 progressBar_1.setValue(points[0]);
		 progressBar_2.setValue(points[1]);
		 
	}
	
	public void opponentDisconnected() {
		
		JOptionPane.setDefaultLocale(locale);
		JOptionPane.showConfirmDialog(frame,
	            "Want to play again?",
	            "Squared is Fun Fun Fun",
	            JOptionPane.YES_NO_OPTION);
		
	}

	public Segmento riceviSegmento(){
		return panelGame.getNuovoSegmento();
	}
	
	public void inviaSegmento(Segmento daInviare){
		out.println(daInviare.id);
	}
	public char otherMark(char mark){
		char out = 0;
		if(mark=='1'){
			out='2';
		}
		if(mark=='2'){
			out='1';
		}
		return out;
	}
	
	
	
	private boolean wantsToPlayAgain() {
        int response = JOptionPane.showConfirmDialog(frame,
            "Want to play again?",
            "Squared is Fun Fun Fun",
            JOptionPane.YES_NO_OPTION);
        frame.dispose();
        return response == JOptionPane.YES_OPTION;
    }
	
	
/**
 * Metodo main della classe GameClient
 * @param args
 * @throws Exception
 */
	public static void main(String[] args) throws Exception {
        while (true) {
        	

        	String serverAddress = (args.length == 0) ? "localhost" : args[1];
        	GameClient client = new GameClient(serverAddress);
        	
        	
        	
        	
        	client.frame.pack();
        	client.frame.setSize(startWidth, startHeight);
        	client.frame.setMinimumSize(new Dimension(minWidth, minHeight));
        	client.frame.setVisible(true);
        	client.frame.setEnabled(true);
        	
        	client.play();
        	
        	
        	
        	
        	if (!client.wantsToPlayAgain()) {
                break;
            }
        }
	}

	public static int getVolume() {
		return livVolume;
		
	}
	public void startSound(){
		GameSound.START.play();
	}
	
	public void cellClosedSound(){
		GameSound.CELL_CLOSED.play();
	}
	
	
}
