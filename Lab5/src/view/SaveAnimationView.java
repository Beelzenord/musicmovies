/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;


import javafx.animation.AnimationTimer;
import javafx.geometry.Insets;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class SaveAnimationView {

    private ClockTimer timer;
    private ImageView bookImage;
    private VBox imageBox;
    private double y, w ;
    private long old;
    
    public SaveAnimationView(VBox imageBox) {
        timer = new ClockTimer();
        this.imageBox = imageBox;
        
        initAnimation(imageBox);
    }

    private void initAnimation(VBox imageBox) {
        bookImage = new ImageView(this.getClass().
                getResource("/resources/library-books-t.png").
                toString());
        
        bookImage.setPreserveRatio(true);
        bookImage.setVisible(false);
        imageBox.getChildren().setAll(bookImage);
        imageBox.setMouseTransparent(true);
    }
    
    public void startAnimation() {
        y = 300.0;
        w = 100.0;
        old = 0;
        imageBox.setPadding(new Insets(y, 10, 10, 350));
        bookImage.setFitWidth(w);

        bookImage.setVisible(true);
        timer.start();
    }
    

    private class ClockTimer extends AnimationTimer{
        @Override
        public void handle(long now) {
            // normal animation for super high fps
            if (now > old + 10000000) {
                old = now;
                y -= 3;
                w -= 1;
                imageBox.setPadding(new Insets(y, 10, 10, 400));
                bookImage.setFitWidth(w);
                if (y < 40) {
                    bookImage.setVisible(false);
                    timer.stop();
                }
            }
        }
    }
}
