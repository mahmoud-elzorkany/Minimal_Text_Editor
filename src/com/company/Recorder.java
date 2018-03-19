package com.company;
import java.util.HashMap;

/**
 * Keeps all recordings and provides methods to create and get them.
 */
public class Recorder {
	/**
	 * The hashMap of all recordings
	 * HashMap because we can store multiple recordings and name each one
	 */
	private HashMap<String, Recording> recordings;

	/**
	 * The recording started by the R command
	 */
	private Recording activeRecording;

	public Recorder() {
		this.recordings = new HashMap<>();
		activeRecording = null;
	}

	/**
	 * Create a new recording and set it as the active recording
	 */
	public void startRecording() {
		activeRecording = new Recording();
		System.out.println("Started recording");

	}

	public int getRecordingsNum()
	{
		return recordings.size();
	}


	/**
	 * Save the active recording with the given name
	 * If a recording with the same name already exists, it will be replaced
	 * @param name The desired name of the recording.
	 */
	public void saveRecording(String name) {
		if (activeRecording != null) {
			recordings.put(name, activeRecording);
			activeRecording = null;
			System.out.println("Recording " + name + " created");
		}
	}

	/**
	 * Save recording with the default name.
	 * This is done in order to support saving a recording without giving any specific name.
	 */
	public void saveRecording() {
		this.saveRecording("default");
	}

	/**
	 * Get the active recording
	 * @return Recording The active recording
	 */
	public Recording getActiveRecording() {
		return activeRecording;
	}

	/**
	 * Get a recording based on its name
	 * @param name Name of recording to get
	 * @return The desired recording OR null if not found
	 */
	public Recording getRecording(String name) {
		if(recordings.containsKey(name))
			return recordings.get(name);
		return null;
	}

	/**
	 * Get the default named recording (the recording saved without giving a name)
	 * @return The desired recording OR null if not found
	 */
	public Recording getRecording() {
		return this.getRecording("default");
	}

	/**
	 * Add command to active recording, if there is any
	 */
	public void addCommandToActiveRecording(Command command){
		if(command != null)
			if(this.getActiveRecording() != null)
				this.getActiveRecording().addCommand(command);
	}
}