function changeImage() {
    const newImage = `
          <div id="image-input">
               <input class="fileinput col-xs-3 form-control" type="file" name="upfile"/>
          </div>
    `
    $('#image-input').replaceWith(newImage);
}