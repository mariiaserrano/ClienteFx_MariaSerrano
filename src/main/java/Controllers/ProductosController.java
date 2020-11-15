package Controllers;

import Config.Configuration;
import Modelo.Producto;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
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
import java.util.stream.Collectors;

public class ProductosController implements Initializable {
    private PrincipalController inicio;
    private Alert alertError;
    private Alert alertInformacion;

    @FXML
    private ListView<Producto> fxListViewProducto;

    private Alert alert;

    public Alert getAlert() {
        return alert;
    }


    public void setInicio(PrincipalController inicio) {
        this.inicio = inicio;
    }

    public List verProductos() throws IOException {
        OkHttpClient clientOK = inicio.getOkHttpClient();

        Request request = new Request.Builder()
                .url(Configuration.getInstance().getRuta() + "productos")
                .build();

        Response resp = clientOK.newCall(request).execute();
        String productos = resp.body().string();
        String[] productosLista = productos.split(",");
        List<String> listaProductos = Arrays.asList(productosLista);

        clientOK.connectionPool().evictAll();

        return listaProductos;
    }

    public void cargarLista() throws IOException {
        fxListViewProducto.getItems().clear();

        List<Producto> productos;
        productos = verProductos();

        if (productos != null) {

            fxListViewProducto.getItems().addAll(verProductos());


        } else {
            alertError.setContentText("Lista Vacia");
            alertError.showAndWait();
        }
    }

    @FXML
    private void abreCesta() throws IOException {
        OkHttpClient okHttpClient = inicio.getOkHttpClient();
        List<Producto> productoComprado = new ArrayList<>();

        productoComprado.addAll(fxListViewProducto.getSelectionModel().getSelectedItems());


        if(!productoComprado.isEmpty()){
            String url = Configuration.getInstance().getRuta() + "cesta";
            FormBody.Builder form1 = new FormBody.Builder();
            form1.add("anadiendo","true");
            form1.add("productoComprado",productoComprado.toString());


            FormBody formBody = form1.build();

            Request request = new Request.Builder()
                    .url(url)
                    .post(formBody)
                    .build();

            Response resp = okHttpClient.newCall(request).execute();
            String response = resp.body().string();


            if(resp.isSuccessful()){
                alert.setContentText(resp.code() + response);
                alert.showAndWait();
            }
            else {
                alert.setContentText(resp.code() + response);
                alert.showAndWait();

            }


        }



        inicio.cargarPantallaCesta();
    }


    @SneakyThrows
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fxListViewProducto.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        alert =  new Alert(Alert.AlertType.INFORMATION);

    }
}
