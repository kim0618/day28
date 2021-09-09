package ex02;


import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.Slider;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

public class MediaServiceImpl implements MediaService{
        MediaPlayer mediaPlayer;
        MediaView mediaView;
        Button btnPlay, btnPause, btnStop;
        boolean endOfMedia;
        
        Label labelTime;
        Slider sliderVolume;
        ProgressBar progressBar;
        ProgressIndicator progressIndicator;
        
        
        @Override
        public void myStart() {	// 동영상 시작하기
                mediaPlayer.play();
        }
        @Override
        public void myStop() {	// 동영상 멈추기
                mediaPlayer.stop();
        }
        @Override
        public void myPause() {	// 동영상 일시정지
                mediaPlayer.pause();
        }
        public void setControl(Parent root) {
                mediaView = (MediaView)root.lookup("#fxMediaView");
                btnPlay = (Button)root.lookup("#btnPlay");
                btnPause = (Button)root.lookup("#btnPause");
                btnStop = (Button)root.lookup("#btnStop");
                
                labelTime = (Label)root.lookup("#labelTime");
                sliderVolume = (Slider)root.lookup("#sliderVolume");
                progressBar = (ProgressBar)root.lookup("#progressBar");
                progressIndicator = (ProgressIndicator)root.lookup("#progressIndicator");
        }
        public void setMedia(Parent root, String mediaName) {
                setControl(root);
                //System.out.println( getClass().getResource(mediaName) );
                Media media = new Media( getClass().getResource(mediaName).toString() );
                mediaPlayer = new MediaPlayer(media);
                
                mediaView.setMediaPlayer(mediaPlayer);
                
                mediaPlayer.setOnReady(new Runnable() {
                        public void run() {		// 동영상 시작 전에
                                btnPlay.setDisable(false);
                                btnStop.setDisable(true);
                                btnPause.setDisable(true);
                                mediaPlayer.currentTimeProperty().addListener((a,b,c)->{
                                	// 흐르는 시간 / 최송 시간
                                	double progress = 
       			mediaPlayer.getCurrentTime().toSeconds() / mediaPlayer.getTotalDuration().toSeconds();
                                	progressBar.setProgress(progress);
                                	progressIndicator.setProgress(progress);
                                	labelTime.setText(
                                			(int)mediaPlayer.getCurrentTime().toSeconds()+"/"+
                                	(int)mediaPlayer.getTotalDuration().toSeconds()
                                	);
                                });
                        }
                });
                mediaPlayer.setOnPlaying(()->{	// 동영상이 시작되면
                	sliderVolume.setValue(50.0);
                	btnPlay.setDisable(true);
                    btnStop.setDisable(false);
                    btnPause.setDisable(false);
                });
                mediaPlayer.setOnStopped(()->{	// 동영상을 종료하면
                	btnPlay.setDisable(false);
                    btnStop.setDisable(true);
                    btnPause.setDisable(true);
                });
                mediaPlayer.setOnPaused(()->{	// 동영상을 일시정지하면
                	btnPlay.setDisable(false);
                    btnStop.setDisable(true);
                    btnPause.setDisable(false);
                });
                mediaPlayer.setOnEndOfMedia(()->{ // 동영상이 끝까지 보고 끝나면
                	btnPlay.setDisable(false);
                    btnStop.setDisable(true);
                    btnPause.setDisable(true);
                    myStop();						// 다시 처음으로 돌아가도록
                });
        }
		@Override
		public void volumeControl() {
				mediaPlayer.setVolume(sliderVolume.getValue() / 100.0); // 볼륨 기본설정값정하기
			
		}
        
        
        
        
        
        
        
}