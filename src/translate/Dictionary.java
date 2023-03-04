package translate;
import java.util.ArrayList;


public class Dictionary{
    ArrayList<Word>words;

    public Dictionary() {
        this.words =new ArrayList<Word>();
    }

    public ArrayList<Word> getDictionary() {
        return words;
    }

    public void setDictionary(ArrayList<Word> dictionary) {
        this.words = dictionary;
    }

    public Word searchWord(Word word){
        for(Word word_iterator:this.words)
            if(word_iterator.getWord().equals(word.getWord()))
                return word_iterator;
            return null;
    }

    public Word searchWordbyName(String word){
        for(Word word_iterator:this.words)
            if(word_iterator.getWord().equals(word))
                return word_iterator;
        return null;
    }

    public String searchWordbyEnglishTranslation(String word){
        for(Word word_iterator:this.words)
            if(word_iterator.getWord_en().equals(word))
                return word_iterator.getWord();
        return null;
    }

}
