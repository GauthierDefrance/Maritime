package saveSystem;

import config.GameParameter;

import java.io.Serial;
import java.io.Serializable;

public class ParamSave implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private GameParameter param;

    private ParamSave(GameParameter param) {
        this.param = param;
    }

    //Getters

    public GameParameter getParam() {
        return param;
    }

    //Setters

    public void setParam(GameParameter param) {
        this.param = param;
    }

    //Create

    public static ParamSave create(GameParameter param) {
        return new ParamSave(param);
    }

}
