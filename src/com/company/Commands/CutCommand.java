package com.company.Commands;

import com.company.Command;
import com.company.EditorCommand;
import com.company.EditorEngine;

public class CutCommand extends EditorCommand {
	public CutCommand(EditorEngine engine) {
		this.engine = engine;
	}

	public void execute()
	{

		this.engine.editorCut();
	}


}
