/*
 * Copyright 2003-2023 The IdeaVim authors
 *
 * Use of this source code is governed by an MIT-style
 * license that can be found in the LICENSE.txt file or at
 * https://opensource.org/licenses/MIT.
 */

package org.jetbrains.plugins.ideavim.extension.matchit

import com.maddyhome.idea.vim.command.VimStateMachine
import org.jetbrains.plugins.ideavim.VimTestCase
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInfo

class MatchitPhpTest : VimTestCase() {
  @Throws(Exception::class)
  @BeforeEach
  override fun setUp(testInfo: TestInfo) {
    super.setUp(testInfo)
    enableExtensions("matchit")
  }

  @Test
  fun `test basic jump to closing tag`() {
    doTest(
      "%",
      """
        <${c}h1>Heading</h1>
      """.trimIndent(),
      """
        <h1>Heading<$c/h1>
      """.trimIndent(),
      VimStateMachine.Mode.COMMAND,
      VimStateMachine.SubMode.NONE,
      fileName = "file.php"
    )
  }

  @Test
  fun `test basic jump to opening tag`() {
    doTest(
      "%",
      """
        <h1>Heading</${c}h1>
      """.trimIndent(),
      """
        <${c}h1>Heading</h1>
      """.trimIndent(),
      VimStateMachine.Mode.COMMAND,
      VimStateMachine.SubMode.NONE,
      fileName = "file.php"
    )
  }

  /*
   *  g% motion tests. For HTML, g% should behave the same as %.
   */

  @Test
  fun `test reverse jump to closing tag`() {
    doTest(
      "g%",
      """
        <${c}h1>Heading</h1>
      """.trimIndent(),
      """
        <h1>Heading<$c/h1>
      """.trimIndent(),
      VimStateMachine.Mode.COMMAND,
      VimStateMachine.SubMode.NONE,
      fileName = "file.php"
    )
  }

  @Test
  fun `test reverse jump to opening tag`() {
    doTest(
      "g%",
      """
        <h1>Heading</${c}h1>
      """.trimIndent(),
      """
        <${c}h1>Heading</h1>
      """.trimIndent(),
      VimStateMachine.Mode.COMMAND,
      VimStateMachine.SubMode.NONE,
      fileName = "file.php"
    )
  }

}
