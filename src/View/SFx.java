package View;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.layout.Region;
import javafx.util.Duration;

public class SFx {
	public static void fadeInAndOut(Node oldNode, Node newNode, double duration) {
		fadeNode(oldNode, false, 1000);
		fadeNode(newNode, true, 1000);

	}

	public static void fadeNode(Node node, boolean direction, double duration) {

		FadeTransition fadeTransition = new FadeTransition();
		fadeTransition.setDuration(Duration.millis(duration));
		fadeTransition.setNode(node);
		if (direction) {
			System.out.println("SFX: FADING IN");
			fadeTransition.setFromValue(0);
			fadeTransition.setToValue(1);
			fadeTransition.setOnFinished(event -> {
				node.setOpacity(1);

			});
		} else {
			System.out.println("SFX: FADING OUT");

			fadeTransition.setFromValue(1);
			fadeTransition.setToValue(0);
			fadeTransition.setOnFinished(event -> {
				node.setOpacity(0);
			});
		}
		fadeTransition.play();
	}

	public void name() {

	}

	public static void slideVertically(Region region, boolean direction, double height) {
		System.out.println("SFX: SLIDING " + direction);
		double switcher = direction ? height : 0.0;
		fadeNode(region, direction, 250);
		Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO, new KeyValue(region.prefHeightProperty(), region.getHeight())), new KeyFrame(Duration.millis(250), new KeyValue(region.prefHeightProperty(), switcher)));
		timeline.play();

	}

	public static void slideHorizontally(Region region, boolean direction, double width) {
		System.out.println("SFX: SLIDING " + direction);
		double switcher = direction ? width : 0.0;
		fadeNode(region, direction, 250);
		Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO, new KeyValue(region.prefWidthProperty(), region.getWidth())), new KeyFrame(Duration.millis(250), new KeyValue(region.prefWidthProperty(), switcher)));
		timeline.play();

	}

}