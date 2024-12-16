
/* 
 *
 * case I: class A or interface X 
 * case II: class A extends B or interface X extends Y
 * case III: class A implements X, Y, Z... or interface X extends P, Q, R...
 * case IV: class A extends B implements X, Y, Z...
 * case V: class A<T extends X> extends B<T> ...
 * */
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;

class Inheritance {
    private static String cleanedWord(String word) {
        if(word.contains("{")) {
            return "";            
        }
        else if(word.endsWith(",")) {
            return word.substring(0, word.length()-1);
        }     
        else {
            return word;
        }
    }

    private static String[] deletingTemplates(String sentence) {
        List<String> words = new CopyOnWriteArrayList<>(Arrays.asList(sentence.split("\\s+")));
        boolean nextWord = true;
        int index = 0, startPos = -1;
        int leftIndex = 0, rightIndex = 0;
        while (index < words.size()) {
            if (nextWord) {
                if (words.get(index).equals("class") || words.get(index).equals("interface")) { 
                    nextWord = false;
                    startPos = index;
                }
            }
            else {
                if (words.get(index).equals(">")) {
                    rightIndex = index;
                    for (index = rightIndex; index >= leftIndex; index--) {
                        words.remove(index);
                    }
                    index = startPos-1;    
                }
                else if (words.get(index).equals("<")) {
                    leftIndex = index;
                }
                else if (cleanedWord(words.get(index)).isEmpty()) {
                    nextWord = true;
                }    
            }
            index++;     
        }
        String[] array = new String[words.size()];
        return words.toArray(array);
    }

    public static void buildReverseIndex(Path path, Map<String, List<String>> reverseIndex) {
        String regexStrings = "\".*?\"";
        String regexComments = "//[\\s\\S]*?\n";
        String regexMultlineComments = "/\\*[\\s\\S]*?\\*/";
        try {
            String content = new String(Files.readAllBytes(path))
                .replaceAll(regexStrings, "\"\"")
                .replaceAll(regexComments, "")
                .replaceAll(regexMultlineComments, "")
                .replaceAll("<", " < ")
                .replaceAll(">", " > ")
                .replaceAll(",", ", ")
                .replaceAll("\\{", " {")
                .replaceAll("\\}", "} ");
            String[] words = deletingTemplates(content);
            boolean nextWord = true;
            boolean isClass = false;
            boolean hasParents = false;
            boolean hasAdditionPart = false;
            String child = "";
            for(String word: words) {
                if (nextWord) {
                    if (!(word.equals("class") || word.equals("interface"))) {
                        continue;
                    }
                    else {
                        nextWord = false;
                    }
                }
                if(cleanedWord(word).isEmpty()) {
                    nextWord = true;
                    isClass = false;
                    hasParents = false;
                    hasAdditionPart = false;
                    child = "";
                }
                else if(hasAdditionPart && !word.equals(",")) {
                    String parent = cleanedWord(word);
                    if (!parent.isEmpty()) {
                        List <String> children = reverseIndex.getOrDefault(parent, new CopyOnWriteArrayList<>());
                        children.add(child);
                        reverseIndex.put(parent, children);
                    }
                    else {
                        nextWord = true;
                        isClass = false;
                        hasParents = false;
                        hasAdditionPart = false;
                        child = "";
                    }
                }
                else if(hasParents && word.equals("implements")){
                    hasAdditionPart = true;
                }
                else if(hasParents && !word.equals(",")) {
                    String parent = cleanedWord(word);
                    if (!parent.isEmpty()) {
                        List <String> children = reverseIndex.getOrDefault(parent, new CopyOnWriteArrayList<>());
                        children.add(child);
                        reverseIndex.put(parent, children);
                    }
                    else {
                        nextWord = true;
                        isClass = false;
                        hasParents = false;
                        child = "";
                    }
                }
                else if(!child.isEmpty() && (word.equals("extends") || word.equals("implements"))) {
                    hasParents = true;
                }
                else if (!child.isEmpty()) {
                    nextWord = true;
                    isClass = false;
                    hasParents = false;
                    child = "";
                } 
                else if(isClass) {
                    child = cleanedWord(word);
                }

                else if(word.equals("class") || word.equals("interface")) {
                    isClass = true;
                }
            }	
        }
        catch (IOException e) {
            e.printStackTrace();    
        }
    }
}

class ThreadUsing {
    public static Map<String, List<String>> use(List<Path> filepaths) {
        Map<String, List<String>> reverseIndex = new ConcurrentHashMap<>();
        try {
            List<Thread> threads = new CopyOnWriteArrayList<Thread>(); 
            for(Path filepath: filepaths) {
                Runnable task = () -> {
                    Inheritance.buildReverseIndex(filepath, reverseIndex);
                };
                Thread thread = new Thread(task);
                threads.add(thread);    
            }
            for(Thread thread: threads) {
                thread.start();
            }
            for(Thread thread: threads) {
                thread.join();
            }
        } catch (InterruptedException e) {
            System.out.println("Interrupted");
        }
        return reverseIndex;
    }   
}

public class Main {

    public static void main(String [] args) throws InterruptedException {
        try {
            Path directoryname = Paths.get(args[0]);
            List<Path> filepaths = Files.walk(directoryname).filter(file ->{
                    String filename = file.toString();
                    return !filename.contains("test") && filename.endsWith(".java");
                    }).toList();
            //System.out.println(ThreadUsing.use(filepaths));
            Map<String, List<String>> reverseIndex = ThreadUsing.use(filepaths);
            CopyOnWriteArrayList<List<String>> childrenArray = new CopyOnWriteArrayList<>(reverseIndex.values());
            int count = 0;
            for(List<String> childrenList: childrenArray) {
                count = count+childrenList.size();
            }
            System.out.println(count);
            System.out.println("\n");
            System.out.println(reverseIndex.keySet().size());

        } catch(IOException e) {
            e.printStackTrace();	
        } 
    }
} 
