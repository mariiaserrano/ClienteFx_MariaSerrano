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
import java.util.*;

public class CestaController implements Initializable {
    private PrincipalController inicio;
    private Alert alertError;
    private Alert alertinfo;

    @FXML
    private ListView fxListaCesta;

    public void setInicio(PrincipalController inicio) {
        this.inicio = inicio;
    }

    protected void cargarCesta() throws IOException {
        OkHttpClient okHttpClient = inicio.getOkHttpClient();
        String url = Configuration.getInstance().getRuta() + "cesta";
        FormBody.Builder form1 = new FormBody.Builder();
        form1.add("anadiendo", "false");
        FormBody formBody = form1.build();

        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();
        Response resp = okHttpClient.newCall(request).execute();
        String response = resp.body().string();


        if (resp.isSuccessful()) {

            List<String> productos = new ArrayList<>();
            productos.add(response);

            fxListaCesta.getItems().add(productos);


        } else {
            alertError.setContentText(resp.code() + resp.message());
            alertError.showAndWait();
        }
        okHttpClient.connectionPool().evictAll();

    }

    @FXML
    private void anadirProductos() throws IOException {
        inicio.cargarPantallaProductos();
    }


    @FXML
    private void comprarProductos() {

        fxListaCesta.getItems().clear();
        alertinfo.setContentText(" La compra se realizó correctamente");
        alertinfo.showAndWait();
    }


    @FXML
    private void cerrarSesion() throws IOException {
        inicio.setOkHttpClient(null);

        alertinfo.setContentText("Cerrando sesion");
        alertinfo.showAndWait();

        inicio.cargarPantallaLogin();

    }

    @SneakyThrows
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        alertError = new Alert(Alert.AlertType.ERROR);
        alertinfo = new Alert(Alert.AlertType.INFORMATION);
    }
}
