package translate;

import java.util.ArrayList;

public class Definition implements Comparable<Definition>{

    String dict;
    String dictType;
    int year;
    ArrayList<String> text;

    public String getDict() {
        return dict;
    }

    public String getDictType() {
        return dictType;
    }

    public int getYear() {
        return year;
    }


    public int missingText(String definitionToFind) {

        for(String defContent: this.text)
            if(definitionToFind.equals(defContent))
                return 0;

            return 1;
    }




    public int checkIfEqual(Definition o) {
        if(this.getDict().equals(o.getDict()) && this.getDictType().equals(o.getDictType()))
        return 0;

        return -1;
    }

    @Override
    public int compareTo(Definition o) {
        return this.year-o.year;
    }





}
