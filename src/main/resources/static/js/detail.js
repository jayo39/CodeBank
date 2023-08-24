var editor;
var language = '';
var value = '';
const copyBtn = $('#copybtn');

$(function() {
    require.config({ paths: { 'vs': 'https://cdn.jsdelivr.net/npm/monaco-editor@0.24.0/min/vs' } });
    require(['vs/editor/editor.main'], function () {
        editor = monaco.editor.create(document.getElementById('editor'), {
            value: value,
            language: language,
            theme: 'vs-dark',
            wordWrap: 'on',
            automaticLayout: true,
            readOnly: true
        });

        var lower_lang = set_lang.toLowerCase();
        monaco.editor.setModelLanguage(editor.getModel(), lower_lang);
        var decoded = $("<textarea/>").html(set_code).val();
        editor.getModel().setValue(decoded);
    });

    copyBtn.on('click', function () {
        var editorValue = editor.getValue();
        copyToClipboard(editorValue);
    });
})

function copyToClipboard(text) {
    navigator.clipboard.writeText(text).then(function() {
      alert('Copied to clipboard!');
    }).catch(function(err) {
      console.error('Unable to copy text to clipboard', err);
    });
}