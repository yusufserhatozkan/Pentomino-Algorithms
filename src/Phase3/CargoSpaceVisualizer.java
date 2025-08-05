package Phase3;

import java.util.ArrayList;
import java.util.Random;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.AmbientLight;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.DrawMode;
import javafx.scene.PerspectiveCamera;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;

public class CargoSpaceVisualizer extends Application {
    private static final int BOXFACTOR = 30;
    private static final double CAMERA_INITIAL_DISTANCE = -2000;
    private static final int EMPTY = -1;
    private static final int DEPTH = 33;
    private static final int WIDTH = 5;
    private static final int HEIGHT = 8;
    private static Rotate xRotate;
    private static Rotate yRotate;
    private static CargoSpace cargoSpace = new CargoSpace();
    private static int[][][] cargoArray = cargoSpace.getSpace();
    private static Scene scene;
    private int trueL = 0;
    private int trueT = 0;
    private int trueP = 0;
    // private int num1 = StartingPage.getnum1();
    // private int num2 = StartingPage.getnum2();
    // private int num3 = StartingPage.getnum3();
    private static int num1 = 10;
    private static int num2 = 10;
    private static int num3 = 10;

    static PentominoCollection pentLCollection = new PentominoCollection('L', num1);
    static PentominoCollection pentTCollection = new PentominoCollection('T', num2);
    static PentominoCollection pentPCollection = new PentominoCollection('P', num3);
    static PentominoCollection pentACollection = new PentominoCollection('A', num1);
    static PentominoCollection pentBCollection = new PentominoCollection('B', num2);
    static PentominoCollection pentCCollection = new PentominoCollection('C', num3);
    static ArrayList<Pentomino> allPentominosL = new ArrayList<>();
    static ArrayList<Pentomino> allPentominosT = new ArrayList<>();
    static ArrayList<Pentomino> allPentominosP = new ArrayList<>();
    static ArrayList<Pentomino> allPentominosA = new ArrayList<>();
    static ArrayList<Pentomino> allPentominosB = new ArrayList<>();
    static ArrayList<Pentomino> allPentominosC = new ArrayList<>();

    static {
        // PENTOMINO BELOW
        if (num1 > 0) {
            for (int i = 0; i < num1; i++) {
                Pentomino pent = pentLCollection.getPentominos().get(i);
                allPentominosL.add(pent);
            }
            System.out.println("Size of L: " + allPentominosL.size());
        } else {
            System.out.println("Size of L: 0");
        }

        if (num2 > 0) {
            for (int i = 0; i < num2; i++) {
                Pentomino pent = pentTCollection.getPentominos().get(i);
                allPentominosT.add(pent);
            }
            System.out.println("Size of T: " + allPentominosT.size());
        } else {
            System.out.println("Size of T: 0");
        }

        if (num3 > 0) {
            for (int i = 0; i < num3; i++) {
                Pentomino pent = pentPCollection.getPentominos().get(i);
                allPentominosP.add(pent);
            }
            System.out.println("Size of P: " + allPentominosP.size());
        } else {
            System.out.println("Size of P: 0");
        }

        // PARCEL BELOW
        if (num1 > 0) {
            for (int i = 0; i < num1; i++) {
                Pentomino pent = pentACollection.getPentominos().get(i);
                allPentominosA.add(pent);
            }
            System.out.println("Size of A: " + allPentominosA.size());
        } else {
            System.out.println("Size of A: 0");
        }

        if (num2 > 0) {
            for (int i = 0; i < num2; i++) {
                Pentomino pent = pentBCollection.getPentominos().get(i);
                allPentominosB.add(pent);
            }
            System.out.println("Size of B: " + allPentominosB.size());
        } else {
            System.out.println("Size of B: 0");
        }

        if (num3 > 0) {
            for (int i = 0; i < num3; i++) {
                Pentomino pent = pentCCollection.getPentominos().get(i);
                allPentominosC.add(pent);
            }
            System.out.println("Size of C: " + allPentominosC.size());
        } else {
            System.out.println("Size of C: 0");
        }

    }

