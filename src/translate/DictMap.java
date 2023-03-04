
package translate;
import com.google.gson.Gson;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;


public class DictMap {
    Map<String, Dictionary> DictMap;


    public DictMap(Map<String, Dictionary> dictMap) {
        this.DictMap = dictMap;
    }

    public boolean addWord(Word word, String language) {

        Dictionary dictionary = this.DictMap.get(language);
        Word existingWord = dictionary.searchWord(word);

        int updated = 0;

        if (existingWord != null) {
            for (Definition newWordDef : word.definitions) {
                for (Definition existingWordDef : existingWord.definitions)

                    if (newWordDef.checkIfEqual(existingWordDef) == 0) {

                        for (String newTextContent : newWordDef.text) {
                            if (existingWordDef.missingText(newTextContent) != 0) {
                                existingWordDef.text.add(newTextContent);
                                updated = 1;
                            }
                        }
                    }
            }

            if (updated == 1)
                return true;
        } else {
            dictionary.getDictionary().add(word);
            return true;
        }
        //they are identical
        return false;
    }


    boolean removeWord(String word, String language) {

        Dictionary dictionary = this.DictMap.get(language);
        Word existingWord = dictionary.searchWordbyName(word);

        if (existingWord == null)
            return false;         //the word doesn't exist in the dictionary

        dictionary.words.remove(existingWord);

        return true;
    }


    boolean addDefinitionForWord(String word, String language, Definition definition) {
        Dictionary dictionary = this.DictMap.get(language);
        Word existingWord = dictionary.searchWordbyName(word);

        if (existingWord == null)
            return false;      //the word doesn't exist in the dictionary

        for (Definition definition_iterator : existingWord.definitions)
            if (definition_iterator.getDict().equals(definition.getDict()))
                return false;
        existingWord.definitions.add(definition);
        return true;
    }

    boolean removeDefinition(String word, String language, String dictionary) {

        Dictionary dictionaryValue = this.DictMap.get(language);
        Word existingWord = dictionaryValue.searchWordbyName(word);
        String[] dictionary_identifier = dictionary.split("\n");
        dictionary_identifier[0] = dictionary_identifier[0].substring(0, dictionary_identifier[0].length() - 1);  //remaining char from parsing

        Definition dictionaryToRemove = existingWord.searchDefinition(dictionary_identifier[0], dictionary_identifier[1]);
        boolean removed = existingWord.definitions.remove(dictionaryToRemove);

        if (removed)
            return true;

        return false;
    }


    String translateWord(String word, String fromLanguage, String toLanguage) {

        Dictionary fromDictionary = this.DictMap.get(fromLanguage);
        Dictionary toDictionary = this.DictMap.get(toLanguage);

        Word existingWord = fromDictionary.searchWordbyName(word);
        if(existingWord==null)
            return null;
        String englishTranslation = existingWord.getWord_en();

        String translatedWord = toDictionary.searchWordbyEnglishTranslation(englishTranslation);

        return translatedWord;
    }


    String translateSentence(String sentence, String fromLanguage, String toLanguage) {

        Dictionary fromDictionary = this.DictMap.get(fromLanguage);
        Dictionary toDictionary = this.DictMap.get(toLanguage);
        String[] wordsToTranslate = sentence.split(" ");

        int i = 0;
        String translatedSentence = "";
        while (i < wordsToTranslate.length) {
            Word existingWord = fromDictionary.searchWordbyName(wordsToTranslate[i]);

            if (existingWord == null) {
                translatedSentence += wordsToTranslate[i] + " ";
                i++;
                continue;
            }

            String englishTranslation = existingWord.getWord_en();
            String translatedWord = toDictionary.searchWordbyEnglishTranslation(englishTranslation);
            if (translatedWord == null) {
                translatedSentence += wordsToTranslate[i] + " ";
                i++;
                continue;
            }

            translatedSentence += translatedWord + " ";
            i++;
        }
        return translatedSentence;
    }


    ArrayList<String> translateSentences(String sentence, String fromLanguage, String toLanguage) {

        Dictionary toDictionary = this.DictMap.get(toLanguage);
        String translatedSentence = translateSentence(sentence, fromLanguage, toLanguage);
        System.out.println(translatedSentence);

        String[] words = translatedSentence.split(" ");

        int i = words.length-1;

        ArrayList<String> partial_translations=new ArrayList<String>();
        ArrayList<String> translations=new ArrayList<String>();

        partial_translations.add("");


        String result="";
        while(i>=0) {
            for (String synonym1 : toDictionary.searchWordbyName(words[i]).getSynonyms()) {
                for (String partialSentence : partial_translations) {
                    result = synonym1 +  " "+partialSentence ;
                    translations.add(result);

                }
            }
            partial_translations.clear();
            partial_translations.addAll(translations);
            if(i!=0)
            translations.clear();
            i--;
        }

        return translations;
    }


    public ArrayList<Definition> getDefinitionsForWord(String word, String language){

        Dictionary dictionary = this.DictMap.get(language);
        Word existingWord = dictionary.searchWordbyName(word);
        Collections.sort(existingWord.definitions);
        return existingWord.definitions;

    }

    void exportDictionary(String language) throws IOException {
        Dictionary dictionary = this.DictMap.get(language);
        Collections.sort(dictionary.words);

        FileOutputStream OutputFile = new FileOutputStream("out/output/Export_Dictionary");
        PrintStream printStreamToFile = new PrintStream(OutputFile);
        System.setOut(printStreamToFile);


        System.out.println(new Gson().toJson(dictionary));

        OutputFile.close();

    }



}
