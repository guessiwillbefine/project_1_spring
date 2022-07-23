package vadim.andreich.util;

/**
 * temporary storage of data from input field
 */

public class Temp {
    private final String request;
    public Temp(){
        this.request = "";
    }
    public Temp(String request){
        this.request = request;
    }
    public String getParam() {return request;}

}
