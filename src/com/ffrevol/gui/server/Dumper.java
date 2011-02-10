package com.ffrevol.gui.server;

import java.io.IOException;
import java.io.InputStream;

public interface Dumper
{
	void Save(InputStream in) throws IOException;
}
