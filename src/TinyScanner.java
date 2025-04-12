import java.io.*;
import java.util.*;


public class TinyScanner {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(args[0]));
        List<Token> tokens = new ArrayList<>();

        String line;
        while ((line = br.readLine()) != null) {
            line = line.trim();

            if (line.startsWith("#")) {
                tokens.add(new Token(line, "comment"));
                continue;
            }

            char[] chars = line.toCharArray();
            StringBuilder tokenBuilder = new StringBuilder();

            for (int i = 0; i < chars.length; i++) {
                char ch = chars[i];

                // flush token when delimiter comes
                // white space is delimiter
                if (Character.isWhitespace(ch)) {
                    flushToken(tokenBuilder, tokens);
                }
                // symbol
                else if (isSingleSymbol(ch)) {
                    flushToken(tokenBuilder, tokens);

                    // two char operator ( +=, <= )
                    if (i + 1 < chars.length) {
                        String twoCharOperator = "" + ch + chars[i + 1];
                        if (getSymbolType(twoCharOperator) != null) {
                            tokens.add(new Token(twoCharOperator, getSymbolType(twoCharOperator)));
                            i++;
                        }
                        else
                            tokens.add(new Token(String.valueOf(ch), getSymbolType(String.valueOf(ch))));
                    }
                    // single operator
                    else
                        tokens.add(new Token(String.valueOf(ch), getSymbolType(String.valueOf(ch))));
                }
                // string literal
                else if (ch == '"') {
                   flushToken(tokenBuilder, tokens);
                   i++;
                   while (i < chars.length && chars[i] != '"') {
                       tokenBuilder.append(chars[i++]);
                   }

                    if (i < chars.length && chars[i] == '"') {
                        tokens.add(new Token("\"" + tokenBuilder + "\"", "string literal"));
                    } else {
                        tokens.add(new Token("\"" + tokenBuilder, "unterminated string literal"));
                    }

                   tokenBuilder.setLength(0);
                }
                // identifier
                else {
                    tokenBuilder.append(ch);
                }
            }
        }

        br.close();

        for (Token t : tokens) {
            System.out.printf("%-25s %-30s%n", t.value, t.type);
        }
    }

    static class Token {
        String value;
        String type;

        public Token(String value, String type) {
            this.value = value;
            this.type = type;
        }

        @Override
        public String toString() {
            return String.format("%-15s %s", value, type);
        }
    }

    static boolean isAlphabetChar(char ch) {
        return (ch >= 'A' && ch <= 'Z') ||
                (ch >= 'a' && ch <= 'z') ||
                (ch >= '0' && ch <= '9') ||
                ch == '_' || ch == '.' || ch == '$' || ch == '@';
    }

    static class ReservedWords {
        private ReservedWords() {}

        static Set<String> keywords = new HashSet<>();

        static {
            keywords.add("if");
            keywords.add("elif");
            keywords.add("for");
            keywords.add("while");
            keywords.add("print");
            keywords.add("integer");
        }

        static boolean isKeyword(String word) {
            return keywords.contains(word);
        }
    }
    static boolean isSingleSymbol(char ch) {
        return "=+-*/<>!();{}".indexOf(ch) != -1;
    }

    static String getSymbolType(String sym) {
        Map<String, String> staticTokens = Map.ofEntries(
                Map.entry("+", "plus operator"),
                Map.entry("-", "subtract operator"),
                Map.entry("*", "multiply operator"),
                Map.entry("/", "divide operator"),
                Map.entry("!", "negation operator"),

                Map.entry("=", "assignment operator"),
                Map.entry("+=", "Plus Assignment Operator"),
                Map.entry("-=", "Subtract Assignment Operator"),
                Map.entry("*=", "Multiply Assignment Operator"),
                Map.entry("/=", "Divide Assignment Operator"),

                Map.entry("<", "less than operator"),
                Map.entry(">", "greater than operator"),
                Map.entry("<=", "less than or equal to operator"),
                Map.entry(">=", "greater than or equal to operator"),
                Map.entry("==", "Equality Operator "),
                Map.entry("!=", "Not Equality Operator"),

                Map.entry("(", "left parenthesis"),
                Map.entry(")", "right parenthesis"),
                Map.entry("{", "left curly brace"),
                Map.entry("}", "right curly brace"),

                Map.entry(";", "statement terminator")
        );
        return staticTokens.get(sym);
    }

    static boolean isNumber(String str) {
        for (char ch : str.toCharArray()) {
            if (!Character.isDigit(ch)) return false;
        }
        return true;
    }

    static boolean isValidIdentifier(String tokenStr) {
        if (tokenStr.isEmpty()) return false;

        char first = tokenStr.charAt(0);
        if (!Character.isLetter(first) && first != '_') return false;

        for (char ch : tokenStr.toCharArray()) {
            if (!isAlphabetChar(ch)) return false;
        }

        return true;
    }

    static void flushToken(StringBuilder tokenBuilder, List<Token> tokens) {
        if (tokenBuilder.isEmpty()) return;

        String tokenStr = tokenBuilder.toString();

        if (ReservedWords.isKeyword(tokenStr)) {
            tokens.add(new Token(tokenStr, "keyword"));
        }
        else if (isNumber(tokenStr)) {
            tokens.add(new Token(tokenStr, "number literal"));
        }
        else if (isValidIdentifier(tokenStr)) {
            tokens.add(new Token(tokenStr, "identifier"));
        } else {
            tokens.add(new Token(tokenStr, "illegal identifier"));
        }

        tokenBuilder.setLength(0);
    }
}