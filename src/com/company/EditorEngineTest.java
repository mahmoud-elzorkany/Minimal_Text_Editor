package com.company;

import static org.junit.Assert.*;

import com.company.Commands.*;
import org.junit.Before;
import org.junit.Test;

public class EditorEngineTest {
	private EditorEngine editorEngine;
	private Recorder recorder;


	@Before
	public void setUp() throws Exception {
		editorEngine = new EditorEngineStub();
		recorder = new Recorder();
	}

	@Test
	public void testEditorInsert() {
		invariant(editorEngine);
		editorEngine.editorInsert("hello");
		assertEquals("entered string not inserted", editorEngine.getBuffer(), "hello");

		editorEngine.editorSelect(0, 1);
		editorEngine.editorInsert("world");
		assertEquals("selection didn't replace to insert", editorEngine.getBuffer(), "worldello");
		assertEquals("Selection should be at the end of the insert", 5, editorEngine.getSelectionStart());

		editorEngine.editorSelect(0, 4);
		editorEngine.editorInsert("you");
		assertEquals("short selection fail to replace by the inserted string", editorEngine.getBuffer(), "youdello");


	}

	@Test
	public void testEditorSelect() {
		editorEngine.editorInsert("hey there cuttie!");
		editorEngine.editorSelect(-5, 2);
		assertEquals("Selecting negative should not break the editor", editorEngine.getSelection(), "he");

		editorEngine.editorSelect(0, 999999);
		assertEquals("Over selecting should not break the editor", editorEngine.getSelection(), "hey there cuttie!");

		editorEngine.editorSelect(1, 1);
		assertEquals("Empty selection should work", editorEngine.getSelection(), "");

		editorEngine.editorSelect(2, 1);
		assertEquals("reverse selection should work", editorEngine.getSelection(), "e");

		editorEngine.editorSelect(2, -3);
		assertEquals("Selecting negative end need change it to 0 position ", editorEngine.getSelection(), "he");

		editorEngine.editorSelect(-2, -3);
		assertEquals("both negative values fail to change it to no selection ", editorEngine.getSelection(), "");
	}

	@Test
	public void testEditorCopy() {
		assertTrue("Buffer should be empty", editorEngine.getBuffer().isEmpty());
		String buffer = "testing string buffer";
		editorEngine.editorInsert(buffer);

		editorEngine.editorSelect(0, 4);
		editorEngine.editorCopy();
		String selection = editorEngine.getSelection();
		assertEquals("The clipboard should exactly contain exactly the substring indicated by the selection", selection, editorEngine.getClipboard());

		editorEngine.editorSelect(buffer.length(), buffer.length());
		editorEngine.editorCopy();
		assertEquals("The clipboard should be empty when coptying an empty selection", editorEngine.getClipboard(), "");
	}

	@Test
	public void testEditorCut() {
		assertTrue("Buffer should be empty", editorEngine.getBuffer().isEmpty());

		String buffer = "testing string buffer";
		editorEngine.editorInsert(buffer);
		int oldSize = editorEngine.getBuffer().length();

		editorEngine.editorSelect(0, 4);
		String selection = editorEngine.getSelection();

		editorEngine.editorCut();
		int newSize = editorEngine.getBuffer().length();

		assertEquals("The clipboard should exactly contain exactly the substring indicated by the selection", selection, editorEngine.getClipboard());

		assertFalse("THE buffer shouldn't have the selected text after the cut opertation", editorEngine.getBuffer().contains(selection));

		assertTrue("The buffer length should decrease by the size of the selection after cut", newSize == oldSize - selection.length());


		editorEngine.editorSelect(buffer.length(), buffer.length());
		editorEngine.editorCut();
		assertEquals("The clipboard should be empty when cutting an empty selection", editorEngine.getClipboard(), "");
	}

	@Test
	public void testEditorPaste() {
		assertTrue
				("Buffer should be empty", editorEngine.getBuffer().isEmpty());
		String myString = "Hello everybody123456";
		int mySize = myString.length();
		editorEngine.editorInsert(myString);
		assertEquals
				("Buffer should be equal to inserted text",
						myString, editorEngine.getBuffer());
		editorEngine.editorSelect(15, 21);
		editorEngine.editorCut();
		mySize = mySize - 6;
		assertEquals
				("Buffer should lose 6 characters",
						mySize, editorEngine.getBuffer().length());
		editorEngine.editorSelect(1, 5);
		editorEngine.editorPaste();
		mySize = mySize - 4 + 6;
		assertEquals
				("Buffer size should increase",
						mySize, editorEngine.getBuffer().length());

		String oldBuffer = editorEngine.getBuffer();
		editorEngine.editorSelect(editorEngine.getBuffer().length(), editorEngine.getBuffer().length());
		editorEngine.editorCopy();
		String oldSelection = editorEngine.getSelection();
		editorEngine.editorPaste();
		assertTrue("pasting an empty clipboard shouldnt do any modification to the current buffer", oldBuffer.equals(editorEngine.getBuffer()));
		assertTrue("the selection should be same before and after paste", oldSelection.equals(editorEngine.getSelection()));
	}


