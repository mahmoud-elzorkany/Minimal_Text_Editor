package com.company.Commands;

import com.company.EditorCommand;
import com.company.EditorEngine;

public class PasteCommand extends EditorCommand {
	private String clipboard;
	public PasteCommand(EditorEngine engine) {
		this.engine = engine;
	}

	public void execute()
	{
		clipboard = engine.getClipboard();
		engine.editorPaste();
	}

}
