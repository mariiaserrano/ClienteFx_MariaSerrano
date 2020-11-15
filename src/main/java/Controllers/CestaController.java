package Controllers;

import Config.Configuration;
import Modelo.Producto;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import lombok.SneakyThrows;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class CestaController implements Initializable {
    private PrincipalController inicio;
    private Alert alert;

    @FXML
    private ListView fxListaCesta;

    public void setInicio(PrincipalController inicio) {
        this.inicio = inicio;
    }

    protected void cargarCesta() throws IOException {
        OkHttpClient okHttpClient = inicio.getOkHttpClient();
        String url = Configuration.getInstance().getRuta() + "cesta";
        FormBody.Builder form1 = new FormBody.Builder();
        form1.add("anadiendo","false");
        FormBody formBody = form1.build();

        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();
        Response resp = okHttpClient.newCall(request).execute();
        String response = resp.body().string();


        if(resp.isSuccessful()){

            List<String> productos = new ArrayList<>();
            productos.add(response);
            fxListaCesta.getItems().add(productos);
        }
        else{
            alert.setContentText(resp.code() + response);
            alert.showAndWait();
        }


    }

    @FXML
    private void anadirProductos() throws IOException {
        inicio.cargarPantallaProductos();
    }


    @FXML
    private void comprarProductos(){

        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.getDialogPane().lookupButton(ButtonType.OK).setId("alertOK");
        alert.setContentText(" La compra se realizó correctamente");
        alert.showAndWait();
    }

    @FXML
    private void limpiarCesta(){
        fxListaCesta.getItems().clear();
        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.getDialogPane().lookupButton(ButtonType.OK).setId("alertOK");
        alert.setContentText(" Has limpiado la cesta");
        alert.showAndWait();
    }

    @FXML
    private void cerrarSesion() throws IOException {
      OkHttpClient okHttpClient = inicio.getOkHttpClient();
      okHttpClient = null;

            alert = new Alert(Alert.AlertType.INFORMATION);
        alert.getDialogPane().lookupButton(ButtonType.OK).setId("alertOK");
        alert.setContentText("Cerrando sesion");
        alert.showAndWait();

        inicio.cargarPantallaLogin();

    }

    @SneakyThrows
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        alert =  new Alert(Alert.AlertType.ERROR);
    }
}
