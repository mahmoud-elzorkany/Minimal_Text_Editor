package com.company;

public class EditorEngineStub implements EditorEngine
{
	private StringBuffer stringBuffer;
	private String clipboard = "";

	private int selectionStart;
	private int selectionEnd;

	EditorEngineStub() {
		this.stringBuffer = new StringBuffer();
		selectionStart = 0;
		selectionEnd = 0;
	}

	@Override
	public String getBuffer()
	{
		return stringBuffer.toString();
	}

	@Override
	public String getSelection()
	{
		return stringBuffer.substring(selectionStart, selectionEnd);
	}

	@Override
	public String getClipboard()
	{
		return clipboard;
	}

	@Override
	public int getSelectionStart() {
		return selectionStart;
	}

	@Override
	public int getSelectionEnd() {
		return selectionEnd;
	}

	/**
	 * Replace current selection with the given String
	 */
	@Override
	public void editorInsert(String substring)
	{
	    stringBuffer.replace(selectionStart,selectionEnd,substring);
	    selectionEnd=selectionStart+substring.length();
	    selectionStart=selectionEnd;
		System.out.println("DEBUG: inserting text [" + substring + "]");
	}

	/**
	 * Change selection
	 * We take care of any edge cases so that we never crash
	 */
	@Override
	public void editorSelect(int start, int stop)
	{
		if(start > stop) {
			selectionStart = stop;
			selectionEnd = start;
		} else {
			selectionStart = start;
			selectionEnd = stop;
		}

		if(selectionEnd > stringBuffer.length())
			selectionEnd = stringBuffer.length();

		if(selectionStart > stringBuffer.length())
			selectionStart = stringBuffer.length();

		if(selectionStart < 0)
			selectionStart = 0;

		if(selectionEnd < 0)
			selectionEnd = 0;

		System.out.println("DEBUG: selecting interval [" + start + "," + stop + "]");
	}

	/**
	 * Update the clipboard with the current selection
	 */
	@Override
	public void editorCopy()
	{
		clipboard = stringBuffer.substring(selectionStart,selectionEnd);

		System.out.println("DEBUG: performing Copy" + clipboard) ;
	}

	/**
	 * Update the clipboard with the current selection and delete the current selection
	 */
	@Override
	public void editorCut()
	{
		editorCopy();
		editorInsert("");
		System.out.println("DEBUG: performing Cut") ;
	}

	/**
	 * Replace the current selection with the clipboard content
	 */
	@Override
	public void editorPaste()
	{
        editorInsert(clipboard);
		System.out.println("DEBUG: performing Paste") ;
	}

	public void debugSelection()
	{
		System.out.println("start:"+selectionStart+"End:"+selectionEnd);
	}
}
