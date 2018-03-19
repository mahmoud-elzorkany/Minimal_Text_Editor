package com.company.Commands;

import com.company.Command;
import com.company.EditorCommand;
import com.company.EditorEngine;

public class SelectCommand extends EditorCommand {
	private int start;
	private int stop;

	public SelectCommand(EditorEngine engine, int start, int stop) {
		this.start = start;
		this.stop = stop;
		this.engine = engine;
	}

	public void execute()
	{
		this.engine.editorSelect(start, stop);
	}

}
