/*
 * Copyright 2003-2023 The IdeaVim authors
 *
 * Use of this source code is governed by an MIT-style
 * license that can be found in the LICENSE.txt file or at
 * https://opensource.org/licenses/MIT.
 */

@file:Suppress("RemoveCurlyBracesFromTemplate")

package org.jetbrains.plugins.ideavim.action.motion.visual

import com.maddyhome.idea.vim.command.VimStateMachine
import org.jetbrains.plugins.ideavim.VimTestCase
import org.junit.jupiter.api.Test

class VisualToggleLineModeActionTest : VimTestCase() {
  @Test
  fun `test enter visual with count`() {
    doTest(
      "1V",
      """
                    Lorem Ipsum

                    I ${c}found it in a legendary land
                    consectetur adipiscing elit
                    Sed in orci mauris.
                    Cras id tellus in ex imperdiet egestas.
      """.trimIndent(),
      """
                    Lorem Ipsum

                    ${s}I ${c}found it in a legendary land
                    ${se}consectetur adipiscing elit
                    Sed in orci mauris.
                    Cras id tellus in ex imperdiet egestas.
      """.trimIndent(),
      VimStateMachine.Mode.VISUAL,
      VimStateMachine.SubMode.VISUAL_LINE,
    )
  }

  @Test
  fun `test enter visual with count multicaret`() {
    doTest(
      "1V",
      """
                    Lorem Ipsum

                    I ${c}found it in a legendary land
                    consectetur adipiscing elit
                    where it ${c}was settled on some sodden sand
                    Cras id tellus in ex imperdiet egestas.
      """.trimIndent(),
      """
                    Lorem Ipsum

                    ${s}I ${c}found it in a legendary land
                    ${se}consectetur adipiscing elit
                    ${s}where it ${c}was settled on some sodden sand
                    ${se}Cras id tellus in ex imperdiet egestas.
      """.trimIndent(),
      VimStateMachine.Mode.VISUAL,
      VimStateMachine.SubMode.VISUAL_LINE,
    )
  }

  @Test
  fun `test enter visual with 3 count`() {
    doTest(
      "3V",
      """
                    A Discovery

                    I ${c}found it in a legendary land
                    all rocks and lavender and tufted grass,
                    where it was settled on some sodden sand
                    hard by the torrent of a mountain pass.
      """.trimIndent(),
      """
                    A Discovery

                    ${s}I found it in a legendary land
                    all rocks and lavender and tufted grass,
                    wh${c}ere it was settled on some sodden sand
                    ${se}hard by the torrent of a mountain pass.
      """.trimIndent(),
      VimStateMachine.Mode.VISUAL,
      VimStateMachine.SubMode.VISUAL_LINE,
    )
  }

  @Test
  fun `test enter visual with 100 count`() {
    doTest(
      "100V",
      """
                    A Discovery

                    I ${c}found it in a legendary land
                    all rocks and lavender and tufted grass,
                    where it was settled on some sodden sand
                    hard by the torrent of a mountain pass.
      """.trimIndent(),
      """
                    A Discovery

                    ${s}I found it in a legendary land
                    all rocks and lavender and tufted grass,
                    where it was settled on some sodden sand
                    ha${c}rd by the torrent of a mountain pass.${se}
      """.trimIndent(),
      VimStateMachine.Mode.VISUAL,
      VimStateMachine.SubMode.VISUAL_LINE,
    )
  }

  @Test
  fun `test selectmode option`() {
    configureByText(
      """
                    Lorem Ipsum

                    I${c} found it in a legendary land
                    consectetur adipiscing elit
                    Sed in orci mauris.[long line]
                    Cras id tellus in ex imperdiet egestas.
      """.trimIndent(),
    )
    enterCommand("set selectmode=cmd")
    typeText("V")
    assertState(VimStateMachine.Mode.SELECT, VimStateMachine.SubMode.VISUAL_LINE)
  }
}