    @Override
    public void start(Stage primaryStage) {
        Group root = new Group();
        scene = new Scene(root, 800, 800, true);
        scene.setFill(Color.LIGHTSLATEGREY);

        createCargoSpaceVisualization(root);
        setupCameraAndLightAndWireFrame(root);
        animateGroup(root); // Animate the entire group

        // testerr(root);
        // callRecursion();
        // callRecursiom1();

        callRecursionLau2();

        repaintCargo(root);

        primaryStage.setTitle("CargoSpace Visualization");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void callRecursionLau2() {
        // Create an instance of RecursionLau2
        RecursionLau2 recursion = new RecursionLau2(allPentominosL, allPentominosT, allPentominosP);

        // Attempt to insert pentominos at different coordinates
        // recursion.insert(0, 0, 0);
        // recursion.insert(1, 0, 0);

        // recursion.insert(1, 0, 0);

        // recursion.insert(1, 1, 0);
        // recursion.insert(1, 0, 0);
        // recursion.insert(1, 0, 0);

        // recursion.insert(0, 1, 0);
        // recursion.insert(1, 1, 0);
        // recursion.insert(2, 0, 0);
        // recursion.insert(2, 0, 0);
        // recursion.insert(2, 0, 0);

        // recursion.insert(2, 2, 1);
        // recursion.insert(2, 2, 1);
        // recursion.insert(2, 2, 1);
        // recursion.insert(2, 2, 1);
        // recursion.insert(2, 2, 1);
        // recursion.insert(2, 2, 1);

        // recursion.insert(1, 2, 1);

        boolean solve = recursion.solve(1, 0, 0, 0);
        System.out.println(solve);

        // Output the results
        System.out.println("Successful insertions: " + recursion.getSucessfullInsert());
        recursion.getInformation();

    }

    private void callRecursionLau1() {
        RecursionLau recursion = new RecursionLau(allPentominosL, allPentominosT, allPentominosP);

        // Start the recursive process of placing pentominos
        recursion.toNextPentomino(0, 0, 0, cargoArray, 1);

        // After the recursive process ends, you can check how many pentominos were used
        System.out.println(recursion.getTotalUsed());
        System.out.println(recursion.toString());
        System.out.println(recursion.getBackTrackArray().size());
    }

    private void callRecursion() {
        RecursionLau recursion = new RecursionLau(allPentominosL, allPentominosT, allPentominosP);

        recursion.insertPentomino2(3, 0, 0, 0, cargoArray);
        recursion.insertPentomino2(2, 0, 0, 0, cargoArray);
        recursion.insertPentomino2(1, 0, 1, 0, cargoArray);
        recursion.insertPentomino2(1, 1, 1, 0, cargoArray);

        // recursion.insertPentomino2(1, 0, 0, 0, cargoArray);

        // recursion.insertPentomino2(2, 0, 0, 0, cargoArray);

        // recursion.insertPentomino2(1, 0, 0, 0, cargoArray);

        // recursion.insertPentomino2(3, 0, 1, 0, cargoArray);
        // recursion.insertPentomino2(3, 0, 0, 0, cargoArray);
        // recursion.insertPentomino2(3, 0, 0, 0, cargoArray);

        // recursion.insertPentomino2(2, 0, 0, 0, cargoArray);
        // recursion.insertPentomino2(3, 0, 0, 1, cargoArray);
        // recursion.insertPentomino2(3, 0, 0, 0, cargoArray);
        // recursion.insertPentomino2(3, 3, 0, 1, cargoArray, 0, 0);
        // recursion.insertPentomino2(1, 1, 0, 2, cargoArray, 0, 0);
        // recursion.insertPentomino2(1, 1, 0, 2, cargoArray, 0, 0);
        // recursion.insertPentomino2(1, 2, 1, 2, cargoArray, 0, 0);
        // recursion.insertPentomino2(3, 1, 1, 2, cargoArray, 0, 0);
        // recursion.insertPentomino2(3, 1, 1, 2, cargoArray, 0, 0);

        System.out.println(recursion.getTotalUsed());
        System.out.println(recursion.toString());
        System.out.println(recursion.getBackTrackArray().size());
        // System.out.println(recursion.getBackTrackArray().get(5).getType());
        // System.out.println(recursion.getBackTrackArray().get(5).getZPos());
        // System.out.println(recursion.getBackTrackArray().get(5).getRotationNum());
    }

    private void testerr(Group root) {
        Random rand = new Random();
        int valX;
        int valY;
        int valZ;

        int counter1 = 0;
        int counter2 = 0;
        int counter3 = 0;

        int rotatVal = 0;
        int attempts = 0;
        int maxAttempts = 10000; // Define a max number of attempts to avoid infinite loop

        while (counter1 < num1 && attempts < maxAttempts) {
            valX = rand.nextInt(DEPTH);
            valY = rand.nextInt(WIDTH);
            valZ = rand.nextInt(HEIGHT);

            Pentomino currentPent = allPentominosL.get(counter1).getRotations().get(rotatVal);

            if (insertPentomino(currentPent, valX, valY, valZ, cargoArray)) {
                counter1++;
                trueL++;
                rotatVal = 0; // Reset rotation for next Pentomino
            } else {
                rotatVal++;
                if (rotatVal >= allPentominosL.get(counter1).getRotations().size()) {
                    rotatVal = 0; // Reset rotation and try a new position
                }
            }
            repaintCargo(root);
            attempts++; // Increment attempts
        }

        attempts = 0;

        while (counter2 < num2 && attempts < maxAttempts) {
            valX = rand.nextInt(DEPTH);
            valY = rand.nextInt(WIDTH);
            valZ = rand.nextInt(HEIGHT);

            Pentomino currentPent = allPentominosT.get(counter2).getRotations().get(rotatVal);

            if (insertPentomino(currentPent, valX, valY, valZ, cargoArray)) {
                counter2++;
                trueT++;
                rotatVal = 0; // Reset rotation for next Pentomino
            } else {
                rotatVal++;
                if (rotatVal >= allPentominosT.get(counter2).getRotations().size()) {
                    rotatVal = 0; // Reset rotation and try a new position
                }
            }
            repaintCargo(root);
            attempts++; // Increment attempts
        }
        attempts = 0;

        while (counter3 < num3 && attempts < maxAttempts) {
            valX = rand.nextInt(DEPTH);
            valY = rand.nextInt(WIDTH);
            valZ = rand.nextInt(HEIGHT);

            Pentomino currentPent = allPentominosP.get(counter3).getRotations().get(rotatVal);

            if (insertPentomino(currentPent, valX, valY, valZ, cargoArray)) {
                counter3++;
                trueP++;
                rotatVal = 0; // Reset rotation for next Pentomino
            } else {
                rotatVal++;
                if (rotatVal >= allPentominosP.get(counter3).getRotations().size()) {
                    rotatVal = 0; // Reset rotation and try a new position
                }
            }
            repaintCargo(root);
            attempts++; // Increment attempts
        }
    }

    public static boolean insertPentomino(Pentomino pentomino, int startX, int startY, int startZ,
            int[][][] cargoSpace) {
        int[][][] shape = pentomino.getShape();
        // Check if the pentomino can be placed
        for (int x = 0; x < shape.length; x++) {
            for (int y = 0; y < shape[x].length; y++) {
                for (int z = 0; z < shape[x][y].length; z++) {
                    // Check boundaries
                    if (startX + x >= DEPTH || startY + y >= WIDTH || startZ + z >= HEIGHT) {
                        return false; // Out of bounds
                    }
                    // Check for overlap
                    if (shape[x][y][z] != EMPTY && cargoSpace[startX + x][startY + y][startZ + z] != EMPTY) {
                        return false; // Space already occupied
                    }
                }
            }
        }
        // Place the pentomino
        for (int x = 0; x < shape.length; x++) {
            for (int y = 0; y < shape[x].length; y++) {
                for (int z = 0; z < shape[x][y].length; z++) {
                    if (shape[x][y][z] != EMPTY) {
                        cargoSpace[startX + x][startY + y][startZ + z] = shape[x][y][z]; // Or use a unique identifier
                                                                                         // for the pentomino
                    }
                }
            }
        }
        return true; // Successfully inserted
    }

    public static void removePentomino(Pentomino pentomino, int[][][] cargoSpace, int startX, int startY, int startZ) {
        int[][][] shape = pentomino.getShape();
        // Remove the pentomino from the cargo space
        for (int x = 0; x < shape.length; x++) {
            for (int y = 0; y < shape[x].length; y++) {
                for (int z = 0; z < shape[x][y].length; z++) {
                    if (shape[x][y][z] != EMPTY) {
                        cargoSpace[startX + x][startY + y][startZ + z] = EMPTY;
                    }
                }
            }
        }
    }

    /**
     * Sets up the camera, light, and the wireframe around the Cargo Space box
     * 
     * @param root
     */
    private void setupCameraAndLightAndWireFrame(Group root) {
        // Creates an Ambient Light so the colors of the block show
        AmbientLight ambientLight = new AmbientLight();
        ambientLight.setColor(Color.WHITE);
        root.getChildren().add(ambientLight);

        // Set up the camera
        PerspectiveCamera camera = new PerspectiveCamera(true);
        camera.setTranslateZ(CAMERA_INITIAL_DISTANCE); // z position
        camera.setTranslateY(50); // y position
        camera.setTranslateX(500); // x position
        camera.setNearClip(0.1);
        camera.setFarClip(3000.0);
        scene.setCamera(camera);

        // Set up the WireFrame
        double wireframeWidth = cargoSpace.getDepth() * BOXFACTOR;
        double wireframeHeight = cargoSpace.getWidth() * BOXFACTOR;
        double wireframeDepth = cargoSpace.getHeight() * BOXFACTOR;

        // Create a wireframe box that encompasses the entire cargo space
        Box wireframeBox = new Box(wireframeWidth, wireframeHeight, wireframeDepth);
        wireframeBox.setDrawMode(DrawMode.LINE); // Set the box to wireframe mode
        wireframeBox.setMaterial(new PhongMaterial(Color.BLACK)); // Set wireframe color

        // Calculate the center of the cargo space
        double centerX = cargoSpace.getDepth() * BOXFACTOR / 2.0;
        double centerY = cargoSpace.getWidth() * BOXFACTOR / 2.0;
        double centerZ = cargoSpace.getHeight() * BOXFACTOR / 2.0;

        // Setting the translation of the wireframe box to the center
        wireframeBox.setTranslateX(centerX - 15);
        wireframeBox.setTranslateY(centerY - 15);
        wireframeBox.setTranslateZ(centerZ - 15);

        root.getChildren().add(wireframeBox);
    }

    /**
     * Repaint method that clears the visualiztion, then sets up the visualization
     * again
     * It is used when we add/remove pentomino letters to the Cargo Space
     * 
     * @param root
     */
    private void repaintCargo(Group root) {
        // System.out.println("Repainting the Cargo");
        root.getChildren().clear(); // Clears the visualizaition
        setupCameraAndLightAndWireFrame(root); // Sets up the scene
        createCargoSpaceVisualization(root); // Rebuild the visualization
        addWheelsToCargoSpace(root);
        addFrontBoxToCargoSpace(root);
    }

    /**
     * Goes through the Cargo array box/space and adds colors approperatly to the
     * values in the array (-1, 1, 2, 3)
     * 
     * @param root
     */
    private void createCargoSpaceVisualization(Group root) {
        double borderSize = BOXFACTOR + 1;
        double boxSize = BOXFACTOR;

        for (int i = 0; i < cargoSpace.getDepth(); i++) {
            for (int j = 0; j < cargoSpace.getWidth(); j++) {
                for (int k = 0; k < cargoSpace.getHeight(); k++) {
                    int value = cargoArray[i][j][k];

                    double translateX = i * BOXFACTOR;
                    double translateY = j * BOXFACTOR;
                    double translateZ = k * BOXFACTOR;

                    // shadow
                    if (value != EMPTY) {
                        // Create and add the border box
                        Box borderBox = createBox(borderSize, translateX, translateY, translateZ, Color.BLACK);
                        root.getChildren().add(borderBox);
                    }

                    // Create and add the main box
                    Color color = getColorForValue(value);
                    Box unitBox = createBox(boxSize, translateX, translateY, translateZ, color);
                    root.getChildren().add(unitBox);
                }
            }
        }
    }

    private Box createBox(double size, double translateX, double translateY, double translateZ, Color color) {
        Box box = new Box(size, size, size);
        box.setTranslateX(translateX);
        box.setTranslateY(translateY);
        box.setTranslateZ(translateZ);
        box.setMaterial(new PhongMaterial(color));
        return box;
    }

    public static int getValueForLetter(char ch) {
        switch (ch) {
            case 'L':
                return 1;
            case 'T':
                return 2;
            case 'P':
                return 3;
            case 'A':
                return 4;
            case 'B':
                return 5;
            case 'C':
                return 6;
            default:
                throw new IllegalArgumentException("INVALID LETTER: " + ch);
        }
    }

    private static Color getColorForValue(int value) {
        switch (value) {
            case EMPTY:
                return new Color(0, 0, 0, 0); // Transparent for empty
            // return Color.DARKMAGENTA;
            case 1:
                return Color.BLUE; // Pentomino L
            case 2:
                return Color.RED; // Pentomino T
            case 3:
                return Color.GREEN; // Pentomino P
            case 4:
                return Color.BLUE;
            case 5:
                return Color.ORANGE;
            case 6:
                return Color.PINK;
            case -10:
                return Color.BLACK; // Testing purposes
            default:
                throw new IllegalArgumentException("INVALID COLOR NUMBER: " + value);
        }
    }

    private void animateGroup(Group group) {
        // Calculate center coordinates of the cargo space
        double centerX = cargoSpace.getDepth() * BOXFACTOR / 2.0;
        double centerY = cargoSpace.getWidth() * BOXFACTOR / 2.0;
        double centerZ = cargoSpace.getHeight() * BOXFACTOR / 2.0;

        // Create rotation transforms with the specified pivot points
        xRotate = new Rotate(0, Rotate.X_AXIS);
        xRotate.setPivotX(centerX);
        xRotate.setPivotY(centerY);
        xRotate.setPivotZ(centerZ);

        yRotate = new Rotate(0, Rotate.Y_AXIS);
        yRotate.setPivotX(centerX);
        yRotate.setPivotY(centerY);
        yRotate.setPivotZ(centerZ);

        group.getTransforms().addAll(xRotate, yRotate);

        Timeline timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);

        KeyValue xKeyValue = new KeyValue(xRotate.angleProperty(), 720);
        KeyFrame xKeyFrame = new KeyFrame(Duration.seconds(15), xKeyValue);

        KeyValue yKeyValue = new KeyValue(yRotate.angleProperty(), 360);
        KeyFrame yKeyFrame = new KeyFrame(Duration.seconds(15), yKeyValue);

        timeline.getKeyFrames().addAll(xKeyFrame, yKeyFrame);
        timeline.play();
    }

