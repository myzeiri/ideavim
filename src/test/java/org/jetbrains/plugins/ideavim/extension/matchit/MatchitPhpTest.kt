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

  @Test
  fun `test jump from if to endif`() {
    doTest(
      "%",
      """
        ${c}if (true):
          echo "true";
        endif;
      """.trimIndent(),
      """
        if (true):
          echo "true";
        ${c}endif;
      """.trimIndent(),
      fileName = "file.php",
    )
  }

  @Test
  fun `test jump from endif to if`() {
    doTest(
      "%",
      """
        if (true):
          echo "true";
        ${c}endif;
      """.trimIndent(),
      """
        ${c}if (true):
          echo "true";
        endif;
      """.trimIndent(),
      fileName = "file.php",
    )
  }

  // NOTE: Dollar signs are excluded from code examples to avoid escaping issues.

  @Test
  fun `test jump from if to else`() {
    doTest(
      "%",
      """
        ${c}if (x > 0):
          echo "x is greater than 0";
        else:
          echo "x is not greater than 0";
        endif;
      """.trimIndent(),
      """
        if (x > 0):
          echo "x is greater than 0";
        ${c}else:
          echo "x is not greater than 0";
        endif;
      """.trimIndent(),
      fileName = "file.php",
    )
  }

  @Test
  fun `test jump from else to endif`() {
    doTest(
      "%",
      """
        if (x > 0):
          echo "x is greater than 0";
        ${c}else:
          echo "x is not greater than 0";
        endif;
      """.trimIndent(),
      """
        if (x > 0):
          echo "x is greater than 0";
        else:
          echo "x is not greater than 0";
        ${c}endif;
      """.trimIndent(),
      fileName = "file.php",
    )
  }

  @Test
  fun `test jump from endif to if in if-else structure`() {
    doTest(
      "%",
      """
        if (x > 0):
          echo "x is greater than 0";
        else:
          echo "x is not greater than 0";
        ${c}endif;
      """.trimIndent(),
      """
        ${c}if (x > 0):
          echo "x is greater than 0";
        else:
          echo "x is not greater than 0";
        endif;
      """.trimIndent(),
      fileName = "file.php",
    )
  }

  @Test
  fun `test jump from if to elseif`() {
    doTest(
      "%",
      """
        ${c}if (first):
          echo "first"
        elseif (second):
          echo "second"
        else:
          echo "false"
        endif;
      """.trimIndent(),
      """
        if (first):
          echo "first"
        ${c}elseif (second):
          echo "second"
        else:
          echo "false"
        endif;
      """.trimIndent(),
      fileName = "file.php",
    )
  }

  @Test
  fun `test jump from elseif to else`() {
    doTest(
      "%",
      """
        if (first):
          echo "first"
        ${c}elseif (second):
          echo "second"
        else:
          echo "false"
        endif;
      """.trimIndent(),
      """
        if (first):
          echo "first"
        elseif (second):
          echo "second"
        ${c}else:
          echo "false"
        endif;
      """.trimIndent(),
      fileName = "file.php",
    )
  }

  @Test
  fun `test jump from endif to if in else-if structure`() {
    doTest(
      "%",
      """
        if (first):
          echo "first"
        elseif (second):
          echo "second"
        else:
          echo "false"
        ${c}endif;
      """.trimIndent(),
      """
        ${c}if (first):
          echo "first"
        elseif (second):
          echo "second"
        else:
          echo "false"
        endif;
      """.trimIndent(),
      fileName = "file.php",
    )
  }

  @Test
  fun `test jump from switch to case`() {
    doTest(
      "%",
      """
        ${c}switch (x):
          case "one":
            echo "one";
            continue;
          case "two":
            echo "two";
            break;
        endswitch;
      """.trimIndent(),
      """
        switch (x):
          ${c}case "one":
            echo "one";
            continue;
          case "two":
            echo "two";
            break;
        endswitch;
      """.trimIndent(),
      fileName = "file.php",
    )
  }

  @Test
  fun `test jump from case to continue`() {
    doTest(
      "%",
      """
        switch (x):
          ${c}case "one":
            echo "one";
            continue;
          case "two":
            echo "two";
            break;
        endswitch;
      """.trimIndent(),
      """
        switch (x):
          case "one":
            echo "one";
            ${c}continue;
          case "two":
            echo "two";
            break;
        endswitch;
      """.trimIndent(),
      fileName = "file.php",
    )
  }

  @Test
  fun `test jump from continue to case`() {
    doTest(
      "%",
      """
        switch (x):
          case "one":
            echo "one";
            ${c}continue;
          case "two":
            echo "two";
            break;
        endswitch;
      """.trimIndent(),
      """
        switch (x):
          case "one":
            echo "one";
            continue;
          ${c}case "two":
            echo "two";
            break;
        endswitch;
      """.trimIndent(),
      fileName = "file.php",
    )
  }

  @Test
  fun `test jump from case to break`() {
    doTest(
      "%",
      """
        switch (x):
          case "one":
            echo "one";
            continue;
          ${c}case "two":
            echo "two";
            break;
        endswitch;
      """.trimIndent(),
      """
        switch (x):
          case "one":
            echo "one";
            continue;
          case "two":
            echo "two";
            ${c}break;
        endswitch;
      """.trimIndent(),
      fileName = "file.php",
    )
  }

  @Test
  fun `test jump from break to endswitch`() {
    doTest(
      "%",
      """
        switch (x):
          case "one":
            echo "one";
            continue;
          case "two":
            echo "two";
            ${c}break;
        endswitch;
      """.trimIndent(),
      """
        switch (x):
          case "one":
            echo "one";
            continue;
          case "two":
            echo "two";
            break;
        ${c}endswitch;
      """.trimIndent(),
      fileName = "file.php",
    )
  }

  @Test
  fun `test jump from endswitch to switch`() {
    doTest(
      "%",
      """
        switch (x):
          case "one":
            echo "one";
            continue;
          case "two":
            echo "two";
            break;
        ${c}endswitch;
      """.trimIndent(),
      """
        ${c}switch (x):
          case "one":
            echo "one";
            continue;
          case "two":
            echo "two";
            break;
        endswitch;
      """.trimIndent(),
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

  @Test
  fun `test reverse jump from if to endif`() {
    doTest(
      "g%",
      """
        ${c}if (true):
          echo "true";
        endif;
      """.trimIndent(),
      """
        if (true):
          echo "true";
        ${c}endif;
      """.trimIndent(),
      fileName = "file.php",
    )
  }

  @Test
  fun `test reverse jump from endif to if`() {
    doTest(
      "g%",
      """
        if (true):
          echo "true";
        ${c}endif;
      """.trimIndent(),
      """
        ${c}if (true):
          echo "true";
        endif;
      """.trimIndent(),
      fileName = "file.php",
    )
  }

  @Test
  fun `test reverse jump from if to endif in if-else structure`() {
    doTest(
      "g%",
      """
        ${c}if (x > 0):
          echo "x is greater than 0";
        else:
          echo "x is not greater than 0";
        endif;
      """.trimIndent(),
      """
        if (x > 0):
          echo "x is greater than 0";
        else:
          echo "x is not greater than 0";
        ${c}endif;
      """.trimIndent(),
      fileName = "file.php",
    )
  }

  @Test
  fun `test reverse jump from else to if`() {
    doTest(
      "g%",
      """
        if (x > 0):
          echo "x is greater than 0";
        ${c}else:
          echo "x is not greater than 0";
        endif;
      """.trimIndent(),
      """
        ${c}if (x > 0):
          echo "x is greater than 0";
        else:
          echo "x is not greater than 0";
        endif;
      """.trimIndent(),
      fileName = "file.php",
    )
  }

  @Test
  fun `test reverse jump from endif to else in if-else structure`() {
    doTest(
      "g%",
      """
        if (x > 0):
          echo "x is greater than 0";
        else:
          echo "x is not greater than 0";
        ${c}endif;
      """.trimIndent(),
      """
        if (x > 0):
          echo "x is greater than 0";
        ${c}else:
          echo "x is not greater than 0";
        endif;
      """.trimIndent(),
      fileName = "file.php",
    )
  }

  @Test
  fun `test reverse jump from elseif to if`() {
    doTest(
      "g%",
      """
        if (first):
          echo "first"
        ${c}elseif (second):
          echo "second"
        else:
          echo "false"
        endif;
      """.trimIndent(),
      """
        ${c}if (first):
          echo "first"
        elseif (second):
          echo "second"
        else:
          echo "false"
        endif;
      """.trimIndent(),
      fileName = "file.php",
    )
  }

  @Test
  fun `test reverse jump from else to elseif`() {
    doTest(
      "g%",
      """
        if (first):
          echo "first"
        elseif (second):
          echo "second"
        ${c}else:
          echo "false"
        endif;
      """.trimIndent(),
      """
        if (first):
          echo "first"
        ${c}elseif (second):
          echo "second"
        else:
          echo "false"
        endif;
      """.trimIndent(),
      fileName = "file.php",
    )
  }

  @Test
  fun `test reverse jump from elseif to elseif`() {
    doTest(
      "g%",
      """
        if (first):
          echo "first"
        elseif (second):
          echo "second"
        ${c}elseif (third):
          echo "third"
        else:
          echo "false"
        endif;
      """.trimIndent(),
      """
        if (first):
          echo "first"
        ${c}elseif (second):
          echo "second"
        elseif (third):
          echo "third"
        else:
          echo "false"
        endif;
      """.trimIndent(),
      fileName = "file.php",
    )
  }

  @Test
  fun `test reverse jump from switch to endswitch`() {
    doTest(
      "g%",
      """
        ${c}switch (x):
          case "one":
            echo "one";
            continue;
          case "two":
            echo "two";
            break;
        endswitch;
      """.trimIndent(),
      """
        switch (x):
          case "one":
            echo "one";
            continue;
          case "two":
            echo "two";
            break;
        ${c}endswitch;
      """.trimIndent(),
      fileName = "file.php",
    )
  }

  @Test
  fun `test reverse jump from endswitch to break`() {
    doTest(
      "g%",
      """
        switch (x):
          case "one":
            echo "one";
            continue;
          case "two":
            echo "two";
            break;
        ${c}endswitch;
      """.trimIndent(),
      """
        switch (x):
          case "one":
            echo "one";
            continue;
          case "two":
            echo "two";
            ${c}break;
        endswitch;
      """.trimIndent(),
      fileName = "file.php",
    )
  }

  @Test
  fun `test reverse jump from break to case`() {
    doTest(
      "g%",
      """
        switch (x):
          case "one":
            echo "one";
            continue;
          case "two":
            echo "two";
            ${c}break;
        endswitch;
      """.trimIndent(),
      """
        switch (x):
          case "one":
            echo "one";
            continue;
          ${c}case "two":
            echo "two";
            break;
        endswitch;
      """.trimIndent(),
      fileName = "file.php",
    )
  }

  @Test
  fun `test reverse jump from case to continue`() {
    doTest(
      "g%",
      """
        switch (x):
          case "one":
            echo "one";
            continue;
          ${c}case "two":
            echo "two";
            break;
        endswitch;
      """.trimIndent(),
      """
        switch (x):
          case "one":
            echo "one";
            ${c}continue;
          case "two":
            echo "two";
            break;
        endswitch;
      """.trimIndent(),
      fileName = "file.php",
    )
  }

  @Test
  fun `test reverse jump from continue to case`() {
    doTest(
      "g%",
      """
        switch (x):
          case "one":
            echo "one";
            ${c}continue;
          case "two":
            echo "two";
            break;
        endswitch;
      """.trimIndent(),
      """
        switch (x):
          ${c}case "one":
            echo "one";
            continue;
          case "two":
            echo "two";
            break;
        endswitch;
      """.trimIndent(),
      fileName = "file.php",
    )
  }

  @Test
  fun `test reverse jump from case to switch`() {
    doTest(
      "g%",
      """
        switch (x):
          ${c}case "one":
            echo "one";
            continue;
          case "two":
            echo "two";
            break;
        endswitch;
      """.trimIndent(),
      """
        ${c}switch (x):
          case "one":
            echo "one";
            continue;
          case "two":
            echo "two";
            break;
        endswitch;
      """.trimIndent(),
      fileName = "file.php",
    )
  }

}
