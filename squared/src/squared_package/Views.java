package squared_package;

import java.awt.Color;

public enum Views {

		
		
	
		CLASSIC(Color.BLUE , Color.RED, Color.YELLOW, new Color(255,215,0)
				, Color.BLACK,Color.BLACK,Color.RED),
		DARK(new Color(123,65,115), new Color(60,103,115), Color.BLACK, new Color(125,120,120)
				, Color.WHITE, Color.WHITE, new Color(230,70,155)),
		NATURE(new Color(255,128,0), new Color(0, 230, 168), new Color(153,255,204), new Color(102,255,102),
				 Color.BLACK,new Color(0,153,0), Color.RED),
		PINK(new Color(204,0,255), new Color(255,60,153), new Color(255,0,255),new Color(204,0,204),
				Color.BLACK,Color.BLACK, Color.RED);
	
	
		private Color cella1;
		private Color cella2;
		private Color backgroundGame;
		private Color backgroundSettings;
		private Color writes;
		private Color lines;
		private Color lastLine;
	
	
		private Views (Color cella1,  Color cella2, Color backgroundGame, Color backgroundSettings,
				Color writes, Color lines, Color lastLine){
			this.setCella1(cella1);
			this.setCella2(cella2);
			this.setBackgroundGame(backgroundGame);
			this.setBackgroundSettings(backgroundSettings);
			this.setWrites(writes);
			this.setLines(lines);
			this.setLastLine(lastLine);
		}
		
		

		public Color getCella1() {
			return cella1;
		}



		public void setCella1(Color cella1) {
			this.cella1 = cella1;
		}



		public Color getCella2() {
			return cella2;
		}



		public void setCella2(Color cella2) {
			this.cella2 = cella2;
		}



		public Color getBackgroundGame() {
			return backgroundGame;
		}

		public void setBackgroundGame(Color backgroundGame) {
			this.backgroundGame = backgroundGame;
		}

		public Color getBackgroundSettings() {
			return backgroundSettings;
		}

		public void setBackgroundSettings(Color backgroundSettings) {
			this.backgroundSettings = backgroundSettings;
		}

		public Color getWrites() {
			return writes;
		}

		public void setWrites(Color writes) {
			this.writes = writes;
		}

		public Color getLines() {
			return lines;
		}

		public void setLines(Color lines) {
			this.lines = lines;
		}

		public Color getLastLine() {
			return lastLine;
		}

		public void setLastLine(Color lastLine) {
			this.lastLine = lastLine;
		}
		
		
}
