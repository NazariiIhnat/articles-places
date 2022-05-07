import components.MainFrame;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(
                GUIComponentsScan.class,
                BackendComponentsScan.class,
                DatabaseComponentScan.class
        );
        MainFrame mainFrame = context.getBean(MainFrame.class);
        mainFrame.setVisible(true);
    }
}
