package translate;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

public class Main {

    public static void main(String[] args) throws IOException {

        Tester tester=new Tester();
        DictMap dictMap=new DictMap(tester.init("./input/DataBase"));

        tester.TestAddWord("./input/Words_to_Add",dictMap);
        tester.TestRemoveWord("./input/Words_to_Remove",dictMap);
        tester.TestAddDefinition("./input/Definitions_to_Add",dictMap);
        tester.TestRemoveDefinition("./input/Definitions_to_Remove",dictMap);
        tester.TestTranslateWord("./input/Words_to_Translate",dictMap);
        tester.TestTranslateSentence("./input/Sentences_to_Translate",dictMap);
        tester.TestTranslateSentence3("./input/Sentences_to_Translate3",dictMap);
        tester.TestGetDefinitions("./input/Definitions_to_Get",dictMap);
        tester.TestExportDictionary("./input/Dictionary_to_Export",dictMap);

    }
}
