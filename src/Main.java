import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        new JFXPanel();
        Platform.runLater(Main::launch);
    }

    private static void launch() {
        Image image = new Image("/face2.jpg");
        int windowWidth = (int) image.getWidth();
        int windowHeight = (int) image.getHeight();
        long a = System.currentTimeMillis();
        Stage stage = new Stage();
        stage.setTitle("maze");
        stage.setWidth(windowWidth);
        stage.setHeight(windowHeight);
        stage.setResizable(false);
        stage.getIcons().add(new Image("/face.png"));
        stage.show();

        Group group = new Group();
        Scene scene = new Scene(group);
        stage.setScene(scene);


        ImageView im = new ImageView();
        //im.setPreserveRatio(false);
        im.setFitWidth(windowWidth);
        im.setFitHeight(windowHeight);
        group.getChildren().add(im);

        PixelReader pr = image.getPixelReader();
        WritableImage wIm = new WritableImage((int) image.getWidth(), (int) image.getHeight());
        PixelWriter pw = wIm.getPixelWriter();
        ArrayList<Integer> al = new ArrayList<>();

        System.out.println(al.toString());
        for (int y = 0; y < windowHeight; y++) {
            for (int x = 0; x < windowWidth; x++) {
                Color color = pr.getColor(x, y);
                if (color.toString().equals("0xffffffff")) {
                    al.add(1);
                } else {
                    al.add(0);
                }
            }
        }

        int y = -1;
        for (int x = 0; x < (windowWidth * windowHeight); x++) {
            if (x % 500 == 0 && y < windowHeight - 1) y++;
            if (al.get(x).equals(0)) {
                pw.setColor(x % windowWidth, y, Color.BLACK);
                //pw.setColor(x % windowWidth, y, Color.rgb((int)Math.ceil(Math.random()*100),(int)Math.ceil(Math.random()*100),(int)Math.ceil(Math.random()*100)));

            } else {
                pw.setColor(x % windowWidth, y, Color.BLUE);
                //pw.setColor(x % windowWidth, y, Color.rgb((int)Math.ceil(Math.random()*255),(int)Math.ceil(Math.random()*255),(int)Math.ceil(Math.random()*255)));
            }

        }
        im.setImage(wIm);

        try {
            BufferedImage bi = new BufferedImage(500, 500, 2);
            SwingFXUtils.fromFXImage(wIm, bi);
            File out = new File("out.png");
            ImageIO.write(bi, "png", out);
        } catch (IOException e) {
            e.printStackTrace();
        }
        long b = System.currentTimeMillis();
        System.out.println("done in " + (double) (b - a) / 1000 + " seconds");
    }
}