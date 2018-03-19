package com.company;

import com.company.Commands.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MiniEditorTextInterface {
	static BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
	static EditorEngine editorEngine = new EditorEngineStub();
	static Recorder recorder = new Recorder();

	public static void main(String[] args) {
		String inputLine;
		char commandLetter;

		System.out.println("Welcome to MiniEditor V2.0");
		System.out.println("-----------------------------------------------------------");

		System.out.println("Enter command (I/S/C/X/V/D/R/E/P/Q) > ");
		try {
			inputLine = keyboard.readLine();
		} catch (IOException e) {
			System.out.println("Unable to read standard input");
			inputLine = "W";
		}

		if (inputLine.isEmpty()) {
			commandLetter = '0';
		} else {
			commandLetter = Character.toUpperCase(inputLine.charAt(0));
		}
		while (commandLetter != 'Q') /* Quit */ {
			Command command = null;
			switch (commandLetter) {
				case '0':
					break;
				case 'I': /* Insert */
					command = new InsertCommand(editorEngine, inputLine.substring(2));
					recorder.addCommandToActiveRecording(command);
					command.execute();
					break;
				case 'S': /* Select */
					String numberString = "";
					try {
						String[] arguments = inputLine.substring(2).split("\\s+");
						numberString = arguments[0];
						int start = Integer.parseInt(numberString);
						numberString = arguments[1];
						int stop = Integer.parseInt(numberString);

						command = new SelectCommand(editorEngine, start, stop);
						recorder.addCommandToActiveRecording(command);
						command.execute();
					} catch (Exception e) {
						System.out.println("Invalid number: " + numberString);
					}
					break;
				case 'C': /* Copy */
					command = new CopyCommand(editorEngine);
					recorder.addCommandToActiveRecording(command);
					command.execute();
					break;
				case 'X': /* cut */
					command = new CutCommand(editorEngine);
					recorder.addCommandToActiveRecording(command);
					command.execute();
					break;
				case 'V': /* paste */
					command = new PasteCommand(editorEngine);
					recorder.addCommandToActiveRecording(command);
					command.execute();
					break;
				case 'D': /* Delete, i.e. insert empty string */
					command = new DeleteCommand(editorEngine);
					recorder.addCommandToActiveRecording(command);
					command.execute();

					break;
				case 'R': /* start Recording */
					recorder.startRecording();
					break;
				case 'E': /* End recording */
					if (inputLine.length() > 2) {
						String name = inputLine.substring(2);
						recorder.saveRecording(name);
					} else {
						recorder.saveRecording();
					}
					break;
				case 'P': /* Play recording */
					Recording recording;
					if (inputLine.length() > 2) {
						String name = inputLine.substring(2);
						recording = recorder.getRecording(name);
					} else {
						recording = recorder.getRecording();
					}

					if (recording != null) {
						//replay the recording and add the newly issued commands to the UndoRedo queue
						recording.replay();
					}

					// Insert your code here (V2)
					break;
				default:
					System.out.println("Unrecognized command, please try again:");
					break;
			}


			System.out.println("-----------------------------------------------------");
			System.out.println("[" + editorEngine.getBuffer() + "]");
			System.out.println("-----------------------------------------------------");
			System.out.println(editorEngine.getSelectionStart() + ":" + editorEngine.getSelectionEnd() + " [" + editorEngine.getSelection() + "]");
			System.out.println("-----------------------------------------------------");
			System.out.println("[" + editorEngine.getClipboard() + "]");
			System.out.println("-----------------------------------------------------");

			System.out.println("Enter command (I/S/C/X/V/D/R/E/P/Q) > ");
			try {
				inputLine = keyboard.readLine();
			} catch (IOException e) {
				System.out.println("Unable to read standard input");
				inputLine = "W";
			}
			if (inputLine.isEmpty()) {
				commandLetter = '0';
			} else {
				commandLetter = Character.toUpperCase(inputLine.charAt(0));
			}
		}
		System.out.println("Goodbye");
	}
}
