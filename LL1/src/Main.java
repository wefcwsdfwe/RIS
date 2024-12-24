import analyzer.Analyzer;
import analyzer.IAnalyzer;

public class Main {
    public static void main(String[] args) {
        String s = """
                func sum(a,b):
                    return a + b
                """;
        IAnalyzer analyzer = new Analyzer(s);
        analyzer.analyze();
    }
}
