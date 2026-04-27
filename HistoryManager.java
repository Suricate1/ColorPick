import javax.swing.DefaultListModel;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class HistoryManager {
    public static void saveHistory(Path file, DefaultListModel<String> model) throws IOException {
        try (BufferedWriter bw = Files.newBufferedWriter(file)) {
            for (int i = 0; i < model.size(); i++) {
                bw.write(model.get(i));
                bw.newLine();
            }
        }
    }

    public static List<String> loadHistory(Path file) throws IOException {
        if (!Files.exists(file)) return List.of();
        return Files.readAllLines(file).stream().filter(l -> !l.isBlank()).collect(Collectors.toList());
    }
}