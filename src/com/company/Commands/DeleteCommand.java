package com.company.Commands;

import com.company.Command;
import com.company.EditorCommand;
import com.company.EditorEngine;

public class DeleteCommand  extends EditorCommand {
	public DeleteCommand(EditorEngine engine) {
		this.engine = engine;
	}
	public void execute() {
		this.engine.editorInsert("");
	}

}
