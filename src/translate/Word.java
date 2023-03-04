package translate;

import java.util.ArrayList;

public class Word implements Comparable<Word>{
    String word;
    String word_en;
    String type;
    ArrayList<String> singular;
    ArrayList<String> plural;
    ArrayList<Definition>definitions;

    public Word(String word, String word_en, String type) {
        this.word = word;
        this.word_en = word_en;
        this.type = type;
        this.singular =new ArrayList<String>();
        this.plural =new ArrayList<String>();
        this.definitions =new ArrayList<Definition>();
    }

    public String getWord() {
        return word;
    }

    public String getWord_en() {
        return word_en;
    }

    public String getType() {
        return type;
    }


    public Definition searchDefinition(String identifier1, String identifier2){

        for(Definition definition:this.definitions)
            if(definition.getDict().equals(identifier1)&&
            definition.getDictType().equals(identifier2))
                return definition;

            return null;
    }

    public ArrayList<String> getSynonyms(){
        for(Definition definition:this.definitions)
            if(definition.getDictType().equals("synonyms"))
                return definition.text;

        return null;
    }


    @Override
    public int compareTo(Word o) {
       return this.getWord().compareTo(o.getWord());
    }
}
