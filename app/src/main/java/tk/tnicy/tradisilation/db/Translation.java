package tk.tnicy.tradisilation.db;

import org.litepal.crud.DataSupport;

import java.io.Serializable;
import java.util.ArrayList;

public class Translation extends DataSupport implements Serializable {
    private static final long serialVersionUID = -2401737782097994016L;
    private int id;
    private String BigType;
    private String SmallType;
    private String chi;
    private String eng;
    private String related;
    private String detail;

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getRelated() {
        return related;
    }

    public void setRelated(String related) {
        this.related = related;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBigType() {
        return BigType;
    }

    public void setBigType(String bigType) {
        BigType = bigType;
    }

    public String getSmallType() {
        return SmallType;
    }

    public void setSmallType(String smallType) {
        SmallType = smallType;
    }

    public String getChi() {
        return chi;
    }

    public void setChi(String chi) {
        this.chi = chi;
    }

    public String getEng() {
        return eng;
    }

    public void setEng(String eng) {
        this.eng = eng;
    }



    //解码String 的 related 返回列表
    public ArrayList<String> getRelatedString(){

        ArrayList<String> strings = new ArrayList<>();
        String[] rels = related.split(" ");
        for (String s :
                rels) {
            if (!s.isEmpty()) {
                strings.add(s);
            }
        }
        return strings;
    }

    public boolean addRelated(String rel) {
        try {
            ArrayList<String> relate = getRelatedString();
            relate.add(rel);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public Translation(){

    }

    public Translation(int id, String bigType, String smallType, String chi, String eng, String related, String detail) {
        this.id = id;
        BigType = bigType;
        SmallType = smallType;
        this.chi = chi;
        this.eng = eng;
        this.related = related;
        this.detail = detail;
    }
}