    private void addWheelsToCargoSpace(Group root) {
        double wheelRadius = 50; // Half of BOXFACTOR for the wheel radius
        double wheelHeight = 30; // Quarter of BOXFACTOR for the wheel height

        // Coordinates for wheel positions
        double[] frontRight = { 50, 130, -55 };
        ;
        double[] frontLeft = { 50, 0, -55 };
        double[] backLeft = { 900, 0, -55 };
        double[] backRight = { 900, 130, -55 };
        double[] backSecondRight = { 750, 0, -55 };
        double[] backSecondLeft = { 750, 130, -55 };

        // Create and add wheels
        root.getChildren().addAll(
                createWheel(wheelRadius, wheelHeight, frontLeft),
                createWheel(wheelRadius, wheelHeight, frontRight),
                createWheel(wheelRadius, wheelHeight, backLeft),
                createWheel(wheelRadius, wheelHeight, backRight),
                createWheel(wheelRadius, wheelHeight, backSecondLeft),
                createWheel(wheelRadius, wheelHeight, backSecondRight));
    }

    private Cylinder createWheel(double radius, double height, double[] position) {
        Cylinder wheel = new Cylinder(radius, height);
        wheel.setTranslateX(position[0]);
        wheel.setTranslateY(position[1]);
        wheel.setTranslateZ(position[2]);
        wheel.setMaterial(new PhongMaterial(Color.BLACK)); // Set wheel color
        return wheel;
    }

    private void addFrontBoxToCargoSpace(Group root) {
        double boxWidth = 120; // Width of the front box
        double boxHeight = 200; // Height of the front box
        double boxDepth = 200; // Depth of the front box

        // Calculate the position of the front box relative to the cargo space
        double posX = -115;
        double posY = 70; // Position it in front
        double posZ = 100;

        // Create the front box
        Box frontBox = new Box(boxWidth, boxHeight, boxDepth);
        frontBox.setTranslateX(posX);
        frontBox.setTranslateY(posY);
        frontBox.setTranslateZ(posZ);
        frontBox.setMaterial(new PhongMaterial(Color.GRAY)); // Set box color

        // Add the box to the root group
        root.getChildren().add(frontBox);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
