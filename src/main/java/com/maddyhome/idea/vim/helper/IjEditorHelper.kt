package com.maddyhome.idea.vim.helper

import com.intellij.openapi.components.Service
import com.maddyhome.idea.vim.api.EngineEditorHelper
import com.maddyhome.idea.vim.api.VimEditor
import com.maddyhome.idea.vim.common.TextRange
import com.maddyhome.idea.vim.newapi.IjVimEditor

@Service
class IjEditorHelper : EngineEditorHelper {
  override fun normalizeOffset(editor: VimEditor, offset: Int, allowEnd: Boolean): Int {
    return EditorHelper.normalizeOffset((editor as IjVimEditor).editor, offset, allowEnd)
  }

  override fun getText(editor: VimEditor, range: TextRange): String {
    return EditorHelper.getText((editor as IjVimEditor).editor, range)
  }

  override fun getOffset(editor: VimEditor, line: Int, column: Int): Int {
    return EditorHelper.getOffset((editor as IjVimEditor).editor, line, column)
  }
}
