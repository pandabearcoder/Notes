package elec.dev.notes;

import java.util.ArrayList;

public class InitialNotes {
    private String ipsum;

    public ArrayList initItems() {
        ipsum = "Text";
        ArrayList<Note> initArray = new ArrayList<>();

        Note noteItem1 = new Note();
        noteItem1.setNoteID(0);
        noteItem1.setNoteTitle("Assignment");
        noteItem1.setNoteContent("Research about the history of programming");
        noteItem1.setNoteDate("Dec 24");
        initArray.add(noteItem1);

        Note noteItem2 = new Note();
        noteItem2.setNoteID(1);
        noteItem2.setNoteTitle("TODO");
        noteItem2.setNoteContent("Make fifteen tuna sandwich");
        noteItem2.setNoteDate("Dec 30");
        initArray.add(noteItem2);

        Note noteItem3 = new Note();
        noteItem3.setNoteID(2);
        noteItem3.setNoteTitle("Android");
        noteItem3.setNoteContent("Android Development is fun");
        noteItem3.setNoteDate("Jan 01");
        initArray.add(noteItem3);

        Note noteItem4 = new Note();
        noteItem4.setNoteID(2);
        noteItem4.setNoteTitle("Web Development");
        noteItem4.setNoteContent("Finish");
        noteItem4.setNoteDate("Jan 01");
        initArray.add(noteItem4);

        Note noteItem5 = new Note();
        noteItem5.setNoteID(2);
        noteItem5.setNoteTitle("Github Account");
        noteItem5.setNoteContent("pandabearcoder");
        noteItem5.setNoteDate("Jan 02");
        initArray.add(noteItem5);

        return  initArray;
    }
}
