package com.company.Commands;

import com.company.EditorCommand;
import com.company.EditorEngine;

public class InsertCommand extends EditorCommand {
	private String buff;

	public InsertCommand(EditorEngine engine, String buff) {
		this.engine = engine;
		this.buff = buff;
	}

	public void execute()
	{
		this.engine.editorInsert(buff);
	}


}
