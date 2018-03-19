package com.company;

public interface EditorEngine
{
	public String getBuffer();
	public String getSelection();
	public String getClipboard();

	public int getSelectionStart();
	public int getSelectionEnd();

	public void editorInsert(String substring);
	public void editorSelect(int start, int stop);
	public void editorCopy();
	public void editorCut();
	public void editorPaste();
	public void debugSelection();

}
