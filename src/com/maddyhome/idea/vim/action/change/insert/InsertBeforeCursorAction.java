/*
 * IdeaVim - Vim emulator for IDEs based on the IntelliJ platform
 * Copyright (C) 2003-2019 The IdeaVim authors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */

package com.maddyhome.idea.vim.action.change.insert;

import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.editor.Editor;
import com.maddyhome.idea.vim.VimPlugin;
import com.maddyhome.idea.vim.action.VimCommandAction;
import com.maddyhome.idea.vim.command.Argument;
import com.maddyhome.idea.vim.command.Command;
import com.maddyhome.idea.vim.command.CommandFlags;
import com.maddyhome.idea.vim.command.MappingMode;
import com.maddyhome.idea.vim.handler.ChangeEditorActionHandler;
import com.maddyhome.idea.vim.handler.VimActionHandler;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

final public class InsertBeforeCursorAction extends VimCommandAction {
  @Contract(" -> new")
  @NotNull
  @Override
  final protected VimActionHandler makeActionHandler() {
    return new ChangeEditorActionHandler.SingleExecution() {
      @Override
      final public boolean execute(@NotNull Editor editor, @NotNull DataContext context, int count, int rawCount,
                                   @Nullable Argument argument) {
        VimPlugin.getChange().insertBeforeCursor(editor, context);
        return true;
      }
    };
  }

  @Contract(pure = true)
  @NotNull
  @Override
  final public Set<MappingMode> getMappingModes() {
    return MappingMode.N;
  }

  @NotNull
  @Override
  final public Set<List<KeyStroke>> getKeyStrokesSet() {
    return parseKeysSet("i", "<Insert>");
  }

  @Contract(pure = true)
  @NotNull
  @Override
  final public Command.Type getType() {
    return Command.Type.INSERT;
  }

  @NotNull
  @Override
  final public EnumSet<CommandFlags> getFlags() {
    return EnumSet.of(CommandFlags.FLAG_MULTIKEY_UNDO);
  }
}
