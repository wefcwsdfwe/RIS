package lexer;

import java.util.*;
import java.util.regex.*;
import java.util.stream.Collectors;

public class Lexer implements ILexer {
    private List<Map.Entry<String, String>> tokenPatterns;
    private List<String> tokenNames = new ArrayList<>();
    private final String symbols;

    public Lexer(String symbols, List<Map.Entry<String, String>> tokensDict) {
        this.tokenPatterns = tokensDict;
        this.symbols = symbols;
        tokenNames = tokenize(symbols);
    }

    public List<String> tokenize(String input) {
        List<Map.Entry<String, String>> TOKEN_PATTERNS = tokenPatterns;
        List<String> tokens = new ArrayList<>();
        final String[] remainingInput = {input};

        while (!remainingInput[0].isBlank()) {
            Optional<Map.Entry<String, Integer>> matchedToken = TOKEN_PATTERNS.stream()
                    .map(tokenPattern -> {
                        Pattern pattern = Pattern.compile("^" + tokenPattern.getValue());
                        Matcher matcher = pattern.matcher(remainingInput[0]);
                        return matcher.find() ? Map.entry(tokenPattern.getKey(), matcher.end()) : null;
                    })
                    .filter(Objects::nonNull)
                    .findFirst();

            matchedToken.ifPresent(token -> {
                tokens.add(token.getKey());
                remainingInput[0] = remainingInput[0].substring(token.getValue()).trim();
            });
        }

        tokens.add("END");
        return tokens;
    }

    public List<String> getTokenNames() {
        return tokenNames;
    }
}
