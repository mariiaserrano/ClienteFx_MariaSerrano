package Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import lombok.Data;
import okhttp3.OkHttpClient;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PrincipalController implements Initializable {

    @FXML
    private BorderPane fxRoot;

    OkHttpClient okHttpClient;

    private Stage myStage = new Stage();

    public void setMyStage(Stage myStage) {
        this.myStage = myStage;
    }

    private AnchorPane pantallaLogin;
    private LoginController controllerLogin;

    private AnchorPane pantallaProductos;
    private ProductosController controllerProductos;

    private AnchorPane pantallaCesta;
    private CestaController controllerCesta;


    private void preCargaLogin() {
        try {
            FXMLLoader loaderMenu = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
            pantallaLogin = loaderMenu.load();
            controllerLogin = loaderMenu.getController();
            controllerLogin.setInicio(this);
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void preCargaProductos() {
        try {
            FXMLLoader loaderMenu = new FXMLLoader(getClass().getResource("/fxml/Productos.fxml"));
            pantallaProductos = loaderMenu.load();
            controllerProductos = loaderMenu.getController();
            controllerProductos.setInicio(this);
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void preCargaCesta() {
        try {
            FXMLLoader loaderMenu = new FXMLLoader(getClass().getResource("/fxml/Cesta.fxml"));
            pantallaCesta = loaderMenu.load();
            controllerCesta = loaderMenu.getController();
            controllerCesta.setInicio(this);
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void cargarPantallaLogin() {
        fxRoot.setCenter(pantallaLogin);
        fxRoot.setMinWidth(1000);
        fxRoot.setMinHeight(500);
        myStage.sizeToScene();
    }

    @FXML
    public void cargarPantallaProductos() throws IOException {
        controllerProductos.cargarLista();
        fxRoot.setCenter(pantallaProductos);
        fxRoot.setMinWidth(1000);
        fxRoot.setMinHeight(500);
        myStage.sizeToScene();
    }

    @FXML
    public void cargarPantallaCesta() throws IOException {
        controllerCesta.cargarCesta();

        fxRoot.setCenter(pantallaCesta);
        fxRoot.setMinWidth(1000);
        fxRoot.setMinHeight(500);
        myStage.sizeToScene();

    }

    public void setOkHttpClient(OkHttpClient okHttpClient){
        this.okHttpClient=okHttpClient;

    }

    public OkHttpClient getOkHttpClient(){
        return okHttpClient;
    }



    @Override
    public void initialize(URL url, ResourceBundle rb) {
        preCargaLogin();
        preCargaProductos();
        preCargaCesta();
        cargarPantallaLogin();
    }


}