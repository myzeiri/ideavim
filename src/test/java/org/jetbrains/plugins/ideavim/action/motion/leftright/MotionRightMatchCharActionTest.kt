/*
 * Copyright 2003-2023 The IdeaVim authors
 *
 * Use of this source code is governed by an MIT-style
 * license that can be found in the LICENSE.txt file or at
 * https://opensource.org/licenses/MIT.
 */

package org.jetbrains.plugins.ideavim.action.motion.leftright

import com.maddyhome.idea.vim.command.VimStateMachine
import org.jetbrains.plugins.ideavim.VimTestCase
import org.junit.jupiter.api.Test

class MotionRightMatchCharActionTest : VimTestCase() {
  @Test
  fun `test move and repeat`() {
    doTest(
      "fx;",
      "hello ${c}x hello x hello",
      "hello x hello ${c}x hello",
      VimStateMachine.Mode.COMMAND,
      VimStateMachine.SubMode.NONE,
    )
  }

  @Test
  fun `test move and repeat twice`() {
    doTest(
      "fx;;",
      "${c}hello x hello x hello x hello",
      "hello x hello x hello ${c}x hello",
      VimStateMachine.Mode.COMMAND,
      VimStateMachine.SubMode.NONE,
    )
  }

  @Test
  fun `test move and repeat two`() {
    doTest(
      "fx2;",
      "${c}hello x hello x hello x hello",
      "hello x hello x hello ${c}x hello",
      VimStateMachine.Mode.COMMAND,
      VimStateMachine.SubMode.NONE,
    )
  }

  @Test
  fun `test move and repeat three`() {
    doTest(
      "fx3;",
      "${c}hello x hello x hello x hello x hello",
      "hello x hello x hello x hello ${c}x hello",
      VimStateMachine.Mode.COMMAND,
      VimStateMachine.SubMode.NONE,
    )
  }
}
