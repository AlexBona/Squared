package squared_package;

import java.awt.Point;



public class Segmento{
	
	
	
	Point estremoA;
	Point estremoB;
	String id;

	
	Point[]estremi={estremoA,estremoB};
	private char mark;
	
	public Segmento(Point estremoA, Point estremoB, char mark){
		this.estremoA=estremoA;
		this.estremoB=estremoB;
		this.id=toString();	
		this.setMark(mark);
		
	}
	public Segmento(Point estremoA, Point estremoB){
		this.estremoA=estremoA;
		this.estremoB=estremoB;
		this.id=toString();
	}
	
	
	public String toString(){
		
		String id=String.format("Segmento A:%d,%d B:%d,%d",estremoA.x,estremoA.y,
				estremoB.x,estremoB.y);
		return id;
	}
	public boolean equals(Segmento input){
		if(estremoA.equals(input.estremoA) && estremoB.equals(input.estremoB)) return true;
		return false;
	}
	
	public boolean isOrizzontale(){
		if(estremoA.x+1==this.estremoB.x && estremoA.y==estremoB.y) return true;
		else return false;
	}
	
	
	
	public Segmento[] findParalleli(){
		Segmento[] paralleli=new Segmento [2];
		
		if(isOrizzontale()){
			Point altoSx=new Point(estremoA.x, estremoA.y-1);
			Point altoDx=new Point(estremoB.x, estremoB.y-1);
			paralleli[0] =new Segmento(altoSx, altoDx);
			
			Point bassoSx=new Point(estremoA.x, estremoA.y+1);
			Point bassoDx=new Point(estremoB.x, estremoB.y+1);
			paralleli[1]=new Segmento(bassoSx, bassoDx);
		}
		else{
			Point altoSx=new Point(estremoA.x-1, estremoA.y);
			Point bassoSx=new Point(estremoB.x-1, estremoB.y);
			paralleli[0]=new Segmento(altoSx, bassoSx);
			
			Point altoDx=new Point(estremoA.x+1, estremoA.y);
			Point bassoDx=new Point(estremoB.x+1, estremoB.y);
			paralleli[1]=new Segmento(altoDx, bassoDx);
		}
		return paralleli;
	}
	public char getMark() {
		return mark;
	}
	public void setMark(char mark) {
		this.mark = mark;
	}
	
	
}