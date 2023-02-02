package shipspack.ships;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import java.util.ResourceBundle;

public class ShipsController implements Initializable {

    @FXML
    public GridPane grid;
    @FXML
    public GridPane enemyGrid;
    @FXML
    private Label Ships1;
    @FXML
    private Label Ships2;
    @FXML
    private Label Ships3;
    @FXML
    private Label Ships4;
    @FXML
    private Label Ships5;
    @FXML
    public Label Feedback;
    @FXML
    public Button RotateButton;
    @FXML
    public Button ModeButton;
    @FXML
    private Label Preview4;
    @FXML
    private Label Preview3;
    @FXML
    private Label Preview2;
    @FXML
    private Label Preview1;
    @FXML
    private Label Preview0;
    @FXML
    private Button spy_button;

    @FXML
    private Button sh1plus;
    @FXML
    private Button sh2plus;
    @FXML
    private Button sh3plus;
    @FXML
    private Button sh4plus;
    @FXML
    private Button sh5plus;
    @FXML
    private Button sh1min;
    @FXML
    private Button sh2min;
    @FXML
    private Button sh3min;
    @FXML
    private Button sh4min;
    @FXML
    private Button sh5min;

    @FXML
    private Button toMenuBut;
    @FXML
    private ProgressBar fullnessBar;
    @FXML
    private Label fullnessLabel;


    //testing
    ArrayList<Integer> testList = new ArrayList<>();


    static GridPane tempGrid = new GridPane();
    static coordinates currentCoordinates = new coordinates(-1, -1);
    static String FeedbackString;

    //temp
    static ArrayList<coordinates> tempBlockedList = new ArrayList<>();
    ArrayList<Integer> tempShipNumberList = new ArrayList<>();
    static ArrayList<ArrayList<coordinates>> tempShipPosition = new ArrayList<ArrayList<coordinates>>();

    // Player
    ArrayList<coordinates> blockedList = new ArrayList<>();
    ArrayList<Integer> shipNumberList = new ArrayList<>();
    static ArrayList<ArrayList<coordinates>> shipPosition = new ArrayList<ArrayList<coordinates>>();

    //AI enemy
    ArrayList<ArrayList<coordinates>> AIShipPosition = new ArrayList<ArrayList<coordinates>>();
    public ArrayList<coordinates> AIBlockedList = new ArrayList<>();
    public ArrayList<Integer> AIShipNumberList = new ArrayList<>();

    //Values
    static int shipLength = 4;
    static int row = 10;
    static Boolean rotated = true;
    Boolean AIon = false;
    static Boolean visible = false;
    static Boolean tryAgain = true;
    static Boolean spyMode = false;
    int fullness;

    static int AIsunken = 0;
    static int PlayerSunken = 0;
    static int tempSunken = 0;
    int selectedMode = 0;
    static int totalShips = 0;
    boolean firstStart = true;

    int gameMode = 0;
    int ships1 = 1;
    int ships2 = 2;
    int ships3 = 1;
    int ships4 = 2;
    int ships5 = 1;


    private Stage stage;
    private Scene scene;
    private Parent root;

