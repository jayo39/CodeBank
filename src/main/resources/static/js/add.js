var editor;
var language = '';
// Initialize Monaco Editor
$(function() {
    require.config({ paths: { 'vs': 'https://cdn.jsdelivr.net/npm/monaco-editor@0.24.0/min/vs' } });
    require(['vs/editor/editor.main'], function () {
        editor = monaco.editor.create(document.getElementById('editor'), {
            value: '',
            language: language,
            theme: 'vs',
            wordWrap: 'on',
            automaticLayout: true,
        });
    
        editor.onDidChangeModelContent(function(event) {
            var value = editor.getValue();
            document.getElementById('textInput').value = value;
        });
    });
})


function changeLanguage(selectElement) {
    language = selectElement.value;
    monaco.editor.setModelLanguage(editor.getModel(), language);
}