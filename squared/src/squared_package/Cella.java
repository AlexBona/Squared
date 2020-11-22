package squared_package;

import java.awt.Point;





public class Cella{

	Segmento lato1;
	Segmento lato2;
	Segmento lato3;
	Segmento lato4;
	String id;
	
	Point puntoChiave;
	private int x;
	private int y;
	private char mark;

	public Cella(Segmento lato1,Segmento lato2,Segmento lato3, Segmento lato4){
		this.lato1=lato1;
		this.lato2=lato2;
		this.lato3=lato3;
		this.lato4=lato4;
		this.id=toString();
		this.puntoChiave=findPuntoChiave();


	}
	public Cella(int x, int y, char mark){
		this.x=x;
		this.y=y;
		this.mark=mark;
	}
	
	public char getMark(){
		return mark;
	}
	public int getX(){
		return x;
		
	}
	public int getY(){
		return y;
	}
	

	public String toString(){
		return String.format("Vertici: %s, %s, %s, %s", lato1.id,lato2.id,lato3.id,lato4.id);
	}
	
	public String toStringPuntoChiave(){
		int x=(int) findPuntoChiave().getX();
		int y=(int) findPuntoChiave().getY();
		return String.format("Cella chiusa: %d, %d", x,y);
	}
	public Point findPuntoChiave(){
		Segmento [] lati ={lato1,lato2,lato3,lato4};
		int minX=10;
		int minY=10;
		for(Segmento s:lati){
			if(s.estremoA.getX()<=minX && s.estremoA.getY()<=minY){
				minX=s.estremoA.x;
				minY=s.estremoA.y;
			}
		}
		return new Point(minX,minY);
	}

}