    public void ToGame(ActionEvent event) throws IOException {
        System.out.println("Changing scene");
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("ShipBoard.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        stage.setTitle("Ships - Game (beta v0.3)");
        stage.getIcons().add(new Image("Ships_ico.png"));
        stage.setMaximized(false);
        stage.resizableProperty().set(true);


    }

    public void ToMenu(ActionEvent event) throws IOException {
        System.out.println("Changing scene");
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Menu&Settings.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        stage.setTitle("Ships - Menu (beta v0.3)");
        stage.getIcons().add(new Image("Ships_ico.png"));
        stage.setMaximized(false);
        stage.resizableProperty().set(true);


    }

    public void ToStats(ActionEvent event) throws IOException {
        System.out.println("Changing scene");
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    public void CustomGame(ActionEvent event) {
        Button sourceButton = (Button) event.getSource();
        char num = sourceButton.getId().charAt(2);
        char change = sourceButton.getId().charAt(3);

        if (num == '1') {
            if (change == 'm') {
                ships1 -= 1;
                System.out.println(ships1);
            }
            else ships1 += 1;

        }

        if (num == '2') {
            if (change == 'm') {
                ships2 -= 1;
                System.out.println(ships2);
            }
            else ships2 += 1;

        }

        if (num == '3') {
            if (change == 'm') {
                ships3 -= 1;
                System.out.println(ships3);
            }
            else ships3 += 1;

        }

        if (num == '4') {
            if (change == 'm') {
                ships4 -= 1;
                System.out.println(ships4);
            }
            else ships4 += 1;

        }

        if (num == '5') {
            if (change == 'm') {
                ships5 -= 1;
                System.out.println(ships5);
            }
            else ships5 += 1;

        }


        //zaplnenost (n+2)*3
        fullness = ships1*6 + ships2*9 + ships3*12 + ships4*15 + ships5*18;
        System.out.println(fullness);
        if(fullness <= 0){toMenuBut.setDisable(true);}
        else toMenuBut.setDisable(false);


        sh1min.setDisable(ships1 == 0);
        sh2min.setDisable(ships2 == 0);
        sh3min.setDisable(ships3 == 0);
        sh4min.setDisable(ships4 == 0);
        sh5min.setDisable(ships5 == 0);

        //kontrola zaplnenosti
        sh5plus.setDisable(fullness + 18 >= 100);
        sh4plus.setDisable(fullness + 15 >= 100);
        sh3plus.setDisable(fullness + 12 >= 100);
        sh2plus.setDisable(fullness + 9 >= 100);
        sh1plus.setDisable(fullness + 6 >= 100);

        fullnessLabel.setText(fullness + "%");
        fullnessBar.setProgress((double) fullness/100);

        Preview0.setText(String.valueOf(ships1));
        Preview1.setText(String.valueOf(ships2));
        Preview2.setText(String.valueOf(ships3));
        Preview3.setText(String.valueOf(ships4));
        Preview4.setText(String.valueOf(ships5));

    }


    @FXML
    public void modeControll() {

        selectedMode++;
        if (selectedMode > 5) {
            selectedMode = 0;
        }
        if (selectedMode == 0) {
            gameMode = 0;
            ModeButton.setText("Herní mód: Flotila");
        }
        if (selectedMode == 1) {
            gameMode = 1;
            ModeButton.setText("Herní mód: Tradiční");
        }
        if (selectedMode == 2) {
            gameMode = 2;
            ModeButton.setText("Herní mód: Tradiční - M. Bradley");
        }
        if (selectedMode == 3) {
            gameMode = 3;
            ModeButton.setText("Herní mód: Ruská");
        }
        if (selectedMode == 4) {
            gameMode = 4;
            ModeButton.setText("Herní mód: Loterie");
        }
        if (selectedMode == 5) {
            gameMode = 5;
            ModeButton.setText("Herní mód: Vlastní");
        }


        if (selectedMode != 5) {
            sh1min.setDisable(true);
            sh2min.setDisable(true);
            sh3min.setDisable(true);
            sh4min.setDisable(true);
            sh5min.setDisable(true);
            sh1plus.setDisable(true);
            sh2plus.setDisable(true);
            sh3plus.setDisable(true);
            sh4plus.setDisable(true);
            sh5plus.setDisable(true);
        } else {
            sh1min.setDisable(false);
            sh2min.setDisable(false);
            sh3min.setDisable(false);
            sh4min.setDisable(false);
            sh5min.setDisable(false);
            sh1plus.setDisable(false);
            sh2plus.setDisable(false);
            sh3plus.setDisable(false);
            sh4plus.setDisable(false);
            sh5plus.setDisable(false);
        }




        //Mody hry
        // FLotila
        if (gameMode == 0) {
            ships1 = 0;
            ships2 = 3;
            ships3 = 1;
            ships4 = 2;
            ships5 = 1;
        }

        //Tradicni
        if (gameMode == 1) {
            ships1 = 2;
            ships2 = 4;
            ships3 = 2;
            ships4 = 1;
            ships5 = 0;
        }

        //tradicni M. Bradley 1990
        if (gameMode == 2) {
            ships1 = 0;
            ships2 = 1;
            ships3 = 1;
            ships4 = 1;
            ships5 = 1;
        }

        //Ruská
        if (gameMode == 3) {
            ships1 = 4;
            ships2 = 3;
            ships3 = 2;
            ships4 = 1;
            ships5 = 0;
        }

        //Loterie
        if (gameMode == 4) {
            ships1 = 1;
            ships2 = 0;
            ships3 = 0;
            ships4 = 0;
            ships5 = 0;
        }

        // test
        if (gameMode == 5) {
            ships1 = 0;
            ships2 = 0;
            ships3 = 0;
            ships4 = 0;
            ships5 = 0;
        }

        fullness = ships1*6 + ships2*9 + ships3*12 + ships4*15 + ships5*18;
        System.out.println(fullness);

        fullnessLabel.setText(fullness + "%");
        fullnessBar.setProgress((double) fullness/100);

        if(ships1 == 0){sh1min.setDisable(true);}
        if(ships2 == 0){sh2min.setDisable(true);}
        if(ships3 == 0){sh3min.setDisable(true);}
        if(ships4 == 0){sh4min.setDisable(true);}
        if(ships5 == 0){sh5min.setDisable(true);}

        Preview0.setText(String.valueOf(ships1));
        Preview1.setText(String.valueOf(ships2));
        Preview2.setText(String.valueOf(ships3));
        Preview3.setText(String.valueOf(ships4));
        Preview4.setText(String.valueOf(ships5));

    }


    @FXML
    void firstStart() {

        if (!firstStart) {
            return;
        }
        firstStart = false;

        //grid.setStyle("-fx-background-image: url('Water.png')");

        //test
        for (int i = 0; i < 14; i++) {
            testList.add(i, 0);
        }

        totalShips = ships1 + 2 * ships2 + 3 * ships3 + 4 * ships4 + 5 * ships5;
        System.out.println("total ships =" + totalShips);


        /** Pocet lodi*/

        shipNumberList.add(0, ships1);
        shipNumberList.add(1, ships2);
        shipNumberList.add(2, ships3);
        shipNumberList.add(3, ships4);
        shipNumberList.add(4, ships5);

        for (int i = 0; i < shipNumberList.size(); i++) {
            AIShipNumberList.add(i, shipNumberList.get(i));
        }


        /** vytvoreni tlacitek na gridu */
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                Button PlaceBut = new Button("  ");   //"Button " + x + " " + y
                int finalX = x;
                int finalY = y;

                /** Button Style  */
                PlaceBut.setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-background-color: transparent;");
                PlaceBut.setOnMouseEntered(e -> PlaceBut.setStyle("-fx-background-color: #92f238; -fx-opacity: 0.7;"));
                PlaceBut.setOnMouseExited(e -> PlaceBut.setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-background-color: transparent;"));

                PlaceBut.setPrefHeight(grid.getWidth());
                PlaceBut.setPrefWidth(grid.getWidth());
                /** Button event */
                PlaceBut.setOnAction(e -> {
                    currentCoordinates.set(finalX, finalY);
                    CreateShip();
                });
                GridPane.setHalignment(PlaceBut, HPos.CENTER);
                GridPane.setValignment(PlaceBut, VPos.CENTER);
                grid.add(PlaceBut, x, y);
            }
        }


        /** Vytvoreni tlacitek na "enemy gridu" */
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                Button ShootBut = new Button("  ");   //"Button " + x + " " + y

                int finalX = x;
                int finalY = y;


                /** Button Style  */

                ShootBut.setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-background-color: transparent;");
                ShootBut.setOnMouseEntered(e -> ShootBut.setStyle("-fx-background-image: url('target.png'); -fx-background-color:transparent; -fx-background-size: cover; -fx-opacity: 0.8;"));
                ShootBut.setOnMouseExited(e -> ShootBut.setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-background-color: transparent;"));

                ShootBut.setPrefHeight(grid.getWidth());
                ShootBut.setPrefWidth(grid.getWidth());

                /** Button event */
                ShootBut.setOnAction(e -> {
                    currentCoordinates.set(finalX, finalY);
                    BattleController();
                });
                GridPane.setHalignment(ShootBut, HPos.CENTER);
                GridPane.setValignment(ShootBut, VPos.CENTER);
                enemyGrid.add(ShootBut, x, y);
            }
        }


        enemyGrid.setDisable(true);
        Start();
    }


