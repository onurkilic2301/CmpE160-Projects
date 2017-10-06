import java.io.FileNotFoundException;

public class MainClass {

	public static void main(String[] args) throws FileNotFoundException {
		ChessFrame theFrame = new ChessFrame();
		theFrame.setVisible(true);
		
		//theFrame.move("d1", "h5");
		System.out.println(theFrame.isCheckmate());		
	}

}
