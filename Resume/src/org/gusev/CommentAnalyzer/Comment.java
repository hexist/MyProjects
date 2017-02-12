package org.gusev.CommentAnalyzer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Comment {

    enum Label {
        SPAM, NEGATIVE_TEXT, TOO_LONG, OK
    }


    interface TextAnalyzer {
        Label processText(String text);
    }


    abstract class KeywordAnalyzer implements TextAnalyzer {
        protected abstract String[] getKeywords();

        protected abstract Label getLabel();

        @Override
        public Label processText(String text) {
            return null;
        }
    }


    class NegativeTextAnalyzer extends KeywordAnalyzer {
        private String[] check = new String[]{":(", ":|", "=("};
        private Label label = null;

        @Override
        public Label processText(String text) {
            label = Label.OK;

            for (int i = 0; i < check.length; i++) {
                if (text.contains(check[i])) {
                    label = Label.NEGATIVE_TEXT;
                }
            }

            return label;
        }


        @Override
        protected Label getLabel() {
            return label;
        }

        @Override
        protected String[] getKeywords() {
            return check;
        }
    }


    class SpamAnalyzer extends KeywordAnalyzer {
        private String[] keywords;
        private Label label = null;

        SpamAnalyzer(String[] keywords) {
            this.keywords = Arrays.copyOf(keywords, keywords.length);
        }

        @Override
        public Label processText(String text) {
            label = Label.OK;

            for (int i = 0; i < keywords.length; i++) {
                if (text.contains(keywords[i])) {
                    label = Label.SPAM;
                }
            }

            return label;
        }


        @Override
        protected Label getLabel() {
            return label;
        }

        @Override
        protected String[] getKeywords() {
            return keywords;
        }
    }


    class TooLongTextAnalyzer implements TextAnalyzer {
        private int maxLength;

        public TooLongTextAnalyzer(int maxLength) {
            this.maxLength = maxLength;
        }

        @Override
        public Label processText(String text) {
            if (text.length() <= maxLength) {
                return Label.OK;
            } else return Label.TOO_LONG;
        }
    }

    public List<Label> checkLabels(TextAnalyzer[] analyzers, String text) {
        List<Label> labels = new ArrayList<>();
        Label label = Label.OK;

        for (int i = 0; i < analyzers.length; i++) {
            if (analyzers[i].processText(text) != Label.OK) {
                label = analyzers[i].processText(text);
                labels.add(label);
            }
        }

        if (labels.isEmpty()) labels.add(label);
        return labels;
    }


    public static void main(String[] args) {
        String text = new String("Nice text =( spam2");
        TextAnalyzer a = new Comment().new SpamAnalyzer(new String[]{"spam1", "spam2"});
        TextAnalyzer b = new Comment().new NegativeTextAnalyzer();
        TextAnalyzer c = new Comment().new TooLongTextAnalyzer(20);
        System.out.println(text + "\nThis text is " + new Comment().checkLabels(new TextAnalyzer[]{a, b, c}, text));


    }
}