    @FXML
    void Start() {

        spy_button.setDisable(true);

        //Rotate button CSS
        if (rotated) {
            RotateButton.setStyle("-fx-background-image: url('rotateShip.png'); -fx-background-size: contain; -fx-rotate: 0; -fx-background-repeat: no-repeat; -fx-background-position: center ");
        } else
            RotateButton.setStyle("-fx-background-image: url('rotateShip.png'); -fx-background-size: contain; -fx-rotate: 90; -fx-background-repeat: no-repeat; -fx-background-position: center ");

        /** Nastavi pocet lodi*/
        shipNumberList.set(0, ships1);
        shipNumberList.set(1, ships2);
        shipNumberList.set(2, ships3);
        shipNumberList.set(3, ships4);
        shipNumberList.set(4, ships5);


        for (int i = 0; i < shipNumberList.size(); i++) {
            AIShipNumberList.set(i, shipNumberList.get(i));
        }
        Refresh();
    }


    @FXML
    protected void Rotate() {
        RotateButton.setText("");
        rotated = !rotated;
        System.out.println("rotated = " + rotated);
        if (rotated) {
            RotateButton.setStyle("-fx-background-image: url('rotateShip.png'); -fx-background-size: contain; -fx-rotate: 0; -fx-background-repeat: no-repeat; -fx-background-position: center ");
        } else
            RotateButton.setStyle("-fx-background-image: url('rotateShip.png'); -fx-background-size: contain; -fx-rotate: 90; -fx-background-repeat: no-repeat; -fx-background-position: center ");
    }



