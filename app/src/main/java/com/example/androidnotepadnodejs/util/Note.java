package com.example.androidnotepadnodejs.util;

public class Note {

    public String _ID;
    public String title;
    public String note;
    public String date;

    public Note() {
    }

    public Note(String _ID, String title, String note, String date) {
        this._ID = _ID;
        this.title = title;
        this.note = note;
        this.date = date;
    }

    public String get_ID() {
        return _ID;
    }

    public void set_ID(String _ID) {
        this._ID = _ID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
