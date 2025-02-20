/*
 * Copyright 2003-2023 The IdeaVim authors
 *
 * Use of this source code is governed by an MIT-style
 * license that can be found in the LICENSE.txt file or at
 * https://opensource.org/licenses/MIT.
 */
package com.maddyhome.idea.vim.action.motion.gn

import com.maddyhome.idea.vim.api.ExecutionContext
import com.maddyhome.idea.vim.api.VimEditor
import com.maddyhome.idea.vim.api.injector
import com.maddyhome.idea.vim.command.Argument
import com.maddyhome.idea.vim.command.CommandFlags
import com.maddyhome.idea.vim.command.MotionType
import com.maddyhome.idea.vim.command.OperatorArguments
import com.maddyhome.idea.vim.command.VimStateMachine
import com.maddyhome.idea.vim.handler.Motion
import com.maddyhome.idea.vim.handler.MotionActionHandler
import com.maddyhome.idea.vim.handler.toMotionOrError
import com.maddyhome.idea.vim.helper.inVisualMode
import com.maddyhome.idea.vim.helper.noneOfEnum
import java.util.*
import kotlin.math.max

public class VisualSelectNextSearch : MotionActionHandler.SingleExecution() {
  override val flags: EnumSet<CommandFlags> = noneOfEnum()

  override fun getOffset(
    editor: VimEditor,
    context: ExecutionContext,
    argument: Argument?,
    operatorArguments: OperatorArguments,
  ): Motion {
    return selectNextSearch(editor, operatorArguments.count1, true).toMotionOrError()
  }

  override val motionType: MotionType = MotionType.EXCLUSIVE
}

public class VisualSelectPreviousSearch : MotionActionHandler.SingleExecution() {
  override val flags: EnumSet<CommandFlags> = noneOfEnum()

  override fun getOffset(
    editor: VimEditor,
    context: ExecutionContext,
    argument: Argument?,
    operatorArguments: OperatorArguments,
  ): Motion {
    return selectNextSearch(editor, operatorArguments.count1, false).toMotionOrError()
  }

  override val motionType: MotionType = MotionType.EXCLUSIVE
}

private fun selectNextSearch(editor: VimEditor, count: Int, forwards: Boolean): Int {
  val caret = editor.primaryCaret()
  val range = injector.searchGroup.getNextSearchRange(editor, count, forwards) ?: return -1
  val adj = injector.visualMotionGroup.selectionAdj
  if (!editor.inVisualMode) {
    val startOffset = if (forwards) range.startOffset else max(range.endOffset - adj, 0)
    caret.moveToOffset(startOffset)
    injector.visualMotionGroup.enterVisualMode(editor, VimStateMachine.SubMode.VISUAL_CHARACTER)
  }
  return if (forwards) max(range.endOffset - adj, 0) else range.startOffset
}
