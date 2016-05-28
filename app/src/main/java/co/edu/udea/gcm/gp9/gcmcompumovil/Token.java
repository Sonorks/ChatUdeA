package co.edu.udea.gcm.gp9.gcmcompumovil;

/**
 * Created by sonorks on 23/05/16.
 */
public class Token {
    private static Token instance = null;
    private String token;
    private Token(){

    }
    public static Token getInstance(){
        if(instance == null) {
            instance = new Token();
        }
        return instance;
    }
    public void setToken(String token){
        this.token=token;
    }
    public String getToken(){
        return token;
    }
}
