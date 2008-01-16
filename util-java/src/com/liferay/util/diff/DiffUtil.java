/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

package com.liferay.util.diff;

import com.liferay.portal.kernel.util.StringMaker;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.util.FileUtil;

import java.io.Reader;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.incava.util.diff.Diff;
import org.incava.util.diff.Difference;

/**
 * <a href="DiffUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * This class can compare two different versions of a text. Source refers to
 * the earliest version of the text and target refers to a modified version of
 * source. Changes are considered either as a removal from the source or as an
 * addition to the target. This class detects changes to an entire line and also
 * detects changes within lines, such as, removal or addition of characters.
 * Take a look at <code>DiffTest</code> to see the expected inputs and outputs.
 * </p>
 *
 * @author Bruno Farache
 *
 */
public class DiffUtil {

	public static final String OPEN_ADD = "<ADD>";

	public static final String CLOSE_ADD = "</ADD>";

	public static final String OPEN_DEL = "<DEL>";

	public static final String CLOSE_DEL = "</DEL>";

	/**
	 * This is a diff method with default values.
	 *
	 * @param		source the <code>Reader</code> of the source text
	 * @param		target the <code>Reader</code> of the target text
	 * @return		a list of <code>DiffResults</code>
	 */
	public static List diff(Reader source, Reader target) {
		return diff(source, target, OPEN_ADD, CLOSE_ADD, OPEN_DEL, CLOSE_DEL);
	}

	/**
	 * The main entrance of this class. This method will compare the two texts,
	 * highlight the changes by enclosing them with markers and return a list
	 * of <code>DiffResults</code>.
	 *
	 * @param		source the <code>Reader</code> of the source text, this can
	 *				be for example, an instance of FileReader or StringReader
	 * @param		target the <code>Reader</code> of the target text
	 * @param		addedMarkerStart the initial marker for highlighting
	 * 				additions
	 * @param		addedMarkerEnd the end marker for highlighting additions
	 * @param		deletedMarkerStart the initial marker for highlighting
	 * 				removals
	 * @param		deletedMarkerEnd the end marker for highlighting removals
	 * @return		A List of <code>DiffResults</code>
	 */
	public static List diff(
		Reader source, Reader target, String addedMarkerStart,
		String addedMarkerEnd, String deletedMarkerStart,
		String deletedMarkerEnd) {

		List results = new ArrayList();

		// Convert the texts to Lists where each element are lines of the texts.

		List sourceStringList = FileUtil.toList(source);
		List targetStringList = FileUtil.toList(target);

		// Make a a Diff of these lines and iterate over their Differences.

		Diff diff = new Diff(sourceStringList, targetStringList);

		List differences = diff.diff();

		Iterator itr = differences.iterator();

		while (itr.hasNext()) {
			Difference difference = (Difference)itr.next();

			if (difference.getAddedEnd() == Difference.NONE) {

				// Lines were deleted from source only.

				_highlight(
					sourceStringList, difference.getDeletedStart(),
					difference.getDeletedEnd(), deletedMarkerStart,
					deletedMarkerEnd);

				DiffResult diffResult = new DiffResult(
					DiffResult.SOURCE, difference.getDeletedStart(),
					sourceStringList.subList(
						difference.getDeletedStart(),
						difference.getDeletedEnd() + 1));

				results.add(diffResult);
			}
			else if (difference.getDeletedEnd() == Difference.NONE) {

				// Lines were added to target only.

				_highlight(
					targetStringList, difference.getAddedStart(),
					difference.getAddedEnd(), addedMarkerStart, addedMarkerEnd);

				DiffResult diffResult = new DiffResult(
					DiffResult.TARGET, difference.getAddedStart(),
					targetStringList.subList(
						difference.getAddedStart(),
						difference.getAddedEnd() + 1));

				results.add(diffResult);
			}
			else {

				// Lines were deleted from source and added to target at the
				// same position. It needs to check for characters differences.

				_checkCharDiffs(
					addedMarkerStart, addedMarkerEnd, deletedMarkerStart,
					deletedMarkerEnd, sourceStringList, targetStringList,
					difference, results);
			}
		}

		return results;
	}

