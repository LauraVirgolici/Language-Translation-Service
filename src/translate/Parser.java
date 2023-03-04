package translate;
import java.io.*;
import java.lang.reflect.Type;
import java.nio.charset.*;
import java.util.ArrayList;
import java.util.Map;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.io.*;



public class Parser {

    public void parseDictionary(String Path, String FileName, Map<String, Dictionary> DictMap) {

        String[] key = FileName.split("_"); // dictionary language; key[0]=language, key[1]="dict.json"

        File file = new File(Path);
        try {
            String file_content = FileUtils.readFileToString(file, StandardCharsets.UTF_8);

            Type dict_type=new TypeToken<ArrayList<Word>>(){}.getType();                  //define object type

            Dictionary dictionary=new Dictionary();
            dictionary.setDictionary(new Gson().fromJson(file_content,dict_type));       //get dictionary from json

            DictMap.put(key[0],dictionary);                                              //insert dictionary in map

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public Word parseWord(String Path, String FileName, DictMap dictMap) {

        boolean added_word=false;

        File file = new File(Path);
        try {
            String file_content = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
            Word word=new Gson().fromJson(file_content,Word.class);       //get dictionary from json
            return word;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }



    public String parseString(String Path, String FileName){

        String[] key = FileName.split("_");
        File file = new File(Path);
        try {
            String contentString = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
            return contentString;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }


    public Definition parseDefinition(String Path, String FileName) {

        File file = new File(Path);
        try {
            String file_content = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
            Definition definition=new Gson().fromJson(file_content,Definition.class);       //get dictionary from json
            return definition;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }







}
