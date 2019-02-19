package tk.tnicy.tradisilation.Entity;

import tk.tnicy.tradisilation.db.Translation;

import java.io.Serializable;
import java.util.ArrayList;

public class SmallType implements Serializable {
    private static final long serialVersionUID = -7588809775343363918L;
    public String typeName;
    public ArrayList<Translation> translations;

    SmallType(){

    }

    public SmallType(String typeName, ArrayList<Translation> translations) {
        this.typeName = typeName;
        this.translations = translations;
    }
}