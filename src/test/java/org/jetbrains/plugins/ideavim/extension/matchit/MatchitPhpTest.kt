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
  fun `test jump from opening HTML angle bracket to closing bracket`() {
    doTest(
      "%",
      "$c<h1>Heading</h1>",
      "<h1$c>Heading</h1>",
      VimStateMachine.Mode.COMMAND,
      VimStateMachine.SubMode.NONE,
      fileName = "file.php",
    )
  }

  @Test
  fun `test jump from opening HTML tag to closing tag`() {
    doTest(
      "%",
      "<${c}h1>Heading</h1>",
      "<h1>Heading<$c/h1>",
      VimStateMachine.Mode.COMMAND,
      VimStateMachine.SubMode.NONE,
      fileName = "file.php",
    )
  }

  @Test
  fun `test jump from opening angle bracket to closing question mark`() {
    doTest(
      "%",
      "$c<?php \$n=1 ?>",
      "<?php \$n=1 $c?>",
      VimStateMachine.Mode.COMMAND,
      VimStateMachine.SubMode.NONE,
      fileName = "file.php",
    )
  }

  @Test
  fun `test jump from whitespace before opening angle bracket to closing question mark`() {
    doTest(
      "%",
      "$c  <?php \$n=1 ?>",
      "  <?php \$n=1 $c?>",
      VimStateMachine.Mode.COMMAND,
      VimStateMachine.SubMode.NONE,
      fileName = "file.php",
    )
  }

  @Test
  fun `test jump from closing angle bracket to opening angle bracket`() {
    doTest(
      "%",
      "<?php \$n=1 ?$c>",
      "$c<?php \$n=1 ?>",
      VimStateMachine.Mode.COMMAND,
      VimStateMachine.SubMode.NONE,
      fileName = "file.php",
    )
  }

  @Test
  fun `test jump from opening question mark to closing question mark`() {
    doTest(
      "%",
      "<$c?php \$n=1 ?>",
      "<?php \$n=1 $c?>",
      VimStateMachine.Mode.COMMAND,
      VimStateMachine.SubMode.NONE,
      fileName = "file.php",
    )
  }

  @Test
  fun `test jump from closing question mark to opening angle bracket`() {
    doTest(
      "%",
      "<?php \$n=1 $c?>",
      "$c<?php \$n=1 ?>",
      VimStateMachine.Mode.COMMAND,
      VimStateMachine.SubMode.NONE,
      fileName = "file.php",
    )
  }

  @Test
  fun `test jump from php to closing question mark`() {
    doTest(
      "%",
      "<?ph${c}p \$n=1 ?>",
      "<?php \$n=1 $c?>",
      VimStateMachine.Mode.COMMAND,
      VimStateMachine.SubMode.NONE,
      fileName = "file.php",
    )
  }

  @Test
  fun `test jump from angle bracket on short opening to closing question mark`() {
    doTest(
      "%",
      "$c<?= func(123) ?>",
      "<?= func(123) $c?>",
      VimStateMachine.Mode.COMMAND,
      VimStateMachine.SubMode.NONE,
      fileName = "file.php",
    )
  }

  @Test
  fun `test jump from question mark on short opening to closing question mark`() {
    doTest(
      "%",
      "<$c?= func(123) ?>",
      "<?= func(123) $c?>",
      VimStateMachine.Mode.COMMAND,
      VimStateMachine.SubMode.NONE,
      fileName = "file.php",
    )
  }

  @Test
  fun `test jump from equals on short opening to closing question mark`() {
    doTest(
      "%",
      "<?$c= func(123) ?>",
      "<?= func(123) $c?>",
      VimStateMachine.Mode.COMMAND,
      VimStateMachine.SubMode.NONE,
      fileName = "file.php",
    )
  }

  @Test
  fun `test jump from function to closing paren`() {
    doTest(
      "%",
      "<?= ${c}func(123) ?>",
      "<?= func(123$c) ?>",
      VimStateMachine.Mode.COMMAND,
      VimStateMachine.SubMode.NONE,
      fileName = "file.php",
    )
  }

  @Test
  fun `test jump from whitespace before question mark to opening angle bracket`() {
    doTest(
      "%",
      "<?= func(123)$c ?>",
      "$c<?= func(123) ?>",
      VimStateMachine.Mode.COMMAND,
      VimStateMachine.SubMode.NONE,
      fileName = "file.php",
    )
  }

  @Test
  fun `test jump from closing angle bracket to opening bracket on short tag`() {
    doTest(
      "%",
      "<?= func(123) ?$c>",
      "$c<?= func(123) ?>",
      VimStateMachine.Mode.COMMAND,
      VimStateMachine.SubMode.NONE,
      fileName = "file.php",
    )
  }

  @Test
  fun `test jump from angle bracket on extra short opening to closing question mark`() {
    doTest(
      "%",
      "$c<? func(123) ?>",
      "<? func(123) $c?>",
      VimStateMachine.Mode.COMMAND,
      VimStateMachine.SubMode.NONE,
      fileName = "file.php",
    )
  }

  @Test
  fun `test jump from question mark on extra short opening to closing question mark`() {
    doTest(
      "%",
      "<$c? func(123) ?>",
      "<? func(123) $c?>",
      VimStateMachine.Mode.COMMAND,
      VimStateMachine.SubMode.NONE,
      fileName = "file.php",
    )
  }

  @Test
  fun `test jump from function to closing paren in extra short tags`() {
    doTest(
      "%",
      "<? ${c}func(123) ?>",
      "<? func(123$c) ?>",
      VimStateMachine.Mode.COMMAND,
      VimStateMachine.SubMode.NONE,
      fileName = "file.php",
    )
  }

  @Test
  fun `test jump from whitespace before question mark to opening extra short tag`() {
    doTest(
      "%",
      "<? func(123)$c ?>",
      "$c<? func(123) ?>",
      VimStateMachine.Mode.COMMAND,
      VimStateMachine.SubMode.NONE,
      fileName = "file.php",
    )
  }

  @Test
  fun `test jump from closing angle bracket to opening bracket on extra short tag`() {
    doTest(
      "%",
      "<? func(123) ?$c>",
      "$c<? func(123) ?>",
      VimStateMachine.Mode.COMMAND,
      VimStateMachine.SubMode.NONE,
      fileName = "file.php",
    )
  }

  /*
   *  g% motion tests.
   */

  @Test
  fun `test reverse jump from opening angle bracket to closing question mark`() {
    doTest(
      "g%",
      "$c<?php \$n=1 ?>",
      "<?php \$n=1 $c?>",
      VimStateMachine.Mode.COMMAND,
      VimStateMachine.SubMode.NONE,
      fileName = "file.php",
    )
  }

  @Test
  fun `test reverse jump from whitespace before opening angle bracket to closing question mark`() {
    doTest(
      "g%",
      "$c  <?php \$n=1 ?>",
      "  <?php \$n=1 $c?>",
      VimStateMachine.Mode.COMMAND,
      VimStateMachine.SubMode.NONE,
      fileName = "file.php",
    )
  }

  @Test
  fun `test reverse jump from closing angle bracket to opening angle bracket`() {
    doTest(
      "g%",
      "<?php \$n=1 ?$c>",
      "$c<?php \$n=1 ?>",
      VimStateMachine.Mode.COMMAND,
      VimStateMachine.SubMode.NONE,
      fileName = "file.php",
    )
  }

  @Test
  fun `test reverse jump from opening question mark to closing question mark`() {
    doTest(
      "g%",
      "<$c?php \$n=1 ?>",
      "<?php \$n=1 $c?>",
      VimStateMachine.Mode.COMMAND,
      VimStateMachine.SubMode.NONE,
      fileName = "file.php",
    )
  }

  @Test
  fun `test reverse jump from closing question mark to opening angle bracket`() {
    doTest(
      "g%",
      "<?php \$n=1 $c?>",
      "$c<?php \$n=1 ?>",
      VimStateMachine.Mode.COMMAND,
      VimStateMachine.SubMode.NONE,
      fileName = "file.php",
    )
  }

  @Test
  fun `test reverse jump from php to closing question mark`() {
    doTest(
      "g%",
      "<?ph${c}p \$n=1 ?>",
      "<?php \$n=1 $c?>",
      VimStateMachine.Mode.COMMAND,
      VimStateMachine.SubMode.NONE,
      fileName = "file.php",
    )
  }

  @Test
  fun `test reverse jump from angle bracket on short opening to closing question mark`() {
    doTest(
      "g%",
      "$c<?= func(123) ?>",
      "<?= func(123) $c?>",
      VimStateMachine.Mode.COMMAND,
      VimStateMachine.SubMode.NONE,
      fileName = "file.php",
    )
  }

  @Test
  fun `test reverse jump from question mark on short opening to closing question mark`() {
    doTest(
      "g%",
      "<$c?= func(123) ?>",
      "<?= func(123) $c?>",
      VimStateMachine.Mode.COMMAND,
      VimStateMachine.SubMode.NONE,
      fileName = "file.php",
    )
  }

  @Test
  fun `test reverse jump from equals on short opening to closing question mark`() {
    doTest(
      "g%",
      "<?$c= func(123) ?>",
      "<?= func(123) $c?>",
      VimStateMachine.Mode.COMMAND,
      VimStateMachine.SubMode.NONE,
      fileName = "file.php",
    )
  }

  @Test
  fun `test reverse jump from function to closing paren`() {
    doTest(
      "g%",
      "<?= ${c}func(123) ?>",
      "<?= func(123$c) ?>",
      VimStateMachine.Mode.COMMAND,
      VimStateMachine.SubMode.NONE,
      fileName = "file.php",
    )
  }

  @Test
  fun `test reverse jump from whitespace before question mark to opening angle bracket`() {
    doTest(
      "g%",
      "<?= func(123)$c ?>",
      "$c<?= func(123) ?>",
      VimStateMachine.Mode.COMMAND,
      VimStateMachine.SubMode.NONE,
      fileName = "file.php",
    )
  }

  @Test
  fun `test reverse jump from closing angle bracket to opening bracket on short tag`() {
    doTest(
      "g%",
      "<?= func(123) ?$c>",
      "$c<?= func(123) ?>",
      VimStateMachine.Mode.COMMAND,
      VimStateMachine.SubMode.NONE,
      fileName = "file.php",
    )
  }

  @Test
  fun `test reverse jump from angle bracket on extra short opening to closing question mark`() {
    doTest(
      "g%",
      "$c<? func(123) ?>",
      "<? func(123) $c?>",
      VimStateMachine.Mode.COMMAND,
      VimStateMachine.SubMode.NONE,
      fileName = "file.php",
    )
  }

  @Test
  fun `test reverse jump from question mark on extra short opening to closing question mark`() {
    doTest(
      "g%",
      "<$c? func(123) ?>",
      "<? func(123) $c?>",
      VimStateMachine.Mode.COMMAND,
      VimStateMachine.SubMode.NONE,
      fileName = "file.php",
    )
  }

  @Test
  fun `test reverse jump from function to closing paren in extra short tags`() {
    doTest(
      "g%",
      "<? ${c}func(123) ?>",
      "<? func(123$c) ?>",
      VimStateMachine.Mode.COMMAND,
      VimStateMachine.SubMode.NONE,
      fileName = "file.php",
    )
  }

  @Test
  fun `test reverse jump from whitespace before question mark to opening extra short tag`() {
    doTest(
      "g%",
      "<? func(123)$c ?>",
      "$c<? func(123) ?>",
      VimStateMachine.Mode.COMMAND,
      VimStateMachine.SubMode.NONE,
      fileName = "file.php",
    )
  }

  @Test
  fun `test reverse jump from closing angle bracket to opening bracket on extra short tag`() {
    doTest(
      "g%",
      "<? func(123) ?$c>",
      "$c<? func(123) ?>",
      VimStateMachine.Mode.COMMAND,
      VimStateMachine.SubMode.NONE,
      fileName = "file.php",
    )
  }


}