	private static void _checkCharDiffs(
		String addedMarkerStart, String addedMarkerEnd,
		String deletedMarkerStart, String deletedMarkerEnd,
		List sourceStringList, List targetStringList, Difference difference,
		List results) {

		boolean aligned = false;

		int i = difference.getDeletedStart();
		int j = difference.getAddedStart();

		// A line with changed characters may have its position shifted some
		// lines above or below. These for loops will try to align these lines.
		// While these lines are not aligned, highlight them as either additions
		// or deletions.

		for (; i <= difference.getDeletedEnd(); i++) {
			for (; j <= difference.getAddedEnd(); j++) {
				if (_lineDiff(
						sourceStringList, targetStringList, addedMarkerStart,
						addedMarkerEnd, deletedMarkerStart, deletedMarkerEnd, i,
						j, false, results)) {

					aligned = true;

					break;
				}
				else {
					_highlight(
						targetStringList, j, j, addedMarkerStart,
						addedMarkerEnd);

					DiffResult diffResult = new DiffResult(
						DiffResult.TARGET, j,
						targetStringList.subList(j, j + 1));

					results.add(diffResult);
				}
			}

			if (aligned) {
				 break;
			}
			else {
				_highlight(
					sourceStringList, i, i, deletedMarkerStart,
					deletedMarkerEnd);

				DiffResult diffResult = new DiffResult(
					DiffResult.SOURCE, i, sourceStringList.subList(i, i + 1));

				results.add(diffResult);
			}
		}

		i = i + 1;
		j = j + 1;

		// Lines are aligned, check for differences of the following lines.

		for (; i <= difference.getDeletedEnd() && j <= difference.getAddedEnd();
				i++, j++) {

			_lineDiff(
				sourceStringList, targetStringList, addedMarkerStart,
				addedMarkerEnd, deletedMarkerStart, deletedMarkerEnd, i, j,
				true, results);
		}

		// After the for loop above, some lines might remained unchecked.
		// They are considered as deletions or additions.

		for (; i <= difference.getDeletedEnd();i++) {
			_highlight(
				sourceStringList, i, i, deletedMarkerStart, deletedMarkerEnd);

			DiffResult diffResult = new DiffResult(
				DiffResult.SOURCE, i, sourceStringList.subList(i, i + 1));

			results.add(diffResult);
		}

		for (; j <= difference.getAddedEnd(); j++) {
			_highlight(
				targetStringList, j, j, addedMarkerStart, addedMarkerEnd);

			DiffResult diffResult = new DiffResult(
				DiffResult.TARGET, j, targetStringList.subList(j, j + 1));

			results.add(diffResult);
		}
	}

	private static void _highlight(
		List stringList, int beginPos, int endPos, String markerStart,
		String markerEnd) {

		String start = markerStart + stringList.get(beginPos);

		stringList.set(beginPos, start);

		String end = stringList.get(endPos) + markerEnd;

		stringList.set(endPos, end);
	}

	private static boolean _lineDiff(
		List sourceStringList, List targetStringList, String addedMarkerStart,
		String addedMarkerEnd, String deletedMarkerStart,
		String deletedMarkerEnd, int sourceChangedLine, int targetChangedLine,
		boolean aligned, List results) {

		String source = (String)sourceStringList.get(sourceChangedLine);
		String target = (String)targetStringList.get(targetChangedLine);

		// Convert the lines to lists where each element are chars of the lines.

		List sourceList = _toList(source);
		List targetList = _toList(target);

		Diff diff = new Diff(sourceList, targetList);

		List differences = diff.diff();

		Iterator itr = differences.iterator();

		int deletedChars = 0;
		int addedChars = 0;

		// The following while loop will calculate how many characters of
		// the source line need to be changed to be equals to the target line.

		while (itr.hasNext() && !aligned) {
			Difference difference = (Difference)itr.next();

			if (difference.getDeletedEnd() != Difference.NONE) {
				deletedChars =
					deletedChars +
					(difference.getDeletedEnd() -
						difference.getDeletedStart() + 1);
			}

			if (difference.getAddedEnd() != Difference.NONE) {
				addedChars =
					addedChars +
					(difference.getAddedEnd() - difference.getAddedStart() + 1);
			}
		}

		// If a lot of changes were needed (more than half of the source line
		// length), consider this as not aligned yet.

		if ((deletedChars > (sourceList.size() / 2)) ||
			(addedChars > sourceList.size() / 2)) {

			return false;
		}

		itr = differences.iterator();

		boolean sourceChanged = false;
		boolean targetChanged = false;

		// Iterate over Differences between chars of these lines.

		while (itr.hasNext()) {
			Difference difference = (Difference)itr.next();

			if (difference.getAddedEnd() == Difference.NONE) {

				// Chars were deleted from source only.

				_highlight(
					sourceList, difference.getDeletedStart(),
					difference.getDeletedEnd(), deletedMarkerStart,
					deletedMarkerEnd);

				sourceChanged = true;
			}
			else if (difference.getDeletedEnd() == Difference.NONE) {

				// Chars were added to target only.

				_highlight(
					targetList, difference.getAddedStart(),
					difference.getAddedEnd(), addedMarkerStart, addedMarkerEnd);

				targetChanged = true;
			}
			else {

				// Chars were both deleted and added.

				_highlight(
					sourceList, difference.getDeletedStart(),
					difference.getDeletedEnd(), deletedMarkerStart,
					deletedMarkerEnd);

				sourceChanged = true;

				_highlight(
					targetList, difference.getAddedStart(),
					difference.getAddedEnd(), addedMarkerStart, addedMarkerEnd);

				targetChanged = true;
			}
		}

		if (sourceChanged) {
			DiffResult diffResult = new DiffResult(
				DiffResult.SOURCE, sourceChangedLine, _toString(sourceList));

			results.add(diffResult);
		}

		if (targetChanged) {
			DiffResult diffResult = new DiffResult(
				DiffResult.TARGET, targetChangedLine, _toString(targetList));

			results.add(diffResult);
		}

		return true;
	}

	private static List _toList(String line) {
		String[] stringArray = line.split(StringPool.BLANK);

		List result = new ArrayList();

		for (int i = 1; i < stringArray.length; i++) {
			result.add(stringArray[i]);
		}

		return result;
	}

	private static String _toString(List line) {
		StringMaker sm = new StringMaker();

		Iterator itr = line.iterator();

		while (itr.hasNext()) {
			sm.append((String)itr.next());
		}

		return sm.toString();
	}

}