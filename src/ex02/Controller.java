package ex02;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import javafx.scene.Parent;

public class Controller implements Initializable{
        Parent root;
        MediaService ms;
        public void setRoot(Parent root) {
                this.root = root;
                ms.setMedia(root, "/media/video.m4v");
        }
        @Override
        public void initialize(URL arg0, ResourceBundle arg1) {
                ms = new MediaServiceImpl();
        }
        public void myPlay() {
                ms.myStart();
        }
        public void myPause() {
                ms.myPause();
        }
        public void myStop() {
                ms.myStop();
        }
        public void volumeControl() {
        	ms.volumeControl();
        }
}