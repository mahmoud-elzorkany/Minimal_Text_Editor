package com.company;

import java.util.ArrayList;

/**
 * Contains the commands (and method to add one) and provides a replay method
 */
public class Recording {

	/**
	 * The list of commands in the recording
	 */
	private ArrayList<Command> commands;

	public Recording() {
		commands = new ArrayList<>();
	}

	/**
	 * Add a command to the recording
	 *
	 * @param command Command to add
	 */
	public void addCommand(Command command) {
		commands.add(command);
	}


	public int getCommandsSize() {
		return commands.size();
	}

	public boolean isCommandsEmpty() {
		return commands.isEmpty();
	}

	/**
	 * Replay the recording.
	 * Basically just clone every command and execute it, in the order they were added.
	 * @return The list of the new executed commands (will be used to add to UndoRedo)
	 */
	public ArrayList<Command> replay() {
		ArrayList<Command> commandsNew = new ArrayList<>();
		for (Command command : commands) {
			Command clone = null;

			clone = command;
			clone.execute();
			commandsNew.add(clone);
		}

		System.out.println("Playback finished");
		return commandsNew;
	}
}
