//Pier-alexandre Yale

package animation;

public class Application{
	
	public static void main(String[] args) {
		FenetreCourse fenetrecourse = new FenetreCourse();
		Thread thread = new Thread(fenetrecourse);
		thread.start();
		fenetrecourse.setVisible(true);

	}
}
