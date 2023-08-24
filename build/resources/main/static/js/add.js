var editor;
var language = '';
var value = '';
var current_code = null;
var current_lang = null;

$(function() {
    require.config({ paths: { 'vs': 'https://cdn.jsdelivr.net/npm/monaco-editor@0.24.0/min/vs' } });
    require(['vs/editor/editor.main'], function () {
        editor = monaco.editor.create(document.getElementById('editor'), {
            value: value,
            language: language,
            theme: 'vs-dark',
            wordWrap: 'on',
            automaticLayout: true,
        });
    
        editor.onDidChangeModelContent(function(event) {
            var value = editor.getValue();
            document.getElementById('textInput').value = value;
        });

        if(current_lang !== null && current_code !== null) {
            language = current_lang.toLowerCase();
            monaco.editor.setModelLanguage(editor.getModel(), language);
            value = current_code;
            editor.getModel().setValue(value);
        }
    });
})


function changeLanguage(selectElement) {
    language = selectElement.value.toLowerCase();
    monaco.editor.setModelLanguage(editor.getModel(), language);
}