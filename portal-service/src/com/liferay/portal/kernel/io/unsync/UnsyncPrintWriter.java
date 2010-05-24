/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.kernel.io.unsync;

import com.liferay.portal.kernel.util.StringPool;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

import java.util.Formatter;
import java.util.Locale;

/**
 * <a href="UnsyncPrintWriter.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * See http://issues.liferay.com/browse/LPS-6648.
 * </p>
 *
 * @author Shuyang Zhou
 */
public class UnsyncPrintWriter extends PrintWriter {

	public UnsyncPrintWriter(Writer out) {
		this(out, false);
	}

	public UnsyncPrintWriter(Writer out, boolean autoFlush) {
		super(out);

		_out = out;
		_autoFlush = autoFlush;
	}

	public UnsyncPrintWriter(OutputStream out) {
		this(out, false);
	}

	public UnsyncPrintWriter(OutputStream out, boolean autoFlush) {
		this(new OutputStreamWriter(out), autoFlush);
	}

	public UnsyncPrintWriter(String fileName) throws IOException {
		this(new FileWriter(fileName), false);
	}

	public UnsyncPrintWriter(String fileName, String csn)
		throws FileNotFoundException, UnsupportedEncodingException {
		this(new OutputStreamWriter(new FileOutputStream(fileName), csn),
			false);
	}

	public UnsyncPrintWriter(File file) throws IOException {
		this(new FileWriter(file), false);
	}

	public UnsyncPrintWriter(File file, String csn)
		throws FileNotFoundException, UnsupportedEncodingException {
		this(new OutputStreamWriter(new FileOutputStream(file), csn), false);
	}

	public PrintWriter append(char c) {
		write(c);
		return this;
	}

	public PrintWriter append(CharSequence csq) {
		if (csq == null) {
			write(StringPool.NULL);
		} else {
			write(csq.toString());
		}
		return this;
	}

	public PrintWriter append(CharSequence csq, int start, int end) {
		CharSequence cs = (csq == null ? StringPool.NULL : csq);
		write(cs.subSequence(start, end).toString());
		return this;
	}

	public boolean checkError() {
		if (_out != null) {
			flush();
		}
		return _hasError;
	}

	public void close() {
		try {
			if (_out == null) {
				return;
			}
			_out.close();
			_out = null;
		} catch (IOException x) {
			_hasError = true;
		}
	}

	public void flush() {
		if (_out == null) {
			_hasError = true;
		} else {
			try {
				_out.flush();
			} catch (IOException x) {
				_hasError = true;
			}
		}
	}

	public PrintWriter format(String format, Object... args) {
		return format(Locale.getDefault(), format, args);
	}

	public PrintWriter format(Locale l, String format, Object... args) {
		if (_out == null) {
			_hasError = true;
		} else {
			try {
				if ((_formatter == null) || (_formatter.locale() != l)) {
					_formatter = new Formatter(this, l);
				}
				_formatter.format(l, format, args);
				if (_autoFlush) {
					_out.flush();
				}
			} catch (InterruptedIOException x) {
				Thread.currentThread().interrupt();
			} catch (IOException x) {
				_hasError = true;
			}
		}
		return this;
	}

	public void print(boolean b) {
		write(b ? StringPool.TRUE : StringPool.FALSE);
	}

	public void print(char c) {
		write(c);
	}

	public void print(char[] s) {
		write(s);
	}

	public void print(double d) {
		write(String.valueOf(d));
	}

	public void print(float f) {
		write(String.valueOf(f));
	}

	public void print(int i) {
		write(String.valueOf(i));
	}

	public void print(long l) {
		write(String.valueOf(l));
	}

	public void print(Object obj) {
		write(String.valueOf(obj));
	}

	public void print(String s) {
		if (s == null) {
			s = StringPool.NULL;
		}
		write(s);
	}

	public PrintWriter printf(String format, Object... args) {
		return format(format, args);
	}

	public PrintWriter printf(Locale l, String format, Object... args) {
		return format(l, format, args);
	}

	public void println() {
		if (_out == null) {
			_hasError = true;
		} else {
			try {
				_out.write(_LINE_SEPARATOR);
				if (_autoFlush) {
					_out.flush();
				}
			} catch (InterruptedIOException x) {
				Thread.currentThread().interrupt();
			} catch (IOException x) {
				_hasError = true;
			}
		}
	}

	public void println(boolean x) {
		print(x);
		println();
	}

	public void println(char x) {
		print(x);
		println();
	}

	public void println(char[] x) {
		print(x);
		println();
	}

	public void println(double x) {
		print(x);
		println();
	}

	public void println(float x) {
		print(x);
		println();
	}

	public void println(int x) {
		print(x);
		println();
	}

	public void println(long x) {
		print(x);
		println();
	}

	public void println(Object x) {
		print(x);
		println();
	}

	public void println(String x) {
		print(x);
		println();
	}

	public void write(int c) {
		if (_out == null) {
			_hasError = true;
		} else {
			try {
				_out.write(c);
			} catch (InterruptedIOException x) {
				Thread.currentThread().interrupt();
			} catch (IOException x) {
				_hasError = true;
			}
		}
	}

	public void write(char[] buf) {
		write(buf, 0, buf.length);
	}

	public void write(char[] buf, int off, int len) {
		if (_out == null) {
			_hasError = true;
		} else {
			try {
				_out.write(buf, off, len);
			} catch (InterruptedIOException x) {
				Thread.currentThread().interrupt();
			} catch (IOException x) {
				_hasError = true;
			}
		}
	}

	public void write(String s) {
		if (_out == null) {
			_hasError = true;
		} else {
			try {
				_out.write(s);
			} catch (InterruptedIOException x) {
				Thread.currentThread().interrupt();
			} catch (IOException x) {
				_hasError = true;
			}
		}
	}

	public void write(String s, int off, int len) {
		if (_out == null) {
			_hasError = true;
		} else {
			try {
				_out.write(s, off, len);
			} catch (InterruptedIOException x) {
				Thread.currentThread().interrupt();
			} catch (IOException x) {
				_hasError = true;
			}
		}
	}

	private static String _LINE_SEPARATOR = System.getProperty(
			"line.separator");

	private boolean _autoFlush = false;
	private Formatter _formatter;
	private boolean _hasError = false;
	private Writer _out;

}