    public void SelectShip(ActionEvent event) {
        Button sourceButton = (Button) event.getSource();
        char num = sourceButton.getId().charAt(6);
        firstStart();
        shipLength = Character.getNumericValue(num);
    }

    public void CreateShip() {

        if (AIon) {
            tempGrid = enemyGrid;
            tempShipNumberList = AIShipNumberList;
            tempBlockedList = AIBlockedList;
            tempShipPosition = AIShipPosition;
        } else {
            tempGrid = grid;
            tempShipNumberList = shipNumberList;
            tempBlockedList = blockedList;
            tempShipPosition = shipPosition;
        }

        ShipBuilder.CheckPosition(tempShipNumberList, tempBlockedList);
        if (ShipBuilder.error == 0) {
            ShipBuilder.BuildShip(AIon);
        }

        /** Vrácení hodnot do daných listů*/
        if (AIon) {
            enemyGrid = tempGrid;
            AIBlockedList = tempBlockedList;
            AIShipNumberList = tempShipNumberList;
            AIShipPosition = tempShipPosition;
        } else {
            grid = tempGrid;
            blockedList = tempBlockedList;
            shipNumberList = tempShipNumberList;
            shipPosition = tempShipPosition;
        }
        Refresh();
    }


    @FXML
    public void Switch() {
        visible = !visible;

        int totalShips = 0;


        if (visible) {
            for (int i = 0; i < AIShipPosition.size(); i++) {
                for (int j = 0; j < AIShipPosition.get(i).size(); j++) {

                    ImageView indikator = new ImageView("Indicator.png");
                    indikator.setFitHeight((tempGrid.getPrefHeight() / tempGrid.getColumnCount()) * 0.5);
                    indikator.setFitWidth((tempGrid.getPrefWidth() / tempGrid.getRowCount()) * 0.5);
                    indikator.setStyle("-fx-background-color:transparent; -fx-background-size: cover; -fx-opacity: 1;");
                    GridPane.setValignment(indikator, VPos.CENTER);
                    GridPane.setHalignment(indikator, HPos.CENTER);
                    enemyGrid.add(indikator, AIShipPosition.get(i).get(j).x, AIShipPosition.get(i).get(j).y);
                }
            }
        } else {

            for (int i = 0; i < AIShipPosition.size(); i++) {
                totalShips = totalShips + AIShipPosition.get(i).size();

            }
            enemyGrid.getChildren().remove(row * row + 1 + blockedList.size() + PlayerSunken, row * row + 1 + totalShips + blockedList.size() + PlayerSunken);

        }

    }


    @FXML
    public void Refresh() {

        Feedback.setText(FeedbackString);

        Ships1.setText(String.valueOf(shipNumberList.get(0)));
        Ships2.setText(String.valueOf(shipNumberList.get(1)));
        Ships3.setText(String.valueOf(shipNumberList.get(2)));
        Ships4.setText(String.valueOf(shipNumberList.get(3)));
        Ships5.setText(String.valueOf(shipNumberList.get(4)));
    }


