package tk.tnicy.tradisilation.Entity;

import java.io.Serializable;
import java.util.ArrayList;

public class BigType implements Serializable {


    private static final long serialVersionUID = 6684005020816352968L;
    public String typeName;
    public ArrayList<SmallType> smallTypes;

    BigType(){

    }

    public BigType(String typeName, ArrayList<SmallType> smallTypes) {
        this.typeName = typeName;
        this.smallTypes = smallTypes;
    }
}
