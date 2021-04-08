
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class GUIApplication extends Application{

    @Override
    public void start(Stage stage) throws Exception {

        // Fix weird font issue in dialog boxes on Macs
        // Application.setUserAgentStylesheet(Application.STYLESHEET_MODENA);        
        // com.sun.javafx.css.StyleManager.getInstance().addUserAgentStylesheet("MainWindow.css");
        
        var loader = new FXMLLoader(getClass().getResource("MainWindow.fxml"));
        var scene = new Scene(loader.load());

        stage.setScene(scene);
        stage.setTitle("Hello"); // Title of main window
        stage.show();
    }

}