    @FXML
    public void ClearBoard() {

        //test
        for (int j = 0; j < shipPosition.size(); j++) {

            if (ContainsThisCoords(shipPosition.get(j), new coordinates(0, 0))) {
                testList.set(0, testList.get(0) + 1);
            }
            if (ContainsThisCoords(shipPosition.get(j), new coordinates(2, 0))) {
                testList.set(1, testList.get(1) + 1);
            }
            if (ContainsThisCoords(shipPosition.get(j), new coordinates(4, 0))) {
                testList.set(2, testList.get(2) + 1);
            }
            if (ContainsThisCoords(shipPosition.get(j), new coordinates(0, 2))) {
                testList.set(3, testList.get(3) + 1);
            }
            if (ContainsThisCoords(shipPosition.get(j), new coordinates(2, 2))) {
                testList.set(4, testList.get(4) + 1);
            }
            if (ContainsThisCoords(shipPosition.get(j), new coordinates(4, 2))) {
                testList.set(5, testList.get(5) + 1);
            }
            if (ContainsThisCoords(shipPosition.get(j), new coordinates(0, 4))) {
                testList.set(6, testList.get(6) + 1);
            }
            if (ContainsThisCoords(shipPosition.get(j), new coordinates(2, 4))) {
                testList.set(7, testList.get(7) + 1);
            }
            if (ContainsThisCoords(shipPosition.get(j), new coordinates(4, 4))) {
                testList.set(8, testList.get(8) + 1);
            }
            if (ContainsThisCoords(shipPosition.get(j), new coordinates(1, 1))) {
                testList.set(9, testList.get(9) + 1);
            }
            if (ContainsThisCoords(shipPosition.get(j), new coordinates(1, 3))) {
                testList.set(10, testList.get(10) + 1);
            }
            if (ContainsThisCoords(shipPosition.get(j), new coordinates(3, 1))) {
                testList.set(11, testList.get(11) + 1);
            }
            if (ContainsThisCoords(shipPosition.get(j), new coordinates(3, 3))) {
                testList.set(12, testList.get(12) + 1);
            }

        }
        testList.set(13, testList.get(13) + 1);
        for (int i = 0; i < testList.size(); i++) {
            System.out.println(i + " " + testList.get(i));
        }


        grid.getChildren().remove(row * row + 1, grid.getChildren().size());

        blockedList.clear();
        AIBlockedList.clear();

        for (int i = 0; i < shipPosition.size(); i++) {
            shipPosition.get(i).clear();
        }

        Start();
    }

    void ShootShip() {


        if (AIon) {
            tempGrid = grid;
            tempShipPosition = shipPosition;
            tempBlockedList = AIBlockedList;
            tempSunken = AIsunken;

        } else {
            tempGrid = enemyGrid;
            tempShipPosition = AIShipPosition;
            tempBlockedList = blockedList;
            tempSunken = PlayerSunken;
        }


        /** Spies order in grid*/
        if (visible) {
            Switch();
            BattleHandler.Shoot(tempShipPosition, AIon);
            Switch();
        } else BattleHandler.Shoot(tempShipPosition, AIon);


        /** Vrácení hodnot do daných listů*/
        if (AIon) {
            grid = tempGrid;
            shipPosition = tempShipPosition;
            AIBlockedList = tempBlockedList;
            AIsunken = tempSunken;

        } else {
            enemyGrid = tempGrid;
            AIShipPosition = tempShipPosition;
            blockedList = tempBlockedList;
            PlayerSunken = tempSunken;

        }
        Refresh();

    }


    @FXML
    void StartBattle() {
        int postaveneLode = 0;
        for (int num = 4; num >= 0; num--) {
            postaveneLode += tempShipNumberList.get(num);
        }

        System.out.println(postaveneLode);
        if (postaveneLode != 0) {
            FeedbackString = "Muséte postavit všechny lodě";
            Refresh();
            return;
        }


        AIon = true;

        RandomBuild();

        tempBlockedList.clear();
        blockedList.clear();
        AIBlockedList.clear();

        AIon = false;

        enemyGrid.setDisable(false);
        grid.setDisable(true);

        //TODO - prechod z build modu do battle modu

        spy_button.setDisable(!spyMode);


    }


    @FXML
    void RandomBuild() {
        Random r = new Random();

        if (AIon) {
            tempShipNumberList = AIShipNumberList;
        } else {
            tempShipNumberList = shipNumberList;
        }


        for (int num = 4; num >= 0; num--) {
            while (tempShipNumberList.get(num) != 0) {
                rotated = r.nextBoolean();
                shipLength = num + 1;
                currentCoordinates.x = r.nextInt(10);
                currentCoordinates.y = r.nextInt(10);
                CreateShip();
            }
        }
        if (AIon) {
            AIShipNumberList = tempShipNumberList;
        } else {
            shipNumberList = tempShipNumberList;
        }
    }


    void BattleController() {
        ShootShip();

        if (tryAgain) {
            return;
        }

        AIon = true;

        tryAgain = true;
        while (tryAgain) {
            BotAlgorithm.BotAttack();
            ShootShip();
        }
        AIon = false;

    }

    public static Boolean ContainsThisCoords(ArrayList<coordinates> coordsArray, coordinates finding) {
        //System.out.println("checking coordinates " + finding);
        for (int i = 0; i < coordsArray.size(); i++) {
            if (finding.x == coordsArray.get(i).x && finding.y == coordsArray.get(i).y) {
                return true;
            }
        }
        return false;
    }


    @FXML
    public void SpymodeBool() {
        spyMode = !spyMode;
        System.out.println(spyMode);
    }


    //test
    @FXML
    public void Test() {
        for (int i = 0; i < 57412; i++) {
            RandomBuild();
            ClearBoard();
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {




    }


}