	//Methods for generating text editor commands
	private EditorCommand doSelect(int selectionStart, int selectionEnd) {
		EditorCommand command = new SelectCommand(this.editorEngine, selectionStart, selectionEnd);
		recorder.addCommandToActiveRecording(command);
		command.execute();
		return command;
	}

	private EditorCommand doCut() {
		EditorCommand command = new CutCommand(this.editorEngine);
		recorder.addCommandToActiveRecording(command);
		command.execute();
		return command;
	}

	private EditorCommand doInsert(String buff) {
		EditorCommand command = new InsertCommand(this.editorEngine, buff);
		recorder.addCommandToActiveRecording(command);
		command.execute();
		return command;
	}

	private EditorCommand doCopy() {
		EditorCommand command = new CopyCommand(this.editorEngine);
		recorder.addCommandToActiveRecording(command);
		command.execute();
		return command;
	}

	private EditorCommand doPaste() {
		EditorCommand command = new PasteCommand(this.editorEngine);
		recorder.addCommandToActiveRecording(command);
		command.execute();
		return command;
	}

	private EditorCommand doDelete() {
		EditorCommand command = new DeleteCommand(this.editorEngine);
		recorder.addCommandToActiveRecording(command);
		command.execute();
		return command;
	}
	////////////////////////////////////////////////

	EditorCommand command;

	@Test
	public void replay() {


		recorder.startRecording();

		doSelect(0, editorEngine.getBuffer().length());
		//System.out.println(editorEngine.getBuffer());

		doCut();
		// System.out.println(editorEngine.getBuffer());

		doInsert("Test");
		//System.out.println(editorEngine.getBuffer());

		doSelect(0, 2);
		// System.out.println(editorEngine.getBuffer());

		doCopy();
		//System.out.println(editorEngine.getBuffer());

		doSelect(2, 2);
		//System.out.println(editorEngine.getBuffer());

		doPaste();
		//System.out.println(editorEngine.getBuffer());
		recorder.saveRecording();

		int commandNumber = 7;

		Recording rec = recorder.getRecording();
		rec.replay();
		String testBuffer = "TeTestTeTest";

		assertFalse("You can't play a recorder without starting one", rec.isCommandsEmpty());
		assertEquals("number of commands recorded should equal to the command list length", rec.getCommandsSize(), commandNumber);
		assertEquals("commands are stored and played back properly", editorEngine.getBuffer(), testBuffer);
		System.out.println(editorEngine.getBuffer());

		//testing playing same recording twice
		editorEngine = new EditorEngineStub();
		recorder.startRecording();

		doInsert("1");
		doInsert("2");
		doInsert("3");
		doSelect(editorEngine.getBuffer().length() - 1, editorEngine.getBuffer().length());
		doCopy();
		doSelect(editorEngine.getBuffer().length(), editorEngine.getBuffer().length());
		doPaste();
		doInsert("4");

		//System.out.println("MIAU:" + editorEngine.getBuffer());

		recorder.saveRecording("test");
		rec = recorder.getRecording("test");
		doSelect(editorEngine.getBuffer().length(), editorEngine.getBuffer().length() + 1);
		rec.replay();
		rec.replay();
		//System.out.println(editorEngine.getBuffer());
		assertEquals("Replay should replay everything", "123343412334123", editorEngine.getBuffer());

	}

	@Test
	public void saveRecording() {
		EditorCommand command;
		recorder.startRecording();


		doInsert("Test");

		doSelect(0, 2);

		doCopy();

		doPaste();

		recorder.saveRecording();
		int recorder_size = recorder.getRecordingsNum();

		doSelect(0, 2);
		doCopy();
		doPaste();

		recorder.saveRecording();
		int recorder_size2 = recorder.getRecordingsNum();

		assertTrue("if no recording has started the commands list should be empty at saving", recorder.getActiveRecording() == null);
		assertEquals("No empty recording has been added (saving recording without starting a recording)", recorder_size, recorder_size2);
	}


	private void invariant(EditorEngine edit) {
		assertTrue("Selection not bigger than buffer",
				edit.getSelection().length()
						<= edit.getBuffer().length());
	}

}
