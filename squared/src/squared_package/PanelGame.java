package squared_package;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import java.awt.Point;
import java.awt.RenderingHints;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.Stack;
import java.util.Vector;

import javax.swing.JComponent;







/**
 * Classe che implementa il piano di gioco
 * @author Alessandro
 *
 */

public class PanelGame extends  JComponent implements MouseListener, MouseMotionListener, KeyListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Point mousePos=new Point(1, 1);
	private int size;
	int dim;
	
	private Point firstClick=null;
	private Point secondClick=null;
	private Point currentMousePos=new Point(1, 1);
	private int nRepeat=0;
	
	private Segmento nuovoSegmento=null;
	private GameClient client;
	 boolean stato=false;
	
	
	
	Color colorPl1=Color.BLUE;
	Color colorPl2=Color.RED;
	Color backgroundGame=Color.YELLOW;
	
	Color lines=Color.BLACK;
	Color lastLine=Color.RED;
	
	private Vector<Cella> listaPuntiChiave=new Vector<>();
	
	private HashMap<String,Segmento> listaSegmenti= new HashMap<>();
	
	private Stack<Segmento> stackSegmenti = new Stack<>();
	
	private int [] points={0,0};
	private int redraw;
	
	
	public PanelGame( GameClient client){
		this();
		
		this.client=client;
		
		addMouseMotionListener(this);
		addMouseListener(this);
		this.addKeyListener(this);
		this.setFocusable(true);
		this.requestFocus();
		addNotify();
	}
	
	public PanelGame() {
		
	}

	/**
	 * Override del metodo PaintComponent dell classe JComponent
	 * @param g: Graphics
	 */
	protected void paintComponent(Graphics g){
		
		
		Graphics2D g2=(Graphics2D)g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING	, RenderingHints.VALUE_ANTIALIAS_ON);
		
		size=getBoardCellSize();
		super.paintComponent(g2);
		
		paintBoard(g2);
		paintSegmenti(g2);
		paintCelle(g2);
		paintActiveDot(g2);
		paintSelectedDot(g2);
		paintSegmentoDinamico(g2);


		paintLastSegmento(g2);
		redraw++;
		
		//g2.drawString(String.format("%d", redraw), size*2, size*2); for test

		
	}
	
	
	/**
	 * Metodo che crea il piano di gioco
	 * @param g: Graphics2D
	 */
	public void paintBoard( Graphics2D g){
		int size=getBoardCellSize();
		g.setColor(backgroundGame);
		g.fillRect(-1000,-1000,getWidth()*100,getHeight()*100);
		
		g.setColor(lines);
		
		for(int i=1;i<dim+1;i++){
			for(int j=1;j<dim+1;j++){
				int x=i*size;
				int y=j*size;
				g.fillOval(x, y,size/4,size/4);
			}
		}
		
	}

	/**
	 * Metodo che colora il punto su cui si posa il mouse, o su cui ci si muove con la tastiera
	 * Verificando eventualmente la validità della mossa
	 * @param g: Graphics2D
	 */
	public void paintActiveDot(Graphics2D g){
		if(mousePos==null) return;
		if(stato==false) return;
		int x = mousePos.x * size;
		int y = mousePos.y * size;
	
		if(firstClick==null){
			if(isInTheBoard(mousePos)){
	
				g.setColor(Color.PINK);
				g.fillOval(x, y, size/4, size/4);
	
			}
		}else{
			try{
				if(isInTheBoard(mousePos)){
					if(validMove(firstClick, mousePos)){
						if(!(listaSegmenti.containsKey(new Segmento(firstClick, mousePos).toString())||
								listaSegmenti.containsKey(new Segmento(mousePos, firstClick).toString()))){
							g.setColor(Color.GREEN);
							g.fillOval(x, y, size/4, size/4);
						}else{
							g.setColor(Color.RED);
							g.fillOval(x, y, size/4, size/4);
						}
	
					}else{
						g.setColor(Color.RED);
						g.fillOval(x, y, size/4, size/4);
					}
				}
			}catch(NullPointerException e){
	
			}
		}
		
	}
	/**
	 * Metodo che colora di verde il punto selezionato
	 * @param g: Graphics2D
	 */
	public void paintSelectedDot(Graphics2D g){
	
		if(firstClick==null || secondClick!= null) return;
	
		int size=getBoardCellSize();
		int x=firstClick.x*size;
		int y=firstClick.y*size;
		
		if(isInTheBoard(firstClick)){
			
			
			g.setColor(Color.GREEN);
			g.fillOval(x, y, size/4, size/4);
		}
	
	
	}
	/**
	 * Metodo che disegna un segmento quando si ha effettuato il primo click e si sta scegliendo il secondo
	 * vertice del segmento
	 * @param g: Graphics2D
	 */
	public void paintSegmentoDinamico(Graphics2D g){
	if(firstClick==null || secondClick!=null) return;
		
		int x=firstClick.x*size;
		int y=firstClick.y*size;
		
		int x2=mousePos.x*size;
		int y2=mousePos.y*size;
		
		if((isInTheBoard(firstClick))&&(isInTheBoard(mousePos))){
			g.setStroke(new BasicStroke((float)(size)/14));
			g.setColor(lines);
			g.drawLine(x+size/8, y+size/8, x2+size/8, y2+size/8);
			
		}
		
	}
	/**
	 * Metodo che colora con diversamente l'ultimo segmento creato dall'avversario, per evidenziare la mossa
	 * @param g: Graphics2D
	 */
	public void paintLastSegmento(Graphics2D g){
		if(stackSegmenti.isEmpty()){
			return;
		}
		if(stato==false){
			return;
		}
		Segmento last=stackSegmenti.peek();
		
		Point first=boardToScreen(last.estremoA);
		
		Point second=boardToScreen(last.estremoB);
	
		g.setStroke(new  BasicStroke((float)(size)/13));
		g.setColor(lastLine);
		g.drawLine(first.x+size/8, first.y+size/8 , second.x+size/8 , second.y+size/8);
		g.setStroke(new BasicStroke((float)(size)/20));
		g.drawOval(first.x, first.y, size/4, size/4);
		g.drawOval(second.x, second.y, size/4, size/4);
	}

	/**
	 * Metodo che disegna tutti i segmenti presenti sulla board
	 * @param g: Graphics2D
	 */
	public void paintSegmenti(Graphics2D g){
		
		Set<String> list=listaSegmenti.keySet();
		Iterator<String> iter= list.iterator();
		
		while(iter.hasNext()){
			Object key=iter.next();
			Segmento value=listaSegmenti.get(key);
			
			Point first=boardToScreen(value.estremoA);
			Point second=boardToScreen(value.estremoB);
		
			g.setStroke(new  BasicStroke((float)(size)/10));
			g.setColor(lines);
			g.drawLine(first.x+size/8, first.y+size/8 , second.x+size/8 , second.y+size/8);
	
		}
	}

	/**
	 * Metodo che disegna sulla board un cercho all'interno di ogni quadrato chiuso
	 * @param g: Graphics2D
	 */
	public void paintCelle(Graphics2D g){
	
		if(listaPuntiChiave.isEmpty()) return;
		try{
			
			for(Cella s:listaPuntiChiave){
				
				Point vert= boardToScreen(new Point(s.getX(), s.getY()));
				if(s.getMark()=='1'){
					g.setColor(colorPl1);
					
				}
				else g.setColor(colorPl2);
				
				
				g.fillOval((int)(vert.x+size/(5.52)),(int)(vert.y+size/(5.52)),(int)( size-size/10),(int) (size-size/10));
			}
			
		}catch(NullPointerException e){
	
		}
	}

	public void addSegmenti(Segmento nuovo){
		
		listaSegmenti.put(nuovo.toString(),nuovo);
		
		repaint();
	}

	public void addCelle(Cella nuova){
		listaPuntiChiave.addElement(nuova);
		repaint();
	}

	public void addToStack(Segmento nuovo){
		stackSegmenti.push(nuovo);
		repaint();
	}

	public int getDimMax(){
		return Math.max(getWidth(), getHeight());
	}
	/**
	 * Verifica che la mossa sia regolare
	 * @param start: Point
	 * @param end: Point
	 * @return boolean
	 */
	public boolean validMove(Point start, Point end){
	
		if(start.x>=0 && start.y>=0 && end.x<9 && end.y<9){
			if(((start.x==end.x)&&(Math.abs(start.y-end.y))==1)
					|| ((start.y==end.y) && (Math.abs(start.x - end.x)==1))){
	
				return true;
	
			}else return false;
	
		}else return false;
	
	}

	public int getBoardCellSize(){
		return Math.min(getHeight(), getWidth())/(dim+1);
	}
	
	public boolean isInTheBoard(Point a){
		if(a.x>=1 && a.x<dim+1 && a.y>=1 && a.y<dim+1) return true;
		else return false;
	}
	
	public Point screenToBoard(Point sc){
		return new Point ((sc.x/size), sc.y/size);
	}
	public Point boardToScreen(Point bc){
		return new Point(bc.x*size, bc.y*size);
	}

	public void setPlColor(Color color, char mark){
		if(mark=='1'){
			colorPl1=color;
		}
		if(mark=='2'){
			colorPl2=color;
		}
	}

	public void setStato(boolean stato){
		this.stato=stato;
	}

	public Segmento getNuovoSegmento(){
		return nuovoSegmento;
	}
	
	public int getPunti(char mark){
		int punti=0;
		for(Cella s:listaPuntiChiave){
			
			if(s.getMark()==mark){
				punti++;
			}
			
		}
		return punti;
	}

	/**
	 * Algoritmo che verifica la validità di una nuova mossa
	 * @param input: Point
	 */
	public void nuovaMossa(Point input){
		if(stato==false) return;
 		if(isInTheBoard(input)) touchSound();
		
		if(firstClick==null || firstClick==mousePos){
			firstClick=new Point(input.x, input.y);
		
			repaint();
			
		}
		else{
			
			secondClick=new Point(input.x, input.y);
			repaint();
			
			if(isInTheBoard(firstClick)&&isInTheBoard(secondClick)){
				if(!firstClick.equals(secondClick)){
					if(validMove(firstClick, secondClick)){
						if(isOriented(new Segmento(firstClick, secondClick))){
							nuovoSegmento=new Segmento(firstClick, secondClick);
						}
						else nuovoSegmento=new Segmento(secondClick, firstClick);
						
						if(!listaSegmenti.containsKey(nuovoSegmento.id)){
							addSegmenti(nuovoSegmento);
							client.inviaSegmento(nuovoSegmento);
							setStato(false);
							
							repaint();
							
						}
						
						
						
					}
				}
			}
			firstClick=null; 
			secondClick=null;
		}
		
	}
	
	
	public void ottieniPoints(){
		points[0] = getPunti('1');
		points[1]= getPunti('2');
		client.setProgressBars(points);
	}

	
	
	public boolean isOrizzontale(Segmento input){
		if(input.estremoA.x+1==input.estremoB.x && input.estremoA.y==input.estremoB.y) return true;
		else return false;
	}

	
	
	public boolean isOriented(Segmento input){
		if(isOrizzontale(input)){
			if(input.estremoA.x<input.estremoB.x) return true;
			else return false;
		}else{
			if(input.estremoA.y<input.estremoB.y) return true;
			else return false;
		}
	}

	
	
	public void touchSound(){
		GameSound.TOUCH.play();
	}

	
	
	public void cellClosedSound(){
		GameSound.CELL_CLOSED.play();
	}

	
	
	
	
	public void addNotify() {
		super.addNotify();
		this.requestFocus();
		
	}

	/**
	 * Gestione dei tasti
	 */
	public void keyPressed(KeyEvent arg0) {
		int code=arg0.getKeyCode();
		
		if(mousePos==null){
			mousePos.x=1;
			mousePos.y=1;
			
		}
		switch (code){
		
		case KeyEvent.VK_ESCAPE:
			System.out.println("escape");
			client.closingWindow();

		case KeyEvent.VK_UP  :
		case KeyEvent.VK_W:
			if(mousePos.y>1){
				mousePos.y--;
				repaint();
			}
			
			
			
			break;

		case KeyEvent.VK_DOWN :
		case KeyEvent.VK_S:
			
			if(mousePos.y<dim){
				mousePos.y++;
				repaint();
			}
			

			break;

		case KeyEvent.VK_LEFT:
		case KeyEvent.VK_A:
		
			if(mousePos.x>1){
				mousePos.x--;
				repaint();
			}
			
			
			break;

		case KeyEvent.VK_RIGHT:
		case KeyEvent.VK_D:
			
			if(mousePos.x<dim){
				mousePos.x++;
				repaint();
			}
			
			
			break;
			
		case KeyEvent.VK_ENTER:
			
			nuovaMossa(mousePos);
			
			break;
			
			
		}
		


	}
	
	
	
	
	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}
/**
 * Gestione dei click del mouse
 */
	public void mouseClicked(MouseEvent e){
		Point x= screenToBoard(e.getPoint());
		nuovaMossa(x);
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

/**
 * Gestione dei movimenti del mouse
 */
	public void mouseMoved(MouseEvent e) {
		currentMousePos = screenToBoard(e.getPoint());

		if(!isInTheBoard(currentMousePos)){
			return;
		}
		if(mousePos == null || 
				!currentMousePos.equals(mousePos)) {

			mousePos = screenToBoard(e.getPoint());
			repaint();

		}

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	
}
