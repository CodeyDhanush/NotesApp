import java.io.*;
import java.util.Scanner;

class Note {
    private String title;
    private String content;

    public Note(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() { return title; }
    public String getContent() { return content; }
}

class FileHandler {
    private static final String FILE_NAME = "notes.txt";

    public static void saveNoteToFile(Note note) throws IOException {
        try (FileWriter fw = new FileWriter(FILE_NAME, true)) { // append mode
            fw.write(note.getTitle() + ":" + note.getContent() + "\n");
        }
    }

    public static void readNotesFromFile() throws IOException {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            System.out.println("No notes found.");
            return;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            System.out.println("\nYour Notes:");
            System.out.println("-----------");
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(":", 2);
                if (parts.length == 2) {
                    System.out.println("Title: " + parts[0]);
                    System.out.println("Content: " + parts[1]);
                    System.out.println("-----------");
                }
            }
        }
    }
}

class NotesManager {
    private Scanner scanner = new Scanner(System.in);

    public void addNote() {
        System.out.print("Enter note title: ");
        String title = scanner.nextLine();
        System.out.print("Enter note content: ");
        String content = scanner.nextLine();
        try {
            FileHandler.saveNoteToFile(new Note(title, content));
            System.out.println("Note saved!");
        } catch (IOException e) {
            System.out.println("Error saving note: " + e.getMessage());
        }
    }

    public void viewNotes() {
        try {
            FileHandler.readNotesFromFile();
        } catch (IOException e) {
            System.out.println("Error reading notes: " + e.getMessage());
        }
    }
}

public class T04NotesManagerApp {
    public static void main(String[] args) {
        NotesManager manager = new NotesManager();
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\n--- Notes App ---");
            System.out.println("1. Add Note");
            System.out.println("2. View Notes");
            System.out.println("3. Exit");
            System.out.print("Choose option: ");
            int choice;
            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid choice! Enter a number.");
                continue;
            }
            switch (choice) {
                case 1: manager.addNote(); break;
                case 2: manager.viewNotes(); break;
                case 3: System.out.println("Exiting..."); System.exit(0);
                default: System.out.println("Invalid choice");
            }
        }
    }
}
