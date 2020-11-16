package Controllers;

import Config.Configuration;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import okhttp3.*;

import java.io.IOException;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.URL;
import java.util.ResourceBundle;


public class LoginController implements Initializable {

    private PrincipalController inicio;

    public void setInicio(PrincipalController inicio) {
        this.inicio = inicio;
    }


    @FXML
    private TextField fxUsuario;
    @FXML
    private TextField fxPass;

    private Alert alert;

    public Alert getAlert() {
        return alert;
    }


    @FXML
    private void hazLogin() throws IOException {
        CookieManager cookieManager = new CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);

        inicio.setOkHttpClient( new OkHttpClient.Builder()
                .cookieJar(new JavaNetCookieJar(cookieManager))
                .build());
        OkHttpClient okHttpClient = inicio.getOkHttpClient();


        String url = Configuration.getInstance().getRuta() + "login";

        RequestBody formBody = new FormBody.Builder()
                .add("usuario", fxUsuario.getText())
                .add("contrasena", fxPass.getText())
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();


            Response resp = okHttpClient.newCall(request).execute();

            if(resp.isSuccessful()){
                String respuesta = resp.body().string();

                if(respuesta.equals("correcto")){
                    inicio.cargarPantallaProductos();
                }
                else{
                    alert.setContentText("Error :" + resp.code() +" "+ resp.message() + " " + respuesta);
                    alert.showAndWait();
                }
            }
            else{
                alert.setContentText("Error : " + resp.code() + " :  " + resp.message());
                alert.showAndWait();
            }


       okHttpClient.connectionPool().evictAll();



    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        alert =  new Alert(Alert.AlertType.ERROR);
    }
}
