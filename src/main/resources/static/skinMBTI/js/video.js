// Init camera
function camInit(stream) {
  var cameraView = document.getElementById("cameraview");
  cameraView.srcObject = stream;
  cameraView.play();
}

function camInitFailed(error) {
  console.log("get camera permission failed : ", error)
}

// Main init

function mainInit() {
  // Check navigator media device available
  if(!navigator.mediaDevices || !navigator.mediaDevices.getUserMedia )
  {
      // Navigator mediaDevices not supported
      alert("Media Device not supported")
      return;
  }

  navigator.mediaDevices.getUserMedia({video:true})
      .then(camInit)
      .catch(camInitFailed);

}