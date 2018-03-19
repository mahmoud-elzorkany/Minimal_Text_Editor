package com.company.Commands;

import com.company.Command;
import com.company.EditorCommand;
import com.company.EditorEngine;

public class CopyCommand extends EditorCommand {
	public CopyCommand(EditorEngine engine) {
		this.engine = engine;
	}

	public void execute()
	{
		this.engine.editorCopy();
	}


}