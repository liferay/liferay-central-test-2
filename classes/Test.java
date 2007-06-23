/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

import com.liferay.portlet.mail.util.*;
import com.liferay.util.*;
import com.liferay.util.xml.*;

import de.hunsicker.jalopy.storage.*;

import java.awt.image.*;

import java.io.*;

import java.lang.reflect.*;

import java.net.*;

import java.sql.*;

import java.util.*;

import javax.imageio.*;

import net.jmge.gif.Gif89Encoder;

import org.apache.oro.text.perl.Perl5Util;

import org.apache.tools.ant.DirectoryScanner;

import org.jdom.*;
import org.jdom.input.*;

/**
 * <a href="Test.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class Test {

	public static void main(String[] args) {
		try {
			String[] array1 = new String[] {"a", "b", "c"};

			ArrayUtil.append(array1, "d");

			String[] array2 = new String[] {"a", "b", "c"};

			ArrayUtil.append(array1, array2);

			Object[][] array3 = new Object[][] {
				new Object[] {"a", "b", "c"},
				new Object[] {"a", "b", "c"}
			};

			Object[][] array4 = new Object[][] {
				new Object[] {"a", "b", "c"},
				new Object[] {"a", "b", "c"}
			};

			ArrayUtil.append(array3, new Object[] {"a", "b", "c"});

			ArrayUtil.append(array3, array4);

			Long[] array5 = new Long[] {new Long(1), new Long(2), new Long(3)};

			ArrayUtil.append(array5, new Long(4));

			Long[] array6 = new Long[] {new Long(1), new Long(2), new Long(3)};

			ArrayUtil.append(array5, array6);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}