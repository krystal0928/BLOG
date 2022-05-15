<template>
  <div class="editor-wrap" style="border: 1px solid #ccc">
    <Toolbar
      style="border-bottom: 1px solid #ccc"
      :editor="editorRef"
      :defaultConfig="toolbarConfig"
      :mode="mode"
    />
    <div id="content">
      <div id="editor-container">
        <div id="title-container">
          <input v-model="valueTitle" placeholder="Page title..." @change="changeTitle">
        </div>
        <Editor
          class="wang-editor"
          style="height: 500px; overflow-y: hidden;"
          v-model="valueHtml"
          :defaultConfig="editorConfig"
          :mode="mode"
          @onCreated="handleCreated"
          @onChange="handleChange"
        />
      </div>
    </div>
  </div>
</template>
<script lang="ts" setup>
import '@wangeditor/editor/dist/css/style.css' // 引入 css
import { Editor, Toolbar } from '@wangeditor/editor-for-vue'
import { onBeforeUnmount, shallowRef, ref, watch, watchEffect } from 'vue'
import { IEditorConfig, IDomEditor } from '@wangeditor/core';

const props = defineProps(['title', 'content'])
const emit = defineEmits(['update:title', 'update:content']) 

const mode = 'default'
// 编辑器实例，必须用 shallowRef
const editorRef = shallowRef()

let valueHtml = ref(props.content)
let valueTitle = ref(props.title)

watchEffect(() => {
  valueTitle.value = props.title
  valueHtml.value = props.content
})

const toolbarConfig = {
  excludeKeys: ['fullScreen'],
}
const editorConfig: Partial<IEditorConfig> = {
  placeholder: '请输入内容...',
  scroll: true, // 禁止编辑器滚动
  MENU_CONF: {
    uploadImage: {
      fieldName: 'wang-editor-file',
      base64LimitSize: 10 * 1024 * 1024 // 10M 以下插入 base64
    }
  }
}

const changeTitle = () => {
  emit('update:title', valueTitle)
}

// 组件销毁时，也及时销毁编辑器
onBeforeUnmount(() => {
  const editor = editorRef.value
  if (editor == null) return
  editor.destroy()
})

const handleCreated = (editor) => {
  editorRef.value = editor // 记录 editor 实例，重要！
}
const handleChange = (editor: IDomEditor) => {
  valueHtml.value = editor.getHtml()
  emit('update:content', valueHtml)
}
</script>
<style scoped>
::v-deep .w-e-text-container ul li {
  list-style: disc;
}

::v-deep .w-e-text-container ol li {
  list-style: auto;
}
.editor-wrap {
  width: 100vw;
}
#editor-toolbar {
  width: 1450px;
  background-color: #FCFCFC;
  margin: 0 auto;
}
#content {
  height: calc(100vh - 100px);
  background-color: rgb(245, 245, 245);
  overflow-y: auto;
  position: relative;
}
#editor-container {
  width: 80%;
  margin: 30px auto 20px auto;
  background-color: #fff;
  padding: 20px 50px 50px 50px;
  border: 1px solid #e8e8e8;
  box-shadow: 0 2px 10px rgb(0 0 0 / 12%);
}

#title-container {
  padding: 20px 0;
  border-bottom: 1px solid #e8e8e8;
}

#title-container input {
  font-size: 30px;
  border: 0;
  outline: none;
  width: 100%;
  line-height: 1;
}

.wang-editor {
  min-height: 900px;
  margin-top: 20px;
}
</style>
