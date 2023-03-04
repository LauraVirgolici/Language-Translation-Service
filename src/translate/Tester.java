package translate;

import com.google.gson.Gson;

import java.io.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class Tester {
    File folder;
    Parser parser;

    public Tester() {
        this.parser = new Parser();
    }

    /*Get initial data from database*/
    public Map<String, Dictionary> init(String FilePath) {
        Map<String, Dictionary> DictMap = new TreeMap<>();

        this.folder = new File(FilePath);
        File[] listOfFiles = folder.listFiles();

        for (File file : listOfFiles) {
            if (file.isFile()) {
                this.parser.parseDictionary(file.getPath(), file.getName(), DictMap);
            }
        }
        return DictMap;
    }


    /*Add new words*/
    public void TestAddWord(String FilePath, DictMap dictMap) throws IOException {
        this.folder = new File(FilePath);
        File[] listOfFiles = folder.listFiles();


        FileOutputStream OutputFile = new FileOutputStream("out/output/Add_Words");
        PrintStream printStreamToFile = new PrintStream(OutputFile);
        System.setOut(printStreamToFile);

        for (File file : listOfFiles) {
            if (file.isFile()) {

                String[] key = file.getName().split("_");
                Word word_to_add = this.parser.parseWord(file.getPath(), file.getName(), dictMap);

                boolean added_word=dictMap.addWord(word_to_add, key[0]);

                if(added_word)
                System.out.println("Cuvantul "+word_to_add.getWord()+" a fost adaugat.");
                else
                    System.out.println("Cuvantul "+word_to_add.getWord()+" NU a fost adaugat.");
            }
        }
        OutputFile.close();

    }


    /*Remove words*/
    public void TestRemoveWord(String FilePath, DictMap dictMap) throws IOException {
        this.folder = new File(FilePath);
        File[] listOfFiles = folder.listFiles();

        FileOutputStream OutputFile = new FileOutputStream("out/output/Remove_Words");
        PrintStream printStreamToFile = new PrintStream(OutputFile);
        System.setOut(printStreamToFile);

        for (File file : listOfFiles) {
            if (file.isFile()) {
                String[] key = file.getName().split("_");
                String contentString = this.parser.parseString(file.getPath(), file.getName());
                boolean removed_word = dictMap.removeWord(contentString, key[0]);

                if(removed_word)
                    System.out.println("Cuvantul "+contentString+" a fost sters.");
                else
                    System.out.println("Cuvantul "+contentString+" nu exista in dictionar.");
            }
        }
        OutputFile.close();
    }



    /*Add a new definition*/
    public void TestAddDefinition(String FilePath, DictMap dictMap)throws IOException {
        this.folder = new File(FilePath);
        File[] listOfFiles = folder.listFiles();

        FileOutputStream OutputFile = new FileOutputStream("out/output/Add_Defs");
        PrintStream printStreamToFile = new PrintStream(OutputFile);
        System.setOut(printStreamToFile);

        for (File file : listOfFiles) {
            if (file.isFile()) {
                String[] key = file.getName().split("_");
                Definition definition = this.parser.parseDefinition(file.getPath(), file.getName());
                boolean added_def = dictMap.addDefinitionForWord(key[1], key[0], definition);

                if(added_def)
                    System.out.println("Cuvantului "+key[1]+" i-a fost adaugata noua definitie.");
                else
                    System.out.println("Cuvantului "+key[1]+" NU i-a fost adaugata noua definitie.");
            }
        }
        OutputFile.close();
    }


    /*Remove a definition*/
    public void TestRemoveDefinition(String FilePath, DictMap dictMap) throws IOException {
        this.folder = new File(FilePath);
        File[] listOfFiles = folder.listFiles();

        FileOutputStream OutputFile = new FileOutputStream("out/output/Remove_def");
        PrintStream printStreamToFile = new PrintStream(OutputFile);
        System.setOut(printStreamToFile);

        for (File file : listOfFiles) {
            if (file.isFile()) {
                String[] key = file.getName().split("_");
                String contentString = this.parser.parseString(file.getPath(), file.getName());
                boolean removed_def = dictMap.removeDefinition(key[1], key[0], contentString);

                if(removed_def)
                    System.out.println("Definitia cuvantului "+key[1]+" a fost stearsa");
                else
                    System.out.println("Definitia cuvantului "+key[1]+" NU exista.");
            }
        }
        OutputFile.close();
    }



    /*Translate a word*/
    public void TestTranslateWord(String FilePath, DictMap dictMap) throws IOException {
        this.folder = new File(FilePath);
        File[] listOfFiles = folder.listFiles();

        FileOutputStream OutputFile = new FileOutputStream("out/output/Translate_words");
        PrintStream printStreamToFile = new PrintStream(OutputFile);
        System.setOut(printStreamToFile);


        for (File file : listOfFiles) {
            if (file.isFile()) {
                String[] key = file.getName().split("_");
                String word_to_translate = this.parser.parseString(file.getPath(), file.getName());
                String translated_word = dictMap.translateWord(word_to_translate, key[0], key[1]);

                if(translated_word!=null)
                    System.out.println("Traducerea cuvantului (din limba "+key[0]+") "+word_to_translate+" in limba "+key[1]+" este "+ translated_word);
                else
                    System.out.println("Cuvantul "+word_to_translate+" nu exista in dictionar.");

            }
        }
        OutputFile.close();
    }

    /*Translate a sentence*/
    public void TestTranslateSentence(String FilePath, DictMap dictMap) throws IOException {
        this.folder = new File(FilePath);
        File[] listOfFiles = folder.listFiles();

        FileOutputStream OutputFile = new FileOutputStream("out/output/Translate_sentences");
        PrintStream printStreamToFile = new PrintStream(OutputFile);
        System.setOut(printStreamToFile);

        for (File file : listOfFiles) {
            if (file.isFile()) {
                String[] key = file.getName().split("_");
                String sentence_to_translate = this.parser.parseString(file.getPath(), file.getName());
                String translated_sentence = dictMap.translateSentence(sentence_to_translate, key[0], key[1]);

                System.out.println("Propozitie: "+sentence_to_translate+"\n"+"Traducere: "+translated_sentence);
                System.out.println("-------------------\n");

            }
        }
        OutputFile.close();
    }


    public void TestTranslateSentence3(String FilePath, DictMap dictMap) throws IOException {
        this.folder = new File(FilePath);
        File[] listOfFiles = folder.listFiles();

        FileOutputStream OutputFile = new FileOutputStream("out/output/Translate_sentences3");
        PrintStream printStreamToFile = new PrintStream(OutputFile);
        System.setOut(printStreamToFile);

        for (File file : listOfFiles) {
            if (file.isFile()) {
                String[] key = file.getName().split("_");
                String sentence_to_translate = this.parser.parseString(file.getPath(), file.getName());
                ArrayList<String> translations = dictMap.translateSentences(sentence_to_translate, key[0], key[1]);

                if (translations.size() < 3) {
                    System.out.println("Nu se pot furniza 3 traduceri diferite");
                    for (int i = 0; i < translations.size(); i++)
                        System.out.println(translations.get(i));
                } else
                    for (int i = 0; i < 3; i++)
                        System.out.println(translations.get(i));

                System.out.println("---------------\n");
            }
        }
        OutputFile.close();
    }


    public void TestGetDefinitions(String FilePath, DictMap dictMap) throws IOException{
        this.folder = new File(FilePath);
        File[] listOfFiles = folder.listFiles();

        FileOutputStream OutputFile = new FileOutputStream("out/output/Get_definitions");
        PrintStream printStreamToFile = new PrintStream(OutputFile);
        System.setOut(printStreamToFile);

        for (File file : listOfFiles) {
            if (file.isFile()) {
                String[] key = file.getName().split("_");
                String word = this.parser.parseString(file.getPath(), file.getName());

                ArrayList<Definition> definitions = dictMap.getDefinitionsForWord(word, key[0]);
                System.out.println(new Gson().toJson(definitions));
            }
        }

        OutputFile.close();
    }


    public void TestExportDictionary(String FilePath, DictMap dictMap) throws IOException {
        this.folder = new File(FilePath);
        File[] listOfFiles = folder.listFiles();

        for (File file : listOfFiles) {
            if (file.isFile()) {
                String language = this.parser.parseString(file.getPath(), file.getName());
                dictMap.exportDictionary(language);
            }
        }


    }
}