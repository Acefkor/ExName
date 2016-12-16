package exname;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author cedal
 */
public class ExName {

    private static JFileChooser jfc;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        List<String> fileNames = new ArrayList<>();
        try {
            String uri = setDirectory();
            Files.walk(Paths.get(uri)).forEach((Path filePath) -> {
                if (Files.isRegularFile(filePath)) {
                    fileNames.add(filePath.getFileName().toString());
                } else if (Files.isDirectory(filePath)) {
                    fileNames.add("Directory: " + filePath.getFileName().toString());
                }
            });
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        createFile(fileNames);
        fileNames.stream().forEach((s) -> {
            System.out.println(s);
        });
    }

    @SuppressWarnings({"null", "UnusedAssignment"})
    private static void createFile(final List<String> fileNames) {
        try {
            File file = new File(System.getProperty("user.name"), "lista.txt");
            jfc = new JFileChooser();
            jfc.setCurrentDirectory(new File(System.getProperty("user.name")));
            jfc.setSelectedFile(file);
            jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
            int retrival = jfc.showSaveDialog(null);
            if (retrival == JFileChooser.APPROVE_OPTION) {
                try (PrintWriter writer = new PrintWriter(new File(jfc.getSelectedFile().getAbsolutePath()), "UTF-8")) {
                    fileNames.stream().forEach((s) -> {
                        writer.println(s);
                    });
                }
            }
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            JOptionPane.showMessageDialog(null, "File not created\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static String setDirectory() {
        try {
            jfc = new JFileChooser();
            jfc.setCurrentDirectory(new File(System.getProperty("user.name")));
            jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int retrival = jfc.showOpenDialog(null);
            if (retrival == JFileChooser.APPROVE_OPTION) {
                return jfc.getSelectedFile().getAbsolutePath();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

        }
        return "";
    }
}
