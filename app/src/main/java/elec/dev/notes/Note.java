package elec.dev.notes;

public class Note {
    private int noteID;
    private String noteTitle;
    private String noteContent;
    private String noteDate;

    public int getNoteID() {return noteID; }
    public void setNoteID(int id) { this.noteID = id; }

    public String getNoteTitle() { return  noteTitle; }
    public void setNoteTitle(String title) { this.noteTitle = title; }

    public String getNoteContent() {
        return  noteContent;
    }
    public void setNoteContent(String content) { this.noteContent = content; }

    public String getNoteDate() { return  noteDate; }
    public void setNoteDate(String date) { this.noteDate = date; }
}

