/*
 * Copyright 2003-2023 The IdeaVim authors
 *
 * Use of this source code is governed by an MIT-style
 * license that can be found in the LICENSE.txt file or at
 * https://opensource.org/licenses/MIT.
 */

package org.jetbrains.plugins.ideavim.action.motion.text

import com.maddyhome.idea.vim.command.VimStateMachine
import com.maddyhome.idea.vim.helper.VimBehaviorDiffers
import org.jetbrains.plugins.ideavim.VimTestCase
import org.junit.jupiter.api.Test

class MotionSectionForwardEndActionTest : VimTestCase() {
  @VimBehaviorDiffers(originalVimAfter = c, description = "Full text is deleted")
  @Test
  fun `test remove full text`() {
    doTest(
      "d][",
      """
          ${c}Lorem ipsum dolor sit amet,
          consectetur adipiscing elit
          Sed in orci mauris.
          Cras id tellus in ex imperdiet egestas.
      """.trimIndent(),
      "$c.",
      VimStateMachine.Mode.COMMAND,
      VimStateMachine.SubMode.NONE,
    )
  }

  @VimBehaviorDiffers(originalVimAfter = c, description = "Full text is deleted")
  @Test
  fun `test remove full text with new line at the end`() {
    doTest(
      "d][",
      """
          ${c}Lorem ipsum dolor sit amet,
          consectetur adipiscing elit
          Sed in orci mauris.
          Cras id tellus in ex imperdiet egestas.
          
      """.trimIndent(),
      "$c.\n",
      VimStateMachine.Mode.COMMAND,
      VimStateMachine.SubMode.NONE,
    )
  }

  @Test
  fun `test move forward`() {
    doTest(
      "][",
      """
      {
        {
        
        }
      $c}
      {
        {
        }
      }
      {
        {
        }
      }
      """.trimIndent(),
      """
      {
        {
        
        }
      }
      {
        {
        }
      $c}
      {
        {
        }
      }
      """.trimIndent(),
      VimStateMachine.Mode.COMMAND,
      VimStateMachine.SubMode.NONE,
    )
  }

  @Test
  fun `test move forward twice`() {
    doTest(
      "][][",
      """
      {
        {
        $c
        }
      }
      {
        {
        }
      }
      {
        {
        }
      }
      """.trimIndent(),
      """
      {
        {
        
        }
      }
      {
        {
        }
      $c}
      {
        {
        }
      }
      """.trimIndent(),
      VimStateMachine.Mode.COMMAND,
      VimStateMachine.SubMode.NONE,
    )
  }

  @Test
  fun `test move forward till the end`() {
    doTest(
      "][][][",
      """
      {
        {
        $c
        }
      }
      {
        {
        }
      }
      {
        {
        }
      }
      """.trimIndent(),
      """
      {
        {
        
        }
      }
      {
        {
        }
      }
      {
        {
        }
      $c}
      """.trimIndent(),
      VimStateMachine.Mode.COMMAND,
      VimStateMachine.SubMode.NONE,
    )
  }
}
